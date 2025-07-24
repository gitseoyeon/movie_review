package org.example.movie_review.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {
    private Long id;

    private String title;

    private Integer release_year;
}
