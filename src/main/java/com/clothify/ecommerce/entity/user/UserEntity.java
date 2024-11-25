package com.clothify.ecommerce.entity.user;

import com.clothify.ecommerce.entity.order.OrderEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String address;
    private Date updateDate = new Date(System.currentTimeMillis());
    private Date createdDate = new Date(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private List<RoleEntity> roles;


}
