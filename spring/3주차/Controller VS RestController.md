# `스프링 MVC`

![MVC](https://user-images.githubusercontent.com/45676906/91659890-9f6c5280-eb0d-11ea-8831-2b2c2b3ee40d.png)

<br>

# `Controller` 

* MVC 중에 하나인 Controller이다. @Controller 어노테이션을 추가해주면 스프링에서 Controller라고 인식을 해줌
* Bean으로 등록됨



<br>

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    
    @GetMapping("")
    public String test(Model model) {
        model.addAttribute("data", "hi");
        return "hello";
    }
}
```

<br>

### HTML View 


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

<br>

### `Controller 내부 구조`

![Controller](https://user-images.githubusercontent.com/45676906/91659934-de9aa380-eb0d-11ea-88aa-98b9bc2ad5f2.png)

* 요청이 오면 DispatchServlet에 의해 요청 URL이 존재한다면 Controller에게 전달한다. 
* Controller는 내부적으로 viewResolver가 존재하기 때문에 return 값이 view 파일 이름이다. (ex: hello.html)


<br>

### `RestController 구조`

![RestController](https://user-images.githubusercontent.com/45676906/91660011-641e5380-eb0e-11ea-8ca9-a39b513451e0.png)

* RestController = Controller + ResponseBody이다.
* RestController는 내부적으로 HttpMessageConverter가 존재하기 때문에 return 타입에 따라 변환을 해준다.
* ex) return 타입이 Object라면 JsonConverter가 실행 되고, return 타입이 String 이라면 StringConverter가 실행된다.



