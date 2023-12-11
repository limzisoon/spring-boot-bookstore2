package com.project.bookstore.payload.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookResponseDTO {

    private String isbn;
    private String title;
    private String[] authors;
    private Integer year;
    private Double price;
    private String genre;

}
