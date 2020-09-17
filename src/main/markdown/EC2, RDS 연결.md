## Spring Boot AWS EC2, RDS 사용하기

```
AWS에서 EC2, RDS 설정하는 것은 생략
```

자세한 내용은 [여기](https://ooeunz.tistory.com/35?category=816210) 에서 확인하자

<br>

### EC2 접속하기


```
1. AWS에서 발급 받은 pem 파일이 있는 곳으로 이동한다.

2. chmod 400 파일명.pem

3. sudo ssh -i '{key file name}' {접속하고자하는 user}@{ip address}
ex) sudo ssh -i 'AWS.pem' ubuntu@52.79.90.119
```


<img src="https://user-images.githubusercontent.com/45676906/93416524-ecf40800-f8e0-11ea-8764-eba8cbaaafd2.png">

<br> <br>

위에는 `EC2`에 접속한 모습이다. 

<br>


### RDS 사용하기

자세한 내용은 [여기](https://ooeunz.tistory.com/36?category=816210) 에서 확인하자

<br>

### application.properties

```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://본인엔드포인트:3306/mybatis?useUnicode=yes&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Seoul
spring.datasource.username=마스터사용자이름 (AWS 에서 설정했을 것임)
spring.datasource.password=본인이 설정한 비밀번호 (AWS 에서 설정했을 것임)
```

위의 코드를 `application.properties`에 추가하자. 

<br>

### Java 8 설치

```
sudo apt install openjdk-8-jdk
java -version(자바 버전 확인)
```

위에 명령어가 되지 않는다면 아래의 명령어로 설치해보자. (위에께 된다면 아래는 패스)

```
1. sudo-apt-get-install software-properties-common
2. sudo add-apt-repository ppa:webupd8team/java
3. sudo apt-get update
4. sudo apt-get install oracle-java8-instller
5. java -version (자바 버전확인)
```

위의 명령어를 치고나서 뜨는 화면은 `Yes, OK`를 하고 설치하면 된다.

<br>

### Maven 설치

```
1. sudo apt list maven
2. sudo apt-get install maven
3. mvn -v (메이븐 버전 확인)
```

<br>

### TimeZone 변경

```
1. sudo-dpkg-reconfigure tzdata
```

위의 명령어를 쳤을 때 뜨는 화면에서 `Asia`, `Seoul`을 체크하고 `Ok`를 누르자.

<br>

이제 기본 `EC2`, `RDS`세팅은 끝났다. 그럼 `Github Repository`를 `clone`받아보자. 

```
git clone 클론할 레포의 주소
```

<br>

### jar 파일 배포

```
1. cd 레포지토리 이름 
2. mvn package (현재 레포에 있는 의존성, 코드들을 jar파일에 압축해서 넣는다.)
3. cd target (target 디렉토리가 생겼을 것이다.)
4. java -jar jar파일이름.jar  
ex) java -jar demo-0.0.1-SNAPSHOT.jar 
```

<br>

### jar 파일 백그라운드 실행

```
nohup java -jar jar파일이름.jar &
ex) nohup java -jar demo-0.0.1-SNAPSHOT.jar &

명령어 : jobs (백그라운드 프로세스 확인)
백그라운드 프로세스 죽이기 : kill %jobsNumber
ex) kill %1 
```

<br>
 
### jar 파일 재배포 

```
1. cd 레포지토리이름
2. git pull origin master (github Repository pull 받기)
3. mvn clean
4. mvn package 
5. cd target
6. nohup java -jar jar파일이름.jar &
ex) nohup java -jar demo-0.0.1-SNAPSHOT.jar &
```
