package com.clothify.ecommerce.controller.user;

import com.clothify.ecommerce.dto.user.RoleDTO;
import com.clothify.ecommerce.entity.user.RoleEntity;
import com.clothify.ecommerce.service.user.RoleService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Object> addRole(@RequestBody RoleDTO roleDTO) {
        return Boolean.TRUE.equals(roleService.save(roleDTO)) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Object> assignRoleToUser(@RequestBody JsonNode payload) {
        roleService.assignRoles(payload);
        return ResponseEntity.ok().build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<RoleEntity> getAllRoles() {
        return roleService.getAll();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{roleId}")
    public void deleteRole(@PathVariable Integer roleId) {
        roleService.delete(roleId);
    }

    @GetMapping
    public List<RoleEntity> getRoleByEmail(@RequestBody JsonNode jsonNode) {
        return roleService.getRolesByEmail(jsonNode.get("email").asText());
    }
}
