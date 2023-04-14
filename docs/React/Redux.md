# Redux

## 설명

리덕스란 자바스크립트 상태 관리 라이브러리 이다. 
리덕스의 본질은 Node.js 모듈이다. 

### 상태관리 도구의 필요성
상태란?
React에서 State는 component 안에서 관리되는 것이다

그런데 Component 간의 정보(상태) 공유는 다음과 같은 문제가 있다. 
  * 자식 컴포넌트들 간의 다이렉트 데이터 전달은 불가능 하다.
  * 자식 컴포넌트들 간의 데이터를 주고 받을 때는 상태를 관리하는 부모 컴포넌트를 통해서 주고 받는다.
  * 그런데 자식이 많아진다면 상태 관리가 매우 복잡해진다.
  * 상태를 관리하는 상위 컴포넌트에서 계속 내려 받아야한다. => Props drilling 이슈

이러한 상태 관리의 복잡성을 해결해 주는 라이브러리의 사용해야 한다.
그러면 상태 관리 툴은 어떤 문제를 해결해 주나?
  * 전역 상태 저장소 제공
  * Props drilling 이슈 해결 
    * 예를 들어, A 라는 컴포넌트에 상태가 있고, I 라는 컴포넌트가 해당 상태를 사용한다고 하면,
     그 중간에 존재하는 C, G 등은 굳이 name이라는 상태가 필요하지 않음에도, 컴포넌트에 props를 만들어 자식 컴포넌트에 넘겨주어야 했다.
     이를 props drilling(프로퍼티 내려꽂기) 문제라고 부른다. 전역 상태 저장소가 있고, 어디서든 해당 저장소에 접근할 수 있다면 이러한 문제는 해결 된다.

상태 관리 툴 종류
  * React Context
  * Redux (다운로드가 제일 많다.)
  * MobX

### Redux 설명 

기본 개념 3가지 원칙
1. Single source of truth  
   동일한 데이터는 항상 같은 곳에서 가지고 온다.
   즉, 스토어라는 하나뿐인 데이터 공간이 있다는 의미이다.
2. State is read-only  
   리액트에서는 setState 메소드를 활용해야만 상태 변경이 가능하다.
   리덕스에서도 액션이라는 객체를 통해서만 상태를 변경할 수 있다.
3. Changes are made with pure functions  
   변경은 순수함수로만 가능하다.
   리듀서와 연관되는 개념이다.
   Store(스토어) – Action(액션) – Reducer(리듀서)

Store, Action, Reducer의 의미와 특징
* Store
  * Store(스토어)는 상태가 관리되는 오직 하나의 공간이다. 
  * 컴포넌트와는 별개로 스토어라는 공간이 있어서 그 스토어 안에 앱에서 필요한 상태를 담는다. 
  * 컴포넌트에서 상태 정보가 필요할 때 스토어에 접근한다.
* Action (액션)
  * Action(액션)은 앱에서 스토어에 운반할 데이터를 말한다. (주문서)
  * Action(액션)은 자바스크립트 객체 형식으로 되어있다.
* Reducer (리듀서)
  * Action(액션)을 Store(스토어)에 바로 전달하는 것이 아니다. 
  * Action(액션)을 Reducer(리듀서)에 전달해야한다. 
  * Reducer(리듀서)가 주문을 보고 Store(스토어)의 상태를 업데이트하는 것이다. 
  * Action(액션)을 Reducer(리듀서)에 전달하기 위해서는 dispatch() 메소드를 사용해야한다.


```plantuml
[*] --> Action
Action --> Reducer
    Action : * Action(액션) 객체가 dispatch() 메소드에 전달된다. 
    Action : * dispatch(액션)를 통해 Reducer를 호출한다.
Reducer --> Store
    Reducer : * Reducer는 새로운 Store 를 생성한다.
Store --> UI
UI --> [*]
UI : Change UI 
```

이런 방식으로 동작해야만 하는 이유는 데이터가 한 방향으로만 흘러야하기 때문이다.

Redux에서 위 개념을 구현하는 두 가지 방법
* mapStateToProps()
* Redux hooks (비교적 최근에 나온 기술)

useSelector  
useDispatch


Redux의 장점
* 상태를 예측 가능하게 만든다. (순수함수를 사용하기 때문)
* 유지보수 (복잡한 상태 관리와 비교)
* 디버깅에 유리 (action과 state log 기록 시) → redux dev tool (크롬 확장)
* 테스트를 붙이기 용의 (순수함수를 사용하기 때문)

Code spliting
* src 폴더 안에 actions, reducers, store, components, pages 폴더로 분할한다.
* 이때, component 폴더는 프리젠테이션 컴포넌트로, page 폴더는 컨테이너 컴포넌트로 나눈다.
* 이때, Store는 하나의 리듀서만 관리할 수 있지만 리듀서를 여러개 나눠서 하나로 합칠 수는 있다.



### 참조 

[링크 - 리액트 리덕스란?](https://hanamon.kr/redux%EB%9E%80-%EB%A6%AC%EB%8D%95%EC%8A%A4-%EC%83%81%ED%83%9C-%EA%B4%80%EB%A6%AC-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC/)
[상태관리 도구의 필요성](https://hanamon.kr/redux%EB%9E%80-%EB%A6%AC%EB%8D%95%EC%8A%A4-%EC%83%81%ED%83%9C-%EA%B4%80%EB%A6%AC-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC/)




## 설치

### Redux Toolkit (RTK)

    npm install @reduxjs/toolkit

RTK는 
* 저장소 준비  
  * <https://redux-toolkit.js.org/api/configureStore>
* 리듀서 생산과 불변 수정 로직 작성  
  * <https://redux-toolkit.js.org/api/createreducer>  
* 상태 "조각" 전부를 한번에 작성
  * <https://redux-toolkit.js.org/api/createslice>

일반적인 작업들을 단순화해주는 유틸리티를 포함하고 있습니다.

### Redux React app 만들기 

    npx create-react-app my-app --template redux

### Redux 코어 설치
    
    npm install redux

### Nextjs with Redux


```
npm i redux
npm i redux-thunk
npm i @redux-devtools/extension
npm i next-redux-wrapper

```


[넥스트 with Redux](https://devkkiri.com/post/59cb38dd-f939-462d-9e7f-afcc338b621f)  
[참조2](https://www.devkkiri.com/post/56578a18-d1fc-4c67-a2c4-6d64e21cf70c)  





### 의문 사항
 * 리덕스 홈페이지에 다음과 같은 문장이 있다.
   * 여러분은 Redux를 React나 다른 뷰 라이브러리와 함께 사용할 수 있습니다.


### 참조 링크
[Redux Home](https://ko.redux.js.org/introduction/getting-started/)  
[Redux 간단 이해](https://velog.io/@qf9ar8nv/%EA%B0%84%EB%8B%A8%ED%95%9C-%EC%98%88%EC%A0%9C%EB%A5%BC-%ED%86%B5%ED%95%B4-Redux%EB%A5%BC-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0)



# Nomad Study

``` 
npx create-next-app@latest

=> nextsjs-intro


code nextsjs-intro


```

## NextJs React 연동



### 정리 사항
내용을 보다 보면 설명을 봐도 이해 하기 어려운 부분이 자주 보인다. 
소스가 어려운것도 아닌데 이해하기 어렵다면 주로 소스가 생략이 되서 프레임웍이 알아서 해주는 케이스가 많았다. 
그래서 결국 그 생략된 관계를 파악하고 이해 하는게 중요하다. 
아래 createSlice 를 사용하면 소스가 많이 줄어 든다는 블로그가 있다. 
이런 케이스가 그런 케이스 중에 하나이다. 
소스가 줄어 든다는 것은 동일하게 만들어 사용되는 것을 규칙으로 공식 화해 더이상 두개를 만들지 않게 하는 경우가 많다.
하나의 선언으로 자동으로 설정을 해주는 경우가 많다.



### 참조    

[Nextjs 와 Redux 연동](https://devkkiri.com/post/59cb38dd-f939-462d-9e7f-afcc338b621f)   
[Nextjs : React hydration error](https://velog.io/@juurom/TIL-react-hydration-error-%EC%9B%90%EC%9D%B8-%EB%B0%8F-%ED%95%B4%EA%B2%B0%EB%B0%A9%EB%B2%95-feat.-react-calendar)  
[Redux 기초 개념](https://developerntraveler.tistory.com/144)   
[Redux Toolkit - createSlice 알아보기](https://velog.io/@goonerholic/Redux-Toolkit-createSlice-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)  



