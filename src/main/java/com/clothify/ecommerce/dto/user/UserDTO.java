package com.clothify.ecommerce.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties({"roles"}) // Exclude roles from serialization

public class UserDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private Date updateDate = new Date(System.currentTimeMillis());
    private Date createdDate = new Date(System.currentTimeMillis());
    @JsonIgnoreProperties({"roles"})
    private Set<RoleDTO> roles = new HashSet<>();
}

