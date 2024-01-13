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
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    WardDTO toDto(Ward s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
