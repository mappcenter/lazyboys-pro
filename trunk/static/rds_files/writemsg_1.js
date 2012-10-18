var zmAppMsg = {

	URL_MSG: zmConfig.ZMSG_URL,
	MAX_LENGH_CONTENT: 300,
	MAX_LENGH_TITLE: 100,
	currentcompose:false,
	bxCompose :null,
	
	//Fit noi dung 
	FitToContent: function(id, maxHeight){
		var text = id && id.style ? id : document.getElementById(id);
		if (!text)
			return;
		var adjustedHeight = text.clientHeight;
		if (!maxHeight || maxHeight > adjustedHeight) {
			adjustedHeight = Math.max(text.scrollHeight, adjustedHeight);
			if (maxHeight)
				adjustedHeight = Math.min(maxHeight, adjustedHeight);
			if (adjustedHeight > text.clientHeight)
				text.style.height = adjustedHeight + "px";
		}
		zmAppMsg.limitLength('tbccontent',zmAppMsg.MAX_LENGH_CONTENT);
	},
	
	alert:function(title,content){
		zm.Boxy.alert("<p class='textalert'>" + content+ "</p>",title,2000);		
	},

	limitLength:function(id,MAX_LENGH) {
		var content =zm('#'+id).val();
		if(content.length> MAX_LENGH)
		{
			content=content.substr(0,MAX_LENGH);
			zm('#'+id).val(content);			
		}
		zm('#'+id+'_notice').html('(Tối đa ' +(MAX_LENGH - content.length)+ ' ký tự)');
	},
		
	showCompose:function(userName){		
		if(zmAppMsg.currentcompose){
			return;
		}
		if(zmAppMsg.bxCompose!=null)
		{
			zm('#tbctitle').val('');
			zm('#tbccontent').val('');
			zm('#tbccontent').css('height','56px');
			zm('#tbctitle_notice').html('(Tối đa ' +zmAppMsg.MAX_LENGH_TITLE + ' ký tự)');
			zm('#tbccontent_notice').html('(Tối đa ' +zmAppMsg.MAX_LENGH_CONTENT + ' ký tự)');
			zmAppMsg.bxCompose.show();
			zm('#tbctitle').focus();
			zmAppMsg.currentcompose=true;			
			return;
		}		
		zm.getJSON(zmAppMsg.URL_MSG+'/writemailv3/sendirect',function(data){			
			if(data==null || data==''){
				zmAppMsg.currentcompose=false;
				return;
			}
			zmAppMsg.currentcompose=true;
			zmAppMsg.bxCompose = new zmCore.Boxy({
				title: 'Gửi Tin Nhắn Riêng Cho Bạn Bè',
				content: data.content,
				okButton: 'Gửi Tin',
				cancelButton: 'Bỏ Qua',
				contentClass: 'lbx_widlar',
				onOk:function(){
					var success = zmAppMsg.submitsendirect(userName);
					if(success){												
						zmAppMsg.currentcompose=false;
						return true;
					}
					else{
						return false;
					}
				},
				onCancel:function(){					
					zmAppMsg.currentcompose=false;
				},
				beforeHide:function(){
					zmAppMsg.currentcompose=false;
				}
			});
			zmAppMsg.bxCompose.show();
			zm('#tbctitle').focus();
		});
	},

	
	submitsendirect:function(userName) {
		var tbctitle =zm('#tbctitle').val();
		var tbccontent = zm('#tbccontent').val();
		//Replace tag		
       	tbctitle= zmAppMsg.replaceAll(tbctitle, '\n', '<br/>');				 	
       	tbccontent= zmAppMsg.replaceAll(tbccontent, '\n', '<br/>');	

		if(tbccontent!='' && tbctitle!=''){			
			zm.getJSON(zmAppMsg.URL_MSG+"/writemailv3/sendmaildirect?title="+
				tbctitle + "&friendcompose="+ userName + "&content="+ tbccontent, function(data) {
				zmAppMsg.successdirect(data.threadId);
			});
			return true;
		}
		zmAppMsg.alert('Lỗi','Xin vui lòng nhập nội dung.');		
		return false;
	},

	successdirect:function(threadId){
		var msg='Bạn đã gửi tin nhắn thành công!';
		var title='Thông Báo';
		if((threadId=='')||(threadId<0)){
			msg='Có lỗi xảy ra trong quá trình gởi tin nhắn !';
			title='Lỗi';
		}
		zmAppMsg.bxCompose.hide();
		zmAppMsg.alert(title,msg);				
	},	
	
	replaceAll:function(text, strA, strB)
    {
        while ( text.indexOf(strA) != -1) {
            text = text.replace(strA,strB);
        }
        return text;
    }

};





