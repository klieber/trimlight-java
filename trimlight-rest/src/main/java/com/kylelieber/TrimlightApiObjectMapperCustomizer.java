package com.kylelieber;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class TrimlightApiObjectMapperCustomizer implements ObjectMapperCustomizer {

  @Override
  public void customize(ObjectMapper objectMapper) {
    objectMapper.registerModule(new GuavaModule());
    objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
  }
}
