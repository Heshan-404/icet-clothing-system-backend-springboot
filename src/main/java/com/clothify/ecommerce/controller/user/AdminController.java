package com.clothify.ecommerce.controller.user;

import com.clothify.ecommerce.dto.user.UserDTO;
import com.clothify.ecommerce.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;


    @PutMapping
    public UserDTO update(@RequestBody UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/reset-password")
    public ResponseEntity<Object> updatePassword(@RequestBody UserDTO userDTO) {
        return Boolean.TRUE.equals(userService.resetPassword(userDTO)) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Object> persist(@RequestBody UserDTO user) {
        userService.persist(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> adminLogin(@RequestBody UserDTO user, HttpServletResponse response) {
        String token = userService.adminValidate(user);
        if (token.equals("FAIL")) return ResponseEntity.badRequest().build();
        Cookie tokenCookie = new Cookie("AUTH_TOKEN", token);

        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(-1);

        Cookie roleCookie = new Cookie("role", "ADMIN");
        roleCookie.setHttpOnly(false);
        roleCookie.setSecure(true);
        roleCookie.setPath("/");
        roleCookie.setMaxAge(-1);

        response.addCookie(tokenCookie);
        response.addCookie(roleCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> adminLogout(HttpServletResponse response) {

        Cookie authToken = new Cookie("AUTH_TOKEN", "");
        authToken.setHttpOnly(true);
        authToken.setSecure(true);
        authToken.setPath("/");
        authToken.setMaxAge(0);
        response.addCookie(authToken);

        Cookie roleCookie = new Cookie("role", "");
        roleCookie.setHttpOnly(false);
        roleCookie.setSecure(true);
        roleCookie.setPath("/");
        roleCookie.setMaxAge(0);

        response.addCookie(roleCookie);

        return ResponseEntity.ok("Cookies added successfully");
    }


}
