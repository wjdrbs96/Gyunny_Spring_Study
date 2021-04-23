## `@Valid를 이용해 @RequestBody 객체 검증하기`

`@RestController`와 `@RequestBody`를 이용하여 클라이언트로부터 들어오는 값들을 검증하는 방법을 소개한다.
예를들어 로그인을 하는 과정에서 `아이디`, `비밀번호`의 값이 올바른 값으로 오지 않았을 때, 일정길이 이상이 되었으면
할 때 등등의 상황에서 사용할 수 있다.

<br>

### `Gradle`

```
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

### `Maven`

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

<br>


### `@Valid 사용하는 방법`

```java
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginReq {
    @NotNull
    private String id;
    @NotNull
    private String password;
    @Email
    private String email;
}
```

위와 같이 `로그인`정보를 담고 있는 클래스가 있다. `@NotNull`과 `@Email` 어노테이션을 사용하였는데 의미는 아래와 같다.

* `@NotNull` : 필드의 값에 `null`을 허용하지 않는다. (`""`는 가능하다. 그리고 `어떤 타입`이든 수용 가능하다.)

* `@Email` : 필드의 값이 `Email`형식을 갖춰야 한다. 

<br>

```java
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginReq loginReq) {
        try {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
```

따라서 위와 같이 `로그인` 정보를 받는 간단한 `Controller`가 있을 때 `@Valid`어노테이션을 이용해서 실제 객체 검증이 가능한지 테스트 해보자.

<br>

<img src="https://user-images.githubusercontent.com/45676906/93712204-60bb3c80-fb8f-11ea-92ec-9a883c220f4e.png">

<br> 

포스트맨으로 테스트 했을 때 위와 같이 `비밀번호`가 `null`이기 때문에 `400에러`가 발생한다.

<br>

### `테스트 코드로 검증`

```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void loginTest() throws Exception {
        LoginReq loginReq = LoginReq.builder()
                .id("id")
                .password("123")
                .email("wjdrbs966naver.com")   // 이메일 형식에 맞지 않음
                .build();

        String loginDto =  objectMapper.writeValueAsString(loginReq);
        mockMvc.perform(post("/user/login")
                        .content(loginDto)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
    }
}
```

위와 같이 `이메일 주소`의 형식을 일부로 틀리게 한 후에 테스트 코드로 테스트를 해볼 수도 있다.

<br>

### `@Valid시 발생하는 Exception Handling`

`@Valid`로 `@ReustBody`로 들어온 객체 검증에서 `Bad Request`가 발생할 때 `Custom한 errorHandling`을 할 수 있다.

```java
@ControllerAdvice
@Slf4j
public class ExceptionController {

    /*
      * @valid 유효성 체크에 통과하지 못하면 MethodArgumentNoValidException이 발생한다.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity test(MethodArgumentNotValidException e) {
        List<String> errorList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(c -> {
            errorList.add(c.getDefaultMessage());
        });

        return new ResponseEntity(ErrorException.res(StatusCode.BAD_REQUEST, errorList), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

`@Valid`의 검증에서 예외가 발생하면 `MethodArgumentNotValidException`에서 처리하기 때문에 `@ExceptionHandler` 어노테이션을 이용해서
`MethodArgumentNotValidException`의 에러를 위의 메소드로 처리하게 된다.

<br>

그리고 `@ControllerAdvice`는 `Controller`가 실행되면 자동으로 같이 실행이 되는 어노테이션이다. 

<br>

```
e.getBindingResult().getAllErrors().forEach(c -> {
    errorList.add(c.getDefaultMessage());
});
```

그리고 위의 코드로 현재 발생한 에러의 `default Message`를 꺼낼 수 있다. 그리고 발생한 에러 메세지가 여러개일 수 있기 때문에
`List`형태로 담아서 응답을 보내려 한다.

<br>

<img src="https://user-images.githubusercontent.com/45676906/93714589-c19e4100-fb9e-11ea-9426-bda9c53dbb1c.png">

<br>


따라서 위와 같이 `비밀번호`, `이메일` 에서 2개의 예외가 발생하면 메시지도 2개가 나오는 것을 확인할 수 있다.


<br>

그리고 `Request Header에 데이터의 값을 찾지 못할 때` 에러를 담는 클래스는 아래와 같다. 

<br>

### `MissingRequestHeaderException`

```java
@ControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity notHeaders() {
        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, ResponseMessage.NULL_VALUE), HttpStatus.BAD_REQUEST);
    }

}
```

<br>

Request로 부터 Multipart 자료형과 같이 `Request Body가 아닌 form-data Request에서 받고 @Valid 에러`가 발생했을 때

<br>

### `BindException`

```java
@ControllerAdvice
@Slf4j
public class AdviceController {
    @ExceptionHandler(BindException.class)
    public ResponseEntity bindException(BindException b) {
        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, b.getMessage()), HttpStatus.BAD_REQUEST);
    }
    
}
```


<br>

## `@Valid 검증 annotation`

* ### NotBlank
    * null이 아닌 값이다.
    * 공백이 아닌 문자를 하나 이상 포함한다.
    * 공백 문자를 제외한 길이가 0보다 커야 한다.
    
* ### NotEmpty
    * null이거나 empty(빈 문자열)이 아니어야 한다. (길이 혹은 크기가 0보다 커야함)
    
* ### NotNull
    *  null이 아닌 값이다.
    
* ### max
    * 지정된 최대 값보다 작거나 같아야 한다.
    
* ### min
    * 지정된 최소 값보다 크거나 같아야 한다.
    
* ### size(min = , max = )
    * 문자열 또는 배열이 지정된 값 사이일 경우 통과
    

<br>

## `정리가 잘 된 블로그`

* [https://cheese10yun.github.io/spring-guide-exception/](https://cheese10yun.github.io/spring-guide-exception/)

* [https://jyami.tistory.com/55](https://jyami.tistory.com/55)

* [https://meetup.toast.com/posts/223](https://meetup.toast.com/posts/223)
