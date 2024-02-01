package com.duke.bds.service.impl;

import com.duke.bds.domain.Image;
import com.duke.bds.domain.Post;
import com.duke.bds.repository.ImageRepository;
import com.duke.bds.repository.PostRepository;
import com.duke.bds.service.PostService;
import com.duke.bds.service.dto.ImageDTO;
import com.duke.bds.service.dto.PostDTO;
import com.duke.bds.service.mapper.ImageMapper;
import com.duke.bds.service.mapper.PostMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Post}.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    private final PostMapper postMapper;
    private final ImageMapper imageMapper;

    public PostServiceImpl(PostRepository postRepository, ImageRepository imageRepository, PostMapper postMapper, ImageMapper imageMapper) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.postMapper = postMapper;
        this.imageMapper = imageMapper;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public PostDTO update(PostDTO postDTO) {
        log.debug("Request to update Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    @Override
    public Optional<PostDTO> partialUpdate(PostDTO postDTO) {
        log.debug("Request to partially update Post : {}", postDTO);

        return postRepository
            .findById(postDTO.getId())
            .map(existingPost -> {
                postMapper.partialUpdate(existingPost, postDTO);

                return existingPost;
            })
            .map(postRepository::save)
            .map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable).map(postMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Long id) {
        log.debug("Request to get Post : {}", id);
        return postRepository.findById(id).map(postMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Post : {}", id);
        postRepository.deleteById(id);
    }

    @Override
    public Page<PostDTO> getVipPost(Pageable pageable) {
        Page<Post> posts = postRepository.findVipPost(pageable);
        List<Post> lstPost = posts.getContent();

        List<Image> lstImg = imageRepository.findAllByPostIn(lstPost);
        List<ImageDTO> lstImgDto = lstImg.stream().map(imageMapper::toDto).collect(Collectors.toList());

        Map<PostDTO, List<ImageDTO>> mapImagePost = lstImgDto.stream().collect(Collectors.groupingBy(ImageDTO::getPost));
        Page<PostDTO> result = posts.map(postMapper::toDto);
        result
            .getContent()
            .forEach(postDTO -> {
                if (mapImagePost.get(postDTO) != null) {
                    postDTO.setImage(mapImagePost.get(postDTO).get(0));
                }
            });
        return result;
    }

    @Override
    public Page<PostDTO> findByPostType(Pageable pageable, Long postTypeId) {
        Page<Post> posts = postRepository.findByPostTypeId(pageable, postTypeId);
        List<Post> lstPost = posts.getContent();

        List<Image> lstImg = imageRepository.findAllByPostIn(lstPost);
        List<ImageDTO> lstImgDto = lstImg.stream().map(imageMapper::toDto).collect(Collectors.toList());

        Map<PostDTO, List<ImageDTO>> mapImagePost = lstImgDto.stream().collect(Collectors.groupingBy(ImageDTO::getPost));
        Page<PostDTO> result = posts.map(postMapper::toDto);
        result
            .getContent()
            .forEach(postDTO -> {
                if (mapImagePost.get(postDTO) != null) {
                    postDTO.setImage(mapImagePost.get(postDTO).get(0));
                }
            });
        return result;
    }
}
