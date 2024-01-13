package com.duke.bds.service.mapper;

import com.duke.bds.domain.Image;
import com.duke.bds.domain.Post;
import com.duke.bds.service.dto.ImageDTO;
import com.duke.bds.service.dto.PostDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {
    @Mapping(target = "post", source = "post", qualifiedByName = "postId")
    ImageDTO toDto(Image s);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);
}
