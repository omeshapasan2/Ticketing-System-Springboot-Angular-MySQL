package com.example.demo.service;

import com.example.demo.entity.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigurationService {
    List<Configuration> getAllConfigurations();

    Configuration getConfigurationByID(Long configID);

    Configuration createNewConfiguration(Configuration configuration);

    void deleteConfigurationByID(Long configID);

}
