package com.tp.sharding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2023/02/13 12:05:34
 */
@Controller
public class UserInfoController {
    @GetMapping("/gettest")
    public void test(){
        System.out.println("----------");
    }
}
