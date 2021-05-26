# `들어가기 전에`

![스크린샷 2021-05-26 오후 12 57 40](https://user-images.githubusercontent.com/45676906/119600411-f47d9080-be21-11eb-8a82-3d9863d8e6ae.png)

Java에서 어떤 프레임워크를 사용하지 않고 데이터베이스를 연결한다면 `JDBC`를 사용했을 것입니다. 자바에서 위와 같이 `JDBC Driver Manager` 인터페이스를 두고 설계 했기 때문에 자신이 사용할 데이터베이스의 Driver만 연결시켜주면 되도록 편리하게 사용할 수 있습니다. 

<br>

![1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOvHi2%2FbtqDibLdLep%2FRLGbbmViRkyd4jkfDKkGw1%2Fimg.png)

다들 JDBC를 사용해본 적이 있다면 위와 같이 `Connection`, `Statement`, `ResultSet` 등등을 이용해서 했을 것입니다. 

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class Example1 {
    public static void main(String[] args) {
        String jdbc_driver = "com.mysql.cj.jdbc.Driver";
        String jdbc_url = "jdbc:mysql://localhost:3306/board?serverTimezone=UTC";
        try {
            Class.forName(jdbc_driver).newInstance();
            Connection con = DriverManager.getConnection(jdbc_url, "root", "root");
            Statement st = con.createStatement();

            String sql = "SELECT * FROM member";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString(1);
                String owner = rs.getString(2);
                String date = rs.getString(3);

                System.out.println(name + " " + owner + " " + date);
            }
            
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

간단한 코드의 예시를 들면 위와 같습니다. 매번 JDBC가 정해놓은 규칙대로 쿼리문을 실행해야 하는데요. 지금은 단순한 코드라서 그렇지 점점 복잡해진다면 여간 귀찮은 것이 아닐 것입니다. 
(중복된 코드를 반복적으로 사용해야 하고.. close()도 수동으로 해야 하고 등등 불편한 점이 많습니다.)

<br>

이러한 JDBC 프로그래밍의 복잡함이나 번거로움 없이 간단한 작업만으로 데이터베이스와 연동할 수 있도록 도와주는 것을 `Persistence Framework` 라고 합니다. 이것은 크게 `SQL Mapper`, `ORM`으로 나눌 수 있는데요.  

<br>

## `SQL Mapper`

- SQL을 직접 작성해야 합니다.
- SQL문의 결과와 객체의 필드를 매핑하여 데이터를 객체화시킵니다. 
- JDBC에서 해야하는 반복적인 작업들을 대신 해줍니다.
- 대표적으로 `MyBatis`가 존재합니다.

<br>

<br>

## `MyBatis`

`SQL Mapper`의 대표적인 프레임워크는 `MyBatis`가 있습니다. MyBatis를 사용하면 쿼리문은 XML에 따로 작성하고 JDBC에서 해야하는 복잡한 작업들을 알아서 해주기 때문에 생산성 높고 쉽게 데이터베이스를 연결해서 사용할 수 있도록 도와줍니다.
([MyBatis 사용하기](https://github.com/wjdrbs96/Gyunny_Spring_Study/blob/master/spring/4%EC%A3%BC%EC%B0%A8/MyBatis/MyBatis%20%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94%20%EB%B2%95.md))

![2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fkxm3y%2FbtqG9AmoINy%2FHJ1FY97boFffhwBkUjYQr0%2Fimg.jpg)

위의 그림은 MyBatis가 DataBase에 접근하는 순서를 나타낸 그림입니다. `보라색`으로 보이는 부분만 `개발자`가 작성하면 되고(Mapper 인터페이스 및, XML 쿼리 파일) 나머지는 MyBatis가 자동으로 해줍니다.

되게 복잡하고 많은 작업들을 MyBatis에서 해주고 있습니다. 간단하게 정리하면 아래와 같은데요.

<br>

### `1 ~ 3은 응용 프로그램 시작시 수행되는 프로세스입니다`

1. 응용 프로그램이 SqlSessionFactoryBuilder를 위해 SqlSessionFactory를 빌드하도록 요청합니다.

2. SqlSessionFactoryBuilder는 SqlSessionFactory를 생성하기 위한 MyBatis 구성 파일을 읽습니다.

3. SqlSessionFactoryBuilder는 MyBatis 구성 파일의 정의에 따라 SqlSessionFactory를 생성합니다.

<br>

### `4 ~ 10은 클라이언트의 각 요청에 대해 수행되는 프로세스입니다.`

4. 클라이언트가 응용 프로그램에 대한 프로세스를 요청합니다.

5. 응용 프로그램은 SqlSessionFactoryBuilder를 사용하여 빌드된 SqlSessionFactory에서 SqlSession을 가져옵니다.

6. SqlSessionFactory는 SqlSession을 생성하고 이를 애플리케이션에 반환합니다.

7. 응용 프로그램이 SqlSession에서 매퍼 인터페이스의 구현 개체를 가져옵니다.

8. 응용 프로그램이 매퍼 인터페이스 메서드를 호출합니다.

9. 매퍼 인터페이스의 구현 개체가 SqlSession 메서드를 호출하고 SQL 실행을 요청합니다.

10. SqlSession은 매핑 파일에서 실행할 SQL을 가져와 SQL을 실행합니다.

<br>

그냥 보기에는 눈에 들어오지 않은데.. MyBatis가 처음이니! 일단은 `Mapper` 인터페이스의 구현체를 만들어 자동으로 주입을 해준 후에 SQL을 실행해주는데 그 과정에서 많은 일들이 일어나는구나 정도만 일단 받아들여도 좋을 것 같습니다. 

<br> <br>

# `Reference`

- [https://khj93.tistory.com/entry/MyBatis-MyBatis%EB%9E%80-%EA%B0%9C%EB%85%90-%EB%B0%8F-%ED%95%B5%EC%8B%AC-%EC%A0%95%EB%A6%AC](https://khj93.tistory.com/entry/MyBatis-MyBatis%EB%9E%80-%EA%B0%9C%EB%85%90-%EB%B0%8F-%ED%95%B5%EC%8B%AC-%EC%A0%95%EB%A6%AC)
