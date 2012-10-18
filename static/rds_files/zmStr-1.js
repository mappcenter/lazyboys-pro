(function(){
	var dtIds = {},
		dtTimer = 0,
		onoff = true,
		dtInterval = 60000;
	function fill2(value) {
		return value < 10 ? ('0' + value) : value;
	}
	function format(t){
		t = zm.intval(t);
		if (t <= 0) return '';
		var e = [[11, 'sáng'], [14, 'trưa'], [19, 'chiều']],
			f = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'],
			g = new Date(),
			j = new Date(t * 1000),
			d = Math.floor(g.getTime() / 1000) - t;
		if (d < 60) return (d < 0 ? 0 : d).toString() + ' giây trước';
		if ( d < 3600) return Math.floor(d / 60) + ' phút trước';
		if (d < 43200) return Math.floor(d / 3600) + ' tiếng trước';
		var h = j.getHours(),
			m = fill2(j.getMinutes());
		if (d < 518400) {
			var b = 'tối';
			for (var i = 0; i < 3; i++)
				if (h < e[i][0]) {
					b = e[i][1];
					break;
				}
			d = (g.getDay() + 7 - j.getDay()) % 7;
			var k = '';
			if (d == 0)
				k = 'hôm nay';
			else if (d == 1)
				k = 'hôm qua';
			else k = f[j.getDay()];
			return fill2(h<=12?h:h%12).toString() + ':' + m + ' ' + b + ' ' + k;
		}
		h = fill2(h);
		return h + ':' + m + ' ' + fill2(j.getDate()) + '/' + fill2(j.getMonth()+1) + '/' + j.getFullYear();
	}
	function renderTime(id){
		var z = zm('#' + id),
			ts = parseInt(z.attr('rel'));
		z.html(format(ts));
		return ts;
	}
	function timer(){
		var current = Math.round((new Date()).getTime()/1000);
		for(var id in dtIds){
			if (dtIds[id]<=current){
				var ts = renderTime(id);
				if (current - ts<3600)
					dtIds[id] = current + 60;
				else if (current - ts<86400)
					dtIds[id] = current + 3600;
				else
					dtIds[id] = Number.MAX_VALUE;
			}
		}
		if (onoff)
			dtTimer = setTimeout(timer, dtInterval);
	}
	window.zmDateTime = {
		format: format,
		add: function(e, force) {
			if (zm.isArray(e)){
				for(var i=0, t; t = e[i]; i++)
					if (force || dtIds[t]==undefined){
						dtIds[t] = 0;
						renderTime(t);
					}
			}
			else if (dtIds[e]==undefined){
				dtIds[e] = 0;
				renderTime(e);
			}
		},
		setOnOff: function(val){
			onoff = val;
			if (!val && dtTimer!=0){
				clearTimeout(dtTimer);
				dtTimer = 0;
			}
		},
		setInterval: function(interval){
			dtInterval = interval;
		}
	};
	setTimeout(timer, dtInterval);
})();
(function(){
	function _render(tpl, data){
		for(var f in data)
			tpl = tpl.replace(new RegExp("{" + f + "}", "g"), data[f]);
		tpl = tpl.replace(/{[a-z_0-9]+}/gi, "");
		return tpl;
	}
	window.zmTemplate = {
		render: function(template, data){
			if (zm.isArray(data)){
				var result = new Array();
				for(var i=0; i<data.length; i++)
					result.push(_render(template, data));
				return result;
			}
			else
				return _render(template, data);
		}
	};
})();
(function() {
	window.zmString = {
		formatNumber: function(number, separator) {
			try {
				number = parseFloat(number);
			} catch(e) {}
			if (isNaN(number))
				return '';
			number = number.toString();
			separator = separator || '.';
			var result = new Array(),
				t = number.split('.'),
				fractional = '';
			if (t && t[1]) {
				fractional = (separator == '.' ? ',' : '.') + t[1];
				number = t[0];
			}
			for (var k = number.length; k > 0; k -= 3)
				result.push(number.substring(k - 3, k));
			return result.reverse().join(separator) + fractional;
		}
	};
})();