var zmEvent={subscribers:function(){if(!this._subscribersMap){this._subscribersMap={}}return this._subscribersMap},subscribe:function(b,a){var c=this.subscribers();if(!c[b]){c[b]=[a]}else{c[b].push(a)}},unsubscribe:function(b,a){var c=this.subscribers()[b];zm.each(c,function(d,e){if(e==a){c[d]=null}})},monitor:function(b,d){if(!d()){var a=this,c=function(){if(d.apply(d,arguments)){a.unsubscribe(b,c)}};this.subscribe(b,c)}},clear:function(a){delete this.subscribers()[a]},fire:function(){var b=Array.prototype.slice.call(arguments),a=b.shift();var c=this.subscribers()[a];zm.each(c,function(d,e){if(e){e.apply(this,b)}})}};var zmContent={bx_handler:null,msg:{invalid_account:"Tài khoản hoặc mật khẩu không đúng !",input_account:"Vui lòng nhập Tài khoản và Mật khẩu !",idx_login_title:"Đăng nhập với ",title_loading:"Đang tải dữ liệu",baned_account:'<div class="titloginby" align="center"><strong>Tài khoản này đã bị khóa</strong></div><p align="center">',loading:'<div class="titloginby" align="center"><strong> Đang kết nối vào hệ thống, bạn chờ tí nhé </strong></div><p align="center"><img src="http://img.me.zdn.vn/v3/images/load_small.gif" alt="" /></p>',error:'<div class="titloginby" align="center"><strong> Kết nối bị gián đoạn, vui lòng thử lại </strong></div>',bxcontent:'<div id="bxcontainer"></div>',ok:"Đồng ý",cancel:"Bỏ qua",login:"Đăng Nhập",mapping:"Đồng bộ tài khoản Zing ID",register:"Đăng ký  tài khoản Zing ID"},initBoxy:function(){if(zmContent.bx_handler==null){zmContent.bx_handler=new zm.Boxy({autoFocus:false,contentClass:"lbx_widmid",footer:false,closeButton:false,animated:false,content:this.msg.bxcontent})}},hideAndClearBoxy:function(){if(zmContent.bx_handler==null){return false}zm("#bxcontainer").children().each(function(){zm(this).remove()});zm("#bxcontainer").remove();zmContent.bx_handler.hide(false);zmContent.bx_handler=null},hideAllBxContent:function(){zm("#bxcontainer").children().each(function(){zm(this).hide(true)});return true},appendBxContent:function(b,a){if(zm("#"+b).size()>0){return false}return zm("#bxcontainer").append('<div id="'+b+'">'+a+"</div>")},showLoginBox:function(a){zmContent.initBoxy();zmContent.bx_handler.changeSettings({footer:true,onOk:zmWgLogin.zmDoLogin,cancelButton:this.msg.cancel,okButton:this.msg.ok,title:this.msg.login});zmContent.hideAllBxContent();try{zmContent.bx_handler.show()}catch(b){}if(zmContent.appendBxContent("zmefrmlogin",a)){}else{zm("#zmefrmlogin").show()}zmContent.bx_handler.moveTo("center","center");return false},showRegisterBox:function(a){zmContent.initBoxy();zmContent.bx_handler.changeSettings({footer:true,onOk:zmWgRegister.zmDoRegister,cancelButton:this.msg.cancel,okButton:this.msg.ok,title:this.msg.register});zmContent.hideAllBxContent();try{zmContent.bx_handler.show()}catch(b){}if(zmContent.appendBxContent("zmefrmregister",a)){}else{zm("#zmefrmregister").show()}zmContent.bx_handler.moveTo("center","center");return false},showBanBox:function(){zmContent.initBoxy();zmContent.bx_handler.changeSettings({footer:true,okButton:this.msg.ok,onOk:true,cancelButton:false,title:this.msg.login});zmContent.hideAllBxContent();if(!zmContent.appendBxContent("banned",this.msg.baned_account)){zm("#banned").show()}try{zmContent.bx_handler.show()}catch(a){}zmContent.bx_handler.moveTo("center","center");return false},showLoginError:function(a){if(!a){a=this.msg.invalid_account}zm("#lblError").html(a).show()},showLoading:function(){zmContent.initBoxy();zmContent.bx_handler.changeSettings({title:this.msg.title_loading,footer:false,closeButton:false});zmContent.hideAllBxContent();if(!zmContent.appendBxContent("loading",this.msg.loading)){zm("#loading").show()}try{zmXLoginWg.bx_handler.show()}catch(a){}zmContent.bx_handler.moveTo("center","center");return false},showErrorBox:function(b){zmContent.initBoxy();zmContent.bx_handler.changeSettings({footer:true,okButton:this.msg.ok,onOk:true,cancelButton:false,title:this.msg.login});zmContent.hideAllBxContent();if(!zmContent.appendBxContent("errorbox",this.msg.error)){zm("#errorbox").show()}try{zmContent.bx_handler.show()}catch(a){}zmContent.bx_handler.moveTo("center","center");return false},showMappingForm:function(a){zmContent.initBoxy();zmContent.bx_handler.changeSettings({footer:true,onOk:zmXLoginWg.zmDoRegister,cancelButton:this.msg.cancel,okButton:this.msg.ok,title:this.msg.mapping});zmContent.hideAllBxContent();try{zmContent.bx_handler.show()}catch(b){}if(zmContent.appendBxContent("zmexfrmlogin",a)){}else{zm("#zmexfrmlogin").show()}zmContent.bx_handler.moveTo("center","center");return false},showAccountValid:function(a){zmContent.hideMappingError("frmAccountError");zm("#btnChkZaccount").hide();zm("#btnChkZaccount").attr("rel",1);zm("#chk_fullname_ok").show()},showAccountInvalid:function(a){zmContent.showMappingError("frmAccountError",a.content);zm("#btnChkZaccount").attr("rel",0);zm("#btnChkZaccount").show();zm("#chk_fullname_ok").hide()},showReqAccountValid:function(a){zmContent.hideMappingError("reg_account_error");zm("#reg_checkacc").hide();zm("#reg_checkacc").attr("rel",1);zm("#chk_acc_ok").show()},showReqAccountInvalid:function(a){zmContent.showMappingError("reg_account_error",a.content);zm("#reg_checkacc").attr("rel",0);zm("#reg_checkacc").show();zm("#chk_acc_ok").hide()},showMappingError:function(b,a){zm("#"+b).html(a);zm("#"+b).show();return false},hideMappingError:function(a){zm("#"+a).hide();return false}};zmXLoginWg={idxUrl:"http://idx.me.zing.vn",loginUrl:"http://openwidget.me.zing.vn",registerUrl:"http://openwidget.me.zing.vn",image_url:"http://img.me.zdn.vn",maxretry:60,retry:0,timer:null,provider:null,pu_handler:null,openPopup:function(d){var c=zmXCall.getXCallID();zmXCall.register("zmxLoginCallback",function(f){zmXLoginWg.pu_handler=null;zmXLoginWg.zmxLogin(f)});if(zmXLoginWg.pu_handler!=null&&zmXLoginWg.pu_handler.closed==true){zmXLoginWg.pu_handler=null}if(zmXLoginWg.provider!=d&&zmXLoginWg.pu_handler!=null){zmXLoginWg.pu_handler.close()}if(zmXLoginWg.provider==d&&zmXLoginWg.pu_handler!=null){try{zmXLoginWg.pu_handler.focus();return false}catch(b){zmXLoginWg.pu_handler=null}}zmXLoginWg.provider=d;var e=encodeURIComponent(zmXLoginWg.loginUrl+"/widget/opidx?zmxcid="+c);var a=zmXLoginWg.idxUrl+"/oauth/dialog?provider="+d+"&callback="+e+"&t="+ +Math.floor(Math.random()*10000);zmXLoginWg.pu_handler=window.open(a,"_blank","height=500,width=500,left=400, top=100","resizable=yes","scrollbars=no","toolbar=no","status=no");return zmXLoginWg.pu_handler},openPopupForYahoo:function(){provider="Yahoo";zmXLoginWg.openPopup(provider.toLowerCase())},openPopupForGoogle:function(){provider="Google";return zmXLoginWg.openPopup(provider.toLowerCase())},openPopupForFacebook:function(){provider="Facebook";return zmXLoginWg.openPopup(provider.toLowerCase())},openPopupForTwitter:function(){provider="Twitter";return zmXLoginWg.openPopup(provider.toLowerCase())},zmDoRegister:function(){var a=zm("#zxaccount").val();if(a==""){zmContent.showMappingError("frmAccountError","Bạn phải nhập Tài khoản");zm("#zxaccount").focus();return false}if(zm("#btnChkZaccount").attr("rel")==0){return false}if(a.length>24||a.length<6){zmContent.showMappingError("frmAccountError","Vui lòng nhập tài khoản từ 6 đến 24 ký tự");return false}zmContent.hideMappingError("frmAccountError");var f=zm("#zxpwd").val();if(f==""){zmContent.showMappingError("frmPwdError","Vui lòng nhập mật khẩu");zm("#zxpwd").focus();return false}if(f.length>32||f.length<6){zm("#zxpwd").focus();zmContent.showMappingError("frmPwdError","Vui lòng nhập mật khẩu từ 6 đến 32 ký tự");return false}zmContent.hideMappingError("frmPwdError");var d=zm("#zxconpwd").val();if(d!=f){zm("#zxconpwd").focus();zmContent.showMappingError("frmConError","Nhập lại mật khẩu không giống");return false}zmContent.hideMappingError("frmConError");if(zm("#xagree").attr("checked")!=true){zm("#xagree").focus();zmContent.showMappingError("frmAgreeError","Vui lòng đồng ý với thỏa thuận sử dụng");return false}zmContent.hideMappingError("frmAgreeError");var b=zm("#tranfer_data").val();var e={method:"doreg",account:a,pwd:f,tranfer_data:b};var g=zm.param(e);var c=zmXLoginWg.loginUrl+"/widget/opidx/doreg?"+g;zmContent.showLoading("Đang tạo tài khoản trên Zing Me");zm.getJSON(c,function(h){switch(h.error){case -1:zmContent.showErrorBox(h.content);return false;case 0:zmContent.hideAndClearBoxy();zmEvent.fire("zmLogin",zmeOpenWidget.zme_widget_callback_data,h.acn,h.zid,{register:true,provider:zmXLoginWg.provider,logged:false});zmEvent.clear("zmLogin");return false;case 1:zmContent.showMappingForm();zmContent.showMappingError("frmAccountError",h.content);break;case 2:zmContent.showMappingForm();zmContent.showMappingError("frmPwdError",h.content);break}});return false},checkZAccount:function(b){var a=this.registerUrl+"/widget/opreg?method=chkacc&account="+b;zm.getJSON(a,function(c){if(c.error==0){zmContent.showAccountValid(c)}else{zmContent.showAccountInvalid(c)}})},processXLogin:function(b){var a=this.loginUrl+"/widget/opidx?method=getinfo&reqid="+b+"&r="+Math.floor(Math.random()*10000);zm.getJSON(a,function(c){switch(c.status){case 2:if(zmXLoginWg.timer!=null){window.clearTimeout(zmXLoginWg.timer)}zmContent.showMappingForm(c.content);setTimeout(function(){zm("#zxaccount").focus();zm("#zxaccount").keypress(function(){zmContent.hideMappingError("frmAccountError")});zm("#zxaccount").blur(function(){var d=zm("#zxaccount").val();if(d==""){zmContent.showMappingError("frmAccountError","Bạn phải nhập Tài khoản");return false}zmXLoginWg.checkZAccount(d);return false});zm("#btnChkZaccount").click(function(){var d=zm("#zxaccount").val();if(d==""){zmContent.showMappingError("frmAccountError","Bạn phải nhập Tài khoản");return false}zmXLoginWg.checkZAccount(d);return false});zm("#frmLoginBy").keypress(function(d){if(d.keyCode=="13"){zmXLoginWg.zmDoRegister()}})},500);break;case -1:zmContent.showErrorBox();break;case 1:case 0:if(zmXLoginWg.retry>0){zmXLoginWg.timer=window.setTimeout(function(){zmXLoginWg.processXLogin(b)},1000);zmXLoginWg.retry--}else{zmContent.showErrorBox();window.clearTimeout(zmXLoginWg.timer)}break}});return false},zmxLogin:function(a){if(a.mapped==1){zmContent.hideAndClearBoxy();zmEvent.fire("zmLogin",zmeOpenWidget.zme_widget_callback_data,a.acn,a.zid,{register:false,provider:zmXLoginWg.provider,logged:false})}else{if(!a.reqid||a.reqid==""){zmContent.showErrorBox()}else{zmXLoginWg.retry=zmXLoginWg.maxretry;zmXLoginWg.processXLogin(a.reqid)}}}};var zmWgRegister={register_url:"http://openwidget.me.zing.vn",captcha_url:"http://captcha.tool.zing.vn",zmRegister:function(f){if(typeof(zmeOpenWidget.zme_widget_callback)=="function"){zmEvent.subscribe("zmRegister",zmeOpenWidget.zme_widget_callback)}var h={r:Math.floor(Math.random()*10000),src:zmeOpenWidget.zme_consumer,tpl:zmeOpenWidget.zme_widget_tpl,method:"register"};if(f){var e=zm(f).attr("data-acn");var a=zm(f).attr("data-fullname");var g=zm(f).attr("data-dob");var c=zm(f).attr("data-email");var d=zm(f).attr("data-checksum");if(e!=undefined&&e!=""){h.acn=e}if(a!=undefined&&a!=""){h.fullname=a}if(g!=undefined&&g!=""){h.dob=g}if(c!=undefined&&c!=""){h.email=c}if(d!=undefined&&d!=""){h.checksum=d}}h=zm.param(h);var b=this.register_url+"/widget/opreg?"+h;zm.getJSON(b,function(j){switch(j.status){case 0:var i=function(k){zmContent.hideAndClearBoxy();zmEvent.fire("zmRegister",zmeOpenWidget.zme_widget_callback_data,k.acn,k.zid,{register:false,provider:"zingme",logged:false});zmEvent.clear("zmRegister")};zmXCall.register("zmRegisterCallback",i);zmContent.showRegisterBox(j.content);setTimeout(function(){var l=zm("#frmRegister").attr("action");var m=zmXCall.getXCallID();l=l+"?zmxcid="+m;zm("#frmRegister").attr("action",l);var k=zm("#reg_account").val();zm("#reg_account").focus();zm("#reg_account").keypress(function(){zmContent.hideMappingError("reg_account_error")});zm("#reg_account").blur(function(){var n=zm("#reg_account").val();if(n==""){return false}zmWgRegister.checkZAccount(n);return false});zm("#reg_checkacc").click(function(){var n=zm("#reg_account").val();if(n==""){zmContent.showMappingError("reg_account_error","Bạn phải nhập Tài khoản");return false}zmWgRegister.checkZAccount(n);return false});zm("#frmRegister").keypress(function(n){if(n.keyCode=="13"){zmWgRegister.zmDoRegister()}});zm("#req_login").click(function(n){zmWgLogin.zmLogin()})},500);break;case 1:zmContent.hideAndClearBoxy();zmEvent.fire("zmRegister",zmeOpenWidget.zme_widget_callback_data,j.acn,j.zid,{register:false,provider:"zingme",logged:true});zmEvent.clear("zmRegister");break}return false});return false},checkZAccount:function(b){var a=this.register_url+"/widget/opreg?method=chkacc&account="+b;zm.getJSON(a,function(c){if(c.error==0){zm("#reg_checkacc").attr("rel",1);zmContent.showReqAccountValid(c)}else{zmContent.showReqAccountInvalid(c);zm("#reg_checkacc").attr("rel",0)}})},zmDoRegister:function(){var a=zm("#reg_account").val();if(a==""){zmContent.showMappingError("reg_account_error","Bạn phải nhập Tài khoản");return false}if(a.length>24||a.length<6){zmContent.showMappingError("reg_account_error","Vui lòng nhập Tài khoản từ 6 đến 24 ký tự");return false}if(zm("#reg_checkacc").attr("rel")==1){zmContent.hideMappingError("reg_account_error")}else{return false}var d=zm("#reg_pwd").val();if(d==""){zm("#reg_pwd").focus();zmContent.showMappingError("reg_pwd_error","Vui lòng nhập mật khẩu");return false}if(d.length>32||d.length<6){zm("#reg_pwd").focus();zmContent.showMappingError("reg_pwd_error","Vui lòng nhập mật khẩu từ 6 đến 32 ký tự");return false}zmContent.hideMappingError("reg_pwd_error");var c=zm("#reg_cpwd").val();if(c!=d){zm("#reg_cpwd").focus();zmContent.showMappingError("reg_cpwd_error","Nhập lại mật khẩu không giống");return false}zmContent.hideMappingError("reg_cpwd_error");var b=zm("#veryfied_code").val();if(b.length!=6){zm("#veryfied_code").focus();zmContent.showMappingError("reg_captcha_error","Vui lòng nhập đúng mã xác nhận");return false}zmContent.hideMappingError("reg_captcha_error");if(zm("#ragree").attr("checked")!=true){zm("#ragree").focus();zmContent.showMappingError("reg_agree_error","Vui lòng đồng ý với thỏa thuận sử dụng");return false}zmContent.hideMappingError("reg_agree_error");zmXCall.register("zmDoRegCallback",function(e){switch(e.error){case -1:zmContent.showErrorBox(e.content);return false;case 0:zmContent.hideAndClearBoxy();zmEvent.fire("zmRegister",zmeOpenWidget.zme_widget_callback_data,e.acn,e.zid,{register:true,provider:"zingme"});zmEvent.clear("zmRegister");return false;case 1:zmContent.showRegisterBox();zmContent.showReqAccountInvalid(e);break;case 2:zmContent.showRegisterBox();zmContent.showMappingError("reg_pwd_error",e.content);break;case 3:zmContent.showRegisterBox();zmContent.showMappingError("reg_captcha_error",e.content);break}zm("#veryfied_code").val("");zmWgRegister.refressCaptcha()});zmContent.showLoading();zm("#frmRegister").submit();return false},refressCaptcha:function(){var b=zm("#token").val();var c=new Date();var a=zmWgRegister.captcha_url+"/captcha/getcaptcha?token="+b+"&type=img&jt="+c.getTime();zm("#captcha").attr("src",a)}};var zmWgLogin={zme_login_url:"http://openwidget.me.zing.vn",zme_login_tpl:"default",zme_login_consumer:"zme",zmDoLogin:function(){if(zm("#zaccount").val()==""||zm("#zpwpd").val()==""){zmContent.showLoginError(zmContent.msg.input_account);return false}zmContent.showLoading();zm("#frmLogin").submit();return false},zmLogin:function(){if(typeof(zmeOpenWidget.zme_widget_callback)=="function"){zmEvent.subscribe("zmLogin",zmeOpenWidget.zme_widget_callback)}var b={r:Math.floor(Math.random()*10000),src:zmeOpenWidget.zme_consumer,tpl:zmeOpenWidget.zme_widget_tpl};b=zm.param(b);var a=this.zme_login_url+"/widget/op/login?"+b;zm.getJSON(a,function(c){switch(c.status){case 0:var d=function(e){if(e.error==0){zmContent.hideAndClearBoxy();zmEvent.fire("zmLogin",zmeOpenWidget.zme_widget_callback_data,e.acn,e.zid,{register:false,provider:"zingme",logged:false});zmEvent.clear("zmLogin")}else{if(e.error==2){zmContent.showBanBox()}else{zmContent.showLoginBox();zmContent.showLoginError()}}};zmXCall.register("zmLoginCallback",d);zmContent.showLoginBox(c.content);setTimeout(function(){var g=zm("#u1").val();var f=zmXCall.getXCallID();g=g+encodeURIComponent("&zmxcid="+f);zm("#u1").val(g);var e=zm("#fp").val();e=e+"&zmxcid="+f;zm("#fp").val(e);zm("#zaccount").focus();zm("#bntYahooLogin").click(function(){zmXLoginWg.openPopupForYahoo()});zm("#bntGoogleLogin").click(function(){zmXLoginWg.openPopupForGoogle()});zm("#bntFacebookLogin").click(function(){zmXLoginWg.openPopupForFacebook()});zm("#bntTwitterLogin").click(function(){zmXLoginWg.openPopupForTwitter()});zm("#link_register").click(function(){zmWgRegister.zmRegister()});zm("#frmLogin").keypress(function(h){if(h.keyCode=="13"){zmWgLogin.zmDoLogin()}})},500);break;case 1:zmContent.hideAndClearBoxy();zmEvent.fire("zmLogin",zmeOpenWidget.zme_widget_callback_data,c.acn,c.zid,{register:false,provider:"zingme",logged:true});zmEvent.clear("zmLogin");break;case 2:zmContent.showBanBox();break}return false});return false}};fbdiv=document.createElement("div");fbdiv.setAttribute("id","fb-root");document.body.appendChild(fbdiv);fbscript=document.createElement("script");fbscript.src="http://connect.facebook.net/en_US/all.js";if(document.body.firstChild){document.body.insertBefore(fbscript,document.body.firstChild)}else{document.body.appendChild(fbscript)}var zmOpenShareLink={appId:"418319678231355",popup_height:400,popup_width:600,serverlog:"http://openwidget.me.zing.vn/widget/logsharelink",share_link_server:"http://openwidget.me.zing.vn/widget",callback_url:"http://static.me.zing.vn/openwidget/sharelink_callback.html?zmxcid=",provider:null,pu_handler:null,openPop:function(c,a){if(zmOpenShareLink.pu_handler!=null&&zmOpenShareLink.pu_handler.closed==true){zmOpenShareLink.pu_handler=null}if(zmOpenShareLink.provider!=c&&zmOpenShareLink.pu_handler!=null){zmOpenShareLink.pu_handler.close()}if(zmOpenShareLink.provider==c&&zmOpenShareLink.pu_handler!=null){try{zmOpenShareLink.pu_handler.focus();return false}catch(b){zmOpenShareLink.pu_handler=null}}zmOpenShareLink.provider=c;if(c=="zingme"){zmOpenShareLink.popup_width=600,zmOpenShareLink.popup_height=500}else{if(c=="facebook"){zmOpenShareLink.popup_width=650,zmOpenShareLink.popup_height=355}else{if(c=="linkedin"){zmOpenShareLink.popup_width=600,zmOpenShareLink.popup_height=325}else{if(c=="linkhay"){zmOpenShareLink.popup_width=810,zmOpenShareLink.popup_height=600}else{if(c=="digg"){zmOpenShareLink.popup_width=810,zmOpenShareLink.popup_height=400}else{zmOpenShareLink.popup_width=600,zmOpenShareLink.popup_height=400}}}}}zmOpenShareLink.pu_handler=window.open(a,"_blank","height="+zmOpenShareLink.popup_height+",width="+zmOpenShareLink.popup_width+",left=400, top=100","resizable=yes","scrollbars=yes","toolbar=no","status=no")},openShareLink:function(b){var c=b.attr("link-provider");var a=encodeURIComponent(window.location);a=zmOpenShareLink.share_link_server+"/sharelink?social="+c+"&page="+a;zmOpenShareLink.openPop(c,a)},openZingme:function(f,g,d){var c="http://link.apps.zing.vn/share";var b=window.location.toString();zm.getJSON(zmOpenShareLink.serverlog,{action:"click",social:"zingme",link:encodeURIComponent(b)});zmOpenShareLink.popup_width=600;zmOpenShareLink.popup_height=500;zmXCall.register("zmSharelinkCallback",sharelink_callback);zmxcid=zmXCall.getXCallID();title=typeof f!=="undefined"?f:"";desc=typeof g!=="undefined"?g:"";image=typeof d!=="undefined"?d:"";var e="";if(title!=""&&desc!=""&&image!=""){e="&t="+title+"&desc="+desc+"&images="+image}b=encodeURIComponent(b);var a=encodeURIComponent(zmOpenShareLink.serverlog+"?zmxcid="+zmxcid+"&action=share&social=zingme&link="+b);shareurl=c+"?url="+b+e+"&callback="+a;zmOpenShareLink.openPop("zingme",shareurl)},showdialogFacebook:function(g){var f=zm("meta[property=og\\:title]").attr("content");var a=zm("meta[property=og\\:site_name]").attr("content");var b=zm("meta[property=og\\:description]").attr("content");var e=zm("meta[property=og\\:image]").attr("content");var c=zmCore.cookie("acn");link=window.location.toString()+"&acn="+c+"&fid="+g;var d={method:"feed",link:link,picture:e,name:f,caption:a,description:b};function h(i){zm.getJSON(zmOpenShareLink.serverlog,{action:"share",social:"facebook",link:encodeURIComponent(link),linkid:i.post_id})}FB.ui(d,h)},openFacebook:function(){var a=encodeURIComponent(window.location);zm.getJSON("http://openwidget.me.zing.vn/widget/logsharelink",{action:"click",social:"facebook",link:a});FB.init({appId:zmOpenShareLink.appId,status:true,cookie:true,oauth:true});var b="";FB.api("/me",function(c){b=c.id;if(b){zmOpenShareLink.showdialogFacebook(b)}else{FB.login(function(d){if(d.authResponse){FB.api("/me",function(e){b=e.id;zmOpenShareLink.showdialogFacebook(a,b)})}else{}},{scope:""})}});return false},};function openZingme(b,c,a){zmOpenShareLink.openZingme(b,c,a)}function openFacebook(){var a=encodeURIComponent(window.location);zmOpenShareLink.openFacebook(a)}var zmeOpenWidget={zme_widget_tpl:"default",zme_consumer:"zme",zme_widget_callback:function(){},zme_widget_callback_data:{},init:function(data){if(data.tpl){zmeOpenWidget.zme_widget_tpl=data.tpl}if(data.consumer){zmeOpenWidget.zme_consumer=data.consumer}zmeOpenWidget.zme_widget_callback=eval("window."+data.callback);zmeOpenWidget.zme_widget_callback_data=data;var continue_process=true;var precallback=eval("window."+data.precallback);zm("a[rel=zme-loginwg]").each(function(){zm(this).click(function(){try{if(typeof precallback=="function"){var cbdata=[data.precallback];continue_process=precallback.apply(precallback,cbdata)}}catch(err){continue_process=false}if(continue_process==false){return false}zmWgLogin.zmLogin();return false})});zm("a[rel=zme-registerwg]").each(function(){zm(this).click(function(){try{if(typeof precallback=="function"){var cbdata=[data.precallback];continue_process=precallback.apply(precallback,cbdata)}}catch(err){continue_process=false}if(continue_process==false){return false}zmWgRegister.zmRegister(this);return false})});if(zm("#zme-loginwg").size()>0){zm("#zme-loginwg").click(function(){try{if(typeof precallback=="function"){var cbdata=[data.precallback];continue_process=precallback.apply(precallback,cbdata)}}catch(err){continue_process=false}if(continue_process==false){return false}zmWgLogin.zmLogin()})}if(zm("#zme-registerwg").size()>0){zm("#zme-registerwg").click(function(){try{if(typeof precallback=="function"){var cbdata=[data.precallback];continue_process=precallback.apply(precallback,cbdata)}}catch(err){continue_process=false}if(continue_process==false){return false}zmWgRegister.zmRegister(this)})}zm("[link-provider]").click(function(){try{if(typeof precallback=="function"){var cbdata=[data.precallback];continue_process=precallback.apply(precallback,cbdata)}}catch(err){continue_process=false}if(continue_process==false){return false}zmOpenShareLink.openShareLink(zm(this));return false})},decode:function(h){var a=decodeURIComponent,c={},e=h.split("&"),f,d;for(f=0;f<e.length;f++){d=e[f].split("=");if(d&&d[0]){var b=d.slice(0,1);var g=d.slice(1,d.length);var j=g.join("=");c[a(b)]=a(j)}}return c}};window.setTimeout(function(){var b=/(static.me.zing.vn).*?#(.*)/;zm.each(document.getElementsByTagName("script"),function(d,e){if(e.src){var c=b.exec(e.src);if(c){var f=zmeOpenWidget.decode(c[2]);zm.each(f,function(g,h){if(h=="0"){f[g]=0}});zmeOpenWidget.init(f)}}});var a=zm.createElement("script",{src:"http://static.me.zing.vn/v3/zds/zt-1.03-1.min.js"});zm("head").append(a)},0);