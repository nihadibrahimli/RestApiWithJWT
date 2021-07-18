package com.restapiwithjwt.storage.repository;

import com.restapiwithjwt.storage.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  RoleEntity findByName(String roleName);

}
