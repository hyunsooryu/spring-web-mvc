package me.hyunsoo.handlermethod.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;


/**
 * @SessionAttributes
 * 모델 정보를 HTTP 세션에 저장해주는 애노테이션 입니다.
 * 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어줍니다.
 * @ModelAttribute는 세션에 있는 데이터도 바인딩 합니다.
 * 여러 화면(또는 요청)에서 사용해야 하는 객체를 공유할 때 사용합니다.
 *
 * SessionStatus를 사용해서, 세션 처리 완료를 알려줄 수 있고, 폼 처리가 끝나고 세션을 비울 때 사용합니다..
 */

@Controller
@RequestMapping("/session")
@SessionAttributes("event")
//위에 처럼, SessionAttributes 설정을 해준다면, Model에 값을 넣을 때 Session을 통해, 넘겨줄 수 있게 된다.
public class SessionController {

    @GetMapping("/events/form")
    public String eventsForm(HttpSession httpSession, Event event){
     //   Event newEvent = new Event();
        event.setId(20132513);
        event.setName("hyunsoo");
   //     model.addAttribute("event", newEvent);
    //    httpSession.setAttribute("event", newEvent);
        return "/events/form";
    }


}
