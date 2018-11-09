package com.hua.miaosha.controller;

import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.domain.MiaoshaGoods;
import com.hua.miaosha.domain.MiaoshaOrder;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.domain.OrderInfo;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.service.MiaoshaService;
import com.hua.miaosha.service.OrderService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    public String doMiaosha(MiaoshaUser user, Model model,@RequestParam(value = "goodsId") long goodsId){

        //没有登入返回登入页面
        if(user == null){
            return "login";
        }

        //查询商品判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stock = goods.getStockCount();
        if(stock <= 0){
            //秒杀失败
            model.addAttribute("errmsg", CodeEnums.MIAOSHA_OVER.getDesc());
            return "miaosha_fail";
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);

        if(order != null){
            model.addAttribute("errmsg", CodeEnums.REPEATE_MIAOSHA.getDesc());
            return "miaosha_fail";
        }

        //减少库存，下单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);

        //jian
        model.addAttribute("goods",goods);
        model.addAttribute("orderInfo",orderInfo);
        return "order_detail";
    }



}
