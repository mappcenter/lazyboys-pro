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