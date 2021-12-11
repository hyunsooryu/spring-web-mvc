package me.hyunsoo.handlermethod.controller;

import lombok.*;
import me.hyunsoo.handlermethod.annotations.MyAnnotation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@MyAnnotation
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Event {

    interface ValidateId {}
    interface ValidateName {}

    @Min(value = 5, groups = ValidateId.class)
    private long id;

    @NotBlank(groups = ValidateName.class)
    private String name;
}
