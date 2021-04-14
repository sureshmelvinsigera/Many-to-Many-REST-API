package com.rest.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.rest.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.exception.NotFoundException;
import com.rest.api.model.Actor;
import com.rest.api.model.Movie;
import com.rest.api.repository.ActorRepository;
import com.rest.api.repository.MovieRepository;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("")
    public Movie createMovie(@Valid @RequestBody Movie movie) {
        return movieService.createMovie(movie);
    }

    @GetMapping("")
    List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable(value = "id") int movieId, @Valid @RequestBody Movie movieRequest) {
        return movieService.updateMovie(movieId, movieRequest);
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable(value = "id") int movieId) {
        return movieService.getMovie(movieId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") int movieId) {
        return movieService.deleteMovie(movieId);

    }

    @PutMapping("/{id}/actors")
    public Movie putMoviesActors(
            @Valid @PathVariable(value = "id") int movieId,
            @RequestBody HashMap<String, ArrayList<Integer>> actors) {
        return movieService.putMoviesActors(movieId, actors);
    }

    @DeleteMapping("/{id}/actors")
    public Movie deleteMoviesActors(@Valid @PathVariable(value = "id") int movieId,
                                    @RequestBody HashMap<String, ArrayList<Integer>> actors) {
        return movieService.deleteMoviesActors(movieId, actors);
    }
}
