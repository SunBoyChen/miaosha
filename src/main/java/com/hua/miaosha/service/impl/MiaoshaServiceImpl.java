package com.hua.miaosha.service.impl;

import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.service.MiaoshaService;
import com.hua.miaosha.service.OrderService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        ////减少库存，下单 写入秒杀订单
        goodsService.reduceStock(goods);

        OrderInfo orderInfo = orderService.createOrder(user,goods);

        return orderInfo;
    }

}
