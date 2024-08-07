package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Role;
import com.example.movie_api.model.User;
import com.example.movie_api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    public User createUser(Long roleId, User user) {
        Role role = roleService.getRole(roleId);
        user.setRole(role);
        return userRepository.save(user);
    }

    public void updateUser(Long userId, User updatedUser) {
        User user = getUser(userId);
        updatedUser.setId(user.getId());
        if(updatedUser.getRole() == null){
            updatedUser.setRole(user.getRole());
        }
        userRepository.save(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = getUser(userId);
        Role role = user.getRole();
        role.getUsers().remove(user);
        roleService.updateRole(role.getId(), role);
        userRepository.deleteById(userId);
    }
}
