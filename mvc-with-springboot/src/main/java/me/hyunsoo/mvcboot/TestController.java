package me.hyunsoo.mvcboot;

import lombok.Getter;
import me.hyunsoo.mvcboot.beans.Person;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    //MediaType -> APPLICATION_FORM_URLENCODED_VALUE

    @PostMapping(value = "/test", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public Map<String,String> getTest(@RequestBody MultiValueMap<String, String> box){

        Map<String,String> map = new HashMap<String, String>();

        box.forEach((k, v)->{
            System.out.println("key : " + k);
            v.forEach(System.out::println);
            map.putIfAbsent(k, v.get(0));
        });

        return map;
    }

    @PostMapping(value = "/test2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> getTest2(@RequestParam Map<String,String> map){

        map.forEach((k, v)->{
            System.out.println(k + " " + v);
        });
        return map;
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public Person personXml(){
        Person person = new Person();
        person.setAge(19);
        person.setName("hyunsoo");
        return person;
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person personJson(){
        Person person = new Person();
        person.setAge(19);
        person.setName("hyunsoo");
        return person;
    }

    @PostMapping(value="/save")
    ResponseEntity save(){
        System.out.println("save");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/redirect_self")
    ResponseEntity redirect(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/here"));
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).headers(httpHeaders).build();
    }

    @GetMapping("/here")
    public String here(@RequestParam("name") String name){
        return name;
    }

}
