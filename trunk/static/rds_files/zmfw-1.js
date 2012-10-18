var zmfw = new function()
{
    var HOVER_TIMEOUT = 1000,
		LEAVE_TIMEOUT = 1000,
        detailBoxy = new zm.Boxy({"footer": false, "contentClass": "lbx_widlar", title: "Thông tin hoạt động", content: '<div id="dboxy_content"></div><div id="dboxy_footer"></div>'}),
		scrollui = new zm.ScrollableUI({width: 585, height: 400}).init(),
		div = zm.createElement('div', {}, {width:"585px", "position": "absolute", "top":"-9999px", "left": "-9999px"}),
        boxType =0,
		inIframe = window != top,
		boxyOffset = inIframe ? 100 : null,
		_createCmt = 0,
		_moreCmt = 0,
		_limitCmt = 15,
		_lstClick = new Object(),
		_lastCurrent = null,
		_mediaSize= {width:(zmConfig.pageName=="home")?450:400, height: 300},
		commentTemplate = "<div class='rowcomment' id='crw_{COMMENT_ID}'> " +
									"<div class='rowcommentlt'><a href='http://me.zing.vn/u/{USER_NAME}' target='_top'><img src='{AVATAR}' width='32px' height='32px' title='{DISPLAY_NAME}'> </img></a></div>" +
									"<div class='rowcommentrt'>" +
										"<a href='http://me.zing.vn/u/{USER_NAME}' target='_top'><strong>{DISPLAY_NAME}</strong></a>" +
										"<span> {COMMENT} </span>" +
										"<p><span class='grey'>1 giây trước</span></p>"+
									"</div>" +
						  "</div>";


	zm('#dboxy_content').append(scrollui.getElement());
	document.body.appendChild(div);

	function doPost(url, data) {
		var success;
		for (var i = 2, a; a = arguments[i]; i++) {
			if (zm.isFunction(a)) {
				success = a;
				break;
			}
		}
		if (inIframe)
			zm.getJSON(zmConfig.ME_URL + url + '?' + zm.param(data), success);
		else
			zm.post.apply(zm, arguments);
	}

	function alertBoxy(msg, options) {
		zm.Boxy.alert(msg,"Thông báo", null, zm.extend({iframeTopOffset: boxyOffset}, options));
	}

	function showFeed(current, feedId, options, windows)
	{
		_lastCurrent = current;
        boxType = 1;
		var url = '/fdw/show/',
			data = {"viewerId":zmConfig.viewerId,"feedId":feedId, "time":zmConfig.time, "signkey": zmConfig.signkey, "windows": windows},
			success = function(data) {
				if(data.errorCode==0 && _lastCurrent == current){                    
					zm(current).showDisplaybox(data.body, zm.extend({
                                dboxClass:inIframe ? "disbox_stt_quickview_iframe" : "disbox_stt_quickview", 
                                "footer": data.footer, 
                                "noPad": true, "width": inIframe?446:532, afterShow: function(){
                                    zmlw.load(data.feedIds);
					                zmfw.inputTxt(feedId);
                                    for(var i =0; i< data.commentIds.length; i++){
                                        zm('#crw_' + data.commentIds[i]).hover(overfc, outfc);
                                    }
                                }
                    }, options));					
				}
			};
		doPost(url, data, {dataType:"json"}, success);
	    return false;
	}
    function popupFeed(feedId){
        var meta = getMetadata(zm("#cmtf_" + feedId).attr("rel"));
		var url = '/fdw/share_popup/',
			data = {"type": "detail","viewerId":zmConfig.viewerId,"feedId":feedId, "time":zmConfig.time, "signkey": zmConfig.signkey},
			success = function(data) {
				if(data.errorCode==0){
					zm(div).html(data.body);
					var h = zm(div).outerHeight();
                    var objLikeShare = zm("#actLikeShare-" + feedId);
                    var isLike = parseInt(objLikeShare.attr("islike"));
                    if (isLike==1){
                        h+=31;
                    }
                    else{
                        h+=1;
                    }
					scrollui.setContent(data.body);
					scrollui.setHeight(h);
					zm('#dboxy_footer').html(data.footer);
					detailBoxy.show();
                    zmlw.load(data.feedIds);
	                zmfw.inputTxt(feedId);
                    for(var i =0; i< data.commentIds.length; i++){
                        zm('#crw_' + data.commentIds[i]).hover(overfc, outfc);
                    }
				}
				else{
					zm.Boxy.alert(data.body,"Thông báo", 5000, {"contentClass": "lbx_widmid", okButton: "Đóng"});
				}
			};
		zm.post(url, data, {dataType:"json"}, success);
        return false;
    }
    function hidePopupFeed(){
        detailBoxy.hide();
        return false;
    }
	function playMedia(feedId, source)
	{
		embeded = '<iframe width="' + _mediaSize.width + '" height="' + _mediaSize.height + '" frameborder="0" allowfullscreen="" src="'+ source +'"></iframe>';
		var media = zm("#mediaw_" + feedId);
		media.attr("class", "mediawide");
		media.html(embeded);
		return false;
	}
	function showsf(feedId, listIds){
		zm("#moresfw_" + feedId).css("display", "none");
		var arrIds = listIds.split(",");
		for(var i =0; i< arrIds.length; i++){
		  var fId = arrIds[i]
		  zm("#fiw_" + fId).css("display", "block");
		}
		return false;
	}

	/************************************************************************************************
	 * REGION FEED COMMENT
	 ************************************************************************************************/

	function crtc(){
		var a = this;
		if(this.nodeName.toLowerCase()=="em"){
			a = this.parentNode;
		}
		var feedId = a.id.substring(5);
		zmfw.crtc2(feedId);
	}
	function crtc2(feedId)
	{
		var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));
		var objTextBox = zm('#txtfcw_' + feedId);
		var comment = objTextBox[0].value;
		if(comment == 'Nhập lời bình' || zm.trim(comment)=="")
		{
			alertBoxy("Bạn chưa nhập lời bình");
		}
		else
		{
			if(_createCmt==1){return;}
			_createCmt = 1;

			objTextBox.attr("disabled", true);
            // created comment
			var url = "/fc/create2",
				data = {"feedId":feedId, "appId": meta.appId, "actId": meta.actId, "objectId": meta.objectId, "message":comment, "ownerId": zmConfig.viewerId,
					"userIdFrom":meta.fromUserId, "userIdTo":meta.toUserId, "time": meta.cTime, "signKey": meta.signKeyCreate},
				success = function(data) {
						if(data.cmtId>0) {
							objTextBox.val('');
				            objTextBox.attr("disabled", false);
				            objTextBox.focus();
				            // create temp comment item
				            data = zm.extend(data,{"COMMENT": comment,
				                    "FEED_ID": feedId,
				                    "COMMENT_ID": data.cmtId,
									"USER_NAME": zmConfig.viewerName,
									"AVATAR": zmConfig.viewerAva50,
									"DISPLAY_NAME": zmConfig.viewerFullName
							});

				            // append comment item
							var cmtItem = zmTemplate.render(commentTemplate, data);
				            zm("#acmtiw_" + feedId).append(cmtItem);
                            if(boxType==1){
							    zm.plugins.displaybox.scrollUI.scrollToEnd();
                            }
                            else{
                                scrollui.scrollToEnd();
                            }    
							// render emotions
				            zm("#crw_" + data.cmtId).renderEmotions();
						}
						else
						{
							alertBoxy("Có lỗi xảy ra. Bạn vui lòng thực hiện lại");
							objTextBox.attr("disabled", false);
						}
						if(data.plusPoint > 0){
							var cfeed = zm('#zmdbox');
							zmCommon.plusPoint("+" + data.plusPoint, 'zmdbox', {offsetLeft: -20, start: cfeed.top()+ cfeed.height() , end:cfeed.top(), inWindow:true});
						}
						else{
                            var cfeed = zm('#zmdbox');
							zmCommon.fullPoint(1, 'zmdbox', {offsetLeft: -20, start: cfeed.top()+ cfeed.height() , end:cfeed.top(), inWindow:true});
						}
						_createCmt = 0;
				},
				error = function(){
						alertBoxy("Có lỗi xảy ra. Bạn vui lòng thực hiện lại");
						objTextBox.attr("disabled", false);
						_createCmt = 0;
				};
			doPost(url, data, {dataType: 'json'}, success, error);
		}
		return false;
	}

	function delc(commentId, feedId, signKeyDelete)
	{
		var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));

		zm.Boxy.confirm("Bạn có muốn xóa bình luận này không?",
            function(){
                var url = '/fc/remove',
					data = {feedId:feedId, appId: meta.appId, objectId: meta.objectId, commentId:commentId, fromUserId: meta.fromUserId,
								toUserId: meta.toUserId, viewerId: zmConfig.viewerId, signKey: signKeyDelete, isAdmin: meta.isAdmin},
					success = function(status) {
						if(status == 1)
						{
							zm("#crw_" + commentId).remove();
							var cfeed = zm('#zmdbox');
							zmCommon.plusPoint("-6", 'zmdbox', {offsetLeft: -20, start: cfeed.top()+ cfeed.height() , end:cfeed.top()});
						}
						else if(status == 0)
						{
							alertBoxy("Có lỗi xảy ra. Bạn vui lòng thực hiện lại", {okButton: "Đồng ý"});
						}
					};
				doPost(url, data, success);
            },
            {
                title: "Xóa bình luận",
                okButton: "Đồng ý",
                cancelButton: "Hủy bỏ",
				iframeTopOffset: boxyOffset,
				isTop:true
            });
		return false;
	}

	function replyc(username, feedId)
	{
		if(_lstClick[feedId]!=true){
	        uiTextbox(feedId);
			registerEnter('txtfcw_' + feedId);
			try{zm('#emofeedw_' + feedId).attachEmoButtons('txtfcw_');} catch(e){}
		}
		var obj = zm('#txtfcw_' + feedId);
		var text = obj.val() + "@" + username + ": ";
		obj.val(text);
		return false;
	}

	function morefc(feedId, lastCommentId)
	{
		var f = function(){
			if(_moreCmt==1) {
				return false;
			}
			var linkMore = zm("#lmcw_" + feedId);
			var fromCommentId = linkMore.attr("fromId");
			var page = parseInt(linkMore.attr("page"));
			_moreCmt = 1;
			var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));
			var limit = _limitCmt;
			// get more comment
			var url = '/fdw/morec',
				data = {
					"createdId": meta.fromUserId,
					"feedId":feedId,
					"fromCommentId": fromCommentId,
					"lastCommentId": lastCommentId,
					"totalComment": meta.totalComment,
					"limit": limit,
					"page":page,
					"viewerId": zmConfig.viewerId,
					"time":zmConfig.time,
					"signkey": zmConfig.signkey
				},
				success = function(data) {
					zm("#smorecw_" + feedId).prepend(data.body);
					linkMore.attr("fromId", data.lastId);
					if(data.more==0)
					{
						if(page * _limitCmt + 2 >= meta.totalComment){
							zm("#lmcw_" + feedId).remove();
						}
						else{
							var mcloading = zm("#fclw_" + feedId);
							if(mcloading.size() == 0){
								zm("#lmcw_" + feedId).append("<img id='fcl_" + feedId + "' src='http://img.me.zdn.vn/v3/images/loading_small.gif'> </img>");
								setTimeout(f,2000);
								_moreCmt = 0;
								return false;
							}
							else{
								mcloading.remove();
								alertBoxy("Có lỗi không mong muốn xảy ra. Bạn vui lòng thực hiện lại");
							}
						}
					}
					else
					{
						linkMore.attr("page", page + 1);
					}
					_moreCmt = 0;
				};
			doPost(url, data, {dataType: 'json'}, success);
			return false;
		}
		return f();
	}

    function overfc()
    {
		var feedId  = this.id.split("_")[1];
        var fc = zm("#delcw_" + feedId);
        fc.attr("class", "closefed active");
        return false;
    }

    function outfc()
    {
		var feedId  = this.id.split("_")[1];
        var fc = zm("#delcw_" + feedId);
        fc.attr("class", "closefed");
        return false;
    }
	function spam(userId)
	{
		zm.Boxy.confirm("Bạn không muốn user này bình luận hoạt động của bạn?",
            function(){
                var url = '/f/'+ zmConfig.ownerName + '/feed/spam',
					data = {userId:userId},
					success = function(status) {
						if(status == 1)
						{
							alertBoxy("Yêu cầu đã được thực hiện");
						}
						else if(status == 0)
						{
							alertBoxy("Có lỗi không mong muốn xảy ra. Bạn vui lòng thực hiện lại");
						}
					};
				doPost(url, data, success);
            },
            {
                title: "Thông báo",
                okButton: "Đồng ý",
                cancelButton: "Hủy bỏ",
				iframeTopOffset: boxyOffset,
				isTop:true
            });
		return false;

	}
	/************************************************************************************************
	 * REGION COMMON
	 ************************************************************************************************/
    function inputTxt(feedId){
		uiTextbox(feedId);
		var objTxt = zm('#txtfcw_' + feedId);
		try{objTxt.tagFriend(objTxt.parent()[0]);} catch(e){}
		try{zm('#emofeedw_' + feedId).attachEmoButtons('txtfcw_');} catch(e){}
		objTxt.focus();
		registerEnter('txtfcw_' + feedId);
		return false;
    }
	function uiTextbox(feedId)
	{
        zm("#cmtitw_" + feedId).css('display', "block");

		if(zm("#cmtfw_" + feedId).css('display') == 'none')
		{
			zm("#cmtfw_" + feedId).css('display', "block");
		}
		zm("#lmcw_" + feedId).css('display', "block");
        zm("#cmtboxw_" + feedId).css('display', "block");

        zm('#spcmtw_' + feedId).show();
        zm('#txtfcw_' + feedId).focus();
		return false;
	}
	function registerEnter(txtId)
	{
		var elm  = zm("#" + txtId);
		// Press "enter" as submit
		elm.keypress(function(event) {
			if (event.keyCode == 13 && !event.shiftKey) {
				var id = zm(this).attr('id').substr(7);
				zm('#crtcw_' + id).click();
				return false;
			}
		});
        try{elm.autoResize();} catch(e){}
	}

	function getMetadata(strMetadata)
	{
		var metaData = strMetadata.split(':');

		return {appId:metaData[0],objectId:metaData[1],fromUserId:metaData[2],toUserId:metaData[3],
				cTime:metaData[4],signKeyCreate:metaData[5],isAdmin:metaData[6],totalComment:metaData[7], actId:metaData[8]};
	}


// public feed function
this.playMedia = playMedia;
this.showsf = showsf;
this.showFeed = showFeed;
this.popupFeed = popupFeed;
this.hidePopupFeed = hidePopupFeed;
// public comment function
this.crtc = crtc;
this.crtc2 = crtc2;
this.delc = delc
this.replyc  = replyc;
this.morefc = morefc;
this.overfc = overfc;
this.spam = spam;
this.inputTxt = inputTxt;
this.uiTextbox = uiTextbox;
}();
