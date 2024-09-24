package com.example.demo.service;

import com.example.demo.model.SensorDTO;
import com.example.demo.repository.entity.SensorEntity;

import java.util.List;
import java.util.Map;

public interface SensorService {
    List<SensorDTO> getSensor(Map<String, Object> params);
    SensorDTO getSensorById(Long id);
    SensorDTO getLatestSensorData();
    void saveSensorData(SensorEntity sensorData);
}
