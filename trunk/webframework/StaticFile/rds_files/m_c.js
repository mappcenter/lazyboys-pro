var apps={},pwg={purl:pml.url,plink:"",aid:"",hSrc:"",imgObj:"#mimg",bootmap:[],bStop:0,lsv:"",ild:!1,iss:!1,fStart:function(){},fClose:function(){},errorWC:function(){},init:function(){apps=zm.extend(apps,{viewerName:zmConfig.viewerName,viewerId:zmConfig.viewerId,viewerFullName:"undefined"!=typeof zmConfig.viewerFullName?zmConfig.viewerFullName:zm("#fullName_"+zmConfig.viewerId).text()});m_upload.init();ptp.init();pwg.initBox();mshare.init()},eventBox:function(){var a=zm.browser.msie?"mousedown":
"click";0<arguments.length?zm(document).unbind(a,pwg.zmeBox):zm(document).bind(a,pwg.zmeBox)},offBubble:function(a){null!=a&&(a.stopPropagation&&a.stopPropagation(),a.cancelBubble=!0)},offDefault:function(a){a.preventDefault&&a.preventDefault();a.returnValue=!1},zmeBox:function(a){function b(){return null!=e&&-1!=e.indexOf("/apps/photo?")?(-1!=e.indexOf("/tagged")?h="/t/t":-1!=e.indexOf("/liked")&&(h="/l/l"),!0):!1}function c(){e=d.getAttribute("href");return b()?!0:!1}function f(){return null!=d.parentNode&&
(d=d.parentNode,null!=d.parentNode&&void 0!=d.parentNode.style)?(i=d.parentNode.style.backgroundImage.replace("url(","").replace(")","").replace(/("|')/gi,""),c()):!1}var d=a.target||a.srcElement,e,h="",i="";if(function(){var a=d.getAttribute("src");null!=d.style.backgroundImage&&""!=d.style.backgroundImage&&(a=d.style.backgroundImage.replace("url(","").replace(")","").replace(/("|')/gi,""));if(null!=a&&(-1!=a.search("photo.zdn.vn")||-1!=a.search("ugc.zdn.vn"))){var c=d.parentNode;if(null!=c&&(e=
c.getAttribute("href"),null==e&&(c=c.parentNode,e=c.getAttribute("href")),b()))return i=a,!0}return!1}()||c()||f()||f()||f()){var g=e.match(/=(.*?)\/album\/(my\-album|photo\-detail)\/id\/(\d{1,})(.*)/i);if(null!=g&&2>a.button&&!a.ctrlKey&&!a.shiftKey)if(pwg.sLd(d),pwg.sub="","undefined"!=typeof g[4]&&(pwg.sub=g[4]),g[1]=g[1].replace("/dt/","/").replace("/apps/photo","").replace("/",""),"my-album"==g[2])if(""!=i)pwg.rdBox({un:g[1],aid:"/aid/aid"+h,al:e,pid:g[3],ps:i}),pwg.offDefault(a);else return pwg.offDefault(a),
top.location.href=apps.meAppUrl+"/albumdetail/"+g[1]+"/"+g[3];else pwg.rdBox({un:g[1],aid:""+h,al:e,pid:g[3],ps:i}),pwg.offDefault(a)}},sLd:function(a){function b(a){"IMG"==a.attr("nodeName")&&(e=!0);if(0<a.length){var b=a.height(),g=a.width();if(90<b&&b<f||90>f)f=b,d=a.offset(!0);if(90<g&&g<c||90>c)c=g;return!0}return!1}var a=zm(a),c=a.width(),f=a.height(),d=a.offset(!0),e=!1;"IMG"==a.attr("nodeName")&&(e=!0);b(a.children())||(b(a.parent()),b(a.parent().parent()));e&&pwg.sldObj.css({left:d.left+
"px",top:d.top+"px",width:c+"px",height:f+"px"}).show()},rdBox:function(a){pwg.ild||(pwg.ild=!0,"undefined"!=typeof a.sub&&(pwg.sub=a.sub),zm.extend(apps,{ownerName:a.un}),pwg.aid=a.aid,pwg.plink=a.al,pwg.hSrc=a.ps,pwg.initObj(a.pid),pwg.iss=!0)},closeBox:function(a){zm("#zmebox").hide();zm.browser.msie&&(document.getElementsByTagName("html")[0].style.overflow="auto");zm(document.body).removeClass("boxvc");pwg.resetBox();pwg.offBubble(a);pwg.fClose.call(this);pwg.eventBox();pwg.ild=!1;pwg.iss=!1},
resetBox:function(){zm("#zmefm").show();zm("#zmefe").hide()},moveBase:function(){pwg.sldObj.hide();zm("#zmebox").show();zm.browser.msie&&(document.getElementsByTagName("html")[0].style.overflow="hidden");zm(document.body).addClass("boxvc")},initObj:function(a){pwg.loadInitWg(a);""!=pwg.hSrc&&pwg.imgObj.attr("src",pwg.hSrc.replace("_130_130","_574_574").replace("t.f","d.f").replace("_320_320","_574_574").replace("m.f","d.f"));zm.browser.msie&&pwg.moveBase();pwg.ctFox(-1)},initBoot:function(a){if("undefined"==
typeof pnav){pwg.bStop=a.length;for(var b in a)zm.addScript(apps.staticUrl+"/js/"+a[b],pwg.callBoot)}pwg.callExeBoot()},callBoot:function(){pwg.bStop--},callExeBoot:function(){if(0!=pwg.bStop)return setTimeout(pwg.callExeBoot,1);for(var a in pwg.bootmap)eval(pwg.bootmap[a]);pwg.fStart.call(this)},loadInitWg:function(a,b){zm.get(apps.appsPhotoUrl+"/"+apps.ownerName+"/zme/getinitseg/pid/"+a+pwg.aid+(b||"")+"/tk/"+apps.token+pwg.sub,{dataType:"json"},pwg.cbnitWg,pwg.errorWC);pwg.mkZwg()},cbnitWg:function(a){if(a.err)3==
a.err?pwg.sPw(a.msg):pwg.sPri();else{if(""!=a.pri)return pwg.sPri();zm.extend(apps,a.isFF);pwg.bootmap=a.onboot;pwg.initBoot(a.boot)}},sPri:function(){pwg.moveBase();zm("#zmebase").css("height","auto");var a=zm("#fe_tpl").html().replace(/#PRIUN#/,apps.ownerName).replace(/##/,apps.viewerId);zm("#zmefe").html(a).show();zm("#zmefm").hide();zm("#zmebase").css({height:"auto",width:"900px"});zm("#priun"+apps.viewerId).attr("rel_f","");zwg.addWgItem(new wgItem("priun"+apps.viewerId,"ZMED_"+apps.ownerName));
zwg.fillWg()},sPw:function(a){pwg.moveBase();"undefined"!=typeof a?(zm("#zmefe").html(a).show(),zm("#zmefm").hide(),zm("#zmebase").css({height:"auto",width:"900px"})):(zm("#zmefe").html("").hide(),zm("#zmefm").show())},lpwg:function(){var a=zm("#lpass");if(""==a.val())return zm.Boxy.alert(a.attr("r"),"Th\u00f4ng b\u00e1o");var b=zm("#lcapt");if(""==b.val())return zm.Boxy.alert(b.attr("r"),"Th\u00f4ng b\u00e1o");a=apps.appsPhotoUrl+"/"+apps.ownerName+"/zme/lpass/id/"+b.attr("a")+"/ctk/"+b.attr("tk")+
"/c/"+b.val()+"/p/"+a.val()+"/tk/"+apps.token+"/v/"+(new Date).getTime();zm.get(a,{dataType:"json"},pwg.cblpwg,pwg.errorWC)},cblpwg:function(a){return!a.err?(a=zm("#lcapt").attr("p"),pwg.sPw(),pwg.loadInitWg(a,"/v/"+(new Date).getTime())):zm.Boxy.alert(a.msg,"Th\u00f4ng b\u00e1o")},ctFox:function(a){0<a?pwg.ctid=a:"undefined"!=typeof pwg.ctid&&(pwg.pc=void 0,(new Image).src=apps.appsPhotoUrl+"//zme/rtagbox/pid/"+pwg.ctid+"/tk/"+apps.token,zm("#photo_counter").remove())},ctBox:function(){if("undefined"!=
pml.r.c&&0<pml.r.c){var a=zm("#photo_counter");a.html(pml.r.c).attr("title","");"undefined"!=typeof zmConfig&&"app/photo"==zmConfig.pageName?(zm("#photom").prepend(a[0]),a.remove(),zm("#photo_counter").show()):a.show();var b=function(){if(typeof pwg.pc!="undefined"){clearTimeout(pwg.pcTime);pwg.iTag=false;var a=zm("#photo_counter"),d=a.offset();if(zm("#photo_tags").length==0){zm("#www-warp").after(pwg.pc);zm("#photo_tags").hover(function(){clearTimeout(pwg.pcTime);pwg.iTag=true},function(){pwg.iTag=
false;c()})}var e=document.documentElement.scrollTop||document.body.scrollTop;pwg.pcTime=setTimeout(function(){zm("#photo_tags").css({left:d.left+a.outerWidth()+"px",top:d.top-a.outerHeight()+e+"px"}).show()},800)}else zm.get(pwg.purl+"//zme/tagbox/n/"+pml.r.c,{dataType:"json"},function(a){pwg.pc=a.h;b()})},c=function(){if(!pwg.iTag){clearTimeout(pwg.pcTime);pwg.pcTime=setTimeout(function(){zm("#photo_tags").fadeOut()},500)}};zm("#photo_counter").hover(function(){b()},function(){c()})}},sdBox:function(){if("undefined"!=
typeof localStorage){pwg.lsv=pml.r.n;var a=localStorage.getItem(pwg.lsv);if(null!=a){if(a=zm.parseJSON(a),"undefined"!=typeof a.ss&&"undefined"!=typeof a.ss.vid&&a.ss.vid==apps.viewerId)return pwg.cbnitBox(a)}else if(a=localStorage.getItem(pml.r.o),null!=a&&(localStorage.removeItem(pml.r.o),a=zm.parseJSON(a),"undefined"!=typeof a.ss&&"undefined"!=typeof a.ss.vid&&a.ss.vid==apps.viewerId))return pwg.cbnitBox(a)}zm.get(pwg.purl+"//zme/initzme",pwg.cbnitBox,pwg.errorWC)},initBox:function(){pwg.ctBox();
pwg.sdBox()},lCss:function(a){var b=document.createElement("link");b.setAttribute("type","text/css");b.setAttribute("rel","stylesheet");b.setAttribute("media","screen");b.setAttribute("href",a);document.getElementsByTagName("head")[0].appendChild(b)},cbnitBox:function(a){if("object"!=typeof a)var b=a,a=zm.parseJSON(a);if(!a.err){apps=zm.extend(apps,a.ss);pwg.lCss(apps.staticUrl+"/css/zmebox."+pml.r.s+".css");if(zm.browser.msie&&8>parseInt(zm.browser.version))return!1;zm("#www-warp").after(a.h);zm("#zmeboxb").bind("click",
pwg.closeBox).bind("mouseenter",function(){zm("#closetip").show()}).bind("mouseleave",function(){zm("#closetip").hide()});pwg.eventBox();"undefined"!=typeof zmXCall&&(zmXCall.register("appZBox",function(a){pwg.rdBox(a)}),"pushState"in window.history&&(zmXCall.register("appZPush",function(a){window.history.pushState(a.p1,a.p2,a.p3)}),window.onmessage=function(a){-1!=a.data.indexOf("zme")&&(pwg.fc=a.source)},window.onpopstate=function(a){"undefined"!=typeof pwg.fc&&pwg.fc.postMessage(zm.serialize(a.state),
"*")}));zm("#zmebox").bind("click",function(a){(a.target||a.srcElement).getAttribute("id")=="zmearea"&&pwg.closeBox(a)});pwg.imgObj=zm(pwg.imgObj);zm(document.body).append('<div style="position:absolute;left:0;top:0;z-index:801;background:url('+apps.staticUrl+'/images/load_small.gif) center center no-repeat rgba(0,0,0,0.4);display:none" id="sld"></div>');pwg.sldObj=zm("#sld");""!=pwg.lsv&&"undefined"!=typeof b&&localStorage.setItem(pwg.lsv,b)}},fAbl:function(a){zm("#a_username").attr("rel_f","");
zm("#aa_username").attr("rel_f","");zm("#cad_username").attr("rel_f","");zwg.addWgItem(new wgItem("a_username","ZMED_"+a+"?l=2"));zwg.addWgItem(new wgItem("aa_username","ZMEA_"+a+"?l=2&width=42&height=42"));zwg.addWgItem(new wgItem("cad_username","ZMED_"+a+"?l=2"));zm("#dsg1").attr("rel_f","");zm("#dsg2").attr("rel_f","");zwg.addWgItem(new wgItem("dsg1","ZMED_"+a+"?l=2&n=f&c=5&vi=0"));zwg.addWgItem(new wgItem("dsg2","ZMED_"+a+"?l=2&n=f&c=5&vi=0"))},fAct:function(a){zwg.addWgItem(new wgItem("CMA_un",
"ZMEA_"+a+"?l=2&width=42&height=42"));zwg.addWgItem(new wgItem("CMD_un","ZMED_"+a+"?l=2"));zwg.addWgItem(new wgItem("CMA2_un","ZMEA_"+a+"?l=2&width=42&height=42"));zwg.addWgItem(new wgItem("SHAREA_un","ZMEA_"+a+"?l=2&width=50&height=50"))},mkZwg:function(){pwg.fAbl(apps.ownerName);pwg.fAct(apps.viewerName);zwg.fillWg()}},m_upload={init:function(){zm("#stbox_createalbum").bind("click",m_upload.start);zm("#actbox_photo").bind("click",m_upload.start);zm("#puploadpt").bind("click",m_upload.start);"undefined"==
typeof upload&&zm("#jpt_ups").bind("click",m_upload.start);"undefined"!=typeof zmXCall&&zmXCall.register("appZUpload",function(a){m_upload.start(a)});m_upload.objScroll=zm.browser.chrome||zm.browser.safari?document.body:document.compatMode&&"BackCompat"!=document.compatMode?document.documentElement:document.body},close:function(){zm("#mupload").remove();pwg.ild=!1;zm("#www-warp").css({width:"",position:""});m_upload.objScroll.scrollTop=m_upload.offTop},start:function(a){if(zm.browser.msie&&8>parseInt(zm.browser.version)||
zm.browser.firefox&&4>parseInt(zm.browser.version))return top.location.href=apps.meAppUrl+"/upload";"undefined"==typeof a.cb&&pwg.offDefault(a);if("undefined"==typeof swfobject)return zm.addScript(zmConfig.IMAGE_URL+"/v3/js/swfobject.js",function(){m_upload.startCb(a)});m_upload.startCb(a)},chkFileApi:function(){return(window.XMLHttpRequest||window.XDomainRequest)&&(window.FormData||window.FileReader&&(window.XMLHttpRequest&&XMLHttpRequest.sendAsBinary||window.ArrayBuffer&&window.Uint8Array&&(window.MozBlobBuilder||
window.WebKitBlobBuilder||window.BlobBuilder)))},eUps:function(a){m_upload.offTop=m_upload.objScroll.scrollTop;zm("#www-warp").after(a).css({width:"100%",position:"fixed",top:-m_upload.offTop+10+"px"});m_upload.objScroll.scrollTop=0;zm("#wzmeboxb").bind("click",m_upload.close).bind("mouseenter",function(){zm("#wclosetip").show()}).bind("mouseleave",function(){zm("#wclosetip").hide()})},startCb:function(a){function b(){e=(new Date).getTime()-e;20>=e&&""!=d&&(f="html5");var a=window.location.href.match(/uptype=([0-9a-z]+)/i);
null!=a&&(f=a[1]);zm.get(apps.appsPhotoUrl+"//zme/uploadwg/type/"+f+"/from/"+c,{dataType:"json"},function(a){m_upload.h=a.h;m_upload.eUps(a.h);"undefined"!=typeof upload?m_upload.t!=f?zm.addScript(apps.staticUrl+"/js/"+a.j):upload.init():(apps=zm.extend(apps,a.ss),zm.addScript(apps.staticUrl+"/js/"+a.j));m_upload.t=f})}if("undefined"!=typeof upload&&"undefined"!=typeof zmConfig.pageName&&"photojpt"!=zmConfig.pageName)return m_upload.eUps(m_upload.h),upload.init();var c="photo";"undefined"==typeof a.cb?
(a=a.target||a.srcElement,null==a.getAttribute("href")&&(a=a.parentNode),c=a.getAttribute("href").replace(/.*?from\=/,"")):"undefined"!=typeof zmConfig.pageName&&(c=zmConfig.pageName.replace("/","_"),"undefined"!=typeof a.from&&(c=a.from),m_upload.cb=a.cb,"undefined"!=typeof a.fcb&&(m_upload.fcb=a.fcb),"undefined"!=typeof a.aid&&(c+="/aid/"+a.aid));if(!pwg.ild){pwg.ild=!0;var f="flash",d="";void 0!=m_upload.chkFileApi()&&!zm.browser.msie&&(d="/has5/1");10>swfobject.getFlashPlayerVersion().major&&
(f=""!=d?"html5":"html");var e=(new Date).getTime();""!=d?zm.get(apps.appsPhotoUrl+"//zme/ping",b):b()}}},ptp={init:function(){"undefined"!=typeof zmXCall&&zmXCall.register("appZPoint",function(a){ptp.show(a.p)})},show:function(a){function b(){c.css({top:h+"px",left:f-g+"px"});h-=d;h<i?setTimeout(function(){c.fadeOut()},500):ptp.tt=setTimeout(b,e)}if(""!=a){"undefined"!=typeof ptp.tt&&clearTimeout(ptp.tt);var c=zm("#infopt");c.html(a).show();var a=zm.getViewport(),f=zm(document.body).outerWidth(),
d=1,e=5,h=a.height,i=2*h/3,g=c.outerWidth();b()}}},pnf={showPhoto:function(a,b,c,f,d){pnf.type="a"==f?"album":"photo";pnf.iid=c;pnf.opts=zm.extend({dboxClass:"disbox_stt_quickview",noPad:!0,width:532},d);pnf.load(a)},load:function(a,b){zm.get(apps.appsPhotoUrl+"//zme/notifybox/iid/"+pnf.iid+"/type/"+pnf.type+(b||"")+"/tk/"+apps.token,{dataType:"json"},function(b){b.err?zm(a).showDisplaybox(b.h,pnf.opts):(pnf.iid=b.iid,zm.extend(apps,b.isFF),pnf.opts=zm.extend(pnf.opts,{footer:b.f,afterShow:function(){pnf.initNF(b.t)},
noPad:!0}),zm(a).showDisplaybox(b.h,pnf.opts),"undefined"==typeof cmf&&zm.addScript(apps.staticUrl+"/js/"+b.j))})},fill:function(a){var b=zm("#nf_date");b.html(zmDateTime.format(b.html()));zwg.addWgItem(new wgItem("anf_u","ZMEA_"+a+"?id=1&l=2&width=50&height=50"));zwg.addWgItem(new wgItem("dnf_u","ZMED_"+a+"?id=1&l=2"));zwg.addWgItem(new wgItem("dnf_au","ZMED_"+a+"?id=1&l=2"));zwg.addWgItem(new wgItem("dnf_u2","ZMED_"+a+"?id=1&l=2"));zwg.addWgItem(new wgItem("anf_c","ZMEA_"+apps.viewerId+"?id=1&l=2&width=30&height=30"));
zwg.addWgItem(new wgItem("anf_uc","ZMEA_"+apps.viewerId+"?id=1&l=2&width=30&height=30"));zwg.addWgItem(new wgItem("dnf_uc","ZMED_"+apps.viewerId+"?id=1&l=2"));zwg.fillWg()},initNF:function(a){if("undefined"==typeof cmf)return setTimeout(function(){pnf.initNF(a)},1);pnf.fill(a.uid);cmf.eventCM("_"+pnf.iid);vtf.eventVT("_"+pnf.iid);cmf.initCM(pnf.iid,pnf.type,a.cm);vtf.initVT(pnf.iid,pnf.type,a.vt)}},mshare={init:function(){mshare.ipp=!1;"undefined"!=typeof zmXCall&&zmXCall.register("appZshare",function(a){mshare.ipp=
!0;mshare.getShare(a.id,a.type)})},getShare:function(a,b){var c=apps.appsPhotoUrl+"//iaction/embed/tk/"+apps.token+"?"+zm.param({iid:a,t:b,from:mshare.ipp?"app":"jpt"});zm.get(c,{dataType:"json"},mshare.getShareCb)},getShareCb:function(a){a.err||(zm.Boxy.alert(a.h,"Chia s\u1ebb "+a.t+" n\u00e0y v\u1edbi b\u1ea1n b\u00e8",!1,{okButton:"\u0110\u00f3ng",contentClass:"lbx_widmid"}),(0<apps.viewerId||pwg.iss)&&zm("#zingme_b").bind("click",mshare.shareE),a=function(){0==zm("#at15s").length&&(addthis.addEventListener("addthis.menu.close",
function(){zm("#at16lb").hide();zm("#at15s").hide()}),window.addthis_caption_share="C\u1ed9ng \u0111\u1ed3ng Zing Me Photo");ado=zm("#addthis_b");window.addthis.ost=0;window.addthis.update("share","url",ado.attr("url"));window.addthis.update("share","title",ado.attr("t"));ado.bind("click",function(){addthis_sendto("more")})},"undefined"!=typeof zmConfig.pageName&&"photojpt"==zmConfig.pageName||mshare.ipp?"undefined"==typeof addthis?zm.addScript("http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-5029d22b7a5289ec",
a):a():zm("#addthis_b").remove(),mshare.ipp=!1)},shareE:function(a){a.preventDefault&&a.preventDefault();a.returnValue=!1;zm.Boxy.stopAlert();if(pwg.iss)return share.share();if("undefined"!=typeof jshare)return jshare.share();"undefined"==typeof share&&(window.share={shareBlur:function(a){""==a.value&&(a.value=a.getAttribute("df"))},shareFocus:function(a){a.value==a.getAttribute("df")&&(a.value="")}});a=zm(this);mshare.share({iid:a.attr("i"),name:a.attr("n"),url:a.attr("u"),user_name:a.attr("o"),
ctype:a.attr("t")})},share:function(a){var b=zm("#share_tpl").html().replace(/##/,"").replace(/#SHARE_IMG#/,a.url.replace(".m",".t").replace("_320_320","_130_130")).replace(/#SHARE_NAME#/,a.name).replace(/#SHARE_ID#/g,a.iid);zm.Boxy.confirm(b,function(){mshare.shareCf(a)},{cancelButton:"H\u1ee7y b\u1ecf",okButton:"Chia s\u1ebb",title:"Chia s\u1ebb v\u1edbi b\u1ea1n b\u00e8",contentClass:"lbx_widmid"});zwg.addWgItem(new wgItem("SHAREA_un","ZMEA_"+apps.viewerName+"?l=2&width=50&height=50"));zwg.addWgItem(new wgItem("shareD"+
a.iid,"ZMED_"+a.user_name+"?l=2"));b=zm("#share_txt"+a.iid);b.val(b.attr("df"));zwg.fillWg()},shareCf:function(a){var b=zm("#share_txt"+a.iid),c=b.val(),c=c==b.attr("df")?"":c,a=apps.appsPhotoUrl+"//iaction/share/tk/"+apps.token+"?"+zm.param({iid:a.iid,t:a.ctype,msg:c});(new Image).src=a;zm.Boxy.alert("Chia s\u1ebb th\u00e0nh c\u00f4ng.","Th\u00f4ng b\u00e1o",!1,{okButton:"\u0110\u00f3ng"});setTimeout(zm.Boxy.stopAlert,1E3)}};pwg.init();
