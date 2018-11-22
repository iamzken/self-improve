$(function(){
	
	$("#upload").change(function(){
		$(".progress-bar").css({width:"0%"});
		$(".progress-bar span").html("" + this.value);
	});
	

	$("#uploadBtn").click(function(){
		
		var self = this;
		var callback = $.query.get("callback");
		
		self.prokey = new Date().getTime();  //progressId 就是获取系统时间 保存到对象本身
	    $("#uploadform").attr("action", "/web/upload" + ".json?X-Progress-ID=" + self.prokey
	    + "&path=" + $.query.get("path")
	    //+ "&id=" + $.query.get("id")
	    //+ "&enc=" + $.query.get("enc")
	    //+ "&c=" + $.query.get("c")
		).submit();

	    (getProgress = function(){
	        ajax({
	            type: "GET",
	            url:  "/web/upload/progress.json?callback=?",
	            data: {"X-Progress-ID": self.prokey},
	            dataType: "jsonp",
	            success: function (data) {
	                if(data.finish == 1){
	                	if(this.progressTimer) {
                            clearInterval(this.progressTimer);
                        }
	                    $(".progress-bar")
	                        .animate({width:"100%"});
	                    $(".progress-bar span").html("上传完毕" );
	                    window.lastreceived = 0;
	                    
	                    if(callback){
	                    	eval("window.parent." + callback + "(" + JSON.stringify(data) +  ")");
	                    }
	                    
	                } else {
	                    if(data.received && data.size){
	                        var prs = [Math.ceil((data.received / data.size) * 100), Math.ceil((data.received-(window.lastreceived||0)) / 1000)];
	                        $(".progress-bar")
	                            .animate({width:prs[0] + "%"})
	                            .html("已上传" + prs[0] +"% " + "上传速度" + prs[1] + "KB/s" );
	                        window.lastreceived = data.received
	                    }
	                    this.progressTimer = setInterval(getProgress, 1000);

	                }
	            }
	        });
	    })();
	});
	
});