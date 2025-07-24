package org.example.movie_review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.movie_review.dto.MovieDto;
import org.example.movie_review.model.Movie;
import org.example.movie_review.repository.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie-list";
    }

    @GetMapping("/addMovie")
    public String showAddMovie(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "movie-form";
    }

    @PostMapping("/addMovie")
    public String addMovie(@Valid @ModelAttribute MovieDto movieDto,
                           BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return "movie-form";

        Movie movie = Movie.builder()
                .title(movieDto.getTitle())
                .release_year(movieDto.getRelease_year())
                .build();

        movieRepository.save(movie);

        return "redirect:/movies";

    }

    @GetMapping("/editMovie/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        Movie movie = movieRepository.findById(id);
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setRelease_year(movieDto.getRelease_year());

        model.addAttribute("movieDto", movieDto);

        return "movie-form";
    }

    @PostMapping("/editMovie")
    public String edit(@ModelAttribute Movie movie) {
        movieRepository.update(movie);

        return "redirect:/movies";
    }

    @PostMapping("/deleteMovie/{id}")
    public String delete(@PathVariable Long id) {
        movieRepository.delete(id);
        return "redirect:/movies";
    }
}
