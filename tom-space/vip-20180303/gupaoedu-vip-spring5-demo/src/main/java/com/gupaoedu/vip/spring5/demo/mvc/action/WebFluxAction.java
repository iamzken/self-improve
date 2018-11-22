package com.gupaoedu.vip.spring5.demo.mvc.action;

import com.gupaoedu.vip.spring5.demo.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//2007年
@RestController
public class WebFluxAction {

    //必须要依赖Servlet 3.x
    //如果你的返回结果是一个单体的实例，那么你就用Mono
    @GetMapping("/mono")
    public Mono<Member> mono(@RequestParam String name){
        Member member = new Member();
        member.setName(name);
        return Mono.just(member);
    }

    //Flux包含了单个
    @GetMapping("/flux")
    public Flux<Member> flux(){
        List<Member> result = new ArrayList<Member>();
        Member tom = new Member();
        tom.setName("Tom");
        result.add(tom);

        Member mic = new Member();
        mic.setName("Mic");
        result.add(mic);
        return Flux.fromIterable(result);
    }

    //RequestMapping
    @GetMapping("/collection")
//    @RequestMapping(value="/collection",method= RequestMethod.GET)
    public Collection<Member> collection(){
        List<Member> result = new ArrayList<Member>();
        Member tom = new Member();
        tom.setName("Tom");
        result.add(tom);

        Member mic = new Member();
        mic.setName("Mic");
        result.add(mic);
        return result;
    }

}
