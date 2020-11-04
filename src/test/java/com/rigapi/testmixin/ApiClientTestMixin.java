package com.rigapi.testmixin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static com.rigapi.testmixin.ApiClientTestMixin.Private.testRestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public interface ApiClientTestMixin extends TestUtilityMixin {

  class Private {
    static TestRestTemplate testRestTemplate;
  }

  @Autowired
  default void apiClientTestMixinDependencies(TestRestTemplate testRestTemplate) {
    Private.testRestTemplate = testRestTemplate;
  }

  default String exchangeExpectingStatus(HttpMethod method,
                                         String url,
                                         HttpHeaders headers,
                                         HttpStatus status,
                                         Object body,
                                         Object... urlVariables) {
    testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    ResponseEntity<String> response = testRestTemplate
        .exchange(url, method, body == null ? new HttpEntity<>(headers) : new HttpEntity<>(body, headers), String.class, urlVariables);
    assertThat(response.getStatusCode()).isEqualTo(status);
    return response.getBody();
  }

  default String exchangeExpectingStatusWithBasicAuth(HttpMethod method, String url,
                                                      String clientName,
                                                      String password,
                                                      HttpStatus status,
                                                      Object body,
                                                      Object... urlVariables) {
    ResponseEntity<String> response = testRestTemplate.withBasicAuth(clientName, password)
        .exchange(url, method, body == null ? null : new HttpEntity<>(body), String.class, urlVariables);
    assertThat(response.getStatusCode()).isEqualTo(status);
    return response.getBody();
  }

  default String get(String url, HttpHeaders headers) {
    return exchangeExpectingStatus(GET, url, headers, OK, null);
  }

  default String getExpectingNotFound(String url, HttpHeaders headers) {
    return exchangeExpectingStatus(GET, url, headers, NOT_FOUND, null);
  }

  default String postExpectingCreated(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, headers, CREATED, jsonMap(jsonEntries));
  }

  default String post(String url, String clientName, String password, MultiValueMap<String, String> values) {
    return exchangeExpectingStatusWithBasicAuth(POST, url, clientName, password, OK, values);
  }

  default String postExpectingBadRequest(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, headers, BAD_REQUEST, jsonMap(jsonEntries));
  }

  default String postExpectingNotFoundRequest(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, headers, NOT_FOUND, jsonMap(jsonEntries));
  }

  default HttpHeaders getHttpHeaders() throws JsonProcessingException {
    String user = "demo-user";
    String userPassword = "user-password";
    String client = "postman";
    String clientPassword = "postman-password-secret";
    String scope = "webclient";
    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
    request.set("scope", scope);
    request.set("username", user);
    request.set("password", userPassword);
    request.set("grant_type", "password");
    var tokenResponse = post("/oauth/token", client, clientPassword, request);
    String accessToken = (String) toMap(tokenResponse).get("access_token");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    return headers;
  }
}
