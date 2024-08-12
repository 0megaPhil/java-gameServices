package com.firmys.gameservices.character.config;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.config.CommonConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.generated.models.Character;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback;
import reactor.core.publisher.Mono;

@Configuration
@Import({CommonConfig.class, SpringSecurityConfiguration.class})
public class CharacterConfig {

  @Bean
  @Profile(CommonConstants.PROFILE_SERVICE)
  public AfterConvertCallback<Character> afterConvertCallback() {
    return (d, table) -> {
      d =
          d.toBuilder()
              .uuid(Optional.ofNullable(d.uuid()).orElseGet(UUID::randomUUID))
              .stats(Optional.ofNullable(d.stats()).orElseGet(HashSet::new))
              .build();
      return Mono.just(d);
    };
  }
}
