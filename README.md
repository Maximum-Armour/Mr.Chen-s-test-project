# 微服务项目结构
ccit-javatemplate  
├── ccit-javatemplate-api：微服务调用feignClient接口jar包，路由前缀规则：/api，路由完整路径全部为小写，例如：某购物车服务需要调用订单服务的某个接口，便可把当前jar包发布到私仓，其他服务依赖此jar包即可调用；
├── ccit-javatemplate-common：基础common模块，例如：自定义工具类、枚举；  
├── ccit-javatemplate-dao：持久化访问层模块，包含：mapper接口、（domain）数据库表实体、dto对象定义；
├── ccit-javatemplate-job：定时任务模块，包含主启动类，如果想开发一个定时任务的微服务，建议删除web、mobile模块； 
├── ccit-javatemplate-mobile：移动端模块，包含主启动类，如果想开发移动端微服务，建议删除web、job模块，路由前缀规则：/webapi，路由完整路径全部为小写；
├── ccit-javatemplate-openapi：外部接口开放模块，提供第三方业务系统调用的api接口，需要经过安全认证；   
├── ccit-javatemplate-rpc：调用外部 rpc接口，例如：调用天气预报接口、三方短信、邮箱接口；  
├── ccit-javatemplate-service：业务处理模块；
├── ccit-javatemplate-web：web模块，前后端交互入口，接口路由前缀规则：/webapi，路由完整路径全部为小写；   
├── pom.xml：pom文件；  
├── README.md：项目 编译 打包 启动,说明等快速指引； 
└── sql：数据库建表脚本；


# POJO模型以及适用模块
VO: 和前端界面进行交互，调用接口传递参数、返回前端界面参数都使用VO，放入web、mobile、job、openapi模块；
DTO：数据传输对象，放置在dao模块，多表关联返回的结果集使用此模型接收，因为service模块依赖了dao模块，所以可以在dao模块创建service层所需的对象；
PO：数据库表实体类，1对1，放置在dao模块；
api模块、rpc模块创建的POJO对象都为DTO模型；


# 当前脚手架项目包含了多个微服务（web、mobile、job），并且对应的业务处理逻辑都在service模块，
# 举个例子：如果开发一个微服务包含pc端和移动端并且开发功能相对独立，他们之间的业务逻辑、数据模型和交互方式差异较大，使用两个
# 脚手架开发比较好，如果它们之间有大量的共享逻辑和资源，或者团队希望简化部署和运维，那么基于
# 当前的项目结构开发也可以，一个脚手架包含web、mobile两个微服务或者只保留一个web服务（包含mobile模块功能）；
# 如果为了方便当前应用全部代码的管理，使用一个git仓库，可参考下述结构自定义完整应用的代码结构；

ccit-miss   //应用名称
├── ccit-frame   //微服务名称
├───── ccit-frame-api
├───── ccit-frame-common
├───── ccit-frame-dao
├───── ccit-frame-rpc
├───── ccit-frame-service
├───── ccit-frame-web
├───── pom.xml
├── ccit-odp     //微服务名称
├───── ccit-odp-api
├───── ccit-odp-common
├───── ccit-odp-dao
├───── ccit-odp-rpc
├───── ccit-odp-service
├───── ccit-odp-web
├───── pom.xml
├── ccit-tdp     //微服务名称 
├───── ccit-tdp-api
├───── ccit-tdp-common
├───── ccit-tdp-dao
├───── ccit-tdp-rpc
├───── ccit-tdp-service
├───── ccit-tdp-web
├───── pom.xml
├── pom.xml


# 设置代码规范检查是否跳过的两种方式：
1. 根目录pom文件下，checkstyle.skip设置true，跳过检查
   <checkstyle.skip>true</checkstyle.skip>
2. 通过checkstyle.skip命令传参设置代码规范检查，false不跳过检查
   mvn checkstyle:check -Dcheckstyle.skip=false
# 项目编译打包
mvn  clean compile package -Dmaven.test.skip=true

# 项目启动
java -jar ccit-javatemplate-web/target/ccit-javatemplate-web-1.0-SNAPSHOT.jar -Dspring.profiles.active=dev

说明：
1. 环境变量 Environment variables：spring.profiles.active=xxx
2. 启动类 ：ccit-javatemplate-web的WebApplication