package com.duke.bds.service.impl;

import com.duke.bds.domain.Config;
import com.duke.bds.repository.ConfigRepository;
import com.duke.bds.service.ConfigService;
import com.duke.bds.service.dto.ConfigDTO;
import com.duke.bds.service.mapper.ConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Config}.
 */
@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {

    private final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private final ConfigRepository configRepository;

    private final ConfigMapper configMapper;

    public ConfigServiceImpl(ConfigRepository configRepository, ConfigMapper configMapper) {
        this.configRepository = configRepository;
        this.configMapper = configMapper;
    }

    @Override
    public ConfigDTO save(ConfigDTO configDTO) {
        log.debug("Request to save Config : {}", configDTO);
        Config config = configMapper.toEntity(configDTO);
        config = configRepository.save(config);
        return configMapper.toDto(config);
    }

    @Override
    public ConfigDTO update(ConfigDTO configDTO) {
        log.debug("Request to update Config : {}", configDTO);
        Config config = configMapper.toEntity(configDTO);
        config = configRepository.save(config);
        return configMapper.toDto(config);
    }

    @Override
    public Optional<ConfigDTO> partialUpdate(ConfigDTO configDTO) {
        log.debug("Request to partially update Config : {}", configDTO);

        return configRepository
            .findById(configDTO.getId())
            .map(existingConfig -> {
                configMapper.partialUpdate(existingConfig, configDTO);

                return existingConfig;
            })
            .map(configRepository::save)
            .map(configMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Configs");
        return configRepository.findAll(pageable).map(configMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfigDTO> findOne(Long id) {
        log.debug("Request to get Config : {}", id);
        return configRepository.findById(id).map(configMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Config : {}", id);
        configRepository.deleteById(id);
    }
}
