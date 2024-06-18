package org.dtoexampleapp.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private int id;
    private String name;
    private int buyerId;
}
