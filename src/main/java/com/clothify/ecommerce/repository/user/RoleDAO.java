package com.clothify.ecommerce.repository.user;

import com.clothify.ecommerce.entity.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleDAO extends JpaRepository<RoleEntity, Integer> {
}
