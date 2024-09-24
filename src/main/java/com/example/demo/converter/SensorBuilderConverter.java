package com.example.demo.converter;

import com.example.demo.builder.SensorBuilder;
import com.example.demo.utils.MapUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SensorBuilderConverter {
    public SensorBuilder toSensorBuilder(Map<String, Object> params) {
        SensorBuilder sensorBuilder = new SensorBuilder.Builder()
                .setHumidity(MapUtil.getObjcet(params, "humidity", Integer.class))
                .setTemperature(MapUtil.getObjcet(params, "temperature", Integer.class))
                .setLight(MapUtil.getObjcet(params, "light", Integer.class))
                .setTimestamp(MapUtil.getObjcet(params, "timestamp", LocalDateTime.class))
                .build();
        return sensorBuilder;
    }
}
