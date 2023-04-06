package ru.vitasoft.adminrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.adminrest.entity.Role;
import ru.vitasoft.adminrest.entity.User;
import ru.vitasoft.adminrest.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AdminController {

    @Autowired
    protected UserRepository userRepository;

    @PutMapping("/admin/users/{id}")
    public ResponseEntity setOperatorRole(@PathVariable(value = "id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            User saved = user.get();
            saved.getRoles().add(Role.OPERATOR);
            userRepository.save(saved);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers(@RequestParam(name = "name", required = false) String name) {
        List<User> users;
        if (!Objects.isNull(name)){
            users = userRepository.findByNameContaining(name);
        }else {
            users = (List<User>) userRepository.findAll();
        }
        return users;
    }
}