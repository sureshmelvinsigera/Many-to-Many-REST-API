package com.rest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	Movie findById(int movieId);
}
