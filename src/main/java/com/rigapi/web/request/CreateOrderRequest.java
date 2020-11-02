package com.rigapi.web.request;

import java.util.List;

public class CreateOrderRequest {
  private int customerId;
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
