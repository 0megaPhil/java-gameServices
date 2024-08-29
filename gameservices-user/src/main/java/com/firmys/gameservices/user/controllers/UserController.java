package com.firmys.gameservices.user.controllers;

import static com.firmys.gameservices.common.CommonConstants.USER_PATH;

import com.firmys.gameservices.generated.models.User;
import com.firmys.gameservices.service.ServiceController;
import com.firmys.gameservices.user.services.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(USER_PATH)
@Accessors(chain = true, fluent = true)
public class UserController extends ServiceController<User> {
  private final UserService service;
}
