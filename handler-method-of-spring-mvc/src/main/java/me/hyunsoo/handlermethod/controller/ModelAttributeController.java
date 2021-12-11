package me.hyunsoo.handlermethod.controller;


import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * ModelAttribute 바로 옆에, BindingResult 가 있다면, BindingException 400을 바로 발생시켜 던지지않고,
 * 바인딩 에러를 직접 다룰 수 있다. BindingResult 타입의 아규먼트를 ModelAttribute 바로 오른쪽에 추가한다.
 *
 * 바인딩 이후에 검증 작업을 추가로 하고 싶다면?
 * !!바인딩 이후에 검증 하는 작업입니다..
 * @Valid 나, @Validated를 활용한다.
 *
 * @Valid vs @Validated ?
 *
 * @Validated는 스프링이 제공하는 애노테이션으로, 그룹 클래스를 설정할 수 있다. 즉 어떤 Validation 그룹으로 검증하겠다.
 * 이런 설정을 만들어 낼 수 있다. @Valid는 할 수 없다.
 *
 *
 */
@RestController
@RequestMapping("/model")
public class ModelAttributeController {

    @PostMapping("/event")
    public Event getEvent(@Validated(Event.ValidateName.class) @ModelAttribute Event event, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.toString());
            });
        }
        return event;
    }
}
