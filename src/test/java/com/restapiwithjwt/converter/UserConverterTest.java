package com.restapiwithjwt.converter;

import com.restapiwithjwt.storage.entity.UserEntity;
import com.uranus.garbags.client.java.model.User;
import com.restapiwithjwt.util.ObjectsGenerator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class UserConverterTest {

  @Test
  public void convertUserToUserEntity() {
    User user = ObjectsGenerator.createUser();

    UserEntity userEntity = UserConverter.convertUserToUserEntity(user);

    assertEquals(user.getPassword(), userEntity.getPassword());
    assertEquals(user.getPasswordConfirm(), userEntity.getPasswordConfirm());
    assertEquals(user.getUsername(), userEntity.getUsername());
    assertEquals(user.getEmail(), userEntity.getEmail());
    assertEquals(user.getFirstname(), userEntity.getFirstName());
    assertEquals(user.getLastname(), userEntity.getLastName());
  }
}
