package com.hua.miaosha.vo;

import com.hua.miaosha.domain.MiaoshaUser;

public class GoodsDetailVo {


    //秒杀状态
    private int miaoshaStatus = 0;
    //距离秒杀还有多少秒
    private int remainSeconds = 0;

    private MiaoshaUser user;

    private GoodsVo goods;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
