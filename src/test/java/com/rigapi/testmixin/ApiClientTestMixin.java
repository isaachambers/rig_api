package com.rigapi.testmixin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.OK;
import static com.rigapi.testmixin.ApiClientTestMixin.Private.testRestTemplate;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;

public interface ApiClientTestMixin extends JsonTestMixin {

  class Private {
    static TestRestTemplate testRestTemplate;
  }

  @Autowired
  default void apiClientTestMixinDependencies(TestRestTemplate testRestTemplate) {
    Private.testRestTemplate = testRestTemplate;
  }

  default String exchangeExpectingStatus(HttpMethod method,
                                         String url,
                                         HttpStatus status,
                                         Object body,
                                         Object... urlVariables) {
    ResponseEntity<String> response = testRestTemplate
        .exchange(url, method, body == null ? null : new HttpEntity<>(body), String.class, urlVariables);
    assertThat(response.getStatusCode()).isEqualTo(status);
    return response.getBody();
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

  default String get(String url) {
    return exchangeExpectingStatus(GET, url, OK, null);
  }

  default String patch(String url, HttpHeaders headers, Object... urlVariables) {
    return exchangeExpectingStatus(PATCH, url, headers, OK, null, urlVariables);
  }

  default void getExpectingBadRequest(String url) {
    exchangeExpectingStatus(GET, url, BAD_REQUEST, null);
  }

  default String postArray(String url, Map... jsonObjects) {
    return exchangeExpectingStatus(POST, url, OK, jsonArray((Object[]) jsonObjects));
  }

  default String post(String url, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, OK, jsonMap(jsonEntries));
  }

  default String post(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, headers, OK, jsonMap(jsonEntries));
  }

  default String post(String url, HttpHeaders headers, List<?> list) {
    return exchangeExpectingStatus(POST, url, headers, OK, list);
  }

  default String post(String url, String clientName, String password, MultiValueMap<String, String> values) {
    return exchangeExpectingStatusWithBasicAuth(POST, url, clientName, password, OK, values);
  }

  default String post(String url, String userName, String password, Map.Entry... jsonEntries) {
    return exchangeExpectingStatusWithBasicAuth(POST, url, userName, password, OK, jsonMap(jsonEntries));
  }

  default String postExpectingBadRequest(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, headers, BAD_REQUEST, jsonMap(jsonEntries));
  }

  default String postExpectingBadRequest(String url, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, BAD_REQUEST, jsonMap(jsonEntries));
  }

  default String postExpectingBadRequestWithBasicAuth(String url,
                                                      String clientName,
                                                      String password,
                                                      MultiValueMap<String, String> values) {
    return exchangeExpectingStatusWithBasicAuth(POST, url, clientName, password, BAD_REQUEST, values);
  }

  default String postExpectingAccepted(String url, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(POST, url, ACCEPTED, jsonMap(jsonEntries));
  }

  default void delete(String url, Map.Entry... jsonEntries) {
    exchangeExpectingStatus(DELETE, url, OK, jsonMap(jsonEntries));
  }

  default String delete(String url, HttpHeaders headers, Map.Entry... jsonEntries) {
    return exchangeExpectingStatus(DELETE, url, headers, OK, jsonMap(jsonEntries));
  }

  default void deleteExpectingGone(String url, Map.Entry... jsonEntries) {
    exchangeExpectingStatus(DELETE, url, GONE, jsonMap(jsonEntries));
  }

}
