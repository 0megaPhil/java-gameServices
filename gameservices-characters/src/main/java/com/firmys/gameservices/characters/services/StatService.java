package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.data.StatRepository;
import com.firmys.gameservices.characters.models.Stat;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class StatService extends CommonService<Stat> {
  private final StatRepository repository;
  private final GatewayClient gatewayClient;
}
