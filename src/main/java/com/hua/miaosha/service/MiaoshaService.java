package com.hua.miaosha.service;

import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.vo.GoodsVo;

public interface MiaoshaService {
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);
}
