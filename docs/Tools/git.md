# git 관련 설명




## 계정별 git 분리 작업 설정

* 기본은 회사 작업을 기본으로 사용하고 개인 작업이 필요한 경우 별도 조건으로 설정을 오버라이딩 한다. 

1. ~/ 폴더에 git 개인 설정을 만든다. 
```shell
vi .gitconfig-personal

[user]
  email = your@gmail.com
  name = yourgituser
  signingkey = yourkeyXXXXXXXXXXX
  
```
2. ~/.gitconfig 설정의 [user] 설정 밑에 개인 설정을 추가 한다.
```shell
[includeIf "gitdir:~/Study/"]
  path = .gitconfig-personal


```

* 주의 사항 별도 계정 으로 하는 것은  특정 폴더에 몰아 넣고 설정 하는 것이 좋다. 
* 난 공부를 목적으로 했기 때문에 Study 폴더를 사용 하였다. 

[Git 계정 여러 개 동시 사용하기](https://blog.outsider.ne.kr/1448)  


