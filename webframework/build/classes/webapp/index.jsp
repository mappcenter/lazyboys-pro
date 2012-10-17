<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="appinfo.MyAppInfo" %>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javax.servlet.http.HttpServlet" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>AJAX and Java - veerasundar.com</title>
<script type="text/javascript" src="http://static.me.zing.vn/feeddialog/js/zmfeeddialog-2.01.min.js" ></script>
<!-- <link rel="stylesheet" type="text/css" href="http://fresher2012.dev/css/lazyboys-style.css"> -->
</head>
<body>
    <h1>hello world</h1>
    <script>
        
        //alert(me.id);
    </script>
    <a href="javascript:getDialog7();">Click here get template 3</a><br/>
    <script type="text/javascript">
        var me=<%=request.getAttribute("me").toString()%>;
        var sigKey="<%=request.getAttribute("sigkey").toString()%>";
        var itemFeed=<%=request.getAttribute("itemFeed").toString()%>
        document.write(me.id.toString());
        function postFeed(){
         zmf.ui(
        {
            pub_key:"<%=MyAppInfo.getInstance().getAppKey()%>",
            sign_key:sigKey,
            action_key: "created by app",
            action_id:1,
            uid_to: document.write(me.id.toString()),
            object_id: "",
            attach_name: "Ca s\u0129 Quang Hà chuẩn bị kiện nhân chứng Vietnam Airlines",
            attach_href: "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
            attach_caption: "vnexpress.net",
            attach_des: "Nh\u1eadn được tin nhắn xin lỗi được cho là của bà Eileen Tan - nhân chứng trong vụ HLV Taekwondo, nhưng ca sĩ Quang Hà vẫn quyết đưa việc này ra tòa.",
            media_type:1,
            media_img:"http://vnexpress.net/Files/Subject/3b/a2/99/39/quang-ha-250.jpg.thumb150x0.ns.jpg",
            media_src:"http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
            actlink_text:"Link",
            actlink_href:"http://me.zing.vn/apps/link",
            tpl_id:3,
            suggestion: ["suggestion1", "suggestion2", "suggestion3"]
        });
        }
        function fCallback(data) {
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
/*
        function openFeedDialog(){            
            zmf.ui(
                {
                    pub_key:"",
                    sign_key:sigKey,
                    action_id:itemFeed.actId,
                    uid_to: itemFeed.userIdTo,
                    object_id: "113|234",
                    attach_name: itemFeed.attachName,
                    attach_href: itemFeed.attachHref,
                    attach_caption:itemFeed.attachCaption,
                    attach_des:itemFeed.attachDescription,
                    media_type:itemFeed.mediaType,
                    media_img:itemFeed.mediaImage,
                    media_src:itemFeed.mediaSource,
                    actlink_text:itemFeed.actionLinkText,
                    actlink_href:itemFeed.actionLinkHref,
                    tpl_id:itemFeed.tplId,		
                    suggestion: ["Chia se cho ban be", "Chia sẻ cho bạn bè", "Mời bạn bè"],
                    state:"123456",
                    callback : "fCallback"
                });
        }
        alert(itemFeed.userIdFrom);
        */
       function openFeedDialog(){
            zmf.ui(
            {
                pub_key:"<%=MyAppInfo.getInstance().getAppKey()%>",
                sign_key:"<%=request.getAttribute("sigkey").toString()%>",
                action_id:1,
                uid_to: 487573,
                object_id: "113|234",
                attach_name: 'Nhà \u0111ầu tư bán tháo, giá vàng thế giới tụt dốc - VnExpress',
                attach_href: "blank",
                attach_caption: "vnexpress.net",
                attach_des: "Giá vàng th\u1ebf giới giảm gần 40 USD xuống dưới 1.480 USD mỗi ounce trong một phiên giao dịch hoảng loạn, sau khi thị trường dầu lửa và chứng khoán tụt dốc với biên độ lớn nhất kể từ 2009.",
                media_type:1,
                media_img:"",
                media_src:"",
                actlink_text:"t\u1eeb link",
                actlink_href:"me.zing.vn",
                tpl_id:4,
                comment:"hello",
                suggestion: ["Chia se cho ban be", "Chia s\u1ebb cho bạn bè", "M\u1eddi bạn bè"],
                state:"123456",
                callback : "fCallback"
            });
        }
        function getDialog7(){
                    zmf.ui(
                    {
                        pub_key:"159c4a92ba596ed3de61b0678acf52a7",
                        sign_key:"9416f6d8a08913189ca218bcf8ff179e",
                        action_id:1,
                        uid_to: 487573,
                        object_id: "113|234",
                        attach_name: "YouTube - Việt Nam Idol 2010 - Cô nàng tự tin nhất VNidol 2010",
                        attach_href: "blacnk",
                        attach_caption: "http://www.youtube.com",
                        attach_des: "Việt Nam Idol 2010 - Cô nàng tự tin nhất VNidol 2010 lıllı ((((|̲̅̅●̲̅̅|̲̅̅=̲̅̅|̲̅̅●̲̅̅|)))) ıllı",
                        media_type:2,
                        media_img:"http://stc.quick-upload.zdn.vn/file_uploads/gallery/zingshare/2011/05/06/f/3/5/2/f352833439d504e965dfe818624901bd.jpg",
                        media_src:"http://www.youtube.com/watch?v=_5UsK-mNgiE&playnext=1&list=PLF66F466F51CCFAB4",
                        actlink_text:"từ link",
                        actlink_href:"me.zing.vn",
                        tpl_id:7,
                        comment:"hello",
                        suggestion: ["suggestion1", "suggestion2", "suggestion3"]
                    });
            }
    </script>
    <h1></h1>
    <%=MyAppInfo.getInstance().getAppName()%>
    <% 
    //BasicConfigurator.configure();
   
    //System.out.println(MyAppInfo.getInstance().getAppName());
    //ZME_Authentication zme=new ZME_Authentication(MyAppInfo.getInstance().getAppName(), MyAppInfo.getInstance().getAppKey(), MyAppInfo.getInstance().getSecretKey(), MyAppInfo.getInstance().getEnv());
    /*
    ZME_Authentication zme = new ZME_Authentication(MyAppInfo.getInstance().getAppName(), MyAppInfo.getInstance().getAppKey(), MyAppInfo.getInstance().getSecretKey(), MyAppInfo.getInstance().getEnv());
    String url=MyAppInfo.getInstance().getUrlAuthorCode(zme);
    if (request.getParameter("isRedirect") == null){
                    response.sendRedirect(url);
        }
    String authorizationCode="";
                if (request.getParameter("code") == null) {
                    out.println("Please enter your name.");
                } else {
                    //out.println("Hello <b>"+request.getParameter(i)+"</b>!");
                    authorizationCode=request.getParameter("code");
                }
    */
    

    
        
        %>
    <div id="logined">
<style>
    .disabledButton{
        
    }
</style>
    <div>Ban dang xem chu de:  <span id="statusTag"></span></div>
    <div>
    <div><textarea id="ContentItem" cols="100" rows="3" ></textarea></div>
    <div>Tag lien quan:<div id="listTag"></div></div>
    <div><a href="#" id="getItem">Suy Nghi</a></div>
    <div>
        <span><a name="backItem" id="backItem" href="javascript:backItems();">Back Items</a>     
            <a name="nextItem" id="nextItem" href="javascript:nextItems();" disabled="true">Next Items</a></span>
    </div>
    <div>
        <div id="listTopTags">            
        </div>
    </div>
    <div><a href="javascript:feedItem();">Feed Item</a></div>
    </div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.js"></script>
        <script type="text/javascript" src="http://json-sans-eval.googlecode.com/svn/trunk/src/json_sans_eval.js"></script>
        <script type="text/javascript" src="http://fresher2012.dev/cachingfolder/listtags.js">
        
            //var mylisTags = jsonParse(listTags);                        
        </script>
        <script>
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
                //alert($currentTagID);
                $(document).ready(function(){                
                    $.post("random", {tagID:$currentTagID}, function(data) {	
                            var myJsonObj = jsonParse(data);
                            addItemsToQueue(myJsonObj);
                           // $("#statusTag").html(data);
                           // alert(data);
                            $("#ContentItem").html(myJsonObj.content); 
                            var listTagsID=myJsonObj.tagsID.toString().split(",");
                            //var listTagsID=listTagsID1.split(",");
                            var lTags="";
                            for (var k=0;k< listTagsID.length;k++) {
                            lTags+="<a href='javascript:getRandomItemOfTag("+listTagsID[k]+");' rel='"+listTagsID[k]+"'>"+getTagName(listTagsID[k].toString())+"</a>     ";
                            }                            
                           // alert(lTags);
                          $("#listTag").html(lTags)
			});
                });
                }
                //next - back button
                var queueItem=new Array();
                var currentIndex=0;
                function backItems(){
                    var item=new Array();
                    if(currentIndex>0) currentIndex--;
                    else currentIndex=0;
                    var index=queueItem.length;
                    if(index>=0 && currentIndex>=0 && currentIndex<=index){
                        item=queueItem[currentIndex-1];
                    }
                    else{
                        
                    }
                    showItemBackNext(item);
                }
                function nextItems(){
                    var item=new Array();
                    if(currentIndex<queueItem.length) currentIndex++;
                    else{
                        return;
                    }
                    var index=queueItem.length;
                    if(index>=0 && currentIndex>=0 && currentIndex<=index){
                        item=queueItem[currentIndex];
                    }
                    else{         
                        return;
                    }
                    showItemBackNext(item);
                }
                function addItemsToQueue(_item){
                    var item=new Array();
                    //var tagsId=new Array();                            
                    item[0]=_item.itemID;
                    item[1]=_item.content;
                    item[2]=_item.tagsID.toString().split(",");                                                                                    
                    var index=queueItem.length;
                    if(index<11){                        
                        queueItem[index]=item;
                        currentIndex=queueItem.length;
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
                        var aTagsID=item[2];
                        var lTags=""; 
                            for (var k=0;k< aTagsID.length;k++) {
                            lTags+="<a href='javascript:getRandomItemOfTag("+aTagsID[k]+");' rel='"+aTagsID[k]+"'>"+getTagName(aTagsID[k].toString())+"</a>     ";
                            }                            
                          $("#listTag").html(lTags);
                    });
                    //turnOnOffBackNext();
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
                function feedItem(){
                    zmf.ui(
                    {
                        pub_key:"2dd6f7f390c7c2ac5d8c70f243cecdcb",
                        sign_key:"ee89b21d46ec1b2b44ca784b12000ecb",
                        action_key: "created by app",
                        action_id:1,
                        uid_to: 5037964,
                        object_id: "",
                        attach_name: "Ca sĩ Quang Hà chuẩn bị kiện nhân chứng Vietnam Airlines",
                        attach_href: "http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                        attach_caption: "vnexpress.net",
                        attach_des: "Nhận được tin nhắn xin lỗi được cho là của bà Eileen Tan - nhân chứng trong vụ HLV Taekwondo, nhưng ca sĩ Quang Hà vẫn quyết đưa việc này ra tòa.",
                        media_type:1,
                        media_img:"http://vnexpress.net/Files/Subject/3b/a2/99/39/quang-ha-250.jpg.thumb150x0.ns.jpg",
                        media_src:"http://vnexpress.net/gl/kinh-doanh/2011/05/ca-si-quang-ha-chuan-bi-kien-nhan-chung-vietnam-airlines/",
                        actlink_text:"Link",
                        actlink_href:"http://me.zing.vn/apps/link",
                        tpl_id:3,
                        suggestion: ["suggestion1", "suggestion2", "suggestion3"]
                    });
                }
        </script>
	<script type="text/javascript">
	$(document).ready(function() {            
            turnOnOffBackNext();
            //get ramdom item when loading page
            $.post("random", {alfa:123}, function(data) {	
                            var myJsonObj = jsonParse(data);                            
                            $("#ContentItem").html(myJsonObj.content); 
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
                            var myJsonObj = jsonParse(data);
                            var lTags="";
                            for (var prop in myJsonObj) {
                                if (myJsonObj.hasOwnProperty(prop)) {
                                    lTags+="<a href='javascript:getRandomItemOfTag("+myJsonObj[prop].tagID+");' rel='"+myJsonObj[prop].tagID+"'>"+myJsonObj[prop].tagName+"</a>     ";                                    
                                }
                            }
                            
                                                   
                           // alert(lTags);
                          $("#listTopTags").html(lTags);
			});
                        
                  //
                        
		$("#getItem").click(function(){
                    
			$.post("random", {alfa:123}, function(data) {	
                           // $("#testdata").html(data);
                            var myJsonObj = jsonParse(data);
                           // alert(data);
                           addItemsToQueue(myJsonObj);
                           
                            $("#ContentItem").html(myJsonObj.content); 
                            var listTagsID=myJsonObj.tagsID.toString().split(",");
                            //var listTagsID=listTagsID1.split(",");
                            var lTags="";
                            for (var k=0;k< listTagsID.length;k++) {
                            lTags+="<a href='javascript:getRandomItemOfTag("+listTagsID[k]+");' rel='"+listTagsID[k]+"'>"+getTagName(listTagsID[k].toString())+"</a>     ";
                            }                            
                           // alert(lTags);
                          $("#listTag").html(lTags)
			});
		});                               
	});
	</script>
</div

    <div>
        
    </div>
</body>
</html>