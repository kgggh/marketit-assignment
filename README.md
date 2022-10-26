### API 응답 구현

* 주문관리 API 
  * 주문 접수처리: /api/orders/{id}/accept
  * 주문 완료처리: /api/orders/{id}/complete
  * 단일 주문조회: /api/orders/{id}
  * 주문 목록조회: /api/orders  
* doc
  * swagger - http://{host}:{ip}/swagger-ui/index.html
### 구현
1. 요구사항에 주문에 대한 API는 명시되 있지 않아 open된 API는 존재 하지 않습니다.  
2. exception은 exception handler에서 처리하도록 구현했습니다.
3. 따로 주어진 제약조건이 없어, 주문 상태 변경에 대해선 전 후 상태가 같을 시 excpetion 처리했습니다.
4. service layer의 order() method는 test 및 auto-gen 하기 위해 만들었습니다.   
  * 추가로 주문건 생성요청 API가 없기에 테스터의 데이터 확인을 위함, auto-gen flag를 넣었습니다.  

  
#### 참고 - [application.yml](src/main/resources/application.yml)
```yml
server:
  port: 8888
spring:
  jpa:
    generate-ddl: true
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        format_sql: true
  datasource:
    url: jdbc:h2:mem:mydb
    username: sa
    password:
    driverClassName: org.h2.Driver


order:
  enable-mock: true <-- mock auto gen

```

### Test
테스트 정의는 주문 상태에 따른 처리 성공 및 실패 케이스 위주로 진행했습니다. 크게는 아래와 같이 3가지입니다.
#### 참고 - [OrderServiceTest](src/test/java/com/marketit/service/OrderServiceTest.java)
1. 주문요청 
2. 주문요청 -> 주문접수
3. 주문접수 -> 주문완료