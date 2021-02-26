package com.codpixproject.codpix.application.entrypoint;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class TestJson {
  private static final ObjectMapper jsonMapper = jsonMapper();
  
  private static ObjectMapper jsonMapper() {
    return new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
      .registerModule(new JavaTimeModule())
      .registerModule(new Jdk8Module())
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .disable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  public static <T> String writeAsString(T object) {
    
    try {
      return jsonMapper.writeValueAsString(object);
      
    } catch (JsonProcessingException e) {
      throw new AssertionError("Error serializing object: " + e.getMessage(), e);
    }
  }

  public static <T> T readFromJson(String json, Class<T> clazz) {
    
    try {
      return jsonMapper.readValue(json, clazz);
      
    } catch (IOException e) {
      throw new AssertionError("Error reading value from json: " + e.getMessage(), e);
    }
  }
}
