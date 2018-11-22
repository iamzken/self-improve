package com.gupao.standalone.mytomcat;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by James on 2017-05-27.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class GPRequest {
    private InputStream inputStream;
    private String url;
    private String method;

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

    //GET /favicon.ico HTTP/1.1
    private void extractFileds(String content) {
        String command = content.split("\\n")[0];
        String words[] = command.split("\\s");
        setMethod(words[0]);
        setUrl(words[1]);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
