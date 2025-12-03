# HipHop Ghetto 项目结构说明

## 项目概述

HipHop Ghetto 是一个基于Java的音乐社区平台，采用多模块单体架构设计，旨在提供高质量的Hip-Hop音乐内容分享和社交体验。

## 技术栈

- Java 17
- Maven 3.8+
- Spring Boot 3.5.8
- 多模块单体架构

## 项目结构

```
hiphop-ghetto/
├── hiphop-ghetto-common/              # 公共模块
│   ├── hiphop-ghetto-common-core/     # 核心公共组件
│   ├── hiphop-ghetto-common-web/      # Web公共组件
│   ├── hiphop-ghetto-common-mq/       # 消息队列组件
│   ├── hiphop-ghetto-common-crawler/  # 爬虫组件
│   └── pom.xml
├── hiphop-ghetto-service/             # 业务服务模块
│   ├── hiphop-ghetto-service-user/    # 用户服务
│   ├── hiphop-ghetto-service-album/   # 专辑服务
│   ├── hiphop-ghetto-service-song/    # 歌曲服务
│   ├── hiphop-ghetto-service-collection/ # 收藏服务
│   ├── hiphop-ghetto-service-like/    # 点赞服务
│   ├── hiphop-ghetto-service-rating/  # 评分服务
│   ├── hiphop-ghetto-service-topic/   # 话题服务
│   ├── hiphop-ghetto-service-comment/ # 评论服务
│   └── pom.xml
├── hiphop-ghetto-web/                 # Web入口模块
└── pom.xml                            # 根项目配置
```

## 模块详细说明

### 公共模块 (hiphop-ghetto-common)

#### common-core 核心公共组件
包含项目中通用的常量、枚举、异常定义和工具类：
- `constant/`: 项目常量定义
- `enums/`: 通用枚举类型
- `exception/`: 自定义异常类及全局异常处理
- `util/`: 通用工具类

#### common-web Web公共组件
包含Web层通用组件：
- `interceptor/`: 拦截器实现
- `aspect/`: 切面编程组件
- `config/`: Web配置类
- `exception/handler/`: Web层异常处理器

#### common-mq 消息队列组件
封装消息队列相关功能，采用策略模式设计：
- `strategy/`: 消息处理策略接口及实现
- `consumer/`: 消息消费者
- `producer/`: 消息生产者
- `config/`: MQ配置

#### common-crawler 爬虫组件
网络爬虫相关功能：
- 网易云音乐数据爬取
- 数据解析和存储

### 业务服务模块 (hiphop-ghetto-service)

每个服务模块遵循统一的分层架构：
- `controller/`: 控制层，处理HTTP请求
- `service/`: 业务逻辑层
- `repository/`: 数据访问层
- `model/`: 数据实体类
- `dto/`: 数据传输对象

#### 服务列表
1. **用户服务 (user)**: 用户注册、登录、个人信息管理
2. **专辑服务 (album)**: 专辑信息管理
3. **歌曲服务 (song)**: 歌曲信息管理
4. **收藏服务 (collection)**: 用户收藏功能
5. **点赞服务 (like)**: 点赞功能
6. **评分服务 (rating)**: 评分功能
7. **话题服务 (topic)**: 社区话题讨论
8. **评论服务 (comment)**: 评论功能

### Web入口模块 (hiphop-ghetto-web)
Spring Boot应用入口，负责：
- 整合各业务服务
- 提供统一API网关
- 处理跨域等问题

## 包命名规范

所有Java包均遵循以下命名规范：
```
com.xxxyjade.hiphopghetto.{模块}.{子包}
```

例如：
- 用户服务控制器: `com.xxxyjade.hiphopghetto.user.controller`
- 核心工具类: `com.xxxyjade.hiphopghetto.common.util`

## 开发规范

1. 所有业务服务模块应保持独立，通过接口进行通信
2. 公共组件通过Maven依赖引入
3. 代码遵循统一的分层架构模式
4. 异常处理统一由common-web模块提供
5. 消息队列处理采用策略模式实现

## 构建和运行

```bash
# 构建整个项目
mvn clean install

# 运行Web服务
cd hiphop-ghetto-web
mvn spring-boot:run
```