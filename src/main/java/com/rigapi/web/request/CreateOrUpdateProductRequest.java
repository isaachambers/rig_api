package com.rigapi.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
public class CreateOrUpdateProductRequest {

  @NotNull(message = "name must not be null")
  @Size(max = 255)
  @ApiModelProperty(required = true)
  private String name;

  @NotNull(message = "author must not be null")
  @Size(max = 255)
  @ApiModelProperty(required = true)
  private String author;

  @ApiModelProperty(required = true)
  private int quantity;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
