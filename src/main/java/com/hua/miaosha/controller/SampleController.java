package com.hua.miaosha.controller;

import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.domain.User;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.result.Result;
import com.hua.miaosha.service.UserService;
import com.sun.tools.javac.jvm.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;


@RequestMapping("/demo")
@Controller
/* @RestController*/
public class SampleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> home(){
        return new Result<String>(CodeEnums.SUCCESS,null);
    }


    @RequestMapping("/hello/themaleaf")
    public String themaleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }


    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return new Result<>(CodeEnums.SUCCESS,user);
    }

    @RequestMapping("/db/save")
    @ResponseBody
    public Result<Boolean> dbSave() {

        User user = new User();
        user.setAge(25);
        user.setBalance(new BigDecimal(100));
        user.setName("zhihua");
        user.setUsername("user1");

        if(userService.insert(user)){
            return Result.successResult(CodeEnums.SUCCESS);
        }else {
            return Result.errorResult(CodeEnums.Error);
        }

    }


    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.successResult(CodeEnums.SUCCESS);
    }



    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = userService.getById(1);

        redisService.set("key"+1, user,10);//UserKey:id1
        return Result.successResult(CodeEnums.SUCCESS);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result redisGet() {
        String s = redisService.get("key" + 1, String.class);
        return new Result(CodeEnums.SUCCESS,s);
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<User> error() {
        return new Result<>(CodeEnums.LOGINOUTTIME,null);
    }


}
