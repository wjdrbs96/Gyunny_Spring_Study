# `Lombok 알아보기`

- ### `Maven`

```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```


- ### `gradle`

```
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```

<br>

## `Lombok은 무엇이고 왜 쓰는 것일까?`

Java의 코드를 보면 일반적으로 아래와 같이 변수는 `private`를 선언하고 `getter/setter`를 사용하게 됩니다. 

```java
public class Login {

    private String id;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

위와 코드를 보면 Getter/Setter를 직접 작성하고 있습니다. 그런데 만약에 필드가 20개 30개라면 클래스의 길이가 엄청 길어질 것입니다. 
또한 필드가 수정되면 메소드를 다시 수정해야 한다는 단점이 존재합니다.  

이러한 단점을 해결하기 위해서 나온 것이 `lombok` 입니다. Lombok은 자바 라이브러리이고, `@Data, @Getter, @Setter, @Builder` 등등의 다양한 어노테이션을 제공합니다. 이번글에서는 어노테이션 의미를 간단하게 정리해보겠습니다.

<br>

## `Data란?`

![스크린샷 2021-04-29 오후 2 36 23](https://user-images.githubusercontent.com/45676906/116506526-6b456d80-a8f8-11eb-80c6-c0e031880935.png)

`Data` 어노테이션을 사용하면 위와 같이 `getter`, `setter`, `hashCode`, `equals`, `toString`, `AllArgusConstruct` 들을 만들어주는 것을 볼 수 있습니다. `@Data` 어노테이션은 거의 대부분의 필요한 메소드를 다 만들어주고 있습니다. 

하지만? 저는 `getter`만 쓰고 싶은데 `@Data`를 사용하는 것이 좋을까요? 당연히 좋지 않을 것입니다. 굳이 만들 필요가 없는 메소드를 만들기 때문입니다. (다 필요하다면 @Data를 쓰는 것이 편리하겠지만..)

그래서 이러한 단점을 해결하기 위해서 각각의 어노테이션들도 존재합니다. 

<br>

## `getter/setter란?` 

![스크린샷 2021-04-29 오후 2 41 34](https://user-images.githubusercontent.com/45676906/116506860-16562700-a8f9-11eb-94b6-d29968a68739.png)

이건 이름에서는 직관적으로 알 수 있습니다. `@Getter`는 getter 메소드를 만들고, `@Setter`는 setter 메소드를 만들어줍니다. 

<br>

## `Equals, ToString, HashCode란?`

![스크린샷 2021-04-29 오후 2 43 49](https://user-images.githubusercontent.com/45676906/116507005-6b923880-a8f9-11eb-9810-e264ac14403a.png)

`@EqualsAndHashCode`, `@ToString` 이것 또한 이름으로 어떤 역할을 하는지 유추할 수 있습니다. 즉, `Equals`, `HashCode`, `toString` 메소드를 만들어주는 역할을 합니다.

<br>

<br>

## `NoArgsConstructor란?`

![스크린샷 2021-04-29 오후 2 50 23](https://user-images.githubusercontent.com/45676906/116507486-4fdb6200-a8fa-11eb-859a-bbf7bcfe5fdc.png)

대부분이 다 이름에서 어떤 것인지 파악할 수 있는데요. 이것은 `인자가 없는 생성자`를 만들어주는 어노테이션입니다. 즉, 기본 생성자를 만들어주는 것입니다. 

<br>

## `AllArgsConstructor란?`

![스크린샷 2021-04-29 오후 2 52 27](https://user-images.githubusercontent.com/45676906/116507644-9e88fc00-a8fa-11eb-966b-77eaf13e0dc3.png)

이것은 `All`이 붙어 있기 때문에 모든 생성자가 다 들어 있는 생성자를 만들어주는 어노테이션입니다. 

<br>

## `RequireArgsConstruct란?`

![스크린샷 2021-04-29 오후 2 54 51](https://user-images.githubusercontent.com/45676906/116507764-e7d94b80-a8fa-11eb-8854-e3c0eae4a4e2.png)

이 어노테이션은 이름에서 유추하기가 살짝 애매?한 거 같습니다. 어떤 역할을 하는 어노테이션일까요? 일단 위와 같이 기존 코드에 추가해보겠습니다. 그리고 확인해보니 `기본 생성자`만 있고 딱히 추가된 것이 없습니다.
위의 상황에서는 기본생성자 자동 생성이기 때문에 `RequireArgsConstruct`는 아무 역할을 안하고 있습니다. 

<br>

![스크린샷 2021-04-29 오후 2 58 07](https://user-images.githubusercontent.com/45676906/116507999-61713980-a8fb-11eb-8e0f-fc15830090bc.png)

위와 같이 필드에 `final`을 추가해주니 생성자 형태가 바뀐 것을 볼 수 있습니다. 즉, `final이 붙어 있는 필드로 생성자`를 만들어주는 역할을 합니다. 

<br>

## `Slf4j란?`

![스크린샷 2021-04-29 오후 3 01 41](https://user-images.githubusercontent.com/45676906/116508277-ed836100-a8fb-11eb-9d14-b04509514f55.png)

`Slf4j`는 위와 같이 로그를 출력할 수 있도록 제공해주는데요. `log.info()`, `log.error()` 등등 존재합니다. 기존에 알고리즘 풀 때는 `System.out.println()`을 사용했겠지만, 실제 애플리케이션 개발에서는 절대 사용하면 안되는 메소드입니다.
즉, 로그를 찍을 때는 위와 같이 사용해야 합니다. 

<br>

## `Builder란?`

![스크린샷 2021-04-29 오후 3 06 44](https://user-images.githubusercontent.com/45676906/116508579-a47fdc80-a8fc-11eb-9868-688625125fbf.png)

Builder가 무엇인지 잘 모르겠다면 [여기](https://devlog-wjdrbs96.tistory.com/258?category=925183) 를 참고하시면 됩니다. 위와 같이 `@Builder` 어노테이션을 사용하면 Builder 관련된 메소드들을 만들어줍니다. 

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public HelloDto hello() {
        return HelloDto.builder()
                .amount(100)
                .name("Gyunny")
                .build();
    }
}
```

사용법은 위와 같이 사용할 수 있습니다. 

<br>

Lombok에서 제공해주는 다른 어노테이션들이 있을텐데요. 그건 필요할 때마다 찾아보면 될 것 같고, 일단은 이정도만 알아도 될 거 같아서 이정도만 정리해놓겠습니다.



