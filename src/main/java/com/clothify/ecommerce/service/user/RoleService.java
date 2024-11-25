package com.clothify.ecommerce.service.user;

import com.clothify.ecommerce.dto.user.RoleDTO;
import com.clothify.ecommerce.entity.user.RoleEntity;
import com.clothify.ecommerce.entity.user.UserEntity;
import com.clothify.ecommerce.exception.user.RoleNotFoundException;
import com.clothify.ecommerce.exception.user.UserNotFoundException;
import com.clothify.ecommerce.repository.user.RoleDAO;
import com.clothify.ecommerce.repository.user.UserDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDAO roleDAO;
    private final ObjectMapper mapper;
    private final UserDAO userDAO;

    public Boolean save(RoleDTO roleDTO) {
        try {
            mapper.convertValue(roleDAO.save(mapper.convertValue(roleDTO, RoleEntity.class)), RoleDTO.class);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    @Transactional
    public void assignRoleForFirstUser(UserEntity userEntity) {
        UserEntity user = userDAO.findById((userEntity.getEmail())).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<RoleEntity> rolesList = new ArrayList<>();
        RoleDTO admin = RoleDTO.builder().name("ADMIN").build();
        roleDAO.save(mapper.convertValue(admin, RoleEntity.class));
        RoleEntity role = roleDAO.findById(1).orElseThrow(() -> new RoleNotFoundException("Role not found"));
        rolesList.add(role);
        user.setRoles(rolesList);
        userDAO.save(user);
    }

    @Transactional
    public void assignRoles(JsonNode payload) {
        UserEntity user = userDAO.findById(payload.get("email").asText()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<RoleEntity> rolesList = new HashSet<>();

        JsonNode roleIdsNode = payload.get("roleIds");
        if (roleIdsNode != null && roleIdsNode.isArray()) {
            for (JsonNode roleIdNode : roleIdsNode) {
                RoleEntity role = roleDAO.findById(roleIdNode.asInt()).orElseThrow(() -> new RoleNotFoundException("Role not found"));
                rolesList.add(role);
            }
        }
        user.setRoles(new ArrayList<>(rolesList));

        userDAO.save(user);
    }


    public List<RoleEntity> getAll() {
        return roleDAO.findAll();
    }

    public void delete(Integer roleId) {
        roleDAO.findById(roleId).orElseThrow(() -> new RoleNotFoundException("role not found"));
    }

    @Transactional
    public List<RoleEntity> getRolesByEmail(String email) {
        UserEntity user = userDAO.findById(email).orElseThrow(() -> new UserNotFoundException("user not found"));
        return user.getRoles();
    }
}
