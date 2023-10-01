package ru.chuldum.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product") // данная аннотация нужна для mongoDB; типа сущность для таблицы, но документ для mongoDB
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {
    // Необходимая аннотация для обозначения сущности для БД
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
