# Travis CI와 AWS S3 연동하기

S3란 AWS에서 제공하는 `일종의 파일 서버`이다. 이미지 파일을 비롯한 정적 파일들을 관리하거나 지금 진행하는 것처럼 배포 파일들을 관리하는 등의 기능을 지원한다.
S3를 비롯한 AWS 서비스와 `Travis CI`를 연동하게 되면 전체 구조는 다음과 같다.

<img src="https://user-images.githubusercontent.com/45676906/95607939-6a9dd480-0a97-11eb-8d68-55000fda8654.png">

<br>

첫 번째 단계로 `Travis CI`와 `S3`를 연동해보자. 실제 배포는 `AWS CodeDeploy`라는 서비스를 이용한다. 하지만, S3 연동이 먼저 필요한 이유는 `Jar 파일을 전달하기 위해서이다.`

<br>

`CodeDeploy`는 저장 기능이 없다. 그래서 `Travis CI`가 빌드한 결과물을 받아서 `CodeDeploy`가 가져갈 수 있도록 보관할 수 있는 공간이 필요하다. 보통은 이럴 때 `AWS S3`를 이용한다.

<br>

## AWS Key 발급

일반적으로 AWS 서비스에 `외부 서비스가 접근할 수 없다.` 그러므로 `접근 가능한 권한을 가진 Key`를 생성해서 사용해야 한다.
AWS에서는 이러한 인증과 관련된 기능을 제공하는 서비스로 `IAM(Identity and Access Management)`이 있다.  
(생성하는 방법은 생략)

AWS에서 `엑세스 키 ID, 비밀 액세스 키`를 발급 받았을 것이다. 이제 그 키를 `Travis CI`에 등록해보자.
먼저 [Travis CI 설정화면](https://travis-ci.org)으로 이동하자.  


<img src="https://user-images.githubusercontent.com/45676906/95608946-df254300-0a98-11eb-8066-e9b79908b96f.png">

<br>

설정 화면을 들어가서 보면 아래와 같이 `Environment Variables` 항목이 있다. 

<img src="https://user-images.githubusercontent.com/45676906/95609242-607cd580-0a99-11eb-9030-7ab25ce19c41.png">

<br>

여기서 `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`를 변수로 해서 `IAM` 사용자에서 발급받은 키 값들을 등록하자.

- `AWS_ACCESS_KEY` : 액세스 키 ID
- `AWS_SECRET_KEY` : 비밀 엑세스 키

여기에 등록된 값들은 이제 `.travis.yml`에서 `$AWS_ACCESS_KEY, $AWS_SECRET_KEY`란 이름으로 사용할 수 있다. 

<br>

## S3 버킷 생성

- 생략

<br>

## .travis.yml 추가

`.travis.yml`에 Travis CI에서 만든 Jar 파일을 S3에 올릴 수 있도록 아래의 코드를 추가하자.

```yaml
language: java
jdk:
  - openjdk8

branches:
  only:
    - master

cache:
  directories:
    - '$HOME/.m2/repository'
    - $HOME/.gradle'

script: "./gradlew clean build"


before_deploy:
  - zip -r SpringBoot_WebService * # 본인 레포지토리 이름
  - mkdir -p deploy
  - mv SpringBoot_WebService.zip deploy/SpringBoot_WebService.zip

deploy:
  -provider: s3
  access_key_id: $AWS_ACCESS_KEY      # 엑세시 키 ID
  secret_access_key: $AWS_SECRET_KEY  # 비밀 키
  bucket: sopt-26th  # 버킷 이름
  region: ap-northeast-2
  skip_cleanup: true
  acl: private  # zip 파일 접근을 private으로
  local_dir: deploy
  wait-until-deployed: true


# CI 실행 완료시 메일로 알람
notifications:
  email:
    recipients:
      - wjdrbs966@naver.com
```

<br>

### `1. before_deploy`

- deploy 명령어가 실행되기 전에 수행된다.
- CodeDeploy는 `Jar 파일은 인식하지 못하므로` Jar+기타 설정 파일들을 모아 압축(zip)한다. 

### `2. zip -r 레포지토리 이름`

- 현재 위치의 모든 파일을 `레포지토리 이름`으로 압축(zip)한다.

### `mkdir -p deploy`

- deploy라는 디렉토리를 Travis CI가 실행 중인 위치에서 생성한다.

### mv 레포지토리이름.zip deploy/레포지토리이름.zip

- 레포지토리이름.zip 파일을 deploy/레포지토리이름.zip으로 이동시킨다. 

### `deploy`

- S3로 파일 업로드 혹은 `CodeDeploy`로 배포 등 `외부 서비스와 연동될 행위들을 선언`한다.

### `local_dir:deploy`

- 앞에서 생성한 deploy 디렉토리를 지정한다.
- `해당 위치의 파일들만` S3로 전송한다. 

<br>

설정이 다 되었으면 `Github로 PUSH`하자. `Travis CI`가 자동으로 빌드가 진행되는 것을 확인하고, 모든 빌드가 성공하는지 확인한다.

<img src="https://user-images.githubusercontent.com/45676906/95611813-51982200-0a9d-11eb-8865-2b9c82dc4e68.png">

<br>


위와 같이 `AWS S3 버킷`을 확인해보면 `zip`이 잘 올라간 것을 확인할 수 있다.
