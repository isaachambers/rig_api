package com.rigapi.web.controller;

import static org.assertj.core.api.Assertions.tuple;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rigapi.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class CustomerControllerTest extends IntegrationTest {

  @DisplayName("Should create new customer")
  @Test
  void shouldCreateNewCustomer() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();

    var response = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));

    assertJsonMap(response).extracting("id").isNotNull();
  }

  @DisplayName("Should fail with invalid request")
  @Test
  void shouldFailCustomerCreationWithInvalidRequest() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();

    var response = postExpectingBadRequest("/v1/customers", headers,
        e("", ""));

    assertJsonMap(response).extracting("errors")
        .asList().extracting("message")
        .containsExactlyInAnyOrder(
            "lastName must not be null",
            "firstName must not be null",
            "address must not be null");
  }

  @DisplayName("Should create retrieve customer order")
  @Test
  void shouldRetrieveCustomerOrders() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));
    var createdOrderResponse1 = postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));
    var createdOrderResponse2 = postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 1), e("productId", toMap(productResponse).get("id"))))));

    var response = get("/v1/customers/" + toMap(customerResponse).get("id") + "/orders", headers);

    assertJsonMap(response).extracting("content")
        .asList()
        .extracting("orderStatus", "orderId")
        .containsExactlyInAnyOrder(
            tuple("CREATED", toMap(createdOrderResponse1).get("id")),
            tuple("CREATED", toMap(createdOrderResponse2).get("id")));
  }

}