 #### 模块结构
```
| -common-backstage
  | - common-biz-core 聚合服务器方面通用模块
  | - common-base-core 父模块，用于控制spring cloud版本号，其他微服务必须继承该模块
  | - common-core 存放封装的公用代码库
  | - common-feign feign部分通用配置
  | - common-gateway zuul网关部分通用配置
  | - common-jwt  token接口相关实现
  | - common-redis redis使用配置
  | - common-security 微服务通用security配置
  | - common-swagger 接口api说明，前后端交互使用。注意api接口包名要以*.controller命名
  | - common-token token部分抽象接口
  | - common-web web部分通用代码
```
