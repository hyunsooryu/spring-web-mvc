package me.hyunsoo.mvcboot;


import me.hyunsoo.mvcboot.argumentResolvers.CriteriaResolver;
import me.hyunsoo.mvcboot.formatters.StudentFormatter;
import me.hyunsoo.mvcboot.interceptors.StudentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 리소스 핸들러란?
 * 이미지, 자바스크립트, CSS 그리고 HTML 파일과 같은 정적인 리소스를 처리하는 핸들러입니다.
 *
 * 컨테이너들에게는 이미 정적인 리소스를 처리할 수 있는 디폴트 서블릿이 존재하고 있습니다. -> DefaultServlet
 *
 * 전역적으로, CATALINA_BASE/conf/web.xml에 이미 등록되어 있습니다.
 *
 * 정적인 리소스 핸들러가 요청을 먼저 다 가로채면, 문제가 생깁니다.
 *
 * 우리가 만든 핸들러가 우선순위가 높게끔, 이후 정적인 리소스 핸들러는 우선순위가 가장 낮게끔 등록이 됩니다.
 *
 * 스프링 부트를 쓴다면, 기본적으로 정적 리소스 핸들러와 캐싱을 제공합니다.
 *
 * static, public directory에 정적자원들에 대해 지원을 해주고 있습니다.
 *
 * 기본적으로 디폴트로 /**, 정적자원 위치는 static, public 안에
 *
 * 캐싱 설정도 가능
 *
 *  HttpMessageConverter란?
 *  요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성할 때(@ResponseBody) 사용한다.
 *
 *  기본 HttpMessageConverter
 *  바이트 배열 컨버터, 문자열 컨버터, Resource 컨버터, Form 컨버터(to/from MultiValueMap<String, String>), (JAXB2 컨버터), (Jackson2 컨버터), (Jackson 컨버터), (Gson 컨버터)
 *
 *  기본적으로 Form 컨버터를 제공하기 때문에 -> application/form-url-encoded 타입의 데이터를 @ModelAttribute Obj obj, 혹은 @RequestBody MultiValueMap<String,String> map 에 converting 하여 채워 넣어줄 수 있다.
 *
 *  기본적인 Ajax, Form submit등의 default 요청은 application/form-url-encoded이다.
 *
 *  기본적으로 등록해주는 컨버터에 새로운 컨버터를 추가하기 위해서는 -> extendMessageConverters를 활용해야 한다. -> WebMvcConfigurer에서.
 *  configureMessageConverters를 사용할 시, 기본으로 등록해주는 컨버터는 다 무시하고, 새로 컨버터를 설정하기 때문이다.
 *
 *  의존성 추가로 컨버터를 등록할 수 도 있는데,
 *  메이븐, 또는 그래들 설정에 의존성을 추가할 시, 그에 따른 컨버터가 자동으로 등록되게 된다.
 *  WebMvcConfigurationSupport에 의해 그렇게 된다. -> 해당 기능은 스프링 프레임워크의 기능이지, 스프링 부트의 기능이 아니다.
 *
 *  XML 설정 방법도 제공
 *
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CriteriaResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new StudentFormatter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new StudentInterceptor());
        registration.addPathPatterns("/student/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
                .addResourceLocations("classpath:/mobile/")
                .setCacheControl(CacheControl.maxAge(Duration.ofSeconds(100)))
                .resourceChain(true);
    }

    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller xmlShaller = new Jaxb2Marshaller();
        xmlShaller.setPackagesToScan("me.hyunsoo.beans");
        return xmlShaller;
    }

}
