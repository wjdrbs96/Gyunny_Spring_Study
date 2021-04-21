## 스프링 시큐리티(Spring Security)



```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
``` 

먼저 `Maven`프로젝트에서 `pom.xml`에 아래의 코드를 작성하자. 그리고 `thymeleaf`도 사용할 것이기 때문에 위의 의존성을 추가해주자. 

<br>

### 1. `thymeleaf` 적용

`resources/templates` 폴더 아래에 `index.html`, `hello.hmtl`, `my.html`을 만들어보자.

### index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/hello">hello</a>
<a href="/my">my</a>
</body>
</html>
```

### hello.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>hello</h1>
</body>
</html>
```

### my.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>hello</h1>
</body>
</html>
```

위와 같이 간단한 html 파일을 작성해보자.

<br>

### 2. `Controller` 만들기

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
```

그리고 나서 `Controller`를 실행하면 원래 생각했던 대로 정상적으로 잘 실행될 것이다.

<br>

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

그리고 이번에는 `pom.xml`에 `security` 의존성을 추가해주자. 그리고 나서 위의 코드를 실행하면 로그화면에서 아래의 로그를 보게 될 것이다.

<br>

```
Using generated security password: b04dd7bb-017d-4df5-ba1c-24a4cc75f9a3
```

로그에 뜨는 패스워드는 아래서 사용할 것이니 아래의 내용을 참고해서 같이 보면 된다.
그리고 `http://localhost:8080`으로 요청하면 아래와 같은 `로그인`화면도 보게될 것이다. 

<img src="https://user-images.githubusercontent.com/45676906/94171331-19ed7f80-fecc-11ea-8c4d-c4fbad9ac6ad.png">

<br> 

<br>

### 로그인 화면을 만든적이 없는데 왜 뜨는 것일까??

일단 `Spring security` 의존성을 추가하면 `Spring Boot`에서 제공해주는 자동 설정들이 있다. 
모든 경로가 인증을 필요로 하게 된다. HTTP `accept header`에 따라 `Authenticate` 차이가 있는데 기본적으로 `text/html`을 담아서 보내기 때문에
이럴 때는 `Spring Security`에서 제공하는 `login` 화면으로 `redirect` 하는 것이다.

<br>

그래서 위의 `로그인` 화면을 로그인해서 `인증`을 해야 우리가 작성했던 `html`화면을 볼 수 있다. 
그래서 `UserName`은 `user`로 제공되고 `password`는 위에 적혀있고 실행하면 로그에 뜨는 패스워드 값을 입력하면 된다.


<br>

### WebSecurityConfigurerAdapter 클래스

`WebSecurityConfigurerAdapter` 클래스는 `Spring Security`의 기본설정을 할 때 사용하는 중요한 클래스이다.

```java
public abstract class WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {
    protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
	}
}
```

`WebSecurityConfigurerAdapter` 클래스 내부에 있는 `configure` 메소드의 있는 설정이 `Spring Boot`에서 기본으로 제공해주는 기능이다.
간단히 말하면 모든 요청에 대해 인증을 요구한다는 설정이다. 따라서 `Spring Security` 의존성을 추가했을 때 `로그인 화면`으로 redirect or `401`에러가 발생하는 것이다.


<br>

### Security config 설정하기

`config` 패키지 안에 `WebSecurityConfig` 클래스를 하나 만들어주자.

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/hello").permitAll()
                .anyRequest().authenticated()
                .and()
             .formLogin()
                .and()
             .httpBasic();

    }
}
``` 

그리고 `WebSecurityConfigurerAdapter` 스프링 시큐리티에서 중요한 클래스를 상속받아 `Spring Boot`에서 자동설정으로 제공해주는 것이 아니라
우리가 필요한대로 커스텀해서 사용하면 위와 같이 사용할 수 있다. <br>

위의 코드를 보면 `/`, `/hello`의 경로는 `인증`요청을 하지 않고 `formLogin()`, `httpBasic()`의 설정만 해준 것이다. 따라서
실행한 후에 `/`, `/hello`로 요청하면 바로 `html`화면이 잘 뜰 것이다. 




 