/* 
 * File:   ItemDB.h
 * Author: toantm
 *
 * Created on September 24, 2012, 11:02 AM
 */

#ifndef ITEMMANAGER_H
#define	ITEMMANAGER_H
#include <string>
#include <sstream>
#include <stdio.h>
#include <cstdlib>
#include <fstream>
#include <utility>
#include <iostream>
#include <algorithm>
#include <json/reader.h>
#include <json/value.h>
#include <json/writer.h>
#include <string.h>
#include <kchashdb.h>
#include <kccachedb.h>
#include <time.h>
#include "Poco/Logger.h"
#include "Poco/Mutex.h"
#include "Poco/NumberParser.h"
#include "Poco/Timestamp.h"
#include "Poco/RegularExpression.h"
#include "BackendMiddleware.h"
#include "DBUtils.h"
#include "ItemTagDB.h"
#include <boost/bind.hpp>
#include <netdb.h>
#include "Utils.h"
#include "FeedBackDB.h"
#include "synchronizeDB.h"

using Poco::Logger;
using Poco::Timestamp;
using Poco::RegularExpression;
using namespace Poco;
using namespace std;
using namespace kyotocabinet;
using namespace BackendMiddleware;
using namespace Json;

//#define LASTID lastID

class ItemDB {
public:
    ItemDB();
    ItemDB(string path);
    virtual ~ItemDB();
public:
    string convertItemToJson(Item& item);
    Item convertJsonToItem(string jsonString);

    Item getItemInHashDB(string itemID);
    Item getItemInGrassDB(string itemID);
    string insertItemToHashDB(Item item);
    string insertItemToGrassDB(Item item);

    void startItemDB();
    void stopItemDB();

public:
    vector<Item> getAllItems(int64_t number);
    Item getItemFromItemID(string itemID);
    vector<Item> getItemsFromListItemID(vector<string> itemIDs);
    vector<Item> getAllItemshaveTag(string tagID, ItemTagDB& itemTagDB);

    bool increaseViewCountItem(string itemID);
    bool increaseLikeCountItem(string itemID);
    bool increaseDislikeCountItem(string itemID);

    string insertItem(string content, vector<string> tagsID, ItemTagDB& itemTagDB);
    bool deleteItem(string itemID, ItemTagDB& itemTagDB);
    bool deleteAllItem(vector<string> itemIDs, ItemTagDB& itemTagDB);
    bool editItem(string itemID, string newItemValue, vector<string> newTagIDs, ItemTagDB& itemTagDB);

    vector<Item> getItemsPage(int64_t pageNumber, int32_t numberItems, string tagID, ItemTagDB& itemTagDB);
    Item getRandomItem();
    Item getRandomItemHaveTag(string tagID, ItemTagDB& itemTagDB);

    vector<Item> getListTopItem(int64_t number);
    vector<Item> getListTopItem(int64_t number, string tagID, ItemTagDB& itemTagDB);
    vector<Item> getItemKeyword(string keyword);
    vector<Item> getItemKeyword(string keyword, int32_t numberItems);
    vector<Item> getItemKeyword(string keyword, string tagID, ItemTagDB& itemTagDB);
    vector<Item> getItemKeyword(string keyword, string tagID, ItemTagDB& itemTagDB, int32_t numberItems);

    vector<string> getTopItemID(int64_t number);
    vector<string> getTopItemID(int64_t number, string tagID, ItemTagDB& itemTagDB);
    void updateListTop();
    bool checkItemInTag(Item& item, string& tagID);
    bool checkItemIDinList(string& itemID, vector<string>& lItemID);

    vector<Item> getListFavouriteItem(string userID, int64_t number, FeedBackDB& feedBackDB);
    vector<Item> getListFavouriteItem(string userID, int64_t number, string tagID, FeedBackDB& feedBackDB);
    vector<string> getFavouriteItemID(string userID, int64_t number, string tagID, FeedBackDB& feedBackDB);

    vector<Item> getAllItemsLike(string userID, int64_t number, FeedBackDB& feedBackDB);
    vector<Item> getAllItemsDislike(string userID, int64_t number, FeedBackDB& feedBackDB);

    HashDB& getHashDB();
    int64_t getSize();
    void setSynDB(synchronizeDB* synDBPtr);
    void addQueue(string command, string itemID, string jsonString);
private:
    //DBUtils dbUtil;
    string lastID; //Key co ten lastID trong DB.
    HashDB hashDB;
    GrassDB grassDB;
    synchronizeDB* syndb;
    Logger* logger;
    string pathHashDB;
    FastMutex mutex;
    vector<string> lTopItemID;
    vector<string> lTopItemIDQueue;
    int timeToUpdateT; // cho timeToUpdateTF=0; khi nao timeToUpdateTF=1000 thi 
    //se cap nhat lTopItemID
    //int timeToUpdateF; // cho timeToUpdateTF=0; khi nao timeToUpdateTF=1000 thi 
    //se cap nhat lFavouriteItemID
};

#endif	/* ITEMMANAGER_H */

