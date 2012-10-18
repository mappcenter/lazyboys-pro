//listTags cached in listtags.js
var myJsonObj = jsonParse(listTags);

function getTagName(tagID){
    var tagName="";                                                      
    for (var prop in myJsonObj) {
        if (myJsonObj.hasOwnProperty(prop)) {
            if(myJsonObj[prop].tagID==tagID){
                tagName=myJsonObj[prop].tagName;
                break;
            }
        }
    }                
    return tagName;
}
function getRandomItemOfTag(tagID){
  var tagName=getTagName(tagID);
    $("#statusTag").html("<a href='javascript:getRandomItemOfTag("+tagID+");'>"+tagName+"</a>");
    $currentTagID=tagID.toString();
    $(document).ready(function(){                
        $.post("random", {tagID:$currentTagID}, function(data) {	
                var myJsonObj = jsonParse(data);
                addItemsToQueue(myJsonObj);
               $("#lz-save-button").attr("rel", myJsonObj.itemID);
                $("#ContentItem").html(myJsonObj.content); 
                var listTagsID=myJsonObj.tagsID.toString().split(",");
                var lTags="";
                for (var k=0;k< listTagsID.length;k++) {
                lTags+="<a href='javascript:getRandomItemOfTag("+listTagsID[k]+");' rel='"+listTagsID[k]+"'>"+getTagName(listTagsID[k].toString())+"</a>     ";
                }                            
               // alert(lTags);
              $("#listTag").html(lTags);
              $("#lztitle").attr("style","visibility: visible;");
            });
    });
    }
    //next - back button
    var queueItem=new Array();
    var currentIndex=0;
    function backItems(){
        var item=new Array();
        if(currentIndex>0) 
            {
                currentIndex--;
                testCurrentIndex(0,currentIndex);
            }
        else currentIndex=0;
        item=queueItem[currentIndex];
        showItemBackNext(item);
    }
    function nextItems(){
        var item=new Array();
        if(currentIndex<queueItem.length-1){ 
            currentIndex++;
            testCurrentIndex(1,currentIndex);
        }
        else{
            return;
        }
        item=queueItem[currentIndex];
        showItemBackNext(item);
    }
    function addItemsToQueue(_item){
        var item=new Array();
        //var tagsId=new Array();
        if(_item.itemID==null) return;
        item[0]=_item.itemID;
        item[1]=_item.content;
        item[2]=_item.tagsID.toString().split(",");                                                                                    
        var index=queueItem.length;
        if(index<111){                        
            queueItem[index]=item;
            currentIndex=queueItem.length-1;
        }
        else{
            return;
        }
        //alert(item[2]);
    }
    function showItemBackNext(item){
        //alert("show item:"+item[1]); 
        if(item==null) return;
        $(document).ready(function(){                        
            $("#ContentItem").html(item[1]);
            $("#lz-save-button").attr("rel", item[0]);
            var aTagsID=item[2];
            var lTags=""; 
                for (var k=0;k< aTagsID.length;k++) {
                lTags+="<a href='javascript:getRandomItemOfTag("+aTagsID[k]+");' rel='"+aTagsID[k]+"'>"+getTagName(aTagsID[k].toString())+"</a>     ";
                }                            
              $("#listTag").html(lTags);
        });
        //turnOnOffBackNext();
        //alert(currentIndex);
    }
    function turnOnOffBackNext(){
        $(document).ready(function(){     
            if(queueItem.length==0 || queueItem.length==currentIndex){
                    $("#nextItem").addClass("disabledButton");
            }
            else{
                if(queueItem.length>0 && queueItem.length>currentIndex){
                    $("#nextItem").removeClass("disabledButton");
                }
            }

        });
    }
    function testCurrentIndex(val,index){
        $(document).ready(function(){
            if(val==0) $("#testCurrentIndexBack") .html(index);
            if(val==1) $("#testCurrentIndexNext") .html(index);
             $("#testTotal") .html(queueItem.length);
        });
    }       
function fCallback(data) {
    alert("call back function");
	if(data.action == 0) {
		//user cancel push feed
		//if game/app to track how many user press cancel/hide pushfeed can be keep track here
		alert('user cancel push feed');
	}
	else if(data.action == 1) {//push feed successful
		var feedId = data.feedId; //feedId of feed pushed
		var validateKey = data.validateKey; //validateKey used for app to verify feedId is published by this app/game
		var state = data.state;
		//if game/app need to validate feedId (with validateKey), app/game must redirect to another page with param feedid and validateKey and
		//use socialapi to validate feedId, with successful, app/game will know exactly user published feed successful.
		alert("feedid=" + feedId + "-validateKey=" + validateKey + "-state=" + state);
	}
}
 function randomFromInterval(from,to)
    {
        return Math.floor(Math.random()*(to-from+1)+from);
    }  
    function delItem(id){
        $(document).ready(function(){   
            //alert(id);
            var uID=$("#usrId").val();
            $.post("delItem", {
                userID:uID,
                itemID:id
            }, function(data) {
                    //var myJsonObj = jsonParse(data);
                    //if(data=="1"){
                        $.post('/userItem', {
                            userID:uID                        
                        }, function(data){
                            $(".popular-data").html(data);
                        }
                        );
                    //}
                });                  
        });
    }
    function feedWall(id){
        $(document).ready(function(){                      
            var uID=$("#usrId").val();            
            var statusText =$('#itemContent'+id).html();            
            var uId_to = $('#usrId').val();
            $.post('/feedItem', {
                userIdTo : uId_to,
                itemContent : statusText
            }, function(data){
                zmf.ui(
                    {
                        pub_key:"eba443348315d8c27c8c070cb2a40a52",
                        sign_key:data,
                        action_id:1,
                        uid_to: uId_to,
                        object_id: "",
                        attach_name: "",
                        attach_href: "",
                        attach_caption: "",
                        attach_des: statusText,
                        media_type:1,
                        media_img:"http://d.f12.photo.zdn.vn/upload/original/2012/10/11/13/34/1349937282850465_574_574.jpg",
                        media_src:"",
                        actlink_text:"",
                        actlink_href:"",
                        tpl_id: 3,
                        comment:statusText,
                        suggestion: ["hello1","hello2"]
                    });      
            }
            );               
        });
    }
    function getRandomItemOfTag2(idTag){
        $(document).ready(function(){            
            var activeTab = "#lztab1"; 
            //alert(activeTab);
            $("a[href=#lztab2]").removeClass("lzactive"); 
            $("a[href=#lztab1]").addClass("lzactive"); 
            $(".lztabContents").hide(); 
            $(activeTab).fadeIn(); 
            getRandomItemOfTag(idTag);
        });
    }
$(document).ready(function() {            
    //turnOnOffBackNext();
    //
    //get ramdom item when loading page
    $.post("http://fresher2012.live/", {alfa:123}, function(data) {	
                    var myJsonObj = jsonParse(data);                     
                    if(myJsonObj.content!=null) $("#ContentItem").html(myJsonObj.content); 
                    $("#lz-save-button").attr("rel", myJsonObj.itemID);                    
                    var aTagsID=myJsonObj.tagsID.toString().split(",");
                    addItemsToQueue(myJsonObj);
                    var lTags=""; 
                    for (var k=0;k< aTagsID.length;k++) {
                    lTags+="<a href='javascript:getRandomItemOfTag("+aTagsID[k]+");' rel='"+aTagsID[k]+"'>"+getTagName(aTagsID[k].toString())+"</a>     ";
                    }                            
                  $("#listTag").html(lTags);
                });
    // get list tag when loading page    
    $.post("listTag", {alfa:123}, function(data) {
                    //var myJsonObj = jsonParse(data);
                    var lTags="";
                    for (var prop in myJsonObj) {
                        if (myJsonObj.hasOwnProperty(prop)) {
                            lTags+="<a href='javascript:getRandomItemOfTag("+myJsonObj[prop].tagID+");' rel='"+myJsonObj[prop].tagID+"'style='font-size:"+randomFromInterval(13, 28)+"'>"+myJsonObj[prop].tagName+"</a>     ";                                    
                        }
                    }
                  $("#lz-toptags-container").html(lTags);
                });
        $("#getItem").click(function(){
                $.post("http://fresher2012.live/", {alfa:123}, function(data) {	
                    
                    var myJsonObj = jsonParse(data);
                    
                   $("#lz-save-button").attr("rel", myJsonObj.itemID);
                   addItemsToQueue(myJsonObj);                   
                    $("#ContentItem").html(myJsonObj.content); 
                    var listTagsID=myJsonObj.tagsID.toString().split(",");
                    var lTags="";
                    for (var k=0;k< listTagsID.length;k++) {
                    lTags+="<a href='javascript:getRandomItemOfTag("+listTagsID[k]+");' rel='"+listTagsID[k]+"'>"+getTagName(listTagsID[k].toString())+"</a>     ";
                    }                            
                  $("#listTag").html(lTags);
                  
                });
        });     
        $("#lz-save-button").click(function (){
            var value=$(this).attr("rel");
            var uID=$("#usrId").val();
            $.post("saveItem", {
                userID:uID,
                itemID:value
            }, function(data) {
                    //var myJsonObj = jsonParse(data);
                    alert(data);
                });
            //alert(value + "userID:"+uID);
        });
        $(".saveItem").click(function (){
            var value=$(this).attr("rel");
            var uID=$("#usrId").val();
            $.post("saveItem", {
                userID:uID,
                itemID:value
            }, function(data) {
                    //var myJsonObj = jsonParse(data);
                    alert(data);
                });
            //alert(value + "userID:"+uID);
        });
        
        $("#lz-feed-button").click(function(){            
            var statusText =$('#ContentItem').html();            
            var uId_to = $('#usrId').val();
            var objId=$("#lz-save-button").attr("rel");
            //alert(objId);
            $.post('/feedItem', {
                userIdTo : uId_to,
                itemContent : statusText
            }, function(data){
                zmf.ui(
                    {
                        pub_key:"eba443348315d8c27c8c070cb2a40a52",
                        sign_key:data,
                        action_id:1,
                        uid_to: uId_to,
                        object_id: "",
                        attach_name: "",
                        attach_href: "",
                        attach_caption: "",
                        attach_des: statusText,
                        media_type:1,
                        media_img:"http://d.f12.photo.zdn.vn/upload/original/2012/10/11/13/34/1349937282850465_574_574.jpg",
                        media_src:"",
                        actlink_text:"",
                        actlink_href:"",
                        tpl_id: 3,
                        comment:statusText,
                        suggestion: ["hello1","hello2"]
                    });      
            }
            );
        
        });
        $(".feedItemWall").click(function(){  
            var itemID=$(this).attr("rel");
            var statusText =$('#itemID'+itemID).html();            
            var uId_to = $('#usrId').val();
            //var objId=$("#lz-save-button").attr("rel");
            //alert(objId);
            $.post('/feedItem', {
                userIdTo : uId_to,
                itemContent : statusText
            }, function(data){
                zmf.ui(
                    {
                        pub_key:"eba443348315d8c27c8c070cb2a40a52",
                        sign_key:data,
                        action_id:1,
                        uid_to: uId_to,
                        object_id: "",
                        attach_name: "",
                        attach_href: "",
                        attach_caption: "",
                        attach_des: statusText,
                        media_type:1,
                        media_img:"http://d.f12.photo.zdn.vn/upload/original/2012/10/11/13/34/1349937282850465_574_574.jpg",
                        media_src:"",
                        actlink_text:"",
                        actlink_href:"",
                        tpl_id: 3,
                        comment:statusText,
                        suggestion: ["hello1","hello2"]
                    });      
            }
            );
        
        });
        //tab favorite
//        $(".feedWall").click(function(){  
//            
//        
//        });
        $(".lztabContents").hide(); // Ẩn toàn bộ nội dung của tab
        $(".lztabContents:first").show(); // Mặc định sẽ hiển thị tab1

        $("#lztabContaier ul li a").click(function(){ //Khai báo sự kiện khi click vào một tab nào đó			
                var activeTab = $(this).attr("href"); 
                //alert(activeTab);
                $("#lztabContaier ul li a").removeClass("lzactive"); 
                $(this).addClass("lzactive"); 
                $(".lztabContents").hide(); 
                $(activeTab).fadeIn(); 
                if($(this).attr("rel")=="fpage"){
                    var uId = $('#usrId').val();
                    $.post('/userItem', {
                        userID:uId                        
                    }, function(data){
                        $(".popular-data").html(data);
                    }
                    );
                }
        });
        $("#likeItem").click(function(){
            var uId = $('#usrId').val();
            var Action="like";
            var objId=$("#lz-save-button").attr("rel");
            //alert("uID:"+uId +"action:"+Action+"ID:"+objId);
            $.post('/like_unlike', {
                userID:uId,
                typeAction : Action,
                itemID : objId
            }, function(data){
                    //if(data=="1"){
                        alert(data);
                    //}
            }
            );
        });
        $("#close-lztittle").click(function(){            
         $("#lztitle").attr("style","visibility: hidden;");
        });
    });
    