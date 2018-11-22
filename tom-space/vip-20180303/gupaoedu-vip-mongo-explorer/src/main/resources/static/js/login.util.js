$(document).ready(function(){

	window.loginSuccess = function(data){

        $('.msg').empty().html(data.msg);

		if(data.status == 1) {
            if(data.data.jumpto) {
                $('.msg').html("登录成功,正在跳转,请稍候...");
                location.href = data.data.jumpto;
            }
		}
	};
		
});