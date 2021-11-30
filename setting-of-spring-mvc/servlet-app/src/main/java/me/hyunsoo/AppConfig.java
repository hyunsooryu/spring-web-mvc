package me.hyunsoo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@ComponentScan(excludeFilters = @ComponentScan.Filter(Controller.class))
@Configuration
public class AppConfig {
}
