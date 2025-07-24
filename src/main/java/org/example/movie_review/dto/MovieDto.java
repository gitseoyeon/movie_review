package org.example.movie_review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    private Integer release_year;
}
