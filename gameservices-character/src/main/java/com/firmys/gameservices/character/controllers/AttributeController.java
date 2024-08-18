package com.firmys.gameservices.character.controllers;

import static com.firmys.gameservices.common.CommonConstants.ATTRIBUTE_PATH;

import com.firmys.gameservices.character.services.AttributeService;
import com.firmys.gameservices.generated.models.Attribute;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(ATTRIBUTE_PATH)
@Accessors(chain = true, fluent = true)
public class AttributeController extends ServiceController<Attribute> {
  private final AttributeService service;
}
