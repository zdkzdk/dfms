#   分布式财务管理系统
##  overview
### 支持将人员以权限分组以提供差异化服务，方便财务人员进行员工管理，提供资产、经营、费用状况的数字化和统计分析的功能。
### 分布式架构提高系统可用性、可扩展性、可伸缩性

![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/1.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/2.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/3.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/4.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/5.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/6.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/14.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/7.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/8.png)

*   微服务架构，划分模块，独立部署，增加可用性和复用性
    *   使用dubbo实现rpc远程声明式调用
    *   Zk作为注册中心
*   以nginx作为http静态资源服务器实现动静分离
    *   跨域
    *   用AOP将动态数据的渲染抽取成切面织入到nginx的静态资源中
*    RBAC思想，shiro实现权限管理
    *   将验证、授权、向作用域存取值的过程封装
    *   项目中任意位置获取权限服务(url,前台,后台)
*    Redis作为分布式节点的公共作用域
*    Z-tree，echarts，dwr
*    Mybatis和spring-data-jpa

##  技术选型
*   Rpc
    *   dubbo
    *   httpclient
*   服务治理：dubbo mock 
*   mvc：springBoot + springmvc + spring
    *   Dao：Mybatis，Springdatajpa
*   公共作用域：redis
    *   提供分布式节点的公共作用域
*   Orm：
    *   数据库：mysql
    *   mybatis-generator 	
*   安全：RBAC&shiro
    *   使用user，role，resource3个对象和它们之间的多对多关系来描述用户和权限的映射
    *   封装了验证、授权的过程并在项目中的任意位置获取登录用户的权限并提供判断的api
*   前端： bootstrap ，thymeleaf，jsp，jquery，js，ajax， DWR
    *   DWR：ajax的一种封装，已经过时，感兴趣所以用了
*   服务器：
    *   web服务器：nginx
    *   应用服务器：tomcat
##  项目架构：微服务
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/9.png)
##  表结构
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/11.png)
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/12.png)
##  项目概述
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/10.png)
*   用户中心：
    *   为用户提供登录、注册、跳转服务
    *   密码加密加盐
    *   远程调用权限服务，降级返回托底数据
*   权限：
![Image text](https://github.com/zdkzdk/blog-template/blob/master/images/13.png)
    *   前台：用户、角色、权限的CRUD
    *   后台：验证，授权，为url、方法、前端标签提供验证，在项目任意位置验证权限。
        *   使用shiro自动验证
        *   使用redis作为公共作用域
*   FMS财务管理系统
    *   部门：工资系数
    *   资产、
    *   工资、
    *   经营、
    *   费用、
    *   统计
        *   表格
        *   使用Echarts动态生成表格，提供多种形式的数据展示和分析
            *   折线、柱状图
            *   雷达图
            *   饼图





