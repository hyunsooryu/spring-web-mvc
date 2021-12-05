package me.hyunsoo.mvcboot;

import me.hyunsoo.mvcboot.beans.Criteria;
import me.hyunsoo.mvcboot.beans.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/student/{student}")
    public String getStudent(@PathVariable Student student){
        return "Hello " + student.getName();
    }

    @GetMapping("/criteria_test")
    public Criteria getCriteria(Criteria criteria){
        return criteria;
    }
}
