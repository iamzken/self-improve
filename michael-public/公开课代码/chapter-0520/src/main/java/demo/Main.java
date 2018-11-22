package demo;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myLoader=new MyClassLoader();

        UserBean userBean=(UserBean) myLoader.loadClass("demo.UserBean").newInstance();
        UserBean userBean1=new UserBean();
        System.out.println(userBean.getClass().getClassLoader());
        System.out.println(userBean1.getClass().getClassLoader());
    }
}
