package org.dtoexampleapp.mappers;

import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.entity.Buyer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapper {
    BuyerMapper INSTANCE = Mappers.getMapper(BuyerMapper.class);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "balance", target = "balance")
    BuyerDto toDto(Buyer buyer);
    Buyer toEntity(BuyerDto buyerDto);
}
