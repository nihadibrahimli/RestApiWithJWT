package com.restapiwithjwt.util;

import com.uranus.garbags.client.java.model.User;

public class ObjectsGenerator {

  public static User createUser() {
    User user = new User();
    user.setFirstname("Tural");
    user.setLastname("Malikli");
    user.setPassword("secretpass");
    user.setUsername("malikli.tural");
    user.setPasswordConfirm("secretpass");
    user.setEmail("tural.malikli@hotmail.com");
    return user;
  }
}
