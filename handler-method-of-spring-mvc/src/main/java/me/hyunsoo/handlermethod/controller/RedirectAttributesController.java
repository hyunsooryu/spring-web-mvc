package me.hyunsoo.handlermethod.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * 리다이렉트 할 때, 기본적으로 Model에 들어있는 primitive Type 데이터는, URI 쿼리 매개변수에 추가됩니다.
 * 스프링 부트에서는 이 기능이 기본적으로 비활성화 되어있으며,
 * Ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 시킬 수 있습니다.
 *
 *spring.mvc.ignore-default-model-on-redirect=false -> 프로퍼티에 설정
 *
 * @RedirectAttribute을 활용하면, spring.mvc.ignore-default-model-on-redirect true일때도,
 * 내가 원하는 값만 넘길 수 있다.
 *
 * Primitive한 타입의 데이터 말고, 복합 객체를 넘기고 싶을 때는 어떻게 하면 될까?
 *
 * RedirectAttributes.setFlashAttribute을 활용하는 것이 참 좋은 방안이 될 수 있다.
 *
 * 주로 리다이렉트 시, 데이터를 전달하는 목적으로 FlashAttribute을 사용하며,
 *
 * 1. 데이터가 URI 에 노출 되지 않는다.
 * 2. 임의의 객체를 저장할 수 있다.
 * 3. 보통 HTTP 세션을 활용한다.
 *
 * Redirect 하기 전에 데이터를 HTTP 세션에 저장하고, 리다이렉트 요청을 처리한 다음 그 즉시 제거해버린다.
 * 또한 Model에 들어가 있기 때문에, Model에서 꺼내 써도 되게 됩니다..
 *
 */

@Controller
@RequestMapping("/re")
public class RedirectAttributesController {

    @GetMapping("/hello")
    public String hello(RedirectAttributes redirectAttributes){
        //redirectAttributes.addAttribute("name", "hyunsoo");
        //redirectAttributes.addAttribute("age", 29);
        Event event = new Event();
        event.setId(12);
        event.setName("gg");
        redirectAttributes.addFlashAttribute("event", event);
        return "redirect:/re/bye";
    }

    @ResponseBody
    @GetMapping("/bye")
    public String bye(@ModelAttribute Event event, Model model){
        System.out.println(event.getName());
        System.out.println(event.getId());

        System.out.println(model.getAttribute("event"));
        return "bye";
    }
}
