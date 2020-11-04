package com.rigapi.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rigapi.IntegrationTest;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class ProductControllerTest extends IntegrationTest {

  @DisplayName("Should create new product")
  @Test
  void shouldCreateNewProduct() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();

    var response = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));

    assertJsonMap(response).extracting("id").isNotNull();
  }

  @DisplayName("Should fail with invalid request")
  @Test
  void shouldFailProductCreationWithInvalidRequest() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();

    var response = postExpectingBadRequest("/v1/products", headers,
        e("", ""));

    assertJsonMap(response).extracting("errors")
        .asList().extracting("message")
        .containsExactlyInAnyOrder("name must not be null", "author must not be null");
  }

  @DisplayName("Should list all products")
  @Test
  void shouldListAllProducts() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));

    var response = get("/v1/products", headers);

    assertJsonMap(response)
        .extracting("content")
        .asList()
        .extracting("author", "name", "quantity").
        containsExactly(Tuple.tuple("F. Scott Fitzgerald", "The Great Gatsby", 10));
  }
}