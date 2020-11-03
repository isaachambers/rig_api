package com.rigapi.testmixin;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.ObjectAssert;
import org.springframework.boot.test.json.JsonContentAssert;

public interface JsonTestMixin {

  default Map<?, ?> jsonMap(Map.Entry... entries) {
    return Map.ofEntries(entries);
  }

  default Map<?, ?> jsonMap(String key, Object value) {
    return jsonMap(e(key, value));
  }

  default List<?> jsonArray(Object... items) {
    return List.of(items);
  }

  default Map.Entry<String, Object> e(String key, Object value) {
    return Assertions.entry(key, value);
  }

  default <K, V> MapAssert<K, V> assertJsonMap(String json, String expression) {
    return new JsonContentAssert(getClass(), json).extractingJsonPathMapValue(expression);
  }

  default AbstractCharSequenceAssert<?, String> assertJsonMapStringValue(String json, String expression) {
    return new JsonContentAssert(getClass(), json).extractingJsonPathStringValue(expression);
  }

  default <K, V> AbstractListAssert<?, ?, Object, ObjectAssert<Object>> assertJsonArray(String json) {
    return assertJsonArray(json, "$");
  }

  default AbstractListAssert<?, ?, Object, ObjectAssert<Object>> assertJsonArray(String json, String expression) {
    return new JsonContentAssert(getClass(), json).extractingJsonPathArrayValue(expression);
  }
}
