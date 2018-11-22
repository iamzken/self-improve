package javax.core.maven.plugin.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义
 * @author Tanyongde
 *
 */
public class DocConstants {
	
	public static final Map<String,String> types = new HashMap<String,String>();
	
	static{
		types.put("char", "字符");
		types.put("character", "字符");
		types.put("string", "字符串");
		types.put("long", "整数");
		types.put("integer", "整数");
		types.put("int", "整数");
		types.put("short", "整数");
		types.put("float", "小数");
		types.put("double", "小数");
		types.put("boolean", "布尔值");
	}
	
	
}
