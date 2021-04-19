package com.example.demo.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookMinimalDto {
    private String name;
    private String author;
    private String genre;
    private Integer quantity;
    private Double price;
}
