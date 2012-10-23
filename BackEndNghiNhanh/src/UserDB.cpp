/* 
 * File:   UserDB.cpp
 * Author: khoint
 * 
 * Created on October 4, 2012, 8:22 AM
 */

#include "UserDB.h"
#include "synchronizeDB.h"

UserDB::UserDB() {
    logger = &Logger::get("UserDB");
    lastID = "lastID";
}

HashDB& UserDB::getHashDB() {
    return hashDB;
}

int64_t UserDB::getUserDBSize() {
    return grassDB.count();
}

UserDB::UserDB(string path) {
    logger = &Logger::get("UserDB");
    pathHashDB = path;
    lastID = "lastID";
}

UserDB::~UserDB() {
}

string UserDB::convertUserToJson(User& user) {
    Json::Value value;
    value["userToken"] = user.userToken;
    value["userRole"] = static_cast<int> (user.userRole);
    //Json::StyledWriter writer;
    FastWriter writer;
    string jsonText = writer.write(value);
    //poco_information_f1(*logger, "convertUserToJson: Convert from User %s to json successful", user.userID);
    return jsonText;
}

User UserDB::convertJsonToUser(string jsonString) {
    User userReturn;
    Json::Value root;
    Json::Reader reader;
    bool parsedSuccess = reader.parse(jsonString, root, false);
    if (not parsedSuccess) {
        // Report failures and their locations in the document.
        cout << "Failed to parse JSON" << endl
                << reader.getFormatedErrorMessages()
                << endl;
        poco_error_f1(*logger, "convertJsonToTag: Failed to parse JSON %s", reader.getFormatedErrorMessages());
        return userReturn;
    }
    //Json::Value tagID = root["tagID"];
    Json::Value userToken = root["userToken"];
    Json::Value userRole = root["userRole"];

    //itemReturn.tagID = tagID.asString();
    userReturn.userToken = userToken.asString();
    userReturn.userRole = userRole.asInt();
    //poco_information(*logger, "convertJsonToUser: Convert from Json to User successfull");
    return userReturn;
}

void UserDB::startUserDB() {
    double sysTime1 = clock();
    DBUtils::openHashDB(hashDB, pathHashDB);
    DBUtils::openGrassDB(grassDB);
    cout << "Copying UserDB" << endl;
    DBUtils::copyDBFromHashDBtoGrassDB(hashDB, grassDB);
    double sysTime2 = clock();
    poco_information_f1(*logger, "startUserDB: Start UserDB in %0.0f milliseconds.", (double) (sysTime2 - sysTime1));
    return;
}

void UserDB::stopUserDB() {
    DBUtils::closeBasicDB(hashDB);
    DBUtils::closeBasicDB(grassDB);
    poco_information(*logger, "stopUserDB: Stop UserDB");
    return;
}

bool UserDB::userExisted(string userID) {
    if (grassDB.check(userID) != -1) {
        return true;
    }
    return false;
}

User UserDB::getUser(string userID) {
    if (userExisted(userID)) {
        string value;
        grassDB.get(userID, &value);
        User user = convertJsonToUser(value);
        user.userID = userID;
        return user;
    } else {
        poco_error_f1(*logger, "getUser: Error user doesn't existed key=%s", userID);
    }
}

vector<string> UserDB::getAllUser() {
    vector<string> listReturn;
    if (grassDB.count() == 0) {
        return listReturn;
    }
    DB::Cursor * cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {
        listReturn.push_back(ckey);
    }
    return listReturn;
}

bool UserDB::addUser(string userID, string userToken, int32_t userRole, FeedBackDB& feedBackDB) {
    if (grassDB.check(userID) != -1) {
        return false;
    }
    User user;
    user.userID = userID;
    user.userToken = userToken;
    user.userRole = userRole;
    return addUser(user, feedBackDB);
}

bool UserDB::addUser(User& user, FeedBackDB& feedBackDB) {
    try {
        string jsonString = convertUserToJson(user);
        string ckey = user.userID;
        if (grassDB.check(ckey) != -1) {
            //cout << "Data existed!" << endl;
            poco_error_f1(*logger, "addUser: Error addUser: Data existed key=%s", user.userID);
            return false;
        }
        grassDB.set(ckey, jsonString);
        vector<string> temp;
        feedBackDB.insertFeedBack(ckey, temp, temp, temp);
        addQueue(ADD, user.userID, jsonString);
        return true;
    } catch (char *str) {
        //cout << str << endl;
        poco_error_f1(*logger, "addUser: Error addUser: %s", str);
        return false;
    }
}

bool UserDB::deleteUser(string userID, FeedBackDB& feedBackDB) {
    if (grassDB.check(userID) != -1) {
        grassDB.remove(userID);
        feedBackDB.deleteFeedBack(userID);
        addQueue(DELETE, userID, "");
        return true;
    }
    //cout << "Data doesn't exit!" << endl;
    poco_error_f1(*logger, "deleteUser: Doesn't exist userID = %s in UserDB", userID);
    return false;
}

bool UserDB::deleteAllUser(FeedBackDB& feedBackDB) {
    try {
        grassDB.clear();
        hashDB.clear();
        feedBackDB.deleteAllFeedBack();
        return true;
    } catch (...) {
        poco_error(*logger, "deleteAllUser: Error deleteAllUser");
        return false;
    }
}

bool UserDB::blockUser(string userID) {
    string cvalue;
    if (grassDB.check(userID) != -1) {
        grassDB.get(userID, &cvalue);
        User user = convertJsonToUser(cvalue);
        user.userID = userID;
        user.userRole = -1;
        return editUser(user.userID, user.userToken, user.userRole);
    }
    return false;
}

/**
 * unblock user
 * @param userID
 * @return 
 */
bool UserDB::unblockUser(string userID) {
    string cvalue;
    if (grassDB.check(userID) != -1) {
        grassDB.get(userID, &cvalue);
        User user = convertJsonToUser(cvalue);
        user.userID = userID;
        user.userRole = 0;
        return editUser(user.userID, user.userToken, user.userRole);
    }
    return false;
}

/**
 * chinh sua va cap nhat user
 * @param userID
 * @param userToken =-1/0/1 tuong ung blocked/active/admin
 * @param userRole
 * @return true neu thanh cong, false neu that bai
 */
bool UserDB::editUser(string userID, string userToken, int32_t userRole) {
    User user;
    user.userID = userID;
    user.userToken = userToken;
    user.userRole = userRole;
    string jsonString = convertUserToJson(user);
    try {
        grassDB.replace(userID, jsonString);
        addQueue(UPDATE, userID, jsonString);
        return true;
    } catch (char* str) {
        poco_error_f1(*logger, "editUser: Error editUser %s", str);
        return false;
    }
}

void UserDB::setSynDB(synchronizeDB* synDBPtr) {
    syndb = synDBPtr;
}

void UserDB::addQueue(string command, string userID, string jsonString) {
    Poco::Tuple<string, string, string> element(command, userID, jsonString);
    syndb->listUsers.push_back(element);
}