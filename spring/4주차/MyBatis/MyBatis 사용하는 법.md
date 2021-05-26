# `MyBatis 사용하는 법`

이번 글에서는 MyBatis 원리에 대한 내용은 다루지 않고 MySQL 기반의 MyBatis를 사용하는 법에 대해서만 간단하게 다루겠습니다.(세세하게 사용하는 부분은 다른 글에서 다루겠습니다. ex: ResultType, ParameterType, @Param 등등..)

<br>

## `Maven`

```
// MyBatis
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>

// MySQL
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

<br>

## `Gradle`

```
// MySQL
runtimeOnly 'mysql:mysql-connector-java'
// MyBatis
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.4'
```

사용하고 있는 것에 맞게 `MyBatis`, `MySQL` 의존성을 먼저 추가하겠습니다. 

<br>

## `application.yml`

![스크린샷 2021-05-26 오전 9 56 39](https://user-images.githubusercontent.com/45676906/119586983-b4f67a80-be08-11eb-8a9d-8aa2a7923e5c.png)

Spring Project를 만들면 Default로 `application.properties`가 만들어집니다. 이것을 `application.yml`로 바꾸겠습니다.

<br>

![스크린샷 2021-05-26 오전 10 01 44](https://user-images.githubusercontent.com/45676906/119587576-ede31f00-be09-11eb-882b-e19d81fa54f5.png)


```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?useUnicode=yes&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Seoul
    username: root
    password: root

mybatis:
  type-aliases-package: com.yapp.ios1
  mapper-locations: /mapper/**/*.xml
```

- url에서 현재는 `localhost`를 사용하기 때문에 위와 같이 적었습니다. 만약 AWS RDS를 사용하는데 그 엔드포인트를 적어야 한다면 `반드시` gitignore를 적용하고 Github에 올리셔야 합니다.
- mybatis type-aliases, mapper-location은 아래에서 좀 더 자세히 알아보겠습니다. 

<br>

![스크린샷 2021-05-26 오전 10 12 14](https://user-images.githubusercontent.com/45676906/119588039-ebcd9000-be0a-11eb-8639-0c211ae255d4.png)

참고로 MySQL에서 위의 이름이 `스키마 이름`입니다. 이것을 엔드포인트 뒤에 적어주셔야 합니다.

```
url: jdbc:mysql://엔드포인트:3306/스키마이름?useUnicode=yes&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Seoul
```

<br>

그리고 MyBatis를 사용하는 방법 `2가지`에 대해서 알아볼 것인데요. 2가지 방법은 아래와 같습니다. 

- `어노테이션 기반의 쿼리 작성`
- `xml 기반의 쿼리 작성`

<br>

하나씩 알아보겠습니다. 

<br>

## `어노테이션 방법`

![스크린샷 2021-05-26 오전 10 16 37](https://user-images.githubusercontent.com/45676906/119588306-7e6e2f00-be0b-11eb-9771-73f2bb0013d6.png)

MyBatis를 DAO의 이름은 `mapper`로 사용하는 것이 대부분입니다. 그래서 mapper 패키지를 만든 후에 `HelloMapper` 인터페이스를 만들겠습니다.

<br>

![스크린샷 2021-05-26 오전 10 19 29](https://user-images.githubusercontent.com/45676906/119588523-f50b2c80-be0b-11eb-8514-2db4a84e95d1.png)

그리고 `@Mapper`라는 어노테이션을 맨 위에 추가하고 사용할 쿼리에 맞는 어노테이션을 위와 같이 사용 후 그 안에 쿼리를 작성하면 됩니다. 사용법만 보면 정말 간단합니다.(다만 어떻게 `@Mapper` 어노테이션을 추가했다고 해서 이런 일이 가능한지 원리는 알아야 할 것입니다. 다른 글에서.. 정리하기로..)

<br>

## `xml 사용하는 방법`

저는 어노테이션의 방법보다는 xml로 하는 방법을 선호하고 있습니다. 뭔가 쿼리는 xml에다 따로 관리하면 가독성도 좋아지고 여러므로 좋다고 생각합니다.  

![스크린샷 2021-05-26 오전 10 27 43](https://user-images.githubusercontent.com/45676906/119589110-1caec480-be0d-11eb-8f11-ab8d3dd589ab.png)

그리고 `Mapper` 인터페이스에 존재하는 메소드들과 xml에 있는 쿼리를 1:1로 매핑시켜서 사용해야 합니다. 그래서 xml을 작성해보겠습니다.

<br>

![스크린샷 2021-05-26 오전 10 29 56](https://user-images.githubusercontent.com/45676906/119589237-5b447f00-be0d-11eb-9315-4d554755934d.png)

패키지에서 `resources/mapper` 디렉토리 구조를 만들고 거기서 `HelloMapper.xml`을 만들겠습니다. 그리고 위의 yml 파일에서 mybatis 설정을 했었는데요.
그 설정 중에 하나가 여기서 사용됩니다.

```
mapper-locations: /mapper/**/*.xml
```

이 설정의 의미는 다음과 같습니다. **는 하위 폴더 레벨에 상관없이 모든 경로를 뜻하며, *.XML은 어떠한 이름도 가능하다는 뜻입니다. 
예를들면 mapper/me/gyun/Member.XML도 가능하고, mapper/Member.XML, mapper/a/b/Member.XML도 가능하다는 뜻입니다.


<br>

![스크린샷 2021-05-26 오전 10 32 48](https://user-images.githubusercontent.com/45676906/119589548-fccbd080-be0d-11eb-94de-c4bf288f5441.png)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.demo.mapper.HelloMapper">
    <select id="getGroup">
        SELECT *
        FROM user
    </select>

    <insert id="insertGroup">
        INSERT INTO ~
    </insert>

    <update id="update">
        UPDATE ~~
    </update>

    <delete id="delete">
        DELETE ~~
    </delete>
</mapper>
```

xml에서도 위와 같이 사용하면 됩니다. 다만 namespace에 `@Mapper` 어노테이션이 붙어 있는 인터페이스와 매칭을 시켜주어야 하고, 해당 쿼리를 작성하는 id 부분에 메소드의 이름을 잘 매칭시켜 적어주어야 올바르게 작동합니다.
