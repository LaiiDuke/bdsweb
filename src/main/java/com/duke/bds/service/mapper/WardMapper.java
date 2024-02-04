package com.duke.bds.service.mapper;

import com.duke.bds.domain.District;
import com.duke.bds.domain.Ward;
import com.duke.bds.service.dto.DistrictDTO;
import com.duke.bds.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ward} and its DTO {@link WardDTO}.
 */
@Mapper(componentModel = "spring")
public interface WardMapper extends EntityMapper<WardDTO, Ward> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtName")
    WardDTO toDto(Ward s);

    @Named("districtName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoDistrictName(District district);
}
