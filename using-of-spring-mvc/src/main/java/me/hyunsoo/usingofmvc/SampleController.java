package me.hyunsoo.usingofmvc;


import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    /**
     * 메소드를 지정하지 않으면, 모든 메소드 요청에 대해 맵핑이 된다.
     *
     * method 가 지정되어있을 때, 다른 method 로 요청이 들어온다면, 405 Method Not Supported 에러가 나게 된다.
     *
     * 아래와 같은 리퀘스트 매핑 조합도 사실 사용할 수 있다.
     *
     * ? 한글자
     * * 여러글자
     * ** 여러 패스
     *
     * 또한 정규식 표현으로도 맵핑할 수 있습니다.
     *
     *
     *
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    @GetMapping(value = "/author???")
    public String getAuthor(){
        return "HELLO";
    }

    /**
    //정규식으로 표현 // PathVariable이며 [a-z] 여야만 합니다.
    @GetMapping(value = "/{name:[a-z]+}")
    public String getName(@PathVariable String name){
        return name;
    }
     **/


    /**
     * 헤더에 특정 값이 없어야 하는 경우, 이런식으로 매핑 해줄 수 도 있습니다.
     *
     * **/
    @GetMapping(value="/bye", headers = "!" + HttpHeaders.FROM)
    public String headerAcceptTest(){
        return "bye";
    }

    /**
     * 파라미터에 특정 값이 있어야 합니다. 매핑이 되어야 합니다.
     *
     * @return
     */

    @GetMapping(value = "/good", params = "name=spring")
    public String goodNameTest(){
        return "hello";
    }

    /**
     * head 요청일 경우 응답 본문을 뺀, 헤더만 받아온다. 참 웃긴게 ContentLength는 또 알 수 있다.
     *
     * Options 는 사용할 수 있는 Http 메소드를 제공하며, 서버 또는 특정 리소스가 제공하는 기능을 확인한다.
     *
     * 서버는 ALLOW 응답 헤더에 사용할 수 있는 HTTP 메소드를 제공해야 한다.
     *
     * 기본으로 제공이 되고 있습니다. 우리가 직접 제공할 필요가 없이 스프링 MVC에 이미 만들어져 있습니다.
     *
     */
}
