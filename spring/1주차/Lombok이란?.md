# `Lombok이란?`

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

## `Gradle (build.gradle)`

```
compileOnly 'org.projectlombok:lombok:1.18.10'
annotationProcessor 'org.projectlombok:lombok:1.18.10'
```


### `Maven (pom.xml)`

```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

자신이 사용하는 프로젝트에 맞게 의존성을 추가하겠습니다.(저희는 일단 Maven을 사용할 것이기 때문에 pom.xml에 맞는 것을 추가하겠습니다.)

<br>

### `Intellij plugin 설치하기`

그리고 intellij에서 lombok을 사용하기 위해 plugin을 설치해주어야 합니다.

- `Windows : file > Setting > Plugins > 'lombok' 입력  > INSTALL`
- `MacOS : Intellij IDEA > preferences > Plugins > 'lombok' 입력 > INSTALL`

![test](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdnHCXf%2FbtqHceLdXR3%2FB4kz8Mhe9bTeQKfhYHxh6k%2Fimg.png)

lombok 설치가 완료되었으면 Restart Intellij IDEA 버튼이 나오고, intellij를 restart 하겠습니다. 

- `Settings > Build > Compiler > Annotation Processors` 로 이동 후, Enable annotation processing을 체크하자.

<br>

![test2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb0dAcC%2FbtqHguNzIkm%2FOKxkGblzDiMiZDluzeXcm1%2Fimg.png)


이제 lombok에서 제공하는 몇가지 어노테이션에 대해 정리해보려 한다. 

<br>

## `1. @Data`

```java
import lombok.Data;

@Data
public class User {
    private int userIdx;
    private String name;
    private String part;
}
```

위와 같이 User 클래스에 `@Data`라는 어노테이션을 추가해주면 아래와 같이 메소드들이 생기는 것을 알 수 있습니다. 따라서 어노테이션 하나만으로 아래의 메소드를 사용할 수 있습니다. 
하지만 일반적으로 `@Data` 어노테이션은 현재 사용하지 않는 많은 메소드를 많들기 때문에 잘 사용하지 않습니다. 

![lom](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc2EvAW%2FbtqHgqKOENE%2FE5uM8YhOrK0nB2fJiMhWl0%2Fimg.png)

<br>


## `2. @NoArgsConstructor`

```java
import lombok.Data;

@Data
@NoArgsConstructor
public class User {
    private int userIdx;
    private String name;
    private String part;

    public User(int userIdx, String name, String part) {
        this.userIdx = userIdx;
        this.name = name;
        this.part = part;
    }
}
```

만약 위와 같이 매게변수가 있는 생성자가 존재하면 기본 생성자는 자동으로 제공해주지 않게 돕니다. 이럴 때 직접 기본 생성자를 만드는 것이 아니라 `@NoArgsConstructor` 어노테이션을 사용하여 기본 생성자를 만들 수 있습니다.

<br>

## `3. @AllArgsConstructor`

```java
@Data
@AllArgsConstructor
public class User {
    private int userIdx;
    private String name;
    private String part;
}
```

위의 코드처럼 직접 모든 필드가 들어간 생성자를 만들어 줄 필요 없이 `@AllArgsConstructor`를 사용하면 모든 필드가 들어간 생성자를 만들어줍니다.

<br>

## 4. `@RequiredArgsConstructor`

```java
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

@RequiredArgsConstructor
public class User {
    private final int userIdx;
    @NotNull
    private String name;
    private String part;
}
```

`@RequiredArgsConstructor`는 final이 붙은 필드와 @NotNull이 붙은 생성자를 만들어주는 역할을 합니다. 위의 예시라면 userIdx, name을 매게변수로 하는 생성자를 만들어 줄 것입니다 

<br> <br>

## `5. @Slf4j`

로그를 출력하기 위해서 알고리즘 풀 때는 System.out.println()을 사용했겠지만 실제 개발 환경에서는 절대 사용하면 안되는 메소드입니다. Spring에서는 @Slf4j나 다른 logger를 사용하여 로그를 출력합니다.

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        log.info("로그입니다");
        return "test";
    }
}
```

위와 같이 log.info()를 사용하여 로그를 출력한다. 


<br>

## `6. @Builder란?`

- [Builder 패턴이란?](https://devlog-wjdrbs96.tistory.com/258?category=925183)

