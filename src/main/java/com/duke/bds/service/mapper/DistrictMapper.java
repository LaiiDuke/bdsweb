package com.duke.bds.service.mapper;

import com.duke.bds.domain.District;
import com.duke.bds.domain.Province;
import com.duke.bds.service.dto.DistrictDTO;
import com.duke.bds.service.dto.ProvinceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "province", source = "province", qualifiedByName = "provinceId")
    DistrictDTO toDto(District s);

    @Named("provinceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProvinceDTO toDtoProvinceId(Province province);
}
