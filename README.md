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
   
# 스프링 MVC 설정

### 1. 기본 전략을 사용하기 싫다면, Bean으로 등록하여 사용하라
Spring MVC를 설정 할 때, 별 다른 설정을 하지 않아도 DispatcherServlet.properties에 있는 기본 전략을 사용하게 된다. 문제는 기본 전략은 모두 해당 기본 값으로 적용되기 때문에 서비스에 이를 사용하기에는 문제가 있다., 극단적인 예로 InternalResourceViewResolver 가 DispatchServlet의 initStratgies Method를 통해 등록 될 때, prefix와 suffix가 없는 것을 들 수 있다. 그렇다면 어떻게 해야할 것인가. ViewResolver를 예로 들자면, DispatcherServlet은 ApplicationContext에, 해당 ViewResolver.class 타입 혹은 자식의 Bean이 있을 경우 해당 Bean을 등록하여 사용하고, 해당 Bean이 없는 경우에 한하여 기본 전략을 구사하게 된다. 즉 만약, DispatcherServlet의 기본전략을 사용하고 싶지 않다면, 사용하고 싶은 기능을 본인이 직접 Bean으로 등록하는 방법이 있을 것이다. @Configuration + @Bean 조합이 가장 간단한 그 예가 될것이다. HandlerMapping, HandlerAdapter 등을 직접 @Bean으로 등록해보자.

### 2. 기본 전략을 사용하기 싫다면, @EnableWebMvc를 사용하라 - 애노테이션 기반 스프링 MVC를 사용할 때 편리한 웹 MVC 기본 설정
@EnableWebMvc는 @Configuration이 달려있는 Config.java 파일과 함께 사용될 수 있다. 해당 애노테이션을 살펴보면, @Import(DelegatingWebMvcConfiguration.class) 라는 부분을 확인 할 수 있다. 
DelegatingWebMvcConfiguration.class 는 WebMvcConfigurationSupport.class를 상속받는 구조인데 해당 클래스 파일을 살펴보면, 실질적으로 MVC와 관련한 많은 @Bean들이 등록 되어있다는 사실을 알 수 있다. WebMvcConfigurationSupport 자바 파일을 살펴보면, if(jackson2Present){adpater.setRequestBodyAdvice(~)}, if(jackson2Present){messageConverter.add(new MappingJackson2HttpMessageConverter())} 와 같은 내용을 볼 수 있다. 클래스 로더 패턴을 사용하여, classUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper") && classUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator") 인 경우, 해당 내용을 빈 설정에 활용 하는 것이다. 즉, classpath에 Jackson 관련 라이브러리가 있는 경우 자동으로 빈설정에 이를 반영하게 된다. 이러한 이유로, 우리가 개발을 할 때 따로 Jackson 관련한 ObjectMapper나, MessageConverㅅer를 직접 등록해 주지 않아
DelegatingWebMvcConfiguration.class 는 WebMvcConfigurationSupport.class를 상속받는 구조인데 해당 클래스 파일을 살펴보면, 실질적으로 MVC와 관련한 많은 @Bean들이 등록 되어있다는 사실을 알 수 있다. WebMvcConfigurationSupport 자바 파일을 살펴보면, if(jackson2Present){adpater.setRequestBodyAdvice()}, if(jackson2Present){messageConverter.add(new MappingJackson2HttpMessageConverter())} 와 같은 내용을 볼 수 있다. 클래스 로더 패턴을 사용하여, classUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper") && classUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator") 인 경우, 해당 내용을 빈 설정에 활용 하는 것이다. 즉, classpath에 Jackson 관련 라이브러리가 있는 경우 자동으로 빈설정에 이를 반영하게 된다. 이러한 이유로, 우리가 개발을 할 때 따로 Jackson 관련한 ObjectMapper나, MessageConverter를 직접 등록해 주지 않아도, pom.xml에 dependency로 추가만 하면 자동으로 사용할 수 있게 되는것이다. 참 재미있는 것은 @EnableWebMvc를 사용하면, HandlerMapping에 해당 되는 핸들러매핑 중 RequestMappingHanderMapping이 가장 맨 앞에 위치하게 된다는 점이다. DispatcherServlet의 기본 전략에서는 BeanNameHandlerMapping이 1위였다.

### 3.WebMvcConfigurer를 구현하라
WebMvcConfigurer 인터페이스를 구현하면, 여러 확장 포인트들을 사용할 수 있다. WebMvcConfigurer는 SpringBoot에서도 사용할 수 있다. 스프링 3.1부터 제공하며, 스프링 부트에서도 웹MVC 자동설정 이외의 추가적인 설정을 하고 싶을 때 해당 인터페이스를 사용하고는 한다. 가장 코딩적으로 확장할 수 있는 인터페이스이다. Boot가 아닌 환경에서는, @EnableWebMvc에서 델리게이션 구조로 WebMvcConfigurer에게 위임한다. 즉 직접 Bean으로 등록하지 않아도, EnableWebMvc가 등록해주는 Bean들을 커스터마이징 할 수 있는 장점이 있다. 즉 WebMvcConfigurer는 MVC 관련 한 설정 Bean들을 커스터 마이징 할 떄 활용할 수 있는 인터페이스 라는 것이다. 가령 Formatter, Converter 같은 경우, SpringBoot에서는 Bean으로만 등록이 되어있어도 FormatterRegistry에 등록해주는 그런 장점이 있기도 해서, Boot의 MVC 설정은 조금 더 자동적으로 수행되는 포인트들이 많아 따로 공부를 해야한다. 또한 ContentNegotiationViewResolver 와 같은 경우, Spring Boot에서는 기본으로 설정이 되어있다.

### 4.SpringBoot의 스프링 MVC 설정

1. 스프링 부트 기본 web mvc 설정 https://github.com/hyunsooryu/spring-web-mvc/tree/master/mvc-with-springboot
2. 스프링 부트 jsp 및 war 배포 설정 https://github.com/hyunsooryu/spring-web-mvc/tree/master/war-with-springboot
3. 스프링 부트 CORS, HATEOS, EXCEPTION HANDLER, COOKIE VALUE 등 설정 https://github.com/hyunsooryu/spring-boot-mvc



