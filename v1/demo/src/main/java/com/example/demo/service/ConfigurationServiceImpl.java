package com.example.demo.service;

import com.example.demo.entity.Configuration;
import com.example.demo.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService{

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public List<Configuration> getAllConfigurations(){
        return configurationRepository.findAll();
    }

    @Override
    public Configuration getConfigurationByID(Long configID){
        return configurationRepository.findById(configID).orElse(null);
    }

    @Override
    public Configuration createNewConfiguration(Configuration configuration){
        return configurationRepository.save(configuration);
    }

    @Override
    public void deleteConfigurationByID(Long configID){
        configurationRepository.deleteById(configID);
    }
}
