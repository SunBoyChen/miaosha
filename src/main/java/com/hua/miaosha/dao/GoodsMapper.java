package com.hua.miaosha.dao;

import com.hua.miaosha.domain.MiaoshaGoods;
import com.hua.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("SELECT b.*, a.miaosha_price,a.start_date,a.stock_count,a.end_date FROM miaosha_goods a LEFT JOIN goods b ON  a.goods_id = b.id")
    List<GoodsVo> listGoodsVo();

    @Select("SELECT b.*, a.miaosha_price,a.start_date,a.stock_count,a.end_date FROM miaosha_goods a LEFT JOIN goods b ON  a.goods_id = b.id WHERE b.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE goods_id = #{goodsId} and stock_count > 0")
    //@Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE goods_id = #{goodsId}")
    int reduceStock(MiaoshaGoods miaoshaGoods);

}
