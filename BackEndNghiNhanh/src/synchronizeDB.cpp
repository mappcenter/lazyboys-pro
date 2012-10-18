/* 
 * File:   synchronizeDB.cpp
 * Author: khoint
 * 
 * Created on October 9, 2012, 10:09 AM
 */

#include "synchronizeDB.h"

synchronizeDB::synchronizeDB() {
}

synchronizeDB::synchronizeDB(HashDB& itemDB, HashDB& itemtagDB, HashDB& tagDB, HashDB& userDB, HashDB& feedbackDB) {
    this->itemDB = &itemDB;
    this->itemtagDB = &itemtagDB;
    this->tagDB = &tagDB;
    this->userDB = &userDB;
    this->feedbackDB = &feedbackDB;
}

synchronizeDB::synchronizeDB(const synchronizeDB& orig) {
}

synchronizeDB::~synchronizeDB() {
}

bool synchronizeDB::addToItemDB(string& itemID, string& stringJson) {
    if (itemDB->check(itemID) == -1 || itemID == "lastID") {
        itemDB->set(itemID, stringJson);
        cout << "Sucess synchronizeDB addToItemDB itemID:" << itemID << endl;
        return true;
    }
    cout << "Error synchronizeDB addToItemDB itemID:" << itemID << endl;
    return false;
}

bool synchronizeDB::addToItemTagDB(string& tagID, string& stringJson) {
    if (itemtagDB->check(tagID) == -1) {
        itemtagDB->set(tagID, stringJson);
        cout << "Sucess synchronizeDB addToItemTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB addToItemTagDB tagID" << tagID << endl;
    return false;
}

bool synchronizeDB::addToTagDB(string& tagID, string& stringJson) {
    if (tagDB->check(tagID) == -1) {
        tagDB->set(tagID, stringJson);
        cout << "Sucess synchronizeDB addToTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB addToTagDB tagID:" << tagID << endl;
    return false;
}

bool synchronizeDB::addToUserDB(string& userID, string& stringJson) {
    if (userDB->check(userID) == -1) {
        userDB->set(userID, stringJson);
        cout << "Sucess synchronizeDB addToUserDB userID:" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB addToUserDB userID:" << userID << endl;
    return false;
}

bool synchronizeDB::addToFeedBackDB(string& userID, string& stringJson) {
    if (feedbackDB->check(userID) == -1) {
        feedbackDB->set(userID, stringJson);
        cout << "Sucess synchronizeDB addToFeedBackDB userID:" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB addToFeedBackDB userID:" << userID << endl;
    return false;
}

bool synchronizeDB::updateToItemDB(string& itemID, string& stringJson) {
    if (itemDB->check(itemID) != -1) {
        itemDB->replace(itemID, stringJson);
        cout << "Sucess synchronizeDB updateToItemDB itemID:" << itemID << endl;
        return true;
    }
    cout << "Error synchronizeDB updateToItemDB itemID:" << itemID << endl;
    return false;
}

bool synchronizeDB::updateToItemTagDB(string& tagID, string& stringJson) {
    if (itemtagDB->check(tagID) != -1) {
        itemtagDB->replace(tagID, stringJson);
        cout << "Sucess synchronizeDB updateToItemTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB updateToItemTagDB tagID:" << tagID << endl;
    return false;
}

bool synchronizeDB::updateToTagDB(string& tagID, string& stringJson) {
    if (tagDB->check(tagID) != -1) {
        tagDB->replace(tagID, stringJson);
        cout << "Sucess synchronizeDB updateToTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB updateToTagDB tagID:" << tagID << endl;
    return false;
}

bool synchronizeDB::updateToUserDB(string& userID, string& stringJson) {
    if (userDB->check(userID) != -1) {
        userDB->replace(userID, stringJson);
        cout << "Sucess synchronizeDB updateToUserDB userID" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB updateToUserDB userID" << userID << endl;
    return false;
}

bool synchronizeDB::updateToFeedBackDB(string& userID, string& stringJson) {
    if (feedbackDB->check(userID) != -1) {
        feedbackDB->replace(userID, stringJson);
        cout << "Sucess synchronizeDB updateToFeedBackDB userID:" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB updateToFeedBackDB userID:" << userID << endl;
    return false;
}

bool synchronizeDB::deleteFromItemDB(string& itemID) {
    if (itemDB->check(itemID) != -1) {
        itemDB->remove(itemID);
        cout << "Sucess synchronizeDB deleteFromItemDB itemID:" << itemID << endl;
        return true;
    }
    cout << "Error synchronizeDB deleteFromItemDB itemID:" << itemID << endl;
    return false;
}

bool synchronizeDB::deleteFromItemTagDB(string& tagID) {
    if (itemtagDB->check(tagID) != -1) {
        itemtagDB->remove(tagID);
        cout << "Sucess synchronizeDB deleteFromItemTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB deleteFromItemTagDB tagID:" << tagID << endl;
    return false;
}

bool synchronizeDB::deleteFromTagDB(string& tagID) {
    if (tagDB->check(tagID) != -1) {
        tagDB->remove(tagID);
        cout << "Sucess synchronizeDB deleteFromTagDB tagID:" << tagID << endl;
        return true;
    }
    cout << "Error synchronizeDB deleteFromTagDB tagID:" << tagID << endl;
    return false;
}

bool synchronizeDB::deleteFromUserDB(string& userID) {
    if (userDB->check(userID) != -1) {
        userDB->remove(userID);
        cout << "Sucess synchronizeDB deleteFromUserDB userID:" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB deleteFromUserDB userID:" << userID << endl;
    return false;
}

bool synchronizeDB::deleteFromFeedBackDB(string& userID) {
    if (feedbackDB->check(userID) != -1) {
        feedbackDB->remove(userID);
        cout << "Sucess synchronizeDB deleteFromFeedBackDB userID:" << userID << endl;
        return true;
    }
    cout << "Error synchronizeDB deleteFromFeedBackDB userID:" << userID << endl;
    return false;
}

void synchronizeDB::runQueueItemDB() {
    //Poco::Tuple<string, string, string> a("a", "b", "c");
    //listItems.push_back(a);
    while (true) {
        boost::this_thread::sleep(boost::posix_time::milliseconds(10));
        list < Poco::Tuple<string, string, string> >::iterator itr = listItems.begin();
        list < Poco::Tuple<string, string, string> >::iterator itrEnd = listItems.end();
        if (itr == itrEnd) {
            //cout << "\tNo item in QUEUE to save to DB!" << endl;
            continue;
        } else {
            Poco::Tuple<string, string, string> &item = *itr;
            string mod = item.get < 0 > ();
            // cout << "Mod = " << mod << endl;
            if (mod == "add")
                addToItemDB(item.get < 1 > (), item.get < 2 > ());
            if (mod == "update")
                updateToItemDB(item.get < 1 > (), item.get < 2 > ());
            if (mod == "delete")
                deleteFromItemDB(item.get < 1 > ());
            listItems.pop_front();
            //cout << "\tSaved item in QUEUE to DB!" << endl;
        }
    }
}

void synchronizeDB::runQueueItemTagDB() {
    while (true) {
        boost::this_thread::sleep(boost::posix_time::milliseconds(10));
        list < Poco::Tuple<string, string, string> >::iterator itr = listItemTags.begin();
        list < Poco::Tuple<string, string, string> >::iterator itrEnd = listItemTags.end();
        if (itr == itrEnd) {
            //cout << "\tNo itemtag in QUEUE to save to DB!" << endl;
            continue;
        } else {
            Poco::Tuple<string, string, string> &itemtag = *itr;
            string mod = itemtag.get < 0 > ();
            if (mod == "add")
                addToItemTagDB(itemtag.get < 1 > (), itemtag.get < 2 > ());
            if (mod == "update")
                updateToItemTagDB(itemtag.get < 1 > (), itemtag.get < 2 > ());
            if (mod == "delete")
                deleteFromItemTagDB(itemtag.get < 1 > ());
            listItemTags.pop_front();
            //cout << "\tSaved itemtag in QUEUE to DB!" << endl;
        }
    }
}

void synchronizeDB::runQueueTagDB() {
    while (true) {
        boost::this_thread::sleep(boost::posix_time::milliseconds(10));
        list < Poco::Tuple<string, string, string> >::iterator itr = listTags.begin();
        list < Poco::Tuple<string, string, string> >::iterator itrEnd = listTags.end();
        if (itr == itrEnd) {
            //cout << "\tNo tag in QUEUE to save to DB!" << endl;
            continue;
        } else {
            Poco::Tuple<string, string, string> &tag = *itr;
            string mod = tag.get < 0 > ();
            if (mod == "add")
                addToTagDB(tag.get < 1 > (), tag.get < 2 > ());
            if (mod == "update")
                updateToTagDB(tag.get < 1 > (), tag.get < 2 > ());
            if (mod == "delete")
                deleteFromTagDB(tag.get < 1 > ());
            listTags.pop_front();
            //cout << "\tSaved tag in QUEUE to DB!" << endl;
        }
    }
}

void synchronizeDB::runQueueUserDB() {
    while (true) {
        boost::this_thread::sleep(boost::posix_time::milliseconds(10));
        list < Poco::Tuple<string, string, string> >::iterator itr = listUsers.begin();
        list < Poco::Tuple<string, string, string> >::iterator itrEnd = listUsers.end();
        if (itr == itrEnd) {
            //cout << "\tNo user in QUEUE to save to DB!" << endl;
            continue;
        } else {
            Poco::Tuple<string, string, string> &user = *itr;
            string mod = user.get < 0 > ();
            if (mod == "add")
                addToUserDB(user.get < 1 > (), user.get < 2 > ());
            if (mod == "update")
                updateToUserDB(user.get < 1 > (), user.get < 2 > ());
            if (mod == "delete")
                deleteFromUserDB(user.get < 1 > ());
            listUsers.pop_front();
            //cout << "\tSaved user in QUEUE to DB!" << endl;
        }
    }
}

void synchronizeDB::runQueueFeedBackDB() {
    while (true) {
        boost::this_thread::sleep(boost::posix_time::milliseconds(10));
        list < Poco::Tuple<string, string, string> >::iterator itr = listUserFeedBacks.begin();
        list < Poco::Tuple<string, string, string> >::iterator itrEnd = listUserFeedBacks.end();
        if (itr == itrEnd) {
            //cout << "\tNo feedback in QUEUE to save to DB!" << endl;
            continue;
        } else {
            Poco::Tuple<string, string, string> &userfeedback = *itr;
            string mod = userfeedback.get < 0 > ();
            if (mod == "add")
                addToFeedBackDB(userfeedback.get < 1 > (), userfeedback.get < 2 > ());
            if (mod == "update")
                updateToFeedBackDB(userfeedback.get < 1 > (), userfeedback.get < 2 > ());
            if (mod == "delete")
                deleteFromFeedBackDB(userfeedback.get < 1 > ());
            listUserFeedBacks.pop_front();
            //cout << "\tSaved feedback in QUEUE to DB!" << endl;
        }
    }
}