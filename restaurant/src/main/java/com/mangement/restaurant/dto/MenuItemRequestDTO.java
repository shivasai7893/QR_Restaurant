package com.mangement.restaurant.dto;

import lombok.Data;

@Data
public class MenuItemRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Long subcategoryId;
    private Boolean available = true;
    private Integer stockQuantity = 0;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getSubcategoryId() {
		return subcategoryId;
	}
	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public Integer getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	@Override
	public String toString() {
		return "MenuItemRequestDTO [name=" + name + ", description=" + description + ", price=" + price
				+ ", subcategoryId=" + subcategoryId + ", available=" + available + ", stockQuantity=" + stockQuantity
				+ "]";
	}
    
}
