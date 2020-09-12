## MyBatis Mapper XML 파일

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    // 회원가입
    int insertMember(Member member);
    // 로그인 아이디로 회원 찾기
    Member findById(String id);
}
```

MemberMapper 인터페이스에서 `insertMember`는 회원가입, findById는 로그인 아이디로 회원을 찾을 용도이다. 따라서 인터페이스
내부에 정의한 메소드를 통해서 XML 파일의 쿼리와 맵핑하여 사용할 것이다. 

<br>

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

XML 파일에 위와 같이 쿼리를 작성할 수 있다. 여기서 `속성`들의 의미를 정리해보려 한다.

* `namespace` : 위에서 작성했던 인터페이스가 존재하는 `패키지 경로 + 인터페이스이름`을 적어주면 된다.

* `id` : `id`를 통해서 쿼리와 어떤 메소드를 연결할지를 정할 수 있다. 따라서 인터페이스 내부의 연결시킬 메소드 이름을 적어주면 된다.

* `resultType` : 쿼리의 결과 값의 형태를 적어주면 된다. 예를들어 int형으로 쿼리가 나온다면 `int`를 적어주고, `Member`테이블의 형태로 나온다면 
`Member`클래스의 이름을 적어주면 된다. (그러면 MyBatis의 기능을 SQL 맵핑을 해준다)

* `parameterType` : 인터페이스 내부의 메소드에서 파라미터를 사용할 수 있다. 이 때 파라미터의 `패키지명 + 클래스이름`을 적어주면 된다.

* `userGeneratedKeys` : `insert, update`에서만 사용한다. 데이터베이스 테이블에서 Auto Increment 값을 받아올 때 사용한다. (Default 값은 false이다)

* `keyProperty` : `insert, update`에서만 사용한다. 테이블에서 Auto Increment된 값을 받을 이름을 적어주면 된다. 예를들어 위의 코드를 보면
Member 클래스 내부에 memberIdx 필드가 존재하는데 Auto Increment 값을 Mmeber 클래스 안에 memberIdx 필드에 setter를 통해서 받아오기 위해서
사용하는 것이다.


<br>

### MyBatis 파라미터 

#### 사용법1

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    Member findByMemberIdxAndId(int memberIdx, String id);
}
```

```xml
<mapper namespace="me.gyun.demo.mapper.MemberMapper">
    <select id="findByMemberIdxAndId" resultType="Member">
        SELECT * FROM member WHERE memberIdx = #{param1} and id = #{param2}
    </select>
</mapper>
```


위와 같이 인터페이스 내부 메소드에서 파라미터를 `param1, param2`의 이름을 사용하여 파라미터를 사용하는 방법이 있다.

<br>

### 사용법2

`@Param`어노테이션을 이용할 수 있다. `@Param`어노테이션을 사용하면 본인이 원하는 이름으로 파라미터를 사용할 수 있다.
예를들면 아래와 같다. 

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    Member findByMemberIdxAndId(@Param("test1") int memberIdx, @Param("test2") String id);
}
```

```xml
<mapper namespace="me.gyun.demo.mapper.MemberMapper">
    <select id="findByMemberIdxAndId" resultType="Member">
        SELECT * FROM member WHERE memberIdx = #{test1} and id = #{test2}
    </select>
</mapper>
```

위와 같이 `test1, test2`로 이름을 지정하여 사용할 수 있다. 

<br>

### 만약 `parameter1 = 객체, parameter2 = 기본자료형`이라면 어떻게 될까?

```java
import me.gyun.demo.dto.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    int insertMember(Member member, int userIdx);
}
```

```xml
<mapper namespace="me.gyun.demo.mapper.MemberMapper">

    <insert id="insertMember" parameterType="me.gyun.demo.dto.Member" useGeneratedKeys="true" keyProperty="member.memberIdx">
        INSERT INTO member (id, password, userIdx) VALUES(#{member.id}, #{member.password}, #{userIdx})
    </insert>

</mapper>
```

위의 경우처럼 `객체`, `기본자료형`으로 넘어왔을 때 `#{member}, #{userIdx}`로만 적어주면 MyBatis에서는 
SQL과 객체를 맵핑할 때 구분을 할 수 없기 때문에 `#{member.id}`와 같이 구분을 해서 적어주어야 한다. 

