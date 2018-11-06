package com.hua.miaosha.service;

import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface MiaoshaUserService {

    MiaoshaUser getById(Long id);

    Boolean login(LoginVo loginVo,HttpServletResponse response);

    MiaoshaUser getByToken(String token,HttpServletResponse response);
}
