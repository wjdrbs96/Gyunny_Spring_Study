# `Spring MVC 어노테이션 사용법`

이번 글에서는 간단하게 Spring MVC 어노테이션 사용법 정도만 정리를 해보겠습니다. 각각 원리에 대해서는 다른 글들과 종합적으로 보면 도움이 될 것 같습니다. 

<br>

## `GetMapping, PostMapping, DeleteMapping, PutMapping, RequestMapping`

실제 URI 경로 매핑을 하기 위해서는 위의 어노테이션을 사용하게 됩니다. 이름에서 알 수 있듯이 `GET`, `POST`, `DELETE`, `PUT` 방식을 지원하는 어노테이션입니다. 
스프링부트에서 위의 어노테이션을 지원하기 때문에 정말 편하게 경로 맵핑을 할 수 있습니다. 내부적으로 Servlet 등등.. 엄청나게 복잡한 것들을 Spring Boot가 해주지만 일단은 그냥 사용법 정도만 알아도 될 것 같습니다. 

Express.js를 할 때 `rotuer.get()`같은 것을 쓸 때도 이렇게 쓰면 get 방식으로 할 수 있구나~로 쓰지 당장 처음부터 내부적으로 어떤 원리로 돌아가는거지? 하면서 파고들진 않기 때문에 이번에도 그런 방향으로 진행해보겠습니다. (좀 더 추후에 내부 원리까지 공부해보면 좋을 거 같습니다.)

```java
@RestController
public class HelloController {
    
    @GetMapping("/")
    public String test() {
        return "test";
    }
}
```

전부 사용법은 똑같기 때문에 하나의 예시만 보겠습니다. 위와 같이 메소드 위에 어노테이션을 달아주면 해당 경로로 요청이 왔을 때 아래의 메소드가 실행이 된다고 생각하면 됩니다. 

그나저나, 다른 것들은 이름에서 어느정도 유추할 수 있는데 `RequestMapping()`은 무엇일까요? NodeJS로 예를들면, user API를 만들 때 index.js에 공통 경로를 만들고 그 하위에 다른 파일들을 만들어 index.js와 연결을 해서 사용했습니다. 
즉, `RequestMapping()`도 index.js 처럼 공통 경로를 만들 때 사용한다고 생각하면 됩니다.

```java
@RestController
@RequestMapping("/api/v2")
public class HelloController {
    
    @GetMapping("/")
    public String test() {
        return "test";
    }
}
```

위와 같이 `HelloController`에서는 모두 `/api/v2` 경로로 시작해야 한다면 위와 같이 사용할 수 있습니다. 이러한 예시들은 아주 간단한 것들이지만, 일단은 이정도만 알고 넘어가겠습니다. 

<br>

## `PathVariable`

PathVariable 어노테이션은 `http://localhost:8080/1` 와 같이 숫자를 받을 때 사용합니다. 

```java
@RestController
public class HelloController {
    
    @GetMapping("/{idx}")
    public String test(@PathVariable int idx) {
        return "test";
    }
}
```

사용법은 위와 같습니다. `{이름}`을 적어주면 매개변수 위치에도 같은 이름을 적어주어야 위와 같이 어노테이션으로 값을 받아올 수 있습니다. 

<br>

## `RequestParam`

`http://localhost:8080/?name=hi` 와 같이 쿼리스트링의 형태로 요청을 받을 때는 `RequestParam`을 사용합니다. 

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public String test(@RequestParam("name") String name) {
        System.out.println(name);
        return "test";
    }
}
```

즉, String name에는 hi의 값이 들어가게 됩니다. 

<br>

## `RequestBody`

RequestBody는 `HttpMessageConverter`라는 것과 연관이 있습니다. 하지만.. 이것에 대해서는 다른 글로 빼고 이번 글에서는 사용법 위주로만 정리를 해보겠습니다. 

NodeJS에서 POST 방식에서 Request Body 데이터는 `const {id, name} = req.body` 뭐 이런 형태로 사용을 했을 것입니다.

```json
{
  "id": 1,
  "name": "Gyunny"
}
```

위와 같은 형태의 `RequestBody`로 클라이언트가 요청을 할 때 데이터를 받기 위한 어노테이션이 `@RequestBody` 입니다. 

JSON 형태를 받기 위해서는 `dto`의 클래스가 필요합니다. dto는 뭐지? 할 수 있지만 일단은 그냥 데이터를 담는 하나의 클래스라고 생각하시면 됩니다. 

<br>

### `HelloDto`

```java
@ToString
public class HelloDto {
    private Long id;
    private String name;
}
```

위와 같이 `id`, `name`의 값을 확인하기 위해서 `ToSting` Lombok을 추가한 후에 PostMan으로 테스트 해보겠습니다. 

<br>

<img width="633" alt="스크린샷 2021-04-30 오전 11 22 03" src="https://user-images.githubusercontent.com/45676906/116640834-64753400-a9a6-11eb-9a90-0307da30fd28.png">

에러 없이 성공적으로 응답이 오긴 했습니다. Dto 클래스 필드에 값이 잘 매핑이 되었는지 로그를 확인해보겠습니다.  

<br>

```java
@RestController
public class HelloController {

    @PostMapping("/")
    public String test(@RequestBody HelloDto helloDto) {
        System.out.println(helloDto.toString());
        return "test";
    }
}
```

위와 같이 임시로 `System.out.println()`으로 출력을 했습니다. 

```
HelloDto(id=null, name=null)
```

하지만 결과는 위와 같이 `null`이 출력된 것을 볼 수 있습니다. 왜 값이 매핑이 안된 것일까요? 이유는 `Getter`, `Setter`가 없어서 그런데요. 

즉, 다른 글에서 알아볼테지만 `Request Body`의 데이터가 JSON으로 온다면 Spring `Jackson2HttpMessageConverter`가 실행되어 Dto와 값을 매핑을 해줍니다.

<br>

```java
@ToString
@Getter
public class HelloDto {
     private Long id;
     private String name;
}
```

하지만 `@Setter`는 사용하지 않고  `@Getter`만을 사용해서 테스트를 해보겠습니다. 이번에는 `PostMan`이 아니라 테스트 코드를 작성해서 해보겠습니다. 처음에는 포스트맨이 편하고 테스트 코드가 어색할텐데요. (저 또한 아직 어색합니다.) 하지만 계속
작성하는 습관을 길러야 익숙해질 수 있고, 테스트 코드를 작성하는 것은 매우 중요하기 때문에 연습해보겠습니다. 

<br>

```java
@Slf4j
@RestController
public class HelloController {

    @PostMapping("/")
    public HelloDto test(@RequestBody HelloDto helloDto) {
        log.info(helloDto.getId() + " " + helloDto.getName());
        return helloDto;
    }
}
```

Controller 코드를 위와 같이 바꾼 후에 테스트 코드를 작성해보겠습니다. 

<br>

```java
import com.example.demo.controller.HelloController;
import com.example.demo.dto.HelloDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void RequestBodyTest() throws Exception {
        String content = objectMapper.writeValueAsString(new HelloDto(1L, "Gyunny"));

        mvc
                .perform(post("/")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(content));
    }
}
```

뭔가 막 복잡해보이지만, Controller Post API를 테스트 하는 코드입니다. 즉, API에 요청을 보냈을 때 내가 예측하는 결과와 같게 잘 나오는지를? 테스트 하는 것입니다. 지금은 코드를 전부 이해하려고 하지 않아도 괜찮습니다. (스터디 할 때 좀 더 설명할 예정입니다.)

<br>

![스크린샷 2021-04-30 오전 11 43 05](https://user-images.githubusercontent.com/45676906/116642251-57a60f80-a9a9-11eb-881c-3482602d81d0.png)

어.. 왜 `@Getter`만 추가했는데 이번엔 되는거지? 하고 의문을 가질 수 있습니다. 이것에 대해 알고 싶다면 [여기](https://jojoldu.tistory.com/407) 를 한번 꼭 읽어보시는 것을 추천합니다.

이유를 간단히 말하면 `@RequestBody`의 데이터가 JSON으로 오면 `Jackson2HttpMessageConverter`가 실행이 되는데 내부적으로 `ObjectMapper`를 사용해서 Dto와 매핑을 시켜주는 것입니다.

지금은 `ObjectMapper`는 뭐고.. `Jackson2HttpMessageConverter`는 뭐고.. 머리가 복잡할텐데요. 일단은 그냥 이렇게 사용할 것이고 Spring에서 편리하게 많은 것을 제공해주는 구나 정도를 느끼시면 좋을 거 같습니다. 

`@RequestBody`는 `HttpMessageConverter`에 대해서도 공부를 해야 합니다. `HttpMessageConverter` 아래에 `Jackson2HttpMessageConverter`가 존재하는 것인데.. 뭐 하여간 이 내용은 중요하기 때문에 따로 글을 빼서 설명할 것입니다. 

<br>

## `RequestHeader`

JWT를 사용할 때 Token을 Header로 받아 온 경험들이 있으실텐데요. 이 토큰을 꺼내기 위해서 `RequestHeader`를 사용합니다. 

```java
@RestController
public class HelloController {

    @@GetMapping("/")
    public String test(@RequestHeader("token") String token) {
        return "test";
    }
}
```

위와 같이 사용하면 Header의 정보를 꺼내올 수 있습니다.

<br>

## `RequestPart`

```java
@RestController
public class HelloController {

    @PostMapping("/")
    public String test(@RequestPart(value = "profileImg") MultipartFile multipartFile) {
        return "test";
    }
}
```

사진 업로드를 할 때 사용하는 어노테이션이 `@RequestPart` 입니다. 타입으로는 `MultipartFile` 이라는 것이 존재하는데요. 이것은 나중에 S3로 파일 업로드 하는 시간에 자세히 다루어보겠습니다. 

지금 당장 사용할 것은 아니니 이런 것이 있다~ 정도만 알면 좋을 거 같습니다.

<br>

이번 글에서는 `Spring MVC 어노테이션 간단한 사용법`에 대해서 알아보았습니다. 정말 간단하게 사용법만 알아보아서 어려운 내용은 아니었을 것이라 생각합니다. 하지만.. `@RequestBody`에서는 살짝? 낯선 개념들이 나왔는데요. 이것들은 다른 글에서 같이 볼 것이니 그 때 개념을 좀 더 익히고
지금의 글을 다시 읽어보아도 좋을 거 같습니다!