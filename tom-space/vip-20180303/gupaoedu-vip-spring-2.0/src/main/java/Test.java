import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 2018/4/25.
 */
public class Test {
    public static void main(String[] args) {
        String line = "\t<h1>å¤§å®¶å¥½ï¼\u008Cæ\u0088\u0091æ\u0098¯${teacher}è\u0080\u0081å¸\u0088<br/>æ¬¢è¿\u008Eå¤§å®¶ä¸\u0080èµ·æ\u009D¥æ\u008E¢ç´¢Springç\u009A\u0084ä¸\u0096ç\u0095\u008C</h1>";
        Matcher m = Test.matcher(line);
        while (m.find()){
            for (int i = 1 ; i <= m.groupCount(); i ++) {

                //要把￥{}中间的这个字符串给取出来
                String paramName = m.group(i);
                line = line.replaceAll("$\\{" + paramName + "\\}","Tom");
                System.out.println(line);


            }
        }

    }

    private static Matcher matcher(String str){
        Pattern pattern = Pattern.compile("￥\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return  matcher;
    }

}
