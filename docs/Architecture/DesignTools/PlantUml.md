# PlantUML
[HOME] <https://plantuml.com/ko/>


### pluntUML Online server
https://www.plantuml.com/plantuml/uml/SoWkIImgAStDuNBAJrBGjLDmpCbCJbMmKiX8pSd9vt98pKi1IW80



### Docker
https://hub.docker.com/r/plantuml/plantuml-server


### MacOS IntelliJ 에서 PlandtUML 사용하기

설치법
1. IntelliJ 에서 **plentUML** Plugin 설치 ( Press ⌘ , to open the IDE settings and select Plugins. )



## 사용시 주의 사항


### Markdown 에서 문법 오류 발생
가끔 키워드에서 문제가 생기는 경우 가 있다.  
아래의 키워드의 경우에 plum 파일에서는 정상적으로 표시 되지만 markdown 에서는 오류가 발생하여 사용하기 어렵다

```
metaclass       metaclass
stereotype      stereotype
```
----
### IntelliJ 의 Markdown 에서 그래프 그릴때 오류가 발생 하는 경우
PluntUML 에서 그래프를 그리지 못하고 DOT 을 그릴수 없다며 아래와 같은 오류가 발생 하는 경우가 있다.
Mac 에서 Brew로 설치 할 때 dot 프로그램의 설치 위치가 달라서 발생 하는 오류이다.

```
Dot Executable: /opt/local/bin/dot File does not exist Cannot find Graphviz. You should try 
```

이런 경우 link 를 만들어 주면 된다.

```
sudo ln -s /opt/homebrew /opt/local
```

## 참고 사항

[참조]: https://devyonghee.github.io/2022/02/24/plantUML-install
