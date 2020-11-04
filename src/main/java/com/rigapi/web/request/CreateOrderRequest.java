package com.rigapi.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel
public class CreateOrderRequest {

  @ApiModelProperty(required = true)
  private int customerId;
  @ApiModelProperty(required = true)
  private List<OrderDetailsRequest> detailsList;

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public List<OrderDetailsRequest> getDetailsList() {
    return detailsList;
  }

  public void setDetailsList(List<OrderDetailsRequest> detailsList) {
    this.detailsList = detailsList;
  }
}
