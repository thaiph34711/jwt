package com.example.jwt.repository;

import com.example.jwt.entity.Profile;
import com.example.jwt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

//    Profile findByName(String name);
}
