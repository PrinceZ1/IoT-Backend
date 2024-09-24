package com.example.demo.service.impl;

import com.example.demo.converter.SensorBuilderConverter;
import com.example.demo.converter.SensorDTOConverter;
import com.example.demo.model.SensorDTO;
import com.example.demo.repository.SensorRepository;
import com.example.demo.repository.entity.SensorEntity;
import com.example.demo.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDTOConverter sensorDTOConverter;
    @Autowired
    private SensorBuilderConverter sensorBuilderConverter;

    @Override
    public List<SensorDTO> getSensor(Map<String, Object> params) {
        Integer temperature = params.containsKey("temperature") ? Integer.valueOf(params.get("temperature").toString()) : null;
        Integer humidity = params.containsKey("humidity") ? Integer.valueOf(params.get("humidity").toString()) : null;
        Integer light = params.containsKey("light") ? Integer.valueOf(params.get("light").toString()) : null;

        LocalDateTime timestamp = null;
        if (params.containsKey("timestamp")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            timestamp = LocalDateTime.parse(params.get("timestamp").toString(), formatter);
        }

        List<SensorEntity> sensorEntities = sensorRepository.findByParams(temperature, humidity, light, timestamp);
        List<SensorDTO> result = sensorDTOConverter.toSensorDTOs(sensorEntities);
        return result;
    }

    @Override
    public SensorDTO getSensorById(Long id) {
        SensorEntity sensorEntity = sensorRepository.findById(id).get();
        return sensorDTOConverter.toSensorDTO(sensorEntity);
    }

    @Override
    public SensorDTO getLatestSensorData() {
        // Lấy dữ liệu entity mới nhất từ repository
        SensorEntity latestEntity = sensorRepository.findTopByOrderByTimestampDesc();
        sensorDTOConverter.toSensorDTO(latestEntity);

        return sensorDTOConverter.toSensorDTO(latestEntity);
    }

    @Override
    public void saveSensorData(SensorEntity sensorData) {
        sensorRepository.save(sensorData);
    }
}
