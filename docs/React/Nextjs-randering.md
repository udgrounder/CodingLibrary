# Nextjs Randering 정리 

## 랜더링 방식에 따른 정리

### 1. CSR(Client-Side Rendering)
클라이언트 사이드 랜더링  

서버 비용이 높지 않으며 첫 로딩 이후의 속도가 빠르다.  
하지만 SEO에 부적합하며 초기 로드시간이 오래 걸린다.  
Next.js에서는 useEffect를 통해 데이터를 CSR을 구현할 수 있다. 하지만 Next.js에서 CSR을 구현하려면 useEffect를 사용하기 보다는 SWR 훅을 사용하여 구현하는 것을 권장한다. SWR을 사용하면 자동으로 캐싱하고 오래된 데이터를 갱신할 수 있다.
이렇게 CSR로 구현된 부분은 pre-render를 하지 않고 자바스크립트로 화면을 render한다.


### 2. SSG(Static Site Generation)
정적 페이지 생성  

웹사이트의 모든 페이지를 미리 렌더링하여 클라이언트의 요청에 맞춰 즉각적으로 페이지를 제공한다.  
완전히 정적인 HTML 기반 사이트인 SSG는 데이터베이스 또는 서버 측 프로세스가 필요하지 않기 때문에 가장 빠른 형식의 웹페이지이며, 미리 만들어져있기 때문에 SEO에 유리하며 안전하다.  
하지만 매 업데이트마다 다시 빌드 후 배포해야 하므로 오랜 시간이 걸리며 귀찮다. 따라서 내용이 거의 변하지 않는 웹사이트에 적합하다.

Next.js에서의 SSG
SSG에서 HTML은 build 할 때 발생한다. 그 후에는 CDN으로 캐시 되어지고 매 요청마다 HTML을 재사용한다.  
Next.js에서 일반적으로 컴포넌트를 생성하면 SSG로 동작한다.  
리액트에서는 useEffect를 통해 렌더링 시 데이터를 가져오지만, Nextjs에서 SSG를 구현하려면 getStaticProps나 getStaticPaths를 사용해야 한다.
getStaticProps와 getStaticPaths를 언제 사용해야 하는지 모르겠다면 앞선 글이 도움이 될 것이다.


### 3. SSR( Server-Side Rendering )
서버 사이드 렌더링  
서버에서 완전히 렌더링 된 페이지를 클라이언트로 보내며, 클라이언트의 자바스크립트 번들이 SPA 프레임워크의 작동을 대신한다.  
서버에서 렌더링되어 전송되기 때문에 동적 데이터를 사용하면서, SEO를 유지할 수 있다.  
다만 서버에서 모든 요청이 처리되므로 서버의 높은 연산 능력이 필요하며 공격할 수 있는 지점이 많기 때문에 보안을 유지하기 어려우며 캐싱에 복잡한 구성이 필요하다.  
SSR은 서버 비용을 크게 증가시킬 수 있으며, SEO에 크게 의존하는 매우 동적인 콘텐츠의 경우에만 사용해야 한다.  
Next.js에서 SSR을 구현하기 위해서는 getServerSideProps을 사용하면 된다.  
getServerSideProps는 빌드시에 요청하는 것이 아니라 매 요청시마다 데이터를 요청하기 때문에 자주 업데이트 되어야 하는 페이지에 사용해야만 한다.  


### 4. ISR (Incremental Static Regeneration)
점진적 정적 재생성  
SSG와 SSR의 장점을 합친 것으로, 전체 사이트를 재빌드 할 필요 없이 페이지별로 정적 생성을 사용할 수 있다.  
페이지를 미리 렌더링하고 캐시하기 때문에 매우 빠르며, 내용이 변경되어도 사이트를 다시 배포할 필요가 없다. SEO에 유리하다.  
다만 웹사이트에 방문한 도중에 업데이트 된다면 사용자는 업데이트 된 컨텐츠를 볼 수 없다.  
따라서 블로그와 같이 동적인 콘텐츠이지만 자주 변경되지 않는 사이트에 ISR을 사용하는 것이 좋다.  

```
export async function getStaticProps() {
  const res = await fetch('https://.../posts')
  const posts = await res.json()

  return {
    props: {
      posts,
    },
    revalidate: 10,
  }
}
```
Next.js에서는 getStaticProps에서 revalidate 옵션을 통해 ISR을 구현할 수 있다.  
revalidate은 데이터 패치 주기를 설정할 수 있는 옵션인데, 위와 같이 10으로 설정한다면 10초마다 데이터를 패칭할 수 있다.




## 참고 사항

### pre-render란? (hydration)
Nextjs의 기본은 pre-renders이다.  
pre-render란 페이지에서 js를 우선하는게 아니라, HTML을 미리 만드는 것을 말한다.  
따라서 더 좋은 성능과 SEO를 기대할 수 있다.  
이때 생성된 HTML은 최소한의 javascript 코드와 연결된다.  
그 후에 브라우저가 로딩될 때 남은 javascript가 페이지와 상호작용하면서 페이지가 render 된다.  
이러한 개념은 hydration이라는 개념이라고 부르기도 한다.  
Nextjs가 pre-rendering하는 방법으로는 SSG와 SSR이 있다.



## 데이터 패칭 방식

기본적으로 getInitialProps를 비롯한 데이터 패칭 메소드들은 리턴한 값을 해당 컴포넌트의 props로 보낸다.  
getinitialProps는 context를 기본 props로 받는데, 그 내부엔 ctx객체와 Component가 존재한다.  
Component는 해당 컴포넌트를 의미하며, Component로 보내는 ctx 객체의 구성은 다음과 같다.

* pathname - 현재 pathname /user?type=normal page 접속 시에는 /user
* query - 현재 query를 object형태로 출력 /user?type=normal page 접속 시에는 {type: 'normal'}
* asPath - 전체 path /user?type=normal page 접속 시에는 /user?type=normal
* req - HTTP request object (server only)
* res - HTTP response object (server only)
* err - Error object if any error is encountered during the rendering

여기서 주의할점은 getInitialProps 내부 로직은 서버에서 실행되기 때문에 Client에서만 가능한 로직은 피해야 한다.(Window, document 등)  
한 페이지를 로드할 때, 하나의 getInitialProps 로직만 실행된다.  
예를 들어 _app.js에 getInitialProps를 달아서 사용한다면 그 하부 페이지의 getInitialProps는 실행되지 않는다. -> 커스터마이징을 통해 따로 처리해줘야 한다.  
이런 것들을 고려해보았을 때, 이렇게 _app.tsx를 건들여서 데이터 패칭을 하는 것은 모든 페이지에서 전역적으로 가져와야 하는 데이터가 일괄적일 때 이외엔 지양하는 편이 좋다는 것을 알 수 있다.


### getInitialProps
모든 페이지에 공통적인 데이터 패칭이 필요하다면 _app.tsx에서 전역적 데이터를 패칭해야 하는데, getInitialProps 이라는 기능을 사용한다.  
하지만 이 방식을 사용하면 SSR 계산없이 페이지를 정적 HTML 으로 사전렌더링 해서 최적화를 하는 자동 정적 최적화(Automatic Static Optimization)가 비활성화되어 모든 페이지가 SSR을 통해 제공되게 된다.

때문에 Next.js 9.3버전 이후엔 이런 것을 방지하고자 SSR과 SSG를 분리해 Static Generation(정적 생성)인 getStaticProps, getStaticPath 와 getServerSideProps 로 나눠졌으며, 전역적인 데이터 패치 기능을 지원하지 않는다.  

### getStaticProps => (SSG, ISR)
"빌드 시에 딱 한 번"만 호출되고, 바로 static file로 빌드되어, 빌드 이후 수정이 불가능하다.   
data를 빌드시에 미리 호출하여 정적으로 제공하기 때문에 페이지 렌더속도가 빠르다.  
때문에 유저의 요청마다 fetch할 필요가 없는 고정된 내용의 페이지를 렌더링할 때 유리하다.  
getStaticProps는 빌드 시 데이터를 가져오며 쿼리 매개변수 또는 HTTP 헤더와 같이 요청 시에만 사용할 수 있는 데이터는 사용할 수 없다.
getStaticProps는 revalidate이라는 옵션을 통해 주기적으로 데이터를 패칭하여 SSG와 SSR의 장점이 합쳐진 ISR을 구현할 수 있다.


### getStaticPath => (Static Generation)
동적라우팅 + getStaticProps를 원할 때 사용한다.  
페이지가 동적 라우팅을 쓰고 있고, getStaticProps를 쓰는 경우, getStaticPaths을 통해 빌드 타임 때 정적으로 렌더링할 경로를 설정해야 한다.  
여기서 정의하지 않은 하위 경로는 접근해도 화면이 뜨지 않는다.  
동적라우팅 할 때, 라우팅 되는 경우의 수를 하나하나 집어넣어야 한다.  


### getServerSideProps

getServerSideProps는 빌드와 상관없이, page가 요청받을때마다" 호출되어 pre-rendering한다.   
SSR (Server Side Rendering) 개념으로 pre-render가 꼭 필요한 동적 데이터가 있는 page에 사용한다. 
매 요청마다 호출되므로 성능은 getStaticProps에 뒤지지만, 내용을 언제든 동적으로 수정이 가능하다다.  
따라서 getServerSideProps는 사용자 대시보드 페이지(내정보 페이지)에 적합하다. 
대시보드는 사용자 고유의 개인 페이지이므로 SEO는 관련이 없으며 페이지를 미리 렌더링할 필요가 없으며, 데이터는 자주 업데이트되므로 요청 시간 데이터를 가져와야 한다.    
Next.js는 getServerSideProps를 정말 필요할 때만 사용하라고 권고한다. 
CDN에 캐싱되지 않기 때문에 느리기 때문이다. 
데이터를 미리 가져올 필요가 없다면 클라이언트 측에서 데이터를 가져오는 것도 고려해봐야 한다. (ex. 사용자 대시보드는 사용자별 비공개 페이지이므로 SEO와 관련 없으며 미리 렌더링할 필요가 없다.)    
그 외 getServerSideProps의 작동방식의 특징으로는 getServerSideProps는 서버사이드에서만 실행되고, 절대로 브라우저에서 실행되지 않는다.  
getServerSideProps는 매 요청시 마다 실행되고, 그 결과에 따른 값을 props로 넘겨준 뒤 렌더링을 한다.



### 참조 링크
* [CSR, SSG, SSR, ISG](https://velog.io/@te-ing/NextJS%EB%A1%9C-%EC%82%B4%ED%8E%B4%EB%B3%B4%EB%8A%94-SSG-ISG-SSR-CSR)  
* [Nextjs 데이터 가져오기 기초(SSR, SSG, CSR)](https://han-py.tistory.com/m/489)  
* [Nextjs 공식문서] (https://nextjs.org/docs/pages/building-your-application/data-fetching/incremental-static-regeneration)  
* [프론트엔드 렌더링: SSG vs ISG vs SSR vs CSR - 언제 어떤 것을 사용해야 할까요?](https://velog.io/@cookie004/%ED%94%84%EB%A1%A0%ED%8A%B8%EC%97%94%EB%93%9C-%EB%A0%8C%EB%8D%94%EB%A7%81-SSG-vs-ISG-vs-SSR-vs-CSR-%EC%96%B8%EC%A0%9C-%EC%96%B4%EB%96%A4-%EA%B2%83%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%95%BC-%ED%95%A0%EA%B9%8C%EC%9A%94)  
* [Next.js의 데이터패칭 방식: getStaticProps, getStaticPath, getServerSideProps은 언제 사용하는가?](https://velog.io/@te-ing/Next.js%EC%9D%98-%EB%8D%B0%EC%9D%B4%ED%84%B0%ED%8C%A8%EC%B9%AD-%EB%B0%A9%EC%8B%9D-getStaticProps-getStaticPath-getServerSideProps%EC%9D%80-%EC%96%B8%EC%A0%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94%EA%B0%80#getstaticpaths-static-generation)  
* [Next.js 100% 활용하기 (feat. getInitialProps, getStaticPath, getStaticProps, getServerSideProps, storybook)](https://velog.io/@devstone/Next.js-100-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0-feat.-initialProps-webpack-storybook#-getstaticprops)


