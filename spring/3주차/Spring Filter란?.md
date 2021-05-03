# `들어가기 전에`

![1](https://user-images.githubusercontent.com/45676906/116671110-86d57480-a9db-11eb-98e8-00e944812410.png)

클라이언트가 Request를 보내면 Controller로 오기 전에 만나는 여러 관문들이 존재합니다. [DispatcherServlet](https://github.com/wjdrbs96/Gyunny_Spring_Study/blob/master/spring/3%EC%A3%BC%EC%B0%A8/DispatcherServlet%20%EC%9D%B4%EB%9E%80%3F.md) 에 대해서는 저번 글에서 정리했었습니다. 
그리고 `Interceptor`, `AOP`가 존재하는데요..?  `Interceptor`는 다른 글에서 정리할까 합니다. AOP는.. 어떤 것이었죠? Spring 삼각형 중에 하나였습니다. 아주 중요한 내용 중에 하나이지만 이번 글에서는 `Filter`에 대해서 알아볼 것입니다.

<br>

# `Spring Filter란 무엇일까?`

```java
package javax.servlet;

import java.io.IOException;

public interface Filter {
    public default void init(FilterConfig filterConfig) throws ServletException {}
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}
}
```

`Filter` 인터페이스가 존재하는데요. 인터페이스 내부에는 `init()`, `doFilter()`, `destroy()` 메소드가 존재합니다. 이름에서도 바로 알 수 있듯이 `초기회`, `Filter 설정..?`, `종료` 뭐 이런 메소드들이라고 추측이 됩니다. 

