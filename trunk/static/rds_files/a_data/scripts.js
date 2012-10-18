jQuery.fn.disableTextSelect = function() {
    return this.each(function() {
        $(this).css({
            'MozUserSelect' : 'none'
        }).bind('selectstart', function() {
            return false;
        }).mousedown(function() {
            return false;
        });
    });
};


$(function(){
    var boxy, boxyFL;

    showMessage = function(data)
    {
        new Boxy(data, {
            fixed:true,
            modal:true,
            closeable:true,
            draggable:true,
            closeText : "x",
            title:"Thông báo"
        })
    }
    
    showPopup = function(statusId, statusText)
    {
        if (typeof boxy == 'undefined')
        {
            boxy = new Boxy('#popup-box', {
                fixed:true,
                modal:true,
                closeable:true,
                draggable:true,
                closeText : "x",
                title:"Đăng lên Trang Cá Nhân của bạn và Trang Chủ của bạn bè"
            });
        }

        $("#status-text").val(statusText);
	$("#status-text").attr('readonly', 'readonly');
        $("#status-id").val(statusId);

        boxy.show();
        
        return false;
    }

    doSearch = function()
    {
        var kw = $("#friendlist-box input[name=friend_kw]").val();
        FriendList.keyword = kw;
        FriendList.getList(kw, 1, false);
    }

    showFriendList = function(statusId)
    {
        if (typeof boxyFL == 'undefined')
        {
            boxyFL = new Boxy('#friendlist-box', {
                fixed:true,
                modal:true,
                closeable:true,
                draggable:true,
                closeText : "x",
                title:"Đăng lên tường bạn bè"
            });

            $('#friendlist-box input[name=friend_kw]').keyup(function(e) {
                if(e.keyCode == 13) {
                    doSearch();
                }
            });

            $("#friendlist-box .icon_search").click(function(){
                doSearch();
            });

            $("#friend_update").click(function(e){
                e.preventDefault();
                if (FriendList.loading)
                {
                    return false;
                }
                //boxyFL.hide();

                $("#friend_na").remove();

                FriendList.showLoader();

                $.post('/api/index/updatefriend', {},
                    function(data){
                        FriendList.loading = false;
                        
                        showMessage(data);
                        FriendList.getList(FriendList.keyword, 1, false);
                    }
                    );
            });

            FriendList.getList('', 1, false);
        }

        boxyFL.show();

        

        return false;
    }

    closePopup = function()
    {
        if(boxy)
        {
            boxy.hide();
        }
        return false;
    }

    closeFriendList = function()
    {
        if(boxyFL)
        {
            boxyFL.hide();
        }
        return false;
    }

    postStatus = function(sendFriend)
    {
        closePopup();
        
        var statusId;

        if (typeof sendFriend != 'undefined' && sendFriend == true)
        {
            statusId = StatusList.getCurrentId();
            var friendId = $("#friendlist-box .info_tag input:radio:checked").val();

            if (!friendId)
            {
                alert('Bạn chưa chọn bạn');
                return false;
            }

            $.post('/api/status/post', {
                id : statusId,
                friend : friendId
            }, function(data){
                closeFriendList();
                showMessage(data);
            }
            );
        }
        else
        {
            var statusText = $("#status-text").val();
            statusId = $("#status-id").val();

            
            $.post('/api/status/post', {
                id : statusId,
                status : statusText
            }, function(data){
                closeFriendList();
                showMessage(data);
            }
            );
        }

        
        return false;
    }


    var StatusList = {
        index : -1,
        list : [],
        tagId : null,
	tag : null,
        enable : true,
        timeout : 800,
        hasMore : true,

        favStatus : function(id)
        {
            if (!this.enable)
            {
                return;
            }

            this.enable = false;

            $.post('/api/status/fav', {
                id : id
            }, function(data){
                StatusList.enable = true;

                showMessage(data);

            //alert(data.message);
            });
        },

        voteStatus : function(point)
        {
            if (!this.enable)
            {
                return;
            }

            this.enable = false;

            $.post('/api/status/vote', {
                id : this.getCurrentId(),
                p : point
            }, function(data){
                StatusList.enable = true;

                $("#voting-feedback").show();
                $("#voting-buttons").hide();

                showMessage(data);
            });
        },

        deleteFav : function(id)
        {
            $.post('/api/favorites/delete', {
                id : id
            }, function(data){
                if (data.code == '1')
                {
                    window.location = window.location;
                }
                else
                {
                    alert(data.message);
                }
            }, 'json');
        },
        

        setTag : function(tid, tag)
        {
            this.enable = true;
            this.hasMore = true;
            
            //tid = parseInt(tid);

            if (tid)
            {
                if (this.tagId != tid)
                {
                    $("#current-tag b").html(tag).parent().css("visibility","visible");
                    this.tagId = tid;
		    this.tag = tag;
                    this.reset();
                    this.getRandomStatus();
                }
                else
                {
                    this.shuffleStatus();
                }
                
            }

        },

        clearTag : function()
        {
            this.hasMore = true;
            
            $("#current-tag b").html("").parent().css("visibility","hidden");
            this.tagId = null;
            this.reset();
            this.getRandomStatus();
        },

        getRandomStatus : function()
        {
            this.enable = true;
            
            var time = new Date().getTime();
            var url = '/api/status/random?t='+time;

            if (this.tagId != null)
            {
                url += '&tid=' + this.tagId;
		url += '&tag=' + this.tag;
            }

            $.getJSON(url, function(data){
                for (var i=0;i<data.length;i++)
                {
                    StatusList.add(data[i]);
                }

                if (data.length)
                {
                    StatusList.shuffleStatus();
                }
            });
        },

        showStatus : function(status)
        {
            $("#voting-feedback").hide();
            $("#voting-buttons").show();

            $("#status").html(status.content);
            $("#status-tags span").html("");

            for (var name in status.tags)
            {
                $("#status-tags span").append('<a href="#" rel="'+ name +'">'+ status.tags[name] +'</a> ');
            }

            this.enableButtons();

            this.toogleBackButton();

            if (typeof status.has_more != 'undefined')
            {
                this.hasMore = false;
            }
        },

        disableButtons : function()
        {
            this.enable = false;
            $("#btn-shuffle").parent().removeClass("button_shuffle").addClass("button_shuffle_dis");
            $("#btn-back").parent().removeClass("button_back").addClass("button_back_dis");
        },

        enableButtons : function()
        {
            this.enable = true;
            $("#btn-shuffle").parent().removeClass("button_shuffle_dis").addClass("button_shuffle");

            var btnBack = $("#btn-back").parent();

            if (this.index > 0)
            {
                // enable
                btnBack.removeClass("button_back_dis").addClass("button_back");
            }

            else
            {
                // disable
                btnBack.removeClass("button_back").addClass("button_back_dis");
            }

        //$("#btn-back").parent().removeClass("button_back_dis").addClass("button_back");
        },

        loading : function()
        {
            $("#status").html("<img src='http://static.me.zing.vn/v3/rds/images/loader.gif' width='16' height='16' alt='loading' />");
            this.disableButtons();
            setTimeout(function(){
                var status = StatusList.getCurrentStatus();
                StatusList.showStatus(status);
            }, this.timeout);
        },

        shuffleStatus : function()
        {
            
            if (!this.enable)
            {
                return;
            }
            var status;

            if (!this.hasMore)
            {
                this.hasMore = true;
                this.index = -1;
            }

            if (this.next() && (status = this.getCurrentStatus()))
            {
                this.loading();
            //this.showStatus(status);
            }
            else
            {
                this.getRandomStatus();
            }

            
        },

        toogleBackButton : function()
        {
            var btn = $("#btn-back").parent();
            
            if (this.index > 0)
            {
                // enable
                btn.removeClass("button_back_dis").addClass("button_back");
            }
            
            else
            {
                // disable
                btn.removeClass("button_back").addClass("button_back_dis");
            }
        },


        
        getCurrentId : function()
        {
            return this.list[this.index].id;
        },

        getCurrentStatus : function()
        {
            return (this.list[this.index] != undefined)?this.list[this.index]:false;
        },

        prevStatus : function()
        {
            if (!this.enable)
            {
                return;
            }
            
            var status;

            if (this.prev() && (status = this.getCurrentStatus()))
            {
                this.loading();
            //this.showStatus(status);
            }
        },

        next : function()
        {
            if (this.index + 1 > this.size() - 1)
            {
                return false;
            }
            ++this.index;
            return true;
        },

        prev : function()
        {
            if (this.index -1 < 0)
            {
                return false;
            }
            --this.index;
            return true;
        },

        size : function()
        {
            return this.list.length;
        },

        add : function(data)
        {
            this.list.push(data);
        },

        reset : function()
        {
            this.list = [];
            this.index = -1;
        }
    };


    var FriendList = {
        friends	 : null,
        total : 0,
        currentPage : 0,
        keyword : null,
        loading : false,

        showLoader : function()
        {
            FriendList.loading = true;
            var html = "<center><img src='http://static.me.zing.vn/v3/rds/images/loader.gif' width='16' height='16' alt='loading' /></center>";
            $("#friendlist-box .info_tag ul").html("<li>"+ html +"</li>");
        },

        getList : function(kw, page, update)
        {
            if (FriendList.loading)
            {
                return;
            }
            
            FriendList.currentPage = page;
            
            var url = '/api/index/getfriend';

            if (kw)
            {
                url += '/kw/' + encodeURI(kw);
            }

            url += '/page/' + page;

            if(update)
            {
                url += '/reload/1';
            }

            FriendList.showLoader();

            $.get(url, function(data){

                FriendList.loading = false;
                
                $("#friend_na").remove();
                
                if(data.total>0)
                {
                    var maxPage = Math.ceil(data.total / 12);
                    FriendList.showFriends(data.items, maxPage, FriendList.currentPage);
                }
                else
                {
                    $("#friendlist-box .info_tag ul").html('');

                    var newHtml='';
                    newHtml +='<div id="friend_na">';
                    newHtml += '<b>Chưa có bạn bè nào thỏa điều kiện!</b>';
                    newHtml +='</div>';
                    newHtml +='<div class="clear"></div>';
                    $(newHtml).insertBefore('#friendlist-box .info_tag ul');

                }

            }, 'json');
        },

        showFriends : function(items, maxPage, page)
        {
            var html = '';
            for (friend in items)
            {
                var label = '<li><input id="frd_'+ items[friend].userid +'" type="radio" name="checked_friend" value="'+ items[friend].userid +'" /> <label for="frd_'+ items[friend].userid +'">' + items[friend].displayname + '</label>';
                label += '</li>';

                html += label;
            }

            $("#friendlist-box .info_tag ul").html(html);

            if((page+1) <= maxPage){
                $('#friend_next').unbind('click')
                .css({
                    cursor : 'pointer'
                })
                .attr("src", 'http://static.me.zing.vn/v3/rds/images/zme_irphoto-ac.gif')
                .click(function(){
                    if (FriendList.loading)
                    {
                        return false;
                    }
                    FriendList.getList(FriendList.keyword, page+1, false);
                });
            } else {
                $('#friend_next').attr("src", 'http://static.me.zing.vn/v3/rds/images/zme_irphoto.gif')
                .css({
                    cursor : 'default'
                })
                .unbind('click');
            }

            if(page > 1){
                $('#friend_pre').unbind('click')
                .css({
                    cursor : 'pointer'
                })
                .attr("src", 'http://static.me.zing.vn/v3/rds/images/zme_ilphoto-ac.gif')
                .click(function(){
                    if (FriendList.loading)
                    {
                        return false;
                    }
                    FriendList.getList(FriendList.keyword, page-1, false);
                });
            } else {
                $('#friend_pre').attr("src", 'http://static.me.zing.vn/v3/rds/images/zme_ilphoto.gif')
                .css({
                    cursor : 'default'
                })
                .unbind('click');
            }
            
        }
    }

    $("#status, .boxCMT").disableTextSelect();

    if ($("#btn-shuffle").length)
    {
        $("#btn-set").click(function(e){
            e.preventDefault();
			showPopup(StatusList.getCurrentId(), StatusList.getCurrentStatus().content);
        });

        $("#btn-set-friend").click(function(e){
            e.preventDefault();
            showFriendList(StatusList.getCurrentId());
        });

        $("#btn-shuffle").click(function(e){
            e.preventDefault();
            StatusList.shuffleStatus();
        });

        $("#btn-back").click(function(e){
            e.preventDefault();
            StatusList.prevStatus();
        });

        $("#btn-fav").click(function(e){
            e.preventDefault();
            StatusList.favStatus(StatusList.getCurrentId());
        });

        $("#status-tags a, #tags a").live("click", function(e){
            e.preventDefault();
            var tagId = $(this).attr('rel');

            var tag = $(this).text();

            StatusList.setTag(tagId, tag);
        });

        $("#current-tag a").live("click", function(e){
            e.preventDefault();
            StatusList.clearTag();
        });

        $("#btn-like, #btn-dislike").live("click", function(e) {
            e.preventDefault();
            if ($(this).attr("id") == "btn-like")
            {
                StatusList.voteStatus(1);
            }
            else
            {
                StatusList.voteStatus(-1);
            }
        });

        if (typeof selectedTag != 'undefined')
        {
            StatusList.setTag(selectedTag.id, selectedTag.tag);
        }
        else
        {
            StatusList.shuffleStatus();
        }
    }


    if ($("a.fav-delete").length)
    {
        $("a.fav-delete").click(function(i){
            var id = $(this).attr('rel');

            Boxy.confirm("Bạn có muốn xóa status này khỏi danh sách ưa thích không ?", function() {
                StatusList.deleteFav(id);
            //alert('Confirmed!');
            }, {
                title: 'Xóa'
            });
            return false;

        });
    }

    if ($("a.fav-status").length)
    {
        $("a.fav-status").click(function(){
            var id = $(this).attr('rel');
            StatusList.favStatus(id);

            return false;
        });
    }

    
});
