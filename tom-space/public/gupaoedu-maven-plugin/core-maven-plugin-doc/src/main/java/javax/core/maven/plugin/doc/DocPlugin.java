package javax.core.maven.plugin.doc;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

import javax.core.maven.plugin.parser.HTMLParser;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal doc 
 * @phase compile
 * @author Tom
 *
 */
public class DocPlugin extends AbstractMojo {

	/**
	 * @parameter expression="${targetFile}"
	 */
	private String targetFile;
	
	/**
	 * @parameter expression="${host}"
	 */
	private String host;
	
	/**
	 * @parameter expression="${basePackage}"
	 */
	private String basePackage;
	
	/**
	 * @parameter expression="${classPath}"
	 */
	private String classPath;
	/**
	 * @parameter expression="${libDir}"
	 */
	private String libDir;
	
	//全局类加载器
	private URLClassLoader loader;

	public void execute() throws MojoExecutionException, MojoFailureException {
		this.getLog().info("================ 这是咕泡学院的自定义插件 ======================");
		this.getLog().info("获取到参数列表");
		this.getLog().info("classPath=" + classPath);
		this.getLog().info("libDir=" + libDir);
		this.getLog().info("basePackage=" + basePackage);
		this.getLog().info("targetFile=" + targetFile);
		this.getLog().info("host=" + host);
		
		try{
			
			//初始化类加载器
			String basePath = (new URL("file",null,new File(classPath).getCanonicalPath() + File.separator)).toString();
			String libPath = (new URL("file",null,new File(libDir).getCanonicalPath() + File.separator)).toString();
			
			URLStreamHandler sh = null;
			List<URL> libs = new ArrayList<URL>();
			File libDir = new File(libPath.replaceAll("file:", ""));
			for (File jar : libDir.listFiles()) {
				libs.add(new URL(null,libPath + jar.getName(),sh));
			}
			libs.add(new URL(null,basePath,sh));
			
			loader = new URLClassLoader(libs.toArray(new URL[libs.size()]),Thread.currentThread().getContextClassLoader());
			
			//缓存所有相关的类文件
			List<Class<?>> classes = new ArrayList<Class<?>>();
			File classDir = new File(classPath);
			list(classes,classDir);
			
			if(classes.size() == 0){
				this.getLog().info("未加载任何类文件");
				return;
			}
			HTMLParser.generate(targetFile, classes, host);
			this.getLog().info("已成功生成文档");
		}catch(Exception e){
			e.printStackTrace();
			this.getLog().error(e.getMessage());
		}
	}
	
	/**
	 * 递归扫描所有的class文件
	 * 
	 * @param clazz
	 * @param dir
	 */
	private void list(List<Class<?>> clazz, File dir) {
		File[] files = dir.listFiles();
		for (File f : files) {

			if (f.isDirectory()) {
				list(clazz, f);
			} else {

				if (!f.getName().endsWith(".class")) { continue; }
				String className = f.getPath().replaceAll("\\\\", "/").replaceAll(classPath.replaceAll("\\\\", "/"), "").replaceAll("/", ".").replaceAll("\\.class", "");
				className = className.substring(1, className.length());
				if (className.startsWith(basePackage)) {
					try {
						clazz.add(Class.forName(className, true, loader));
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
	}
	

}
