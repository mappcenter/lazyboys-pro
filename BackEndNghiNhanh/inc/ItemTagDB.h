/* 
 * File:   ItemTagDB.h
 * Author: khoint
 *
 * Created on September 28, 2012, 4:25 PM
 */

#ifndef ITEMTAGDB_H
#define	ITEMTAGDB_H
#include <stdio.h>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <json/reader.h>
#include <json/value.h>
#include <json/writer.h>
#include <string.h>
#include <time.h>
#include "Poco/Logger.h"
#include "BackendMiddleware.h"
#include "DBUtils.h"
#include "Utils.h"
#include "synchronizeDB.h"

using Poco::Logger;
using namespace Poco;
using namespace std;
using namespace kyotocabinet;
using namespace BackendMiddleware;
using namespace Json;

class ItemTagDB {
public:
    ItemTagDB();
    ItemTagDB(string path);
    virtual ~ItemTagDB();
    void startItemTagDB();
    void stopItemTagDB();
    string convertItemTagToJson(ItemTag& itemtag);
    ItemTag convertJsonToItemTag(string jsonString);
    ItemTag getItemTag(string tagID);
    vector<string> getAllItemsIdHaveTag(string tagID); //lay tat ca Item thuoc Tag
    vector<string> getAllItemsIdHaveTag(string tagID, int32_t numberItemsID);
    string getRandomItemOfTag(string tagID);
    bool insertItemTag(string tagID, vector<string> itemsID);
    bool insertItemIDToTag(string tagID, string itemID);
    bool deleteItemTag(string tagID);
    bool deleteItemIDinTag(string tagID, string itemID);
    bool editItemTag(string tagID, vector<string> itemsID);
    bool deleteAllItemTag();
    HashDB& getHashDB();
    int64_t getItemTagSize(const std::string& tagID);
    int64_t getItemTagDBSize();
    void setSynDB(synchronizeDB* synDBPtr);
    void addQueue(string command, string tagID, string jsonString);
private:
    HashDB hashDB;
    GrassDB grassDB;
    synchronizeDB* syndb;
    Logger* logger;
    string pathHashDB;
};

#endif	/* ITEMTAGDB_H */
