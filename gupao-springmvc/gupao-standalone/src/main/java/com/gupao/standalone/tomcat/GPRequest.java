package com.gupao.standalone.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 * <p>
 * 请求封装体
 */
public class GPRequest {
    private InputStream inputStream;

    private String method;

    private String url;

    public GPRequest(InputStream inputStream) {
        this.inputStream = inputStream;
        try {
            String content = null;
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = inputStream.read(buff)) > 0) {
                content = new String(buff, 0, len);
            }
//            System.out.println(content);
            extractFileds(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void extractFileds(String content) {
        String command = content.split("\\n")[0];
        String words[] = command.split("\\s");
        setMethod(words[0]);
        setUrl(words[1]);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParameters() {
        //TODO
        return null;
    }


}
