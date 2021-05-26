## MyBatis Mapper XML 파일

이번 글에서는 MyBatis를 사용할 때 필요한 개념들 일부를 정리해보겠습니다. 

```java
@Mapper
public interface MemberMapper {

    // 회원가입
    int insertMember(Member member);
    // 로그인 아이디로 회원 찾기
    Member findById(String id);
}
```

MemberMapper 인터페이스에서 `insertMember`는 회원가입, findById는 로그인 아이디로 회원을 찾을 용도로 간단하게 만들었습니다. 

<br>

## `XML 파일`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="me.gyun.demo.mapper.MemberMapper">
    <select id="findById" resultType="Member">
        SELECT * FROM member WHERE id = #{id}
    </select>

    <insert id="insertMember" parameterType="me.gyun.demo.dto" useGeneratedKeys="true" keyProperty="memberIdx">
        INSERT INTO member (id, password) VALUES(#{id}, #{password})
    </insert>
</mapper>
```

여기 보면 `resultType`, `parameterType`, `useGenerateKeys`, `keyProperty` 등등.. 처음 보는 것들이 있는데요. 하나씩 어떤 건지 알아보겠습니다. 

- `namespace` : 위에서 작성했던 `@Mapper` 어노테이션이 있는 인터페이스의 `패키지 경로 + 인터페이스이름`을 적어주면 됩니다.

- `id` : `id`를 통해서 쿼리와 어떤 메소드를 연결할지를 정할 수 있습니다. 따라서 인터페이스 내부의 연결시킬 메소드 이름을 적어주면 됩니다.

- `resultType` : 쿼리의 결과 값의 형태를 적어주면 됩니다. 예를들어 int형으로 쿼리가 나온다면 `int`를 적어주고, `Member`테이블의 형태로 나온다면 
`Member`클래스의 이름을 적어주면 됩니다. 즉, 인터페이스의 메소드 return 타입과 resultType을 똑같이 작성하면 됩니다.

![스크린샷 2021-05-26 오전 11 13 21](https://user-images.githubusercontent.com/45676906/119592493-8b8f1c00-be13-11eb-8ea2-8db941654be0.png)

<br>

- `parameterType` : 인터페이스 내부의 메소드에서 파라미터를 사용할 수 있습니다. 이 때 파라미터의 `패키지명 + 클래스이름`을 적어주면 됩니다.

![스크린샷 2021-05-26 오전 11 17 40](https://user-images.githubusercontent.com/45676906/119592811-225bd880-be14-11eb-83fc-8aa06ed6e6b0.png)

<br>

- `userGeneratedKeys` : `INSERT, UPDATE`에서만 사용합니다. 데이터베이스 테이블에서 Auto Increment 값을 받아올 때 사용합니다. 아주 많이 사용되는.. (Default 값은 false)

- `keyProperty` : `INSERT, UPDATE`에서만 사용합니다. 테이블에서 Auto Increment된 값을 받을 이름을 적어주면 됩니다.(ex) 클래스의 필드 이름) 

![스크린샷 2021-05-26 오전 10 23 49](https://user-images.githubusercontent.com/45676906/119593162-a0b87a80-be14-11eb-8d02-6e8f45eef45d.png)

어노테이션에는 위와 같이 사용할 수 있습니다.

<br>

![스크린샷 2021-05-26 오전 11 24 22](https://user-images.githubusercontent.com/45676906/119593453-f55bf580-be14-11eb-98f2-41c37446ee42.png)

XML에서는 위와 같이 사용하면 됩니다!

<br>

<br>

## `MyBatis 파라미터`

MyBatis에서는 파라미터로 넘어온 값을 쿼리에 적용하기 위해서 `#{}`을 사용하는데요. 이것에 대해서 좀 더 알아보겠습니다.

<br>

### `사용법1`

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    Member findByMemberId(int memberIdx, String id);
}
```

```xml
<mapper namespace="me.gyun.demo.mapper.MemberMapper">
    <select id="findByMemberId" resultType="Member">
        SELECT * 
        FROM member 
        WHERE memberIdx = #{param1} and id = #{param2}
    </select>
</mapper>
```


위와 같이 인터페이스 내부 메소드에서 파라미터를 `param1, param2`의 이름을 사용하여 파라미터를 사용하는 방법이 있습니다.

<br>

### `사용법2`

`@Param`어노테이션을 이용할 수 있다. `@Param`어노테이션을 사용하면 본인이 원하는 이름으로 파라미터를 사용할 수 있습니다.

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    Member findByMemberId(@Param("test1") int memberIdx, @Param("test2") String id);
}
```

```xml
<mapper namespace="me.gyun.demo.mapper.MemberMapper">
    <select id="findByMemberId" resultType="Member">
        SELECT * 
        FROM member 
        WHERE memberIdx = #{test1} and id = #{test2}
    </select>
</mapper>
```

위와 같이 `test1, test2`로 이름을 지정하여 사용할 수 있습니다. 

<br>

### `객체가 파라미터 일 때`

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    @Insert("INSERT INTO member (title) VALUES(#{title})")
    int insertMember(Member member);
}
```

이렇게 객체가 하나일 때는 member 클래스의 필드명만 `#{}`안에 적어주면 알아서 매핑을 할 수 있습니다. 


<br>

### 만약 `parameter1 = 객체, parameter2 = 기본자료형`이라면 어떻게 될까요?

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    int insertMember(Member member, int userIdx);
}
```

![스크린샷 2021-05-26 오전 11 35 50](https://user-images.githubusercontent.com/45676906/119594366-9eefb680-be16-11eb-902e-7433c4054a50.png)

이렇게 매개변수가 `객체`, `일반 변수` 일 때 위와 같이 적으면 아래와 같은 에러가 발생합니다.

<br>

![스크린샷 2021-05-26 오전 11 35 25](https://user-images.githubusercontent.com/45676906/119594308-81225180-be16-11eb-9423-27a6f6c55431.png)

즉, `객체`, `기본자료형`으로 넘어왔을 때 `#{title}, #{part}`로만 적어주면 MyBatis에서는 제대로 직렬화를 할 수가 없습니다. 

<br>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.demo.mapper.MemberMapper">
    <insert id="insertMember" parameterType="com.example.demo.dto.Member" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO member (name, part, userIdx)
        VALUES (#{name}, #{part}, #{userIdx})
    </insert>
</mapper>
```

따라서 이러한 경우에서는 `#{member.name}`와 같이 앞에 파라미터 이름으로 구분을 해서 적어주어야 에러가 발생하지 않습니다. 

