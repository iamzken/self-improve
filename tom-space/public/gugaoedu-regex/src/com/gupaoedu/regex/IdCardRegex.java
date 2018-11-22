package com.gupaoedu.regex;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 第二代身份证
 * 公民身份号码是由17位数字码和1位校验码组成。排列顺序从左至右分别为：6位地址码，8位出生日期码，3位顺序码和1位校验码。
 * 地址码和出生日期码很好理解，顺序码表示在同一地址码所标识的区域范围内，对同年同月同日
 * 出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 身份证最后一位校验码算法如下：
 * 1. 将身份证号码前17位数分别乘以不同的系数，从第1位到第17位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 * 2. 将得到的17个乘积相加。
 * 3. 将相加后的和除以11并得到余数。
 * 4. 余数可能为0 1 2 3 4 5 6 7 8 9 10这些个数字，其对应的身份证最后一位校验码为1 0 X 9 8 7 6 5 4 3 2。
 */
public class IdCardRegex {
	
    private String regex = "^((([1-9]{2})(\\d{2})(\\d{2}))([1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1]))(\\d{3}))([0-9]|[Xx])$";
    //private String regex = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    //每位数的加权因子
    private int[] power = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};

    //补码表
    private char[] comp = {'1','0','X','9','8','7','6','5','4','3','2'};

    private static Properties districts = new Properties();

    private SimpleDateFormat birthdayFmtForCard = new SimpleDateFormat("yyyyMMdd");

    private SimpleDateFormat birthdayFmtForOut = new SimpleDateFormat("yyyy年M月d日");

    private String error = "无效的身份证号码";

    private boolean validate = false;

    //提取出来的信息
    private String cardNo;   //身份证号
    private String addr;     //住址
    private String sex;      //性别
    private String birthday; //出生年月
    private int age;		 //计算年龄
    
    //这里是原始信息
    private String simpleCode;   //身份证前17位
    private String birthCode;    //生日
    private String seriCode;         //顺序码
    private String districtCode;    //行政编码
//    private String provinceCode; //省
//    private String cityCode;	 //市
//    private String countyCode; //区(县)
    private String valiCode;    //识别码


    public IdCardRegex(String cardNo){
        this.cardNo = cardNo;
        this.validate();
    }

    static{
        InputStream is = IdCardRegex.class.getClassLoader().getResourceAsStream("district.properties");

        InputStreamReader isr = null;
        
        try {
        	isr = new InputStreamReader(is,"UTF-8");
            districts.load(isr);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //开始校验
    private void validate(){

        //验证格式
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.cardNo);
        if(m.matches()){

//            for (int i = 1; i <= m.groupCount(); i ++){
//                System.out.println(i + "," + m.group(i));
//            }

            //前17位
            this.simpleCode = m.group(1);

            //行政代码
            this.districtCode = m.group(2);
            
//            //省
//            this.provinceCode = m.group(3);
//            //市
//            this.cityCode = m.group(4);
//            //区(县)
//            this.countyCode = m.group(5);
            
            //出生年月
            this.birthCode = m.group(6);
            //顺序码
            this.seriCode = m.group(12);

            //识别码
            this.valiCode = m.group(13);

        }else{
            this.error = "身份证号为18位";
            return;
        }

        //校验行政区划(必须是有效的行政区划)
        if(!districts.containsKey(this.districtCode)){
            this.error = "行政编码错误";
            return;
        }else{
//        	StringBuffer sb = new StringBuffer();
//        	sb.append(districts.getProperty(this.provinceCode + "0000"));
//        	sb.append(districts.getProperty(this.provinceCode + this.cityCode + "00"));
//        	sb.append(districts.getProperty(this.provinceCode + this.cityCode + this.countyCode));
            this.addr = districts.getProperty(this.districtCode);
//        	this.addr = sb.toString();
        }

        //校验出生年月(可以是今天，必须小于明天)
        try {
            Date birthDate = birthdayFmtForCard.parse(this.birthCode);
            
            Calendar birthday = Calendar.getInstance();
            birthday.setTime(birthDate);
            
            Calendar today = Calendar.getInstance();

            if (birthDate.after(today.getTime())) {
                this.error = "你不能出生在未来";
                return;
            }

            this.birthday = birthdayFmtForOut.format(birthDate);
            this.age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        } catch (ParseException e) {
            this.error = "出生年月格式不对";
            return;
        }


        //校验性别
        this.sex = Integer.valueOf(this.seriCode) % 2 == 0 ? "女" : "男";

        
        //验证补码
        int sum = 0;
        char[] ids = this.simpleCode.toCharArray();
        for (int i = 0; i < ids.length; i ++){
            sum += Integer.valueOf("" + ids[i]) * power[i];
        }
        if(!Character.toString(comp[sum % 11]).equals(valiCode)){
            this.error = "识别码错误";
            return;
        }

        
        this.validate = true;
    }

    public String getInfo(){

        if(!this.validate){ return this.error; }

        StringBuffer sb = new StringBuffer();
        if(null == this.addr || "".equals(this.addr.trim())){
            return this.error;
        }

        sb.append("身份证号:" + this.cardNo + "\n");
        sb.append("出生年月:" + this.birthday + "\n");
        sb.append("年龄:" + this.age + "岁\n");
        sb.append("性别:" + this.sex + "\n");
        sb.append("住址:" + this.addr);
        return sb.toString();
    }



}
