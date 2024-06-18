package org.dtoexampleapp.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private int id;
    private String name;
    private int contactNumber;
}
