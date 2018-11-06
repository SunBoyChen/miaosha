package com.hua.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.hua.miaosha.domain.MiaoshaUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public static final int DEFULTEXPIRE = 2 * 60 * 60;






    public <T> T get(String key,Class<T> clazz){

       Jedis jedis = null;
       try {
           jedis = jedisPool.getResource();

           String s = jedis.get(key);

           return stringToBean(s,clazz);
       } finally {
           //放回连接池
           if(jedis != null) {
               jedis.close();
           }
       }
   }


   public <T> boolean set(String key, T value,int expireTime){
       Jedis jedis = null;
       try {
           jedis = jedisPool.getResource();

           String str = beanToString(value);

           if(expireTime > 0){
               jedis.setex(key,expireTime,str);
           }else {
               jedis.set(key,str);
           }
           return true;
       }finally {
           //放回连接池
           if(jedis != null) {
               jedis.close();
           }
       }

   }

    private <T> String beanToString(T value) {

        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }



    private <T> T stringToBean(String str,Class<T> clazz) {

        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
