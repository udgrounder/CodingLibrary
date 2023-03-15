# Markdown 사용법



## MakDown 에서 그래프 그리기 (feat. intelliJ)

설치법
1. IntelliJ 에서 **Markdown** Plugin 설치 ( Press ⌘ , to open the IDE settings and select Plugins. )
2. **Markdown** plugin 설정에서 plentUML, Mermaid 플러그인 설치및 적용 (Press ⌘ , to open the IDE settings and select Languages & Frameworks | Markdown. )

사용법

```mermaid
graph LR
A(입력)-->B[연산]
B-->C(출력)
```


```plantuml
Alice->Bob: Hello Bob, how are you?
Note right of Bob: Bob thinks
Bob-->Alice: I am good thanks!
```


[참조 링크]

IntelliJ Page: <https://www.jetbrains.com/help/idea/markdown.html#diagrams>  
사용법: <https://richwind.co.kr/147>






## Sample



# 제목 1
## 제목 2
### 제목 3
#### 제목 4
##### 제ㅍ 5
###### 제목 6




이것은 *이텔렉* 이다.  
두껍게는 **두껍게** 하는거다.  
이건 ~~취소선이다.~~   ㄷ ㄷ ㄷ  
줄꿈은  스페이스 두번이다.

엔터 두번은 문단을  바꾸는 거다.

========
### 목록
1. 목록 1
1. 목록 2
1. 목록 3
    1. 하위 목록 1
    2. 하위 목록 2
    - 이건뭐?
    - 하위 목록
    1. 뭘까?
    2. 그렇구나


### 링크
[구글](https://www.google.com)  
네이버 : <https://www.naver.com>


[![대체텍스트(이게 대체 입니다.)](https://www.gstatic.com/webp/gallery/2.jpg "호버 설명 느낌표를 쓰세요.")](https://www.naver.com)


---

`코드 강조`

```html
<html>
   <br>
</html>
```


------

| 값 | 의미 | 기본값 |
|---|:---:|---:|
|`무우무우` | 몰라요 몰라 | 그게 뭐야?|
|`무우무우12312312` | 몰라요12312312312 몰라 | 그게 123123123뭐야?|



> 인용문입니다.
> > 그렇다고 합니다.
> 뭔지 몰라도요
> 그렇지










