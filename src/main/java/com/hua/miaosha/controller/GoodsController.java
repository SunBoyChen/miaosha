package com.hua.miaosha.controller;

import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.service.MiaoshaUserService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private GoodsService goodsService;


   /* @RequestMapping("/to_list")
    public String toList(HttpServletResponse response, Model model,
                         @CookieValue(value = MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String cookieToken,
                         @RequestParam(value = MiaoshaUserServiceImpl.COOKI_NAME_TOKEN,required = false) String paramToken){

        if(StringUtils.isEmpty(cookieToken)  && StringUtils.isEmpty(paramToken) ){

            //返回等入页面
            return "login";
        }

        String token = paramToken != null?paramToken:cookieToken;


        MiaoshaUser user = userService.getByToken(token,response);

        model.addAttribute("user",user);

        return "goods_list";
    }*/


    @RequestMapping("/to_list")
    public String toList(HttpServletResponse response, Model model,MiaoshaUser user){

        model.addAttribute("user",user);

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }




    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser user,
                           @PathVariable("goodsId") long goodsId) {

        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //秒杀状态
        int miaoshaStatus = 0;
        //距离秒杀还有多少秒
        int remainSeconds = 0;

        if(now < startTime){ //秒杀未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startTime - now)/1000);
        }else if(now > endTime){  //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else { //秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("miaoshaStatus",miaoshaStatus);

        return "goods_detail";
    }
}
