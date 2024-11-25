package com.clothify.ecommerce.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Integer id;
    private String name;
    @JsonIgnoreProperties({"users"}) // Exclude the `users` field from being serialized if needed
    private Set<UserDTO> users;

}