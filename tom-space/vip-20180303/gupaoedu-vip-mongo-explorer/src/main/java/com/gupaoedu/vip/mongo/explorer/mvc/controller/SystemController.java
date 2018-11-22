package com.gupaoedu.vip.mongo.explorer.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gupaoedu.vip.mongo.explorer.service.impl.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.core.common.ResultMsg;

/**
 * Created by Tom on 2018/8/20.
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/login.json")
    public ResponseEntity login(@RequestParam("loginName") String loginName,
                                @RequestParam("loginPass") String loginPass,
                                @RequestParam("iframe") String iframe,
                                @RequestParam("callback") String callback,
                                @RequestParam("jumpto") String jumpto){
        ResultMsg<?> data = memberService.login(loginName,loginPass);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        String json = JSON.toJSONString(data);

        if(!(null == jumpto || "".equals(jumpto))) {
            JSONObject obj = JSON.parseObject(json);
            obj.getJSONObject("data").put("jumpto", jumpto);
            json = obj.toString();
        }

        if("1".equals(iframe)) {
            StringBuffer returnStr = new StringBuffer();
            returnStr.append("window.parent." + ((callback == null) ? "callback" : callback) + "(" + json + ");");
            returnStr.insert(0, "<script type=\"text/javascript\">").append("</script>");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/html"))
                    .body(returnStr.toString());
        }else{
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/json"))
                    .body(((callback == null) ? json : (callback +"(" + json + ")")));
        }
    }


    @GetMapping("/logout.json")
    public Mono<Object> logout(){
        ResultMsg<?> result = memberService.logout(null);
        return Mono.just(result);
    }

}
