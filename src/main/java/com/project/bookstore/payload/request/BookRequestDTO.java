package com.project.bookstore.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDTO {

    @NotNull
    private String isbn;

    private String title;
    private String author;
    private String[] authors;
    private Integer year;
    private Double price;
    private String genre;
}
