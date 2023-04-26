# React



## React 의 특징

* React 는 웹 프레임 워크로, 자바스크립트 라이브러리의 하나로서 사용자 인터페이스를 만들기 위해 사용된다.
* Reactsms facebook에서 제공해주는 프론트엔드 라이브러라고 볼 수 있다.
* 싱글 페이지 어플리케이션이나 모바일 어플리케이션의 개발 시 토대로 사용될 수 있다.


### 참조
* [React 간단 정리](https://velog.io/@jini_eun/React-React.js%EB%9E%80-%EA%B0%84%EB%8B%A8-%EC%A0%95%EB%A6%AC)    

## Hook 이란?
React는 상태 관련 로직을 공유하기 위해 좀 더 좋은 기초 요소가 필요했습니다.
Hook을 사용하면 컴포넌트로부터 상태 관련 로직을 추상화할 수 있습니다. 이를 이용해 독립적인 테스트와 재사용이 가능합니다. Hook은 계층의 변화 없이 상태 관련 로직을 재사용할 수 있도록 도와줍니다. 이것은 많은 컴포넌트 혹은 커뮤니티 사이에서 Hook을 공유하기 쉽게 만들어줍니다.
=> 말이 어렵네...   
Hook은 class를 작성하지 않고도 state와 다른 React의 기능들을 사용할 수 있도록 해줍니다.  
Hook은 함수 컴포넌트에서 React state와 생명주기 기능(lifecycle features)을 “연동(hook into)“할 수 있게 해주는 함수입니다. Hook은 class 안에서는 동작하지 않습니다. 대신 class 없이 React를 사용할 수 있게 해주는 것입니다.

Hook은 JavaScript 함수입니다. 하지만 Hook을 사용할 때는 두 가지 규칙을 준수해야 합니다. 우리는 이러한 규칙들을 자동으로 강제하기 위한 linter 플러그인을 제공하고 있습니다.

### 최상위(at the Top Level)에서만 Hook을 호출해야 합니다
반복문, 조건문 혹은 중첩된 함수 내에서 Hook을 호출하지 마세요. 
대신 early return이 실행되기 전에 항상 React 함수의 최상위(at the top level)에서 Hook을 호출해야 합니다. 
이 규칙을 따르면 컴포넌트가 렌더링 될 때마다 항상 동일한 순서로 Hook이 호출되는 것이 보장됩니다. 
이러한 점은 React가 useState 와 useEffect 가 여러 번 호출되는 중에도 Hook의 상태를 올바르게 유지할 수 있도록 해줍니다.

### 오직 React 함수 내에서 Hook을 호출해야 합니다
Hook을 일반적인 JavaScript 함수에서 호출하지 마세요. 대신 아래와 같이 호출할 수 있습니다.

✅ React 함수 컴포넌트에서 Hook을 호출하세요.  
✅ Custom Hook에서 Hook을 호출하세요. (다음 페이지에서 이 부분을 살펴볼 예정입니다)
이 규칙을 지키면 컴포넌트의 모든 상태 관련 로직을 소스코드에서 명확하게 보이도록 할 수 있습니다.

### 참조 링크 
* [react hook 이란](https://ko.legacy.reactjs.org/docs/hooks-intro.html)   
* [react state hook 설명](https://ko.legacy.reactjs.org/docs/hooks-state.html)   
* [Hook 규칙](https://ko.legacy.reactjs.org/docs/hooks-rules.html)   



## 확장자

### jsx 와 js 차이
* jsx 는 Javascript XML의 약자로 React에서 UI 를 구성하는데 쓰인다. 
* js 는 일반적인 Javascript 코드를 포함 한다. UI를 구성할 수 있지만 일반적으로 로직 부분을 담당 한다.  

JSX가 HTML과 유사한 구문을 사용하여 React UI를 작성하는 데 사용된다는 점이다. JSX는 JavaScript 코드를 포함하지만, HTML 요소와 유사한 구문을 사용하여 React에서 UI를 더 쉽게 작성할 수 있도록 도와준다.

** jsx 로 작성한 코드는 나중에 javascript 로 컴파일하여 브라우저에서 실행 할 수 있다.

### tsx 와 ts 차이 
이 두 확장자의 차이는 jsx 와 js 의 차이와 동일하다. 단 typescript를 사용한다는 점이 다르다.

** typescript 컴파일러에 의해 javascritp 코드로 변환된다.

### 참조 링크
* [tsx 와 ts의 차이 & jsx 와 js 차이](https://subtlething.tistory.com/112)   
