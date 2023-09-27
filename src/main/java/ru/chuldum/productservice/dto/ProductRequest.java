package ru.chuldum.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

//
// ProductRequest - это dto класс для входящих сущностей
// @Builder используем для создания экземпляров объекта, и чтобы создать builder метод
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
}
