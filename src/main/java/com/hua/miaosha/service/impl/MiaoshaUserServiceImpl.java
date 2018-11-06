package com.hua.miaosha.service.impl;

import com.alibaba.fastjson.JSON;
import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.dao.MiaoshaUserMapper;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.exception.GlobalException;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.service.MiaoshaUserService;
import com.hua.miaosha.service.UserService;
import com.hua.miaosha.utils.MD5Util;
import com.hua.miaosha.utils.UUIDUtil;
import com.hua.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(Long id) {
        return miaoshaUserMapper.getById((id));
    }

    @Override
    public Boolean login(LoginVo loginVo, HttpServletResponse response) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        MiaoshaUser user = getById(Long.valueOf(mobile));

        if(user == null){
            throw new GlobalException(CodeEnums.USER_EMPTY.getCode(),CodeEnums.USER_EMPTY.getDesc());
        }
        //验证密码
        String passwordDB = user.getPassword();
        String salt = user.getSalt();

        String s = MD5Util.formPassToDBPass(password, salt);

        if(!s.equals(passwordDB)){
            throw new GlobalException(CodeEnums.PASSWORD_ERROR.getCode(),CodeEnums.PASSWORD_ERROR.getDesc());
        }

        addCookie(user,response);

        return true;
    }

    @Override
    public MiaoshaUser getByToken(String token,HttpServletResponse response) {
        if(StringUtils.isEmpty(token)){
            return null;
        }

        MiaoshaUser user = redisService.get(token, MiaoshaUser.class);

        //从新跟新cookie，以及redi
        addCookie(user,response);

        return user;
    }


    private  void  addCookie(MiaoshaUser user,HttpServletResponse response){
        //将用户数据存储到redis中
        String token = UUIDUtil.uuid();
        redisService.set(token, JSON.toJSONString(user),RedisService.DEFULTEXPIRE);

        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(RedisService.DEFULTEXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
