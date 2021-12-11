package me.hyunsoo.handlermethod.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class SampleDB {

    public final ConcurrentMap<String, List<String>> DB;

    public SampleDB(){
        ConcurrentMap<String, List<String>> concurrentMap = new ConcurrentHashMap<String, List<String>>();
        concurrentMap.put("categories", List.of("play","sleep","watch"));
        concurrentMap.put("countries", List.of("korea","japan","china"));
        this.DB = concurrentMap;
    }
}
