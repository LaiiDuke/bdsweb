package com.duke.bds.service.mapper;

import com.duke.bds.domain.PostType;
import com.duke.bds.service.dto.PostTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostType} and its DTO {@link PostTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostTypeMapper extends EntityMapper<PostTypeDTO, PostType> {}
