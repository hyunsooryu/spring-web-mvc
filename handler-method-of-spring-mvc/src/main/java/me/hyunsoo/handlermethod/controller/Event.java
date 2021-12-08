package me.hyunsoo.handlermethod.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.hyunsoo.handlermethod.annotations.MyAnnotation;

@MyAnnotation
@Getter
@Setter
@Builder
public class Event {
    private long id;
    private String name;
}
