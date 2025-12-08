# HipHop Ghetto 音乐社区平台

## 项目概述

HipHop Ghetto 是一个基于Java的音乐社区平台，采用多模块单体架构设计，旨在提供高质量的Hip-Hop音乐内容分享和社交体验。该平台整合了音乐资源管理、用户互动、内容评价等功能，为用户提供一站式的Hip-Hop音乐社区服务。

## 技术栈

- Java 17
- Maven 3.8+
- Spring Boot 3.5.8
- Spring Data MongoDB
- MyBatis Plus
- Redis & Caffeine (多级缓存)
- RabbitMQ (消息队列)
- MySQL (关系型数据存储)
- JWT (身份认证)

## 项目结构

```
hiphop-ghetto/
├── hiphop-ghetto-common/              # 公共模块
│   ├── hiphop-ghetto-common-core/     # 核心公共组件
│   ├── hiphop-ghetto-common-crawler/  # 爬虫组件
│   ├── hiphop-ghetto-common-event/    # 事件处理组件
│   ├── hiphop-ghetto-common-mq/       # 消息队列组件
│   ├── hiphop-ghetto-common-template/ # 模板组件
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
│   ├── hiphop-ghetto-service-upload/  # 上传服务
│   └── pom.xml
├── hiphop-ghetto-web/                 # Web入口模块
├── database/                          # 数据库脚本
└── pom.xml                            # 根项目配置
```

## 模块详细说明

### 公共模块 (hiphop-ghetto-common)

#### common-core 核心公共组件
包含项目中通用的常量、枚举、异常定义和工具类：
- `cache/`: 缓存相关注解及实现
- `config/`: 各种配置类（MongoDB、Redis、RabbitMQ等）
- `constant/`: 项目常量定义
- `enums/`: 通用枚举类型
- `event/`: 事件模型定义
- `exception/`: 自定义异常类及全局异常处理
- `inteceptor/`: 拦截器实现
- `pojo/`: 数据传输对象（DTO、VO、Entity）
- `result/`: 统一返回结果封装
- `strategy/`: 策略模式接口
- `util/`: 通用工具类

#### common-crawler 爬虫组件
网络爬虫相关功能：
- 网易云音乐数据爬取
- 数据解析和存储

#### common-event 事件处理组件
事件驱动架构相关组件：
- 事件监听器
- 事件分发器
- 各种业务事件处理器

#### common-mq 消息队列组件
封装消息队列相关功能：
- 消息消费者
- 消息发送者
- 消息处理器分发器

#### common-template 模板组件
抽象模板类实现，用于规范化业务流程。

### 业务服务模块 (hiphop-ghetto-service)

每个服务模块遵循统一的分层架构：
- `controller/`: 控制层，处理HTTP请求
- `service/`: 业务逻辑层
- `repository/` 或 `mapper/`: 数据访问层
- `strategy/`: 策略模式实现

#### 服务列表
1. **用户服务 (user)**: 用户注册、登录、个人信息管理
2. **专辑服务 (album)**: 专辑信息管理
3. **歌曲服务 (song)**: 歌曲信息管理
4. **收藏服务 (collection)**: 用户收藏功能
5. **点赞服务 (like)**: 点赞功能
6. **评分服务 (rating)**: 评分功能
7. **话题服务 (topic)**: 社区话题讨论
8. **评论服务 (comment)**: 评论功能
9. **上传服务 (upload)**: 文件上传功能

### Web入口模块 (hiphop-ghetto-web)
Spring Boot应用入口，负责：
- 整合各业务服务
- 提供统一API网关
- 应用配置加载

## 核心功能

1. **音乐资源管理**
   - 专辑、歌曲信息展示
   - 音乐评分与评价
   
2. **社交互动功能**
   - 用户关注与粉丝系统
   - 内容点赞、收藏、评论
   - 社区话题讨论
   
3. **内容推荐**
   - 基于用户行为的个性化推荐
   - 热门内容排行
   
4. **数据统计**
   - 用户行为统计
   - 内容热度分析

## 包命名规范

所有Java包均遵循以下命名规范：
```
com.xxxyjade.hiphopghetto.{模块}.{子包}
```

例如：
- 用户服务控制器: `com.xxxyjade.hiphopghetto.controller`
- 核心工具类: `com.xxxyjade.hiphopghetto.util`

## 开发规范

1. 所有业务服务模块应保持独立，通过接口进行通信
2. 公共组件通过Maven依赖引入
3. 代码遵循统一的分层架构模式
4. 使用策略模式处理复杂的业务逻辑分支
5. 采用事件驱动处理非核心业务流程
6. 合理使用多级缓存提升系统性能

## 构建和运行

```bash
# 构建整个项目
mvn clean install

# 运行Web服务
cd hiphop-ghetto-web
mvn spring-boot:run
```

## 配置要求

- MySQL 8.0+
- Redis 5.0+
- MongoDB 4.0+
- RabbitMQ 3.8+

## 注意事项

1. 启动前请确保所有依赖服务已正确配置并运行
2. 数据库表结构可通过 `database/table_create.sql` 初始化
3. 敏感配置信息（如密钥）应通过环境变量或外部配置文件注入，避免硬编码