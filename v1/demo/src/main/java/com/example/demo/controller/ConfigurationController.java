package com.example.demo.controller;

import com.example.demo.entity.Configuration;
import com.example.demo.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/GetAllConfigs")
    public List<Configuration> GetAllConfigs(){
        return configurationService.getAllConfigurations();
    }

    @GetMapping("/GetConfigByID")
    public Configuration GetConfigByID(@PathVariable Long configID){
        return configurationService.getConfigurationByID(configID);
    }

    @PostMapping("/CreateNewConfig")
    public ResponseEntity<Configuration> CreateNewConfig(@RequestBody Configuration configuration){
        Configuration createNewConfig = configurationService.createNewConfiguration(configuration);
        return ResponseEntity.status(201).body(createNewConfig);
    }

    @DeleteMapping("/DeleteConfigByID")
    public void DeleteConfigByID(@PathVariable Long configID){
        configurationService.deleteConfigurationByID(configID);
    }
}
