# 秒杀设计的一些重点
- 资源静态化，加快性能，前后端分离，让页面资源不经过后端，前端拥有自己的服务器，提前放入cdn服务器内容。
- 服务单一职责，扛住高并发的系统需要单一职责。
- 秒杀链接加盐，使URL动态化。可以解决提前的链接暴露.
- Redis集群，主从同步、读写分离，我们还搞点哨兵，开启持久化直接无敌高可用，应对秒杀本身即是读多写少
- Nginx来负载均衡，或微服务里做负载均衡，恶意请求拦截也可以用Nginx来做拦截。
- 前端的按钮控制。秒杀前按钮置灰。
- 限流，分为前端限流和后端限流。
- 库存预热，通过定时任务，将商品库存加载到redis中，整个流程在Redis里去做，等秒杀结束了，再异步去修改库存。
- Lua脚本，在高并发的情况下举例：就比如现在库存只剩下1个了，我们高并发嘛，4个服务器一起查询了发现都是还有1个，那大家都觉得是自己抢到了，就都去扣库存，那结果就变成了-3，是的只有一个是真的抢到了，别的都是超卖的。咋办？Lua脚本就是类似于redis事务，有一定的原子性，不会被其他命令插队，可以完成一定的事务性操作。
- 限流 & 降级 & 熔断 & 隔离
- 用MQ消息队列来削峰填谷
- 订单部分，避免重复下单，可以采用幂等机制，多次请求和一次请求的结果一致。生成全局唯一订单号。

# 零散知识点
- 缓存命中率
  缓存命中率是从缓存中读取数据的次数与总读取次数的比率，命中率越高越好。缓存命中率=从缓存中读取次数 / (总读取次数 (从缓存中读取次数 + 从慢速设备上读取次数))。这是一个非常重要的监控指标，如果做缓存，则应通过监控这个指标来看缓存是否工作良好。

## 页面优化技术
### 页面缓存 + 热点数据对象缓存
页面缓存思路：首先我们需要明白，一个页面是从后端提高数据后，交给springMvc或者SpringBoot进行渲染，主要的页面消耗是在渲染这部分。因此我们需要在这之前进行拦截。当客户的请求到达后端时，先去redis中查询缓存，如果缓存中找不到，则进行数据库逻辑操作，然后渲染，存入缓存并返回给前端!如果在缓存中找到了则直接返回给前端。存储在Redis缓存中的页面需要设置超时时间，缓存的时间长度根据页面数据变化频繁程度适当调整。

### 热点数据缓存思路:
所谓热点数据，就是指在某段时间内被频繁使用的对象数据。比如用户登录信息,用户在登录后，每次访问都会携带其cookie信息进入后端，当信息到达后端后，其cookie信息就是我们存在redis中的key值。在这一步我们会做四个操作，并且在某些时候可使用拦截器进行处理。
1. 当用户操作进来的时候，我们获取到Cookie值并在Redis中查找，找到用户信息则刷新用户的登录时间并允许用户通过，找不到用户信息则拒绝用户继续往下!，在后面的数据操作中，如果存在需要使用用户信息的操作，则去Redis中查找，如果存在则允许操作.
2. 对于热点数据源，被高频访问的不缺分权限信息的热点数据，则设置全局缓存，定时更新则缓存数据，当有操作到此类的热点数据缓存则主动更新缓存中的信息，将用户拦截在数据之外。
3. 当涉及到用户登录的热点数据被更新后，需要根据用户的token作为key值重新写入或者强制用户重新登录
4. 对于需要频繁更新的数据或写入数据的数据，比如点赞次数，在线人数，可以设置一个层级，在没有达到层级前写在缓存中，每次只更新缓存则可以，当到一定次数则写入数据库

### 页面局部缓存：
热点数据缓存，页面静态化进行ajax请求信息更新，此类信息一般都是比较频繁发生变化的，涉及的可能是需要保存在数据库的操作,类似表格信息，即时刷新的数据等！如果是属于查看类的并且前端大量请求，可以经由于后端监控，定时写入缓存。
一般情况下封装以类名--对象名为组合的字符串作为Redis的Key值，然后存入数据库，每次访问到目标的方法都先去缓存读取，然后再处理

### 缓存会出现的问题
- 缓存雪崩问题：
  缓存雪崩是指因为数据未加载到缓存中，或者缓存同一时间大面积的失效，在某一时刻大量的缓存没有命中，从而导致所有请求都去查数据库，导致数据库CPU和内存负载过高，甚至宕机。

- 缓存穿透问题：
  查询一个数据库必然不存在的数据，那么缓存里面也没有。比如文章表中查询一个不存在的id，每次都会访问DB，如果有人恶意破坏，发送高频请求，那么很可能直接对DB造成影响。对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃。或者对于查询为空的字段，设置一个默认值在缓存中，如果查询到则返回默认值! 或者使用具备特点的key值，如果不符合则经由于系统过滤掉，不进入缓存也不进入数据库,此做法可以降低一定的压力，但是解决不了根本的问题

- 缓存失效问题：
  如果缓存集中在一段时间内失效，DB的压力凸显，DB负载急剧上升。这个没有完美解决办法，但可以分析用户行为，尽量让失效时间点均匀分布。

- 缓存预热问题：
  系统部署时，防止用户一瞬间访问数据库，负载过大，由开发人员主动将数据加载到缓存中

### 页面静态化，前后端分离
除了将页面资源与数据进行缓存以减少数据库访问外，还可以利用浏览器特点，将页面给完全缓存在浏览器中，等到浏览器过时，再访问项目，项目的请求经过项目内部缓存，缓存如果过时，再访问数据库!
将页面静态化的特点必须解决页面如何获取与处理数据，如何跳转页面的问题！在此我们可以参考 ajax技术 ，将请求与页面完全独立，保证页面是静态页面，而请求通过Ajax技术局部刷新与全局刷新的特点来实现。

### 静态资源优化，CDN优化
- 除了对静态页面优化，我们还可以通过一些方式减少流量，提供访问的速度!当用户的请求越小，性能也就相对越好!
1. JS/CSS压缩，静态资源尽量使用压缩版的库和包，以减少浏览器加载和请求的流量
2. 多个js/css组合到一个请求，减少连接数（正常30个，从服务端获取，多次访问，通过http获取），把多个文件通过一个js/css一次性请求下来 配置 tengine模块实现
- CDN：内容分发网络，将数据缓存到网络节点上，用户请求来根据位置定向访问到距离最近的节点，可用于解决网络拥挤，跟代码层面关系不是很大，在请求没到网站之前，CDN会根据客户的位置将请求分发到就近地网路节点上，如果节点有则直接返回。

## Redis对象级缓存
Redis缓存减少对于数据库的访问，数据库的访问压力是秒杀系统中的瓶颈。**内存标记方法**也可以减少Redis的访问。

## 订单处理队列RabbitMQ
- 请求入队缓冲，异步下单写入数据库，进行流量削峰。在这里，秒杀接口处，秒杀时客户端会轮询检查是否秒杀成功。
- 秒杀时，先redis完成预减，把秒杀消息通过MQ传到消费者，由消费者完成事务。这里可以削峰限流，满足高可用的系统。

## 解决超卖
- Redis+LUA脚本，使Redis的命令具有原子性，比如库存预减和查询库存是否大于0等。

## 分布式Session
把session的id写入cookies，并且把session的id和登录用户对象的信息一起写入Redis。利用登录拦截器来检查Redis中的User信息是否对应。这样在微服务分布式的环境下，实现了登录验证。

## 秒杀安全
秒杀接口加盐动态化生成。接口防刷限流--拦截器加注解。

## Redis与数据库的库存一致性
redis的数量不是库存,他的作用仅仅只是为了阻挡多余的请求透穿到DB，起到一个保护的作用
因为秒杀的商品有限，比如10个，让1万个请求区访问DB是没有意义的，因为最多也就只能10个
请求下单成功，所有这个是一个伪命题，我们是不需要保持强一致。
### Redis预减成功，DB扣减库存失败
这一步结合消息队列高可用完成。

## RabbitMQ如何做到消息不重复不丢失即使服务器重启
exchange持久化、queue持久化（项目使用中）、发送消息设置MessageDeliveryMode.persisent这个也是默认的行为、手动确认。

## Redis封装 Jedis,Redisson,Lettuce
- Jedis,Redisson,Lettuce三者都可以实现Redis相关封装。三者各有优劣。
  共同点：都提供了基于Redis操作的Java API，只是封装程度，具体实现稍有不同。
  不同点：
  1.1、Jedis
  是Redis的Java实现的客户端。支持基本的数据类型如：String、Hash、List、Set、Sorted Set。
  特点：使用阻塞的I/O，方法调用同步，程序流需要等到socket处理完I/O才能执行，不支持异步操作。Jedis客户端实例不是线程安全的，需要通过连接池来使用Jedis。
  1.2、Redisson
  优点点：分布式锁，分布式集合，可通过Redis支持延迟队列。
  1.3、 Lettuce
  用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。
  基于Netty框架的事件驱动的通信层，其方法调用是异步的。Lettuce的API是线程安全的，所以可以操作单个Lettuce连接来完成各种操作。
- stringRedisTemplate与redisTemplate的使用区别：
  当你的redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate即可，
  但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择。

## Redis锁
### LUA脚本
Redis在2.6推出了脚本功能，允许开发者使用Lua语言编写脚本传到Redis中执行。使用脚本的好处如下:
1. 减少网络开销：本来5次网络请求的操作，可以用一个请求完成，原先5次请求的逻辑放在redis服务器上完成。使用脚本，减少了网络往返时延。
2. 原子操作：Redis会将整个脚本作为一个整体执行，中间不会被其他命令插入。
3. 复用：客户端发送的脚本会永久存储在Redis中，意味着其他客户端可以复用这一脚本而不需要使用代码完成同样的逻辑。

### 为什么要使用锁
- 第一是为了正确性，就像Java里的synchronize，就是用来保证多线程并发场景下，程序的正确性。
  JVM里需要保证并发访问的正确性，在分布式系统里面，也同样需要，只不过并发访问的单位，不再是线程，而是进程。
- 第二是为了效率。比如三台集群机器，只需要一个机器去执行任务，则可以用锁，先获得的锁执行任务，没获得锁的机器去执行其他。
### Redis的分布锁
分布式锁和本地锁之间。单机，并发的单位是线程，分布式，并发的单位是多进程。并发单位的等级上去了，锁的等级自然也得上去。
Redisson框架下的锁，有可重入锁的功能，封装也比较好。

## 服务限流降级、熔断、过载保护
- 限流，分为前端限流、后端限流。前端限流更多偏向于**按钮置灰**这种情况。后端限流里，本身项目中有注解加拦截器的限流。还可以使用阿里的Sentinel完成一些实时监控、流量控制，消息削峰、熔断下游等功能。

## RabbitMQ高可用
RabbitMQ的高可用主要体现在消息的发送、传输和接收的过程中，可以保证消息成功发送、不会丢失，以及被确认消费/不重复消费。
- 对于消息是否发送成功，主要是针对生产者端的消息生产确认机制；
- 对于消息不会丢失，主要是rabbitmq消息持久化机制；
- 对于消息确认消费/不重复消费，主要是针对消费者端对消息的确认消费机制。

## SQL锁
- 悲观锁、了关锁
- SQL的一些写法，这里有疑惑的是，如果用MQ框架如何改善SQL写法。

## SpringBoot Admin
Spring Boot Admin（SBA）是一个开源的社区项目，用于管理和监控 Spring Boot 应用程序。应用程序可以通过 http 的方式，或 Spring Cloud 服务发现机制注册到 SBA 中，然后就可以实现对 Spring Boot 项目的可视化管理和查看了。
Spring Boot Admin 可以监控 Spring Boot 单机或集群项目，它提供详细的健康 （Health）信息、内存信息、JVM 系统和环境属性、垃圾回收信息、日志设置和查看、定时任务查看、Spring Boot 缓存查看和管理等功能。

## 国密算法
SM3是杂凑、哈希加密，为单向加密函数，无法解密。SM4为分组密码算法，SM2为非对称加密，即有公钥加密，私钥解密。对称加密为统一公钥加密。

## 订单号生成
- 生成原则：
1.全局的唯一性 2.自增长 3.长度的要求 4.具有一定的可读性 5.保密,不可推测性 6.效率性
- 常见的ID生成策略。 1. 数据库自增长序列或字段 2. UUID 3. UUID的变种*【UUID to Int64;NHibernate在其主键生成方式中提供了Comb算法（combined guid/timestamp）】 4. Redis生成ID 5. Twitter的snowflake算法 6. 利用zookeeper的znode生成唯一ID 7. MongoDB的ObjectId

# 解决的疑难杂症
- 当使用网关时，静态资源可以放网关下，如果是分别放下游服务静态资源包中，需要在代码中的js、css和img的路径加上/static/，和gateway配置中路径加上 /static/
- Swagger依赖版本可能会导致报错，doc.html页面功能需要另外加个依赖
- 使用MP框架时，想要切换主键生成策略，那么在切换之前，最好对数据库表执行"TRUNCATE TABLE 'table name'" 操作，不然会有影响。
- Druid 1.1.21以下的版本与MQ有冲突，高版本没有。1.1.22比较合适，更高版本的另外报错warn,连接失败。
- Mysql Order是关键字，不能用来作为表名。
- RabbitMQ(若使用RocketMQ则暂时没有多实例容器工厂的问题) 开启多实例容器工厂配置来代替单实例配置后，在消费者中需要手动进行确认消费。并且比起使用单实例模式，一些地方可能出现并发问题，比如更新秒杀库存表。这里使用即时读取Redis库存数字，写入缓存库存数字来试着解决。
- Feign使用的同时，一些bug很多。比如文件上传，像项目中那样配置接口才可，在Feign接口使用@RequestPart，并将content-type修改。调用接口如果要传递参数，必须要用@RequestParam注解。以及FeignConfig中的配置也是值得商榷的地方。
- 在MyBatisPlus与MyBatis共存之时，配置的XML文件中的Mapper层的函数不要与MP本身的方法冲突。以及要在模块中的application配置文件中申明XML地址。
- 使用dynamic-datasource这个依赖来分库分表时，主库不设计为order会出问题。然后也集成了druid连接池。
- Flyway + Druid + dynamic的多数据源方案，在flyway的配置上，主库和flyway的配置类需要像项目那样配合才能行，否则每次主库会强制执行所有同一模块中的所有flyway的SQL文件，导致多数据源的所有表都在那个主库中出现。
- maven 给微服务打包时，发现cloud-common通用模块要打包需要用到发到私库，这时候mission、uaa、manage模块需要依赖common模块。网上一堆答案，都尝试过无果，[摸索出来的pom配置可以供大家参考](https://zhuanlan.zhihu.com/p/571980021)。

# 参考
[如何设计一个秒杀系统总结](https://blog.csdn.net/yin767833376/article/details/103028616)
[Java项目构建基础：统一结果，统一异常，统一日志](https://mp.weixin.qq.com/s/elWDB2uKoPxifsrPdCSmpQ)
[秒杀系统设计](https://youzi530.github.io/2021/11/04/miao-sha-xi-tong-she-ji/)
[秒杀系统更高级的参考](https://github.com/qiurunze123/miaosha)
[为什么现在又流行服务端渲染html](https://www.zhihu.com/question/59578433/answer/1936572256)
[页面优化缓存技术+资源静态化+前后端分离?](https://blog.csdn.net/qq_36505948/article/details/82620908)
[redis分布式锁与redisson](https://www.jianshu.com/p/47fd7f86c848)
[什么是悲观锁、乐观锁](https://www.jianshu.com/p/d2ac26ca6525)
[Jmeter常用的组件](https://cloud.tencent.com/developer/article/1638723?from=article.detail.1636441)
[Java RabbitMQ快速入门教程](https://www.tizi365.com/topic/22.html)
[RabbitMq 入门介绍](https://blog.csdn.net/Fiuty_Da/article/details/114252362?spm=1001.2014.3001.5502)
[一篇文章把RabbitMQ、RocketMQ、Kafka三元归一介绍](https://mp.weixin.qq.com/s/EDJygTIry7HSS0dQOYSHhw)
[高可用架构-消息队列](https://doocs.gitee.io/advanced-java/#/./docs/high-concurrency/how-to-ensure-high-availability-of-message-queues)
[MyBatis-Plus 高级功能 —— 多数据源配置](https://blog.csdn.net/weixin_38111957/article/details/114100901)
[Spring Cloud Gateway 自定义过滤器实现降级](https://cloud.tencent.com/developer/article/1650037)
[前端性能测试工具](https://mp.weixin.qq.com/s/BsUfVqfIQUAsi6z-C-jhug)
[Mysql集群实战](https://www.maishuren.top/archives/mysql集群实战一主多从两主多从到springboot多数据源实战)
[Springboot admin监控](https://mp.weixin.qq.com/s/V0IQIEA81cxk2DfEs1Ul9A)
[SpringBoot admin配置](https://blog.csdn.net/fenglllle/article/details/109342775)
[Springboot admin 配合邮件警告](https://www.cnblogs.com/duanxz/p/9559385.html)
[RocketMQ 知识点和使用](https://juejin.cn/post/6941913162870423588)
[RocketMQ官方文档博客](https://www.itmuch.com/books/rocketmq/)
[Hutool 国密使用文档](https://www.bookstack.cn/read/hutool-5.6.0-zh/9fd3df43ca890fc4.md)
[SpringBoot Docker容器化部署](https://blog.csdn.net/DBC_121/article/details/105135260)
[秒杀方案参考](https://segmentfault.com/a/1190000040743440)
[聊聊电商系统中常见的9大坑](https://mp.weixin.qq.com/s/BgVr0jEBJwQI5UW_ele08A)
[Spring Cloud Gateway 限流实战](https://mp.weixin.qq.com/s/ATVph_iN6gUyjuJyox-0gg)
[常用消息队列 Kafka、RabbitMQ、RocketMQ、ActiveMQ 综合对比](https://mp.weixin.qq.com/s/AgBOplDsVc2CL57WWv9eaw)
[Nginx从安装到高可用，一篇搞定！](https://mp.weixin.qq.com/s/d51GW-xSzXGAD06qAXU2Hg)
[36个接口设计锦囊](https://mp.weixin.qq.com/s/wtF_cj1Y8r3NGoBAY6NzSQ)
[了解 QPS、TPS、RT、吞吐量 这些高并发性能指标](https://mp.weixin.qq.com/s/HI08c1OuMPsQUZv126FhLA)
[一些接口优化技巧](https://mp.weixin.qq.com/s/ARMelvGnqxeWs8wCeV2r7A)
