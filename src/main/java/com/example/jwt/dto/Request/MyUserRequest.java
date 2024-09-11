package com.example.jwt.dto.Request;

import com.example.jwt.entity.Profile;
import com.example.jwt.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserRequest {
    private Integer id;

    private String username;

    private String passworld;

    private Set<Role> roles;

    private Profile profile;
}
