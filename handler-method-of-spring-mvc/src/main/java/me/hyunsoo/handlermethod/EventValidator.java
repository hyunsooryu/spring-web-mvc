package me.hyunsoo.handlermethod;

import me.hyunsoo.handlermethod.controller.Event;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class EventValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event)target;
        if(event.getId() == 931008){
            errors.rejectValue("id", "hyunsoo", "event id should not be like hyunsoo's birth day");
        }
        if("hyunsoo".equals(event.getName())){
            errors.rejectValue("name", "hyunsoo", "event name should not be like hyunsoo's name");
        }
        return;
    }
}
