package com.example.jwt.controller;

import com.example.jwt.dto.LoginDto;
import com.example.jwt.dto.LoginResultDto;
import com.example.jwt.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    @Autowired
    private MyUserService myUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        LoginResultDto loginResultDto = myUserService.login(loginDto);
        if (loginResultDto.isResult()) {
            return new ResponseEntity<>(loginResultDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(loginResultDto, HttpStatus.UNAUTHORIZED);
    }
}
