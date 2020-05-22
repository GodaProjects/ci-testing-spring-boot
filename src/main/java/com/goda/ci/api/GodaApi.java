package com.goda.ci.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/")
public class GodaApi {

    @GetMapping
    public Map<String, Object> getHelloGodaApi(){
        Map<String, Object> godaMap = new HashMap<>();
        godaMap.put("msg","Hi from Goda!");
        return godaMap;
    }
}
