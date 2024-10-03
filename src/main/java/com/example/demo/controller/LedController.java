package com.example.demo.controller;

import com.example.demo.model.LedDTO;
import com.example.demo.service.LedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LedController {

    @Autowired
    private LedService ledService;

    @GetMapping("/led")
    public ResponseEntity<Map<String, Object>> getLed(@RequestParam Map<String, Object> params) {
        Map<String, Object> result = ledService.getLed(params);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/led/{id}")
    public LedDTO getLedById(@PathVariable Long id) {
        return ledService.getLedById(id);
    }

    @PostMapping("/led/control")
    public ResponseEntity<String> ledControl(@RequestBody LedDTO ledDTO) {
        ledService.controlLed(ledDTO.getDeviceName(), ledDTO.getActive());
        return ResponseEntity.ok("Successfully controlled : " + ledDTO.getDeviceName() + " - " + ledDTO.getActive());
    }
}
