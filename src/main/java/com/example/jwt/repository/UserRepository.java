package com.example.jwt.repository;

import com.example.jwt.entity.MyUser;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Integer> {

    Optional<MyUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
