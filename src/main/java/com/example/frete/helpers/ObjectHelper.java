package com.example.frete.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ObjectHelper {
  public static <T> T convertStringTo(String value, Class<T> valueType) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    try {
      var effectiveJava = mapper.readValue(value, valueType);
      return effectiveJava;
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
  }
}