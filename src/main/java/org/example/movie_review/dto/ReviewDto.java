package org.example.movie_review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "평점은 최소 1점에서 최대 5점 사이로 입력 가능합니다.")
    @Max(value = 5, message = "평점은 최소 1점에서 최대 5점 사이로 입력 가능합니다.")
    private int rating;

    private String comment;

    @NotBlank(message = "평가자명을 입력해주세요")
    private String reviewer;

}
