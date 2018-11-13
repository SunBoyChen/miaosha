# miaosha
秒杀项目

一.搭建环境  springboot + mybatis + redis

1.完成result结果集封装
2.通过jedis自己实现redisPool的编写

二.
1.用户登入，密码两次使用MD5加密
2.全局异常处理
3.jsr303参数检验，自定义参数校验注解
4.分布式session，以及使用WebMvcConfigurer类的使用

三
  1.秒杀商品列表展示
  2.秒杀商品详情
  3.秒杀功能简单实现
  
四  页面优化技术
      
    1.页面缓存+url缓存+对象缓存
    将页面数据，对象数据缓存到redis
   
    2.页面静态化+前后端分离
     采用页面静态化技术使用ajax技术手动渲染页面，实现动静分离
    3.静态资源优化  css  js
      采用打包方式压缩静态文件
    4.cdn优化

