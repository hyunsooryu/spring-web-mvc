package me.hyunsoo.mvcboot.converters;

import me.hyunsoo.mvcboot.beans.Student;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


public class StudentConverter implements Converter<String, Student> {
    @Override
    public Student convert(String source) {
        Student student = new Student();
        student.setName(source);
        return student;
    }
}
