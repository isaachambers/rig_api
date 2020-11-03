package com.rigapi.web.response;

public class OrderDetailResponse {
  private int productId;
  private String productName;
  private int quantityOrdered;

  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantityOrdered() {
    return quantityOrdered;
  }

  public void setQuantityOrdered(int quantityOrdered) {
    this.quantityOrdered = quantityOrdered;
  }
}
