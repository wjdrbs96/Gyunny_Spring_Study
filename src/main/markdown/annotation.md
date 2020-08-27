# Spring MVC

### @RequestMapping

* URL Controller의 메소드와 맵핑을 할 때 사용하는 스프링 프레임워크 어노테이션

```
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "spring")
public class TestController {

    @RequestMapping(value = "study", method = RequestMethod.GET)
    public String test() {
        return "test";
    }
}
```
<br>

#### String[] params
```
// /post?useYn=Y 일 경우 호출됨
@RequestMapping(value="/post", params="useYn=Y")

// not equal도 가능
@RequestMapping(value="/post", params="useYn!=Y")

// 값에 상관없이 파라미터에 useYn이 있을 경우 호출됨
@RequestMapping(value="/post", parmas="useYn")

// 파라미터에 useYn이 없어야 호출됨
@RequestMapping(value="/post", params="!useYn")
```


* http://localhost:8080/spring/study 로 접속하면 위의 메소드가 실행된다.
* 클래스 위의 RequestMapping은 공통경로를 지정하기 위해서 사용
* values => URL 경로 설정
* RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DEELETE)

<br>

### @GetMapping, @PostMapping, @DeleteMapping, @PutMapping

```
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
```

* GET, POST, PUT, DELETE 메소드 역할에 맞게 사용하여 URL 매핑하면 된다.

<br>

### @RequestParam

````
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("test")
    public String test(@RequestParam("name1") String name1,
                       @RequestParam("name2") String name2) {
        log.info(name1);
        log.info(name2);
        return "test";
    }
}
````

<br>

### Request 형식
```
http://localhost:8080/test?name1=lee&name2=choi
```

* 위와 같은 GET방식의 query string을 꺼내올 때 @RequestParam을 이용한다.


<br>

### @RequestBody 

````
import com.example.demo.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @PostMapping("test")
    public String test(@RequestBody User user) {
        log.info(user.getName());
        log.info(user.getPart());
        return "test";
    }
}
````

<br>

### Request Body
```
{
    "name" : "이름",
    "part" : "파트"
}
```

* User 클래스에 name, part 필드가 존재한다.
* @RequestBody는 json으로 요청이 왔을 때 객체에 맵핑을 해준다. (단, User에 setter가 꼭 있어야한다!)
* MessageConverter에 의해서 객체 맵핑이 된다. ([MessageConverter란?]())


<br>

### PathVariable 

```
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("test/{id}")
    public String test(@PathVariable(value = "id") int id) {
        log.info(String.valueOf(id));
        return "test";
    }
}
```

<br>

### Request 형식
```
http://localhost:8080/test/1
```

* params 값을 가져오는데 사용하는 어노테이션이다.
* {id}의 값과 파라미터 값이 같다면 value 는 사용하지 않아도 된다.
