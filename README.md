# spring-web-mvc
1. 스프링 MVC의 동작 원리 정리
2. 스프링 MVC의 설정 정리
3. 스프링 MVC의 여러 기능 정리


# 들어가기 전에
Apache Common DBCP2에 대한 심도있는 내용을, https://github.com/hyunsooryu/spring-db-access-ways/tree/master/about-dbcp DBCP 편에 정리하면서, DBCP 설정에서
maxTotal(구 version 에서는 maxActive), maxWait 등과 함께 거론되었던 것이 바로 maxThread (tomcat server.xml에 설정)이다.
1 Request Per Thread, 각각의 Thread가 Request를 수행하게 되는 Spring MVC 구조는 Servlet Application이 그 토대이다.
Servlet Programmming을 더이상, 레거시 프로젝트가 아닌 이상 다룰 일은 없다고 하지만, Spring MVC의 원리에 대해 이해를 하기 위해서 서블릿에 대한 이해는 필수이다.
심지어, web.xml에 servlet으로 spring이 제공하는 DispatcherServlet을 등록해서 사용하고 있다.
Spring-mvc 자체가 어떠한, 고정 된 틀이 라는 개념이 아니라, IocContainer로서, Mvc 로서의 Spring Tech가 Servlet 기반의 웹 애플리케이션에 녹아져 있다고 생각하는 것이 옳다.
실제로, url이 매핑된 서블릿과, front Controller 격인 DispatcherServlet을 동시에 당연히 사용할 수 있으며, DispatcherServlet은 더욱 여러개 등록할 수 있다.
결국, servlet 기반 위에, 어떠한 방법으로 스프링이 돌아가는 지를 잘 알아야 한다.

# 스프링 MVC의 동작 원리의 중요한 포인트
1. ContextLoaderListener 을 활용한 AnnotationConfigApplicationContext 등록 (RootWebApplicationContext)
2. DispatcherServlet 이 생성하는 AnnotationConfigApplicationContxt 활용 (WebApplicationContext)
3. WebApplicationContext VS RootWebApplicationContext 왜 2개를 쓸까? ( DispatcherServlet이 하나 일 필요는 없기 떄문에)
4. Ioc Container 로서의 spring, MVC 로서의 spring 을 ServletApplication에 자연스레 녹여보는 방법과 원리
5. DispatcherServlet을 Debugging 하며 1 request Cycle 확인
6. handerMapping -> handlerAdapter -> doPrehandle(intercetors) -> invoke handler (Reflection) -> HandlerMethodArgumentResolver -> 필요시(MessageConverter)
   이후 returnValue를 보고, viewResolver 혹은, ResponseEntity 로 넘길 것인지 판단
   


