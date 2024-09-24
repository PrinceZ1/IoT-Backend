package com.example.demo.converter;

import com.example.demo.builder.LedBuilder;
import com.example.demo.utils.MapUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class LedBuilderConverter {
    public LedBuilder toLedBuilder(Map<String, Object> params){
        LedBuilder ledBuilder = new LedBuilder.Builder()
                .setActive(MapUtil.getObjcet(params, "active", String.class))
                .setDeviceName(MapUtil.getObjcet(params, "deviceName", String.class))
                .setTimestamp(MapUtil.getObjcet(params, "timestamp", LocalDateTime.class))
                .build();
        return ledBuilder;
    }
}
