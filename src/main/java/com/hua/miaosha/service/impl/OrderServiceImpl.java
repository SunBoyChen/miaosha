package com.hua.miaosha.service.impl;

import com.hua.miaosha.constants.RedisKeyConstants;
import com.hua.miaosha.dao.OrderMapper;
import com.hua.miaosha.domain.MiaoshaOrder;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.service.OrderService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {

        //return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);

        return redisService.get(RedisKeyConstants.MIAOSHA_ORDER_KEY,MiaoshaOrder.class);

    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {

        //创建订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        orderMapper.insert(orderInfo);


        //创建秒杀订单表
        MiaoshaOrder order = new MiaoshaOrder();
        order.setGoodsId(goods.getId());
        order.setOrderId(orderInfo.getId());
        order.setUserId(user.getId());
        orderMapper.insertMiaoshaOrder(order);

        //存入缓存
        redisService.set(RedisKeyConstants.MIAOSHA_ORDER_KEY,order,-1);

        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {

        return  orderMapper.getOrderById(orderId);
    }
}
