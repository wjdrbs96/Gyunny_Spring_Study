#  `Travis CI`와 `AWS S3`, `CodeDeploy` 연동하기

<img width="1018" alt="스크린샷 2020-10-14 오전 1 28 35" src="https://user-images.githubusercontent.com/45676906/95891191-b31f0000-0dbf-11eb-9a80-6e3b64d8938b.png">

<br>

먼저 AWS에 접속한 후에 `IAM`을 찾아서 들어가자

<br>

<img width="1761" alt="스크린샷 2020-10-14 오전 1 28 48" src="https://user-images.githubusercontent.com/45676906/95891246-ca5ded80-0dbf-11eb-912d-b3bcd05047ff.png">

<br>

그리고 `역할`을 눌러 들어가자

<img width="1770" alt="스크린샷 2020-10-14 오전 1 28 57" src="https://user-images.githubusercontent.com/45676906/95891395-fc6f4f80-0dbf-11eb-82ff-5c0c6911ff17.png">

<br>

그리고 `역할 만들기`를 눌러서 진행하자.

<br>

<img width="1583" alt="스크린샷 2020-10-14 오전 1 29 05" src="https://user-images.githubusercontent.com/45676906/95891539-29236700-0dc0-11eb-9207-a4da3a11e33e.png">

<br>

EC2를 선택하고 다음을 누르자. 

<img width="1107" alt="스크린샷 2020-10-14 오전 1 29 37" src="https://user-images.githubusercontent.com/45676906/95891592-3f312780-0dc0-11eb-8c78-5fbbcb0c2e44.png">

<br>

그리고 `EC2RoleForA`라고 검색을 한 후에 위와 같이 체크하고 넘어가자.

<br>

<img width="1106" alt="스크린샷 2020-10-14 오전 1 30 32" src="https://user-images.githubusercontent.com/45676906/95891714-6c7dd580-0dc0-11eb-94ce-03d8d8688701.png">

<br>

위와 같이 `키` `값`에 원하는 이름을 정하자.

<br>

<img width="1106" alt="스크린샷 2020-10-14 오전 1 40 28" src="https://user-images.githubusercontent.com/45676906/95891827-90411b80-0dc0-11eb-9d3d-fe21c3cc2f1a.png">

<br>

![1](https://user-images.githubusercontent.com/45676906/95891876-a3ec8200-0dc0-11eb-8a70-5e7453bdddc2.png)

<br>

<img width="898" alt="스크린샷 2020-10-14 오전 1 41 25" src="https://user-images.githubusercontent.com/45676906/95891925-b6ff5200-0dc0-11eb-83d1-a96484facb67.png">

<br>

그리고 나서 `인스턴스`를 `재부팅` 해주면 된다. 이제 EC2에 접속을 하고 아래의 명령어를 입력해보자.

```
aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
```

그러면 아래와 같이 뜨는 것을 확인할 수 있을 것이다. 

```
download: s3://aws-codedeploy-ap-northeast-2/latest/install to ./install
```

그리고 아래의 명령어를 순차적으로 입력하자.

```
install 파일에 실행 권한이 없으니 실행 권한을 추가한다.
chmod +x ./install

install 파일로 설치를 진행한다.
sudo ./install auto

위의 명령어가 /usr/bin/env: ruby: No such file or directory 이게 뜨면서 안된다면
sudo apt-get install ruby;  설치를 진행하고 해보자.

설치가 끝났으면 Agent가 정상적으로 실행되고 있는지 상태 검사를 한다.
sudo service codedeploy-agent status

다음과 같이 running 메세지가 출력되면 정상이다.
The AWS CodeDeploy agent is running as PID xxx
```

<br>

## CodeDeploy를 위한 권한 생성

CodeDeploy에서 EC2에 접근하려면 마찬가지로 권한이 필요하다. AWS의 서비스이니 IAM 역할을 생성한다. 위에서 했던 거 처럼 IAM에 역할까지 들어가자.

<img width="1046" alt="스크린샷 2020-10-14 오전 2 19 38" src="https://user-images.githubusercontent.com/45676906/95895101-14959d80-0dc5-11eb-90c3-c3bbe97ce7e2.png">

<br>

<img width="1039" alt="스크린샷 2020-10-14 오전 2 19 44" src="https://user-images.githubusercontent.com/45676906/95895150-2f681200-0dc5-11eb-9453-2da738235fd9.png">

<br>

그리고 위에서 `CodeDeploy`를 선택하자.

<img width="1038" alt="스크린샷 2020-10-14 오전 2 19 57" src="https://user-images.githubusercontent.com/45676906/95895198-43137880-0dc5-11eb-9258-2360f9d5a446.png">

<br>

표시된 대로 선택해서 넘어가면 된다.


<img width="1035" alt="스크린샷 2020-10-14 오전 2 20 23" src="https://user-images.githubusercontent.com/45676906/95895205-4575d280-0dc5-11eb-97e8-f32183562494.png">

<br>

키, 값을 임의로 정해서 역할을 만들자.

<img width="1033" alt="스크린샷 2020-10-14 오전 2 20 39" src="https://user-images.githubusercontent.com/45676906/95895246-56264880-0dc5-11eb-9153-990a66bfa8eb.png">

<br>

## CodeDeploy 생성하기

