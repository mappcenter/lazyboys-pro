var zmLightBox={DEF_IFFILTER_VAL:"Nhập tên cần tìm",pokefriend:function(a,b){zm.post(a,{},{dataType:"json",timeout:5e3},function(a){switch(a.type){case"confirm":zm.Boxy.confirm(a.content,function(){var a=zmLightBox.checkMessageContent();if(a)zmLightBox.submitPokeFriend();else return false},{title:b,okButton:b,cancelButton:"Đóng",contentClass:"lbx_widmid"});break;case"alert":zm.Boxy.alert(a.content,b,4e3);break;default:break}},function(a,b){zm.Boxy.alert("Có lỗi xảy ra, vui lòng nhấn F5 để refresh trang","Thông báo")})},submitPokeFriend:function(){var a=zm("#cboPoke").val();var b=zm("#chkview").val();var c=zm("#url").val();zm.post(c,{cboPoke:a,chkview:b,isPost:true},{dataType:"json",timeout:5e3},function(a){zm.Boxy.alert(a.content,"Chọc ghẹo")})},checkMessageContent:function(){if(zm("#content").val()!=null&&zm.trim(zm("#content").val())==""){zm.Boxy.alert("Vui lòng nhập nội dung tin nhắn!","Thông Báo","2000");return false}else return true},subcribe:function(a,b,c){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert('Bạn vui lòng <a href="http://login.me.zing.vn?ref='+location.href+'">Đăng nhập</a> để thực hiện chức năng này.',"Thông báo");return false}zm.post(a,{},{dataType:"json",timeout:5e3},function(a){if(a.status=="success"){zm(b).html(a.content);if(c!="viplist"){if(a.from=="fp")window.location=a.link;else window.location=zmConfig.PROFILE_URL+"/"+zmConfig.ownerName}}else{zm.Boxy.alert(a.content,a.title,4e3)}},function(a,b){zm.Boxy.alert("Có lỗi xảy ra, vui lòng nhấn F5 để refresh trang","Thông báo")})},deleteSubscribe:function(a){var b=zm(a).attr("rel");zm.get(b,{dataType:"json"},function(b){if(b.error==0){zm(a).html("<em>"+b.msg+"</em>")}else{zm.Boxy.alert('<div style="width:200px">'+b.msg+"</div>")}})},reportAbuse:function(a,b){zm.get(a,{dataType:"json"},function(a){if(a.type=="confirm"){zm.Boxy.confirm(a.content,function(){zmLightBox.acceptReportAbuse()},{title:b,okButton:"Gởi",cancelButton:"Đóng",contentClass:"lbx_widmid"})}else{zm.Boxy.alert(a.content,"",4e3)}})},acceptReportAbuse:function(){var a="";zm("[name=abusetype]").each(function(){if(this.checked){a+=zm(this).val()+","}});var b=zm("#txtabusecontent").val(),c=zm("#frmreport").attr("action");zm.post(c,{content:b,abusetype:a,isPost:true},{dataType:"json"},function(a){zm.Boxy.alert(a.content,"",4e3)})},introFriend:function(a,b){zm.post(a,{},{dataType:"json",timeout:5e3},function(a){switch(a.type){case"confirm":if(!zmLightBox.ifBoxy){zmLightBox.ifBoxy=new zm.Boxy({title:b,okButton:b,content:'<div class="fl"><p class="font12 mbt10"><input type="checkbox" class="checkbox checkBR" id="ifcheckall"><span class="marl5"><label for="ifcheckall"> Chọn tất cả</label> <span id="iftotalcheck"></span></span></p>\n</div><div style="position: relative;" class="search-fr"><input  class="sharefrd" type="text" value="Nhập tên cần tìm" id="iffilter"><a href=""></a><img style="display: none; position: relative; right: 15px;" src="http://img.me.zdn.vn/v3/images/loadingi.gif" id="ifloading"></div><div class="clr"></div>'+a.content,cancelButton:"Đóng",contentClass:"lbx_widlar",onOk:function(){return zmLightBox.submitIntroFriend()}})}else{zmLightBox.ifBoxy.setContent('<div class="fl"><p class="font12 mbt10"><input type="checkbox" class="checkbox checkBR" id="ifcheckall"><span class="marl5"><label for="ifcheckall"> Chọn tất cả</label> <span id="iftotalcheck"></span></span></p>\n</div><div style="position: relative;" class="search-fr"><input  class="sharefrd" type="text" value="Nhập tên cần tìm" id="iffilter"><a href=""></a><img style="display: none; position: relative; right: 15px;" src="http://img.me.zdn.vn/v3/images/loadingi.gif" id="ifloading"></div><div class="clr"></div>'+a.content)}zm("#iffilter").click(function(){this.focus()}).focus(function(){if(this.value==zmLightBox.DEF_IFFILTER_VAL)this.value=""}).blur(function(){if(!this.value)this.value=zmLightBox.DEF_IFFILTER_VAL}).keyup(function(a){if(zmLightBox.ifFilterTimeout)clearTimeout(zmLightBox.ifFilterTimeout);var b=a.keyCode==27;zmLightBox.ifFilterTimeout=setTimeout(function(){zmLightBox.filterIntroFriend(b)},400)});zm("#ifcheckall").click(function(){var a=zm("#lsFID").css("display")!="none"?zm("#lsFID li .checked"):zm("#lsFIDF li .checked");if(this.checked){a.show();zm("#iftotalcheck").html("| Đang chọn <strong>"+a.size()+"</strong> người")}else{a.hide();zm("#iftotalcheck").html("")}});zmLightBox.ifBoxy.show();break;case"alert":zm.Boxy.alert(a.content,a.title,4e3);break;default:break}},function(a){zm.Boxy.alert("Có lỗi xảy ra, vui lòng nhấn F5 để refresh trang","Thông báo")})},introFriendApp:function(a,b,c,d,e){zm.post(a,{apl:c,apn:d,apt:e},{dataType:"json",timeout:5e3},function(a){switch(a.type){case"confirm":if(!zmLightBox.ifBoxy){zmLightBox.ifBoxy=new zm.Boxy({title:b,okButton:b,content:'<div class="fl"><p class="font12 mbt10"><input type="checkbox" class="checkbox checkBR" id="ifcheckall"><span class="marl5"><label for="ifcheckall"> Chọn tất cả</label> <span id="iftotalcheck"></span></span></p>\n</div><div style="position: relative;" class="search-fr"><input  class="sharefrd" type="text" value="Nhập tên cần tìm" id="iffilter"><a href=""></a><img style="display: none; position: relative; right: 15px;" src="http://img.me.zdn.vn/v3/images/loadingi.gif" id="ifloading"></div><div class="clr"></div>'+a.content,cancelButton:"Đóng",contentClass:"lbx_widlar",onOk:function(){return zmLightBox.submitIntroFriend()}})}else{zmLightBox.ifBoxy.setContent('<div class="fl"><p class="font12 mbt10"><input type="checkbox" class="checkbox checkBR" id="ifcheckall"><span class="marl5"><label for="ifcheckall"> Chọn tất cả</label> <span id="iftotalcheck"></span></span></p>\n</div><div style="position: relative;" class="search-fr"><input  class="sharefrd" type="text" value="Nhập tên cần tìm" id="iffilter"><a href=""></a><img style="display: none; position: relative; right: 15px;" src="http://img.me.zdn.vn/v3/images/loadingi.gif" id="ifloading"></div><div class="clr"></div>'+a.content)}zm("#iffilter").click(function(){this.focus()}).focus(function(){if(this.value==zmLightBox.DEF_IFFILTER_VAL)this.value=""}).blur(function(){if(!this.value)this.value=zmLightBox.DEF_IFFILTER_VAL}).keyup(function(a){if(zmLightBox.ifFilterTimeout)clearTimeout(zmLightBox.ifFilterTimeout);var b=a.keyCode==27;zmLightBox.ifFilterTimeout=setTimeout(function(){zmLightBox.filterIntroFriend(b)},400)});zm("#ifcheckall").click(function(){var a=zm("#lsFID").css("display")!="none"?zm("#lsFID li .checked"):zm("#lsFIDF li .checked");if(this.checked){a.show();zm("#iftotalcheck").html("| Đang chọn <strong>"+a.size()+"</strong> người")}else{a.hide();zm("#iftotalcheck").html("")}});zmLightBox.ifBoxy.show();break;case"alert":zm.Boxy.alert(a.content,a.title,4e3);break;default:break}},function(a){zm.Boxy.alert("Có lỗi xảy ra, vui lòng nhấn F5 để refresh trang","Thông báo")})},resetIfBoxy:function(){zm("#lsFID li .checked").hide();zm("#lsFIDF").html("");zm("#iftotalcheck").html("");zm("#ifcheckall").attr("checked",false);this.filterIntroFriend(true);zm("#iffilter").val(zmLightBox.DEF_IFFILTER_VAL)},introFriendMore:function(a){zm.post(a,{},{dataType:"json",timeout:5e3},function(a){zm("#lsFID").html(a.content);zmLightBox.filterIntroFriend()},function(){zm.Boxy.alert("Có lỗi xảy ra, vui lòng nhấn F5 để refresh trang","Thông báo")})},filterIntroFriend:function(a){var b=zm("#iffilter");if(a)b.val("");var c=zm.trim(b.val());if(c&&c!=zmLightBox.DEF_IFFILTER_VAL){var d="http://search.me.zing.vn/friend/select?fl=userid&wt=json&q=friendids:"+zmConfig.viewerId+"%20AND%20pft_fullname:"+encodeURIComponent(c)+"&start=0&rows=100&json.wrf=?";zm("#ifloading").show();zm.getJSON(d,function(a){if(a&&c==zm.trim(zm("#iffilter").val())){var b="";for(var d=0,e;e=a.response.docs[d];d++){var f=e.userid,g="ifava_"+f,h="ifn_"+f;b+='<li id="ifriend-'+f+'" onclick="zmLightBox.checkIntroFriend(this);">\n								<span class="avatar" id="'+g+'"></span>\n								<div class="user"> <span id="'+h+'"></span></div><div class="checked" style="display:none;"> </div>\n							</li>';zwg.addItem(g,"ZMEA_"+f+"?id=1&size=50&width=42&height=42&l=0");zwg.addItem(h,"ZMED_"+f+"?id=1&l=0")}if(b=="")b="Không tìm thấy bạn bè nào.";zm("#lsFID").hide();zm("#lsFIDF").html(b).show();zwg.fillWg();zm("#ifloading").hide()}})}else{zm("#lsFID").show();zm("#lsFIDF").hide()}},submitIntroFriend:function(){var a=this.getCheckedIntroFriend();if(!a){zm.Boxy.alert("Bạn chưa chọn bạn bè để chia sẻ","Thông báo");return false}var b=zm("#apn").val();var c=zm("#apt").val();var d=zm("#apl").val();var e=zm("#urlsm").val();zm.post(e,{friend:a,apn:b,apt:c,apl:d,signkey:zmConfig.signkey,time:zmConfig.time},{dataType:"json",timeout:5e3},function(a){zm.Boxy.alert(a.content,"Thông báo")});return true},getCheckedIntroFriend:function(){var a=new Array,b=zm("#lsFID").css("display")!="none"?zm("#lsFID li"):zm("#lsFIDF li");b.each(function(){var b=zm(this);if(b.css("display")!="none"){if(b.children(".checked").css("display")!="none")a.push(b.attr("id").substr(8))}});return a.join(",")},checkIntroFriend:function(a){var b=zm(a).children(".checked"),c=zm.intval(zm("#iftotalcheck strong").html());if(b.css("display")!="none"){b.hide();c--}else{b.show();c++}var d="";if(c>0)d="| Đang chọn <strong>"+c+"</strong> người";zm("#iftotalcheck").html(d);var e=zm("#lsFID").css("display")!="none"?zm("#lsFID li .checked").size():zm("#lsFIDF li .checked").size();zm("#ifcheckall").attr("checked",c==e)},init:function(){zm("#zm_ps").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=zm(this).parent().attr("rel");zmLightBox.subcribe(a,this,"");return false});zm("#idSubcribe").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=zm(this).attr("rel");zmLightBox.subcribe(a,this,"");return false});zm("#idPoke").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=this;if(a.nodeName.toLowerCase()!="a")a=a.parentNode;var b=zm(a),c=b.attr("rel"),d=b.attr("title");zmLightBox.pokefriend(c,d);return false})},acceptFriend:function(a){zm.Boxy.alert(a,"Kết bạn",4e3,{beforeHide:function(){window.location.reload()}})}};zmLightBox.init();zm.ready(function(){zm("#zm_pmf").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=zm(this).parent().attr("rel");var b=zm(this).parent().attr("source");var c=zm(this).parent().attr("h");zmFriendRequests.sendMakeFriendRequest(a,{},{check_avatar:1,h:c,source:b});return false});zm("#idMakeFriend").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=zm(this).attr("rel");var b=zm(this).attr("source");var c=zm(this).attr("h");zmFriendRequests.sendMakeFriendRequest(a,{},{check_avatar:1,h:c,source:b});return false});zm("#idAbuse").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=zm(this).attr("rel");var b=zm(this).attr("title");zmLightBox.reportAbuse(a,b);return false});zm("#idIntro").click(function(){if(parseInt(zmConfig.viewerId)<=0){zm.Boxy.alert("Bạn vui lòng đăng nhập để thực hiện chức năng này.","Thông báo");return false}var a=this;if(a.nodeName.toLowerCase()!="a")a=a.parentNode;var b=zm(a),c=b.attr("rel"),d=b.attr("title");zmLightBox.introFriend(c,d);return false});if(typeof zmPAvaSrc!="undefined"){var a=1,b,c,d,e=false;function f(){if(e)return;b=0;e=true;var f=setInterval(function(){b+=20;var h=Math.floor(c*Math.sin(b/200*(Math.PI/2))*a)+(a==1?g:d);if(h>=d)h=d;if(h<=g)h=g;zm("#avar-pf").css("height",h+"px");if(h>=d||h<=g){clearInterval(f);a*=-1;a==1?zm("#ava-zoom").removeClass("ava-zoomout"):zm("#ava-zoom").addClass("ava-zoomout");e=false}},20)}var g=300,h=new Image;h.onload=function(){d=h.height;if(d>g){zm("#avar-pf").css("height",g+"px").show();c=d-g;zm("#avar-pf").append('<a id="ava-zoom" href="#"></a>');zm("#ava-zoom").click(function(){f();return false})}else zm("#avar-pf").show()};h.src=zmPAvaSrc;zm("#avar-pf img").attr("src",zmPAvaSrc)}})