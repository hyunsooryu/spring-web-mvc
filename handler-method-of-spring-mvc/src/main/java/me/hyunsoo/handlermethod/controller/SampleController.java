package me.hyunsoo.handlermethod.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.InputStream;
import java.io.Reader;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * 핸들러 메소드 1부 : 지원하는 메소드 아규먼트와 리턴타입
 *
 * 핸들러 메소드 아규먼트 : 주로 요청 그 자체 또는 요청에 들어있는 정보를 받아오는데 사용이 됩니다.
 * 1. WebRequest, NativeWebRequest, ServletRequest(Response), HttpServletRequest(Response)
 *  - 요청 또는 응답 자체에 접근 가능한 API
 *  - 저수준 타입의 요청 처리 메소드 아규먼트
 *  - WebRequest, NativeWebRequest 는 스프링이 제공해주는 아규먼트. 서블릿을 살짝 감싼 상태
 *  - ServletRequest, HttpServletRequest, ServletResponse, HttpServletResponse
 *    는 서블릿 프로그래밍 레벨.
 *
 * 2. InputStream, Reader, OutputStream, Writer
 *  - 요청 본문을 읽어오거나, 응답 본문을 쓸 때 사용할 수 있는 API 이며, JAVA LEVEL
 *
 * 3. PushBuilder -> HTTP2 리소스 푸쉬에 사용합니다.
 *
 * 4. HttpMethod -> GET, POST ... 등, 요청 메소드에 대한 정보
 *
 * 5. Locale, TimeZone, ZoneId -> LocaleResolver가 분석한 요청의 Locale 정
 *
 * 애노테이션 기반
 * @PathVaraiable
 * @MatrixVariable
 * @RequestParam
 * @RequestHeader.. 등등
 */

@RestController
public class SampleController {

    @GetMapping("/events/1")
    public String events1(NativeWebRequest nativeWebRequest, WebRequest webRequest,
                         HttpServletRequest httpServletRequest, ServletRequest servletRequest){

        return "events1";
    }

    @GetMapping("/events/2")
    public String events2(InputStream inputStream, Reader reader){

        return "events2";
    }

    @GetMapping("/events/3")
    public String events3(PushBuilder pushBuilder){

        return "events3";
    }

    @GetMapping("/events/4")
    public String events4(HttpMethod httpMethod){
        System.out.println(httpMethod.toString());

        return "events4";
    }

    @GetMapping("/events/5")
    public ZoneId events5(Locale locale, TimeZone timeZone, ZoneId zoneId){
        System.out.println(locale);
        System.out.println(timeZone);
        System.out.println(zoneId);
        return zoneId;
    }

    @GetMapping("/events/set_cookie")
    public String eventsSetCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("hyunsoo", "handsome");
        //cookie.setMaxAge(-1); //음수를 지정하면 브라우저가 종료될 때 쿠키가 삭제
        //0으로 지정하면 쿠키가 삭제
        //설정안하면 세션 쿠키일걸??
        response.addCookie(cookie);
        return "eventsCookie";
    }

    @GetMapping("/events/get_cookie")
    public String eventsGetCookie(@CookieValue(value = "hyunsoo", required = false) String cookie, WebRequest request, HttpServletResponse response){
        request.getHeaderNames().forEachRemaining(name->{
            System.out.println("header-name : " + name);
            System.out.println("header-value : " + request.getHeader(name));
        });

        System.out.println("cookieValue : " + cookie);
        //타겟 쿠키에 대해 setMaxAge를 0으로 지정해주면, 쿠키를 삭제하게 됩니다.
        Cookie cookieTmp = new Cookie("hyunsoo", "handsome");
        cookieTmp.setMaxAge(0);
        response.addCookie(cookieTmp);
        return cookie;
    }

    @PostMapping("/events/form") //form을 @RequestBody로 받는 방법 -> MultiValueMap을 활용하라. 단 인코딩 안된다.
                                 //form을 @RequestParam으로 받는 방법 -> MultiValueMap을 활용하라 단 인코딩 안된다.
                                 //form을 @RequestParam으로 받으려면? -> Map을 잘 활용하자.
    public List<String> formExample(@RequestBody MultiValueMap<String, String> params, @RequestParam Map<String, String> params2, @RequestParam List<String> name){
        return name;
    }

    @GetMapping("events/header")
    public String getCookieWithHeader(@RequestHeader("Host") String host){
        return host;
    }



























}
