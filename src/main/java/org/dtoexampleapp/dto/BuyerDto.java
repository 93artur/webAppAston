package org.dtoexampleapp.dto;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyerDto {
    private int id;
    private String name;
    private int balance;
    private List<ShopDto> shops;
    private List<PurchaseDto> purchases;
}
