# `Filter vs Interceptor 차이 간단 정리`

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile22.uf.tistory.com%2Fimage%2F9983FB455BB4E5D30C7E10)

저번에 `Filter`에 대해서 간단하게 알아본 적이 있었는데요. 이번에는 `Interceptor`에 대해서도 간단하게 알아보려고 합니다. 

<br>

## `Filter`

간단하게 말하면 Filter는 Spring 영역에 들어오기 전에 Filter 작업을 하는 역할을 합니다. 즉, `DispatcherServlet`이 실행되기 전에 앞단에서 이름 그대로 어떤 것들을 필터링 하는 역할을 할 수 있습니다.

- 인코등 작업
- XSS, CORS와 같이 보안 방어

예를들면 위와 같은 것들을 할 수 있습니다.

<br>

## `Interceptor`

인터셉터는 그림에서 볼 수 있듯이 `스프링 영역` 안에서 실행되는데요. 즉, `DispatcherServlet`이 컨트롤러를 호출하기 전, 후로 끼어들 수 있는 메소드를 제공합니다. 

```java
public interface HandlerInterceptor {

	default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}
	
	default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}
}
```

가령 `HandlerInterceptor` 내부 메소드를 보면 `preHandle`, `postHandle`, `afterCompletion`가 존재합니다. 

- `preHandler()`: Controller가 실행되기 전에 실행 됨
- `postHandle()`: Controller 메소드 실행 직 후에 실행 됨
- `afterompletion()`: view 페이지가 렌더링 되고 난 후에 실행

<br>

> 요청에 대한 작업 전/후로 가로챈다고 보면 된다.

> 필터는 스프링 컨텍스트 외부에 존재하여 스프링과 무관한 자원에 대해 동작한다.
> 하지만 인터셉터는 스프링의 DistpatcherServlet이 컨트롤러를 호출하기 전, 후로 끼어들기 때문에 스프링 컨텍스트(Context, 영역) 내부에서 Controller(Handler)에 관한 요청과 응답에 대해 처리한다.


<br>

# `Reference`

- [참고하면 좋은 글](https://goddaehee.tistory.com/154)


