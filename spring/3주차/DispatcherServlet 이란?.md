# `DispatcherServlet 이란?`

`DispatcherServlet`이 무엇인지 바로 보기 전에 `Spring Boot` or `Spring` 프레임워크를 사용하지 않고 개발한다면 어떻게 할까요? 저 같은 경우는 학교에서 `데이터베이스 프로그래밍` 수업을 들을 때 프레임워크를 쓰지 않고
`Servlet`을 이용해서 MVC 형태로 개발을 했었습니다. (여러분도 경험이 있으실지도..?)

```java
public class HelloServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		super.service(arg0, arg1);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
```

간단하게 말하면 `HttpServlet` 클래스를 상속 받아 `doGet`, `doPost` 등등 메소드를 `Override` 해서 구현하였을 것입니다. 이런 것 말고도 `web.xml`에서도 설정을 해야하고.. 뭔가 복잡하게 설정을 했어야 했습니다. 

<br>

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FIVnVy%2FbtqF1SU0QR6%2FrXenY47NpRgzuXhvw61Md0%2Fimg.png)

위와 같이 요청 URI당 Servlet을 직접 만들어 작성을 해야 하는 번거로움이 있었습니다. 

이러한 번거로움과 단점?을 보완하기 위해 `DispatcherServlet`이 등장했습니다. 

![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FXgjhG%2FbtqF1AUAxyO%2FAkXutqJmN4lwxJfOiib8o1%2Fimg.png)

`DispatcherServlet`은 위와 같은 구조로 되어 있습니다. 즉, `DispatcherServlet`가 해당 URI 요청에 맞는 Controller를 찾아서 매핑을 시켜주는 역할을 합니다. 이러한 역할 때문에 기존에 하나하나 직접 Servlet으로 작성해주었던 것을 현재는 엄청나게 편리하게 사용할 수 있는 것입니다. 
(위 그림의 동작 원리는 중요하기 때문에 꼭 잘 봐두셔야 합니다.)

<br>

![3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVLuGa%2FbtqF0QD6bNs%2Fkfk40qPWLOYtWlZGRn3au0%2Fimg.png)

그림으로 보면 위와 같은 그림으로 볼 수도 있을 것 같습니다.

그리고 [Controller vs RestController](https://github.com/wjdrbs96/Gyunny_Spring_Study/blob/master/spring/3%EC%A3%BC%EC%B0%A8/Controller%20VS%20RestController.md) 차이를 저번 글에서 알아보았습니다. 
어떤 차이가 있었죠? `@Controller`는 `view` 파일 이름으로 return 하고, `@RestController`는 return 타입에 맞는 `HttpMessageConverter`가 실행이 된다고 했습니다.

<br>

## `Controller`

![4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb5T0Z2%2FbtqFZ5BtHO4%2FPKUsvSKATRMAGTv8FGQOS0%2Fimg.png)

`DispacherServlet` 까지 합친 그림은 위와 같습니다. 대략적으로 어떤 원리로 이루어진 어노테이션인지 조금이나마 감이 오시나요? 

<br>

## `RestController`

![5](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcwuGb3%2FbtqF1BzdDe9%2FF3sBKQQOz1l2ZqXEUcBfk1%2Fimg.png)

`RestController`는 `Controller` + `ResponseBody`이기 때문에 위와 같은 그림으로 표현할 수 있습니다. 

더 깊게 들어가면.. 더 많은 내용들이 있지만 간단하게 `DispatcherServlet`에 대해서 알아보는 글이었습니다. 간단하게라도 MVC에서 어떠한 형태로 돌아가는지는 알아야 하기 때문에 잘 알아두시면 좋을 거 같습니다.

<br>

# `참고하면 좋은 글`

- [https://mangkyu.tistory.com/18](https://mangkyu.tistory.com/18)