# spring-web-mvc
1. 스프링 MVC의 동작 원리 정리
2. 스프링 MVC의 설정 정리
3. 스프링 MVC의 여러 기능 정리

# 스프링 MVC의 동작 원리의 중요한 포인트
1. ContextLoaderListener 을 활용한 AnnotationConfigApplicationContext 등록 (RootWebApplicationContext)
2. DispatcherServlet 이 생성하는 AnnotationConfigApplicationContxt 활용 (WebApplicationContext)
3. WebApplicationContext VS RootWebApplicationContext 왜 2개를 쓸까? ( DispatcherServlet이 하나 일 필요는 없기 떄문에)
4. Ioc Container 로서의 spring, MVC 로서의 spring 을 ServletApplication에 자연스레 녹여보는 방법과 원리
5. DispatcherServlet을 Debugging 하며 1 request Cycle 확인
6. handerMapping -> handlerAdapter -> doPrehandle(intercetors) -> invoke handler (Reflection) -> HandlerMethodArgumentResolver -> 필요시(MessageConverter)
   이후 returnValue를 보고, viewResolver 혹은, ResponseEntity 로 넘길 것인지 판단

