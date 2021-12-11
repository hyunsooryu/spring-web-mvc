package me.hyunsoo.handlermethod.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validate-form")

/**
 * 바인딩 에러 발생 시 Model에 담기는 정보는?
 * Event, BindingResult.Event
 *
 *
 *
 *
 *
 */
public class FormSubmitWithValidationController {

    @PostMapping("/event")
    public String validateEvent(@Validated(Event.ValidateName.class) Event event){
        return "events/form";
    }


}
