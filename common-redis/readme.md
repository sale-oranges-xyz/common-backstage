
### spring data redis 集群配置      
- redis集群参考官网  https://docs.spring.io/spring-data/redis/docs/2.0.9.RELEASE/reference/html/#cluster
- 设置集群密码，看了半天，源码里没有为单个加点设置密码的处理过程,估计不需要
- 启用redis模块，需要在application 配置文件配置redis
- redis 单机配置,参考 https://blog.csdn.net/zsg88/article/details/72792947
- 如果出现 ERR This instance has cluster support disabled，需要在redis的redis.conf 开启集群功能
  参考： https://blog.csdn.net/z960339491/article/details/80521851

