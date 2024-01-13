package com.duke.bds.service.mapper;

import com.duke.bds.domain.Config;
import com.duke.bds.service.dto.ConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Config} and its DTO {@link ConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConfigMapper extends EntityMapper<ConfigDTO, Config> {}
