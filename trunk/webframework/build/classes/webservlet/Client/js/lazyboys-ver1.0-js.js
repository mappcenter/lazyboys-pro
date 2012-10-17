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
        if(currentIndex>0) 
            {
                currentIndex--;
                testCurrentIndex(0,currentIndex);
            }
        else currentIndex=0;
        //var index=queueItem.length;
        //if(index>=0 && currentIndex>=0 && currentIndex<=index){
            item=queueItem[currentIndex];
        //}
        //else{

        //}
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
        //var index=queueItem.length;
        //if(index>=0 && currentIndex>=0 && currentIndex<=index){
            item=queueItem[currentIndex];
        //}
        //else{         
           // return;
        //}
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
        if(index<11){                        
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

$(document).ready(function() {            
    turnOnOffBackNext();
    //get ramdom item when loading page
    $.post("http://fresher2012.dev/random", {alfa:123}, function(data) {	
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

                $.post("http://fresher2012.dev/random", {alfa:123}, function(data) {	
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