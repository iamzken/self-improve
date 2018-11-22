package com.gupao.course.demo.serialize;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.gupao.course.demo.json.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class JavaSerialize {

    private static User init(){
        User u=new User();
        List<User> friends=new ArrayList<User>();
        u.setUserName("mic");
        u.setSex("男");
        u.setFriends(friends);
        User fr1=new User();
        fr1.setUserName("james");
        fr1.setSex("男");
        User fr2=new User();
        fr2.setUserName("sam");
        fr2.setSex("女");
        friends.add(fr1);
        friends.add(fr2);
        return u;
    }

    private static void executeWithSerialize() throws IOException, ClassNotFoundException {
        User u=init();

        Long t1=System.currentTimeMillis();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        ObjectOutputStream obj=new ObjectOutputStream(out);
        for(int i=0;i<10;i++){
            obj.writeObject(u);
        }
        System.out.println("java serialize:"+(System.currentTimeMillis()-t1)+"ms : 总大小："+out.toByteArray().length);

        Long t2=System.currentTimeMillis();
        ObjectInputStream ois=new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(out.toByteArray())));
        User user=(User)ois.readObject();
        System.out.println("java deserialize:"+(System.currentTimeMillis()-t2)+"ms: User:"+user);
    }

    public static void executeWithJackson() throws IOException {
        User u=init();
        ObjectMapper mapper=new ObjectMapper();
        Long t1=System.currentTimeMillis();
        byte[] writeValueAsBytes=null;
        for(int i=0;i<10;i++){
            writeValueAsBytes=mapper.writeValueAsBytes(u);
        }
        System.out.println("json serialize:"+(System.currentTimeMillis()-t1)+"ms:总大小: "+writeValueAsBytes.length);
        Long t2=System.currentTimeMillis();
        User user=mapper.readValue(writeValueAsBytes,User.class);
        System.out.println("json deserialize:"+(System.currentTimeMillis()-t2)+"ms:User:"+user);
    }

    public static void executeWithFastjson(){
        User u=init();
        Long t1=System.currentTimeMillis();
        String text=null;
        for(int i=0;i<10;i++){
            text= JSON.toJSONString(u);
        }
        System.out.println("fastJson serialize:"+(System.currentTimeMillis()-t1)+"ms:总大小："+text.getBytes().length);

        Long t2=System.currentTimeMillis();
        User user=JSON.parseObject(text,User.class);
        System.out.println("fastJson deserialize:"+(System.currentTimeMillis()-t2)+"ms:User:"+user);
    }

    public static void executeWithProtoBuf() throws IOException {
        Codec<User> studentClassCodes= ProtobufProxy.create(User.class,false);
        User u=init();
        Long stime_jpb_encode=System.currentTimeMillis();
        byte[] bytes=null;
        for(int i=0;i<10;i++){
            bytes=studentClassCodes.encode(u);
        }
        System.out.println("jprotobuf序列化："+(System.currentTimeMillis()-stime_jpb_encode)+"ms:总大小"+bytes.length);

        Long stime_jpb_decode=System.currentTimeMillis();
        User user=studentClassCodes.decode(bytes);
        Long etime_jpb_decode=System.currentTimeMillis();
        System.out.println("jprotobuf反序列化耗时："+(etime_jpb_decode-stime_jpb_decode)+"ms: User:"+user);

    }

    public static void executeWithHessian() throws IOException {
        User u=init();
        Long t1=System.currentTimeMillis();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        for(int i=0;i<10;i++) {
            ho.writeObject(u);
        }
        System.out.println("Hessian serialize:"+(System.currentTimeMillis()-t1)+"ms:总大小"+os.toByteArray().length);

        Long t2=System.currentTimeMillis();
        HessianInput hi=new HessianInput(new ByteArrayInputStream(os.toByteArray()));
        User user=(User)hi.readObject();
        System.out.println("Hessian deserialize:"+(System.currentTimeMillis()-t2)+"ms: User:"+user);
    }

    public static void executeWithKyro() throws FileNotFoundException {
        User u=init();
        Long t1=System.currentTimeMillis();

        Kryo kryo=new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.register(User.class);
        Output output=new Output(new FileOutputStream("d:/aa.bin"));
        for(int i=0;i<10;i++){
            kryo.writeObject(output,u);
        }
        System.out.println("Kryo serialize:"+(System.currentTimeMillis()-t1)+"ms: "+output.toBytes().length);

        output.flush();
        output.clear();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        executeWithSerialize();
        executeWithJackson();
        executeWithFastjson();
        executeWithProtoBuf();

        executeWithHessian();
        executeWithKyro();
    }

}
