package com.example.buoi9.controller;

import com.example.buoi9.config.CurrentUSer;
import com.example.buoi9.dto.UserDTO;
import com.example.buoi9.enums.EnumRole;
import com.example.buoi9.jwt.JwtUtils;
import com.example.buoi9.model.Role;
import com.example.buoi9.model.User;
import com.example.buoi9.repo.RoleRepository;
import com.example.buoi9.repo.UserRepository;
import com.example.buoi9.request.LoginRequest;
import com.example.buoi9.respone.UserRespone;
import com.example.buoi9.service.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
//import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();

        try{
            String accessToken = jwtUtils.generateTokenByUsername(userDetail.getUsername(), 60000L);
            String refreshToken = jwtUtils.generateTokenByUsername(userDetail.getUsername(), 600000L);
            return ResponseEntity.ok(new UserRespone(userDetail.getId(),
                    userDetail.getFullName(), accessToken, refreshToken, userDetail.getAuthorities()));
        } catch (Exception e) {
            return ResponseEntity.ok("Login failed : " + e.getMessage());
        }
    }
//
//    @GetMapping("/current")
//    public ResponseEntity<?> current(@Parameter(name = "principal", hidden = true)
//                                         @CurrentUSer UserDetailImpl userDetail) {
//        return ResponseEntity.ok(userRepository.getUser(userDetail));
//    }

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = roleRepository.findRoleByRoleName(EnumRole.ROLE_USER);
        user.setRole(role);
//        user = modelMapper.map(userDTO, User.class);
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        Role role = roleRepository.findRoleByRoleName(EnumRole.ROLE_USER);
//        user.setRole(role);

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/user/find/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isEmpty()) {
            return ResponseEntity.ok("Not found user has ID: " + id);
        }
        return ResponseEntity.ok(findUser.get());
    }

    @GetMapping("/admin/find-all")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
