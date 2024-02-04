package com.duke.bds.service.mapper;

import com.duke.bds.domain.Category;
import com.duke.bds.domain.District;
import com.duke.bds.domain.Post;
import com.duke.bds.domain.PostType;
import com.duke.bds.domain.Province;
import com.duke.bds.domain.Street;
import com.duke.bds.domain.User;
import com.duke.bds.domain.Ward;
import com.duke.bds.service.dto.CategoryDTO;
import com.duke.bds.service.dto.DistrictDTO;
import com.duke.bds.service.dto.PostDTO;
import com.duke.bds.service.dto.PostTypeDTO;
import com.duke.bds.service.dto.ProvinceDTO;
import com.duke.bds.service.dto.StreetDTO;
import com.duke.bds.service.dto.UserDTO;
import com.duke.bds.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "type", source = "type", qualifiedByName = "postTypeName")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "province", source = "province", qualifiedByName = "provinceName")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtName")
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardName")
    @Mapping(target = "street", source = "street", qualifiedByName = "streetName")
    PostDTO toDto(Post s);

    @Named("postTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PostTypeDTO toDtoPostTypeName(PostType postType);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("provinceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProvinceDTO toDtoProvinceName(Province province);

    @Named("districtName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoDistrictName(District district);

    @Named("wardName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WardDTO toDtoWardName(Ward ward);

    @Named("streetName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StreetDTO toDtoStreetName(Street street);
}
