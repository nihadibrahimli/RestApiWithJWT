package com.restapiwithjwt.converter;

import com.restapiwithjwt.storage.entity.UserEntity;
import com.uranus.garbags.client.java.model.User;

public class UserConverter {

  public static UserEntity convertUserToUserEntity(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(user.getPassword());
    userEntity.setPasswordConfirm(user.getPasswordConfirm());
    userEntity.setEmail(user.getEmail());
    userEntity.setFirstName(user.getFirstname());
    userEntity.setLastName(user.getLastname());

    return userEntity;
  }
}
