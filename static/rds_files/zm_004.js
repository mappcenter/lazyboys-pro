var zmGift={GIFT_DEFMSG:"Nhập nội dung lời nhắn...",curGift:{},getGAScript:function(){var b=b||[];b.push(["_setAccount","UA-4718926-1"]);b.push(["_setAllowLinker",true]);b.push(["_setAllowHash",false]);b.push(["_trackPageview"]);b.push(["z._setAccount","UA-4695288-1"]);b.push(["z._setDomainName",".zing.vn"]);b.push(["z._setAllowLinker",true]);b.push(["z._setAllowHash",false]);b.push(["z._trackPageview"]);var a=(("https:"==current_protocol)?"https://ssl.":"http://www.");zm.addScript(a+"google-analytics.com/ga.js")},showGift:function(e,c,g,f,b){var d=c.toString();if(d.length<10){var a="/gt/api/me/widget?pid="+g+"&id="+c+"&oname="+f}else{var a="/ajx/gift/showbox?date="+c+"&id="+g+"&oid="+f}zm.get(a,{dataType:"json"},function(j){if(j.error==0){zm(e).showDisplaybox(j.html,zm.extend({dboxClass:"disbox_writebirthwish",checkImage:false},b));zwg.addItem("gufrom","ZMED_"+f+"?id=1&l=1");zwg.fillWg();var h=zm("#gname").html();zm("#gaccept").click(function(){var l=zm("#gpushfeed").attr("checked");var k=1;if(!l){k=0}zmGift.acceptGift(e,c,g,f,h,k,b)});zm("#grefuse").click(function(){zmGift.refuseGift(e,c,g,f,h,b)})}else{if(j.error==2){var i='<div class="disbox_receive_giftstxt">					<p class="marb8">Bạn đã nhận quà. </p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+f+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>				<div class="clr"></div>';zm(e).toggleDisplaybox(i,zm.extend({dboxClass:"disbox_writebirthwish",width:390},b))}else{if(j.error==3){var i='<div class="disbox_receive_giftstxt">					<p class="marb8">Bạn đã từ chối nhận quà.  </p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+f+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>				<div class="clr"></div>';zm(e).toggleDisplaybox(i,zm.extend({dboxClass:"disbox_writebirthwish",width:390},b))}else{var i='<div class="disbox_receive_giftstxt">                	<p class="marb8">Có lỗi xảy ra! </p>                	<a class="btn_L3" href="#" onclick="zm(this).hideDisplaybox();return false;"><em>Đóng</em></a>                  </div>            <div class="clr"></div>';zm(e).showDisplaybox(i,zm.extend({dboxClass:"disbox_writebirthwish",width:390},b))}}}zmGift.getGAScript()},function(h){})},acceptGift:function(f,h,b,g,d,a,c){var e=h.toString();if(e.length<10){zm.post("/gt/api/me/agreegift",{id:h,pid:b,oname:g,pf:a},{dataType:"json"},function(j){if(j.error==0){var i='<div class="disbox_receive_giftstxt">                	<p class="marb8">Bạn đã nhận món quà <a href="#" style="cursor: default;text-decoration: none">'+d+'</a></p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+g+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>            <div class="clr"></div>';zm(f).showDisplaybox(i,zm.extend({dboxClass:"disbox_writebirthwish",width:390},c));zmGift.getGAScript();return false}})}else{zm.post("/ajx/gift/accept",{id:b,date:h,from:g,pf:a},{dataType:"json"},function(j){if(j.error==0){var i='<div class="disbox_receive_giftstxt">                	<p class="marb8">Bạn đã nhận món quà <a href="#" style="cursor: default;text-decoration: none">'+d+'</a></p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+g+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>            <div class="clr"></div>';zm(f).showDisplaybox(i,zm.extend({dboxClass:"disbox_writebirthwish",width:390},c))}zmGift.getGAScript()})}return false},refuseGift:function(e,g,a,f,c,b){var d=g.toString();if(d.length<10){zm.post("/gt/api/me/refusegift",{id:g,pid:a,oname:f},{dataType:"json"},function(i){if(i.error==0){var h='<div class="disbox_receive_giftstxt">                	<p class="marb8">Bạn đã từ chối nhận quà.</p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+f+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>            <div class="clr"></div>';zm(e).toggleDisplaybox(h,zm.extend({dboxClass:"disbox_writebirthwish",width:390},b));return false}zmGift.getGAScript()})}else{zm.post("/ajx/gift/reject",{id:a,date:g,from:f},{dataType:"json"},function(i){if(i.error==0){var h='<div class="disbox_receive_giftstxt">                	<p class="marb8">Bạn đã từ chối nhận quà.</p>                	<a class="btn_L3" onclick="zm(this).hideDisplaybox();zmCommon.sendGift('+f+'); return false;" href="#"><em>G\u1eedi quà cám ơn</em></a>                  </div>            <div class="clr"></div>';zm(e).toggleDisplaybox(h,zm.extend({dboxClass:"disbox_writebirthwish",width:390},b))}zmGift.getGAScript()});return false}}};