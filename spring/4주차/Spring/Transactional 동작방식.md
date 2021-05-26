# `Transactional 어노테이션 동작 방식`

이번 글에서는 `@Transactional` 어노테이션이 동작하는 방식에 대해서 알아보겠습니다. 저도 개발을 하면서 자주 사용했던 어노테이션입니다. 하지만 정확한 원리를 알고 썼다기 보단 이름에서 알 수 있듯이
사용하면 어떤 작업을 트랜잭션을 걸어 중간에 오류나면 롤백을 시켜주는구나? 정도의 느낌만 알고 사용했었는데요. 

이번 글에서 `@Transactional` 어노테이션이 어떤 원리로 작동하는지에 대해서 알아보겠습니다. 

<img width="356" alt="스크린샷 2021-05-04 오후 2 25 17" src="https://user-images.githubusercontent.com/45676906/116963698-8cd19b00-ace4-11eb-83ae-f158744c50e6.png">

간단한 예제를 보면서 실습해보기 위해서 위와 같이 디비 테이블 설계를 하였습니다. 요약하자면 유저-그룹 간의 관계가 `N:M`이기 때문에 `user-group`이라는 매핑 테이블을 만들었습니다. 

즉, user가 Group을 만들면, Group 테이블에도 INSERT를 하고 user_group에도 INSERT 하게 될 것인데요. 그러한 로직을 `Spring Boot`, `MyBatis`를 이용해서 테스트 해보겠습니다. 

<br>

## `Controller`

```java
@RequiredArgsConstructor
@RestController
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/")
    public String gyun() {
        System.out.println("test");
        helloService.helloService();
        return "Gyunny";
    }
}
```

<br>

## `Service`

```java
@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloMapper helloMapper;

    @Transactional
    public void helloService() {
        GroupDto groupDto = new GroupDto("제목");
        helloMapper.insertGroup(groupDto);                          // 1
        helloMapper.insertGroupUsers(groupDto.getGroupId(), 1);     // 2
    }
}

```

비즈니스 로직을 담당하는 `Service`에서 `@Transactional` 어노테이션이 존재하는 것을 볼 수 있습니다. 그리고 내부 로직을 보면 위에서 말했듯이 `INSERT` 쿼리 2번을 날리고 있습니다. 

<br>

## `Mapper`

```java
@Mapper
public interface HelloMapper {

    @Insert("INSERT INTO gyunny_group (title) VALUES(#{group.title})")
    @Options(useGeneratedKeys = true, keyProperty = "group.groupId")
    int insertGroup(@Param("group") GroupDto groupDto);

    @Insert("INSERT INTO user_group (users_id, gyunny_group_id) VALUES (#{userId}, #{groupId})")
    void insertGroupUsers(@Param("groupId") int groupId, @Param("userId") int userId);
}
```

전체 코드는 위와 같습니다. 이 글에서는 코드가 중요한 것이 아니라 `@Transacntional` 어노테이션 동작 방식이 중요하기 때문에 MyBatis를 모른다고 해도 무방할 것 같습니다. (DB 개념만 안다면 충분하다고 생각합니다.)

위의 코드를 동작시키면 아무 문제 없이 작동합니다. 그런데 `@Transacntional` 어노테이션 동작 방식은 모르더라도 트랜잭션의 의미는 대략이라도 알고 계실 것입니다. (중간에 오류가 나면 오류가 발생하기 직전까지의 작업들은 실행되기 전의 상태처럼 롤백되어야 할 것입니다.)

정말 그렇게 되는지 `insertGroupUsers`에서 쿼리 에러를 고의로 만들어보겠습니다.

<br>

![스크린샷 2021-05-04 오후 2 34 18](https://user-images.githubusercontent.com/45676906/116964204-e7b7c200-ace5-11eb-9377-52e42a0df767.png)

그리고 실행을 시킨 후에 호출을 해보겠습니다. 

<br>

![스크린샷 2021-05-04 오후 2 35 59](https://user-images.githubusercontent.com/45676906/116964259-0cac3500-ace6-11eb-8b4c-f45c68dcd6c9.png)

그러면 당연히 에러가 발생하는데요. 로그에는 어떻게 찍히는지 확인해보겠습니다.

<br>

![스크린샷 2021-05-04 오후 2 36 59](https://user-images.githubusercontent.com/45676906/116964371-539a2a80-ace6-11eb-9b60-9bf1f645441b.png)

로그를 보면 예상했던 대로 `SQLSyntexErrorException`이 발생했는데요. 잘 보면 두번 째 INSERT 쿼리에서 에러가 발생했습니다. 그러면 첫 번째 쿼리는 잘 수행되었다는 건데 디비에 INSERT가 되었을까요? 안되었을까요? 

<br>

<img width="192" alt="스크린샷 2021-05-04 오후 2 39 42" src="https://user-images.githubusercontent.com/45676906/116964469-91974e80-ace6-11eb-8114-6def52a711b5.png">

디비에도 값이 들어가지 않은 것을 볼 수 있습니다. `@Transacntional` 어노테이션 붙어서 `RollBack`을 시켜줬을 것이라 추측이 되는데요. 그러면 어노테이션이 없다면 첫 번째 쿼리는 DB에 들어가게 될까요? 

```java
@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloMapper helloMapper;

    //@Transactional
    public void helloService() {
        GroupDto groupDto = new GroupDto("제목");
        helloMapper.insertGroup(groupDto);
        helloMapper.insertGroupUsers(groupDto.getGroupId(), 1);
    }
}
```

위와 같이 `@Transactional`을 주석처리 한 후에 다시 실행시켜보았습니다. 역시 똑같이 `insertGroupUsers`에서 `SQLSyntaxException`이 발생합니다. 

<img width="133" alt="스크린샷 2021-05-04 오후 2 41 14" src="https://user-images.githubusercontent.com/45676906/116964568-d15e3600-ace6-11eb-8b99-2b28f6deaf34.png">

그리고 결과를 보면 이번에는 `insertGroup`에 해당 하는 쿼리는 DB에 잘 들어가있는 것을 볼 수 있습니다. 확실히 `@Transactional` 어노테이션이 없다면 롤백을 하지 못하는 것을 볼 수 있습니다. (실제 상황이라면 상당히 골치아픈 문제가 발생했을 것입니다..)

<br>

다시 `@Transactional` 어노테이션 주석을 풀고 몇 번 실행을 더 시켜보았습니다.(당연히 SQL 문법 에러가 반복되었는데요) 그 과정에서 저에게는..? 흥미로운 점을 하나 발견했습니다.

![스크린샷 2021-05-04 오후 2 49 35](https://user-images.githubusercontent.com/45676906/116965096-00c17280-ace8-11eb-9ffc-8e0be05ab483.png)

<br>

<img width="210" alt="스크린샷 2021-05-04 오후 2 47 26" src="https://user-images.githubusercontent.com/45676906/116964954-ae805180-ace7-11eb-9ed5-42cbd5ba38a6.png">

실패의 경우로 몇 번 해보다가 쿼리 오류로 고치고 시도해보면 위와 같이 데이터는 예상했던대로 무사히 두 곳 모두 잘 들어갑니다. 그런데..? `gyunny_group_id`를 보면 `2 -> 6`으로 폴짝 뛴 것을 볼 수 있습니다. 

롤백 과정에서 PK 값은 계속 증가하는 거 같은데요. 이것을 좀 더 알아보기 위해서 디버깅을 해보았습니다. 

<br>

![스크린샷 2021-05-25 오후 5 15 10](https://user-images.githubusercontent.com/45676906/119463480-c42fe680-bd7c-11eb-953c-5e50eb8ad5cc.png)

위와 같이 하고 `디버그 모드`로 실행시켜보고 `insertGroup`이 잘 INSERT가 되는지? 테스트를 먼저 해보겠습니다. 

<br>

![스크린샷 2021-05-25 오후 5 19 26](https://user-images.githubusercontent.com/45676906/119464071-5cc66680-bd7d-11eb-875a-0ae07195249d.png)

그리고 디버그 모드로 실행시켜서 확인해보면 위와 같이 원하는 지점에서 멈춘 것을 확인할 수 있습니다. 그러면 그 위에까지는 실행이 되었으니까 DB에는 잘 INSERT가 되었는지 궁금해서 확인해보았습니다. 

<br>

<img width="150" alt="스크린샷 2021-05-25 오후 5 20 48" src="https://user-images.githubusercontent.com/45676906/119464302-8da69b80-bd7d-11eb-8596-636ad70677c4.png">

그런데.. 디비에는 데이터가 제대로 안들어간 것을 볼 수 있습니다. 그래서 `@Transacntional`이 붙어있어서 두 개의 쿼리가 실행된 후에 COMMIT이 일어나서 아직 디비에 반영이 안된건가? 라는 생각에 디버그를 다음으로 계속 실행해보았습니다.

<br>

![스크린샷 2021-05-25 오후 5 23 27](https://user-images.githubusercontent.com/45676906/119464716-fc83f480-bd7d-11eb-8336-6e877cdf5bd2.png)

그리고 위와 같이 다음 버튼을 누른 후에 다시 DB를 확인해보았습니다. 

<br>

![스크린샷 2021-05-25 오후 5 24 35](https://user-images.githubusercontent.com/45676906/119464899-289f7580-bd7e-11eb-9f93-a273ee6b67a0.png)

그러면 데이터가 잘 들어간 것을 볼 수 있는데요. 그런데 PK 값이 6 -> 7이 아니라 6 -> 8로 늘어난 것을 볼 수 있습니다. 왜 그런가 생각해보니 방금 위에서 테스트를 해보다가 디버그 모드를 재실행 한 상태입니다. (즉, 디버그 모드를 2번 실행한..)
그래서 첫 번째 실행했을 때 실행하다가 종료 했기 때문에 id가 7인 데이터가 들어갔다가(COMMIT이 되지는 않았지만) 제가 두 번째 INSERT 쿼리는 날리지 않았기 때문에 롤백 시켜서 7이 사라진 것입니다. 

<br>

![스크린샷 2021-05-25 오후 5 30 14](https://user-images.githubusercontent.com/45676906/119465709-e1fe4b00-bd7e-11eb-8499-9e4442d8080c.png)

그러면 이번에는 `@Transacntional` 어노테이션이 없는 상태로 똑같은 테스트를 해보겠습니다. 

![스크린샷 2021-05-25 오후 5 30 46](https://user-images.githubusercontent.com/45676906/119465805-fa6e6580-bd7e-11eb-8198-46440894a630.png)

확실히 이번에는 바로 데이터가 들어간 것을 볼 수 있습니다. `@Transactional` 고립 레벨과 관련이 있는 내용인 거 같습니다. ([트랜잭션 고립레벨이란?](https://devlog-wjdrbs96.tistory.com/334))


<br>

<br>

![스크린샷 2021-05-25 오후 5 16 01](https://user-images.githubusercontent.com/45676906/119463687-f3465800-bd7c-11eb-9984-c84c51c8540d.png)

그리고 이번에는 다시 어노테이션을 넣고 두 번째 쿼리는 위와 같이 일부로 `SQLException`이 발생하도록 하였습니다. 

<br>

![스크린샷 2021-05-25 오후 5 48 57](https://user-images.githubusercontent.com/45676906/119468516-7c5f8e00-bd81-11eb-9a6c-22ec626e05a9.png)

그리고 위와 같이 다시 디버그 모드 후에 `다음`을 눌러서 롤백 과정이 어떻게 이루어 지는지 확인해보겠습니다.

<br>


<br>


<br>

## `Transactional(readOnly=true)`

```java
@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloMapper helloMapper;

    @Transactional(readOnly = true)
    public void helloService() {
        GroupDto groupDto = new GroupDto("제목22");
        helloMapper.getGroup();            // 읽기 작업
        helloMapper.insertGroup(groupDto); // 쓰기 작업
    }
}
```

`readOnly=true`를 사용해서 트랜잭션을 `읽기 전용`으로 만들 수 있습니다. 즉, 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 방지할 수 있습니다. 또한 읽기 전용으로 사용하게 되면 성능을 최적화하는데도 사용이 됩니다.  

<br>

![스크린샷 2021-05-04 오후 3 23 42](https://user-images.githubusercontent.com/45676906/116967252-daea9c80-acec-11eb-8460-2bbdb5a99a72.png)

일반적으로 읽기 전용 트랜잭션이 시작된 이후 `INSERT, UPDATE, DELETE 같은 쓰기 작업`이 진행되면 위와 같은 예외가 발생합니다.

<br>

# `Referenece`

- [https://springsource.tistory.com/136](https://springsource.tistory.com/136)