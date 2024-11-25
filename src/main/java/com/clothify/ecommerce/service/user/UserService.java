package com.clothify.ecommerce.service.user;

import com.clothify.ecommerce.dto.user.UserDTO;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.exception.user.UserNotFoundException;
import com.clothify.ecommerce.exception.user.UserPasswordAlreadyUsedException;
import com.clothify.ecommerce.repository.user.RoleDAO;
import com.clothify.ecommerce.repository.user.UserDAO;
import com.clothify.ecommerce.security.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final ObjectMapper mapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final RoleService roleService;

    public void persist(UserDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<UserEntity> all = userDAO.findAll();
        if (all.isEmpty()) {
            UserEntity userEntity = userDAO.save(mapper.convertValue(user, UserEntity.class));
            roleService.assignRoleForFirstUser(userEntity);
        } else {
            mapper.convertValue(userDAO.save(mapper.convertValue(user, UserEntity.class)), UserDTO.class);
        }
    }

    public UserDTO update(UserDTO userDTO) {
        UserEntity user = userDAO.findById(userDTO.getEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setUpdateDate(new Date(System.currentTimeMillis()));
        user.setPhoneNo(userDTO.getPhoneNo());
        return mapper.convertValue(userDAO.save(user), UserDTO.class);
    }

    public Boolean resetPassword(UserDTO userDTO) {
        UserEntity user = userDAO.findById(userDTO.getEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
        if (user.getPassword().equals(userDTO.getPassword()))
            throw new UserPasswordAlreadyUsedException("password already used");
        user.setPassword(userDTO.getPassword());
        userDAO.save(user);
        return true;
    }


    public String validate(UserDTO user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }
        return "FAIL";
    }

    public String adminValidate(UserDTO user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                    return jwtService.generateToken(user);
                }
            }
        }
        return "FAIL";
    }

//    public UserEntity assignRolesToUser(String email, Set<String> roleNames) {
//        UserEntity user = userDAO.findById(email).orElseThrow(() -> new RuntimeException("User not found"));
//
//        Set<RoleEntity> roles = new HashSet<>();
//        for (String roleName : roleNames) {
//            RoleEntity role = roleDAO.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
//            roles.add(role);
//        }
//
//        user.setRoles(roles);
//        return userDAO.save(user);
//    }
}
