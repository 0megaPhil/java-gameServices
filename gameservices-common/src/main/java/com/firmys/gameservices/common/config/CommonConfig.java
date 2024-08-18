package com.firmys.gameservices.common.config;

import com.firmys.gameservices.common.CommonProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Slf4j
@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class CommonConfig {}
