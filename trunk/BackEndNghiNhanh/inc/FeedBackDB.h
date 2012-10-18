/* 
 * File:   FeedBackDB.h
 * Author: khoint
 *
 * Created on October 4, 2012, 4:01 PM
 */

#ifndef FEEDBACKDB_H
#define	FEEDBACKDB_H
#include <stdio.h>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <json/reader.h>
#include <json/value.h>
#include <json/writer.h>
#include <string.h>
#include <time.h>
#include <algorithm>
#include "Poco/Logger.h"
#include "BackendMiddleware.h"
#include "DBUtils.h"
#include "ItemTagDB.h"
#include "synchronizeDB.h"

using Poco::Logger;
using namespace Poco;
using namespace std;
using namespace kyotocabinet;
using namespace BackendMiddleware;
using namespace Json;

class FeedBackDB {
public:
    FeedBackDB();
    FeedBackDB(string path);
    virtual ~FeedBackDB();
    void startFeedBackDB();
    void stopFeedBackDB();
    
    string convertUserFeedBackToJson(UserFeedBack& userFeedBack);
    UserFeedBack convertJsonToUserFeedBack(string jsonString);
    
    UserFeedBack getUserFeedBack(string userID);
    
    vector<string> getAllItemsIDLike(string userID); //lay tat ca ItemID da like cua user
    bool insertLikedItem(string userID, string itemID);
    bool deleteLikedItem(string userID, string itemID);
    
    vector<string> getAllItemsIDDislike(string userID); //lay tat ca ItemID da dislike cua user
    bool insertDislikedItem(string userID, string itemID);
    bool deleteDislikedItem(string userID, string itemID);
    
    bool insertFeedBack(string userID, vector<string>& itemsLike, vector<string>& itemsDislike, vector<string>& favouriteItems);
    bool deleteFeedBack(string userID);
    bool editFeedBack(string userID, vector<string>& itemsLike, vector<string>& itemsDislike, vector<string>& favouriteItems);
    bool updateFeedBack(UserFeedBack& userFeedBack);
    bool deleteAllFeedBack();
    
    vector<string> getFavouriteItemID(string userID, int64_t number);
    bool insertFavouriteItem(string userID, string itemID);
    bool deleteFavouriteItem(string userID, string itemID);
    
    HashDB& getHashDB();
    int64_t getItemsLikeSize(const std::string& userID);
    int64_t getItemsDislikeSize(const std::string& userID);
    int64_t getFavouriteItemsSize(const std::string& userID);
    void setSynDB(synchronizeDB* synDBPtr);
    void addQueue(string command, string itemID, string jsonString); 
private:
    HashDB hashDB;
    GrassDB grassDB;
    synchronizeDB* syndb;
    Logger* logger;
    string pathHashDB;
};

#endif	/* FEEDBACKDB_H */

