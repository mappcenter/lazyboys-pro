/* 
 * File:   TagDB.cpp
 * Author: khoint
 * 
 * Created on September 28, 2012, 8:31 AM
 */

#include "TagDB.h"
#include "ItemTagDB.h"
#include "synchronizeDB.h"

TagDB::TagDB() {
    logger = &Logger::get("TagDB");
}

TagDB::TagDB(string path) {
    logger = &Logger::get("TagDB");
    pathHashDB = path;
}

TagDB::~TagDB() {
}

string TagDB::convertTagToJson(Tag& tag) {
    Json::Value value;
    value["tagName"] = tag.tagName;
    value["viewCounts"] = static_cast<int> (tag.viewCounts);
    value["dateAdd"] = tag.dateAdd;
    value["dateUpdate"] = tag.dateUpdate;
    //Json::StyledWriter writer;
    FastWriter writer;
    string jsonText = writer.write(value);
    //poco_information_f1(*logger, "convertTagToJson: Convert from Tag %s to json successful", tag.tagID);
    return jsonText;
}

Tag TagDB::convertJsonToTag(string jsonString) {
    Tag itemReturn;
    Json::Value root;
    Json::Reader reader;
    bool parsedSuccess = reader.parse(jsonString, root, false);
    if (not parsedSuccess) {
        // Report failures and their locations in the document.
        cout << "Failed to parse JSON" << endl
                << reader.getFormatedErrorMessages()
                << endl;
        poco_error_f1(*logger, "convertJsonToTag: Failed to parse JSON %s", reader.getFormatedErrorMessages());
        return itemReturn;
    }
    //Json::Value tagID = root["tagID"];
    Json::Value tagName = root["tagName"];
    Json::Value viewCounts = root["viewCounts"];
    Json::Value dateAdd = root["dateAdd"];
    Json::Value dateUpdate = root["dateUpdate"];

    //itemReturn.tagID = tagID.asString();
    itemReturn.tagName = tagName.asString();
    itemReturn.viewCounts = viewCounts.asInt();
    itemReturn.dateAdd = dateAdd.asString();
    itemReturn.dateUpdate = dateUpdate.asString();
    //poco_information(*logger, "convertJsonToTag: Convert from Json to Tag successfull");
    return itemReturn;
}

void TagDB::startTagDB() {
    double sysTime1 = clock();
    DBUtils::openHashDB(hashDB, pathHashDB);
    DBUtils::openGrassDB(grassDB);
    cout << "Copying TagDB..." << endl;
    DBUtils::copyDBFromHashDBtoGrassDB(hashDB, grassDB);
    double sysTime2 = clock();
    DBUtils::initalizeLastID(grassDB);
    //cout << "Time to search: " << (double) (sysTime2 - sysTime1) << endl;
    poco_information_f1(*logger, "startTagDB: Start TagDB in %0.0f milliseconds.", (double) (sysTime2 - sysTime1));
    return;
}

void TagDB::stopTagDB() {
    DBUtils::closeBasicDB(hashDB);
    DBUtils::closeBasicDB(grassDB);
    poco_information(*logger, "stopTagDB: Stop TagDB");
    return;
}

Tag TagDB::getTag(string tagID) {
    Tag tag;
    string value;
    if (grassDB.get(tagID, &value)) {
    } else {
        cerr << "getTag: Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getTag: Can't open tagID %s in GrassDB", tagID);
        tag.tagID = "-1";
        cerr << "getTag: Error on Item : " << tag.tagID << endl;
        return tag;
    }
    tag = convertJsonToTag(value);
    tag.tagID = tagID;
    //poco_information_f1(*logger, "getTag: getTag In GrassDB: Get TagID = %s successful", tag.tagID);
    return tag;
}

vector<Tag> TagDB::getTagKeyword(string keyword) {
    vector<Tag> result;
    if (keyword.empty()) {
        poco_error(*logger, "getTagKeyword: keyword is NULL.");
        return result;
    }
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {

        if (ckey != "lastID" && Utils::findStringInStringForTag(cvalue, keyword)) {
            Tag tag = convertJsonToTag(cvalue);
            tag.tagID = ckey;
            result.push_back(tag);
        }
    }
    delete cur;
    return result;

}

vector<Tag> TagDB::getAllTag() {
    vector<Tag> listTag;
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {
        if (ckey != "lastID") {
            Tag tag = convertJsonToTag(cvalue);
            tag.tagID = ckey;
            listTag.push_back(tag);
        }
    }
    delete cur;
    //cout << "t" << listTag.size() << endl;
    return listTag;
}

//vector<string> TagDB::getIDListOfTags(string tagID) {
//    vector<string> listIDofTag;
//    if (grassDB.check(tagID) == -1) {
//        return listIDofTag;
//    } 
//}

bool TagDB::insertTag(string tagID, string tagName, ItemTagDB& itemTagDB) {
    if (grassDB.check(tagID) != -1) {
        return false;
    }
    Tag newTag;
    newTag.tagID = tagID;
    newTag.tagName = tagName;
    newTag.viewCounts = 0;
    newTag.dateAdd = Utils::getTimeNow();
    newTag.dateUpdate = "Today";
    return insertTag(newTag, itemTagDB);
}

bool TagDB::insertTag(Tag& tag, ItemTagDB& itemTagDB) {
    try {
        string jsonString = convertTagToJson(tag);
        string ckey = tag.tagID;
        if (grassDB.check(ckey) != -1) {
            //cout << "Data existed!" << endl;
            poco_error_f1(*logger, "insertTag: Data existed key=%s", tag.tagID);
            return false;
        }
        grassDB.set(ckey, jsonString);
        addQueue(ADD, ckey, jsonString);
        DBUtils::setLastID(grassDB, ckey);
        
        vector<string> lItemID;
        itemTagDB.insertItemTag(tag.tagID, lItemID);
        return true;
    } catch (char *str) {
        //cout << str << endl;
        poco_error_f1(*logger, "insertTag: Error insertTag: %s", str);
        return false;
    }
}

/**
 * Them moi mot tag.
 * @param tagName
 * @return bool
 */
bool TagDB::insertTag(string tagName, ItemTagDB& itemTagDB) {
    int temp = Utils::convertStringToInt(DBUtils::getLastID(grassDB));
    string lastID = Utils::convertIntToString(temp+1);
    return insertTag(lastID, tagName, itemTagDB);
}

bool TagDB::deleteTag(string tagID, ItemTagDB& itemTagDB) {
    if (grassDB.check(tagID) != -1) {
        grassDB.remove(tagID);
        itemTagDB.deleteItemTag(tagID);
        addQueue(DELETE, tagID, "");
        return true;
    }
    //cout << "Data doesn't exit!" << endl;
    poco_error(*logger, "deleteTag: Error deleteTag data doesn't existed!");
    return false;
}

bool TagDB::deleteTag(Tag tag, ItemTagDB& itemTagDB) {
    return deleteTag(tag.tagID, itemTagDB);
}

bool TagDB::editTag(string tagID, string tagName) {
    Tag tag;
    tag.tagID = tagID;
    tag.tagName = tagName;
    tag.viewCounts = 0;
    //    time_t now = time(0);
    // convert now to string form
    //    char* dt = ctime(&now);
    tag.dateAdd = Utils::getTimeNow();
    string jsonString = convertTagToJson(tag);
    try {
        grassDB.replace(tagID, jsonString);
        addQueue(UPDATE, tagID, jsonString);
        return true;
    } catch (char* str) {
        //cout << "Edit error!" << endl;
        poco_information_f1(*logger, "editTag: Error editTag %s", str);
        return false;
    }
}

bool TagDB::setViewCountTag(string tagID) {
    if (grassDB.check(tagID) == -1) {
        return false;
    }
    Tag tag = getTag(tagID);
    tag.viewCounts++;
    string jsonString = convertTagToJson(tag);
    try {
        grassDB.replace(tagID, jsonString);
        addQueue(UPDATE, tagID, jsonString);
        return true;
    } catch (char* str) {
        poco_error_f1(*logger, "setViewCountTag: Error editTag %s", str);
        return false;
    }
}

// Toan viet ham nay de test.

bool TagDB::setViewCountTag(string tagID, int viewCounts) {
    if (grassDB.check(tagID) == -1) {
        return false;
    }
    Tag tag = getTag(tagID);
    tag.viewCounts = viewCounts;
    string jsonString = convertTagToJson(tag);
    try {
        grassDB.replace(tagID, jsonString);
        addQueue(UPDATE, tagID, jsonString);
        return true;
    } catch (char* str) {
        poco_error_f1(*logger, "setViewCountTag: Error editTag %s", str);
        return false;
    }
}

bool TagDB::deleteAllTag(ItemTagDB& itemTagDB) {
    try {
        grassDB.clear();
        itemTagDB.deleteAllItemTag();
        return true;
    } catch (char* str) {
        //cout << "error deleteAllTag" << endl;
        poco_error_f1(*logger, "deleteAllTag: Error deleteAllTag %s", str);
        return false;
    }
}

bool TagDB::deleteAllTag(vector<string> tagIDs, ItemTagDB& itemTagDB) {
    try {
        int n = tagIDs.size();
        for (int i = 0; i < n; i++) {
            deleteTag(tagIDs[i], itemTagDB);
            //itemTagDB.deleteItemTag(tagIDs[i]);
        }
        return true;
    } catch (char* str) {
        //cout << "error deleteAllTag" << endl;
        poco_error_f1(*logger, "deleteAllTag: Error deleteAllTag %s", str);
        return false;
    }
}

vector<Tag> TagDB::getTopTags(int number) {
    vector<Tag> topTag;
    if (number == 0 || number<-1) {
        poco_error_f1(*logger, "getTopTags: Number %d is available", number);
        return topTag;
    }

    //    int siz>GrassDB = grassDB.count() - 1; // trừ 1 vì trừ ra key="lastID"
    //    int arr[sizeGrassDB];
    //    DB::Cursor* cur = grassDB.cursor();
    //    cur->jump();
    //    string ckey, cvalue;
    //    int count = 0;
    //    while (cur->get(&ckey, &cvalue, true)) {
    //        if (ckey == LASTID)
    //            continue;
    //        Tag tag = convertJsonToTag(cvalue);
    //        tag.tagID = ckey;
    //        arr[count++] = tag.viewCounts;
    //    }
    //    delete cur;
    //    //sap xep viewcount
    //    for (int i = 0; i < sizeGrassDB; i++) {
    //        int max = arr[i];
    //        for (int j = i + 1; j < sizeGrassDB; j++) {
    //            if (max < arr[j]) {
    //                int temp = max;
    //                max = arr[j];
    //                arr[j] = temp;
    //            }
    //        }
    //    }
    //    //lay top number tag co viewcount lon nhat
    //    for (int i = 10; i < number; i++) {
    //        DB::Cursor* cur = grassDB.cursor();
    //        cur->jump();
    //        string ckey, cvalue;
    //        while (cur->get(&ckey, &cvalue, true)) {
    //            if (ckey == LASTID)
    //                continue;
    //            Tag tag = convertJsonToTag(cvalue);
    //            tag.tagID = ckey;
    //            if (tag.viewCounts == arr[i]) {
    //                topTag.push_back(tag);
    //                if (topTag.size() == number) {
    //                    delete cur;
    //                    return topTag;
    //                }
    //            }
    //        }
    //        delete cur;
    //    }

    vector<Tag> listTag = getAllTag();

    int n = listTag.size();
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (listTag.at(i).viewCounts < listTag.at(j).viewCounts) {
                Tag temp = listTag.at(i);
                listTag.at(i) = listTag.at(j);
                listTag.at(j) = temp;
            }
        }
    }
    if (number > n) {
        poco_warning_f2(*logger, "getTopTags: number= %d > listTag.size()= %d", number, n);
        number = n;
    }
    for (int i = 0; i < number; i++) {
        topTag.push_back(listTag.at(i));
    }
    return topTag;
}

HashDB& TagDB::getHashDB() {
    return hashDB;
}

int64_t TagDB::getTagDBSize() {
    return grassDB.count()-1;
}

void TagDB::setSynDB(synchronizeDB* synDBPtr) {
    syndb = synDBPtr;
}

void TagDB::addQueue(string command, string tagID, string jsonString) {
    Poco::Tuple<string, string, string> element(command, tagID, jsonString);
    syndb->listTags.push_back(element);
}