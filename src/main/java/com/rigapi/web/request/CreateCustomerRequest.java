package com.rigapi.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
public class CreateCustomerRequest {

  @NotNull(message = "firstName must not be null")
  @Size(max = 255)
  @ApiModelProperty(required = true)
  private String firstName;

  @NotNull(message = "lastName must not be null")
  @Size(max = 255)
  @ApiModelProperty(required = true)
  private String lastName;

  @Size(max = 255)
  @NotNull(message = "address must not be null")
  @ApiModelProperty(required = true)
  private String address;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
