# `Spring Layer에 대한 학습`

이번 글에서는 Spring에서 Layer를 나눌 때 어떤 식으로 보통? 하는가에 대해서 간략하게 정리해보겠습니다. Layer에 대한 개념은 복잡하게 들어가면 들어가수록 머리 아픈 주제인 것 같고.. 사람 마다 의견 차이도 존재하는 영역인 거 같습니다. 

그래서 이번 글에서는 너무 복잡하게 하진 않고 Layer 마다 보통은 어떤 역할을 하고 용어에 대해서 좀 정리를 해볼까 합니다. 

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbRKzjn%2FbtqHoqiwtuG%2Fkm0VKjT5YI65M6kjyiQVV1%2Fimg.png)

전체 큰 구조로 보면 위와 같이 표현할 수 있을 것 같습니다. `Controller`, `Service`, `DTO`, `DAO` 등등의 용어 들이 보이는데요. 하나씩 어떤 의미인지 간단하게 살펴보겠습니다.

<br>

## `Presentation Layer`

`Presentation Layer`는 그림에서 보면 알 수 있듯이 클라이언트의 요청을 받아서 처리하는 역할을 하고 있는 것을 볼 수 있습니다. 
MVC 패턴 중에 하나 C의 역할인 `Controller`는 어떤 역할을 하는 곳일까요? NodeJS에서 `router`의 역할과 비슷하다고 생각합니다. 

즉, API URI를 담당하고 해당 URI로 사용자의 요청이 들어오면 요청에 맞는 응답을 보내주는 역할일 것입니다.(view의 응답이라면 view 이름, JSON 이라면 JSON 형태 등등..?)

<br>

## `Business Layer`

Service 계층이라고도 하는 `Business Layer`는 어떤 곳일까요? `비즈니스 로직`이라는 말을 많이 들어보셨을 것인데요. 말 그대로 `비즈니스 로직`을 작성하는 계층입니다.

하나 예를 들어보겠습니다. 어떤 유저가 회원가입을 할 때 닉네임을 중복체크를 해야 한다고 가정하겠습니다. 일반적으로 닉네임 중복체크는 서버 통신을 통해서 체크를 할 것입니다.
디비에 해당 닉네임이 존재하는지 안하는지 체크해서 존재한다면.. 해당 뷰에 `닉네임이 존재합니다.` 라고 사용자에게 보여주는 일련의 과정들이 존재합니다.

1. DB 조회 후 닉네임이 존재하면 400 에러 throw
2. DB 조회 후 닉네임이 없다면 Controller로 200 에러 반환하도록 설계

간략하게 말하면 이러한 `비즈니스 로직`이 존재합니다. 이 때 Controller가 `Service` 계층을 거치지 않고 직접 DB를 조회하게 되면 비즈니스 로직은 `Controller`에다 다 작성을 해야 하는 문제가 생깁니다. 이러면.. 계층의 책임 문제가 무너질 것이고 지금은 간단한 코드지만 점점 규모가 커지는 프로젝트라면 나중에는..? 유지보수 할 수 없을 정도의 코드가
되있을 것입니다. 


이러한 이유들 때문에 `Controller`가 DB에 직접 조회하기 전에 `Service` 계층을 놓고 비즈니스 로직을 작성하는 식으로 계층의 역할을 분리하는 것이 일반적입니다. 

<br>

## `Data Object Trasfer`

앞으로 엄청 많이 사용하게 될 텐데요. 계층 간의 데이터를 전달하게 될 때 Dto를 만들어서 사용합니다. 간단하게 말하면 로직을 갖지 않고 `@Getter`, `@setter`, 생성자와 같은 것들만 가진 것을 의미합니다.

가령 로그인을 할 때 클라이언트로부터 `아이디`, `비밀번호`를 받을텐데 그 때도 Dto에 담아서 받습니다.

```java
@AllArgsConstructor
@Getter
public class LoginDto {
    private String id;
    private String password;
}
```


<br>

## `Data Access Layer`

`DAO`라고 불리는 계층은 `Data Access Object`라고 불립니다. 여기는 말 그대로 DB에 접근하는 객체를 뜻하는데요. 나중에 배우게 될 MyBatis, JPA와 같이 DB 관련 기술을 사용할 때 사용하게 될 것입니다. 

- `MyBatis는 Mapper(DAO)를 사용`
- `JPA는 Repository(DAO)를 사용`

위와 같은 용어를 사용해서 설계하게 될 것입니다. 지금은 그냥 디비에 접근하는 객체를 따로 분리를 해놓는구나~ 정도만 알아도 될 것 같습니다. (당연히 이것도.. Controller <-> Service 통신할 때와 Service <-> DAO 통신할 때 사용하는 객체를 분리해야 유지보수에 좋기 때문에 분리하는 것입니다.)

<br>

## `Domain(Entity)`

ORM은 Object Relational Mapping 입니다. 즉, JPA를 사용할 때 객체와 DB 테이블을 매핑해서 사용하게 될 것인데 그 때 디비 테이블과 매핑될 객체를 `Entity` 라고 합니다. 

<br>

![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb9y2vv%2FbtqG7TmUzrp%2FKq6rV9U1j3dUp2wtbWXVT1%2Fimg.png)

