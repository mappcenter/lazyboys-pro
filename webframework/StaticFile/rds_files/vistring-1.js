var ViString=(function(){var c=[["á","à","ả","ã","ạ","â","ấ","ầ","ẩ","ẫ","ậ","ă","ắ","ằ","ẳ","ẵ","ặ"],["Á","À","Ả","Ã","Ạ","Â","Ấ","Ầ","Ẩ","Ẫ","Ậ","Ă","Ắ","Ằ","Ẳ","Ẵ","Ặ"],["é","è","ẻ","ẽ","ẹ","ê","ế","ề","ể","ễ","ệ"],["É","È","Ẻ","Ẽ","Ẹ","Ê","Ế","Ề","Ể","Ễ","Ệ"],["ú","ù","ủ","ũ","ụ","ư","ứ","ừ","ử","ữ","ự"],["Ú","Ù","Ủ","Ũ","Ụ","Ư","Ứ","Ừ","Ử","Ữ","Ự"],["í","ì","ỉ","ĩ","ị"],["Í","Ì","Ỉ","Ĩ","Ị"],["ý","ỳ","ỷ","ỹ","ỵ"],["Ý","Ỳ","Ỷ","Ỹ","Ỵ"],["ó","ò","ỏ","õ","ọ","ô","ố","ồ","ổ","ỗ","ộ","ơ","ớ","ờ","ở","ỡ","ợ"],["Ó","Ò","Ỏ","Õ","Ọ","Ô","Ố","Ồ","Ổ","Ỗ","Ộ","Ơ","Ớ","Ờ","Ở","Ỡ","Ợ"],["đ"],["Đ"]],d=["a","A","e","E","u","U","i","I","y","Y","o","O","d","D"];function b(g){if(typeof g=="string"){for(var f=0;f<c.length;f++){for(var e=0;e<c[f].length;e++){g=g.replace(new RegExp(c[f][e],"g"),d[f])}}}return g}function a(e){return(e instanceof RegExp)?new RegExp(e.source,(e.global?"g":"")+(e.ignoreCase?"i":"")+(e.multiline?"m":"")):e}return{normalize:function(g){if(g instanceof RegExp){var f=g.source,e="";if(g.global){e+="g"}if(g.ignoreCase){e+="i"}if(g.multiline){e+="m"}f=b(f);return new RegExp(f,e)}else{return b(g)}},indexOf:function(g,f,e){g=ViString.normalize(g);f=ViString.normalize(f);if(zm.isString(e)&&e.indexOf("i")>-1){g=g.toLowerCase();f=f.toLowerCase()}return f.indexOf(g)},search:function(f,e){e=ViString.normalize(e);f=ViString.normalize(f);return e.search(f)},replace:function(v,g,p){if(v){var f=ViString.normalize(v),h=ViString.normalize(p),n=h.match(f),s=h.search(f);if(n&&n.length>0){var o=n[0],q,m=0;while((q=h.indexOf(o,s))>=0&&m<n.length){var r=p.substr(q,o.length),t=a(f).exec(o),l=new Array(),u=0;for(var k=1;k<t.length;k++){if(t[k]&&zm.isString(t[k])){l[k]=r.substr(o.indexOf(t[k],u),t[k].length);u=r.indexOf(l[k])+l[k].length}else{l[k]=""}}var e=g.replace(/\$(\d)/g,function(j,w){var i=parseInt(w);if(l&&typeof l[i]=="string"){return l[i]}return j});p=p.substr(0,q)+e+p.substr(q+o.length);h=h.replace(o,e);s=q+e.length;m++}}}return p}}})();