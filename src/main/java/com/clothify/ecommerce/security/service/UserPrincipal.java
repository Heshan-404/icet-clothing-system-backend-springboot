package com.clothify.ecommerce.security.service;

import com.clothify.ecommerce.entity.user.RoleEntity;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final UserEntity user;
    private final RoleService roleService;
    private List<SimpleGrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleEntity> roles = roleService.getRolesByEmail(user.getEmail());
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
