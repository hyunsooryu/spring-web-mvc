package me.hyunsoo.handlermethod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prg")
public class PRG_Controller {

    private List<Event> eventList;

    public PRG_Controller(){
        this.eventList = new ArrayList<>();
        eventList.add( Event.builder().id(1).name("name-1").build());
        eventList.add( Event.builder().id(2).name("name-2").build());
        eventList.add( Event.builder().id(3).name("name-3").build());
        eventList.add( Event.builder().id(4).name("name-4").build());
        eventList.add( Event.builder().id(5).name("name-5").build());
    }

    @GetMapping(value = "/events/form")
    public String events(Event event){
        return "events/form";
    }

    @PostMapping(value = "/events/form")
    public String events_proc(Event event){
        //조건 거르고
        this.eventList.add(event);
        return "redirect:/prg/events";
    }

    @GetMapping(value = "/events")
    public String eventList(Model model){
        model.addAttribute("events", this.eventList);
        return "events/events_list";
    }


}
