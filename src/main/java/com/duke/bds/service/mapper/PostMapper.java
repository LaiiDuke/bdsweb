package com.duke.bds.service.mapper;

import com.duke.bds.domain.Category;
import com.duke.bds.domain.Post;
import com.duke.bds.domain.PostType;
import com.duke.bds.domain.User;
import com.duke.bds.service.dto.CategoryDTO;
import com.duke.bds.service.dto.PostDTO;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "type", source = "type", qualifiedByName = "postTypeId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    PostDTO toDto(Post s);

    @Named("postTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostTypeDTO toDtoPostTypeId(PostType postType);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
