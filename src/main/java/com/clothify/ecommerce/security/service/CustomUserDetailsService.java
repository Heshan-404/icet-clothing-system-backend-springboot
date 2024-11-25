package com.clothify.ecommerce.security.service;

import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.repository.user.UserDAO;
import com.clothify.ecommerce.service.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byUserName = userDAO.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("invalid email address"));
        return new UserPrincipal(byUserName, roleService);
    }
}
