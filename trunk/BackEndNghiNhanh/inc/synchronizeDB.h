/* 
 * File:   synchronizeDB.h
 * Author: khoint
 *
 * Created on October 9, 2012, 10:09 AM
 */

#ifndef SYNCHRONIZEDB_H
#define	SYNCHRONIZEDB_H
#include "BackendMiddleware.h"
#include "Poco/Tuple.h"
#include "kchashdb.h"
#include "boost/bind.hpp"
#include <boost/thread.hpp>

using namespace BackendMiddleware;
using namespace std;
using namespace Poco;
using namespace kyotocabinet;
using Poco::Tuple;
using namespace boost;

class synchronizeDB {
public:
    //   typedef Poco::Tuple<string, string, string> MyTuple;
    //   typedef list<MyTuple> LIST;
    synchronizeDB();
    synchronizeDB(HashDB& itemDB, HashDB& itemtagDB, HashDB& tagDB, HashDB& userDB, HashDB& feedbackDB);
    synchronizeDB(const synchronizeDB& orig);
    virtual ~synchronizeDB();

    //    LIST listItems;
    //    //list < MyTuple > listItems;
    //    list < MyTuple > listItemTags;
    //    list < MyTuple > listTags;
    //    list < MyTuple > listUsers;
    //    list < MyTuple > listUserFeedBacks;

    list < Poco::Tuple<string, string, string> > listItems;
    list < Poco::Tuple<string, string, string> > listItemTags;
    list < Poco::Tuple<string, string, string> > listTags;
    list < Poco::Tuple<string, string, string> > listUsers;
    list < Poco::Tuple<string, string, string> > listUserFeedBacks;

    bool addToItemDB(string& itemID, string& stringJson);
    bool addToItemTagDB(string& tagID, string& stringJson);
    bool addToTagDB(string& tagID, string& stringJson);
    bool addToUserDB(string& userID, string& stringJson);
    bool addToFeedBackDB(string& userID, string& stringJson);

    bool updateToItemDB(string& itemID, string& stringJson);
    bool updateToItemTagDB(string& tagID, string& stringJson);
    bool updateToTagDB(string& tagID, string& stringJson);
    bool updateToUserDB(string& userID, string& stringJson);
    bool updateToFeedBackDB(string& userID, string& stringJson);

    bool deleteFromItemDB(string& itemID);
    bool deleteFromItemTagDB(string& tagID);
    bool deleteFromTagDB(string& tagID);
    bool deleteFromUserDB(string& userID);
    bool deleteFromFeedBackDB(string& userID);

    void runQueueItemDB();
    void runQueueItemTagDB();
    void runQueueTagDB();
    void runQueueUserDB();
    void runQueueFeedBackDB();

private:
    HashDB *itemDB;
    HashDB *itemtagDB;
    HashDB *tagDB;
    HashDB *userDB;
    HashDB *feedbackDB;
};

#endif	/* SYNCHRONIZEDB_H */

