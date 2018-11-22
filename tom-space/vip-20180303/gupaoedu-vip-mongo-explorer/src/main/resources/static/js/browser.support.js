var browsbox = '';
browsbox += ('<link rel="stylesheet" type="text/css" href="http://static.cn95598.com/css/browser.support.css"/>');
browsbox += ('<div id="browser-support-box" style="display:none;">');
browsbox += ('<div class="browser-support-mask"></div>');
browsbox += ('<div class="inner-box">');
browsbox += ('<div class="curr-browser">');
browsbox += ('<span class="browser-icon"></span>');
browsbox += ('<div class="browser-text">当前版本：<span class="browser-text browser-version"></span>+</div>');
browsbox += ('<div class="name-text">尊敬的用户您好：</div>');
browsbox += ('<div class="browser-curr-text">您使用的浏览器版本太低，为便于系统正常使用！</div>');
browsbox += ('</div>');

browsbox += ('<div class="advice-browser">');
browsbox += ('<div class="tip-msg">我们推荐您下载以下浏览器，或者升级浏览器版本:</div>');
browsbox += ('<div class="tip-icons">');

browsbox += ('<span class="advice-icon">');
browsbox += ('<a href="http://download.firefox.com.cn/releases-sha2/stub/official/zh-CN/Firefox-latest.exe" target="downloadResult">');
browsbox += ('<span class="firefox-min min-icon"></span>');
browsbox += ('<span class="browser-name">FireFox</span>');
browsbox += ('</a>');
browsbox += ('</span>');

browsbox += ('<span class="advice-icon">');
browsbox += ('<a href="http://www.chromeliulanqi.com/Chrome_Latest_Setup.exe" target="downloadResult">');
browsbox += ('<span class="chrome-min min-icon"></span>');
browsbox += ('<span class="browser-name">Chrome</span>');
browsbox += ('</a>');
browsbox += ('</span>');

//browsbox += ('<span class="advice-icon">');
//browsbox += ('<a href="http://get.opera.com/ftp/pub/opera/desktop/37.0.2178.32/win/Opera_37.0.2178.32_Setup.exe" target="downloadResult">');
//browsbox += ('<span class="opera-min min-icon"></span>');
//browsbox += ('<span class="browser-name">Opera</span>');
//browsbox += ('</a>');
//browsbox += ('</span>');

//browsbox += ('<span class="advice-icon">');
//browsbox += ('<a href="javascript:;" target="downloadResult">');
//browsbox += ('<span class="mozilla-min min-icon"></span>');
//browsbox += ('<span class="browser-name">Mozilla</span>');
//browsbox += ('</a>');
//browsbox += ('</span>');

browsbox += ('<span class="advice-icon">');
browsbox += ('<a href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-12-worldwide-languages/" target="_blank">');
browsbox += ('<span class="ie-min min-icon"></span>');
browsbox += ('<span class="browser-name">IE11+</span>');
browsbox += ('</a>');
browsbox += ('</span>');

//browsbox += ('<span class="advice-icon">');
//browsbox += ('<a href="javascript:;" target="downloadResult">');
//browsbox += ('<span class="_360-min min-icon"></span>');
//browsbox += ('<span class="browser-name">360&nbsp;8.1+</span>');
//browsbox += ('</a>');
//browsbox += ('</span>');

browsbox += ('<span class="advice-icon">');
browsbox += ('<a href="https://support.apple.com/zh_CN/downloads/safari" target="_blank">');
browsbox += ('<span class="safari-min min-icon"></span>');
browsbox += ('<span class="browser-name">Safari<br/>(苹果电脑)</span>');
browsbox += ('</a>');
browsbox += ('</span>');

browsbox += ('</div>');
browsbox += ('</div>');
browsbox += ('</div>');
browsbox += ('</div>');
browsbox += ('<IFRAME style="display: none" id="downloadResult" name="downloadResult"></IFRAME>');


window.BrowserSupport = {
      init: function (_callback) {
         this.browser = this.searchString(this.dataBrowser) || "未知内核";
         this.version = this.searchVersion(navigator.userAgent)
         || this.searchVersion(navigator.appVersion)
         || "未知版本";
         if(_callback){_callback({browser:this.browser,version:this.version})};
      },
      searchString: function (data) {
         for (var i=0;i<data.length;i++) {
            var dataString = data[i].string;
            var dataProp = data[i].prop;
            this.versionSearchString = data[i].versionSearch || data[i].identity;
            if (dataString) {
               if (dataString.indexOf(data[i].subString) != -1)
                  return data[i].identity;
            }
            else if (dataProp)
               return data[i].identity;
         }
      },
      searchVersion: function (dataString) {
         var index = dataString.indexOf(this.versionSearchString);
         if (index == -1) return;
         return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
      },
      dataBrowser: [
         {
            string: navigator.userAgent,
            subString: "Chrome",
            identity: "Chrome"
         },
         {
            string: navigator.userAgent,
            subString: "QQBrowser",
            identity: "QQBrowser",
            versionSearch: "QQBrowser"
         },
         {
            string: navigator.vendor,
            subString: "Apple",
            identity: "Safari",
            versionSearch: "Version"
         },
         {
            prop: window.opera,
            identity: "Opera",
            versionSearch: "Version"
         },
         { 
            string: navigator.userAgent,
            subString: "Firefox",
            identity: "Firefox"
         },
         {
            string: navigator.userAgent,
            subString: "MSIE",
            identity: "IE",
            versionSearch: "MSIE"
         },
         {
            string: navigator.userAgent,
            subString: "Gecko",
            identity: "Mozilla"
         },
         { // for older Netscapes (4-)
            string: navigator.userAgent,
            subString: "Mozilla",
            identity: "Netscape",
            versionSearch: "Mozilla"
         }
      ]
   };
   

$(window).load(function(){
	if($("body").children().eq(0).length > 0){
		$(browsbox).insertBefore($("body").children().eq(0));
	}else{
		$("body").append(browsbox);
	}

	BrowserSupport.init(function(data){
		var browserTimer = window.setInterval(function(){
			if((data.browser == "IE" && data.version <= 11)){
				$(".browser-version",$("#browser-support-box")).html(data.browser + " " + data.version);
				$(".browser-icon",$("#browser-support-box")).addClass(data.browser.toLocaleLowerCase());
				$("#browser-support-box").show(function(){
					$("body").css({"overflow":"hidden","margin":0,"padding":0});
					$("input").hide();
				});
			}
			clearInterval(browserTimer);
		},1000);
	});
});
   
 