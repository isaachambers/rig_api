package com.rigapi.web.response;

import static java.util.UUID.randomUUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ApiModel("Error response payload")
public class ErrorResponse {

  @ApiModelProperty(value = "Unique error id", required = true, example = "4fdbec67-0a2e-46cf-bb44-b88fae9548f2")
  private final String uuid;

  @ApiModelProperty(value = "Error timestamp", required = true, example = "2019-09-05T17:53:31.099Z")
  private final LocalDateTime timestamp;

  @ApiModelProperty(value = "Explanation of HTTP code", example = "Bad Request", required = true)
  private String status;

  @ApiModelProperty(value = "User friendly error message", example = "Something went wrong while processing your request", required = true)
  private String message;

  @ApiModelProperty(value = "Sub errors", example = "Bad Request")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Map<String, Object>> errors;

  public ErrorResponse() {
    uuid = randomUUID().toString();
    timestamp = LocalDateTime.now();
  }

  public String getUuid() {
    return uuid;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Map<String, Object>> getErrors() {
    return errors;
  }

  public void setErrors(List<Map<String, Object>> errors) {
    this.errors = errors;
  }
}
