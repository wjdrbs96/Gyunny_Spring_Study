# Travis CI 연동하기

Travis CI는 깃허브에서 제공하는 무료 CI 서비스이다. 젠킨스와 같은 CI 도구도 있지만, 젠킨스는 설치형이기 때문에 이를 위한 EC2 인스턴스가 하나 더 필요하다. 

<br>

## Travis CI 웹 서비스 설정

[https://travis-ci.org](https://travis-ci.org) 에서 깃허브 계정으로 로그인을 하자.

<img src="https://user-images.githubusercontent.com/45676906/95602495-f6136780-0a8f-11eb-9821-5df09a2fe530.png">

<br>

그리고 위에 보이는 것처럼 본인이 사용할 `Spring Repository`를 활성화 시키자. Travis CI 웹사이트에서 설정은 이것이 끝이다. 상세한 설정은 `프로젝트의 yml 파일로` 진행해야 하니 프로젝트로 가보자.


<br>

## 프로젝트 설정

Travis CI의 상세한 설정은 프로젝트에 존재하는 `.travis.yml` 파일로 할 수 있다. 여기서 잠깐 처음 보는 파일 확장자 .yml가 있다. 
yml 파일 확장자를 `YAML(야믈)`이라고 한다. 

<br>

`YAML`은 쉽게 말해서 `JSON에서 괄호를 제거한 것`이다. 그럼 이제 Travis CI 설정을 해보자. 프로젝트의 build.gradle과 같은 위치에 `.travis.yml`을 생성한 후에 아래의 코드를 추가하자.

```yaml
language: java
jdk:
  - openjdk8

branches:   # 1
  only:
    - master

cache:      # 2
  directories:
    - '$HOME/.m2/repository'
    - $HOME/.gradle'

script: "./gradlew clean build"   # 3


notifications:   # 4
  email:
    recipients:
      - wjdrbs966@naver.com  # 본인 이메일 주소

```

### `1. branches`

- Travis CI를 어느 브랜치가 푸시될 때 수행할지 지정한다. 
- 현재 옵션은 오직 master 브랜치에 push될 때만 수행한다. 
    
### `2. cache`

- 그레이들을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여, `같은 의존성은 다음 배포 때부터 다시 받지 않도록` 설정한다. 


### `3. script`

- master 브랜치에 푸시되었을 때 수행하는 명령어이다.
- 여기서는 프로젝트 내부에 둔 gradlew을 통해 clean & build를 수행한다.

### `4. notifications`

- Travis CI 실행 완료 시 자동으로 알람이 가도록 설정한다. 


<br>

그리고 나서, master 브랜치에 커밋과 푸시를 하고 다시 `Travis CI 저장소` 페이지를 확인하자. 

<img src="https://user-images.githubusercontent.com/45676906/95604862-201a5900-0a93-11eb-8414-9102115f2a22.png">

<br>

그러면 위와 같이 잘 되었고, Email로도 수신이 온 것을 확인할 수 있다. 

