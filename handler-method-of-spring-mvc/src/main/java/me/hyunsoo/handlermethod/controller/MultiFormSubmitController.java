package me.hyunsoo.handlermethod.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 세션을 활용하여, 여러 폼에 걸쳐 데이터를 나눠 입력받고, 저장 하는 방식
 * 이벤트 아이디 입력받고,
 * 이벤트 이름을 입력받고
 * submit
 *
 * 1. @ModelAttribute은 세션 데이터도 바인딩을 맏는다!
 * 2. model.add("key", "value") -> key값이 @SessionAttributes("key") 로 설정 되어있다면, 세션에도 저장 된다.
 * 3. 세션 활용이 끝난 후에는 꼭 sessionStatus.setComplete() 처리를 해주어여한다.
 *
 *
 * @SessionAttribute? HTTP 세션에 들어있는 값을 참조할 때 사용합니다..
 * HttpSession을 사용할 때 비해 타입 컨버젼을 자동으로 지원하기 때문에 조금 편리합니다.
 *
 * @SessionAttributes 와는 다릅니다.
 * @SessionAttributes는 해당 컨트롤러 내에서만 동작하며, 즉 해당 컨트롤러 안에서 다루는 독점 모델 겍체를 세션에 넣고 공유할 때 사용하는 것입니다.
 * @SessionAttribute는 컨트롤러 밖(인터셉터, 필터) 등에서 만들어준 세션 데이터에 접근할 때 사용하게 됩니다.
 *
 * @SessionAttributes("key") 해당 키 값과 같은 @ModelAttribute을 참조하려 하면,
 * 세션에서 일단 찾고, 없을 때 에러를 뱉어내기 때문에, 꼭 다른 이름으로 받으셔야 합니다..
 *
 *
 */
@Controller
@RequestMapping("/multi-form")
@SessionAttributes({"event"})
public class MultiFormSubmitController {

    private List<Event> eventList = new ArrayList<>();

    @GetMapping("/id")
    public String formId(Model model){
        Event event = new Event();
        model.addAttribute("event", event);
        return "events/multi/form-id";
    }

    @PostMapping("/id")
    public String submitFormId(@ModelAttribute Event event){
        if(event.getId() == 20132513L){
            return "events/multi/form-id";
        }
        return "redirect:/multi-form/name";
    }

    @GetMapping("/name")
    public String formName(Model model, @ModelAttribute Event event){
        model.addAttribute("event", event);
        return "events/multi/form-name";
    }

    @PostMapping("/name")
    public String submitFormName(@ModelAttribute Event event, SessionStatus sessionStatus) {
        if("hyunsoo".equals(event.getName())){
            return "events/multi/form-name";
        }
        eventList.add(event);
        sessionStatus.setComplete();
        return "redirect:/multi-form/event-list";

    }

    @GetMapping("/event-list")
    public String getEventList(Model model){
        model.addAttribute("events", eventList);
        return "events/events_list";
    }

}
