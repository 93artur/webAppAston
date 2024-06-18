package org.dtoexampleapp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private int id;
    private String name;
    private int buyerId;
}
