package com.hua.miaosha.controller;
import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.result.Result;
import com.hua.miaosha.service.MiaoshaUserService;
import com.hua.miaosha.utils.UUIDUtil;
import com.hua.miaosha.utils.ValidatorUtil;
import com.hua.miaosha.vo.LoginVo;
import com.sun.tools.javac.jvm.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;


@RequestMapping("/login")
@Controller
/* @RestController*/
public class LoginController {


    @Autowired
    private MiaoshaUserService miaoshaUserService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }


    @RequestMapping(value = "/do_login",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> doLogin(@Valid LoginVo loginVo, BindingResult bindingResult, HttpServletResponse response){


        if(bindingResult.hasErrors()){
            logger.error("参数不正确, orderForm={}", loginVo);
            return new Result<>(CodeEnums.Error,"");
        }

        logger.info(loginVo.toString());
/*
        if(loginVo.getMobile() == null || loginVo.getMobile().trim() == ""){
            return new Result<>(CodeEnums.MOBILE_EMPTY,false);
        }

        if(!ValidatorUtil.isMobile(loginVo.getMobile())){
            return new Result<>(CodeEnums.MOBILE_ERROR,false);
        }

        if(loginVo.getPassword() == null || loginVo.getPassword().trim() == ""){
            return new Result<>(CodeEnums.PASSWORD_EMPTY,false);
        }*/

        String token = miaoshaUserService.login(loginVo,response);

        System.out.println(token);






        return new Result<>(CodeEnums.SUCCESS,token);
    }


}
