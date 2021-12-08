package me.hyunsoo.handlermethod.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 요청 매개변수에 들어있는 단순 타입 데이터를 메소드 아규먼트로 받아올 수 있다.
 * 1. query param 이나, 2. http 요청 본문에 form data 로 보내져 올 때, -> Servlet이 요청 매개변수로 처리하게 된다.
 *
 * @RequestParam(1.url query params or 2. http 요청 본문의 key value 쌍의 Form Data)
     * 1.required=false 또는 Optional을 활용해서 부가적인 값으로 설정할 수도 있다.
     * 2.String이 아닌 값들은 타입 컨버전을 지원하며,
     * 3.Map<String,String> 또는 MultiValueMap<String,String> 을 사용하여 모든 요청 매개변수를 받아올 수도 있다.
     * 4.이 애노테이션은 생략이 가능하다.
 *
 * 복합 타입이 아닌, 단순타입이라면, 하나의 값이라면 @RequestParam이 좋은 선택이 될 수 있다.
 *
 *
 * @ModelAttribute 과 같은 경우,
 *  여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나, 해당 객체를 새로 만들 떄 사용할 수 있다.
 *  여러곳? URI 패스, 요청 매개변수, 세션 등도 가능하며, 생략이 가능합니다.
 *
 *  값을 바인딩 할 수 없는 경우에는? BindingException 발생 400 에거가 납니다..
 *
 *  바인딩 에러를 직접 다루고 싶은 경우, BindingResult 타입의 아규먼트를 "바로" 오른 쪽에 추가해줍니다.
 *
 *  바인딩 이후에 검증 작업은 @valid, @Validated 등으로 처리합니다.
 *
 */

@RestController
@RequestMapping("/param")
public class RequestParamController {

    @GetMapping("/events")
    @ResponseBody
    public Event getEvent(@RequestParam long id, @RequestParam String name){

        return Event.builder()
                .id(id)
                .name(name)
                .build();
    }

    @PostMapping("/events")
    @ResponseBody
    public Event getEventWithForm(@RequestParam long id, @RequestParam String name){

        return Event.builder()
                .id(id)
                .name(name)
                .build();
    }

    /**
     * 아래와 같은 Binding은 어떨까? Get, Post 모두 쌉 가능
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/events/map", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Event getEventWithForm(@RequestParam Map<String, String> params){

        return Event.builder()
                .id(Long.valueOf(params.get("id")))
                .name(params.get("name"))
                .build();
    }



}
