package com.boss.restdemo.controller;

import com.boss.restdemo.util.JwtUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @RequestMapping("hello")
    public String hello(){
        return "Hello world!";
    }

    @RequestMapping("getToken")
    public Map<String,Object> getToken(){
        final String userId="zhangsan";
        Map<String,Object> map=new HashMap<>();
        String sign = JwtUtils.sign(userId);
        map.put("token", sign);
        map.put("useId", userId);
        return map;
    }
}
