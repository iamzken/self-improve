package com.gupaoedu.regex;

public class RegexTest {

    public static void main(String[] args) {

        IdCardRegex idCardRegex = new IdCardRegex("230606198504106133");
        System.out.println("=======提取号码信息如下=======\n" + idCardRegex.getInfo());
        
//        try {
//        	Map<String,String> params = new HashMap<String,String>();
//        	params.put("name", "Tom");
//        	params.put("data", "这是自定义信息");
//			String result = TemplateRegex.parse("template.html", params);
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        
    }
    
    
}
