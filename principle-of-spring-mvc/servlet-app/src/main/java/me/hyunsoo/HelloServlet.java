package me.hyunsoo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 1. 서블릿 애플리케이션 개발
 *
 * dependency에서
 *   <artifactId>javax.servlet-api</artifactId>
 *   <scope>provided</scope>
 * scope이 provided인 이유는, 코딩 할때만 이 의존성을 쓰고,
 * 이후에는 Container에게 제공을 받을 것이기 때문입니다.
 *
 * 서블릿 독자적으로 실행할 수 있는 방법은 없고,
 * 서블릿 컨테이너가 꼭 필요하다.
 *
 * war exploded vs war
 *
 * war를 압축을 풀어헤친 상태로 배포하는 방법 VS 압축을 풀지 않고 배포하는 방법
 *
 *
 * 해당 서블릿을 쓰기 위해서는,
 * web.xml에 (1)서블릿을 등록하고, (2)서블릿 매핑도 해주어야 한다.
 *
 *
 *
 * 2. 서블릿 리스터와 필터
 *
 * 1)서블릿 리스너?
 * 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고, 각 이벤트에 특별한 작업이 필요한 경우에 사용할 수 있다.
 * 서블릿 컨텍스트 수준의 이벤트
 *  컨텍스트 라이프사이클 이벤트
 *  컨텍스트 애트리뷰트 변경 이벤트
 * 세션 수준의 이벤트
 *  세션 라이프사이클 이벤트
 *  세션 애트리뷰트 변경 이벤트
 *
 * 2)서블릿 필터?
 * 들어오는 요청을 서블릿으로 보내고, 또 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 처리가 필요한 경우 사용할 수 있다.
 * 체인 형태의 구조를 갖는다.
 *
 * ServletContainer -> Filter A -> Filter B -> Servlet [Request]
 * ServletContainer <- Filter A <- Filter B <- Servlet [Response]
 *
 *
 * 3. 스프링 IOC 컨테이너 연동
 *
 *  <listener>
 *     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
 *  </listener>
 *
 *  스프링에서 ApplicationContext를 ServletContext 생명주기에 맞게 등록 시켜주는 리스너입니다.
 *  !!!
 *  즉, 스프링 설정파일이 있어야 하고, Listener가 등록시킬 ApplicationContext를 만들어줘야 합니다.
 *
 *  컨텍스트 파람으로 AnnotationConfigApplicationContext와, 그 설정클래스를 넘겨줘야 합니다.
 *
 *  servletContext가 생성 되는 시점에
 *  ContextLoaderListener -> initWebApplicationContext(servletContext)
 *
 *  contextInitialized(ServletContextEvent event)
 *  서블릿 컨텍스트가 초기화 되는 시점에 -> initWebApplicationContext() 하고 등록 되어집니다.
 *
 *  여기서 문제! 요청마다 서블릿을 각각 등록하고, 매핑하면 너무 힘들것 같다!
 *
 *  그래서 나온것이 DispatcherServlet
 *
 *  이 DispatcherServlet(FrontController)가 요청을 다 받아서, 분배하는 패턴이 어떨까?
 *
 *  바로, 스프링 MVC가 제공하는 DispatcherServlet을 활용하면 된다.
 *
 *  DispatcherServlet은 ServletContext에 등록되어있는 RootWebApplicationContext를 상속받는
 *  ApplicationContext를 하나 더 또 만듭니다. 이 때 만드는 이 ApplicationContext는 RootWebApplicationContext를
 *  부모로 삼아서 만듭니다.
 *
 *  DispatcherServlet에서 만드는 WebApplicationContext는 각각 독립적인 존재입니다.
 *
 *  RootWebApplicationContext는 즉, Web과 관련된 것들이 존재하지 않습니다.
 *
 *  DispatcherServlet이 여러개일 때, 공유해서 사용할 수 있도록, 분리가 되어있고, Web이 아닌 다른 서비스들을 갖고 있습니다.
 *
 *  DispatcherServlet이 만드는 WebApplicationContext는 Controller, ViewResolver, HandlerMapping 등
 *  해당 DispatcherServlet에 한정 된 것입니다.

 * 4. 스프링 MVC 연동
 *
 *  너무 복잡하다 싶으면,
 *
 *  RootApplicationContext를 없애고, 모든 빈을 WebApplicationContext에 등록하고 사용하는 것은 어떨까?
 *
 *  즉, ContextLoaderListener를 등록하지않고, DispatcherServlet이 생성하는, ApplicationContext만을 사용한다는 것이다.
 *
 *  이렇게 개발해도 된다. 당연히.
 *
 *
 * 5. Dispatcher Servlet
 *
 *
 * 6. 스프링 MVC 구성 요소
 *
 *
 *
 *
 */

public class HelloServlet extends HttpServlet {

    /**
     * 서블릿 처음 사용 될 때, init
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    /**
     * 요청 처리할 때
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
        resp.getWriter().println("<html><head></head><body>");
        resp.getWriter().println("<h2> HELLO, " + getName() + "</h2>");
        resp.getWriter().println("</body></html>");
    }

    /**
     * 서블릿이 파괴될 때 - 메모리에서 내려올 때
     */
    @Override
    public void destroy() {
        System.out.println("destroy");
    }

    private String getName(){
        System.out.println("I AM HELLO SERVLET");
        System.out.println(getServletContext());
        return (String)getServletContext().getAttribute("name");
    }
}
