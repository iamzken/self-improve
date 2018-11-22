$(document).ready(function(){
	
	
	$(".gen-btn").click(function(){
		
		$("#qrcodeFrm").submit();
		
//		var content = "BEGIN:VCARD" + "<br/>";
//		content += "FN:" + $("#name").val() + "<br/>"; 
//		content += "TEL;WORK:" + $("#tel").val() + "<br/>";
//		content += "ORG:" + $("#org").val() + "<br/>";
//		content += "EMAIL;WORK:" + $("#email").val() + "<br/>";
//		content += "URL:" + $("#site").val() + "<br/>";
//		content += "ADR;WORK:" + $("#addr").val() + "<br/>";
//		content += "NOTE:" + $("#note").val() + "<br/>";
//		content += "END:VCARD" + "<br/>";
//		
//		$("#qrcode").attr("src","qrcode.file?content=" + content + "&t=" + new Date().getTime());
			
		/*$.ajax({method:"POST",url:"qrcode.file",data:{content:content},success:function(e){
			
		}})*/
	});
	
});