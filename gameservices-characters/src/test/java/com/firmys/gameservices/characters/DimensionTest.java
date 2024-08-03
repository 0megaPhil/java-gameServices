package com.firmys.gameservices.characters;

import static com.firmys.gameservices.common.CommonConstants.PROFILE_TEST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.common.attributes.Dimension;
import com.firmys.gameservices.common.config.CommonConfig;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles(PROFILE_TEST)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    classes = {
      ObjectMapper.class,
      JsonUtils.class,
      CommonConfig.class,
    })
public class DimensionTest {

  @Autowired private JsonUtils jsonUtils;

  //  @Test
  void testDimension_construct() {
    var original = Dimension.of("INCH", 10);
    String originalJson = original.toJson();
    log.info("Original: {}", originalJson);
    var fromJson = jsonUtils.fromJson(originalJson, Dimension.class);
    log.info("Deserialized {}", fromJson.toJson());
    Assertions.assertThat(originalJson).isEqualTo(fromJson.toJson());
    Assertions.assertThat(original).isEqualTo(fromJson);
  }
}
