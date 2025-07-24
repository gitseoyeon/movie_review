package org.example.movie_review.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long id;
    private Long movie_id;
    private String reviewer;
    private int rating;
    private String comment;
}
