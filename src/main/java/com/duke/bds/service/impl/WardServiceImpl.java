package com.duke.bds.service.impl;

import com.duke.bds.domain.Ward;
import com.duke.bds.repository.WardRepository;
import com.duke.bds.service.WardService;
import com.duke.bds.service.dto.WardDTO;
import com.duke.bds.service.mapper.WardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ward}.
 */
@Service
@Transactional
public class WardServiceImpl implements WardService {

    private final Logger log = LoggerFactory.getLogger(WardServiceImpl.class);

    private final WardRepository wardRepository;

    private final WardMapper wardMapper;

    public WardServiceImpl(WardRepository wardRepository, WardMapper wardMapper) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
    }

    @Override
    public WardDTO save(WardDTO wardDTO) {
        log.debug("Request to save Ward : {}", wardDTO);
        Ward ward = wardMapper.toEntity(wardDTO);
        ward = wardRepository.save(ward);
        return wardMapper.toDto(ward);
    }

    @Override
    public WardDTO update(WardDTO wardDTO) {
        log.debug("Request to update Ward : {}", wardDTO);
        Ward ward = wardMapper.toEntity(wardDTO);
        ward = wardRepository.save(ward);
        return wardMapper.toDto(ward);
    }

    @Override
    public Optional<WardDTO> partialUpdate(WardDTO wardDTO) {
        log.debug("Request to partially update Ward : {}", wardDTO);

        return wardRepository
            .findById(wardDTO.getId())
            .map(existingWard -> {
                wardMapper.partialUpdate(existingWard, wardDTO);

                return existingWard;
            })
            .map(wardRepository::save)
            .map(wardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wards");
        return wardRepository.findAll(pageable).map(wardMapper::toDto);
    }

    public Page<WardDTO> findAllWithEagerRelationships(Pageable pageable) {
        return wardRepository.findAllWithEagerRelationships(pageable).map(wardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WardDTO> findOne(Long id) {
        log.debug("Request to get Ward : {}", id);
        return wardRepository.findOneWithEagerRelationships(id).map(wardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ward : {}", id);
        wardRepository.deleteById(id);
    }

    @Override
    public Page<WardDTO> findByProvinceId(Long provinceId, Pageable pageable) {
        return wardRepository.findByProvinceId(pageable, provinceId).map(wardMapper::toDto);
    }
}
