package com.example.auth.rest;

import com.example.auth.domain.User;
import com.example.auth.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    private ResponseEntity<?> get(Principal principal) {
        return ResponseEntity.ok(userService.loadUserByUsername(principal.getName()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    private ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.of(userService.get(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{page}/{size}")
    private ResponseEntity<?> get(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    private ResponseEntity<?> save(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("denyAll()")
    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
