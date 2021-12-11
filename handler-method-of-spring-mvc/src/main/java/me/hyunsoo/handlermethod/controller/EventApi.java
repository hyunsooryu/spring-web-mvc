package me.hyunsoo.handlermethod.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @RequestBody도 Validation을 활용할 수 있다..
 *
 * HttpMessageConverters -> 메시지 컨버터 추가 하여 설정할 수 있는 방법
 * WebMvcConfigurer
 *  extendMessageConverters : 메시지 컨버터에 추가
 *  configureMessageConverters : 기본 메시지 컨버터를 대체 / 기본것을 아예 안쓰게 됩니다.
 *
 * @EnableWebMvc -> DeligatingWebMvcConfigurationSupport -> 부트의 기본 MVC 설정 사용 불가
 *
 *
 *  HttpEntity<> 를 활용해서도 contents 를 받아 낼 수 있다. -> 이를 사용할 시, 헤더 값의 내용도 찾아낼 수 있다.
 *
 *
 *
 *
 * @ResponseBody vs ResponseEntity
 *
 * 응답 헤더 상태 코드 본문을 직접 다루고 싶다면, ResponseEntity 를 활용하는게 좋다.
 *

 */
@Slf4j
@RestController
@RequestMapping("/api")
public class EventApi {

    @PostMapping("/events")
    public Event createEvent( HttpEntity<Event> event) throws BindException {
        //bindingErrorMessage(bindingResult);

        return event.getBody();
    }

    private static void bindingErrorMessage(BindingResult result) throws BindException {
        if(!result.hasErrors())return;
        result.getAllErrors().stream().forEach(
                objectError -> {
                    if(objectError.getCodes() != null && objectError.getCodes().length > 0) {
                        log.error(objectError.getCodes()[0]);
                    }
                }
        );
        throw new BindException(result);
    }

    @ExceptionHandler
    @ResponseBody
    ResponseEntity exceptionHandler(BindException exception){
        log.error("exception 발생  ");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
