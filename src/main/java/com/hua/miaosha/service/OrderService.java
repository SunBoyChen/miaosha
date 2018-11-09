package com.hua.miaosha.service;

import com.hua.miaosha.domain.MiaoshaOrder;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.vo.GoodsVo;

public interface OrderService {

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId);

    OrderInfo createOrder(MiaoshaUser user, GoodsVo goods);
}
