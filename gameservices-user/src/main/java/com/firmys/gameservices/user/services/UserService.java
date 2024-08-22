package com.firmys.gameservices.user.services;

import com.firmys.gameservices.generated.models.User;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.user.data.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class UserService extends GameService<User> {
  private final UserRepository repository;
  private final GameServiceClient gameServiceClient;

  private final Class<User> entityType = User.class;
}
