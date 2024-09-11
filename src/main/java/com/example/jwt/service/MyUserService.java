package com.example.jwt.service;

import com.example.jwt.dto.CreateDto;
import com.example.jwt.dto.LoginDto;
import com.example.jwt.dto.LoginResultDto;
import com.example.jwt.dto.Request.MyUserRequest;
import com.example.jwt.entity.MyUser;
import com.example.jwt.entity.Profile;
import com.example.jwt.entity.Role;
import com.example.jwt.jwt.JwtTokenProvider;
import com.example.jwt.repository.ProfileRepository;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public CreateDto save(MyUserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return new CreateDto(false,"trung ten ");
        }
        MyUser user = new MyUser();
        user.setUsername(userRequest.getUsername());
        user.setPassworld(passwordEncoder.encode(userRequest.getPassworld()));
        Role role = roleRepository.findByRole("USER").orElseThrow(
                () ->  new  RuntimeException("khong co quyen han")
        );
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);
        Profile profile = new Profile();
        user.setProfile(profile);
        profileRepository.save(profile);
        userRepository.save(user);

        return new CreateDto(true,"Thanh Cong");
    }
    public LoginResultDto login(LoginDto loginDto) {
        MyUser user1 = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        if(user1==null){
            LoginResultDto dto=new LoginResultDto();
            dto.setResult(false);
            dto.setMessage("Ko ton tai");
            return dto;
        }
        boolean ok = passwordEncoder.matches(loginDto.getPassworld(), user1.getPassworld());
        if (!ok) {
            LoginResultDto dto=new LoginResultDto();
            dto.setResult(false);
            dto.setMessage("sai mat khau");
            return dto;
        }
        LoginResultDto dto=new LoginResultDto();
        dto.setResult(true);
        dto.setMessage(jwtTokenProvider.generateToken(loginDto));
        return dto;
    }
    public MyUser finByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
