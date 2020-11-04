package com.rigapi.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rigapi.IntegrationTest;
import com.rigapi.entity.Product;
import com.rigapi.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

class OrderControllerTest extends IntegrationTest {
  @Autowired
  private ProductRepository productRepository;

  @DisplayName("Should Create new customer order")
  @Test
  void shouldCreateCustomerOrder() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));

    var createdOrderResponse = postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));

    assertJsonMap(createdOrderResponse).extracting("id").isNotNull();
  }

  @DisplayName("Should fail to create order if customer does not exist")
  @Test
  void shouldFailToCreateOrderWithInvalidCustomer() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    int nonExistingCustomerId = 33;
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));

    var createdOrderResponse = postExpectingNotFoundRequest("/v1/orders", headers,
        e("customerId", nonExistingCustomerId),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));

    assertJsonMap(createdOrderResponse).extracting("message").isEqualTo("Customer not found");
  }

  @DisplayName("Should fail to create order if product does not exist")
  @Test
  void shouldFailToCreateOrderWithInvalidProduct() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    int invalidProductId = 12435;
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));

    var createdOrderResponse = postExpectingNotFoundRequest("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", invalidProductId)))));

    assertJsonMap(createdOrderResponse).extracting("message").isEqualTo("Product does not exist");
  }

  @DisplayName("Should fail to create new customer order if in-store quantity exceeds stock")
  @Test
  void shouldFailIfQuantityRequestedIsGreaterThanStock() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 2),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));

    var createdOrderResponse = postExpectingBadRequest("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 4), e("productId", toMap(productResponse).get("id"))))));

    assertJsonMap(createdOrderResponse)
        .extracting("message")
        .isEqualTo("Order cannot be fulfilled because only 2 items are left in stock for The Great Gatsby");
  }

  @DisplayName("Should keep correct amount of product stock after each order")
  @Test
  void shouldKeepUpdatedProductStock() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));
    postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));

    Optional<Product> product = productRepository.findById((Integer) toMap(productResponse).get("id"));

    assertThat(product).isNotEmpty();
    assertThat(product.get().getQuantity()).isEqualTo(7);
  }

  @DisplayName("Should retrieve order details for order")
  @Test
  void shouldRetrieveListOfOrderDetails() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));
    var createdOrderResponse = postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));

    var response = get("/v1/orders/" + toMap(createdOrderResponse).get("id") + "/details", headers);

    assertJsonArray(response)
        .extracting("productName", "quantityOrdered")
        .containsExactlyInAnyOrder(tuple("The Great Gatsby", 3));
  }

  @DisplayName("Should get order information")
  @Test
  void shouldGetOrderInformation() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    var customerResponse = postExpectingCreated("/v1/customers", headers,
        e("address", "Upper Hot-springs Road"),
        e("firstName", "Bin"),
        e("lastName", "Chen"));
    var productResponse = postExpectingCreated("/v1/products", headers,
        e("quantity", 10),
        e("name", "The Great Gatsby"),
        e("author", "F. Scott Fitzgerald"));
    var createdOrderResponse = postExpectingCreated("/v1/orders", headers,
        e("customerId", toMap(customerResponse).get("id")),
        e("detailsList", jsonArray(jsonMap(e("quantity", 3), e("productId", toMap(productResponse).get("id"))))));

    var response = get("/v1/orders/" + toMap(createdOrderResponse).get("id"), headers);

    assertJsonMap(response).extracting("orderStatus").isEqualTo("CREATED");
  }

  @DisplayName("Should get list of all orders")
  @Test
  void shouldGetListOfAllOrders() throws JsonProcessingException {
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

    var response = get("/v1/orders", headers);

    assertJsonMap(response).extracting("content")
        .asList()
        .extracting("orderStatus", "orderId")
        .containsExactlyInAnyOrder(
            tuple("CREATED", toMap(createdOrderResponse1).get("id")),
            tuple("CREATED", toMap(createdOrderResponse2).get("id")));
  }

  @DisplayName("Should fail when order is not found")
  @Test
  void shouldFailWhenOrderNotFound() throws JsonProcessingException {
    HttpHeaders headers = getHttpHeaders();
    int invalidOrderNumber = 12345;

    var response = getExpectingNotFound("/v1/orders/" + invalidOrderNumber, headers);

    assertJsonMap(response).extracting("message").isEqualTo("Order Does Not Exist");
  }
}