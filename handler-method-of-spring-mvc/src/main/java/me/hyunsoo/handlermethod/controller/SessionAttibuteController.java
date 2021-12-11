package me.hyunsoo.handlermethod.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/promotion")
public class SessionAttibuteController {

    @GetMapping("")
    public Map<String, String> promotionPsbl(@SessionAttribute LocalDateTime visitTime){
        Map<String, String> map = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        long betweenSecond = (ChronoUnit.SECONDS.between(visitTime, localDateTime));
        map.put("현재시간", localDateTime.toString());
        map.put("최초방문 시간", visitTime.toString());
        if(betweenSecond <= 60){
            map.put("프로모션 참여 가능 여부", "Y");
        }else{
            map.put("프로모션 참여 가능 여부", "N");
        }
        return map;
    }

    @GetMapping("/retry")
    public Map<String, String> promotionInit(HttpSession session){
        Map<String, String> map = new HashMap<>();
        session.removeAttribute("visitTime");
        map.put("세션 해제 여부", "해제 성공");
        return map;
    }
}
