namespace cpp BackendMiddleware
namespace java BackendMiddleware

struct Tag{
  1:string tagID,		//sua string thanh i32
  2:string tagName,
  3:i64 viewCounts,		//them viewCounts
  4:string dateAdd,
  5:string dateUpdate
}

struct Item{   			//struct cau nghi nhanh
  1:string itemID, 
  2:string content, 		//sua itemName -- > content
  3:list<string> tagsID, 	//Luu list TagsID vi moi item thuoc nhieu tag
  4:i64 viewCounts,		//them viewCounts
  5:i64 likeCounts,		//them likeCounts
  6:i64 dislikeCounts,		// them dislikeCounts
  7:string dateAdd,		//ngay tao Item
  8:string dateUpdate		//ngay chinh sua
}

struct ItemTag{
  1:string tagID,
  2:list<string> itemsID
}

struct User{
  1:string userID,		//doi userID thanh userID
  2:string userToken,
  3:i32 userRole,		// 1:Admin 0:User binh thuong -1:User dang bi lock
}

struct UserFeedBack{
  1:string userID,
  2:list<string> itemsLike,
  3:list<string> itemsDislike,
  4:list<string> favouriteItems,
}

service BackendMiddleware{

  i64 itemdbSize(),
  i64 tagdbSize(),
  i64 itemtagdbSize(),
  i64 itemtagSize(1:string tagID),
  i64 userdbSize(),
  i64 itemsLikeSize(1:string userID),
  i64 itemsDislikeSize(1:string userID),
  i64 favouriteItemsSize(1:string userID),

  //cac tac vu lien quan den Tag
  list<Tag> getAllTag(),				//lay danh sach tag
  bool insertTag(1:string tagName),			//them tag
  bool deleteTag(1:string tagID),			//xoa tag
  bool deleteAllTag(1:list<string> tagIDs),
  bool editTag(1:string tagID, 2:string tagName), 	//sua tag
  Tag getTag(1:string tagID),				//them: lay tag from key
  void setViewCountTag(1:string tagID),			//cap nhat viewcount co itemID
  list<Tag> getTopTags(1:i64 number),			//lay danh sach n tag noi bat
  list<Tag> getTagKeyword(1:string keyWord),		//search tag co keyword
  
  //cac tac vu lien quan de Item
  list<Item> getAllItems(1:i64 number),				//lay tat ca cac cau nghi nhanh	
  Item getItemFromItemID(1:string itemID),			//Lay Item co item.itemID=itemID
  list<Item> getItemsFromListItemID(1:list<string> itemIDs), 	//Lay tat ca Item co item.itemID thuoc itemIDs
  list<Item> getAllItemshaveTag(1:string tagID, 2:i32 numberItems),	//lay numberItems cau nghi nhanh thuoc tagID
  list<Item> pagingItemsTag(1:string tagID, 2:i32 pageNumber, 3:i32 numberItems),	//lay numberItems thuoc pageNumber co tagID
  list<string> getAllItemsIDhaveTag(1:string tagID, 2:i32 numberItemsID),	//lay tat ca cac cau nghi nhanh co tagID, tra ve itemIDs
  Item getRandomItem(), 					//lay random itemNumber cau nghi nhanh
  Item getRandomItemhaveTag(1: string tagID), 			//lay random 1 cau trong tag co tagID
  void increaseViewCountItem(1:string itemID),			//tang viewcount co itemID, doi ten cho ham.
  void increaseLikeCountItem(1:string itemID),			//tang likeCounts
  void increaseDislikeCountItem(1:string itemID),		//tang dislikeCounts
  
  string insertItem(1:string content, 2:list<string> tagIDs),	 //them cau nghi nhanh, thuoc cac tag co tagID nam trong list TagID. Tra ve itemID de bao thanh cong, tra ve -1 neu that bai.
  bool deleteItem(1:string itemID),				//xoa 1 cau nghi nhanh
  bool deleteAllItem(1:list<string> itemIDs),
  bool editItem(1:string itemID, 2:string newItemValue, 3:list<string> newTagIDs), //sua cau nghi nhanh
  
  list<Item> getItemsPage(1:i64 pageNumber, 2:i64 itemNumber, 3:string tagID), 	//pageNumber: trang, itemNumber: so records tren 1 trang, neu tagID==-1 thi lay trong itemDB, nguoc lai thi lay item thuoc tagID
  
  list<Item> getItemKeyword(1:string keyWord),			//lay cau nghi nhanh theo keyword

  list<Item> getItemKeywordTag(1:string keyWord,2:string tagID),	//lay cau nghi nhanh theo keyword va tag
  
  list<Item> getTopItems(1:i64 number),				//lay danh sach n cau noi bat
  list<Item> getTopItemsofTag(1:i64 number,2:string tagID),	//lay danh sach n cau cau noi bat thuoc tag=tagName
  
  list<Item> getFavouriteItems(1:string userID,2:i64 number), 	//Get list cac cau nghi nhanh yeu thich
  list<Item> getFavouriteItemsofTag(1:string userID,2:i64 number, 3:string tagID),//Get list cac cau nghi nhanh yeu thich theo TagsID
  bool insertFavouriteItem(1:string userID,2:string itemID),	//them cau nghi nhanh yeu thich vao danh sach
  bool deleteFavouriteItem(1:string userID,2:string itemID),	//xoa cau nghi nhanh yeu thich ra khoi danh sach
  
  list<string> getAllItemsIDLike(1:string userID),
  list<Item> getAllItemsLike(1:string userID,2:i32 number),
  bool insertLikedItem(1:string userID,2:string itemID),
  bool deleteLikedItem(1:string userID,2:string itemID),
  
  list<string> getAllItemsIDDislike(1:string userID),
  list<Item> getAllItemsDislike(1:string userID,2:i32 number),
  bool insertDislikedItem(1:string userID,2:string itemID),
  bool deleteDislikedItem(1:string userID,2:string itemID),
  
  //chuc nang quan ly account cua admin
  bool userExisted(1: string userID),				//kiem tra user co ton tai trong DB ko
  bool blockUser(1: string userID),				//khoa khong cho user truy cap ung dung
  bool unblockUser(1: string userID),				//mo khoa cho user truy cap ung dung
  bool addUser(1:string userID, 2:string userToken,3:i32 userRole),	//them user vao data
  bool deleteUser(1:string userID),				//xoa user
  bool editUser(1:string userID, 2:string userToken,3:i32 userRole),
  bool deleteAllUser()
}
