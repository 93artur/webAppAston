package org.dtoexampleapp.mappers;

import org.dtoexampleapp.dto.PurchaseDto;
import org.dtoexampleapp.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "buyerId", target = "buyerId")
    PurchaseDto toDto(Purchase purchase);
    Purchase toEntity(PurchaseDto purchaseDto);
}
