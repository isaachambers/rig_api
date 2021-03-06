package com.rigapi.testmixin;

import static com.rigapi.testmixin.TestUtilityMixin.Private.objectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.ObjectAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JsonContentAssert;

public interface TestUtilityMixin {

  class Private {
    static ObjectMapper objectMapper;
  }

  @Autowired
  default void responsesTestMixinDependencies(
      ObjectMapper objectMapper
  ) {
    Private.objectMapper = objectMapper;
  }

  default Map<?, ?> jsonMap(Map.Entry... entries) {
    return Map.ofEntries(entries);
  }

  default List<?> jsonArray(Object... items) {
    return List.of(items);
  }

  default Map.Entry<String, Object> e(String key, Object value) {
    return Assertions.entry(key, value);
  }

  default <K, V> MapAssert<K, V> assertJsonMap(String json) {
    return assertJsonMap(json, "$");
  }

  default <K, V> MapAssert<K, V> assertJsonMap(String json, String expression) {
    return new JsonContentAssert(getClass(), json).extractingJsonPathMapValue(expression);
  }

  default <K, V> AbstractListAssert<?, ?, Object, ObjectAssert<Object>> assertJsonArray(String json) {
    return assertJsonArray(json, "$");
  }

  default AbstractListAssert<?, ?, Object, ObjectAssert<Object>> assertJsonArray(String json, String expression) {
    return new JsonContentAssert(getClass(), json).extractingJsonPathArrayValue(expression);
  }

  default Map<Object, Object> toMap(String json) throws JsonProcessingException {
    return objectMapper
        .readValue(json, objectMapper.getTypeFactory()
            .constructMapType(Map.class, String.class, Object.class));
  }
}
