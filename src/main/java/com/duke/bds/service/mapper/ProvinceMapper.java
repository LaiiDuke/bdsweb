package com.duke.bds.service.mapper;

import com.duke.bds.domain.Province;
import com.duke.bds.service.dto.ProvinceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Province} and its DTO {@link ProvinceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, Province> {}
