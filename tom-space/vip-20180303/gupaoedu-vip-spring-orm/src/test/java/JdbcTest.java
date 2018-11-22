import com.gupaoedu.vip.orm.demo.model.Member;
import com.gupaoedu.vip.orm.demo.model.User;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tom on 2018/5/9.
 */
public class JdbcTest {

    public static void main(String[] args) {

//        List<?> result = select(Member.class);
        Member condition = new Member();
        condition.setName("tom");
//        condition.setAge(20);
        List<?> result = select(condition);

        System.out.println(Arrays.toString(result.toArray()));

    }

    //我框架问世的时候，你的Member类都还没有从石头缝里蹦出来
    private static List<?> select(Object condition){
        try{


            Class<?> entityClass = condition.getClass();

            //1、加载驱动类
            Class.forName("com.mysql.jdbc.Driver");

            //2、建立连接
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gupaoedu_demo?characterEncoding=UTF-8&rewriteBatchedStatements=true","root","123456");

            Table table = entityClass.getAnnotation(Table.class);


            //3、创建语句开始事务
            //为了简便，暂时用select * from 代替，不要说我不严谨，OK？
            String sql = "select * from " + table.name();
            StringBuffer where = new StringBuffer(" where 1=1 ");
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(condition);
                if(null != value) {
                    Class<?> clazz = field.getType();
                    if(String.class == clazz){
                        where.append(" and " + field.getName() + " = '" + value + "'");
                    }else{
                        where.append(" and " + field.getName() + " = " + value);
                    }

                }
            }


            System.out.println(sql + where.toString());

            PreparedStatement pstmt = conn.prepareStatement(sql + where.toString());

            //4、执行语句集
            ResultSet rs = pstmt.executeQuery();

            //5、获取结果集

            //数据表中的记录要复制到Java的Object中
            //反射机制
            //自动赋值
            //拿到一共有多少个列
            List<Object> result = new ArrayList<Object>();

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()){ //游标


                //===========Begin ORM ============
                Object instance = entityClass.newInstance();
                for (int i = 1; i <= columnCount; i ++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Field field = entityClass.getDeclaredField(columnName);
                    field.setAccessible(true);

                    //数据类型映射非常关键
//                    Object type = field.getType();
//                    if(type == Long.class){
//                        field.set(instance,rs.getLong(columnName));
//                    }else if(String.class == type){
//                        field.set(instance,rs.getString(columnName));
//                    }else if(Integer.class == type){
//                        field.set(instance,rs.getInt(columnName));
//                    }

                    field.set(instance,rs.getObject(columnName));


                    //各自的厂商实现自己的链接
                    //MySQL为例,以下类型Java语言中是不存在的
                    //bigint ，由开发厂商自动就映射好了
                    //varchar
                    //int
//                    System.out.println(rs.getObject(columnName).getClass());


                }
                //===========End ORM ==============
                result.add(instance);
            }


            //System.out.println(Arrays.toString(result.toArray()));




            //6、关闭结果集、关闭语句集、关闭连接
            rs.close();
            pstmt.close();
            conn.close();

            return  result;
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源要在Finally块中
        }
        return  null;
    }


}
