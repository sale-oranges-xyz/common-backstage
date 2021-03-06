 #### 模块结构
```
| -common-backstage
  | - common-api-core 聚合服务器方面通用模块
  | - common-base-core 父模块，用于控制spring cloud版本号，其他微服务必须继承该模块
  | - common-core 存放封装的公用代码库
  | - common-excel excel 操作模块
  | - common-feign feign部分通用配置
  | - common-gateway zuul网关部分通用配置
  | - common-jwt  token接口相关实现
  | - common-orm  orm框架
  | - common-redis redis使用配置
  | - common-security 微服务通用security配置
  | - common-swagger 接口api说明，前后端交互使用。注意api接口包名要以*.controller命名
  | - common-token token部分抽象接口
  | - common-web web部分通用代码
  | - common-web-security 整合spring security和web部分通用代码
```

1. swagger 源码导读
2. https://yq.aliyun.com/articles/599809
3. spring data jpa 扩展默认查询方法 https://www.tianmaying.com/tutorial/spring-jpa-custom-all
4. yml使用教程 http://www.ruanyifeng.com/blog/2016/07/yaml.html
5. java 多线程编程 https://blog.csdn.net/qq_38428623/article/details/86689800
                   https://blog.csdn.net/ghsau/article/details/7451464
6. java Future 编程 https://www.cnblogs.com/clarechen/p/4604189.html