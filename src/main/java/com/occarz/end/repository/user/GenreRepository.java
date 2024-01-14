package com.occarz.end.repository.user;

import com.occarz.end.entities.user.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>, CrudRepository<Genre, Integer> {
}
