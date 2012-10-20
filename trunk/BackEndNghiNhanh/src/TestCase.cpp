/* 
 * File:   TestCase.cpp
 * Author: toantm
 * 
 * Created on October 3, 2012, 8:35 AM
 */

#include "TestCase.h"

TestCase::TestCase() {
}

TestCase::TestCase(const TestCase& orig) {
}

TestCase::~TestCase() {
}

/**
 * Ham test Insert Item To Database.
 * @param itemDB
 */
void TestCase::testInsertItem(ItemDB& itemDB) {

//    vector<string> lTagsID(3, "3");
//    string content = " ";
//    //int result = Utils::convertStringToInt(itemDB.insertItem(content, lTagsID));
//    switch (result) {
//        case -1:cout << "Insert failed." << endl;
//            break;
//        case -2:cout << "Insert failed. Content is empty.";
//            break;
//        case -3:cout << "Insert failed. List TagsID is empty.";
//            break;
//        default:
//        {
//            //string lastID = DBUtils::getLastID()
//            string lastID = "1000000000";
//            Item item = itemDB.getItemFromItemID(lastID);
//            cout << "result: " << result << "\nlastID: " << lastID << endl;
//            cout << "contentLastID: " << item.content << endl;
//            cout << "TagsID: " << item.tagsID.size() << endl;
//        }
//    }

}

/**
 * Test Delete Item in Database.
 * @param itemDB
 */
void TestCase::testDeleteItem(ItemDB& itemDB) {
//    string itemID = "1000002";
//    //if (itemDB.deleteItem(itemID)) {
//        cout << "Delete successful" << endl;
//        Item item = itemDB.getItemFromItemID(itemID);
//        cout << "itemID: " << item.itemID << endl;
//    } else {
//        cout << "Delete failed." << endl;
//    }
}

void TestCase::testEditItem(ItemDB& itemDB) {
//    string itemID = "1000001";
//    string newContent = "test tinh yeu";
//    vector<string> tagIDs(2, "2");
//    if (itemDB.editItem(itemID, newContent, tagIDs)) {
//        cout << "Successful." << endl;
//
//    } else {
//        cout << "Failed." << endl;
//
//    }

}

bool TestCase::compareString() {
    //    RegularExpression re1("tình yêu", RegularExpression::RE_CASELESS && RegularExpression::RE_UTF8);
    //    string s;
    //    int n = re1.extract("tình yêu không lừa dối",s);// n == 1, s == "123"
    //    cout<<"n: "<<n<<"\ns: "<<s<<endl;
    string str = ".*";
    string str2 = "tình yêu";
    string key = str + str2 + str;
    RegularExpression re4(key, RegularExpression::RE_CASELESS && RegularExpression::RE_UTF8);
    bool match = re4.match("ggg tình yêu không lừa dối", 0); // true
    cout<<match;
}
