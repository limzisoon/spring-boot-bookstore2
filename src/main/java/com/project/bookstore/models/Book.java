package com.project.bookstore.models;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(name = "book")
//@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Convert(attributeName = "entityAttrName", converter = StringArrayType.class)
public class Book {

    @Id
    @Column(name = "isbn", unique=true)
    private String isbn;

    @Column(name = "title")
    private String title;

    //@Type(type = "string-array")
    @Column(
            name = "authors",
            columnDefinition = "varchar[]"
    )
    private String[] authors;

    @Column(name = "year")
    private Integer year;

    @Column(name = "price")
    private Double price;

    @Column(name = "genre")
    private String genre;
}