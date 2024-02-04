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
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardName")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtName")
    StreetDTO toDto(Street s);

    @Named("wardName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    WardDTO toDtoWardName(Ward ward);

    @Named("districtName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DistrictDTO toDtoDistrictName(District district);
}
