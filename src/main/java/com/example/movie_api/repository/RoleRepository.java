package com.example.movie_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movie_api.model.Role;
@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{
    
}
