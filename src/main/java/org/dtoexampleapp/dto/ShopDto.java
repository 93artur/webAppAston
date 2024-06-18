package org.dtoexampleapp.dto;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopDto {
    private int id;
    private String name;
    private int contactNumber;
    private List<BuyerDto> buyers;
}
