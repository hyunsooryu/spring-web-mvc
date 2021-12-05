package me.hyunsoo.warboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 *  해당 SpringBootServerInitializer는 무슨 역할이니?
 *
 *  이 initializer는 WebApplicationInitializer를 상속 받아 만들어짐.
 *
 *   War로 배포할 수 있도록 해줌.
 *
 *   제약 사항
 *   JAR 프로젝트로 만들 수 없음. WAR 프로젝트로 만들어야만, JSP를 사용할 수 있다.
 *   JAVA -JAR로 실행할 수는 있지만, 실행가능한 JAR 파일은 지원하지 않는다.
 *   JBoss 진영의 언더토우(서블릿 컨테이너)는 JSP를 지원하지 않는다.
 *   Whitelabel 에러 페이지를 error.jsp로 오버라이딩 할 수 없다.
 *
 */

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WarBootApplication.class);
    }

}
