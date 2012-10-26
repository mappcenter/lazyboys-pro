// This autogenerated skeleton file illustrates how to build a server.
// You should copy it to another filename to avoid overwriting it.

#include "BackendMiddleware.h"
#include <protocol/TBinaryProtocol.h>
#include <server/TSimpleServer.h>
#include <transport/TServerSocket.h>
#include <transport/TBufferTransports.h>

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;

using boost::shared_ptr;

using namespace  ::BackendMiddleware;

class BackendMiddlewareHandler : virtual public BackendMiddlewareIf {
 public:
  BackendMiddlewareHandler() {
    // Your initialization goes here
  }

  int64_t itemdbSize() {
    // Your implementation goes here
    printf("itemdbSize\n");
  }

  int64_t tagdbSize() {
    // Your implementation goes here
    printf("tagdbSize\n");
  }

  int64_t itemtagdbSize() {
    // Your implementation goes here
    printf("itemtagdbSize\n");
  }

  int64_t itemtagSize(const std::string& tagID) {
    // Your implementation goes here
    printf("itemtagSize\n");
  }

  int64_t userdbSize() {
    // Your implementation goes here
    printf("userdbSize\n");
  }

  int64_t itemsLikeSize(const std::string& userID) {
    // Your implementation goes here
    printf("itemsLikeSize\n");
  }

  int64_t itemsDislikeSize(const std::string& userID) {
    // Your implementation goes here
    printf("itemsDislikeSize\n");
  }

  int64_t favouriteItemsSize(const std::string& userID) {
    // Your implementation goes here
    printf("favouriteItemsSize\n");
  }

  void getAllTag(std::vector<Tag> & _return) {
    // Your implementation goes here
    printf("getAllTag\n");
  }

  bool insertTag(const std::string& tagName) {
    // Your implementation goes here
    printf("insertTag\n");
  }

  bool deleteTag(const std::string& tagID) {
    // Your implementation goes here
    printf("deleteTag\n");
  }

  bool deleteAllTag(const std::vector<std::string> & tagIDs) {
    // Your implementation goes here
    printf("deleteAllTag\n");
  }

  bool editTag(const std::string& tagID, const std::string& tagName) {
    // Your implementation goes here
    printf("editTag\n");
  }

  void getTag(Tag& _return, const std::string& tagID) {
    // Your implementation goes here
    printf("getTag\n");
  }

  void setViewCountTag(const std::string& tagID) {
    // Your implementation goes here
    printf("setViewCountTag\n");
  }

  void getTopTags(std::vector<Tag> & _return, const int64_t number) {
    // Your implementation goes here
    printf("getTopTags\n");
  }

  void getTagKeyword(std::vector<Tag> & _return, const std::string& keyWord) {
    // Your implementation goes here
    printf("getTagKeyword\n");
  }

  void getAllItems(std::vector<Item> & _return, const int64_t number) {
    // Your implementation goes here
    printf("getAllItems\n");
  }

  void getItemFromItemID(Item& _return, const std::string& itemID) {
    // Your implementation goes here
    printf("getItemFromItemID\n");
  }

  void getItemsFromListItemID(std::vector<Item> & _return, const std::vector<std::string> & itemIDs) {
    // Your implementation goes here
    printf("getItemsFromListItemID\n");
  }

  void getAllItemshaveTag(std::vector<Item> & _return, const std::string& tagID, const int32_t numberItems) {
    // Your implementation goes here
    printf("getAllItemshaveTag\n");
  }

  void pagingItemsTag(std::vector<Item> & _return, const std::string& tagID, const int32_t pageNumber, const int32_t numberItems) {
    // Your implementation goes here
    printf("pagingItemsTag\n");
  }

  void getAllItemsIDhaveTag(std::vector<std::string> & _return, const std::string& tagID, const int32_t numberItemsID) {
    // Your implementation goes here
    printf("getAllItemsIDhaveTag\n");
  }

  void getRandomItem(Item& _return) {
    // Your implementation goes here
    printf("getRandomItem\n");
  }

  void getRandomItemhaveTag(Item& _return, const std::string& tagID) {
    // Your implementation goes here
    printf("getRandomItemhaveTag\n");
  }

  void increaseViewCountItem(const std::string& itemID) {
    // Your implementation goes here
    printf("increaseViewCountItem\n");
  }

  void increaseLikeCountItem(const std::string& itemID) {
    // Your implementation goes here
    printf("increaseLikeCountItem\n");
  }

  void increaseDislikeCountItem(const std::string& itemID) {
    // Your implementation goes here
    printf("increaseDislikeCountItem\n");
  }

  void insertItem(std::string& _return, const std::string& content, const std::vector<std::string> & tagIDs) {
    // Your implementation goes here
    printf("insertItem\n");
  }

  bool deleteItem(const std::string& itemID) {
    // Your implementation goes here
    printf("deleteItem\n");
  }

  bool deleteAllItem(const std::vector<std::string> & itemIDs) {
    // Your implementation goes here
    printf("deleteAllItem\n");
  }

  bool editItem(const std::string& itemID, const std::string& newItemValue, const std::vector<std::string> & newTagIDs) {
    // Your implementation goes here
    printf("editItem\n");
  }

  void getItemsPage(std::vector<Item> & _return, const int64_t pageNumber, const int64_t itemNumber, const std::string& tagID) {
    // Your implementation goes here
    printf("getItemsPage\n");
  }

  void getItemKeyword(std::vector<Item> & _return, const std::string& keyWord) {
    // Your implementation goes here
    printf("getItemKeyword\n");
  }

  void getItemKeywordTag(std::vector<Item> & _return, const std::string& keyWord, const std::string& tagID) {
    // Your implementation goes here
    printf("getItemKeywordTag\n");
  }

  void getTopItems(std::vector<Item> & _return, const int64_t number) {
    // Your implementation goes here
    printf("getTopItems\n");
  }

  void getTopItemsofTag(std::vector<Item> & _return, const int64_t number, const std::string& tagID) {
    // Your implementation goes here
    printf("getTopItemsofTag\n");
  }

  void getFavouriteItems(std::vector<Item> & _return, const std::string& userID, const int64_t number) {
    // Your implementation goes here
    printf("getFavouriteItems\n");
  }

  void getFavouriteItemsofTag(std::vector<Item> & _return, const std::string& userID, const int64_t number, const std::string& tagID) {
    // Your implementation goes here
    printf("getFavouriteItemsofTag\n");
  }

  bool insertFavouriteItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("insertFavouriteItem\n");
  }

  bool deleteFavouriteItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("deleteFavouriteItem\n");
  }

  void getAllItemsIDLike(std::vector<std::string> & _return, const std::string& userID) {
    // Your implementation goes here
    printf("getAllItemsIDLike\n");
  }

  void getAllItemsLike(std::vector<Item> & _return, const std::string& userID, const int32_t number) {
    // Your implementation goes here
    printf("getAllItemsLike\n");
  }

  bool insertLikedItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("insertLikedItem\n");
  }

  bool deleteLikedItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("deleteLikedItem\n");
  }

  void getAllItemsIDDislike(std::vector<std::string> & _return, const std::string& userID) {
    // Your implementation goes here
    printf("getAllItemsIDDislike\n");
  }

  void getAllItemsDislike(std::vector<Item> & _return, const std::string& userID, const int32_t number) {
    // Your implementation goes here
    printf("getAllItemsDislike\n");
  }

  bool insertDislikedItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("insertDislikedItem\n");
  }

  bool deleteDislikedItem(const std::string& userID, const std::string& itemID) {
    // Your implementation goes here
    printf("deleteDislikedItem\n");
  }

  bool userExisted(const std::string& userID) {
    // Your implementation goes here
    printf("userExisted\n");
  }

  bool blockUser(const std::string& userID) {
    // Your implementation goes here
    printf("blockUser\n");
  }

  bool unblockUser(const std::string& userID) {
    // Your implementation goes here
    printf("unblockUser\n");
  }

  bool addUser(const std::string& userID, const std::string& userToken, const int32_t userRole) {
    // Your implementation goes here
    printf("addUser\n");
  }

  bool deleteUser(const std::string& userID) {
    // Your implementation goes here
    printf("deleteUser\n");
  }

  bool editUser(const std::string& userID, const std::string& userToken, const int32_t userRole) {
    // Your implementation goes here
    printf("editUser\n");
  }

  bool deleteAllUser() {
    // Your implementation goes here
    printf("deleteAllUser\n");
  }

  void getUser(User& _return, const std::string& userID) {
    // Your implementation goes here
    printf("getUser\n");
  }

  void getAllUser(std::vector<std::string> & _return) {
    // Your implementation goes here
    printf("getAllUser\n");
  }

};

int main(int argc, char **argv) {
  int port = 9090;
  shared_ptr<BackendMiddlewareHandler> handler(new BackendMiddlewareHandler());
  shared_ptr<TProcessor> processor(new BackendMiddlewareProcessor(handler));
  shared_ptr<TServerTransport> serverTransport(new TServerSocket(port));
  shared_ptr<TTransportFactory> transportFactory(new TBufferedTransportFactory());
  shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

  TSimpleServer server(processor, serverTransport, transportFactory, protocolFactory);
  server.serve();
  return 0;
}

