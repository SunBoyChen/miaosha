package com.hua.miaosha.service.impl;

import com.alibaba.fastjson.JSON;
import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.constants.RedisKeyConstants;
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
        MiaoshaUser user = redisService.get(RedisKeyConstants.MIAOSHAUSER_KEY + id, MiaoshaUser.class);

        if(user == null) {
            user = miaoshaUserMapper.getById((id));
            redisService.set(RedisKeyConstants.MIAOSHAUSER_KEY + id,user,-1);
        }

        return user;
    }

    //修改密码同时同步缓存，并写入token
    public boolean updatePassword(String token, long id, String formPass) {

        MiaoshaUser user = this.getById(id);
        if(user==null){
            throw new GlobalException(CodeEnums.USER_EMPTY.getCode(),CodeEnums.USER_EMPTY.getDesc());
        }

        MiaoshaUser updateUser = new MiaoshaUser();

        updateUser.setId(user.getId());
        updateUser.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));



        //一定要先跟新数据库，在删除缓存
        //更新数据库
        miaoshaUserMapper.updatePassword(updateUser);

        //处理缓存
        //删除用户
        redisService.delete(RedisKeyConstants.MIAOSHAUSER_KEY +user.getId());

        //跟新token信息(不能删除)
        user.setPassword(updateUser.getPassword());
        redisService.set(token, user,RedisService.DEFULTEXPIRE);

        return true;
    };






    @Override
    public String login(LoginVo loginVo, HttpServletResponse response) {

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

        String token = UUIDUtil.uuid();
        addCookie(user,response,token);

        return token;
    }

    @Override
    public MiaoshaUser getByToken(String token,HttpServletResponse response) {
        if(StringUtils.isEmpty(token)){
            return null;
        }


        MiaoshaUser user = redisService.get(token, MiaoshaUser.class);
        //从新跟新cookie，以及redis

        addCookie(user,response,token);

        return user;
    }


    private  void  addCookie(MiaoshaUser user,HttpServletResponse response, String token){
        //将用户数据存储到redis中

        redisService.set(token, JSON.toJSONString(user),RedisService.DEFULTEXPIRE);

        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(RedisService.DEFULTEXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
