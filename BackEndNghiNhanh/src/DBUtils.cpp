/*
 * File:   DBUtils.cpp
 * Author: toantm
 *
 * Created on September 20, 2012, 9:19 PM
 */

#include "DBUtils.h"
#include "Utils.h"

DBUtils::DBUtils() {
    logger = &Logger::get("DBUtils");
}

DBUtils::DBUtils(const DBUtils& orig) {
    logger = &Logger::get("DBUtils");
}

DBUtils::~DBUtils() {
}

bool DBUtils::copyDBFromHashDBtoGrassDB(HashDB &hashDB, GrassDB &grassDB) {
    DB::Cursor* cur = hashDB.cursor();
    cur->jump();
    string ckey, cvalue;

    while (cur->get(&ckey, &cvalue, true)) {
        grassDB.set(ckey, cvalue);
    }
    //poco_information(*logger, "Copy complete from HashDB to GrassDB");
    if (cur != NULL)
        cout << "Copy complete." << endl;
    delete cur;
    return true;
}

bool DBUtils::openGrassDB(GrassDB& grassDB) {
    if (!grassDB.open("*", GrassDB::OWRITER | GrassDB::OCREATE)) {
        cerr << "Open grassDB error: " << grassDB.error().name() << endl;
        //poco_error_f1(*logger, "Open grassDB %s error",grassDB.error().name());
        return false;
    }
    //poco_information(*logger, "Open grassDB successful");
    return true;
}

bool DBUtils::openHashDB(HashDB& hashDB, string pathHashDB) {
    if (!hashDB.open(pathHashDB, HashDB::OWRITER | HashDB::OCREATE | HashDB::OAUTOTRAN)) {
        cout << "path : " << pathHashDB << endl;
        cerr << "Open hashDB error: " << hashDB.error().name() << endl;
        //poco_error_f1(*logger, "Open error: %s", hashDB.error().name());
        return false;
    }
    //poco_information_f1(*logger, "Open HashDB %s .", pathHashDB);
    return true;
}

bool DBUtils::closeBasicDB(BasicDB& basicDB) {
    if (!basicDB.close()) {
        cerr << "Close Database error: " << basicDB.error().name() << endl;
        //poco_error_f1(*logger, "Close Database %s error",basicDB.error().name());
        return false;
    }
    //poco_information(*logger, "Close Database successful");
    return true;
}

/**
 * Get lastID in ItemDB
 * @return string
 */
string DBUtils::getLastID(GrassDB& grassDB) {
//    string value;
//    if (grassDB.get(LASTID, &value)) {
//        //poco_information_f1(*logger, "getLastID: Get lastID in GrassDB successful, lastID of ItemDB is : %s", value);
//        return value;
//    } else {
//        cerr << "getLastID: Can't open LastID in Database. GrassDB Error: " << grassDB.error().name() << endl;
//        //poco_error_f1(*logger, "getLastID: Can't open LastID in Database. GrassDB: %s.", grassDB.error().name());
//        return "-1";
//    }
    return "";
}

string DBUtils::initalizeLastID(GrassDB& grassDB) {
    string ckey, cvalue;
    int64_t MAXID = 0;
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    while (cur->get(&ckey, &cvalue, true)) {
        //cout << "key :" << ckey << endl;
        if (MAXID < Utils::convertStringToInt(ckey)) {
            MAXID = Utils::convertStringToInt(ckey);
        }
    }
    string lastID = Utils::convertIntToString(MAXID);
    return lastID;
}

/**
 * Cap nhat itemID cuoi cung da them vao DB.
 * @param itemID
 * @return bool
 */
bool DBUtils::setLastID(GrassDB& grassDB, HashDB& hashDB, string id) {
//    if (!grassDB.set(LASTID, id) ||
//            !hashDB.set(LASTID, id)) {
//        cerr << "setLastID: Error to setLastID. HashDB: " << hashDB.error().name() << "GrassDB: " << grassDB.error().name() << endl;
//        //poco_error_f2(*logger, "setLastID: Error tot setLastID. HashDB: %s. GrassDB: %s", hashDB.error().name(), grassDB.error().name());
//        return false;
//    }
//    //poco_information_f1(*logger, "setLastID: Set lastID successful, lastID of ItemDB is : %s", itemID);
//    return true;
    return true;
}

/**
 * Cap nhat itemID cuoi cung da them vao DB.
 * @param itemID
 * @return bool
 */
bool DBUtils::setLastID(GrassDB& grassDB, string id) {
//    if (!grassDB.replace(LASTID, id)) {
//        cerr << "setLastID: Error to setLastID. GrassDB: " << grassDB.error().name() << endl;
//        //poco_error_f2(*logger, "setLastID: Error tot setLastID. HashDB: %s. GrassDB: %s", hashDB.error().name(), grassDB.error().name());
//        return false;
//    }
    //poco_information_f1(*logger, "setLastID: Set lastID successful, lastID of ItemDB is : %s", itemID);
    return true;
}