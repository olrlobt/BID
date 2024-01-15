# 1. 기술스택 / 컨벤션 / 브랜치전략

### 백엔드

**기술스택**

1. 언어 : Java 17
2. 프레임워크 : 스프링부트 3.2.1(미정)
3. 라이브러리 : spring-data-jpa, spring-security, query dsl
4. 데이터베이스 : 뭐든..!, redis
5. 각자 하고 싶은 것 : 채팅 / 대용량 데이터 관리

**컨벤션**

네이버 컨벤션 사용

[https://naver.github.io/hackday-conventions-java/](https://naver.github.io/hackday-conventions-java/)

[https://google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html)

[https://www.oracle.com/java/technologies/javase/codeconventions-contents.html](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

[https://ui.toast.com/fe-guide/ko_CODING-CONVENTION](https://ui.toast.com/fe-guide/ko_CODING-CONVENTION)

**CICD**

EC2, Jenkins, Docker, S3

**기타**

테스트 : 서비스 코드에 대한 테스트 코드 작성, API 테스트(포스트맨)

---

### 공통

**브랜치전략  (**[https://techblog.woowahan.com/2553/](https://techblog.woowahan.com/2553/))

master : 제품으로 출시될 수 있는 브랜치

develop : 개발 중인 브랜치

feature : 기능을 개발하는 브랜치

- feature/기능이름 형식으로 작성하여 각 기능을 세부적으로 나눔.

**커밋 컨벤션 (**[https://velog.io/@shin6403/Git-git-커밋-컨벤션-설정하기](https://velog.io/@shin6403/Git-git-%EC%BB%A4%EB%B0%8B-%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0)**)**

**`태그 : 제목`의 형태이며, `:`뒤에만 space가 있음에 유의한다.**

ex) `feat: 로그인 기능`

| 커밋 유형 | 의미 |
| --- | --- |
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 |
| style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| refactor | 코드 리팩토링 |
| test | 테스트 코드, 리팩토링 테스트 코드 추가 |
| chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
| design | CSS 등 사용자 UI 디자인 변경 |
| comment | 필요한 주석 추가 및 변경 |
| rename | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
| remove | 파일을 삭제하는 작업만 수행한 경우 |

---

### 프론트엔드

**기술스택**

1. Library : React
2. State : Redux
3. CSS : styled-component
4. Tool : Figma

+ ) 3D 고려해보기 …

**컨벤션**

우아한 형제들 

[GitHub - tipjs/javascript-style-guide: Airbnb JavaScript 스타일 가이드 한국어](https://github.com/tipjs/javascript-style-guide)

[[JavaScript] Airbnb 컨벤션 정리](https://velog.io/@hamham/Airbnb-JavaScript-컨벤션-정리)

**CICD**

GitLab CI/CI …?
