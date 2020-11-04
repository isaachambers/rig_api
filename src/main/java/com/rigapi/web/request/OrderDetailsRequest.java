package com.rigapi.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
public class OrderDetailsRequest {

  @NotNull(message = "quantity must not be null")
  @Min(value = 1, message = "quantity must be greater or equal to 1")
  @ApiModelProperty(required = true)
  private int quantity;

  @NotNull(message = "quantity must not be null")
  @Min(value = 1, message = "quantity must be greater or equal to 1")
  @ApiModelProperty(required = true)
  private int productId;

  public OrderDetailsRequest(int quantity, int productId) {
    this.quantity = quantity;
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }
}