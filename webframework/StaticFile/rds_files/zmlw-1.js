var zmlw = new function(){
	var inIframe = window != top,
        detailLike = new zm.Boxy({isTop: true,title: "Những người thích điều này",okButton: "Đóng", content: '<div id="dlike_content"></div>'}),
		scrollui = new zm.ScrollableUI({width: 325, height: 350}).init(),
		divLike = zm.createElement('div'),
		boxyOffset = inIframe ? 100 : null;

	zm('#dlike_content').append(scrollui.getElement());

	function doPost(url, data) {
		var success;
		for (var i = 2, a; a = arguments[i]; i++) {
			if (zm.isFunction(a)) {
				success = a;
				break;
			}
		}
		if (inIframe)
			zm.getJSON(url + '?' + zm.param(data), success);
		else
			zm.post.apply(zm, arguments);
	}

	function load(feedIds)
	{
		doPost("http://me.zing.vn/like/load", {"feedIds":feedIds.toString(),"viewerId": zmConfig.viewerId}, {"dataType": "json"},
        function(data) {
            if(data.errorCode>=0) {
                for(var i =0; i< feedIds.length; i++){
                    var feedId = feedIds[i];
                    var objLike = data.data[feedId];
                    if(objLike!=undefined){
                        // render button like
                        if(objLike.isLike){
						    var btnLike = zm("#zmevw-" + feedId);
						    btnLike.attr('isliked', "1");
						    btnLike.html("Bỏ Thích");
							zm("#zmflikew-" + feedId).attr("class", "icn12x12 icn_likegrey12x12");
					    }
                        // render who like
                        var divLike = zm("#divLikew_" + feedId);
					    divLike.css("display", "block");

					    var likeView = zm("#like-vieww-" + feedId);
					    var strLikeView = "";
					    if(objLike.isLike==1) {strLikeView = "Bạn" ;}
                        var total = (objLike.isLike==1)? objLike.count-1 : objLike.count;
                        var fromId = 0;
                        if(total>0){
                            var eLength = objLike.entries.length;
                            var cFriend = 0;
                            for( var j =0; j < eLength; j ++){
                                var uId = objLike.entries[j];
                                if(uId!=zmConfig.viewerId && cFriend<2){
                                    zwg.addWgItem(new wgItem("wgvw_" + feedId + "_" + uId, "ZMED_" + uId + "?id=1&l=2&m=1"));  
						            if(objLike.isLike==1 ){
							            strLikeView += ",&nbsp;<span id ='wgvw_" + feedId + "_" + uId + "'></span>";
						            }
						            else{
                                        if(j!=0){
                                            strLikeView+="&nbsp;";
                                        }
							            strLikeView += "<span id = 'wgvw_" + feedId + "_" + uId + "'></span>";
                                        if(cFriend < 1 && j!=objLike.entries.length-1){
                                            strLikeView+=",";
                                        }                    
						            }      
                                    cFriend = cFriend + 1;
                                    total = total-1;
                                }
                                fromId = uId;
                            }
                        }							    							
					    if(total>0){
						    strLikeView += "&nbsp;và " + '<a href="#" onclick="return zmlw.show(' + feedId + ');">' + total + '&nbsp;người khác</a>';
					    }

					    strLikeView += "&nbsp;thích điều này.";
					    likeView.html(strLikeView);
                    }
                }
                zwg.fillWg();
            }
        });
	}
	function like(feedId)
	{
        var btnLike = zm("#zmevw-" + feedId);
		var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));
        var isliked = btnLike.attr('isliked');
        var type = (isliked=="0") ? "like" : "unlike";     
        // like
        doPost("http://me.zing.vn/like/like",
        {
            "type": type,
            "feedId":feedId,
            "appId": meta.appId,
            "actId": meta.actId,
            "objectId": meta.objectId,
            "ownerId": zmConfig.viewerId,
            "viewerId": zmConfig.viewerId,
            "userIdFrom":meta.fromUserId,
            "userIdTo":meta.toUserId,
            "time": meta.cTime,
            "signKey": meta.signKeyCreate
        }, {
            "dataType": "json"
        },
        function(data) {
            if(data.errorCode>=0) {
                var cfeed = zm('#zmdboxctn');
		        if(type=="like"){
			        if(data.point > 0){
                        zmCommon.plusPoint("+" + data.point, 'zmdboxctn', {offsetLeft: -20, start: cfeed.top()+ cfeed.height() , end:cfeed.top(), inWindow:true});
			        }
			        else{
                        zmCommon.fullPoint(2 , 'zmdboxctn', {offsetLeft: -20, start: cfeed.top()+ cfeed.height() , end:cfeed.top(), inWindow:true});
			        }
		        }
		        else{
                    zmCommon.plusPoint("-2", 'zmdboxctn', {offsetLeft: -10, start: cfeed.top()+ cfeed.height() , end:cfeed.top(), inWindow:true});
		        }
            }                
        },
        function(){
            // having error               
        });            
        if(isliked==0){
	        btnLike.attr('isliked', "1");
	        btnLike.html("Bỏ Thích");
			zm("#zmflikew-" + feedId).attr("class", "icn12x12 icn_likegrey12x12");	
        }
        else{
	        btnLike.attr('isliked', "0");
	        btnLike.html("Thích");
			zm("#zmflikew-" + feedId).attr("class", "icn12x12 icn_like12x12");
        }

        zm("#divLikew_" + feedId).css("display", "block");
        var likeView = zm("#like-vieww-" + feedId);
        var strLikeView = likeView.html();

        if(isliked==1){
	        if(strLikeView == "Bạn thích điều này."){
		        zm("#divLikew_" + feedId).css("display", "none");
		        strLikeView = "";
	        }
	        else{
		        strLikeView = strLikeView.replace(/Bạn,&nbsp;/, "");
	        }
        }
        else{
	        if(strLikeView.length>0){
		        strLikeView = "Bạn,&nbsp;" + strLikeView;
	        }
	        else{
		        strLikeView = "Bạn thích điều này.";
	        }
        }
        likeView.html(strLikeView);

        try{zmev.vote(feedId);} catch(e){}
        return false;
	}

	function show(feedId)
	{
		var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));
        doPost('http://me.zing.vn/like/show', {"appId": meta.appId, "feedId": feedId, "objectId": meta.objectId, "viewerId": zmConfig.viewerId, "page": 0, "fromId":0, "count": 5},{dataType:"json"}, 
		function(data)
		{
            if(data.errorCode>=0){
                zm(divLike).html(data.body);                   
			    var h = zm(divLike).outerHeight();
                scrollui.setContent(data.data);
		        scrollui.setHeight(h);
		        detailLike.show();
            }
		});			
		return false;
	}
    function more (feedId){
        var meta = getMetadata(zm("#cmtfw_" + feedId).attr("rel"));
        var page = parseInt(zm("#vote_npage").html());
        var fromId = zm("#vote_lastId").html();
        doPost('http://me.zing.vn/like/more', {"appId": meta.appId, "feedId": feedId, "objectId": meta.objectId, "viewerId": zmConfig.viewerId, "page": page, "fromId":fromId, "count": 5},{dataType:"json"}, 
		function(data)
		{
            if(data.errorCode>=0){
                zm("#smorev").append(data.data);
                if(data.more==1){
                    zm("#vote_npage").html(page + 1);
                    zm("#vote_lastId").html(data.lastId);
                }
                else{
                    zm("#lmorev").remove();
                }
            }
            else{
                zm("#lmorev").remove();
            }
		});			
		return false;
    }
    function getMetadata(strMetadata)
    {
        var metaData = strMetadata.split(':');        
        return {
            appId:metaData[0],
            objectId:metaData[1],
            fromUserId:metaData[2],
            toUserId:metaData[3],
            cTime:metaData[4],
            signKeyCreate:metaData[5],
            isAdmin:metaData[6],
            totalComment:metaData[7],
            actId:metaData[8]
        };
    }
	this.load = load;
	this.like = like;
	this.show = show;
	this.more = more;
}();
