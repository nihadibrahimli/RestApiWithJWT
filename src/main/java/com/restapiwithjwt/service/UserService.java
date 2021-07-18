package com.restapiwithjwt.service;

import com.restapiwithjwt.storage.entity.RoleEntity;
import com.restapiwithjwt.storage.entity.UserEntity;
import com.restapiwithjwt.storage.repository.RoleRepository;
import com.restapiwithjwt.storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }

  @Transactional
  public void save(UserEntity user) {

    user.setPassword(passwordEncoder().encode(user.getPassword()));

    Set<RoleEntity> roleEntities = new HashSet<>();
    RoleEntity entity = roleRepository.findByName("ROLE_USER");
    if (!isNull(entity)) {
      roleEntities.add(roleRepository.findByName("ROLE_USER"));
    } else {
      RoleEntity roleEntity = new RoleEntity();
      roleEntity.setName("ROLE_USER");
      roleEntities.add(roleRepository.save(roleEntity));
    }
    user.setRoles(roleEntities);

    userRepository.save(user);
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
