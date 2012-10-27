/* 
 * File:   BackendMiddlewareHandler.h
 * Author: toantm
 *
 * Created on September 20, 2012, 2:52 PM
 */

#ifndef BACKENDMIDDLEWAREHANDLER_H
#define	BACKENDMIDDLEWAREHANDLER_H

#include "BackendMiddleware.h"
#include "ItemDB.h"
#include "ItemTagDB.h"
#include "TagDB.h"
#include "UserDB.h"
#include "FeedBackDB.h"
#include "synchronizeDB.h"

#include <iostream>
#include <Poco/Logger.h>
#include <Poco/Mutex.h>
#include "Poco/RegularExpression.h"
#include <boost/thread.hpp>

using namespace BackendMiddleware;
using Poco::Logger;
using Poco::FastMutex;
using Poco::RegularExpression;
using namespace boost;

class BackendMiddlewareHandler : public BackendMiddlewareIf {
public:
    typedef Poco::FastMutex Mutex;
public:
    BackendMiddlewareHandler();
    BackendMiddlewareHandler(string pathItemsDB, string pathTagsDB,
            string pathItemTagDB, string pathUserDB, string pathFeedBackDB);
    virtual ~BackendMiddlewareHandler();
    void synchronizeThread();
public:
    int64_t itemdbSize();
    int64_t tagdbSize();
    int64_t itemtagdbSize();
    int64_t itemtagSize(const std::string& tagID);
    int64_t userdbSize();
    int64_t itemsLikeSize(const std::string& userID);
    int64_t itemsDislikeSize(const std::string& userID);
    int64_t favouriteItemsSize(const std::string& userID);

    void getAllTag(std::vector<Tag> & _return);
    bool insertTag(const std::string& tagName);
    bool deleteTag(const std::string& tagID);
    bool deleteAllTag(const std::vector<std::string> & tagIDs);
    bool editTag(const std::string& tagID, const std::string& tagName);
    void getTag(Tag& _return, const std::string& tagID);
    void setViewCountTag(const std::string& tagID);
    void getTopTags(std::vector<Tag> & _return, const int64_t number);
    void getTagKeyword(std::vector<Tag> & _return, const std::string& keyWord);

    void getAllItems(std::vector<Item> & _return, const int64_t number);
    void getItemFromItemID(Item& _return, const std::string& itemID);
    void getItemsFromListItemID(std::vector<Item> & _return, const std::vector<std::string> & itemIDs);
    void getAllItemshaveTag(std::vector<Item> & _return, const std::string& tagID, const int32_t numberItems);
    void pagingItemsTag(std::vector<Item> & _return, const std::string& tagID, const int32_t pageNumber, const int32_t numberItems);
    void getAllItemsIDhaveTag(std::vector<std::string> & _return, const std::string& tagID, const int32_t numberItemsID);
    
    void getRandomItem(Item& _return);
    void getRandomItemhaveTag(Item& _return, const std::string& tagID);

    void increaseViewCountItem(const std::string& itemID);
    void increaseLikeCountItem(const std::string& itemID);
    void increaseDislikeCountItem(const std::string& itemID);

    void insertItem(std::string& _return, const std::string& content, const std::vector<std::string> & tagIDs);
    bool deleteItem(const std::string& itemID);
    bool deleteAllItem(const std::vector<std::string> & itemIDs);
    bool editItem(const std::string& itemID, const std::string& newItemValue, const std::vector<std::string> & newTagIDs);

    void getItemsPage(std::vector<Item> & _return, const int64_t pageNumber, const int64_t itemNumber, const std::string& tagID);
    void getItemKeyword(std::vector<Item> & _return, const std::string& keyWord);
    void getItemKeywordTag(std::vector<Item> & _return, const std::string& keyWord, const std::string& tagID);
    void getItemsPageKeyword(std::vector<Item> & _return, const std::string& keyWord, const int64_t pageNumber, const int64_t itemNumber);
    void getTopItems(std::vector<Item> & _return, const int64_t number);
    void getTopItemsofTag(std::vector<Item> & _return, const int64_t number, const std::string& tagID);

    void getFavouriteItems(std::vector<Item> & _return, const std::string& userID, const int64_t number);
    void getFavouriteItemsofTag(std::vector<Item> & _return, const std::string& userID, const int64_t number, const std::string& tagID);
    bool insertFavouriteItem(const std::string& userID, const std::string& itemID);
    bool deleteFavouriteItem(const std::string& userID, const std::string& itemID);

    void getAllItemsIDLike(std::vector<std::string> & _return, const std::string& userID);
    void getAllItemsLike(std::vector<Item> & _return, const std::string& userID, const int32_t number);
    bool insertLikedItem(const std::string& userID, const std::string& itemID);
    bool deleteLikedItem(const std::string& userID, const std::string& itemID);

    void getAllItemsIDDislike(std::vector<std::string> & _return, const std::string& userID);
    void getAllItemsDislike(std::vector<Item> & _return, const std::string& userID, const int32_t number);
    bool insertDislikedItem(const std::string& userID, const std::string& itemID);
    bool deleteDislikedItem(const std::string& userID, const std::string& itemID);

    bool userExisted(const std::string& userID);
    bool blockUser(const std::string& userID);
    bool unblockUser(const std::string& userID);
    bool addUser(const std::string& userID, const std::string& userToken, const int32_t userRole);
    bool deleteUser(const std::string& userID);
    bool editUser(const std::string& userID, const std::string& userToken, const int32_t userRole);
    bool deleteAllUser();
    void getUser(User& _return, const std::string& userID);
    void getAllUser(std::vector<std::string> & _return);
private:
    Logger* logger;
    Mutex _mutex;
    ItemDB* itemDB;
    TagDB* tagDB;
    ItemTagDB* itemTagDB;
    UserDB* userDB;
    FeedBackDB* feedBackDB;
    synchronizeDB* synDB;
};

#endif	/* BACKENDMIDDLEWAREHANDLER_H */

