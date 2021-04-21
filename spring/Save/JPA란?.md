## 스프링 부트에서 JPA 사용하기

`ORM(Object-Relation-Mapping)`과 `JPA(Java Persistence API`

* 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크

* JPA : ORM을 위한 자바(EE) 표준

<br>

### `pom.xml`

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

위의 의존성을 추가하면 `Spring Data JPA`를 사용할 수 있다.

<br>

### Spring Data JPA 

* Repository 빈 생성

* 쿼리 메소드 자동 구현

* @EnableJpaRepositories 자동 설정됨


<br>

### JPA 예제

```java
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;
}
```

`Account` 클래스를 하나 만들어서 위와 같이 작성하자.

- #### `Entity` : DB 테이블과 매핑될 클래스
- #### `GeneratedValue` : PK의 생성 규칙(스프링부트 2.0 부터는 `strategy = GenerationType.IDENTITY`를 추가해야 `Auto increment`가 됨)

<br>

### Repository 생성

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
```

`JpaRepository`를 상속받아 사용한다. `JpaRepository<a, b>`에서 `a는 테이블과 매핑되는 클래스이름`, `b는 테이블의 pk값`을 써주면 된다.
