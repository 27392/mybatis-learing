# JDBC回顾与问题分析

## 准备工作

### 安装 mysql (Docker)

> 镜像地址: https://hub.docker.com/_/mysql

> 教程地址: https://www.cnblogs.com/sablier/p/11605606.html

拉取镜像文件

```shell
docker pull mysql:5.7.35
```

启动容器

```shell
docker run -p 3306:3306 --name mysql5.7.35 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7.35
```

## JDBC方式操作数据库

```java
// 1. 加载数据库驱动
Class.forName("com.mysql.cj.jdbc.Driver");

// 2. 通过驱动管理类获取数据库连接
Connection connection = DriverManager.getConnection("jdbc:mysql:/mybatis?characterEncoding=utf-8", "root", "root");

// 3. 定义sql语句
String sql = "select * from user where name = ?";

// 4. 获取预处理statement
PreparedStatement preparedStatement = connection.prepareStatement(sql);

// 5. 设置参数
preparedStatement.setString(1, "tom");

// 6. 向数据库发出sql执⾏查询,查询出结果集
ResultSet resultSet = preparedStatement.executeQuery();

// 7. 遍历查询结果集
List<User> list = new ArrayList<>();
while (resultSet.next()) {
    int    id       = resultSet.getInt("id");
    String username = resultSet.getString("name");
    User user = new User();
    user.setId(id);
    user.setName(username);
    list.add(user);
}
```


## 问题分析

### 数据库配置信息存在硬编码

```java
Class.forName("com.mysql.cj.jdbc.Driver");

DriverManager.getConnection("jdbc:mysql:/mybatis?characterEncoding=utf-8", "root", "root");
```

> 解决方案: 使用配置文件存储数据库配置信息

### 频繁的创建释放数据库连接

```java
DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis? characterEncoding=utf-8", "root", "root");
```

> 解决方案: 使用连接池`DBCP`、`c3p0`、`Druid`、`HikariCP`等


### Sql语句、设置参数存在硬编码

```java
String sql = "select * from user where name = ?";

PreparedStatement preparedStatement = connection.prepareStatement(sql);

preparedStatement.setString(1, "tom");
```

> 解决方式: Sql语句存放在配置文件中


### 手动封装返回结果集,较为繁琐

```java
List<User> list = new ArrayList<>();
while (resultSet.next()) {
    int    id       = resultSet.getInt("id");
    String username = resultSet.getString("name");
    User user = new User();
    user.setId(id);
    user.setName(username);
    list.add(user);
}
```

> 解决方式: 使用反射


## 工具类

## 自定义框架

使用方: 
    需要提供数据库配置信息以及sql配置信息(sql,参数,返回值)
    (1) sqlMapConfig.xml -> 数据库配置信息(以及所有的mapper.xml的全路径)
    (2) *mapper.xml -> 存放sql配置信息
    

提供方:
    对JDBC代码封装

(1) 加载配置文件,`Resources`类
    // 提供方法读取配置文件
    InputStream getResourceAsStream(String path);

(2) 配置文件类.
`Configuration`核心配置类,存放 `sqlMapConfig.xml`解析出来的内容
`MappedStatement` 映射配置类,存放`mapper.xml`解析出来的内容

(3) 解析配置文件
SqlSessionFactoryBuilder
    // 创建
    SqlSessionFactory builder(InputStream);

SqlSessionFactory
    1. 使用`Dom4j`解析配置文件,将解析出来的内容封装到配置文件类
    2. 创建`SqlSessionFactory`对象.生产`SqlSession`-> 会话对象.(工厂模式)

(4) 创建工厂接口及默认实现类DefaultSqlSessionFactory
创建`SqlSessionFactory`类;
    // 获取sqlSession类
    Sqlsession openSession()

(5) 创建SqlSession接口及默认实现类DefaultSqlSession
定义对数据的增删改查操作

(6) 创建Executor接口及默认实现SimpleExecutor类
// 底层其实就是支持JDBC代码
// Configuration中存放有数据库的信息,MappedStatement有sql信息,Object...则是参数值
// 有这三个信息则可以完成查询
query(Configuration,MappedStatement,Object...);
update();

(7)