package com.rigapi.web.response;

import com.rigapi.domain.OrderStatus;
import java.util.Date;
import java.util.List;

public class CustomerOrdersResponse {
  private int orderId;
  private Date updatedTime;
  private Date creationTime;
  private OrderStatus orderStatus;
  private List<OrderDetailResponse> details;

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public List<OrderDetailResponse> getDetails() {
    return details;
  }

  public void setDetails(List<OrderDetailResponse> details) {
    this.details = details;
  }
}
