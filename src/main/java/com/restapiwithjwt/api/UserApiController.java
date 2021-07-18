package com.restapiwithjwt.api;

import com.restapiwithjwt.service.UserService;
import com.restapiwithjwt.service.UserValidator;
import com.restapiwithjwt.storage.entity.UserEntity;
import com.uranus.garbags.client.java.model.Response;
import com.uranus.garbags.client.java.model.User;
import com.restapiwithjwt.converter.UserConverter;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class UserApiController {
  @Autowired
  private UserService userService;

  @Autowired
  private UserValidator userValidator;

  @PostMapping("/registration")
  public ResponseEntity<Response> registerUser(@ApiParam(value = "", required = true) @Valid @RequestBody User body, BindingResult bindingResult) {
    Response response = new Response();

    UserEntity userEntity = UserConverter.convertUserToUserEntity(body);
    userValidator.validate(userEntity, bindingResult);

    if (bindingResult.hasErrors()) {
      response.setDetails(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(" ")));
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    userService.save(userEntity);

    response.setDetails("Registered successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
