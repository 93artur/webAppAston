package org.dtoexampleapp.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerShop {
    private int id;
    private int buyerId;
    private int shopId;
}
