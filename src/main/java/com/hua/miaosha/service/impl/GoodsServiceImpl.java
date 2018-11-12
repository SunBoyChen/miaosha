package com.hua.miaosha.service.impl;

import com.hua.miaosha.constants.RedisKeyConstants;
import com.hua.miaosha.dao.GoodsMapper;
import com.hua.miaosha.domain.MiaoshaGoods;
import com.hua.miaosha.redis.RedisService;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisService redisService;


    public List<GoodsVo> listGoodsVo(){
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {

        //缓存数据库
        /*GoodsVo goodsVo = redisService.get(RedisKeyConstants.GOODS_DESC_KEY + goodsId, GoodsVo.class);

        if(goodsVo == null) {
            goodsVo = goodsMapper.getGoodsVoByGoodsId(goodsId);
            redisService.set(RedisKeyConstants.GOODS_DESC_KEY+goodsId,goodsVo,-1);
        }
*/

        GoodsVo goodsVo = goodsMapper.getGoodsVoByGoodsId(goodsId);
        return goodsVo;
    }

    @Override
    public int reduceStock(GoodsVo goods) {

        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goods.getId());
        return goodsMapper.reduceStock(miaoshaGoods);
    }

}
