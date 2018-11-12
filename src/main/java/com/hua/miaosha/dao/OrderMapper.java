package com.hua.miaosha.dao;

import com.hua.miaosha.domain.MiaoshaOrder;
import com.hua.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {


    @Select("SELECT * FROM miaosha_order WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId,@Param("goodsId") long goodsId);

    /**
     * 插入订单返回订单id
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);



    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder order);


    @Select("SELECT * FROM order_info WHERE id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId") long orderId);
}
