/* 
 * File:   ItemDB.cpp
 * Author: toantm
 * 
 * Created on September 24, 2012, 11:02 AM
 */

#include "ItemDB.h"
#include "FeedBackDB.h"

ItemDB::ItemDB() {
    logger = &Logger::get("ItemDB");
}

ItemDB::ItemDB(string path) {
    logger = &Logger::get("ItemDB");
    pathHashDB = path;
}

ItemDB::~ItemDB() {
}

/**
 * Start ItemDB.
 */
void ItemDB::startItemDB() {
    Timestamp sysTime1 = Timestamp().utcTime();
    DBUtils::openHashDB(hashDB, pathHashDB);
    DBUtils::openGrassDB(grassDB);
    cout << "Copying ItemDB" << endl;
    DBUtils::copyDBFromHashDBtoGrassDB(hashDB, grassDB);
    Timestamp sysTime2 = Timestamp().utcTime();
    cout << "Start ItemDB in " << sysTime2 - sysTime1 << " milliseconds." << endl;
    poco_information_f1(*logger, "startItemDB: Start ItemDB in %s milliseconds.", Utils::convertIntToString((sysTime2 - sysTime1)));

    int n = 300;
    cout << "Getting ListTopItemID" << endl;
    // Mac dinh lTopItemID chi co 300 itemID.
    lTopItemID = getListTopItemID(n);
    //Timestamp sysTime3 = Timestamp().utcTime();
    //cout << "Start ItemDB in " << sysTime3 - sysTime2 << " milliseconds." << endl;
    return;
}

/**
 * Stop ItemDB.
 */
void ItemDB::stopItemDB() {
    DBUtils::closeBasicDB(hashDB);
    DBUtils::closeBasicDB(grassDB);
    poco_information(*logger, "Stop ItemDB");
}

/**
 * Ham ma hoa 1 Item thanh chuoi Json, khong ma hoa itemID.
 * @param item
 * @return string
 */
string ItemDB::convertItemToJson(Item& item) {

    Value value;
    value["content"] = item.content;
    value["tagsID"];
    int n = item.tagsID.size();
    for (int i = 0; i < n; i++) {
        value["tagsID"][i] = item.tagsID[i];
    }
    value["likeCounts"] = static_cast<int> (item.likeCounts);
    value["viewCounts"] = static_cast<int> (item.viewCounts);
    value["dislikeCounts"] = static_cast<int> (item.dislikeCounts);
    value["dateAdd"] = item.dateAdd;
    value["dateUpdate"] = item.dateUpdate;
    //StyledWriter writer; //not use Json::Writer because it is a virtual class
    FastWriter writer;
    string jsonString = writer.write(value);
    return jsonString;
}

/**
 * Ham convert mot chuoi string duoc ma hoa bang Json sang Item. ( Khong tra ve itemID cho item).
 * @param jsonString
 * @return Item
 */
Item ItemDB::convertJsonToItem(string jsonString) {

    Item itemReturn;
    Value root;
    Reader reader;
    bool parsedSuccess = reader.parse(jsonString, root, false);
    if (not parsedSuccess) {
        // Report failures and their locations 
        // in the document.
        cout << "Failed to parse JSON" << endl
                << reader.getFormatedErrorMessages()
                << endl;
        poco_error_f1(*logger, "convertJsonToItem: Failed to parse JSON %s", reader.getFormatedErrorMessages());
        return itemReturn;
    }
    Value content = root["content"];
    Value tagsID = root["tagsID"];
    Value viewCounts = root["viewCounts"];
    Value likeCounts = root["likeCounts"];
    Value dislikeCounts = root["dislikeCounts"];
    Value dateAdd = root["dateAdd"];
    Value dateUpdate = root["dateUpdate"];
    //cout << "Tags:" << tags << endl;

    //itemReturn.itemID = itemID.asString();
    itemReturn.content = content.asString();
    int n = tagsID.size();
    for (int i = 0; i < n; i++) {
        string str = tagsID[i].asString();
        itemReturn.tagsID.push_back(str);
    }
    itemReturn.viewCounts = viewCounts.asInt();
    itemReturn.likeCounts = likeCounts.asInt();
    itemReturn.dislikeCounts = dislikeCounts.asInt();
    //itemReturn.likeCounts = likeCounts.asDouble();
    itemReturn.dateAdd = dateAdd.asString();
    itemReturn.dateUpdate = dateUpdate.asString();
    //poco_information(*logger, "convertJsonToItem: Convert from Json to Item successfull");
    return itemReturn;
}

/**
 * Ham get Item co key=itemID trong HashDB.
 * @param hashDB
 * @param itemID
 * @return Item
 */
Item ItemDB::getItemInHashDB(string itemID) {
    string value;
    Item itemReturn;
    try {
        hashDB.get(itemID, &value);
        itemReturn = convertJsonToItem(value);
        itemReturn.itemID = itemID;
        return itemReturn;
    } catch (Exception ex) {
        cerr << "Get error: " << hashDB.error().name() << endl;
        poco_error_f2(*logger, "getItemInHashDB: Can't open ItemID %s in HashDB. Error is %s.", itemID, hashDB.error().name());
        itemReturn.itemID = "-1";
        return itemReturn;
    }
}

/**
 * Ham get Item co key=itemID trong GrassDB.
 * @param grassDB
 * @param itemID
 * @return "Item" Neu khong get duoc thi tra ve item.itemID=-1.
 */
Item ItemDB::getItemInGrassDB(string itemID) {
    //int n_record = grassDB.count();
    string value;
    Item itemReturn;
    try {
        grassDB.get(itemID, &value);
        itemReturn = convertJsonToItem(value);
        itemReturn.itemID = itemID;
        return itemReturn;
    } catch (Exception ex) {
        //cerr << "Get error: " << grassDB.error().name() << endl;
        poco_error_f1(*logger, "getItemInGrassDB: Can't open ItemID %s in GrassDB", itemID);
        itemReturn.itemID = "-1";
        //cerr << "Error on Item : " << itemReturn.itemID << endl;
        return itemReturn;
    }

}

/**
 * Insert Item to HashDB.
 * @param item
 * @return "string" return itemID neu thanh cong, return -1 neu that bai.
 */
string ItemDB::insertItemToHashDB(Item item) {
    string jsonItem = convertItemToJson(item);
    if (hashDB.set(item.itemID, jsonItem)) {
        return item.itemID;
    } else {
        poco_error_f1(*logger, "insertItemToHashDB: Error %s.", hashDB.error().name());
        return "-1";
    }
}

/**
 * Insert Item to GrassDB.
 * @param item
 * @return "string" return itemID neu thanh cong, return -1 neu that bai.
 */
string ItemDB::insertItemToGrassDB(Item item) {
    string jsonItem = convertItemToJson(item);
    if (grassDB.set(item.itemID, jsonItem)) {
        return item.itemID;
    } else {
        poco_error_f1(*logger, "insertItemToGrassDB: Error %s.", grassDB.error().name());
        return "-1";
    }
}

/**
 * Random 1 Item.
 * @return Item
 */
Item ItemDB::getRandomItem() {
    Item item;
    int64_t lastID = Utils::convertStringToInt(DBUtils::getLastID(grassDB));
    string itemID;
    int i = 0;
    do {
        itemID = Utils::convertIntToString(Utils::getRandomNumber(lastID));
        item = getItemFromItemID(itemID);
        i++;
        if (i > 10) {
            poco_error(*logger, "getRandomItem: Random failed because more than 10 times.");
            break;
        }
    } while (item.itemID == "-1");
    return item;
}

/**
 * Get random item thuoc tagID.
 * @param tagID
 * @return "Item" Neu khong get duoc thi tra ve item.itemID = -1.
 */
Item ItemDB::getRandomItemHaveTag(string tagID, ItemTagDB& itemTagDB) {
    string itemID = itemTagDB.getRandomItemOfTag(tagID);
    return getItemFromItemID(itemID);
}

/**
 * Get random number item trong ItemDB, neu number lon hon so record thi se set number=so record
 * @param number
 * @return vector<Item>
 */
vector<Item> ItemDB::getAllItems(int64_t number) {
    vector<Item> result;
    if (number == 0 || number<-1)
        return result;

    int64_t size = grassDB.count() - 1;
    if (number > size || number == -1)
        number = size;

    if (number == size) {
        DB::Cursor* cur = grassDB.cursor();
        cur->jump();
        string itemID, content;
        while (cur->get(&itemID, &content, true)) {
            if (itemID == LASTID)
                continue;
            Item item = convertJsonToItem(content);
            item.itemID = itemID;
            result.push_back(item);
        }
    } else {
        int64_t index = Utils::getRandomNumber(size);
        while (grassDB.check(Utils::convertIntToString(index)) == -1)
            index = Utils::getRandomNumber(size);
        string itemID, content;
        string keyStart = Utils::convertIntToString(index);
        DB::Cursor* cur = grassDB.cursor();
        cur->jump(keyStart);
        int64_t i = 0;
        while (i < number) {
            if (!cur->get(&itemID, &content, true))
                cur->jump();
            if (itemID == LASTID)
                continue;
            Item item = convertJsonToItem(content);
            item.itemID = itemID;
            result.push_back(item);
            i++;
        }
    }
    //poco_information_f1(*logger, "getAllItems: Get %d items.", number);
    return result;
}

/**
 * Lay tat ca Item thuoc tagID
 * @param tagID
 * @return vector<Item>
 */
vector<Item> ItemDB::getAllItemshaveTag(string tagID, ItemTagDB& itemTagDB) {
    vector<Item> listItem;
    vector<string> liststring;
    liststring = itemTagDB.getAllItemsIdHaveTag(tagID);
    int64_t n = liststring.size();
    for (int i = 0; i < n; i++) {
        Item item = getItemInGrassDB(liststring[i]);
        listItem.push_back(item);
    }
    return listItem;
}

/**
 * Tang Viewcount cho Item.
 * @param itemID
 * @return bool
 */
bool ItemDB::increaseViewCountItem(string itemID) {
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "increaseViewCountItem: Don't exits item %s in grassDB", itemID);
        return false;
    }
    Item item = getItemInGrassDB(itemID);
    item.viewCounts++;
    string content = convertItemToJson(item);
    addQueue(UPDATE, itemID, content);
    if (grassDB.replace(itemID, content) == false) {
        poco_error_f1(*logger, "increaseViewCountItem: Replace error in GrassDB %s", grassDB.error().name());
        cout << "error setting";
    }
    return true;
}

bool ItemDB::increaseLikeCountItem(string itemID) {
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "increaseViewCountItem: Don't exits item %s in grassDB", itemID);
        return false;
    }
    Item item = getItemFromItemID(itemID);
    item.likeCounts++;
    string content = convertItemToJson(item);
    addQueue(UPDATE, itemID, content);
    if (grassDB.replace(itemID, content) == false) {
        poco_error_f1(*logger, "increaseViewCountItem: Replace error in GrassDB %s", grassDB.error().name());
        cout << "error setting";
    }
    lTopItemIDQueue.push_back(item.itemID);
    if (lTopItemIDQueue.size() > 1000) {
        updateListTop();
    }
    return true;
}

/**
 * Tang dislikeCounts
 * @param item
 * @return bool
 */
bool ItemDB::increaseDislikeCountItem(string itemID) {
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "increaseDislikeCountItem: Don't exits item %s in grassDB", itemID);
        return false;
    }
    Item item = getItemFromItemID(itemID);
    item.dislikeCounts++;
    string content = convertItemToJson(item);
    addQueue(UPDATE, itemID, content);
    if (grassDB.replace(itemID, content) == false) {
        poco_error_f1(*logger, "increaseViewCountItem: Replace error in GrassDB %s", grassDB.error().name());
        cout << "error setting";
    }
    return true;
}

/**
 * Them Item co value=content va thuoc nhung tag co tagID nam trong tagIDs. 
 * @param content
 * @param tagIDs
 * @return "string" Tra ve itemID neu thanh cong, tra ve -1 neu that bai, 
 * tra ve -2 neu content rong, tra ve -3 tagsID rong, 
 */
string ItemDB::insertItem(string content, vector<string> tagsID, ItemTagDB& itemTagDB) {
    if (content.empty()) {
        poco_error(*logger, "insertItem: Content is empty.");
        return "-2";
    }
    if (tagsID.empty()) {
        poco_error(*logger, "insertItem: List TagsID is Empty.");
        return "-3";
    }
    string temp;
    int lastID = Utils::convertStringToInt(DBUtils::getLastID(grassDB));
    lastID++;
    Item item;
    item.itemID = Utils::convertIntToString(lastID);
    item.content = content;
    int sizeTagsID = tagsID.size();
    for (int i = 0; i < sizeTagsID; i++) {
        temp = tagsID[i];
        item.tagsID.push_back(temp);
    }
    item.viewCounts = 0;
    item.likeCounts = 0;
    item.dislikeCounts = 0;
    item.dateAdd = Utils::getTimeNow();
    item.dateUpdate = "0";
    //string result = insertItemToGrassDB(item);
    string jsonStr = convertItemToJson(item);
    try {
        grassDB.set(item.itemID, jsonStr);
        DBUtils::setLastID(grassDB, item.itemID);
        sizeTagsID = item.tagsID.size();
        for (int i = 0; i < sizeTagsID; i++)
            itemTagDB.insertItemIDToTag(item.tagsID[i], item.itemID);
        addQueue(ADD, item.itemID, jsonStr);
        addQueue(ADD, LASTID, item.itemID);
        return item.itemID;
    } catch (Exception ex) {
        cout << "insertItem: Error to add itemID=" << item.itemID << endl;
        poco_error_f1(*logger, "insertItem: Error to add itemID=%s", item.itemID);
        return "-1";
    }
}

/**
 * Xoa 1 item.
 * @param itemID
 * @return bool
 */
bool ItemDB::deleteItem(string itemID, ItemTagDB& itemTagDB) {
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "deleteItem: Don't exits ItemID %s", itemID);
        return false;
    }
    Item item = getItemFromItemID(itemID);
    if (!grassDB.remove(itemID)) {
        poco_error_f1(*logger, "deleteItem: Error in GrassDB %s", grassDB.error().name());
        return false;
    }
    int size = item.tagsID.size();
    for (int i = 0; i < size; i++)
        itemTagDB.deleteItemIDinTag(item.tagsID[i], itemID);
    addQueue(DELETE, item.itemID, "");
    return true;
}

/**
 * Xoa tat ca cac item co itemID thuoc list itemIDs.
 * @param itemIDs
 * @return bool
 */
bool ItemDB::deleteAllItem(vector<string> itemIDs, ItemTagDB& itemTagDB) {
    int size = itemIDs.size();
    bool result = true;
    for (int i = 0; i < size; i++) {
        if (!deleteItem(itemIDs[i], itemTagDB)) {
            poco_error_f1(*logger, "deleteAllItem: Error to delete ItemID = %s", itemIDs[i]);
            result = false;
        }
    }
    return result;
}

/**
 * Edit a Item.
 * @param itemID
 * @param newItemValue
 * @param newTagIDs
 * @return bool
 */
bool ItemDB::editItem(string itemID, string newItemValue, vector<string> newTagIDs, ItemTagDB& itemTagDB) {
    if (newItemValue.empty()) {
        poco_error(*logger, "editItem: newItemValue is empty");
        return false;
    }
    if (newTagIDs.empty()) {
        poco_error(*logger, "editItem: listTag is empty");
        return false;
    }
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "editItem: Don't exits Item has itemID= %s in DB", itemID);
        return false;
    }
    Item item = getItemInGrassDB(itemID);
    //FastMutex::ScopedLock lock(mutex);
    item.content = newItemValue;
    // Xóa trong ItemTagDB
    for (int i = 0; i < item.tagsID.size(); i++)
        itemTagDB.deleteItemIDinTag(item.tagsID[i], itemID);
    item.tagsID.clear();
    string temp;
    int size = newTagIDs.size();
    for (int i = 0; i < size; i++) {
        temp = newTagIDs[i];
        item.tagsID.push_back(temp);
        //Edit trong ItemTagDB.
        itemTagDB.insertItemIDToTag(temp, item.itemID);
    }
    item.dateUpdate = Utils::getTimeNow();
    string json = convertItemToJson(item);
    if (!grassDB.replace(item.itemID, json)) {
        poco_error(*logger, "editItem: Error to update in GrassDB");
        return false;
    }
    addQueue(UPDATE, item.itemID, json);
    return true;
}

/**
 * Lấy List Item theo kiểu phân trang, nếu tagID==-1 thì sẽ lấy trong ItemDB,
 * tagID>1 thì sẽ lấy Item thuộc tagID đó.
 * @param pageNumber
 * @param itemNumber
 * @param tagID
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemsPage(int64_t pageNumber, int32_t numberItems, string tagID,
        ItemTagDB& itemTagDB) {

    vector<Item> lItem;

    if (pageNumber < 1) {
        poco_error(*logger, "getItemsPage: pageNumber < 0");
        return lItem;
    }
    if (numberItems < 1) {
        poco_error(*logger, "getItemsPage: numberItems < 0");
        return lItem;
    }
    Item item;
    if (tagID == "-1") {
        int64_t size = grassDB.count() - 1;
        int totalPageNumber;
        if (size % numberItems == 0)
            totalPageNumber = size / numberItems;
        else
            totalPageNumber = size / numberItems + 1;
        if (pageNumber > totalPageNumber) {
            poco_warning_f2(*logger, "getItemsPage: pageNumber = %d > totalPageNumber = %d",
                    pageNumber, totalPageNumber);
            pageNumber = totalPageNumber;
        }
        int64_t last = pageNumber * numberItems;
        int64_t first = (pageNumber - 1) * numberItems - 1;
        int64_t i = 0;
        string key, value;
        DB::Cursor* cur = grassDB.cursor();
        cur->jump();
        while (i < last) {
            cur->get(&key, &value, true);
            if (i > first) {
                if (key == LASTID)
                    continue;
                item = convertJsonToItem(value);
                item.itemID = key;
                lItem.push_back(item);
            }
            i++;
        }
        delete cur;
    } else {
        vector<string> lItemID = itemTagDB.getAllItemsIdHaveTag(tagID);
        int64_t sizeLItemID = lItemID.size();
        int totalPageNumber;
        if (sizeLItemID % numberItems == 0)
            totalPageNumber = sizeLItemID / numberItems;
        else
            totalPageNumber = sizeLItemID / numberItems + 1;
        if (pageNumber > totalPageNumber) {
            poco_warning_f2(*logger, "getItemsPage: pageNumber = %d > totalPageNumber = %d",
                    pageNumber, totalPageNumber);
            pageNumber = totalPageNumber;
        }
        int64_t last = pageNumber * numberItems;
        int64_t first = (pageNumber - 1) * numberItems - 1;
        int64_t i = 0;
        if (!lItemID.empty()) {
            for (i = first + 1; i < last; i++) {
                if (i == sizeLItemID)
                    return lItem;
                lItem.push_back(getItemFromItemID(lItemID[i]));
            }
        }
    }
    return lItem;
}

/**
 * Lấy Item có item.itemID=itemID.
 * @param itemID
 * @return "Item" tra ve Item co Item.itemID=-1 neu khong lay duoc.
 */
Item ItemDB::getItemFromItemID(string itemID) {
    // Không tăng viewcount, để cho front-end tăng viewcount.
    //increaseViewCountItem(itemID);
    Item item;
    item.itemID = "-1";
    if (grassDB.check(itemID) == -1) {
        poco_error_f1(*logger, "getItemFromItemID: Don't exits itemID = %s in GrassDB", itemID);
        return item;
    }
    item = getItemInGrassDB(itemID);
    if (item.itemID == "-1") {
        poco_error_f1(*logger, "getItemFromItemID: Can't get itemID = %s in GrassDB", item.itemID);
        return item;
    }
    return item;
}

/**
 * Lấy ListItem có itemID thuộc list itemIDs.
 * @param itemIDs
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemsFromListItemID(vector<string> itemIDs) {
    vector<Item> lItem;
    int64_t size = itemIDs.size();
    if (size == 0) {
        poco_error(*logger, "getItemsFromListItemID: List ItemIDs is empty.");
        return lItem;
    }
    for (int i = 0; i < size; i++) {
        lItem.push_back(getItemFromItemID(itemIDs[i]));
    }
    return lItem;
}

/**
 * Lay nhung ItemID co luot viewscount lon nhat.
 * @param number
 * @return vector<string>
 */
vector<string> ItemDB::getListTopItemID(int64_t number) {
    int n = grassDB.count();
    vector < string> itemsReturn;
    vector < pair<int, string > > allItems;
    if (n == 0) {
        return itemsReturn;
    }
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {
        try {
            if (ckey != LASTID) {
                Item item = convertJsonToItem(cvalue);
                allItems.push_back(std::make_pair(item.likeCounts, ckey));
            }
        } catch (char* str) {
            //cout << "error make pair:" << str << endl;
        }
    }
    sort(allItems.begin(), allItems.end(),
            boost::bind(&std::pair<int, string>::first, _1) >
            boost::bind(&std::pair<int, string>::first, _2)
            );
    int64_t temp = allItems.size();
    if (number > temp) {
        number = temp;
    }
    for (int64_t i = 0; i < number; i++) {
        itemsReturn.push_back(allItems[i].second);
    }
    return itemsReturn;
}

/**
 * Lay nhung ItemID co luot viewCounts lon nhat theo tung tag.
 * @param number
 * @param tagID
 * @param itemTagDB
 * @return vector<string>
 */
vector<string> ItemDB::getListTopItemID(int64_t number, string tagID, ItemTagDB& itemTagDB) {
    vector<string> allItemHaveTag = itemTagDB.getAllItemsIdHaveTag(tagID);
    int n = allItemHaveTag.size();
    vector < string> itemsReturn;
    vector < pair<int, string > > allItems;
    if (n <= 0) {
        return itemsReturn;
    }
    for (int i = 0; i < n; i++) {
        string cvalue;
        try {
            grassDB.get(allItemHaveTag[i], &cvalue);
            Item item = convertJsonToItem(cvalue);
            allItems.push_back(std::make_pair(item.likeCounts, allItemHaveTag[i]));
        } catch (char* str) {
            cout << "error make pair:" << str << endl;
        }
    }
    sort(allItems.begin(), allItems.end(),
            boost::bind(&std::pair<int, string>::first, _1) >
            boost::bind(&std::pair<int, string>::first, _2)
            );
    int64_t temp = allItems.size();
    if (number > temp) {
        number = temp;
    }
    for (int64_t i = 0; i < number; i++) {
        itemsReturn.push_back(allItems[i].second);
    }
    return itemsReturn;
}

vector<Item> ItemDB::getListTopItem(int64_t number) {
    vector<Item> result;
    if (number < 1) {
        poco_error_f1(*logger, "getListTopItem: number = %d < 0.", number);
        return result;
    }
    if (lTopItemID.empty()) {
        poco_error(*logger, "getListTopItem: lTopItemID is empty.");
        return result;
    }
    int64_t sizeLTop = lTopItemID.size();
    if (number > sizeLTop) {
        poco_warning_f2(*logger, "getListTopItem: number = %d > lTopItemID.size() = %d", number, sizeLTop);
        number = sizeLTop;
    }
    for (int i = 0; i < number; i++)
        result.push_back(getItemFromItemID(lTopItemID[i]));
    return result;
}

vector<Item> ItemDB::getListTopItem(int64_t number, string tagID, ItemTagDB& itemTagDB) {
    vector<Item> result;
    if (number < 1) {
        poco_error_f1(*logger, "getListTopItem: number = %d < 0.", number);
        return result;
    }
    return getItemsFromListItemID(getListTopItemID(number, tagID, itemTagDB));
}

/**
 * Cap nhat lTopItem.
 */
void ItemDB::updateListTop() {
    vector<Item> lSum;
    vector<string> lTopItemIDQueueTemp(lTopItemIDQueue);
    lTopItemIDQueue.clear();
    int n = lTopItemID.size();
    for (int i = 0; i < n; i++)
        lSum.push_back(getItemFromItemID(lTopItemID[i]));
    n = lTopItemIDQueue.size();
    for (int i = 0; i < n; i++)
        if (!checkItemIDinList(lTopItemIDQueueTemp[i], lTopItemID))
            lSum.push_back(getItemFromItemID(lTopItemIDQueueTemp[i]));
    vector < pair<int, string > > allItems;
    n = lSum.size();
    for (int i = 0; i < n; i++) {
        allItems.push_back(std::make_pair(lSum[i].likeCounts, lSum[i].itemID));
    }
    sort(allItems.begin(), allItems.end(),
            boost::bind(&std::pair<int, string>::first, _1) >
            boost::bind(&std::pair<int, string>::first, _2)
            );
    if (lTopItemID.size() < allItems.size())
        n = lTopItemID.size();
    else
        n = allItems.size();
    lTopItemID.clear();
    for (int64_t i = 0; i < n; i++) {
        lTopItemID.push_back(allItems[i].second);
    }
}

/**
 * Check item co thuoc tagID hay khong.
 * @param item
 * @param tagID
 * @return bool
 */
bool ItemDB::checkItemInTag(Item& item, string& tagID) {
    int n = item.tagsID.size();
    for (int i = 0; i < n; i++) {
        if (item.tagsID[i] == tagID)
            return true;
    }
    return false;
}

/**
 * Kiểm tra itemID có thuộc listItemID hay không.
 * @param itemID
 * @param lItemID
 * @return bool
 */
bool ItemDB::checkItemIDinList(string& itemID, vector<string>& lItemID) {
    int n = lItemID.size();
    for (int i = 0; i < n; i++)
        if (itemID == lItemID[i])
            return true;
    return false;
}

/**
 * Lấy listItem mà content có chứa từ khóa keyword.
 * @param keyword
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemKeyword(string keyword) {
    vector<Item> result;
    if (keyword.empty()) {
        poco_error(*logger, "getItemKeyword: keyword is empty.");
        return result;
    }
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {
        if (ckey == LASTID)
            continue;
        if (Utils::findStringInString(cvalue, keyword)) {
            Item item = getItemFromItemID(ckey);
            item.itemID = ckey;
            result.push_back(item);
        }
    }
    delete cur;
    return result;
}

/**
 * Lay list number Item theo keyword.
 * @param keyword
 * @param numberItems
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemKeyword(string keyword, int32_t numberItems) {
    vector<Item> result;
    if (keyword.empty()) {
        poco_error(*logger, "getItemKeyword: keyword is empty.");
        return result;
    }
    int32_t i = 0;
    DB::Cursor* cur = grassDB.cursor();
    cur->jump();
    string ckey, cvalue;
    while (cur->get(&ckey, &cvalue, true)) {
        if (ckey != LASTID && Utils::findStringInString(cvalue, keyword)) {
            i++;
            Item item = convertJsonToItem(cvalue);
            item.itemID = ckey;
            result.push_back(item);
        }
        if (i == numberItems)
            break;
    }
    delete cur;
    return result;
}

/**
 * get item co tagID va keyword
 * @param keyword
 * @param tagID
 * @param itemTagDB
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemKeyword(string keyword, string tagID, ItemTagDB& itemTagDB) {
    vector<Item> result;
    if (keyword.empty()) {
        poco_error(*logger, "getItemKeyword: keyword is empty.");
        return result;
    }
    vector<string> lItemID = itemTagDB.getAllItemsIdHaveTag(tagID);
    int64_t n = lItemID.size();
    for (int i = 0; i < n; i++) {
        Item item = getItemFromItemID(lItemID[i]);
        if (Utils::findStringInString(item.content, keyword))
            result.push_back(item);
    }
    return result;
}

/**
 * Get number item co tagID va keyword
 * @param keyword
 * @param tagID
 * @param itemTagDB
 * @param numberItems
 * @return vector<Item>
 */
vector<Item> ItemDB::getItemKeyword(string keyword, string tagID, ItemTagDB& itemTagDB, int32_t numberItems) {
    vector<Item> result;
    if (keyword.empty()) {
        poco_error(*logger, "getItemKeyword: keyword is empty.");
        return result;
    }
    if (numberItems < 1) {
        poco_error_f1(*logger, "getItemKeyword: numberItem = %d < 1", numberItems);
        return result;
    }
    int32_t index = 0;
    vector<string> lItemID = itemTagDB.getAllItemsIdHaveTag(tagID);
    int64_t n = lItemID.size();
    for (int i = 0; i < n; i++) {
        Item item = getItemFromItemID(lItemID[i]);
        if (Utils::findStringInString(item.content, keyword))
            result.push_back(item);
        index++;
        if (index == numberItems)
            return result;
    }
    return result;
}

/**
 * Lay tat ca FavouriteItem cua userID
 * @param userID
 * @param number
 * @param feedBackDB
 * @return vector<Item>
 */
vector<Item> ItemDB::getListFavouriteItem(string userID, int64_t number, FeedBackDB& feedBackDB) {
    vector<string> lItemID = feedBackDB.getFavouriteItemID(userID, number);
    return getItemsFromListItemID(lItemID);
}

/**
 * Lay tat ca FavouriteItem thuoc tagID cua userID
 * @param userID
 * @param number
 * @param tagID
 * @param feedBackDB
 * @return vector<Item>
 */
vector<Item> ItemDB::getListFavouriteItem(string userID, int64_t number, string tagID, FeedBackDB& feedBackDB) {
    vector<string> lItemID = getFavouriteItemID(userID, number, tagID, feedBackDB);
    return getItemsFromListItemID(lItemID);
}

vector<Item> ItemDB::getAllItemsLike(string userID, int64_t number, FeedBackDB& feedBackDB) {
}

vector<Item> ItemDB::getAllItemsDislike(string userID, int64_t number, FeedBackDB& feedBackDB) {
}

/**
 * Lay tat ca FavouriteItemID thuoc tagID cua userID
 * @param userID
 * @param number
 * @param tagID
 * @param feedBackDB
 * @return vector<string>
 */
vector<string> ItemDB::getFavouriteItemID(string userID, int64_t number, string tagID, FeedBackDB& feedBackDB) {
    vector<string> lItemID = feedBackDB.getFavouriteItemID(userID, -1);
    vector<string> result;
    int64_t index = 0;
    for (int i = 0; i < lItemID.size(); i++) {
        Item item = getItemFromItemID(lItemID[i]);
        if (checkItemInTag(item, tagID)) {
            result.push_back(lItemID[i]);
            index++;
            if (index == number)
                break;
        }
    }
    return result;
}

HashDB& ItemDB::getHashDB() {
    return hashDB;
}

int64_t ItemDB::getSize() {
    return grassDB.count() - 1;
}

void ItemDB::setSynDB(synchronizeDB* synDBPtr) {
    syndb = synDBPtr;
}

void ItemDB::addQueue(string command, string itemID, string jsonString) {
    Poco::Tuple<string, string, string> element(command, itemID, jsonString);
    syndb->listItems.push_back(element);
}