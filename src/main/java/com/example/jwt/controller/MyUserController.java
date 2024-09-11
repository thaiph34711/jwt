package com.example.jwt.controller;

import com.example.jwt.dto.CreateDto;
import com.example.jwt.dto.Request.MyUserRequest;
import com.example.jwt.entity.MyUser;
import com.example.jwt.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyUserController {
    @Autowired
    MyUserService myUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello Admin");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello User");
    }

    @PostMapping("/register/user")
    public ResponseEntity<?> create(@RequestBody MyUserRequest userRequest) {
        CreateDto dto = myUserService.save(userRequest);
        if (dto.isResult()) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/user/me")
    public ResponseEntity<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            System.out.println(username);
            MyUser user = myUserService.finByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tồn tại");
            }
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không ");
    }
}

