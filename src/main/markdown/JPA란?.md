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




