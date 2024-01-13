package com.duke.bds.service.mapper;

import com.duke.bds.domain.District;
import com.duke.bds.domain.Street;
import com.duke.bds.domain.Ward;
import com.duke.bds.service.dto.DistrictDTO;
import com.duke.bds.service.dto.StreetDTO;
import com.duke.bds.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Street} and its DTO {@link StreetDTO}.
 */
@Mapper(componentModel = "spring")
public interface StreetMapper extends EntityMapper<StreetDTO, Street> {
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    StreetDTO toDto(Street s);

    @Named("wardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WardDTO toDtoWardId(Ward ward);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
