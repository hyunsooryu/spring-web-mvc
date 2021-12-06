package me.hyunsoo.mvcboot.eventListeners;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;


@Component
public class RequestListener implements ApplicationListener<RequestHandledEvent> {

    @Override
    public void onApplicationEvent(RequestHandledEvent event) {
        System.out.println(event.getDescription().toString());
    }
}
