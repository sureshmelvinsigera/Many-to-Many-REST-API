package com.rest.api.service;

import com.rest.api.controller.ActorController;
import com.rest.api.exception.NotFoundException;
import com.rest.api.model.Actor;
import com.rest.api.repository.ActorRepository;
import com.rest.api.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    MovieRepository movieRepository;
    Logger logger = LoggerFactory.getLogger(ActorController.class);

    public void actorValidation(int actorId) {
        if (!actorRepository.existsById(actorId)) {
            throw new NotFoundException("actor " + actorId + " Not found");
        }
    }

    public Actor createActor(@Valid @RequestBody Actor actor) {
        logger.info("{}", actor);
        return actorRepository.save(actor);
    }

    public List<Actor> getActors() {
        return actorRepository.findAll();
    }

    public Actor updateActor(int actorId,Actor actorRequest) {
        this.actorValidation(actorId);
        Actor actor = actorRepository.findById(actorId);
        actor.setAge(actorRequest.getAge());
        actor.setName(actorRequest.getName());
        actor.setGender(actor.getGender());
        return actorRepository.save(actor);
    }

    public Actor getActor(int actorId) {
        actorValidation(actorId);
        return actorRepository.findById(actorId);
    }

    public ResponseEntity<?> deleteActor(int actorId) {
        this.actorValidation(actorId);

        actorRepository.deleteById(actorId);
        return ResponseEntity.ok().build();
    }

    public Actor putActorMovies(int actorId, HashMap<String, ArrayList<Integer>> movies) {
        this.actorValidation(actorId);
        ArrayList<Integer> movieIds = movies.get("movies");

        Actor actor = actorRepository.findById(actorId);
        for (int movieId : movieIds) {
            if (!movieRepository.existsById(movieId))
                throw new NotFoundException("Movie " + movieId + " Not found");

            actor.addMovies(movieRepository.findById(movieId));
        }
        return actorRepository.save(actor);
    }

    public Actor deleteActorMovies(int actorId, HashMap<String, ArrayList<Integer>> movies) {
        ArrayList<Integer> movieIds = movies.get("movies");
        this.actorValidation(actorId);
        Actor actor = actorRepository.findById(actorId);
        for (int movieId : movieIds) {
            if (!movieRepository.existsById(movieId))
                throw new NotFoundException("Movie " + movieId + " Not found");
            actor.deleteMovies(movieRepository.findById(movieId));
        }
        return actorRepository.save(actor);
    }
}
