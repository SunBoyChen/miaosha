package com.hua.miaosha.service.impl;

import com.hua.miaosha.dao.GoodsMapper;
import com.hua.miaosha.domain.MiaoshaGoods;
import com.hua.miaosha.service.GoodsService;
import com.hua.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    public List<GoodsVo> listGoodsVo(){
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {

        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public int reduceStock(GoodsVo goods) {

        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goods.getId());
        return goodsMapper.reduceStock(miaoshaGoods);
    }

}
