### 作业1 ### 
请查看sharding-test项目  
实现先按订单的创建年月分库到数据库ds202101、ds202102、ds202103、ds202104，再按订单的userID分表到order_info_$->{0..15}  
- 库表建立语句在/src/main/resources下的sql.txt
- 配置文件为application.yml
- 分库的自定义类为com.geek.shardingtest.DatabaseSharding
- 分表的自定义类为com.geek.shardingtest.TableSharding
- 测试方法均在测试目录src/test/java下的com.geek.shardingtest.ShardingTest

### 作业2 ###
请查看hmily-demo项目  
使用hmily分布式事务框架，处理下订单->减库存->扣账户余额分布式事务。分布式框架采用spring cloud。   
- 订单服务为hmily-order模块，核心代码为com.geek.hmilydemo.OrderServiceImpl，是主事务入口，通过Feign接口AccountClient和InventoryClient分别访问库存服务和账户服务
- 库存服务为hmily-demo-inventory模块，核心代码为com.geek.hmilydemo.InventoryServiceImpl
- 账户服务为hmily-demo-account模块，核心代码为com.geek.hmilydemo.InventoryServiceImpl
- 注册中心服务为hmily-demo-eureka模块

