package com.boss.restdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadController {
    @GetMapping("badApi")
    public String badResponse(){
        if(System.currentTimeMillis() % 2L ==0 ){
            while (true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
