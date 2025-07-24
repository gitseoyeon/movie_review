package org.example.movie_review.repository;

import lombok.RequiredArgsConstructor;
import org.example.movie_review.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Review> reviewRowMapper = (resultSet, rowNum) -> Review.builder()
                .id(resultSet.getLong("id"))
                .comment(resultSet.getString("comment"))
                .rating(resultSet.getInt("rating"))
                .reviewer(resultSet.getString("reviewer"))
                .movie_id(resultSet.getLong("movie_id"))
                .build();

    public int save(Review review) {
        return jdbcTemplate.update("INSERT INTO reviews (movie_id, reviewer, rating, comment) " +
                "VALUES (?, ?, ?, ?)", review.getMovie_id(), review.getReviewer(),
                review.getRating(), review.getComment());
    }
    public List<Review> findByMovieId(Long movieId) {
        String sql = "SELECT * FROM reviews WHERE movie_id = ?";

        return jdbcTemplate.query(sql, reviewRowMapper, movieId);
    }

    public Review findByMovieIdAndReviewId(Long movieId, Long reviewId) {
        String sql = "SELECT * FROM reviews WHERE movie_id = ? AND id = ?";

        return jdbcTemplate.queryForObject(sql, reviewRowMapper, movieId, reviewId);
    }

    public int update(Review review) {
        return jdbcTemplate.update(
                "UPDATE reviews SET reviewer = ?, rating = ?, comment = ? WHERE id = ?",
                review.getReviewer(), review.getRating(), review.getComment(), review.getId()
        );
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM reviews WHERE id = ?", id);
    }
}
