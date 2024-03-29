package ru.chuldum.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

// ProductResponse - dto класс для исходящих ответов

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
