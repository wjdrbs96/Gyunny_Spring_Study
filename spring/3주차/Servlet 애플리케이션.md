# `서블릿(Servlet)`

- 자바 엔터프라이즈 에디션에서 제공하는 자바로 웹 애플리케이션을 개발할 수 있는 API 제공합니다.
- 요청 당 쓰레드 사용합니다.
- 그 중에 가장 중요한 클래스 중 하나가 `HttpServlet` 입니다.

<br>

## `서블릿(Servlet) 생명주기`

- 서블릿 컨테이너가 서블릿 인스턴스의 `init()` 메소드를 호출하여 초기화 합니다. 
- 서블릿이 초기화 된 다음부터 클라이언트의 요청을 처리할 수 있습니다. 각 요청은 별도의 쓰레드로 처리하고 이 때 서블릿 인스턴스의 service() 메소드를 호출합니다. 
  - 이 안에서 HTTP 요청을 받고 클라이언트로 보낼 HTTP 요청을 만듭니다
  - 보통 `doGet()`, `doPost()`를 구현합니다.   
- 서블릿 컨테이너 판단에 따라 해당 서블릿을 메모리에서 내려야 할 시점에 `destory()`를 호출합니다. 


```java
public class HelloServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
```
