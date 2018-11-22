package com.gupaoedu.regex;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRegex {

	private static String root = TemplateRegex.class.getResource("/").getFile();
	
	private TemplateRegex(){}
	
	public static String parse(String templateName,Map<String,String> params) throws Exception{
		File file = new File(root + templateName);
		StringBuffer sb = new StringBuffer();
		RandomAccessFile ra = new RandomAccessFile(file, "r");
		try{
			String line = null;
			while(null != (line = ra.readLine())){
				Matcher m = matcher(line);
				while(m.find()){
					for(int i = 1; i <= m.groupCount(); i ++){
						String param = m.group(i);
						Object value = params.get(param);
						if(null == value){continue;}
						line = line.replaceAll("\\$\\{" + param + "\\}", value.toString());
					}
				}
				sb.append(line);
			}
		}finally{
			ra.close();
		}
		return sb.toString();
	}
	
	
    private static Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }  
	
}
