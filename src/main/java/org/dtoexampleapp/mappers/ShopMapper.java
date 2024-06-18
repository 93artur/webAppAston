package org.dtoexampleapp.mappers;

import org.dtoexampleapp.dto.PurchaseDto;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.entity.Purchase;
import org.dtoexampleapp.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShopMapper {
    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "contactNumber", target = "contactNumber")
    ShopDto toDto(Shop shop);
    Shop toEntity(ShopDto shopDto);
}
