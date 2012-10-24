/* 
 * File:   TagDB.h
 * Author: khoint
 *
 * Created on September 28, 2012, 8:31 AM
 */

#ifndef TAGDB_H
#define	TAGDB_H
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
#include "ItemTagDB.h"
#include <ctime>
#include "synchronizeDB.h"

using Poco::Logger;
using namespace Poco;
using namespace std;
using namespace kyotocabinet;
using namespace BackendMiddleware;
using namespace Json;

class TagDB {
public:
    TagDB();
    TagDB(string path);
    virtual ~TagDB();
public:

    void startTagDB();
    void stopTagDB();
    string convertTagToJson(Tag& tag);
    Tag convertJsonToTag(string jsonString);
    Tag getTag(string tagID);
    vector<Tag> getTagKeyword(string keyword);
    vector<Tag> getAllTag();
    vector<string> getIDListOfTags(string tagID);
    bool insertTag(string tagID, string tagName, ItemTagDB& itemTagDB);
    bool insertTag(Tag& tag, ItemTagDB& itemTagDB);
    bool insertTag(string tagName, ItemTagDB& itemTagDB);
    bool deleteTag(string tagID, ItemTagDB& itemTagDB);
    bool deleteTag(Tag tag, ItemTagDB& itemTagDB);
    bool editTag(string tagID, string tagName);
    bool setViewCountTag(string tagID);
    bool setViewCountTag(string tagID, int viewCounts);

    bool deleteAllTag(ItemTagDB& itemTagDB);
    bool deleteAllTag(vector<string> tagIDs, ItemTagDB& itemTagDB);
    vector<Tag> getTopTags(int number);
    HashDB& getHashDB();
    int64_t getTagDBSize();
    void setSynDB(synchronizeDB* synDBPtr);
    void addQueue(string command, string tagID, string jsonString);
private:
    string lastID; //Key co ten lastID trong DB.
    HashDB hashDB;
    GrassDB grassDB; //Key co ten lastID trong DB.
    synchronizeDB* syndb;
    Logger* logger;
    string pathHashDB;
};

#endif	/* TAGDB_H */
