package com.rest.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import com.rest.api.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.rest.api.repository.ActorRepository;
import com.rest.api.repository.MovieRepository;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private ActorService actorService;

    @Autowired
    public void setActorService(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping("")
    public Actor createActor(@Valid @RequestBody Actor actor) {
        return actorService.createActor(actor);
    }

    @GetMapping("")
    List<Actor> getActors() {
        return actorService.getActors();
    }

    @PutMapping("/{id}")
    public Actor updateActor(@PathVariable(value = "id") int actorId, @Valid @RequestBody Actor actorRequest) {
        return actorService.updateActor(actorId, actorRequest);

    }

    @GetMapping("/{id}")
    public Actor getActor(@PathVariable("id") int actorId) {
        return actorService.getActor(actorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable("id") int actorId) {
        actorService.deleteActor(actorId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/movies")
    public Actor putActorMovies(@Valid @PathVariable(value = "id") int actorId, @RequestBody HashMap<String, ArrayList<Integer>> movies) {
        return actorService.putActorMovies(actorId, movies);
    }

    @DeleteMapping("/{id}/movies")
    public Actor deleteActorMovies(@Valid @PathVariable(value = "id") int actorId,
                                   @RequestBody HashMap<String, ArrayList<Integer>> movies) {
        return actorService.deleteActorMovies(actorId, movies);
    }
}
