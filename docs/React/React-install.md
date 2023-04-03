### 설치 방법

- node & npm(Node Packaged Manager : built in node)  설치 (with brew)
    - brew install node
        - node -v
        - npm -v
        - 
- react
    - 설치 하기
        - npx create-react-app [프로젝트명]
            - 프로젝트명으로 폴더가 생성됨
    - 기동하기
        - npm start
- Next.JS
    - 설치 하기
        - npx create-next-app [프로젝트명]
            - 프로젝트명으로 폴더가 생성됨
    - 기동하기
        - npm run dev


- 배포 빌드
    - 배포 빌드
        - npm run build
            - build 폴더 가 생성된다.
    - 빌드 버젼 실행
        - npx serve -s build
        - (실행 안됨) npm install -g serve
    - 포트번호 80으로 실행
        - sudo serve -l 80 -s build

- 궁금한 사항
    - 서비스 할때 배포는 어떻게 하나요? 서버는 nginx사용? or Was 서버에 통합?

### React 코딩 규칙

코딩 컨벤션은 협업 시 유지보수 및 가독성, 코드 이해를 위해 지켜지는 개발자들 사이의 규칙이다. 효율적이고 관리하기 쉬운 코드를 만들기 위해(유지보수) 효율적으로 규칙을 정해야 한다.

* 사용하는 이유
  * 의미 있는 코드 : 웹 표준과 접근성, 크로스 브라우징 증에 맞춘 의미 있는 코드 
  * 유지 보수가 용이한 코드 : 시간과 비용 절약을 위해 타인이 봤을때도 금방 이해하고 수정하기 쉽게끔 작성
  * 일관되 코드 : 모든 개발자가 각자의 규칙을 가지고 있기때문에 하나의 규칙을 정해 일관된 코드로 작성

* Naming Conventions
  * Component 파일명은 Pascal case(첫단어를 대문자로 시작하는 방식) 사용 
  ```  
  Header.js
  MyBanner.js  
  ```
  * Non-component 파일명은 camel case(띄어쓰기 대신에 대문자로 단어를 구분하는 표기 방식) 쓰세요.
  ```
    myStringUtils.js
    campApi.js
  ```

  * Unit test 파일명은 테스트 대상 파일명과 일치
  ```
    Header.js
    Header.test.js
    MyBanner.js
    MyBanner.test.js 
  ```
  
  * 속성/변수명은 camel case를 사용
  ```
    calssname
    onClick 
  
    const name = 'tester'
    let isGoods = fasle;
  ```
  
  * inline 스타일은 Camel case 로 작성해라. 
  ```
    <div style={{ fontSize: '1rem', fontWeight: '700' }}> </div> 
  ```
  
  * Css 파일명은 컴포넌트 이름과 동일하게 사용
  ```
    Header.css
    MyBanner.css 
  ```


* Bug Avoidance
  * null 또는 undefined일 수 있는 것들은 optional chaining(?.)을 사용하세요.
  ```
    obj?.prop;
    obj?.[expr];
    arr?.[index];
    func?.(args);
  ```
  
  * 전달한 파라미터의 유효성을 확실하게 하기 위해서 guard pattern/prop types/typescript을 사용한다.
  * side-effect를 피하기 위해서 함수 생성시 다음 규칙을 지켜주세요.
    * 어떤 함수에서 외부의 데이터를 직접적으로 사용하지 않고 파라미터로 받아서 사용한다.
    * input 값에 의해서 output이 결정되기 때문에 외부의 값들이 변경되더라도 함수 자체는 외부의 영향을 받지 않아요 . 따라서 Side-effect를 방지 할 수 있어요. 
  * 배포시 console.log()를 지운다.
  * props는 읽기 전용으로 사용하며, 직접 수정하지 않는다.

* Atchitecture & Clean Code
  * 유틸리티 파일을 만들어서 중복을 피하낟. 
  * 상태 관리 하는 Component와 UI를 보여주는 Component를 분리 하세요. component/presentation pattern를 따른다
  * 반복되어 import되는 이름을 줄이기 위해 각 폴더에 index.js 파일을 만든다.
    ```
        import {Nav} froim './Nav.js';
        import {MyBanner} froim './MyBanner.js';
        export { Nav, MyBanner }
    ```
  * 하나의 파일에 하나의 React component만 만든다.
  * 가능하면 컴포넌트 안에서 함수를 생성하지 마세요. 
  * 보모 컴포넌트가 아닌 다른 컴포넌트의 함수를 사용하지 마세요.(의존성 역전 피한다.)
  * 불필요한 주석을 사용 하지 않는다. 
  * 현재 화면보다 긴 코드는 더 작은 단위의 코드로 리팩토링 한다. 
  * 주석 처리된 코드는 커밋 하지 말고 삭제 한다.
  * 고차 컴포넌트(Higher Order Components, HOC)는 적절하게 사용한다.
  * JS, test, css로 파일을 분리한다.
  * 

* ES6 ( 마지막 코드의 의미는 정확히 모르겠습니다?)
  * spread 연산자를 사용한다.
  * 구조 분해 할당을 사용한다.
  * let과 const만 사용한다. (var 사용금지)
  * 되도록 화살표 함수를 사용한다.
  * 직접 null을 체크하기 보다 optional chaining 연산자 (?.)를 사용 한다.
  * 

* Testing
  * 테스트를 작성한다.
  * 적정 수준의 테스트 커버리지를 유지한다. 
  * 하나의 테스트에서 하나의 기능만 테스트 한다. 
  * 테스트에서 별도의 로직을 만드어서 사용하지 한다. 
  * 테스트 클래스는 하나의 클래스만 테스트 한다. 
  * 네트워크, 데이터 베이스와 직접 통신하지 말고 가짜 함수를 사용한다. 

* CSS
  * inline css를 사용하지 않는다. 
  * 명명 규칙을 지킨다. (네이밍 컨벤션 - bem, SUIT 등 )



### 주의 사항
* git 으로 공유시 패키지의 의존성이 정상적으로 설치 되지 않는 것같다. 
  * 그래서 다시 설치를 진행하였다.
* 



참조 링크
- brew 설치 : <https://brew.sh/index_ko>
- React : <https://ko.reactjs.org/>
- NextJs : <https://nextjs.org/>  
- 리액트 코딩 컨벤션 1 : [https://tech.toktokhan.dev/2020/08/30/front-convention/](https://tech.toktokhan.dev/2020/08/30/front-convention/)
- 리액트 코딩 컨벤션 2 : <https://3-stack.tistory.com/53>  
- 리액트 코딩 컨벤션 3 : <https://phrygia.github.io/react/2022-04-05-react/>
- 리액트 코딩 컨벤션 4 : <https://levelup.gitconnected.com/react-code-conventions-and-best-practices-433e23ed69aa>
- Next.js 설치법  : [https://hong-devbox.tistory.com/11](https://hong-devbox.tistory.com/11)
- React 설치 : [https://codingapple.com/unit/react1-install-create-react-app-npx/](https://codingapple.com/unit/react1-install-create-react-app-npx/)
- 빌드 및 배포 : [https://poew.tistory.com/608](https://poew.tistory.com/608)
- 빌드 및 배포 2 : [https://stroy-jy.tistory.com/32](https://stroy-jy.tistory.com/32)
- 빌드 및 배포 3 : [https://su-vin25.tistory.com/entry/React-리액트-프로젝트-배포-준비-프로젝트-빌드](https://su-vin25.tistory.com/entry/React-%EB%A6%AC%EC%95%A1%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC-%EC%A4%80%EB%B9%84-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B9%8C%EB%93%9C)
- 빌드 및 배포 4 : [https://su-vin25.tistory.com/entry/React-%EB%A6%AC%EC%95%A1%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC-Firebase-Hosting](https://su-vin25.tistory.com/entry/React-%EB%A6%AC%EC%95%A1%ED%8A%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC-Firebase-Hosting)  
- 배포 버전 실행시 포트 번호 변경법 : [https://ryeowon.github.io/posts/ReactJS_deployment/](https://ryeowon.github.io/posts/ReactJS_deployment/)
- 환경 변수 설정과 실행 환경 분리 : [https://junhyunny.github.io/react/react-env-variable-setting/](https://junhyunny.github.io/react/react-env-variable-setting/)
- Spring Boot 와 react 연동 : [https://velog.io/@u-nij/Spring-Boot-React.js-개발환경-세팅](https://velog.io/@u-nij/Spring-Boot-React.js-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85)
- 초보의 react 설치 : [https://zejeworld.tistory.com/109](https://zejeworld.tistory.com/109)  
- npm 과 npx 차이 <https://basemenks.tistory.com/232>  
- Container 컴포넌트와 Presentational 컴포넌트 : <https://fe-churi.tistory.com/34>
- Spread Operator(스프레드 연산자) : <https://dev-elena-k.tistory.com/28>  
- [node 설치에 대한 내용](https://zibu-story.tistory.com/151)  
-  
- 