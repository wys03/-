# Student Management System - 使用说明

## 项目概述
基于Spring Boot 2.7.18 + MyBatis-Plus + Redis + MySQL的学生管理系统，支持完整的CRUD操作、多表查询、分页查询、Redis缓存和日志记录。

## 技术栈
- **框架**：Spring Boot 2.7.18 (JDK 1.8兼容)
- **持久层**：MyBatis-Plus 3.5.5
- **数据库**：MySQL 8.0
- **缓存**：Redis
- **连接池**：Druid 1.2.20
- **日志**：Logback
- **测试**：JUnit 5
- **构建工具**：Maven

## 快速开始

### 1. 环境准备
```bash
# 启动MySQL和Redis
docker-compose up -d

# 创建数据库
mysql -h localhost -P 3306 -u root -p < sql/schema.sql
# 密码: root
```

### 2. 运行项目
```bash
# 启动应用
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/Student-0.0.1-SNAPSHOT.jar
```

### 3. 运行单元测试
```bash
# 运行所有测试
mvn test

# 运行单个测试类
mvn test -Dtest=StudentServiceTest
mvn test -Dtest=CourseServiceTest
mvn test -Dtest=ScoreServiceTest
```

### 4. Postman测试
1. 导入 `StudentAPI.postman_collection.json` 到Postman
2. 确保应用运行在 `http://localhost:8080`
3. 按顺序执行各接口测试

## 功能特性

### ✅ 已完成功能

#### 1. 单表CRUD操作
- **学生管理**：增删改查
- **课程管理**：增删改查  
- **成绩管理**：增删改查

#### 2. 多表查询
- 查询学生成绩详情（关联学生、课程、成绩表）
- 根据学生ID查询所有成绩
- 根据课程ID查询所有成绩

#### 3. 分页查询
- 学生分页查询（支持按姓名模糊查询）
- 课程分页查询（支持按课程名模糊查询）
- 成绩分页查询（支持按学生ID筛选）

#### 4. Redis缓存
- 使用 `@Cacheable` 查询时缓存
- 使用 `@CachePut` 更新时刷新缓存
- 使用 `@CacheEvict` 删除时清除缓存
- 缓存Key策略：`{cacheName}::{key}`

#### 5. 日志记录
- 控制台输出SQL和执行时间
- 文件日志按级别分离（info/error）
- 日志滚动策略（按天/按大小）
- 日志路径：`logs/student-info.log`, `logs/student-error.log`

#### 6. 单元测试
- 完整的CRUD测试用例
- 多表查询测试
- 分页查询测试
- Redis缓存验证
- 测试数据自动清理

#### 7. RESTful API
- 统一响应格式：`Result<T>`
- 分页响应格式：`PageResult<T>`
- HTTP状态码正确处理
- 完整的API文档（Postman集合）

### 📋 API接口列表

#### 学生管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/students | 添加学生 |
| GET | /api/students/{id} | 根据ID查询 |
| GET | /api/students | 分页查询 |
| PUT | /api/students/{id} | 更新学生 |
| DELETE | /api/students/{id} | 删除学生 |

#### 课程管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/courses | 添加课程 |
| GET | /api/courses/{id} | 根据ID查询 |
| GET | /api/courses | 分页查询 |
| PUT | /api/courses/{id} | 更新课程 |
| DELETE | /api/courses/{id} | 删除课程 |

#### 成绩管理
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/scores | 添加成绩 |
| GET | /api/scores/{id} | 根据ID查询 |
| GET | /api/scores/student/{id} | 按学生查询 |
| GET | /api/scores/course/{id} | 按课程查询 |
| GET | /api/scores/student/{id}/details | 成绩详情（多表） |
| GET | /api/scores | 分页查询 |
| PUT | /api/scores/{id} | 更新成绩 |
| DELETE | /api/scores/{id} | 删除成绩 |

## 项目结构
```
Student/
├── src/main/java/
│   └── com/itranswarp/learnjava/student/
│       ├── StudentApplication.java          # 启动类
│       ├── common/                          # 公共类
│       │   ├── Result.java                  # 统一响应
│       │   ├── PageResult.java              # 分页响应
│       │   └── StudentScoreVO.java          # 成绩VO
│       ├── config/                          # 配置类
│       │   └── MybatisPlusConfig.java       # MyBatis-Plus配置
│       ├── controller/                      # 控制器
│       │   ├── StudentController.java
│       │   ├── CourseController.java
│       │   └── ScoreController.java
│       ├── entity/                          # 实体类
│       │   ├── Student.java
│       │   ├── Course.java
│       │   └── Score.java
│       ├── mapper/                          # Mapper接口
│       │   ├── StudentMapper.java
│       │   ├── CourseMapper.java
│       │   └── ScoreMapper.java
│       └── service/                         # 服务层
│           ├── StudentService.java
│           ├── CourseService.java
│           ├── ScoreService.java
│           └── impl/                        # 实现类
│               ├── StudentServiceImpl.java
│               ├── CourseServiceImpl.java
│               └── ScoreServiceImpl.java
├── src/main/resources/
│   ├── application.yml                      # 配置文件
│   ├── logback-spring.xml                   # 日志配置
│   └── mapper/
│       └── ScoreMapper.xml                  # 多表查询SQL
├── src/test/java/                           # 单元测试
│   └── com/itranswarp/learnjava/student/
│       ├── StudentApplicationTests.java
│       ├── StudentServiceTest.java
│       ├── CourseServiceTest.java
│       └── ScoreServiceTest.java
├── sql/
│   └── schema.sql                           # 数据库脚本
├── docker-compose.yml                       # Docker配置
├── pom.xml                                  # Maven配置
└── StudentAPI.postman_collection.json     # Postman测试集合
```

## 配置说明

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf8
    username: root
    password: root
  redis:
    host: localhost
    port: 6379
    database: 0

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 打印SQL
```

### Redis缓存策略
- **缓存名称**：`studentCache`, `courseCache`, `scoreCache`, `scoreListCache`
- **Key生成**：`@Cacheable(value = "studentCache", key = "#id")`
- **缓存清除**：`@CacheEvict(value = "studentCache", allEntries = true)`

### 日志配置
- 控制台：输出SQL和DEBUG级别日志
- 文件：
  - `logs/student-info.log`（INFO级别，按天滚动）
  - `logs/student-error.log`（ERROR级别，按天滚动）

## 测试数据

### 初始数据（schema.sql）
- **学生**：5条（张三、李四、王五、赵六、钱七）
- **课程**：5条（Java、数据库、数据结构、操作系统、网络）
- **成绩**：12条关联记录

### 测试流程
1. 先添加学生/课程数据
2. 再添加成绩数据（需要有效的student_id和course_id）
3. 测试多表查询功能

## 常见问题

### Q1: Redis连接失败？
```bash
# 检查Redis是否运行
docker ps | grep redis

# 查看Redis日志
docker logs redis
```

### Q2: MySQL连接失败？
```bash
# 检查MySQL是否运行
docker ps | grep mysql

# 查看MySQL日志
docker logs mysql
```

### Q3: 端口被占用？
```bash
# 修改application.yml中的端口
server:
  port: 8081  # 改为其他端口
```

### Q4: 缓存不生效？
- 检查Redis是否正常运行
- 确认实体类实现Serializable接口
- 查看日志中的缓存操作提示

## 性能优化
- Druid连接池监控（访问：`http://localhost:8080/druid`）
- Redis缓存减少数据库压力
- MyBatis-Plus分页插件优化查询
- 日志异步输出提升性能

## 开发建议
1. 生产环境请修改默认密码
2. 添加接口权限控制（Spring Security）
3. 配置Swagger文档
4. 添加异常处理全局拦截器
5. 实现分布式事务（如需）

## 联系方式
如有问题，请检查日志文件或调试代码。项目已完成所有需求功能！
