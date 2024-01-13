package com.duke.bds.service.mapper;

import com.duke.bds.domain.User;
import com.duke.bds.domain.UserInfo;
import com.duke.bds.service.dto.UserDTO;
import com.duke.bds.service.dto.UserInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    UserInfoDTO toDto(UserInfo s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
