package org.dtoexampleapp.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    private int id;
    private String name;
    private int balance;
}
