package com.hua.miaosha.exception;

import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {


    //拦截登录异常
   /* @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //重定向到认证页面
        return new ModelAndView("redirect:http://localhost:8080/sell/index.html");
    }*/

    //处理自定义异常
   /* @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    //@ResponseStatus(code = HttpStatus.FORBIDDEN)    //指定错误码
    public Result handlerGlobalException(GlobalException e){
        return new Result(CodeEnums.Error,e.getMessage());
    }*/


    //处理自定义异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    //@ResponseStatus(code = HttpStatus.FORBIDDEN)    //指定错误码
    public Result handlerException(Exception e){
        e.printStackTrace();
        return new Result(CodeEnums.Error,e.getMessage());
    }

}
