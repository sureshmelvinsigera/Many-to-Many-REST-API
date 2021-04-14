package com.rest.api.service;

import com.rest.api.exception.NotFoundException;
import com.rest.api.model.Actor;
import com.rest.api.model.Movie;
import com.rest.api.repository.ActorRepository;
import com.rest.api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MovieService {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;

    @Autowired
    public void setMovieRepository(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public void movieValidation(int movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie " + movieId + " Not found");
        }
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie updateMovie(int movieId, Movie movieRequest) {
        this.movieValidation(movieId);
        Movie movie = movieRepository.findById(movieId);
        movie.setTitle(movieRequest.getTitle());
        movie.setRelease_date(movieRequest.getRelease_date());
        return movieRepository.save(movie);

    }

    public Movie getMovie(int movieId) {
        this.movieValidation(movieId);
        return movieRepository.findById(movieId);

    }

    public ResponseEntity<?> deleteMovie(int movieId) {
        this.movieValidation(movieId);
        Movie movie = movieRepository.findById(movieId);
        for (Actor actor : movie.getActors())
            movie.deleteActors(actor);

        movieRepository.deleteById(movieId);
        return ResponseEntity.ok().build();
    }

    public Movie putMoviesActors(int movieId, HashMap<String, ArrayList<Integer>> actors) {
        this.movieValidation(movieId);
        ArrayList<Integer> actorIds = actors.get("actors");

        Movie movie = movieRepository.findById(movieId);
        for (int actorId : actorIds) {
            if (!actorRepository.existsById(actorId))
                throw new NotFoundException("Actor " + actorId + " Not found");

            movie.addActors(actorRepository.findById(actorId));
        }
        return movieRepository.save(movie);
    }

    public Movie deleteMoviesActors(int movieId, HashMap<String, ArrayList<Integer>> actors) {
        this.movieValidation(movieId);
        ArrayList<Integer> actorIds = actors.get("actors");

        Movie movie = movieRepository.findById(movieId);
        for (int actorId : actorIds) {
            if (!actorRepository.existsById(actorId))
                throw new NotFoundException("Actor " + actorId + " Not found");

            movie.deleteActors(actorRepository.findById(actorId));
        }
        return movieRepository.save(movie);
    }
}
