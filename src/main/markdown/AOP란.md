# AOP 란?

* 애플리케이션 전체에 걸쳐 사용되는 기능들을 재사용하도록 지원하는 것이다.
* AOP는 Aspect Oriented Programming의 약자로 관점 지향 프로그래밍이라고 불린다. 그리고 흩어진 Aspect를 모듈화 할 수 있는 프로그래밍 기법이다.


### AOP가 필요한 상황

* 모든 메소드의 호출 시간을 측정하고 싶다면?
* 메소드가 천개라면 하나하나 다 작성해야 한다.(그리고 만약 수정을 해야한다면? 하나하나 다시 바꿔야한다)

![AOP](https://user-images.githubusercontent.com/45676906/91657512-6b3c6600-eafc-11ea-9345-c6a11fe28447.png)

<br>

### 시간측정 예제

```java
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MemberService {

    /**
     * 회원가입
     */
    public Long join(Member member) {
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId(); } finally {
            long finish = System.currentTimeMillis(); long timeMs = finish - start; 
            System.out.println("join " + timeMs + "ms");
        } 
    }
    
    /**
     *전체 회원 조회
     */
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start; System.out.println("findMembers " + timeMs + "ms");
        } 
    }
}
```

### 문제

* 위와 같이 메소드마다 초를 재는 중복되는 코드를 작성해야 한다.
* 시간을 측정하는 로직은 공통 관심 사항이다.
* 시간을 측정하는 로직과 핵심 비즈니스의 로직이 섞여서 유지보수가 어렵다.
* 시간을 측정하는 로직을 변경할 때 모든 로직을 찾아가면서 변경해야 한다.

<br>

## AOP 적용

* AOP : Aspect Oriented Programming
* 공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리


![AOP](https://user-images.githubusercontent.com/45676906/91657701-f407d180-eafd-11ea-817e-a243da242ec6.png)

<br>

### AOP 코드

```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {
    @Around("execution(* com.example.demo..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
```

* @Aspect를 선언해줘야 AOP를 사용할 수 있다.
* @Around 어노테이션을 통해서 범위를 지정해준다. (위와 같이 작성하면 com.example.demo 아래 모두 적용하라는 뜻이다)
* TimeTraceAop를 Bean으로 등록해야 한다.

<br>

### 스프링 컨테이너

![컨테이너](https://user-images.githubusercontent.com/45676906/91658168-c45ac880-eb01-11ea-9543-5346b53d4c26.png)

* AOP를 사용하지 않는 다면 Controller에서 Service를 호출하기 때문에 의존관계는 위와 같다.

<br>

### AOP 적용 후 의존관계
![AOP](https://user-images.githubusercontent.com/45676906/91658274-5bc01b80-eb02-11ea-8366-65cd5ce3e245.png)
![AOP](https://user-images.githubusercontent.com/45676906/91658172-d50b3e80-eb01-11ea-8754-f2f373e3d8ba.png)

* AOP를 지정해주면 Controller가 Service를 호출할 때 실제 Service를 호출하는 것이 아니라 위의 그림처럼 '프록시'라는 것으로 감싸서 가짜 Service를 만든다
* 따라서 프록시를 통해서 joinPoint.proceed()를 호출하여 그 때 진짜 Service를 호출하여 사용하는 것이다. 


  





