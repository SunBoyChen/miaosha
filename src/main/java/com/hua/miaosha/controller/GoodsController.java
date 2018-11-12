package com.hua.miaosha.controller;

import com.hua.miaosha.common.CodeEnums;
import com.hua.miaosha.constants.RedisKeyConstants;
import com.hua.miaosha.domain.MiaoshaUser;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.result.Result;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.service.MiaoshaUserService;
import com.hua.miaosha.vo.GoodsDetailVo;
import com.hua.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private RedisService redisService;


    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


   /*
    优化一
   @RequestMapping("/to_list")
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


   /*
   优化二
   @RequestMapping("/to_list")
    public String toList(HttpServletResponse response, Model model,MiaoshaUser user){

        model.addAttribute("user",user);

        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
*/


   //优化三，将页面加入缓存
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){

        model.addAttribute("user",user);

        //取缓存
        String html = redisService.get(RedisKeyConstants.GOODS_LIST_KEY_HTML, String.class);

        if(!StringUtils.isEmpty(html)){
            return html;
        }


        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        IContext context = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(RedisKeyConstants.GOODS_LIST_KEY_HTML,html,RedisKeyConstants.GOODS_LIST_KEY_HTML_EXPIRETIME);
        }
        return html;
    }



    //优化四，页面静态化，前后端分离
    @RequestMapping(value = "/to_detail2/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetail2(MiaoshaUser user,@PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
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

        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setMiaoshaStatus(miaoshaStatus);
        vo.setRemainSeconds(remainSeconds);
        vo.setUser(user);
        return new Result<>(CodeEnums.SUCCESS,vo);
    }






    //优化三，将页面加入缓存
    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user,
                           @PathVariable("goodsId") long goodsId) {

        model.addAttribute("user",user);


        //取缓存
        String html = redisService.get(RedisKeyConstants.GOODS_DESC_KEY_HTML+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }


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



        IContext context = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(RedisKeyConstants.GOODS_DESC_KEY_HTML,html,RedisKeyConstants.GOODS_DESC_KEY_HTML_EXPIRETIME);
        }
        return html;
    }


   /* @RequestMapping("/to_detail/{goodsId}")
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
    }*/
}
