package com.gupaoedu.vip.spring.formework.webmvc.servlet;

import com.gupaoedu.vip.spring.formework.annotation.GPController;
import com.gupaoedu.vip.spring.formework.annotation.GPRequestMapping;
import com.gupaoedu.vip.spring.formework.annotation.GPRequestParam;
import com.gupaoedu.vip.spring.formework.aop.GPAopProxyUtils;
import com.gupaoedu.vip.spring.formework.context.GPApplicationContext;
import com.gupaoedu.vip.spring.formework.webmvc.GPHandlerAdapter;
import com.gupaoedu.vip.spring.formework.webmvc.GPHandlerMapping;
import com.gupaoedu.vip.spring.formework.webmvc.GPModelAndView;
import com.gupaoedu.vip.spring.formework.webmvc.GPViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 2018/4/21.
 */

//Servlet只是作为一个MVC的启动入口
public class GPDispatcherServlet extends HttpServlet {


    private  final String LOCATION = "contextConfigLocation";

//    private Map<String,GPHandlerMapping> handlerMapping = new HashMap<String,GPHandlerMapping>();

    //课后再去思考一下这样设计的经典之处
    //GPHandlerMapping最核心的设计，也是最经典的
    //它牛B到直接干掉了Struts、Webwork等MVC框架
    private List<GPHandlerMapping> handlerMappings = new ArrayList<GPHandlerMapping>();

    private Map<GPHandlerMapping,GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping, GPHandlerAdapter>();

    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();

    @Override
    public void init(ServletConfig config) throws ServletException {

        //相当于把IOC容器初始化了
        GPApplicationContext context = new GPApplicationContext(config.getInitParameter(LOCATION));


        initStrategies(context);

    }


    protected void initStrategies(GPApplicationContext context) {

        //有九种策略
        // 针对于每个用户请求，都会经过一些处理的策略之后，最终才能有结果输出
        // 每种策略可以自定义干预，但是最终的结果都是一致
        // ModelAndView

        // =============  这里说的就是传说中的九大组件 ================
        initMultipartResolver(context);//文件上传解析，如果请求类型是multipart将通过MultipartResolver进行文件上传解析
        initLocaleResolver(context);//本地化解析
        initThemeResolver(context);//主题解析

        /** 我们自己会实现 */
        //GPHandlerMapping 用来保存Controller中配置的RequestMapping和Method的一个对应关系
        initHandlerMappings(context);//通过HandlerMapping，将请求映射到处理器
        /** 我们自己会实现 */
        //HandlerAdapters 用来动态匹配Method参数，包括类转换，动态赋值
        initHandlerAdapters(context);//通过HandlerAdapter进行多类型的参数动态匹配

        initHandlerExceptionResolvers(context);//如果执行过程中遇到异常，将交给HandlerExceptionResolver来解析
        initRequestToViewNameTranslator(context);//直接解析请求到视图名

        /** 我们自己会实现 */
        //通过ViewResolvers实现动态模板的解析
        //自己解析一套模板语言
        initViewResolvers(context);//通过viewResolver解析逻辑视图到具体视图实现

        initFlashMapManager(context);//flash映射管理器
    }

    private void initFlashMapManager(GPApplicationContext context) {}
    private void initRequestToViewNameTranslator(GPApplicationContext context) {}
    private void initHandlerExceptionResolvers(GPApplicationContext context) {}
    private void initThemeResolver(GPApplicationContext context) {}
    private void initLocaleResolver(GPApplicationContext context) {}
    private void initMultipartResolver(GPApplicationContext context) {}




    //将Controller中配置的RequestMapping和Method进行一一对应
    private void initHandlerMappings(GPApplicationContext context) {
        //按照我们通常的理解应该是一个Map
        //Map<String,Method> map;
        //map.put(url,Method)

        //首先从容器中取到所有的实例
        String [] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                //到了MVC层，对外提供的方法只有一个getBean方法
                //返回的对象不是BeanWrapper，怎么办？
                Object proxy = context.getBean(beanName);
                Object controller = GPAopProxyUtils.getTargetObject(proxy);
                Class<?> clazz = controller.getClass();
                //但是不是所有的牛奶都叫特仑苏
                if (!clazz.isAnnotationPresent(GPController.class)) {
                    continue;
                }

                String baseUrl = "";

                if (clazz.isAnnotationPresent(GPRequestMapping.class)) {
                    GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                    baseUrl = requestMapping.value();
                }

                //扫描所有的public方法
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(GPRequestMapping.class)) {
                        continue;
                    }

                    GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                    String regex = ("/" + baseUrl + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    this.handlerMappings.add(new GPHandlerMapping(pattern, controller, method));
                    System.out.println("Mapping: " + regex + " , " + method);

                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initHandlerAdapters(GPApplicationContext context) {
        //在初始化阶段，我们能做的就是，将这些参数的名字或者类型按一定的顺序保存下来
        //因为后面用反射调用的时候，传的形参是一个数组
        //可以通过记录这些参数的位置index,挨个从数组中填值，这样的话，就和参数的顺序无关了

        for (GPHandlerMapping handlerMapping : this.handlerMappings){

            //每一个方法有一个参数列表，那么这里保存的是形参列表
            Map<String,Integer> paramMapping = new HashMap<String, Integer>();


            //这里只是出来了命名参数
            Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
            for (int i = 0; i < pa.length ; i ++) {
                for (Annotation a : pa[i]) {
                    if(a instanceof GPRequestParam){
                        String paramName = ((GPRequestParam) a).value();
                        if(!"".equals(paramName.trim())){
                            paramMapping.put(paramName,i);
                        }
                    }
                }
            }

            //接下来，我们处理非命名参数
            //只处理Request和Response
            Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
            for (int i = 0;i < paramTypes.length; i ++) {
                Class<?> type = paramTypes[i];
                if(type == HttpServletRequest.class ||
                        type == HttpServletResponse.class){
                    paramMapping.put(type.getName(),i);
                }
            }


            this.handlerAdapters.put(handlerMapping,new GPHandlerAdapter(paramMapping));
        }

    }

    private void initViewResolvers(GPApplicationContext context) {
        //在页面敲一个 http://localhost/first.html
        //解决页面名字和模板文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        for (File template : templateRootDir.listFiles()) {
            this.viewResolvers.add(new GPViewResolver(template.getName(),template));
        }

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String url = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        url = url.replace(contextPath,"").replaceAll("/+","/");
//        GPHandlerMapping handler = handlerMapping.get(url);

//        try {
//            GPModelAndView mv = (GPModelAndView)handler.getMethod().invoke(handler.getController());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }


        //对象.方法名才能调用
        //对象要从IOC容器中获取
//        method.invoke(context.);
        try {
            doDispatch(req, resp);
        }catch (Exception e){
            resp.getWriter().write("<font size='25' color='blue'>500 Exception</font><br/>Details:<br/>" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]","")
                    .replaceAll("\\s","\r\n") +  "<font color='green'><i>Copyright@GupaoEDU</i></font>");
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws  Exception{

        //根据用户请求的URL来获得一个Handler
            GPHandlerMapping handler = getHandler(req);
            if(handler == null){
                resp.getWriter().write("<font size='25' color='red'>404 Not Found</font><br/><font color='green'><i>Copyright@GupaoEDU</i></font>");
                return;
            }

            GPHandlerAdapter ha = getHandlerAdapter(handler);


            //这一步只是调用方法，得到返回值
            GPModelAndView mv = ha.handle(req, resp, handler);


            //这一步才是真的输出
            processDispatchResult(resp, mv);


    }

    private void processDispatchResult(HttpServletResponse resp, GPModelAndView mv) throws Exception {
        //调用viewResolver的resolveView方法
        if(null == mv){ return;}

        if(this.viewResolvers.isEmpty()){ return;}

        for (GPViewResolver viewResolver: this.viewResolvers) {

            if(!mv.getViewName().equals(viewResolver.getViewName())){ continue; }
            String out = viewResolver.viewResolver(mv);
            if(out != null){
                resp.getWriter().write(out);
                break;
            }
        }

    }

    private GPHandlerAdapter getHandlerAdapter(GPHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){return  null;}
        return this.handlerAdapters.get(handler);
    }

    private GPHandlerMapping getHandler(HttpServletRequest req) {

        if(this.handlerMappings.isEmpty()){ return  null;}


        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for (GPHandlerMapping handler : this.handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(url);
            if(!matcher.matches()){ continue;}
            return handler;
        }

        return null;
    }


}
