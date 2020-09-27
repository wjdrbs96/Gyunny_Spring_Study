## Spring 에서 JSON 파싱

```
<dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1</version>
</dependency>
```

`pom.xml`에 위의 의존성을 추가하자.

<br>

## JSON 예시

```json
{
    "data": [
        {
            "authors": "윤홍균",
            "title": "자존감 수업",
            "thumbnail": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1637676%3Ftimestamp%3D20200915162113"
        },
        {
            "authors": "이정호",
            "title": "자존감 수업(어린이를 위한)(반양장)",
            "thumbnail": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F3938006%3Ftimestamp%3D20200915164007"
        },
        {
            "authors": "권영애",
            "title": "자존감, 효능감을 만드는 버츄프로젝트 수업",
            "thumbnail": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F909838%3Ftimestamp%3D20200915155608"
        }
    ]
}
```

위와 같은 `JSON`형식을 `Spring에서 parse`해보자.

<br>

### Java 코드로 JSON 파싱

```java
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
    public static void main(String[] args) throws ParseException {

        String jsonData = "{\"Persons\":[{\"name\":\"고경태\",\"age\":\"30\",\"블로그\":\"ktko.tistory.com\",\"gender\":\"남자\"}, {\"name\":\"이홍준\",\"age\":\"31\",\"블로그\":\"없음\",\"gender\":\"남자\"}, {\"name\":\"서정윤\",\"age\":\"30\",\"블로그\":\"없음\",\"gender\":\"여자\"}], \"Books\":[{\"name\":\"javascript의모든것\",\"price\":\"10000\"},{\"name\":\"java의모든것\",\"price\":\"15000\"}]}";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);

        JSONArray jsonArray = (JSONArray)jsonObject.get("Persons");

        for (int i = 0; i < jsonArray.size(); ++i) {
            System.out.println("======== person : " + i + " ========");
            JSONObject bookObject = (JSONObject) jsonArray.get(i);
            System.out.println(bookObject.get("name"));
            System.out.println(bookObject.get("age"));

        }
    }
}
```