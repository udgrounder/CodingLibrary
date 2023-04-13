# javaScript 를 바탕으로 TypeScript 를 이해해 보자.

* 타입스크립트는 마이크로소프트(MS)에서 개발하여 2012년10월에 첫 출시되었다.
* 오픈소스 프로그래밍 언어로 어떤 브라우저나 호스트, 운영체제에서도 동작한다.
* Typescript 는 Javascript 기반의 언어이다. 
* JavaScript는 클라이언트 측 스크립팅 언어 TypeScript는 객체 지향 컴파일 언어
* Typescript는 JavaScript의 상위 집합으로 JavaScript의 모든 기능이 있음
* TypeScript 컴파일러를 사용하여 ts(TypeScript)파일을 js(JavaScript) 파일로 변환, 쉽게 통합
* 정적 유형 검사 제공
* 클래스 기반 객체를 만들 수 있음
* 클래스 기반이므로 객체 지향 프로그래밍 언어로 상속, 캡슐화 및 생성자를 지원할 수 있음


## Typescript의 특징
* class와 interface의 특징을 지원함으로서 완전한 객체지향 프로그래밍 환경을 제공합니다.
  * class, interface, extends, implements 등 처럼 전통적인 객체지향 언어에서 사용하던 키워드를 제공합니다. 하지만, 기존 언어와의 차이는 타입스크립트는 java처럼 다중 생성자를 선언할 수 없다는 점입니다. 타입스크립트는 java와 달리 디폴트 초기화 매개변수와 선택 매개변수를 선언할 수 있습니다.
* javascript의 타입을 확장하고 타입 어노테이션을 이용해서 변수에 타입을 선언할 수 있게 합니다.
  * 이렇게 타입을 지정하게 되면 변수는 엄격한 타이핑이 적용되어 타입의 안정성이 확보되기떄문에 이러한 타입 시스템을 지원합니다.
* ES6에서 제공하는 모듈 선언과 모듈 호출 방식을 지원합니다.
  * class가 커지고 많아짐에 따라 유자한 기능의 class들을 group으로 구분짓기 위해 네임스페이스를 지원하며 라이브러리 단위의 모듈 구성에 유리합니다.
* MS에서 만든 언어답게 같은 회사에서 만든 vscode와 궁합이 너무 좋습니다.
  * IDE는 아니지만, 각종 플러그인을 통해 TS로 상상할 수 있는 모든 개발환경을 너무 쉽게 구축가능합니다.
* nodejs를 포함해서 기존 javascript 생태계에는 언어의 version이 올라가도 어느정도 하위 호환성을 빠르게 새로운 문법을 추가하거나 기존 문법을 변경하지 못합니다.
  * Babel과 같은 트랜스파일러를 통해 ES6+문법을 ES5문법으로 컴파일해서 호환성을 해결해야합니다. ECMA 에서 반려되어 자바스크립트에선 도입될 가능성이 사라진 몇가지 문법들 중에 타입스크립트 개발자들이 보기에 유용하다고 생각된 문법들도 일부 도입했는데 대표적으로 데코레이터와 추상클래스 등이 있습니다.
* 조기 버그를 감지할 수 있습니다.
  * 컴파일러가 코드를 검사하면 개발시에 경고 및 오류가 포착되어 런타임 시 버그 및 예기치 못한 동작의 가능성이 줄어듭니다.


TypeScript 예시 코드
자바스크립트
````javascript
function sum(a, b) {
return a + b;
}
````

//정적 타입을 지원하지 않으므로 어떤 타입의 반환값을 리턴해야 하는지 명확하지 않음
타입스크립트
```typescript 

function sum(a: number, b: number) {
return a + b;
}
//정적 타입을 지원, 컴파일 단계에서 오류 포착 장점  => 코드의 가독성 높임
class Person {
    private name: string;
    
    constructor(name: string) {
        this.name = name;
    }
    
    sayHello() {
        return "Hello, " + this.name;
    }
}

const person = new Person('Lee');

console.log(person.sayHello());
```

```javascript
//위의 타입스크립트를 트랜스파일링하면 다음과 같이 자바스크립트 파일이 생성됨
var Person = /** @class */ (function () {
    function Person(name) {
    this.name = name;
    }
    Person.prototype.sayHello = function () {
    return "Hello, " + this.name;
    };
    return Person;
}());

var person = new Person('Lee');
console.log(person.sayHello());
```


* 정적 타입
```typescript
let foo: string = "hello" //타입스크립트
let foo = "hello" //자바스크립트

// 함수선언식 타입스크립트
function multiply1(x: number, y: number): number {
return x * y;
}

//함수 선언식 자바스크립트
function multiply1(x, y) {
return x * y;
}

// 함수표현식 타입스크립트
const multiply2 = (x: number, y: number): number => x * y;

// 함수표현식 자바스크립트
const multiply2 = (x, y) => x * y;

//boolean
let isBoolean: boolean = false;
//null
let isNull: null = null;
//undefined
let isUndefined:undefined = undefined;
// array
let list: any[] = [1,2,"3",false]
let list2: number[] = [2,3,4]
let list3: Array<number> = [1,2,3]

//tuple
let tuple : [string, number]
tuple = ["hello",10]

//enum : 열거형은 숫자값 집합에 이름을 지정한 것
enum Color1 {Red,Green,Blue}
let c1: Color1 = Color1.Green
console.log(c1) //1


//any 타입추론을 할 수 없거나 타입 체크가 필요없는 변수에 사용
let isAny: any = 5
isAny ="string:
isAny = false

//void : 일반적으로 함수에 반환값이 없을 경우
function isUser():void {
console.log("hello")
}

//never : 결코 발생하지 않은 값
function infiniteLoop(): never {
while(true){}
}
```



### 기타
* 타입은 소문자, 대문자 구별
* 타입 선언을 생략하면 동적으로 타입이 결정 => 타입 추론
* 타입 선언 생략 + 값 할당 x => any 타입
* 
```typescript
var foo;

console.log(typeof foo);  // undefined

foo = null;
console.log(typeof foo);  // object

foo = {};
console.log(typeof foo);  // object

foo = 3;
console.log(typeof foo);  // number

foo = 3.14;
console.log(typeof foo);  // number

foo = "Hi there";
console.log(typeof foo);  // string

foo = true;
console.log(typeof foo);  // boolean
```

```typescript
let foo; // let foo: any와 동치

foo = 'Hello';
console.log(typeof foo); // string

foo = true;
console.log(typeof foo); // boolean
```




[Typescript - 공식 문서](https://www.typescriptlang.org/docs/handbook/utility-types.html)   
[javaScript and TypeScript 차이점 1](https://velog.io/@pluviabc1/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%EC%99%80-%ED%83%80%EC%9E%85%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%B0%A8%EC%9D%B4%EC%A0%90)  
[javaScript and TypeScript 차이점 2](https://choseongho93.tistory.com/319)  
[Typescript 공부 방법 - Good](https://yozm.wishket.com/magazine/detail/1376/)  
[Typescript에서 효과적으로 상수 관리하기](https://blog.toycrane.xyz/typescript%EC%97%90%EC%84%9C-%ED%9A%A8%EA%B3%BC%EC%A0%81%EC%9C%BC%EB%A1%9C-%EC%83%81%EC%88%98-%EA%B4%80%EB%A6%AC%ED%95%98%EA%B8%B0-e926db079f9)   

