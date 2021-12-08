package me.hyunsoo.handlermethod.controller;

import lombok.RequiredArgsConstructor;
import me.hyunsoo.handlermethod.EventValidator;
import me.hyunsoo.handlermethod.exceptions.FormException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/submit")
public class FormSubmitController {

    private final Validator eventValidator;

    private final MessageSource messageSource;

    @GetMapping("/events")
    public String eventsForm(Model model){
        model.addAttribute("event", new Event(0, ""));
        return "events/form";
    }

    @PostMapping("/events")
    @ResponseBody
    public Event getEvent(@Validated @ModelAttribute Event event, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errorBox = new HashMap<>();
                    result.getAllErrors().forEach(objectError -> {
                        try {
                            String errorMessage = messageSource.getMessage(objectError.getCodes()[0], null, null);
                            errorBox.put(objectError.getCodes()[0], errorMessage);
                        } catch (Exception e) {
                            errorBox.put(objectError.getCodes()[0], objectError.getDefaultMessage());
                        }
                    });
            throw new FormException("form-error", errorBox);
        }
        return event;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(eventValidator);
    }

    @ExceptionHandler
    @ResponseBody
    public Map<String, String> exceptionHandler(FormException formException){
        Map<String, String> box = new HashMap<>();
        box.put("message", formException.getMessage());
        formException.getErrorBox().forEach(box::put);
        return box;
    }

}
