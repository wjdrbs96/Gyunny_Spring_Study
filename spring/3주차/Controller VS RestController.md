# `스프링 MVC`

![MVC](https://user-images.githubusercontent.com/45676906/91659890-9f6c5280-eb0d-11ea-8831-2b2c2b3ee40d.png)

MVC 패턴을 간단하게 말하면 위의 그림처럼 표현할 수 있습니다. 

- `Controller`: 요청 URL 경로 매핑
- `Model`: 비즈니스 로직 처리 및 디비 접근
- `View`: 사용자에게 보여줄 뷰를 관리

위의 정도?로 표현할 수 있을 거 같습니다. 복잡하게 말하면 Servlet, jsp 등등.. 얘기해서 정리할 것이 많지만 일단은 MVC는 이런 느낌이구나 정도로 가볍게 알아보겠습니다. 


<br>

# `Controller` 

- MVC 중에 하나인 Controller이 있습ㄴ디ㅏ. @Controller 어노테이션을 추가해주면 스프링에서 Controller라고 인식을 해줍니다. 
- Bean으로 등록됩니다. (Bean, IoC 컨테이너.. 무엇인지 기억나시죠??)


```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    
    @GetMapping("/")
    public String test(Model model) {
        model.addAttribute("data", "hi");
        return "hello";
    }
}
```

`HelloController`에 간단하게 위와 같이 작성해보겠습니다. 코드를 보니 `Model`, `Controller`가 여기에 존재합니다. model는 `key`, `value` 형태의 데이터를 담는 공간이라고 생각하면 편할 거 같습니다. 

<br>

### `HTML View `

![스크린샷 2021-04-30 오후 12 42 24](https://user-images.githubusercontent.com/45676906/116645860-a9529800-a9b1-11eb-857c-8a91dec4efa5.png)

Spring Project에서 View를 작성하는 폴더는 `resources` 아래에 작성합니다. (`Thymeleaf`를 쓰냐 `jsp`를 쓰냐에 따라 조금은 다르지만..) 

<br>

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<p th:text="'안녕하세요. ' + ${data}">안녕하세요. 손님</p>
</body>
</html>
```

```
안녕하세요. hi
```

그래서 위의 html을 보면 결과는 `Model`에 담아놓았던 데이터를 key로 꺼내면 data에 해당하는 것이 출력이 됩니다. 즉, 간단히 말하면 `Controller`에 요청이 들어오면 요청 받은 뷰의 필요한 데이터를 `Model`에 담아 view로 보내주는 역할을 하게 됩니다.  

<br> <br>

## `Controller 내부 구조`

![Controller](https://user-images.githubusercontent.com/45676906/91659934-de9aa380-eb0d-11ea-88aa-98b9bc2ad5f2.png)

- 요청이 오면 `DispatchServlet`에 의해 요청 URL이 존재한다면 Controller에게 전달합니다. (DispacherServlet은 또 뭐지..? 이것도 다른 글에서 따로 정리할 예정입니다.) 
- Controller는 내부적으로 `viewResolver`가 존재하기 때문에 return 값이 view 파일 이름입니다. 즉, 위의 코드에서 `hello`를 return 하고 있기 때문에 `hello.html` 파일을 찾게 됩니다. 

```java
@Controller
public class HelloController {

    @GetMapping("/")
    public String test() {
        return "Gyunny";
    }
}
```

그러면 위의 코드에서 `Gyunny`를 return 하고 있으니 `Gyunny.html` 파일을 찾을 것입니다. 없다면 어떤 것이 뜨게 될까요? 

![스크린샷 2021-04-30 오후 12 50 46](https://user-images.githubusercontent.com/45676906/116646337-bb810600-a9b2-11eb-82fc-ffef4969201e.png)

당연히 `404`가 뜰 것입니다. `Gyunny.html` 파일이 없으니 찾을 수 없다고 뜨는 것입니다. 

<br>


### `RestController 구조`

![RestController](https://user-images.githubusercontent.com/45676906/91660011-641e5380-eb0e-11ea-8ca9-a39b513451e0.png)

- `RestController` = `Controller` + `ResponseBody`입니다.
- `RestController`는 내부적으로 `HttpMessageConverter`가 사용되기 때문에 return 타입에 따라 변환을 해줍니다. 
- ex) return 타입이 Object라면 `JsonConverter`가 실행 되고, return 타입이 String 이라면 `StringConverter`가 실행된다.

<br>

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public String test() {
        return "Gyunny";
    }
}
```

위와 같이 return 타입이 `String`이면 `StringConverter`가 실행되게 됩니다. 

<img width="186" alt="스크린샷 2021-04-30 오후 4 25 36" src="https://user-images.githubusercontent.com/45676906/116662399-b3d05a00-a9d0-11eb-853b-58cc65ba46af.png">

<br>

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public HelloDto test() {
        HelloDto helloDto = new HelloDto(1L, "Gyunny");
        return helloDto;
    }
}
```

이번에는 `Object` 타입을 return 타입으로 놓겠습니다. 위의 API는 어떤 것을 반환할까요? 위에서 말했듯이 Object 타입이면 `JsonConverter`가 실행되는데요. 반환 결과도 JSON으로 나오게 됩니다.

<img width="344" alt="스크린샷 2021-04-30 오후 4 26 47" src="https://user-images.githubusercontent.com/45676906/116662548-dc585400-a9d0-11eb-8efa-59e28559d612.png">

<br>

<br>

## `HttpMessageConverter란?`

<img width="1011" alt="스크린샷 2021-04-30 오후 4 29 01" src="https://user-images.githubusercontent.com/45676906/116662903-55f04200-a9d1-11eb-9e7d-d05600a4abe8.png">

`HttpMessageConverter`는 위와 같이 다양하게 존재하는데요. 각 특징들은 읽어보면 알 수 있습니다. 

그러면.. 스프링은 도대체 상황에 따라 어떤 `HttpMessageConverter`를 사용할 지를 어떻게 결정할 수 있을까요? 

`응답(Response)`을 줄 때는 `반환 타입`에 따라서 결정이 된다고 위에서 말했습니다. 그러면 `요청(Request)`에서는 어떻게 알 수 있을까요? 바로 `Header`에 `Content-Type`을 보고 결정하게 됩니다. 

즉, `RequestBody`가 `application-json` 이라면 `Jackson2HttpMessageConverter`가 실행되고, 다른 형태면 그 형태가 Spring에서 지원한다면 그 형태에 맞는 `HttpMessageConverter`가 실행되게 됩니다.  




