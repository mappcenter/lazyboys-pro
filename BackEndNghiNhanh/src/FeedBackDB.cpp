/* 
 * File:   FeedBackDB.cpp
 * Author: khoint
 * 
 * Created on October 4, 2012, 4:01 PM
 */

#include "FeedBackDB.h"
#include "synchronizeDB.h"

FeedBackDB::FeedBackDB() {
    logger = &Logger::get("FeedBackDB");
}

FeedBackDB::FeedBackDB(string path) {
    logger = &Logger::get("FeedBackDB");
    pathHashDB = path;
}

FeedBackDB::~FeedBackDB() {
}

void FeedBackDB::startFeedBackDB() {
    double sysTime1 = clock();
    DBUtils::openHashDB(hashDB, pathHashDB);
    DBUtils::openGrassDB(grassDB);
    cout << "Copying FeedBackDB" << endl;
    DBUtils::copyDBFromHashDBtoGrassDB(hashDB, grassDB);
    double sysTime2 = clock();
    //cout << "Time to search: " << (double) (sysTime2 - sysTime1) << endl;
    poco_information_f1(*logger, "startFeedBackDB: Start startFeedBackDB in %0.0f milliseconds.", (double) (sysTime2 - sysTime1));
    return;
}

void FeedBackDB::stopFeedBackDB() {
    DBUtils::closeBasicDB(hashDB);
    DBUtils::closeBasicDB(grassDB);
    poco_information(*logger, "Stop ItemTagDB");
    return;
}

string FeedBackDB::convertUserFeedBackToJson(UserFeedBack& userFeedBack) {
    Value value;
    value["itemsLike"];
    value["itemsDislike"];
    int64_t n = userFeedBack.itemsLike.size();
    for (int i = 0; i < n; i++) {
        value["itemsLike"][i] = userFeedBack.itemsLike[i];
    }
    n = userFeedBack.itemsDislike.size();
    for (int i = 0; i < n; i++) {
        value["itemsDislike"][i] = userFeedBack.itemsDislike[i];
    }
    n = userFeedBack.favouriteItems.size();
    for (int i = 0; i < n; i++) {
        value["favouriteItems"][i] = userFeedBack.favouriteItems[i];
    }
    FastWriter writer; //not use Json::Writer because it is a virtual class
    string jsonString = writer.write(value);
    //poco_information_f1(*logger, "convertItemToJson: Convert from Item %s to json successful", item.itemID);
    return jsonString;
}

UserFeedBack FeedBackDB::convertJsonToUserFeedBack(string jsonString) {
    UserFeedBack userFeedBack;
    Value root;
    Reader reader;
    bool parsedSuccess = reader.parse(jsonString, root, false);
    if (not parsedSuccess) {
        cout << "Failed to parse JSON" << endl
                << reader.getFormatedErrorMessages()
                << endl;
        poco_error_f1(*logger, "convertJsonToUserFeedBack: Failed to parse JSON %s", reader.getFormatedErrorMessages());
        return userFeedBack;
    }
    // Let's extract the array contained in the root object
    Value itemsLike = root["itemsLike"];
    Value itemsDislike = root["itemsDislike"];
    Value favouriteItems = root["favouriteItems"];
    int64_t n = itemsLike.size();
    for (int i = 0; i < n; i++) {
        string str = itemsLike[i].asString();
        userFeedBack.itemsLike.push_back(str);
    }
    n = itemsDislike.size();
    for (int i = 0; i < n; i++) {
        string str = itemsDislike[i].asString();
        userFeedBack.itemsDislike.push_back(str);
    }
    n = favouriteItems.size();
    for (int i = 0; i < n; i++) {
        string str = favouriteItems[i].asString();
        userFeedBack.favouriteItems.push_back(str);
    }
    //poco_information(*logger, "convertJsonToItem: Convert from Json to Item successfull");
    return userFeedBack;
}

UserFeedBack FeedBackDB::getUserFeedBack(string userID) {
    string value;
    UserFeedBack userFeedBack;
    if (grassDB.get(userID, &value)) {
        cout << "Read " << userID << ": " << value << endl;
    } else {
        cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getUserFeedBack: Can't open userID %s in grassDB", userID);
        userFeedBack.userID = "-1";
        return userFeedBack;
    }
    userFeedBack = convertJsonToUserFeedBack(value);
    userFeedBack.userID = userID;
    //poco_information_f1(*logger, "getItemInHashDB: Get ItemID = %s successful", itemReturn.itemID);
    return userFeedBack;
}
//lay tat ca Item da like cua userID

vector<string> FeedBackDB::getAllItemsIDLike(string userID) {
    vector<string> itemsLike;
    string value;
    UserFeedBack userFeedBack;
    if (grassDB.get(userID, &value)) {
        userFeedBack = convertJsonToUserFeedBack(value);
        itemsLike = userFeedBack.itemsLike;
        poco_information_f1(*logger, "getAllItemsLike: userID %s in grassDB", userID);
    } else {
        cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getAllItemsLike: Can't open userID %s in grassDB", userID);
    }
    return itemsLike;
}

bool FeedBackDB::insertLikedItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack;
        if (grassDB.check(userID) == -1) {
            userFeedBack.userID = userID;
        } else
            userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.itemsLike.begin(), userFeedBack.itemsLike.end(), itemID);
        if (it != userFeedBack.itemsLike.end()) {
            return false;
            ;
        } else {
            userFeedBack.itemsLike.push_back(itemID);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
                ;
            }
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "insertLikedItem: Can't insert itemID %s into userID %s", itemID, userID);
        return false;
    }
}

bool FeedBackDB::deleteLikedItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.itemsLike.begin(), userFeedBack.itemsLike.end(), itemID);
        if (it != userFeedBack.itemsLike.end()) {
            userFeedBack.itemsLike.erase(it);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "deleteLikedItem: Can't delete itemID %s in userID %s", itemID, userID);
        return false;
    }
}

vector<string> FeedBackDB::getAllItemsIDDislike(string userID) {
    vector<string> itemsDislike;
    string value;
    UserFeedBack userFeedBack;
    if (grassDB.get(userID, &value)) {
        userFeedBack = convertJsonToUserFeedBack(value);
        itemsDislike = userFeedBack.itemsDislike;
        poco_information_f1(*logger, "getAllItemsDislike: userID %s in grassDB", userID);
    } else {
        cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getAllItemsDislike: Can't open userID %s in grassDB", userID);
    }
    return itemsDislike;
}

bool FeedBackDB::insertDislikedItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack;
        if (grassDB.check(userID) == -1) {
            userFeedBack.userID = userID;
        } else
            userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.itemsDislike.begin(), userFeedBack.itemsDislike.end(), itemID);
        if (it != userFeedBack.itemsDislike.end()) {
            return false;
        } else {
            userFeedBack.itemsDislike.push_back(itemID);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
            }
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "insertDislikedItem: Can't insert itemID %s into userID %s", itemID, userID);
        return false;
    }
}

bool FeedBackDB::deleteDislikedItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.itemsDislike.begin(), userFeedBack.itemsDislike.end(), itemID);
        if (it != userFeedBack.itemsDislike.end()) {
            userFeedBack.itemsDislike.erase(it);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "deleteDislikedItem: Can't delete itemID %s in userID %s", itemID, userID);
        return false;
    }
}

bool FeedBackDB::insertFeedBack(string userID, vector<string>& itemsLike, vector<string>& itemsDislike, vector<string>& favouriteItems) {
    if (grassDB.check(userID) != -1) {
        poco_error_f1(*logger, "insertFeedBack: userID %s existed in grassDB", userID);
        return false;
    }
    UserFeedBack userFeedBack;
    userFeedBack.userID = userID;
    userFeedBack.itemsLike = itemsLike;
    userFeedBack.itemsDislike = itemsDislike;
    userFeedBack.favouriteItems = favouriteItems;
    string jsonString = convertUserFeedBackToJson(userFeedBack);
    if (grassDB.set(userFeedBack.userID, jsonString)) {
        addQueue(ADD, userFeedBack.userID, jsonString);
        return true;
    } else {
        poco_error_f1(*logger, "insertFeedBack: Error %s.", grassDB.error().name());
        return false;
    }
}

bool FeedBackDB::deleteFeedBack(string userID) {
    if (grassDB.check(userID) == -1) {
        poco_error_f1(*logger, "deleteFeedBack: userID %s doesnot existed in grassDB", userID);
        return false;
    }
    try {
        grassDB.remove(userID);
        addQueue(DELETE, userID, "");
        poco_information_f1(*logger, "deleteFeedBack: deleted userID %s in grassDB", userID);
        return true;
    } catch (...) {
        poco_error_f1(*logger, "deleteFeedBack: cannot remove userID %s in grassDB", userID);
        return false;
    }
}

bool FeedBackDB::editFeedBack(string userID, vector<string>& itemsLike, vector<string>& itemsDislike, vector<string>& favouriteItems) {
    if (grassDB.check(userID) == -1) {
        poco_error_f1(*logger, "editFeedBack: userID %s doesnot existed in grassDB", userID);
        return false;
    }
    UserFeedBack userFeedBack;
    userFeedBack.userID = userID;
    userFeedBack.itemsLike = itemsLike;
    userFeedBack.itemsDislike = itemsDislike;
    userFeedBack.favouriteItems = favouriteItems;
    string jsonString = convertUserFeedBackToJson(userFeedBack);
    try {
        grassDB.replace(userFeedBack.userID, jsonString);
        addQueue(UPDATE, userFeedBack.userID, jsonString);
        return true;
    } catch (...) {
        poco_error_f1(*logger, "editFeedBack: Error %s.", grassDB.error().name());
        return false;
    }
}

bool FeedBackDB::updateFeedBack(UserFeedBack& userFeedBack) {
    if (grassDB.check(userFeedBack.userID) == -1) {
        poco_error_f1(*logger, "editFeedBack: userID %s doesnot existed in grassDB", userFeedBack.userID);
        return false;
    }
    string jsonString = convertUserFeedBackToJson(userFeedBack);
    try {
        grassDB.replace(userFeedBack.userID, jsonString);
        addQueue(UPDATE, userFeedBack.userID, jsonString);
        return true;
    } catch (...) {
        poco_error_f1(*logger, "editFeedBack: Error %s.", grassDB.error().name());
        return false;
    }
}

/**
 * Delete All FeedBackDB
 * @return 
 */
bool FeedBackDB::deleteAllFeedBack() {
    try {
        grassDB.clear();
        hashDB.clear();
        return true;
    } catch (...) {
        poco_error_f1(*logger, "deleteAllFeedBack: Error %s.", grassDB.error().name());
        return false;
    }
}

/**
 * Lay number Favourite Item cua userID
 * @param userID
 * @param number
 * @return vector<string>
 */
vector<string> FeedBackDB::getFavouriteItemID(string userID, int64_t number) {
    vector<string> strReturn;
    if (grassDB.check(userID) == -1) {
        poco_error_f1(*logger, "getFavouriteItemID: userID %s doesnot existed in grassDB", userID);
        return strReturn;
    }
    UserFeedBack userFeedBack = getUserFeedBack(userID);
    if (number > userFeedBack.favouriteItems.size())
        number = userFeedBack.favouriteItems.size();
    else if (number == -1)
        number = userFeedBack.favouriteItems.size();
    for (int i = 0; i < number; i++) {
        strReturn.push_back(userFeedBack.favouriteItems[i]);
    }
    return strReturn;
}

bool FeedBackDB::insertFavouriteItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack;
        if (grassDB.check(userID) == -1) {
            userFeedBack.userID = userID;
        } else
            userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.favouriteItems.begin(), userFeedBack.favouriteItems.end(), itemID);
        if (it != userFeedBack.favouriteItems.end()) {
            return false;
        } else {
            userFeedBack.favouriteItems.push_back(itemID);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
            }
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "insertFavouriteItem: Can't insert itemID %s into userID %s", itemID, userID);
        return false;
    }
}

bool FeedBackDB::deleteFavouriteItem(string userID, string itemID) {
    try {
        UserFeedBack userFeedBack = getUserFeedBack(userID);
        vector<string>::iterator it;
        it = std::find(userFeedBack.favouriteItems.begin(), userFeedBack.favouriteItems.end(), itemID);
        if (it != userFeedBack.favouriteItems.end()) {
            userFeedBack.favouriteItems.erase(it);
            if (updateFeedBack(userFeedBack) == false) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    } catch (...) {
        poco_error_f2(*logger, "deleteFavouriteItem: Can't delete itemID %s in userID %s", itemID, userID);
        return false;
    }
}

HashDB& FeedBackDB::getHashDB() {
    return hashDB;
}

int64_t FeedBackDB::getItemsLikeSize(const std::string& userID) {
    return getAllItemsIDLike(userID).size();
}

int64_t FeedBackDB::getItemsDislikeSize(const std::string& userID) {
    return getAllItemsIDDislike(userID).size();
}

int64_t FeedBackDB::getFavouriteItemsSize(const std::string& userID) {
    return getFavouriteItemID(userID, -1).size();
}

void FeedBackDB::setSynDB(synchronizeDB* synDBPtr) {
    syndb = synDBPtr;
}

void FeedBackDB::addQueue(string command, string itemID, string jsonString) {
    Poco::Tuple<string, string, string> element(command, itemID, jsonString);
    syndb->listUserFeedBacks.push_back(element);
}