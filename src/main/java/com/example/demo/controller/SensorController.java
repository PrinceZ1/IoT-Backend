package com.example.demo.controller;

import com.example.demo.model.SensorDTO;
import com.example.demo.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @GetMapping("/sensor")
    public List<SensorDTO> getSensor(@RequestParam Map<String, Object> params){
        return sensorService.getSensor(params);
    }
    @GetMapping("/sensor/{id}")
    public SensorDTO getSensorById(@PathVariable Long id){
        return sensorService.getSensorById(id);
    }
    @GetMapping("/sensor/latest")
    public SensorDTO getLatestSensorData() {
        return sensorService.getLatestSensorData();
    }

}
