package com.duke.bds.service.impl;

import com.duke.bds.domain.PostType;
import com.duke.bds.repository.PostTypeRepository;
import com.duke.bds.service.PostTypeService;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.service.mapper.PostTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostType}.
 */
@Service
@Transactional
public class PostTypeServiceImpl implements PostTypeService {

    private final Logger log = LoggerFactory.getLogger(PostTypeServiceImpl.class);

    private final PostTypeRepository postTypeRepository;

    private final PostTypeMapper postTypeMapper;

    public PostTypeServiceImpl(PostTypeRepository postTypeRepository, PostTypeMapper postTypeMapper) {
        this.postTypeRepository = postTypeRepository;
        this.postTypeMapper = postTypeMapper;
    }

    @Override
    public PostTypeDTO save(PostTypeDTO postTypeDTO) {
        log.debug("Request to save PostType : {}", postTypeDTO);
        PostType postType = postTypeMapper.toEntity(postTypeDTO);
        postType = postTypeRepository.save(postType);
        return postTypeMapper.toDto(postType);
    }

    @Override
    public PostTypeDTO update(PostTypeDTO postTypeDTO) {
        log.debug("Request to update PostType : {}", postTypeDTO);
        PostType postType = postTypeMapper.toEntity(postTypeDTO);
        postType = postTypeRepository.save(postType);
        return postTypeMapper.toDto(postType);
    }

    @Override
    public Optional<PostTypeDTO> partialUpdate(PostTypeDTO postTypeDTO) {
        log.debug("Request to partially update PostType : {}", postTypeDTO);

        return postTypeRepository
            .findById(postTypeDTO.getId())
            .map(existingPostType -> {
                postTypeMapper.partialUpdate(existingPostType, postTypeDTO);

                return existingPostType;
            })
            .map(postTypeRepository::save)
            .map(postTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostTypes");
        return postTypeRepository.findAll(pageable).map(postTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostTypeDTO> findOne(Long id) {
        log.debug("Request to get PostType : {}", id);
        return postTypeRepository.findById(id).map(postTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostType : {}", id);
        postTypeRepository.deleteById(id);
    }
}
