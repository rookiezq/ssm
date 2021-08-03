# SSM整合

## Quickstart

项目地址https://github.com/rookiezq/ssm/tree/master

1. git clone到本地
2. 创建数据库名scott，执行项目根目录下`db.sql`表文件
3. 修改`resources/dbconfig.properties`数据库相关配置
4. idea中`Project Structure`-`Artifacts`-`WEB-INF`创建`lib`文件夹中右击`Add Copy of`-`Library Files`将所有除了`servlet-api`和`jsp-api`的依赖导入
5. 使用Tomcat9 启动（如果是7需要将pom.xml中的servlet-api和jsp-api替换成tomcat/lib目录下的）

## 项目创建

1. idea创建一个最基本的maven项目
2. 项目右键--Add Framework Support--Web Application

## 导入依赖

### 必需

#### Spring

```xml
<!--spring核心-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.3.RELEASE</version>
</dependency>
<!--aop-->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.5</version>
</dependency>
<dependency>
    <groupId>aopalliance</groupId>
    <artifactId>aopalliance</artifactId>
    <version>1.0</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>5.2.3.RELEASE</version>
</dependency>
<!--cglib-->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
```

#### SpringMVC

```xml
<!--spring mvc-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>5.2.3.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.3.RELEASE</version>
</dependency>
<!--tomcat servlet相关-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.0</version>
    <scope>provided</scope>
</dependency>
```

#### MyBatis

```xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.4</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.4</version>
</dependency>
<!--数据库连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
<!--jdbc驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

#### 日志

```xml
<!--日志-->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

以上就是SSM整合三大框架所有必须的依赖，下面的就是可选依赖，按需导入

### 非必需

#### 单元测试

```xml
<!--单元测试-->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

#### Json

```xml
<!--json-->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.10.3</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.3</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.10.3</version>
</dependency>
```

#### 文件上传

```xml
<!--文件上传-->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.6</version>
</dependency>
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
```

#### 数据校验

```xml
<!--数据校验-->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.1.0.Final</version>
</dependency>
```

#### jstl标签

```xml
<!--jstl标签-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```

## 项目初始化

主要是各个框架配置文件的修改

### 前提

1. 包结构主要分为四大类
    - bean 实体类
    - dao 数据持久层
    - service 业务方法层
    - controller 前端控制层
2. resources放置各种配置文件，以下除了web.xml位于WEB-INF中，其他默认在resources下
    - `spring.xml` spring配置文件
    - `springmvc.xml` springmvc配置文件
    - `mybatis.xml` mybatis配置文件
    - `dbconfig.properties` 数据库连接配置文件
    - `com/rookied/dao/XxxDao.xml` 所有mybatis映射文件
    - `log4j.properties` 日志配置文件
3. test存放单元测试类
4. web/WEB-INF/page下放跳转页面jsp等
5. idea中`Project Structure`-`Artifacts`-`WEB-INF`创建`lib`文件夹中右击`Add Copy of`-`Library Files`将所有除了`servlet-api`和`jsp-api`的依赖导入

### SpringMvc

#### web.xml

1. 加载spring配置文件监听器
2. 配置DispatcherServlet，加载springmvc配置文件
3. 字符编码过滤器
    - 解决post请求乱码，get请求在`tomcat/conf/server.xml`中`Connector`中添加`URIEncoding="UTF-8"`
    - 解决响应乱码
4. restful风格过滤器

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--加载spring配置文件监听器-->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring.xml</param-value>
    </context-param>
    <!--配置DispatcherServlet，加载springmvc配置文件-->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!--解决post请求乱码-->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <!--解决响应乱码-->
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    <!--restful风格过滤器-->
    <filter>
        <filter-name>restful</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>restful</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

#### springmvc.xml

1. 加入对应命名空间context、mvc
2. 注解扫描只扫描控制层
3. 静态资源交给DefaultHandler
4. 动态资源扫描
5. 配置视图解析器，前缀、后缀

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--扫描注解只扫描Controller-->
    <context:component-scan base-package="com.rookied" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    <!--静态资源交给DefaultHandler-->
    <mvc:default-servlet-handler/>
    <!--动态资源扫描-->
    <mvc:annotation-driven/>
    <!--视图处理器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/page/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```



### Spring

#### spring.xml

1. 加入对应命名空间context、tx(事务)
2. 注解扫描排除Controller
3. 配置数据库
    - 引入外部db配置文件
    - 配置数据源（连接池）
4. 配置事务
    - 注入事务管理器
    - 开启事务注解
5. 配置MyBatis
    - 注入SqlSessionFactoryBean
    - 指定MyBatis配置文件、mapper映射文件、数据源引用
    - 创建MyBatis扫描器，批量配置映射文件、指定sqlSessionFactoryBeanName

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
">
    <!--注解扫描排除Controller-->
    <context:component-scan base-package="com.rookied" use-default-filters="false">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--引入外部db配置文件-->
    <context:property-placeholder location="classpath:dbconfig.properties"/>
    <!--配置数据源（连接池）-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="url" value="${jdbc.url}"/>
    </bean>
    <!--注入事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--开启事务注解-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    <!--注入SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--MyBatis配置文件-->
        <property name="configLocation" value="classpath:mybatis.xml"/>
        <!--mapper映射文件-->
        <property name="mapperLocations" value="classpath:com/rookied/dao/*.xml"/>
        <!--数据源引用-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--创建MyBatis扫描器-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--批量配置映射文件-->
        <property name="basePackage" value="com.rookied.dao"/>
        <!--指定sqlSessionFactoryBeanName-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
    </bean>
</beans>
```

#### db.properties

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.username=root
jdbc.password=root
jdbc.url=jdbc:mysql://localhost:3306/scott?useSSL=false
```

### MyBatis

#### mybatis.xml

由于数据源都在spring中指定了，所以这里不需要配置了，只需要配置一些全局属性

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--驼峰映射-->
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <!--整个实体类包使用别名-->
    <typeAliases>
        <package name="com.rookied.bean"/>
    </typeAliases>
</configuration>
```

## 项目测试

### 前提

1. 使用Tomcat9，如果是7，需要将上面的servlet-api和jsp-api替换成tomcat/lib目录下的
2. 创建数据库名为scott，mysql表结构和数据在项目根目录下`db.sql`

### 代码

实体类

```java
@Component
public class Emp {
    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hiredate;
    private Double sal;
    private Double common;
    private Integer deptno;
    ...
}
```

DAO

```java
@Repository
public interface EmpDao {
    Emp findEmpByEmpno(Integer empno);
}
```

mapper

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.rookied.dao.EmpDao">
    <select id="findEmpByEmpno" resultType="com.rookied.bean.Emp">
        select * from emp where empno = #{empno};
    </select>
</mapper>
```

Controller

```java
@Controller
public class EmpController {
    @Autowired
    EmpDao empDao;
    @RequestMapping("/emp")
    public ModelAndView query(Integer empno) {
        Emp emp = empDao.findEmpByEmpno(empno);
        ModelAndView mv = new ModelAndView();
        mv.addObject("ename", emp.getEname());
        mv.setViewName("success");
        return mv;
    }
}
```

视图success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${ename}
</body>
</html>
```

### 测试

浏览器访问http://localhost:8080/ssm/emp?empno=7369

页面跳转success.jsp，显示ename



到此测试通过，之后新项目可以直接在该项目上搭建。