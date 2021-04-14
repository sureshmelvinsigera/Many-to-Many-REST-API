package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
	Actor findById(int actorId);
}
