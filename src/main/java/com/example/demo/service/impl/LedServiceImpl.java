package com.example.demo.service.impl;

import com.example.demo.builder.LedBuilder;
import com.example.demo.converter.LedBuilderConverter;
import com.example.demo.converter.LedDTOConverter;
import com.example.demo.model.LedDTO;
import com.example.demo.repository.LedRepository;
import com.example.demo.repository.entity.LedEntity;
import com.example.demo.service.LedService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LedServiceImpl implements LedService {

    @Autowired
    private LedRepository ledRepository;

    @Autowired
    private LedBuilderConverter ledBuilderConverter;

    @Autowired
    private LedDTOConverter ledDTOConverter;

    @Autowired
    private MqttClient mqttClient;

    @Override
    public List<LedDTO> getLed(Map<String, Object> params) {
        String deviceName = params.containsKey("deviceName") ? params.get("deviceName").toString() : null;
        String active = params.containsKey("active") ? params.get("active").toString() : null;

        LocalDateTime timestamp = null;
        if (params.containsKey("timestamp")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            timestamp = LocalDateTime.parse(params.get("timestamp").toString(), formatter);
        }

        List<LedEntity> ledControlEntities = ledRepository.findByParams(deviceName, active, timestamp);
        return ledDTOConverter.toLedDTOs(ledControlEntities);
    }

    @Override
    public LedDTO getLedById(Long id) {
        LedEntity ledEntity = ledRepository.findById(id).get();
        return ledDTOConverter.toLedDTO(ledEntity);
    }

    @Override
    public void saveLedData(LedEntity ledData) {
        ledRepository.save(ledData);
    }

    @Override
    public ResponseEntity<String> controlLed(String deviceName, String action) {
        try {
            // 1. Publish command to MQTT to control the LED
            MqttMessage mqttMessage = new MqttMessage(action.getBytes()); // Tạo MqttMessage từ chuỗi action
            mqttClient.publish(deviceName, mqttMessage); // Gửi MqttMessage đến topic deviceName

            // 2. Create a LedEntity to save the control action
            LedEntity ledData = new LedEntity();
            ledData.setDeviceName(deviceName);
            ledData.setActive(action.equalsIgnoreCase("on") ? "on" : "off");
            ledData.setTimestamp(LocalDateTime.now());

            // 3. Save the LED control data to the database
            saveLedData(ledData);

            // Log the control action
            System.out.println("Successfully sent control message and saved data: " + deviceName + " - " + action);

            // Return success response to Postman
            return ResponseEntity.ok("Successfully controlled : " + deviceName + " - " + action);
        } catch (MqttException e) {
            System.err.println("Failed to publish MQTT message: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to publish MQTT message: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to control: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to control : " + e.getMessage());
        }
    }

}
