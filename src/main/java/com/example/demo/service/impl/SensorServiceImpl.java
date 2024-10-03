package com.example.demo.service.impl;

import com.example.demo.converter.SensorBuilderConverter;
import com.example.demo.converter.SensorDTOConverter;
import com.example.demo.model.SensorDTO;
import com.example.demo.repository.SensorRepository;
import com.example.demo.repository.entity.SensorEntity;
import com.example.demo.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
    public Map<String, Object> getSensor(Map<String, Object> params) {
        Integer temperature = params.containsKey("temperature") ? Integer.valueOf(params.get("temperature").toString()) : null;
        Integer humidity = params.containsKey("humidity") ? Integer.valueOf(params.get("humidity").toString()) : null;
        Integer light = params.containsKey("light") ? Integer.valueOf(params.get("light").toString()) : null;

        LocalDateTime timestamp = null;
        if (params.containsKey("timestamp")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            timestamp = LocalDateTime.parse(params.get("timestamp").toString(), formatter);
        }

        // Lấy pageSize và pageNumber từ params
        int pageSize = params.containsKey("pageSize") ? Integer.parseInt(params.get("pageSize").toString()) : 10;
        int pageNumber = params.containsKey("pageNumber") ? Integer.parseInt(params.get("pageNumber").toString()) : 0;

        // Xử lý sắp xếp
        String sortBy = params.containsKey("sortBy") ? params.get("sortBy").toString() : "timestamp"; // Mặc định sắp xếp theo timestamp
        String sortDirection = params.containsKey("sortDirection") ? params.get("sortDirection").toString() : "desc"; // Mặc định sắp xếp giảm dần
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        // Sử dụng Pageable để phân trang
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Gọi repository với Pageable
        Page<SensorEntity> sensorEntities = sensorRepository.findByParams(temperature, humidity, light, timestamp, pageable);

        // Chuyển đổi từ Page<SensorEntity> sang List<SensorDTO>
        List<SensorDTO> result = sensorDTOConverter.toSensorDTOs(sensorEntities.getContent());

        // Tạo một Map để trả về thông tin phân trang
        Map<String, Object> response = new HashMap<>();
        response.put("content", result);
        response.put("currentPage", sensorEntities.getNumber() + 1); // Số trang hiện tại (Pageable bắt đầu từ 0)
        response.put("totalItems", sensorEntities.getTotalElements()); // Tổng số phần tử
        response.put("totalPages", sensorEntities.getTotalPages()); // Tổng số trang
        response.put("pageSize", pageSize); // Kích thước của mỗi trang

        return response;
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
