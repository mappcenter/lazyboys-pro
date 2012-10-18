/* 
 * File:   DatabaseManager.h
 * Author: toantm
 *
 * Created on September 20, 2012, 9:19 PM
 */

#ifndef DATABASEMANAGER_H
#define	DATABASEMANAGER_H
#include <kchashdb.h>
#include <kccachedb.h>
#include <stdio.h>
#include "Poco/Logger.h"
using namespace kyotocabinet;
using namespace std;
using Poco::Logger;

//Trong mỗi file database sẽ lưu thêm 1 record có key="lastID" - ID cuối cùng được thêm vào file, 
//mỗi khi insert new record thì sẽ lấy newID=lastID+1
const string LASTID = "lastID";

class DBUtils {
public:
    DBUtils();
    DBUtils(const DBUtils& orig);
    virtual ~DBUtils();
public:
    static bool openGrassDB(GrassDB& grassDB);
    static bool openHashDB(HashDB& hashDB, string pathHashDB);
    static bool closeBasicDB(BasicDB& basicDB);
    static bool copyDBFromHashDBtoGrassDB(HashDB &hashDB, GrassDB &grassDB);
    static string getLastID(GrassDB& grassDB);
    static bool setLastID(GrassDB& grassDB, HashDB& hashDB, string id);
    static bool setLastID(GrassDB& grassDB, string id);
public:
    Logger* logger;
};

#endif	/* DATABASEMANAGER_H */

