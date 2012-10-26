var zmChatProxy = {
	mainFrame: null,
	params: {},
	getParams: function() {
		return {
			uid: zmChatProxy.params.userId,
			ts: zmChatProxy.params.timestamp,
			sign: zmChatProxy.params.sign
		};
	},
	setOnOff: function(onoff){
		zm.post('/onoff', zm.extend(zmChatProxy.getParams(), {status: (onoff ? '1' : '0')}));
	},
	loadFriends: function(uids) {
		var data = zmChatProxy.getParams();
		data.ref = document.referrer;
		if (uids && uids.length > 0)
			zm.extend(data, {current: uids.join(',')});
		zm.post('/friends', data, {dataType:"json"}, function(resp) {
			if(!resp)
				return;
			zmChatProxy.mainFrame.zmChat.onLoadFriends(resp.data);
		});
	},
	send: function(msg){
		zm.post('/send', zm.extend(zmChatProxy.getParams(), {toUserId: msg.to, msg: msg.msg, id: msg.id, t1: msg.time}),
			{dataType:"json"},
			function(resp) {
				if (resp.err != 0)
					zmChatProxy.mainFrame.zmChat.onError({to: msg.to, sentMsg: msg, err: resp.err});
			}
		);
	},
	getChatHistory: function(uid, full){
		zm.post('/getsession', zm.extend(zmChatProxy.getParams(), {withUserId: uid, full: full ? '1' : undefined}),
			{dataType:"json"},
			function(resp) {
				if (resp.err != 0)
					zmChatProxy.mainFrame.zmChat.onError({to: uid, err: resp.err});
				else {
					var history = resp.data;
					zm.each(history, function() {
						this.time = this.time * 1000;
					});
					zmChatProxy.mainFrame.zmChat.updateHistory(uid, history, full);
				}
			}
		);
	},
	recentChanged: function(recent){
		var cache = zm.storage.get('zmchat.chatlist.' + zmChatProxy.params.userId);
		if (cache) {
			cache.data.list = recent;
			zm.storage.put('zmchat.chatlist.' + zmChatProxy.params.userId, cache, 120);
		}
		zm.post('/updatechatlist', zm.extend(zmChatProxy.getParams(), {value: zm.serialize(recent)}));
	},
	idle: function(status){
		if (zmChatProxy.mainFrame.zmChat.userId) {
			zm.post('/idle', zm.extend(zmChatProxy.getParams(), {status: (status ? '1' : '0')}),
				{dataType: "json"},
				function(resp) {
					if (resp.data && resp.data.sign && resp.data.ts) {
						zmChatProxy.params.sign = resp.data.sign;
						zmChatProxy.params.timestamp = resp.data.ts;
					}
				}
			);
		}
	},
	typing: function(uid, status, ts){
		zm.post('/typing', zm.extend(zmChatProxy.getParams(), {u: uid, status: (status ? '1' : '0'), t1: ts}));
	},
	loadAvatar: function(uid) {
		zm.post('/avatar', zm.extend(zmChatProxy.getParams(), {user: uid}), {dataType: "json"}, function(resp) {
			resp && zmChatProxy.mainFrame.zmChat.updateAvatar(uid, resp.data);
		});
	},
	getInfo: function(data, callback) {
		zm.post('/info', zm.extend(zmChatProxy.getParams(), data), {dataType: "json"}, function(resp) {
			resp && zm.isFunction(callback) && callback(resp);
		});
	},
	config: function(config, callback) {
		zm.storage.remove('zmchat.init.' + zmChatProxy.params.userId);
		zm.post('/config', zm.extend(zmChatProxy.getParams(), config), {dataType: "json"}, function(resp) {
			resp && zm.isFunction(callback) && callback(resp);
		});
	},
	saveSession: function(uid, callback) {
		zm.post('/savess', zm.extend(zmChatProxy.getParams(), {withUid: uid}), {dataType: "json"}, function(resp) {
			resp && zm.isFunction(callback) && callback(resp);
		});
	},
	sendPm: function(msg, callback) {
		zm.post('/sendpm', zm.extend(zmChatProxy.getParams(), msg), {dataType: "json"}, function(resp) {
			resp && zm.isFunction(callback) && callback(resp);
		});
	},
	init: function(){
		if ((!window.parent) || (!window.parent.zmChat) || (!window.parent.zmNotifications) || (typeof zm == 'undefined'))
			return;
		zmChatProxy.mainFrame = window.parent;
		var zmChat = window.parent.zmChat;
		zmChat.getChatHistory(this.getChatHistory);
		zmChat.loadFriends(this.loadFriends);
		zmChat.recentChanged(this.recentChanged);
		zmChat.send(this.send);
		zmChat.setOnOff(this.setOnOff);
		zmChat.typing(this.typing);
		zmChat.loadAvatar(this.loadAvatar);
		zmChat.getInfo(this.getInfo);
		zmChat.config(this.config);
		zmChat.saveSession(this.saveSession);
		zmChat.sendPm(this.sendPm);
		setTimeout(function() {zmChat.idle(zmChatProxy.idle);}, 5000);
		var uid = zmChatProxy.params.userId,
			initcdata = zm.storage.get('zmchat.init.' + uid);
		if (initcdata)
			zmChat.initData(initcdata);
		else
			zm.post('/init', zmChatProxy.getParams(), {dataType:"json"}, function(resp) {
				if (!resp || resp.err == -2) {
					zm.get('/auth', {dataType:"json"}, function(resp) {
						if (resp && resp.data) {
							zmChatProxy.params.timestamp = resp.data.ts;
							zmChatProxy.params.sign = resp.data.sign;
							zm.post('/init', zmChatProxy.getParams(), {dataType:"json"}, function(resp) {
								zmChat.initData(resp);
								zm.storage.put('zmchat.init.' + uid, resp, 300);
							});
						}
					});
				} else {
					zmChat.initData(resp);
					zm.storage.put('zmchat.init.' + uid, resp, 300);
				}
			});
		var listcdata = zm.storage.get('zmchat.chatlist.' + uid);
		function updateChatList(resp) {
				zmChat.userId = resp.data.uid;
				zmChat.username = resp.data.username;
				zmChat.userDisplayname = resp.data.displayname;
				zmChat.avatar = resp.data.avatar;
				if (resp.data.list)
					zmChat.updateRecent(resp.data.list);
			};
		if (listcdata)
			updateChatList(listcdata);
		else
			zm.post('/getchatlist', zmChatProxy.getParams(), {dataType:"json"}, function(resp) {
				updateChatList(resp);
				zm.storage.put('zmchat.chatlist.' + uid, resp, 120);
			});
		var zmNotifications = window.parent.zmNotifications;
		zmNotifications.notify('webchat.onoff', function(type, data) {
			zm.storage.remove('zmchat.init.' + zmChatProxy.params.userId);
			zmChat.updateOnOff(data);
		});
		zmNotifications.notify('webchat', function(type, data) {
			data.time = data.time * 1000;
			zmChat.onMessage(data);
		});
		zmNotifications.notify('webchat.typing', function(type, data) {
			zmChat.updateTyping(data.to, zm.intval(data.status.substr(0, 1)), zm.intval(data.status.substr(2)));
		});
	}
};