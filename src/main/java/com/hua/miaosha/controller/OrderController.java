package com.hua.miaosha.controller;


import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.exception.GlobalException;
import com.hua.miaosha.result.Result;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.service.OrderService;
import com.hua.miaosha.vo.GoodsVo;
import com.hua.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    //查询订单
    @RequestMapping(value = "/detail/{orderId}")
    public Result<OrderDetailVo> info(MiaoshaUser user, @PathVariable("orderId") long orderId) {

        if (user == null){
            throw new GlobalException(CodeEnums.LOGINOUTTIME.getCode(),CodeEnums.LOGINOUTTIME.getDesc());
        }

        //查询订单详情
        OrderInfo order = orderService.getOrderById(orderId);

        Long goodsId = order.getGoodsId();
        //查询商品信息
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goodsVo);
        return new Result<>(CodeEnums.SUCCESS,vo);
    };

}
