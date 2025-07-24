package org.example.movie_review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.movie_review.dto.ReviewDto;
import org.example.movie_review.model.Review;
import org.example.movie_review.repository.MovieRepository;
import org.example.movie_review.repository.ReviewRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping("/{movieId}/reviews")
    public String list(@PathVariable Long movieId, Model model) {
        model.addAttribute("movie", movieRepository.findById(movieId));
        model.addAttribute("reviews", reviewRepository.findByMovieId(movieId));

        return "review-list";
    }

    @GetMapping("/{movieId}/addReview")
    public String addForm(@PathVariable Long movieId, Model model) {
        model.addAttribute("movie", movieRepository.findById(movieId));
        model.addAttribute("reviewDto", new ReviewDto());
        return "review-form";
    }

    @PostMapping("/{movieId}/addReview")
    public String addReview(@PathVariable Long movieId,
                            @Valid @ModelAttribute ReviewDto reviewDto,
                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "review-list";

        Review review = Review.builder()
                .movie_id(movieId)
                .reviewer(reviewDto.getReviewer())
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .build();
        reviewRepository.save(review);

        return "redirect:/" + movieId + "/reviews";
    }

    @GetMapping("/{movieId}/editReview/{reviewId}")
    public String editForm(@PathVariable Long movieId, @PathVariable Long reviewId, Model model) {
        Review review = reviewRepository.findByMovieIdAndReviewId(movieId, reviewId);
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setReviewer(review.getReviewer());

        model.addAttribute("reviewDto", reviewDto);
        model.addAttribute("movie", movieRepository.findById(movieId));

        return "review-form";
    }

    @PostMapping("/{movieId}/editReview/{reviewId}")
    public String edit(@PathVariable Long movieId, @ModelAttribute Review review) {
        reviewRepository.update(review);
        return "redirect:/" + movieId + "/reviews";
    }

    @PostMapping("/{movieId}/deleteReview/{reviewId}")
    public String delete(@PathVariable Long movieId, @PathVariable Long reviewId) {
        reviewRepository.delete(reviewId);

        return "redirect:/" + movieId + "/reviews";
    }
}
