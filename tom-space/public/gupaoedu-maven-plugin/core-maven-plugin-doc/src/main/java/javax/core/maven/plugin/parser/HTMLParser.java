package javax.core.maven.plugin.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Demo;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
import javax.core.common.utils.StringUtils;
import javax.core.maven.plugin.constants.DocConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;

public class HTMLParser {
	
	//当我们不设置自定义模板的时候，使用默认模板
	private final static String template = "/templates/api.html";
	
	
	private static void generate(InputStream in,String targetFile,Class<?> [] classes,String host){
		
		if(in == null){ return; }
		
		Document doc = null;
		try {
			doc = Jsoup.parse(in, "utf-8","");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(doc == null){ return; }
		
		int catelogCount = 1;
		
		//使用的时候，传一个class的一个集合过来，从Spring的IOC容器中获取
		for (Class<?> c : classes) {
			try{
				//看你是不是一个Controller，如果是一个cotroller，那么认为是可以用http请求
				if(!c.isAnnotationPresent(Controller.class)){ continue; }
				
				String namespace = ""; //模块名
				//开始判断有没有配置RequestMapping
				if(c.isAnnotationPresent(RequestMapping.class)){
					Annotation a = c.getAnnotation(RequestMapping.class);
					Method m = a.getClass().getMethod("value",null);
					namespace = ((String [])m.invoke(a))[0];
				}
				
				//可以给每个模块定制一个独立的子域名去访问，自己的注解
				String domain = null;
				String desc = null;
				if(c.isAnnotationPresent(Domain.class)){
					Annotation a = c.getAnnotation(Domain.class);
					
					Method m = a.getClass().getMethod("value",null);
					domain = m.invoke(a).toString();
					
					Method dm = a.getClass().getMethod("desc",null);
					desc = dm.invoke(a).toString();
				}
				
				doc.select(".interface").append(StringUtils.format(doc.select("#interfaceTitleItem").html(),"p" + catelogCount,catelogCount + "、" + (StringUtils.isEmpty(desc) ? "未描述模块" : desc)));
				doc.select(".content-body").append(StringUtils.format(doc.select("#catalogItem").html(),"#p" + catelogCount, catelogCount + "、" + (StringUtils.isEmpty(desc) ? "未描述模块" : desc)));
				
				
				//开始扫描Controller里面的所有的方法
				int interfaceCount = 1;
				Method [] ms = c.getDeclaredMethods();
				for (Method m : ms) {
					//判断，看这个方法有没有配置RequestMapping，如果没有配置
					//那这个方法肯定是请求不到的
					if(!m.isAnnotationPresent(RequestMapping.class)){ continue; }
					
					doc.select(".interface").append(StringUtils.format(doc.select("#interfaceItem").html(),"s" + catelogCount + "-" + interfaceCount));
					Element eleInterface = doc.select(".interface #" + "s" + catelogCount + "-" + interfaceCount).parents().first();
					
					//如果有
					//获取RequestMapping里面的值
					Annotation mapping = m.getAnnotation(RequestMapping.class);
					Method vm = mapping.getClass().getMethod("value",null);
					String url = ((String [])vm.invoke(mapping))[0];
					//到这里为止，就可以拼装出来一个完整的url了
					
					
					Method mm = mapping.getClass().getMethod("method",null);
					
					Object mr = mm.invoke(mapping);
					String method = null;
					if(mr != null){
						if(((RequestMethod [])mr).length > 0){
							method = (((RequestMethod [])mr)[0]).toString();
						}
					}
					
					String mdesc = null;
					String name = null;
					String author = null;
					String createtime = null;
					String baseUrl = "http://" + (null == domain || "".equals(domain) ? "" : (domain + ".")) + host + namespace;
					boolean checkEnc = true;
					boolean checkIp = false;
					boolean checkVersion = false;
					
					Demo demo = null;
					
					Map<String,String> paramDesc = new HashMap<String,String>();
//					List<String[]> ipList = new ArrayList<String[]>();
//					List<String[]> verList = new ArrayList<String[]>();
					List<String[]> returnList = new ArrayList<String[]>();
					
					//获取我们自定义的API配置信息
					if(m.isAnnotationPresent(Api.class)){
						Annotation a = m.getAnnotation(Api.class);
						
						Method nm = a.getClass().getMethod("name",null);
						name = nm.invoke(a).toString();
						
						Method dm = a.getClass().getMethod("desc",null);
						mdesc = dm.invoke(a).toString();
						
						Method am = a.getClass().getMethod("author",null);
						author = am.invoke(a).toString();
						
						Method cm = a.getClass().getMethod("createtime",null);
						createtime = cm.invoke(a).toString();
						
						Method pm = a.getClass().getMethod("params", null);
						Rule [] params = (Rule[])pm.invoke(a);
						
						for (Rule param : params) {
							paramDesc.put(param.name(),param.desc());
						}
						
						Method rm = a.getClass().getMethod("returns", null);
						Rule [] returns = (Rule[])rm.invoke(a);
						
						for (Rule r : returns) {
							returnList.add(new String[]{r.name(),r.type(),r.desc()});
						}
						
						Method auth = a.getClass().getMethod("auth",null);
						Auth [] auths = (Auth[])auth.invoke(a);
						for (Auth au : auths) {
							checkEnc = au.checkEnc();
							
							checkIp = au.checkIp();
							
							checkVersion = au.checkVersion();
							
						}
						
						Method dem = a.getClass().getMethod("demo",null);
						Object o = dem.invoke(a);
						
						if(o != null){
							demo = (Demo)o;
						}
					}
					
					//往html文档里面写配置信息了
					eleInterface.select(".title").html(catelogCount + "." + interfaceCount + "、" + (StringUtils.isEmpty(name) ? "未描述接口名称" : name));
					eleInterface.select(".desc").html(StringUtils.isEmpty(mdesc) ? "未描述功能" : mdesc);
					eleInterface.select(".url").html(baseUrl + url);
					eleInterface.select(".method").html((null == method || "".equals(method) ? "GET/POST" : method.toUpperCase()));
					eleInterface.select(".author").html(StringUtils.isEmpty(author) ? "未描述作者" : author);
					eleInterface.select(".createtime").html(StringUtils.isEmpty(createtime) ? "未描述最后修改时间" : createtime);
					eleInterface.select(".checkEnc").html(checkEnc ? "是" : "否");
					eleInterface.select(".checkIp").html(checkIp ? "是" : "否");
					eleInterface.select(".checkVersion").html(checkVersion ? "是" : "否");
					
//					System.out.println("URL:http://" + (null == domain || "".equals(domain) ? "" : (domain + ".")) + host + namespace + url);
					
					List<String> paramsType = new ArrayList<String>();
					
					for (Class param : m.getParameterTypes()) {
						paramsType.add(param.getSimpleName());
					}
					
					
					
					//开始扫描方法上面参数
					List<String[]> params = new ArrayList<String[]>();
					int i = 0;
					for (Annotation[] anns : m.getParameterAnnotations()) {
						for (Annotation ann : anns) {
							//spring的参数名字定义，有两种形式，第一种就是
							if(ann instanceof RequestParam){
								
								//获取参数是否必填
								boolean required = (Boolean)ann.getClass().getMethod("required").invoke(ann);
								//获取参数的名字
								String pname = ann.getClass().getMethod("value", null).invoke(ann).toString();
								//获取是否有默认值
								String dft = ann.getClass().getMethod("defaultValue").invoke(ann).toString();
								if(dft.startsWith("\n")){
									dft = null;
								}
								params.add(new String[]{
										pname,
										DocConstants.types.get(paramsType.get(i).toLowerCase()),
										(required ? "是" : "否"),
										(StringUtils.isEmpty(dft) ? "无" : dft),
										(paramDesc.get(pname) == null ? "无" : paramDesc.get(pname))});
							//直接配置在路径上的
							}else if(ann instanceof PathVariable){
								String pname = ann.getClass().getMethod("value", null).invoke(ann).toString();
								params.add(new String[]{
										pname,
										DocConstants.types.get(paramsType.get(i).toLowerCase()),
										"是",
										"无",
										(paramDesc.get(pname) == null ? "无" : paramDesc.get(pname))});
							}
						}
						i ++;
					}
					//如果最终扫描出来结果，参数个数为0，表示访问这个接口是不需要带参数的
					if(params.size() == 0){
						eleInterface.select(".params table").html("无");
					}
					
					
					
					//如果有参数，填充到我们模板中，最终生成html
					for (int index = 0; index < params.size(); index ++) {
						String[] arr = params.get(index);
						eleInterface.select(".params table").append(
							StringUtils.format(doc.select("#paramItem").html(),
								(((index + 1) % 2 == 0) ? "odd" : "eve"),
								arr[0],
								arr[1],
								arr[2],
								arr[3],
								arr[4]));
					}
					
					
					if(returnList.size() == 0){
						eleInterface.select(".returns table").html("");
					}
					
					for (int index = 0; index < returnList.size(); index ++) {
						String[] arr = returnList.get(index);
						eleInterface.select(".returns table").append(
							StringUtils.format(doc.select("#returnItem").html(),
								(((index + 1) % 2 == 0) ? "odd" : "eve"),
								arr[0],
								arr[1],
								arr[2]));
					}
					
					
					//demo
					if(demo != null){
						//添加返回说明
						eleInterface.select(".returns").append(doc.select("#demoDetailItem").html());
						
						if(!StringUtils.isEmpty(demo.param())){
							eleInterface.select(".item-bd").append(StringUtils.format(doc.select("#demoItem").html(),baseUrl + url + "?" + demo.param()));
						}
						if(!StringUtils.isEmpty(demo.success())){
							String json = demo.success();
							try{
								json = JSON.toJSONString(JSON.parse(json),true);
								json = json.replaceAll("\n","<br/>").replaceAll("\t","&nbsp;&nbsp;&nbsp;");
							}catch(Exception e){
								
							}
							eleInterface.select(".item-bd").append(StringUtils.format(doc.select("#successItem").html(),json));
						}
						if(!StringUtils.isEmpty(demo.error())){
							String json = demo.error();
							try{
								json = JSON.toJSONString(JSON.parse(json),true);
								json = json.replaceAll("\n","<br/>").replaceAll("\t","&nbsp;&nbsp;&nbsp;");
							}catch(Exception e){
								
							}
							eleInterface.select(".item-bd").append(StringUtils.format(doc.select("#errorItem").html(),json));
						}
					}
					
					
					interfaceCount ++;
				}
				
				doc.select(".interface").append("<div style='clear:both;margin-bottom:20px;'></div>");
				catelogCount ++;
					
			}catch(Exception e){
				continue;
			}
			
			
			//输出到我们的磁盘
			try{
				OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(new File(targetFile)),"utf-8");
				ow.write(doc.html());
				ow.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 生成HTML文档
	 * @param templateFile HTML模板文件
	 * @param targetFile 生成目标文件
	 * @param classes 需要分析的class
	 * @param host url域名
	 */
	public static void generate(String templateFile,String targetFile,List<Class> classes,String host){
		Class[] c = new Class[classes.size()];
		generate(templateFile,targetFile,classes.toArray(c), host);
	}
	
	/**
	 * 生成HTML文档
	 * @param templateFile HTML模板文件
	 * @param targetFile 生成目标文件
	 * @param classes 需要分析的class
	 * @param host url域名
	 */
	public static void generate(String templateFile,String targetFile,Class [] classes,String host){
		InputStream in = null;
		try{
			in = new FileInputStream(new File(templateFile));
		}catch(Exception e){
			e.printStackTrace();
		}
		generate(in,targetFile,classes, host);
	}
	
	/**
	 * 生成HTML文档
	 * @param targetFile 生成目标文件
	 * @param clazz 需要分析的class
	 * @param host url域名
	 */
	public static void generate(String targetFile,List<Class<?>> clazz,String host){
		Class<?>[] c = new Class[clazz.size()];
		generate(targetFile,clazz.toArray(c), host);
	}

	
	/**
	 * 生成HTML文档
	 * @param targetFile 生成目标文件
	 * @param classes 需要分析的class
	 * @param host url域名
	 */
	public static void generate(String targetFile,Class [] classes,String host){
		InputStream in = null;
		try{
			in = HTMLParser.class.getResourceAsStream(template);
		}catch(Exception e){
			e.printStackTrace();
		}
		generate(in,targetFile,classes, host);
	}
	
}
