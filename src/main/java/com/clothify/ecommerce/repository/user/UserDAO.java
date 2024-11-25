package com.clothify.ecommerce.repository.user;

import com.clothify.ecommerce.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDAO extends JpaRepository<UserEntity, String> {
}
