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
    @Mapping(target = "type", source = "type", qualifiedByName = "postTypeId")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "province", source = "province", qualifiedByName = "provinceId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardId")
    @Mapping(target = "street", source = "street", qualifiedByName = "streetId")
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

    @Named("provinceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProvinceDTO toDtoProvinceId(Province province);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("wardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WardDTO toDtoWardId(Ward ward);

    @Named("streetId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StreetDTO toDtoStreetId(Street street);
}
