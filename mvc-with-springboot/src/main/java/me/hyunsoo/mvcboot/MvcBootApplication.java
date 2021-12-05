package me.hyunsoo.mvcboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 스프링 부트에서의 MVC 설정
 * viewResolvers -> ContentNegotiatingViewResolver는 직접 뷰를 찾지않고,
 * 다른 리졸버들에게 해당 작업을 위임하게 됩니다.
 *
 *
 * autoconfigurer -> meta-inf -> spring.factories
 *
 * EnableAutoConfiguration 참조
 *
 * 1. DispatcherServletAutoConfiguration 디스패처 서블릿 자체를 만들고 등록 해 주는 자동설정
 * 2. ServletWebServerAutoConfiguration 톰캣 자동 설정
 * 3. WebMvcAutoConfiguration 스프링 MVC 자동 설정
 * WebMvcAutoConfiguration의 자동 설정 조건은 아래와 같다.
 *
 * -> @ConditionOnWebApplication(type=SERVLET) / 웹 타입이 서블릿 일 때,
 * -> WebMvcConfigurationSupport.class가 없는 경우 DelegatingWebMvcConfiguration.class가 없어야 합니다.
 *
 *  스프링 MVC 커스터마이징
 *  1. application.properties -> ResourceProperties, WebMvcProperties -> prefix가 있다. ex) spring.resources~~
 *  2. @Configuration + Implements WebMvcConfigurer -> 스프링 부트의 스프링 MVC 자동 설정을 사용하고, 거기에 더 설정을 추가 하겠다.
 *  3. @Configuration + @EnableWebMvc + Implements WebMvcConfigurer -> 스프링 부트의 스프링 MVC 자동설정을 사용하지 않는다. / 직접설정
 *
 *
 *
 *
 */

@SpringBootApplication
public class MvcBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcBootApplication.class, args);

    }

}
