# `SpEL(스프링 Expression Language)란?`

- `객체의 그래프를 조회하고 조작하는 기능을 제공합니다.`
- `스프링 3.0부터 지원합니다.`
- `여러 스프링 코드에서 쓰이고 있는 기능입니다.`

<br>

## `사용법`

- `#{"표현식"}`
- `${"프로퍼티"}`
- `표현식은 프로퍼티를 가질 수 있지만 반대는 안됨`
    - `#{${my.data} + 1}`
    
    
<br>

## `코드 예제`

```java
@Component
public class AppRunner implements ApplicationRunner {

    @Value("#{1 + 1}")
    int value;

    @Value("#{'Gyunny ' + 'Spring'}")
    String string;

    @Value("#{1 eq 1}")
    boolean isBool;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(value);    // 2
        System.out.println(string);   // Gyunny Spring
        System.out.println(isBool);   // true
    }
}
```

`표현식`을 사용할 때는 위와 같이 사용할 수 있습니다. 

<br>

### `properties 속성 참고하기`

`application.properties` 파일에다가 `my.data = 100`이라고 적어보겠습니다. 

```java
@Component
public class AppRunner implements ApplicationRunner {

    // properties 참조하기
    @Value("${my.value}")
    int myValue;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(myValue);   // 100
    }
}
```

그리고 `$`를 이용해서 위와 같이 값을 꺼내올 수 있습니다. 

<br>

## `Bean의 값 참조하기`

```java
@Component
public class Sample {
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
```

위와 같이 Bean으로 등록된 클래스의 필드를 참조할 수도 있습니다. 

```java
@Component
public class AppRunner implements ApplicationRunner {

    // Bean의 정보 참조하기
    @Value("#{sample.data}")
    int myValue;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(myValue);   // 100
    }
}
```

