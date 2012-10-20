/* 
 * File:   UserDB.h
 * Author: khoint
 *
 * Created on October 4, 2012, 8:22 AM
 */

#ifndef USERDB_H
#define	USERDB_H
#include <stdio.h>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <json/reader.h>
#include <json/value.h>
#include <json/writer.h>
#include <string.h>
#include <time.h>
#include "Utils.h"
#include "Poco/Logger.h"
#include "BackendMiddleware.h"
#include "DBUtils.h"
#include "synchronizeDB.h"
#include "FeedBackDB.h"

using Poco::Logger;
using namespace Poco;
using namespace std;
using namespace kyotocabinet;
using namespace BackendMiddleware;
using namespace Json;

class UserDB {
public:
    UserDB();
    UserDB(string path);
    virtual ~UserDB();
    string convertUserToJson(User& user);
    User convertJsonToUser(string jsonString);
    void startUserDB();
    void stopUserDB();
    bool userExisted(string userID);
    User getUser(string userID);
    vector<string> getAllUser();
    bool addUser(string userID, string userToken, int32_t userRole, FeedBackDB& feedBackDB);
    bool addUser(User& user, FeedBackDB& feedBackDB);
    bool deleteUser(string userID, FeedBackDB& feedBackDB);
    bool deleteAllUser(FeedBackDB& feedBackDB);
    bool blockUser(string userID);
    bool unblockUser(string userID);
    bool editUser(string userID, string userToken, int32_t userRole);
    bool updateUser(User& user);
    HashDB& getHashDB();
    int64_t getUserDBSize();
    void setSynDB(synchronizeDB* synDBPtr);
    void addQueue(string command, string userID, string jsonString);

public:
    HashDB hashDB;
    GrassDB grassDB;
private:
    string lastID; //Key co ten lastID trong DB.
    synchronizeDB* syndb;
    Logger* logger;
    string pathHashDB;
};

#endif	/* USERDB_H */

