package com.example.movie_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movie_api.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
}
