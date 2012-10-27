#include "BackendMiddlewareHandler.h"

BackendMiddlewareHandler::BackendMiddlewareHandler() : logger(0) {
    logger = &Logger::get("BackendMiddlewareHandler");
}

BackendMiddlewareHandler::BackendMiddlewareHandler(string pathItemsDB, string pathTagsDB,
        string pathItemTagDB, string pathUserDB, string pathFeedBackDB) {
    logger = &Logger::get("BackendMiddlewareHandler");
    itemDB = new ItemDB(pathItemsDB);
    tagDB = new TagDB(pathTagsDB);
    itemTagDB = new ItemTagDB(pathItemTagDB);
    userDB = new UserDB(pathUserDB);
    feedBackDB = new FeedBackDB(pathFeedBackDB);
    tagDB->startTagDB();
    itemTagDB->startItemTagDB();
    userDB->startUserDB();
    feedBackDB->startFeedBackDB();
    itemDB->startItemDB();
    synchronizeThread();
}

BackendMiddlewareHandler::~BackendMiddlewareHandler() {
    itemDB->stopItemDB();
    tagDB->stopTagDB();
    itemTagDB->stopItemTagDB();
    userDB->stopUserDB();
    feedBackDB->stopFeedBackDB();
}

void BackendMiddlewareHandler::synchronizeThread() {
    synDB = new synchronizeDB(itemDB->getHashDB(), itemTagDB->getHashDB(), tagDB->getHashDB(), userDB->getHashDB(), feedBackDB->getHashDB());
    boost::thread(boost::bind(&synchronizeDB::runQueueItemDB, synDB));
    boost::thread(boost::bind(&synchronizeDB::runQueueItemTagDB, synDB));
    boost::thread(boost::bind(&synchronizeDB::runQueueTagDB, synDB));
    boost::thread(boost::bind(&synchronizeDB::runQueueUserDB, synDB));
    boost::thread(boost::bind(&synchronizeDB::runQueueFeedBackDB, synDB));
    itemDB->setSynDB(synDB);
    tagDB->setSynDB(synDB);
    itemTagDB->setSynDB(synDB);
    userDB->setSynDB(synDB);
    feedBackDB->setSynDB(synDB);
}

void BackendMiddlewareHandler::getAllTag(std::vector<Tag> & _return) {
    _return = tagDB->getAllTag();
}

bool BackendMiddlewareHandler::insertTag(const std::string& tagName) {
    return tagDB->insertTag(tagName, *itemTagDB);
}

bool BackendMiddlewareHandler::deleteTag(const std::string& tagID) {
    return tagDB->deleteTag(tagID, *itemTagDB);
}

bool BackendMiddlewareHandler::deleteAllTag(const std::vector<std::string> & tagIDs) {
    return tagDB->deleteAllTag(tagIDs, *itemTagDB);
}

bool BackendMiddlewareHandler::editTag(const std::string& tagID, const std::string& tagName) {
    return tagDB->editTag(tagID, tagName);
}

void BackendMiddlewareHandler::getTag(Tag& _return, const std::string& tagID) {
    _return = tagDB->getTag(tagID);
}

void BackendMiddlewareHandler::setViewCountTag(const std::string& tagID) {
    tagDB->setViewCountTag(tagID);
}

void BackendMiddlewareHandler::getTopTags(std::vector<Tag> & _return, const int64_t number) {
    _return = tagDB->getTopTags(number);
}

void BackendMiddlewareHandler::getTagKeyword(std::vector<Tag> & _return, const std::string& keyWord) {
}

void BackendMiddlewareHandler::getAllItems(std::vector<Item> & _return, const int64_t number) {
    _return = itemDB->getAllItems(number);
}

void BackendMiddlewareHandler::getItemFromItemID(Item& _return, const std::string& itemID) {
    _return = itemDB->getItemFromItemID(itemID);
}

void BackendMiddlewareHandler::getItemsFromListItemID(std::vector<Item> & _return, const std::vector<std::string> & itemIDs) {
    _return = itemDB->getItemsFromListItemID(itemIDs);
}

void BackendMiddlewareHandler::getAllItemshaveTag(std::vector<Item> & _return, const std::string& tagID, const int32_t numberItems) {
    _return = itemDB->getAllItemshaveTag(tagID, *itemTagDB);
}

void BackendMiddlewareHandler::pagingItemsTag(std::vector<Item> & _return, const std::string& tagID, const int32_t pageNumber, const int32_t numberItems) {
    _return = itemDB->getItemsPage(pageNumber, numberItems, tagID, *itemTagDB);
}

void BackendMiddlewareHandler::getAllItemsIDhaveTag(std::vector<std::string> & _return, const std::string& tagID, const int32_t numberItemsID) {
    _return = itemTagDB->getAllItemsIdHaveTag(tagID, numberItemsID);
}

void BackendMiddlewareHandler::getRandomItem(Item& _return) {
    _return = itemDB->getRandomItem();
}

void BackendMiddlewareHandler::getRandomItemhaveTag(Item& _return, const std::string& tagID) {
    _return = itemDB->getRandomItemHaveTag(tagID, *itemTagDB);
}

void BackendMiddlewareHandler::increaseViewCountItem(const std::string& itemID) {
    itemDB->increaseViewCountItem(itemID);
}

void BackendMiddlewareHandler::increaseLikeCountItem(const std::string& itemID) {
    itemDB->increaseLikeCountItem(itemID);
}

void BackendMiddlewareHandler::increaseDislikeCountItem(const std::string& itemID) {
    itemDB->increaseDislikeCountItem(itemID);
}

void BackendMiddlewareHandler::insertItem(std::string& _return, const std::string& content, const std::vector<std::string> & tagIDs) {
    _return = itemDB->insertItem(content, tagIDs, *itemTagDB);
}

bool BackendMiddlewareHandler::deleteItem(const std::string& itemID) {
    return itemDB->deleteItem(itemID, *itemTagDB);
}

bool BackendMiddlewareHandler::deleteAllItem(const std::vector<std::string> & itemIDs) {
    return itemDB->deleteAllItem(itemIDs, *itemTagDB);
}

bool BackendMiddlewareHandler::editItem(const std::string& itemID, const std::string& newItemValue, const std::vector<std::string> & newTagIDs) {
    return itemDB->editItem(itemID, newItemValue, newTagIDs, *itemTagDB);
}

void BackendMiddlewareHandler::getItemsPage(std::vector<Item> & _return, const int64_t pageNumber, const int64_t itemNumber, const std::string& tagID) {
    _return = itemDB->getItemsPage(pageNumber, itemNumber, tagID, *itemTagDB);
}

void BackendMiddlewareHandler::getItemKeyword(std::vector<Item> & _return, const std::string& keyWord) {
    _return = itemDB->getItemKeyword(keyWord);
}

void BackendMiddlewareHandler::getItemKeywordTag(std::vector<Item> & _return, const std::string& keyWord, const std::string& tagID) {
    _return = itemDB->getItemKeyword(keyWord, tagID, *itemTagDB);
}

void BackendMiddlewareHandler::getItemsPageKeyword(std::vector<Item> & _return, const std::string& keyWord, const int64_t pageNumber, const int64_t itemNumber) {
    _return = itemDB->getItemsPageKeyword(keyWord, pageNumber, itemNumber);
}

void BackendMiddlewareHandler::getTopItems(std::vector<Item> & _return, const int64_t number) {
    _return = itemDB->getListTopItem(number);
}

void BackendMiddlewareHandler::getTopItemsofTag(std::vector<Item> & _return, const int64_t number, const std::string& tagID) {
    _return = itemDB->getListTopItem(number, tagID, *itemTagDB);
}

void BackendMiddlewareHandler::getFavouriteItems(std::vector<Item> & _return, const std::string& userID, const int64_t number) {
    _return = itemDB->getListFavouriteItem(userID, number, *feedBackDB);
}

void BackendMiddlewareHandler::getFavouriteItemsofTag(std::vector<Item> & _return, const std::string& userID, const int64_t number, const std::string& tagID) {
    _return = itemDB->getListFavouriteItem(userID, number, tagID, *feedBackDB);
}

bool BackendMiddlewareHandler::insertFavouriteItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->insertFavouriteItem(userID, itemID);
}

bool BackendMiddlewareHandler::deleteFavouriteItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->deleteFavouriteItem(userID, itemID);
}

void BackendMiddlewareHandler::getAllItemsIDLike(std::vector<std::string> & _return, const std::string& userID) {
    _return = feedBackDB->getAllItemsIDLike(userID);
}

void BackendMiddlewareHandler::getAllItemsLike(std::vector<Item> & _return, const std::string& userID, const int32_t number) {
    _return = itemDB->getAllItemsLike(userID, number, *feedBackDB);
}

bool BackendMiddlewareHandler::insertLikedItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->insertLikedItem(userID, itemID);
}

bool BackendMiddlewareHandler::deleteLikedItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->deleteLikedItem(userID, itemID);
}

void BackendMiddlewareHandler::getAllItemsIDDislike(std::vector<std::string> & _return, const std::string& userID) {
    _return = feedBackDB->getAllItemsIDDislike(userID);
}

void BackendMiddlewareHandler::getAllItemsDislike(std::vector<Item> & _return, const std::string& userID, const int32_t number) {
    _return = itemDB->getAllItemsDislike(userID, number, *feedBackDB);
}

bool BackendMiddlewareHandler::insertDislikedItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->insertDislikedItem(userID, itemID);
}

bool BackendMiddlewareHandler::deleteDislikedItem(const std::string& userID, const std::string& itemID) {
    return feedBackDB->deleteDislikedItem(userID, itemID);
}

bool BackendMiddlewareHandler::userExisted(const std::string& userID) {
    return userDB->userExisted(userID);
}

void BackendMiddlewareHandler::getUser(User& _return, const std::string& userID) {
    _return = userDB->getUser(userID);
}

void BackendMiddlewareHandler::getAllUser(std::vector<std::string> & _return) {
    _return = userDB->getAllUser();
}

bool BackendMiddlewareHandler::blockUser(const std::string& userID) {
    return userDB->blockUser(userID);
}

bool BackendMiddlewareHandler::unblockUser(const std::string& userID) {
    return userDB->unblockUser(userID);
}

bool BackendMiddlewareHandler::addUser(const std::string& userID, const std::string& userToken, const int32_t userRole) {
    return userDB->addUser(userID, userToken, userRole, *feedBackDB);
}

bool BackendMiddlewareHandler::deleteUser(const std::string& userID) {
    return userDB->deleteUser(userID, *feedBackDB);
}

bool BackendMiddlewareHandler::editUser(const std::string& userID, const std::string& userToken, const int32_t userRole) {
    return userDB->editUser(userID, userToken, userRole);
}

bool BackendMiddlewareHandler::deleteAllUser() {
    return userDB->deleteAllUser(*feedBackDB);
}

int64_t BackendMiddlewareHandler::itemdbSize() {
    return itemDB->getSize();
}

int64_t BackendMiddlewareHandler::tagdbSize() {
    return tagDB->getTagDBSize();
}

int64_t BackendMiddlewareHandler::itemtagdbSize() {
    return itemTagDB->getItemTagDBSize();
}

int64_t BackendMiddlewareHandler::itemtagSize(const std::string& tagID) {
    return itemTagDB->getItemTagSize(tagID);
}

int64_t BackendMiddlewareHandler::userdbSize() {
    return userDB->getUserDBSize();
}

int64_t BackendMiddlewareHandler::itemsLikeSize(const std::string& userID) {
    return feedBackDB->getItemsLikeSize(userID);
}

int64_t BackendMiddlewareHandler::itemsDislikeSize(const std::string& userID) {
    return feedBackDB->getItemsDislikeSize(userID);
}

int64_t BackendMiddlewareHandler::favouriteItemsSize(const std::string& userID) {
    return feedBackDB->getFavouriteItemsSize(userID);
}