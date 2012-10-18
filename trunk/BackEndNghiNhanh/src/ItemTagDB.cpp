/* 
 * File:   ItemTagDB.cpp
 * Author: khoint
 * 
 * Created on September 28, 2012, 4:25 PM
 */

#include "ItemTagDB.h"
#include "synchronizeDB.h"

ItemTagDB::ItemTagDB() {
    logger = &Logger::get("ItemTagDB");
}

ItemTagDB::ItemTagDB(string path) {
    logger = &Logger::get("ItemTagDB");
    pathHashDB = path;
}

ItemTagDB::~ItemTagDB() {
}

void ItemTagDB::startItemTagDB() {
    double sysTime1 = clock();
    DBUtils::openHashDB(hashDB, pathHashDB);
    DBUtils::openGrassDB(grassDB);
    cout << "Copying ItemTagDB" << endl;
    DBUtils::copyDBFromHashDBtoGrassDB(hashDB, grassDB);
    double sysTime2 = clock();
    //cout << "Time to search: " << (double) (sysTime2 - sysTime1) << endl;
    poco_information_f1(*logger, "startItemDB: Start ItemTagDB in %0.0f milliseconds.", (double) (sysTime2 - sysTime1));
    return;
}

void ItemTagDB::stopItemTagDB() {
    DBUtils::closeBasicDB(hashDB);
    DBUtils::closeBasicDB(grassDB);
    poco_information(*logger, "Stop ItemTagDB");
    return;
}

string ItemTagDB::convertItemTagToJson(ItemTag& itemtag) {
    Value value;
    value["itemsID"];
    for (int i = 0; i < itemtag.itemsID.size(); i++) {
        //cout << "item tag i : " << item.tags[i];
        value["itemsID"][i] = itemtag.itemsID[i];
        //cout <<value["tags"][i];
    }
    StyledWriter writer; //not use Json::Writer because it is a virtual class
    string jsonString = writer.write(value);
    //poco_information_f1(*logger, "convertItemToJson: Convert from Item %s to json successful", item.itemID);
    return jsonString;
}

ItemTag ItemTagDB::convertJsonToItemTag(string jsonString) {
    ItemTag itemtag;
    Value root;
    Reader reader;
    bool parsedSuccess = reader.parse(jsonString, root, false);
    if (not parsedSuccess) {
        // Report failures and their locations 
        // in the document.
        cout << "Failed to parse JSON" << endl
                << reader.getFormatedErrorMessages()
                << endl;
        poco_error_f1(*logger, "convertJsonToItemTag: Failed to parse JSON %s", reader.getFormatedErrorMessages());
        return itemtag;
    }
    // Let's extract the array contained in the root object
    Value itemsID = root["itemsID"];
    for (int i = 0; i < itemsID.size(); i++) {
        string str = itemsID[i].asString();
        itemtag.itemsID.push_back(str);
    }
    //poco_information(*logger, "convertJsonToItem: Convert from Json to Item successfull");
    return itemtag;
}

ItemTag ItemTagDB::getItemTag(string tagID) {
    string value;
    ItemTag itemtag;
    if (grassDB.get(tagID, &value)) {
        cout << "Read " << tagID << ": " << value << endl;
    } else {
        cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getItemTag: Can't open tagID %s in grassDB", tagID);
        itemtag.tagID = "-1";
        return itemtag;
    }
    itemtag = convertJsonToItemTag(value);
    itemtag.tagID = tagID;
    //poco_information_f1(*logger, "getItemInHashDB: Get ItemID = %s successful", itemReturn.itemID);
    return itemtag;
}

/**
 * lay tat ca itemID thuoc co tagID
 * @param tagID
 * @return vector<string>
 */
vector<string> ItemTagDB::getAllItemsIdHaveTag(string tagID) {
    vector<string> items;
    ItemTag itemtag;
    string value;
    if (grassDB.get(tagID, &value)) {
        //cout << "Read " << tagID << ": " << value << endl;
        itemtag = convertJsonToItemTag(value);
        itemtag.tagID = tagID;
        items = itemtag.itemsID;
        return items;
    } else {
        cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getItemTag: Can't open tagID %s in GrassDB", tagID);
        return items;
    }
}

vector<string> ItemTagDB::getAllItemsIdHaveTag(string tagID, int32_t numberItemsID) {
    vector<string> lItemsID;
    vector<string> result;
    if (grassDB.check(tagID) == -1) {
        poco_error_f1(*logger, "getAllItemsIdHaveTag: doesn't exits tagID = %s in ItemTagDB", tagID);
        return result;
    }
    if (numberItemsID < 1) {
        poco_error(*logger, "getAllItemsIdHaveTag: numberItemsID < 1");
        return result;
    }
    lItemsID = getAllItemsIdHaveTag(tagID);
    int64_t size = lItemsID.size();
    if (numberItemsID == size) {
        return lItemsID;
    }
    if (numberItemsID > size) {
        poco_warning_f2(*logger, "getAllItemsIdHaveTag: numberItemsID = %d > lItemsID -1 = %d", numberItemsID, size);
        numberItemsID = size;
    }
    int64_t index = Utils::getRandomNumber(size);
    while (result.size() < numberItemsID) {
        result.push_back(lItemsID[index]);
        if (result.size() == numberItemsID)
            return result;
        index++;
        if (index == (lItemsID.size() - 1))
            index = 0;
    }
}

/**
 * lay random itemID co tagID
 * @param tagID
 * @return "-1" neu ko tim duoc itemID nao
 */
string ItemTagDB::getRandomItemOfTag(string tagID) {
    vector<string> items = getAllItemsIdHaveTag(tagID);
    if (items.empty()) {
        return "-1";
    }
    srand((unsigned) time(0));
    int number = rand() % (items.size());
    return items[number];
}

bool ItemTagDB::insertItemTag(string tagID, vector<string> itemsID) {
    if (grassDB.check(tagID) != -1) {
        poco_error_f1(*logger, "insertItemTag Error ItemTag existed: %s.", tagID);
        return false;
    }
    ItemTag itemtag;
    itemtag.tagID = tagID;
    itemtag.itemsID = itemsID;
    string jsonItemTag = convertItemTagToJson(itemtag);
    if (grassDB.set(itemtag.tagID, jsonItemTag)) {
        addQueue(ADD, itemtag.tagID, jsonItemTag);
        return true;
    } else {
        poco_error_f1(*logger, "insertItemTag: Error %s.", grassDB.error().name());
        return false;
    }
}

bool ItemTagDB::insertItemIDToTag(string tagID, string itemID) {
    vector<string> lItemID = getAllItemsIdHaveTag(tagID);
    lItemID.push_back(itemID);
    return editItemTag(tagID, lItemID);
}

bool ItemTagDB::deleteItemTag(string tagID) {
    if (grassDB.check(tagID) != -1) {
        grassDB.remove(tagID);
        addQueue(DELETE, tagID, "");
        return true;
    }
    //cout << "Data doesn't exit!" << endl;
    poco_error(*logger, "deleteItemTag: deleteItemTag data doesn't existed!");
    return false;
}

bool ItemTagDB::deleteItemIDinTag(string tagID, string itemID) {
    if (grassDB.check(tagID) == -1) {
        poco_error_f1(*logger, "deleteItemIDinTag: Don't exits tagID = %s.", tagID);
        return false;
    }
    vector<string> lItemID = getAllItemsIdHaveTag(tagID);
    int index;
    for (int i = 0; i < lItemID.size(); i++)
        if (lItemID[i] == itemID) {
            index = i;
            break;
        }
    lItemID.erase(lItemID.begin() + index - 1);
    return editItemTag(tagID, lItemID);
}

bool ItemTagDB::editItemTag(string tagID, vector<string> itemsID) {
    ItemTag itemtag;
    itemtag.tagID = tagID;
    itemtag.itemsID = itemsID;
    string jsonItemTag = convertItemTagToJson(itemtag);
    try {
        grassDB.replace(tagID, jsonItemTag);
        addQueue(UPDATE, tagID, jsonItemTag);
        return true;
    } catch (char* str) {
        //cout << "Edit error!" << endl;
        poco_error_f1(*logger, "editItemTag: editItemTag %s", str);
        return false;
    }
}

bool ItemTagDB::deleteAllItemTag() {
    try {
        grassDB.clear();
        hashDB.clear();
        return true;
    } catch (char* str) {
        //cout << "error deleteAllTag" << endl;
        poco_information_f1(*logger, "Error deleteAllItemTag %s", str);
        return false;
    }
}

HashDB& ItemTagDB::getHashDB() {
    return hashDB;
}

int64_t ItemTagDB::getItemTagSize(const std::string& tagID) {
    return getAllItemsIdHaveTag(tagID).size();
}

int64_t ItemTagDB::getItemTagDBSize() {
    return grassDB.count() - 1;//bo di itemtag cua tag co ten la notag
}

void ItemTagDB::setSynDB(synchronizeDB* synDBPtr) {
    syndb = synDBPtr;
}

void ItemTagDB::addQueue(string command, string tagID, string jsonString) {
    Poco::Tuple<string, string, string> element(command, tagID, jsonString);
    syndb->listItemTags.push_back(element);
}