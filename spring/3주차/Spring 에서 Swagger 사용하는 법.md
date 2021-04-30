# `Spring에서 Swagger 사용하는 법`

Swagger는 간단한 설정으로 프로젝트에서 지정한 URL들을 HTML화면으로 확인할 수 있게 해주는 프로젝트입니다. 저 같은 경우는 기존에 `Github wiki`로 매번 API 명세서를 작성했습니다. 하지만 Swagger를 이용하면 간단한 설정 하나만 하면
API 문서를 자동으로 만들어주기 때문에 상당히 편리하게 이용할 수 있습니다. 

<br>

### `pom.xml`

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

<br>

### `build.gradle`

```
compile group: 'io.springfox', name: 'springfox-swagger2', version: '2.5.0'
compile group: 'io.springfox', name: 'springfox-swagger-ui', version: '2.5.0'
```

먼저 `pom.xml` or `build.gradle`에 위의 의존성을 추가해주겠습니다. 

<br>

![스크린샷 2021-04-30 오후 4 49 20](https://user-images.githubusercontent.com/45676906/116665026-327ac680-a9d4-11eb-87fa-4fe063a76849.png)

위와 같이 의존성을 추가 한 후에 `새로고침` 버튼을 눌러야 추가한 의존성이 반영이 됩니다. 

<br>

## `Swagger 설정하기`

![스크린샷 2021-04-30 오후 4 52 08](https://user-images.githubusercontent.com/45676906/116665228-7968bc00-a9d4-11eb-93e6-ddc030980d76.png)

위와 같이 `config/SwaggerConfiguration` 클래스를 하나 만들겠습니다. 


```java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()      
                .apis(RequestHandlerSelectors.any())   // 현재 RequestMapping으로 할당된 모든 URL 리스트 추출
                .paths(PathSelectors.any())            // PathSelectorys.any("/api/**")) 와 같이 /api/** 인 URL로만 필터링 할 수 있습니다.
                .build();                              
    }
}
```

그리고 위와 같이 작성하겠습니다. 가장 먼저 `@Configuration`이 보이는데요. 이것은 2주차 때 간단하게 언급을 했습니다. 무엇일까요? 바로 `Spring 설정 파일`을 명시하는 어노테이션 입니다.(`SwaggerConfiguration`도 Bean으로 등록됩니다.)

- `@EnableSwagger2`: Swagger를 사용할 수 있게 활설화 시켜주는 어노테이션입니다. 
- `@Bean`: 이 어노테이션이 어떤 것이었죠? 바로.. ? `Bean`으로 등록하는 어노테이션 입니다. 즉, `Docket`을 Bean으로 등록시켜 줍니다. 


이게 Swagger 설정이 다 입니다. 매우 쉽지 않나요? 그리고 아래와 같이 간단하게 `Controller`를 만든 후에 실행시켜 보겠습니다. 

```java
@RestController
public class HelloController {

    @PostMapping("/")
    public HelloDto test(@RequestBody HelloDto helloDto) {
        System.out.println(helloDto);
        return helloDto;
    }
    
    @GetMapping("/gyun")
    public String gyun() {
        return "Gyunny";
    }
}
```

```
http://localhost:8080/swagger-ui.html
```

그리고 위의 경로로 들어가보겠습니다. 

<img width="1469" alt="스크린샷 2021-04-30 오후 5 09 13" src="https://user-images.githubusercontent.com/45676906/116667224-e7ae7e00-a9d6-11eb-91c7-294e36b15156.png">

그러면 위와 같이 자동으로 `API 문서`가 만들어진 것을 볼 수 있습니다. NodeJS는 직접 한땀한땀 설정들을 해주어야 하는데.. 스프링은 정말 대단한 거 같습니다. 

Swagger는 이렇게 API 문서를 자동화 시켜주는 것도 정말 좋지만, PostMan의 기능처럼 API 테스트도 직접 해볼 수 있습니다. 

<br>

## `Swagger API 호출하기`

![스크린샷 2021-04-30 오후 5 14 23](https://user-images.githubusercontent.com/45676906/116667890-a79bcb00-a9d7-11eb-98a0-b054b35eef24.png)

`http://localhost:8080`은 HTTP POST 형태의 Request Body를 가지고 있는 API 입니다. 한번 테스트를 어떻게 하는지 알아보겠습니다. 

<br>

<img width="1417" alt="스크린샷 2021-04-30 오후 5 17 43" src="https://user-images.githubusercontent.com/45676906/116668283-2d1f7b00-a9d8-11eb-82ec-69a7a5b4b5e7.png">

`PostMan` 처럼 보내고자 하는 형식에 맞춰서 `Request Body`를 작성하고 `Execute`로 실행을 해보겠습니다. 

<br>


<img width="1411" alt="스크린샷 2021-04-30 오후 5 20 34" src="https://user-images.githubusercontent.com/45676906/116668599-88ea0400-a9d8-11eb-956c-6ddab8643e2c.png">

그러면 위와 같이 원하는 대로 응답이 잘 오는 것을 볼 수 있습니다. 어떠신가요? 저는 Swagger는 아주 편리한 도구 인거 같습니다!
