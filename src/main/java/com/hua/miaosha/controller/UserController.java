package com.hua.miaosha.controller;

import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> home(MiaoshaUser user){

        return new Result<MiaoshaUser>(CodeEnums.SUCCESS,user);
    }
}
