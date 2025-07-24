package org.example.movie_review.repository;

import lombok.RequiredArgsConstructor;
import org.example.movie_review.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Movie> movieRowMapper = (resultSet, rowNum) -> {
        Movie movie = Movie.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .release_year(resultSet.getInt("release_year"))
                .build();

        return movie;
    };

    public int save(Movie movie) {
        String sql = "INSERT INTO movies (title, release_year) VALUES (?, ?)";

        return jdbcTemplate.update(sql, movie.getTitle(), movie.getRelease_year());
    }

    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT * FROM movies", movieRowMapper);
    }

    public Movie findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM movies WHERE id = ?", movieRowMapper, id);
    }

    public int update(Movie movie) {
        return jdbcTemplate.update("UPDATE movies SET title = ?, release_year = ? WHERE id = ?",
                movie.getTitle(), movie.getRelease_year(), movie.getId());
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM movies WHERE id = ?", id);
    }

}
