package com.example.movie_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import com.example.movie_api.model.Role;
import com.example.movie_api.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getUserList() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); 
        assertThat(response.getBody().length).isEqualTo(3); 
    }

    @Test//uri is null again ->
    @DirtiesContext
    void createUser() {
        User user = new User("user", "user", "user@gmail", "user121");   
        URI new_user_location = restTemplate.postForLocation("/users?role_id=1", user, Void.class);
        ResponseEntity<User> response = restTemplate.getForEntity(new_user_location, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getBody().getDisplayname()).isEqualTo(user.getDisplayname());
        assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DirtiesContext
    void updateUser() {
        User user = new User("haw", "sho", "sho@gmail.com", "sho121");
        restTemplate.put("/users/1", user);
        ResponseEntity<User> response = restTemplate.getForEntity("/users/1", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getBody().getDisplayname()).isEqualTo(user.getDisplayname());
        assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getBody().getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @DirtiesContext
    void updateUserWithRelationship() {
        User user = restTemplate.getForObject("/users/1", User.class);
        Role role = restTemplate.getForObject("/roles/2", Role.class);
        user.setRole(role);
        restTemplate.put("/users/1", user);
        ResponseEntity<User> response = restTemplate.getForEntity("/users/1", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getBody().getDisplayname()).isEqualTo(user.getDisplayname());
        assertThat(response.getBody().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getBody().getPassword()).isEqualTo(user.getPassword());
        assertThat(response.getBody().getRole().getName()).isEqualTo(user.getRole().getName());
        Role role2 = restTemplate.getForObject("/roles/2", Role.class);
        assertThat(role2.getUsers().size()).isEqualTo(3);
    }

    @Test
    @DirtiesContext
    void deleteUser() {
        restTemplate.delete("/users/1");
        ResponseEntity<User> response = restTemplate.getForEntity("/users/1", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Role role = restTemplate.getForObject("/roles/1", Role.class);
        assertThat(role.getUsers().size()).isEqualTo(0);
    }
}
