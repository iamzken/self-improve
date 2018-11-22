/**
 * 获得系统时间戳
 */
window.timestamp = function(){
	return new Date().getTime();
};

/**
* 单位换算
*/
window.convertSize = function  (size) {
     if(!size) {
     return '0 Bytes';
     }
     var sizeNames = [' Byte', ' KB', ' MB', ' GB', ' TB', ' PB', ' EB', ' ZB', ' YB'];
     var i = Math.floor(Math.log(size)/Math.log(1024));
     var s = (size/Math.pow(1024, Math.floor(i)));
     return (("" + s).indexOf(".") == -1 ? s : s.toFixed(2)) + sizeNames[i];
};

/**
 * 时间转换
 * @param time
 */
window.convertTime = function(time,format){

    var ftime = function(t){
        return (t < 10?("0" + t):("" + t));
    };
    time = time / 1000;

    var s = Math.round(time % 60);
    var m = Math.floor(time / 60 % 60);
    var h = Math.floor(time / 60 / 60);
    if (s == 60){
        s = 0;
        m++;
    }
    if (m == 60){
        m = 0;
        h++;
    }
    if(format){
        var o = {"h+": h, "m+": m, "s+": s};
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }else {
        return ftime(h) + ":" + ftime(m) + ":" + ftime(s);
    }

};

/**
 * 日期自定义
 * @param systime
 * @param time
 * @returns {string}
 */
window.subformattime = function(systime,time){
    var sub = systime - time;
    var str = "";
    var subtime = systime - time;
    var y = Math.floor(subtime / (3600 * 1000 * 24 * 360)); //年
    var M = Math.floor(subtime / (24 * 3600 * 1000 * 30)) % 12; //月
    var d = Math.floor(subtime / (24 * 3600 * 1000));//日
    var leave1 = subtime % (24 * 3600 * 1000);
    var h = Math.floor(leave1 / (3600 * 1000)); //时
    var leave2 = leave1 % (3600 * 1000);
    var m = Math.floor(leave2 / (60 * 1000)); //分
    var leave3 = leave2 % (60 * 1000);
    var s = Math.round(leave3 / 1000); //秒

   // alert(y + "," + M + "," + d + "," + (y == 0) + "," + (M == 0) + "," + (d >= 1) + "," + (d < 7));

    if(y == 0 && M == 0 && d == 0 && h == 0 && m == 0 && s < 5){
        str = "刚刚";
    }else if(y == 0 && M == 0 && d == 0 && h == 0 && m == 0 && s == 30){
        str = "半分钟前";
    }else if(y == 0 && M == 0 && d == 0 && h == 0 && m == 0 && s > 0){
        str = (s + "秒前");
    }else if(y == 0 && M == 0 && d == 0 && h == 0 && m == 30){
        str = "半小时前";
    }else if(y == 0 && M == 0 && d == 0 && h == 0 && m > 0){
        str = (m + "分钟前");
    }else if(y == 0 && M == 0 && d == 0 && h > 0){
        str = (h + "小时前");
    }else if(y == 0 && M == 0 && d >= 1 && d < 7){
        str = (d + "天前");
    }else if(y == 0 && M == 0 && (d >= 7 && d < 15)){
        str = "一个星期前";
    }else if(y == 0 && M == 0 && d >= 15 && d < 20){
        str = ("半个月前");
    }/*else if(y == 0 && M == 6){
        str = "半年前";
    }else if(y == 0 && M > 0){
        str = (M + "个月前");
    }*/else{
        str = new Date(time).format("yyyy-MM-dd");
    }/*else if(y > 0){
        str = (y + "年前");
    }*/
    return str;
};

/**
 * 获得cookie值
 * @param name
 * @returns {*}
 */
window.getCookieValue = function (name) {
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;
    //查找名为name的cookie的开始位置
    name += "=";
    var pos = allcookies.indexOf(name);
    //如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1) { //如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length; //cookie值开始的位置
        var end = allcookies.indexOf(";", start); //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1)
            end = allcookies.length; //如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start, end); //提取cookie的值
        return unescape(value); //对它解码
    } else
        return ""; //搜索失败，返回空字符串
};


/**
 * 格式化日期
 */
Date.prototype.format = function(fmt)
{
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "\u65e5",
        "1" : "\u4e00",
        "2" : "\u4e8c",
        "3" : "\u4e09",
        "4" : "\u56db",
        "5" : "\u4e94",
        "6" : "\u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
};



/**
 * 删除数组元素
 */
Array.prototype.remove = function(n) {  //n表示第几项，从0开始算起。
    //prototype为对象原型，注意这里为对象增加自定义方法的方法。
    if(n<0) {   //如果n<0，则不进行任何操作。
        return this;
    }else{
        return this.slice(0, n).concat(this.slice(n + 1, this.length));
    }
    /*
　　　concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
　　　　　　　　　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
　　 　　　　　　组成的新数组，这中间，刚好少了第n项。
　　　slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
　　*/
};


/**
 * 按字节截取字符串长度,支持中文
 * @param b
 * @param max
 * @returns
 */
byteLength = function(b, max) {
    if (typeof b == "undefined") return 0;
    if (max > 0) {
        var returnValue = '', byteValLen = 0;
        b = b.split(""); for ( var i = 0; i < b.length; i++) {
            if((byteValLen += (b[i].match(/[^\x00-\x80]/g) != null) ? 2 : 1) > max) break;
            returnValue += b[i];
        }
        return returnValue;
    }
    var a = b.match(/[^\x00-\x80]/g);
    return (b.length + (!a ? 0 : a.length));
};

/**
 * 清除字符串两端空字符
 * @returns
 */
String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
};

/**
 * 替换字符
 * @param search
 * @param replace
 * @returns
 */
String.prototype.replaceAll = function(search, replace) {
    var regex = new RegExp(search, "g");
    return this.replace(regex, replace);
};

/**
 * 获取字符长度，支持中文
 * @returns
 */
String.prototype.len = function(){
    return this.replace(/[^\x00-\xff]/g, "**").length;
};

/**
 * 字符分页
 * @param o
 * @param num
 * @returns
 */
String.prototype.limit = function(num,o){
    var objString = this;
    var objLength = this.replace(/[^x00-xFF]/g,'**').length;
    if (objLength > num) {
        return byteLength(objString, num)+o;
    } else return objString;
};

/**
 * MAP对象，实现MAP功能
 * 
 * 接口： 
 * size() 获取MAP元素个数 
 * isEmpty() 判断MAP是否为空 
 * clear() 删除MAP所有元素 
 * put(key, value) 向MAP中增加元素（key, value) 
 * remove(key) 删除指定KEY的元素，成功返回True，失败返回False 
 * get(key) 获取指定KEY的元素值VALUE，失败返回NULL 
 * element(index) 获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL 
 * containsKey(key) 判断MAP中是否含有指定KEY的元素 
 * containsValue(value) 判断MAP中是否含有指定VALUE的元素 
 * values() 获取MAP中所有VALUE的数组（ARRAY） 
 * keys() 获取MAP中所有KEY的数组（ARRAY）
 * 
 * 例子： 
 * var map = new Map();
 * map.put("key", "value"); 
 * var val = map.get("key") 
 * ……
 * 
 */
Map = function() {
	this.elements = new Array();

	// 获取Map元素个数
	this.size = function() {
		return this.elements.length;
	},

	// 判断Map是否为空
	this.isEmpty = function() {
		return (this.elements.length < 1);
	},

	// 删除Map所有元素
	this.clear = function() {
		this.elements = new Array();
	},

	// 向Map中增加元素（key, value)
	this.put = function(_key, _value) {
		if (this.containsKey(_key) == true) {
			//if (this.containsValue(_value)) {
				if (this.remove(_key) == true) {
					this.elements.push({
						key : _key,
						value : _value
					});
				}
			/*} else {
				this.elements.push({
					key : _key,
					value : _value
				});
			}*/
		} else {
			this.elements.push({
				key : _key,
				value : _value
			});
		}
	},

	// 删除指定key的元素，成功返回true，失败返回false
	this.remove = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					this.elements.splice(i, 1);
					return true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	},

	// 获取指定key的元素值value，失败返回null
	this.get = function(_key) {
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					return this.elements[i].value;
				}
			}
		} catch (e) {
			return null;
		}
	},

	// 获取指定索引的元素（使用element.key，element.value获取key和value），失败返回null
	this.element = function(_index) {
		if (_index < 0 || _index >= this.elements.length) {
			return null;
		}
		return this.elements[_index];
	},

	// 判断Map中是否含有指定key的元素
	this.containsKey = function(_key) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].key == _key) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	},

	// 判断Map中是否含有指定value的元素
	this.containsValue = function(_value) {
		var bln = false;
		try {
			for (var i = 0; i < this.elements.length; i++) {
				if (this.elements[i].value == _value) {
					bln = true;
				}
			}
		} catch (e) {
			bln = false;
		}
		return bln;
	},

	// 获取Map中所有key的数组（array）
	this.keys = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].key);
		}
		return arr;
	},

	// 获取Map中所有value的数组（array）
	this.values = function() {
		var arr = new Array();
		for (var i = 0; i < this.elements.length; i++) {
			arr.push(this.elements[i].value);
		}
		return arr;
	};
};

/**
 * js实现list
 * add()	添加元素
 * size()	List大小
 * get(index)	返回指定索引的值（index）
 * remove(index)	删除指定索引的值（index）
 * removeAll()	删除全部值
 * constains()	是否包含某个对象
 * getAll()	获取所有值，以“,”分开
 */
List = function(){
	this.value = [];

	/* 添加 */
	this.add = function(obj) {
		return this.value.push(obj);
	};

	/* 大小 */
	this.size = function() {
		return this.value.length;
	};

	/* 返回指定索引的值 */
	this.get = function(index) {
		return this.value[index];
	};

	/* 删除指定索引的值 */
	this.remove = function(index) {
		this.value.splice(index, 1);
		return this.value;
	};

	/* 删除全部值 */
	this.removeAll = function() {
		return this.value = [];
	};

	/* 是否包含某个对象 */
	this.constains = function(obj) {
		for ( var i in this.value) {
			if (obj == this.value[i]) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	};

	/* 获取所有值，以“,”分开 */
	this.getAll = function() {
		var allInfos = '';
		for ( var i in this.value) {
			if (i != (value.length - 1)) {
				allInfos += this.value[i] + ",";
			} else {
				allInfos += this.value[i];
			}
		}
		return allInfos += this.value[i] + ",";
	};
};

Array.prototype.indexOf = function(val) { 
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) 
			return i; 
	}
	return -1; 
};
/*
window.getLocationHash = function(){
  return window.location.hash;
};
*/