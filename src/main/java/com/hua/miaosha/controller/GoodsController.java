package com.hua.miaosha.controller;

import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.service.MiaoshaUserService;
import com.hua.miaosha.service.impl.MiaoshaUserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService userService;


   /* @RequestMapping("/to_list")
    public String toList(HttpServletResponse response, Model model,
                         @CookieValue(value = MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String cookieToken,
                         @RequestParam(value = MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String paramToken){

        if(StringUtils.isEmpty(cookieToken)  && StringUtils.isEmpty(paramToken) ){

            //返回等入页面
            return "login";
        }

        String token = paramToken != null?paramToken:cookieToken;


        MiaoshaUser user = userService.getByToken(token,response);

        model.addAttribute("user",user);

        return "goods_list";
    }*/


    @RequestMapping("/to_list")
    public String toList(HttpServletResponse response, Model model,MiaoshaUser user){

        model.addAttribute("user",user);

        return "goods_list";
    }


}
