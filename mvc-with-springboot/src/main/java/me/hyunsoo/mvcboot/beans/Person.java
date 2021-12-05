package me.hyunsoo.mvcboot.beans;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Getter
@Setter
public class Person {
    private String name;
    private int age;
}
