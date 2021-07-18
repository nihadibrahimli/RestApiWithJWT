package com.restapiwithjwt.service;

import com.restapiwithjwt.storage.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
  @Autowired
  private UserService userService;

  @Override
  public boolean supports(Class<?> aClass) {
    return UserEntity.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserEntity user = (UserEntity) o;

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
    if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
      errors.rejectValue("username", "Size.userForm.username", "*Username length must be between 6 and 32");
    }
    if (userService.findByUsername(user.getUsername()) != null) {
      errors.rejectValue("username", "Duplicate.userForm.username", "*Username already exists");
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
    if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
      errors.rejectValue("password", "Size.userForm.password", "*Password length must be between 8 and 32");
    }

    if (!user.getPasswordConfirm().equals(user.getPassword())) {
      errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "*Password and Confirm Password must be the same");
    }
  }
}