package me.hyunsoo.handlermethod.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ModelAttribute의 다른 용법
 * 1. @RequestMapping을 사용한 핸들러 메소드의 아규먼트에 사용하기
 *
 * 2. @Controller 또는, @ControllerAdvice(이 애노테이션은 뒤에서 다룹니다.)를 사용한 클래스에서
 *    모델 정보를 초기화 할 때 사용한다.
 *
 * 3. @RequestMapping과 같이 사용하면, 해당 메소드에서 리턴하는 객체를 모델에 넣어준다.
 *
 * @ModelAttribute의 Function에 @PathVariable을 조회해서도 사용할 수 있다.
 *
 */


@Controller
@RequiredArgsConstructor
@RequestMapping("/model/{group}")
public class ModelAttribute2Controller {

    private final SampleDB sampleDB;

    /**
    @ModelAttribute
    public void categories(Model model, @PathVariable("group") String group){
        model.addAttribute(group, sampleDB.DB.get(group));
    }
    **/
    @ModelAttribute("countries")
    public List<String> countries(@PathVariable("group") String group){
        return sampleDB.DB.get(group);
    }

    @GetMapping("/events")
    @ResponseBody
    public Map<String, List<String>> getEvent(Model model){

        List<String> categories = (List<String>)model.getAttribute("categories");
        List<String> countries = (List<String>)model.getAttribute("countries");

        categories.forEach(System.out::println);
        countries.forEach(System.out::println);

        return Map.of("countries", countries, "categories", categories);

    }

    //아래와 같이 해도, countries 정보가 모델에박히게 됩니다.. 개 신기하네요!!!
    @GetMapping("/countries")
    @ResponseBody
    public List<String> getCountries(@ModelAttribute("countries") List<String> countries){
        return countries;
    }

    @GetMapping("/categories")
    @ResponseBody
    public List<String> getCategories(@ModelAttribute("categories") List<String> countries){
        return countries;
    }
}
