# `@ConfigurationProperties란 무엇일까?`

`@ConfigurationProperties`의 프로퍼티를 사용하여 다양한 형의 프로퍼티 값을 매핑할 수 있습니다. 이번 글에서는 어떻게 사용하는지에 대해서 간단하게 알아보겠습니다. 

<br>

![스크린샷 2021-05-28 오후 2 35 41](https://user-images.githubusercontent.com/45676906/119935185-05650800-bfc2-11eb-8388-0a24e4338e9a.png)

기존에는 위와 같이 `@Value()`를 사용하여 `application.yml or application.properties`의 값을 읽어서 사용했을 것인데요. 하지만 이렇게 사용했을 때 속성 파일의 경로와 맞지 않다면 에러가 발생합니다. 사람이라면 개발하다가 실수로 경로 매핑을 제대로 해주지 못하면 에러가 발생하는 것입니다.

이럴 때 `@ConfigurationProperties`를 사용하여 좀 더 쉽게 값을 읽어올 수 있습니다. 어떠한 방식으로 가능한지 알아보겠습니다. 

<br>

### `pom.xml`

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

<br>

### `build.gradle`

```
annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
```

위의 의존성을 먼저 추가하겠습니다. 

<br>

## `application.yml`

```yaml
fruit:
  list:
    - name: banana
      color: yellow
    - name: apple
      color: red
    - name: water melon
      color: green
```

application.yml을 위와 같이 작성한 후에 `@ConfigurationProperties`으로 읽어오는 테스트를 해보겠습니다. 

<br>

![스크린샷 2021-05-28 오후 3 01 12](https://user-images.githubusercontent.com/45676906/119937396-a43f3380-bfc5-11eb-8b78-166dea5bb6fc.png)

```java
@Setter
@Getter
@Component
@ConfigurationProperties("fruit")
public class FruitProperty {
    private List<Map> list;
}
```

위와 같이 yml 파일을 읽어올 수 있도록 코드를 작성했습니다. yml을 읽어오기 위해서는 위에 보이는 4개의 어노테이션이 모두 필요합니다. 
그리고 `ConfigurationProperties` 안에 `fruit` 이라고 지정했는데 이것은 yml 파일 안에 있는 읽고자 하는 이름입니다. 그리고 필드의 이름이 `list` 인 것을 볼 수 있습니다. 

이것도 yml 파일 내의 이름이랑 연관이 있습니다. 즉, `fruit.list`의 의미를 가지고 있고 fruit.list 아래에 있는 것들을 다 가져오는 것입니다. 

<br>

정말 잘 읽어오는지 테스트 코드를 통해서 확인해보겠습니다. 

<br>

## `테스트 코드`

```java
import com.example.demo.config.FruitProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * created by jg 2021/05/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyTest {

    @Autowired
    FruitProperty fruitProperty;

    @Test
    public void test() {
        List<Map> fruitData = fruitProperty.getList();

        assertThat(fruitData.get(0).get("name")).isEqualTo("banana");
        assertThat(fruitData.get(0).get("color")).isEqualTo("yellow");

        assertThat(fruitData.get(1).get("name")).isEqualTo("apple");
        assertThat(fruitData.get(1).get("color")).isEqualTo("red");

        assertThat(fruitData.get(2).get("name")).isEqualTo("water melon");
        assertThat(fruitData.get(2).get("color")).isEqualTo("green");

    }
}
```

![스크린샷 2021-05-28 오후 3 10 11](https://user-images.githubusercontent.com/45676906/119938109-db621480-bfc6-11eb-80c8-8cc9cf2aaf38.png)

<br>

위와 같이 테스트 코드를 작성한 후에 실행하면 테스트에 성공하는 것을 볼 수 있습니다.

