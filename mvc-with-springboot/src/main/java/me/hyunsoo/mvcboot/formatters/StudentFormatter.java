package me.hyunsoo.mvcboot.formatters;

import me.hyunsoo.mvcboot.beans.Student;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

public class StudentFormatter implements Formatter<Student> {

    @Override
    public Student parse(String text, Locale locale) throws ParseException {
        Student student = new Student();
        student.setName(text);
        return student;
    }

    @Override
    public String print(Student object, Locale locale) {
        return object.getName();
    }
}
