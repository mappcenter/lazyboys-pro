/**
 * Autogenerated by Thrift Compiler (0.9.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef BackendMiddleware_TYPES_H
#define BackendMiddleware_TYPES_H

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>



namespace BackendMiddleware {

typedef struct _Tag__isset {
  _Tag__isset() : tagID(false), tagName(false), viewCounts(false), dateAdd(false), dateUpdate(false) {}
  bool tagID;
  bool tagName;
  bool viewCounts;
  bool dateAdd;
  bool dateUpdate;
} _Tag__isset;

class Tag {
 public:

  static const char* ascii_fingerprint; // = "2304AA70C6D0202BE23C422134FF450B";
  static const uint8_t binary_fingerprint[16]; // = {0x23,0x04,0xAA,0x70,0xC6,0xD0,0x20,0x2B,0xE2,0x3C,0x42,0x21,0x34,0xFF,0x45,0x0B};

  Tag() : tagID(), tagName(), viewCounts(0), dateAdd(), dateUpdate() {
  }

  virtual ~Tag() throw() {}

  std::string tagID;
  std::string tagName;
  int64_t viewCounts;
  std::string dateAdd;
  std::string dateUpdate;

  _Tag__isset __isset;

  void __set_tagID(const std::string& val) {
    tagID = val;
  }

  void __set_tagName(const std::string& val) {
    tagName = val;
  }

  void __set_viewCounts(const int64_t val) {
    viewCounts = val;
  }

  void __set_dateAdd(const std::string& val) {
    dateAdd = val;
  }

  void __set_dateUpdate(const std::string& val) {
    dateUpdate = val;
  }

  bool operator == (const Tag & rhs) const
  {
    if (!(tagID == rhs.tagID))
      return false;
    if (!(tagName == rhs.tagName))
      return false;
    if (!(viewCounts == rhs.viewCounts))
      return false;
    if (!(dateAdd == rhs.dateAdd))
      return false;
    if (!(dateUpdate == rhs.dateUpdate))
      return false;
    return true;
  }
  bool operator != (const Tag &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Tag & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(Tag &a, Tag &b);

typedef struct _Item__isset {
  _Item__isset() : itemID(false), content(false), tagsID(false), viewCounts(false), likeCounts(false), dislikeCounts(false), dateAdd(false), dateUpdate(false) {}
  bool itemID;
  bool content;
  bool tagsID;
  bool viewCounts;
  bool likeCounts;
  bool dislikeCounts;
  bool dateAdd;
  bool dateUpdate;
} _Item__isset;

class Item {
 public:

  static const char* ascii_fingerprint; // = "FD9C9D8A74A290A49728D3BAB8A4E6D0";
  static const uint8_t binary_fingerprint[16]; // = {0xFD,0x9C,0x9D,0x8A,0x74,0xA2,0x90,0xA4,0x97,0x28,0xD3,0xBA,0xB8,0xA4,0xE6,0xD0};

  Item() : itemID(), content(), viewCounts(0), likeCounts(0), dislikeCounts(0), dateAdd(), dateUpdate() {
  }

  virtual ~Item() throw() {}

  std::string itemID;
  std::string content;
  std::vector<std::string>  tagsID;
  int64_t viewCounts;
  int64_t likeCounts;
  int64_t dislikeCounts;
  std::string dateAdd;
  std::string dateUpdate;

  _Item__isset __isset;

  void __set_itemID(const std::string& val) {
    itemID = val;
  }

  void __set_content(const std::string& val) {
    content = val;
  }

  void __set_tagsID(const std::vector<std::string> & val) {
    tagsID = val;
  }

  void __set_viewCounts(const int64_t val) {
    viewCounts = val;
  }

  void __set_likeCounts(const int64_t val) {
    likeCounts = val;
  }

  void __set_dislikeCounts(const int64_t val) {
    dislikeCounts = val;
  }

  void __set_dateAdd(const std::string& val) {
    dateAdd = val;
  }

  void __set_dateUpdate(const std::string& val) {
    dateUpdate = val;
  }

  bool operator == (const Item & rhs) const
  {
    if (!(itemID == rhs.itemID))
      return false;
    if (!(content == rhs.content))
      return false;
    if (!(tagsID == rhs.tagsID))
      return false;
    if (!(viewCounts == rhs.viewCounts))
      return false;
    if (!(likeCounts == rhs.likeCounts))
      return false;
    if (!(dislikeCounts == rhs.dislikeCounts))
      return false;
    if (!(dateAdd == rhs.dateAdd))
      return false;
    if (!(dateUpdate == rhs.dateUpdate))
      return false;
    return true;
  }
  bool operator != (const Item &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const Item & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(Item &a, Item &b);

typedef struct _ItemTag__isset {
  _ItemTag__isset() : tagID(false), itemsID(false) {}
  bool tagID;
  bool itemsID;
} _ItemTag__isset;

class ItemTag {
 public:

  static const char* ascii_fingerprint; // = "25702B8D5E28AA39160F267DABBC8446";
  static const uint8_t binary_fingerprint[16]; // = {0x25,0x70,0x2B,0x8D,0x5E,0x28,0xAA,0x39,0x16,0x0F,0x26,0x7D,0xAB,0xBC,0x84,0x46};

  ItemTag() : tagID() {
  }

  virtual ~ItemTag() throw() {}

  std::string tagID;
  std::vector<std::string>  itemsID;

  _ItemTag__isset __isset;

  void __set_tagID(const std::string& val) {
    tagID = val;
  }

  void __set_itemsID(const std::vector<std::string> & val) {
    itemsID = val;
  }

  bool operator == (const ItemTag & rhs) const
  {
    if (!(tagID == rhs.tagID))
      return false;
    if (!(itemsID == rhs.itemsID))
      return false;
    return true;
  }
  bool operator != (const ItemTag &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const ItemTag & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(ItemTag &a, ItemTag &b);

typedef struct _User__isset {
  _User__isset() : userID(false), userToken(false), userRole(false) {}
  bool userID;
  bool userToken;
  bool userRole;
} _User__isset;

class User {
 public:

  static const char* ascii_fingerprint; // = "343DA57F446177400B333DC49B037B0C";
  static const uint8_t binary_fingerprint[16]; // = {0x34,0x3D,0xA5,0x7F,0x44,0x61,0x77,0x40,0x0B,0x33,0x3D,0xC4,0x9B,0x03,0x7B,0x0C};

  User() : userID(), userToken(), userRole(0) {
  }

  virtual ~User() throw() {}

  std::string userID;
  std::string userToken;
  int32_t userRole;

  _User__isset __isset;

  void __set_userID(const std::string& val) {
    userID = val;
  }

  void __set_userToken(const std::string& val) {
    userToken = val;
  }

  void __set_userRole(const int32_t val) {
    userRole = val;
  }

  bool operator == (const User & rhs) const
  {
    if (!(userID == rhs.userID))
      return false;
    if (!(userToken == rhs.userToken))
      return false;
    if (!(userRole == rhs.userRole))
      return false;
    return true;
  }
  bool operator != (const User &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const User & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(User &a, User &b);

typedef struct _UserFeedBack__isset {
  _UserFeedBack__isset() : userID(false), itemsLike(false), itemsDislike(false), favouriteItems(false) {}
  bool userID;
  bool itemsLike;
  bool itemsDislike;
  bool favouriteItems;
} _UserFeedBack__isset;

class UserFeedBack {
 public:

  static const char* ascii_fingerprint; // = "34668B1D16D71B25479C61503EAC3D32";
  static const uint8_t binary_fingerprint[16]; // = {0x34,0x66,0x8B,0x1D,0x16,0xD7,0x1B,0x25,0x47,0x9C,0x61,0x50,0x3E,0xAC,0x3D,0x32};

  UserFeedBack() : userID() {
  }

  virtual ~UserFeedBack() throw() {}

  std::string userID;
  std::vector<std::string>  itemsLike;
  std::vector<std::string>  itemsDislike;
  std::vector<std::string>  favouriteItems;

  _UserFeedBack__isset __isset;

  void __set_userID(const std::string& val) {
    userID = val;
  }

  void __set_itemsLike(const std::vector<std::string> & val) {
    itemsLike = val;
  }

  void __set_itemsDislike(const std::vector<std::string> & val) {
    itemsDislike = val;
  }

  void __set_favouriteItems(const std::vector<std::string> & val) {
    favouriteItems = val;
  }

  bool operator == (const UserFeedBack & rhs) const
  {
    if (!(userID == rhs.userID))
      return false;
    if (!(itemsLike == rhs.itemsLike))
      return false;
    if (!(itemsDislike == rhs.itemsDislike))
      return false;
    if (!(favouriteItems == rhs.favouriteItems))
      return false;
    return true;
  }
  bool operator != (const UserFeedBack &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const UserFeedBack & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(UserFeedBack &a, UserFeedBack &b);

} // namespace

#endif
