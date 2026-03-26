package com.mangement.restaurant.dto;

import lombok.Data;

@Data
public class MenuItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private Integer stockQuantity;
    private String subcategoryName;
    private String categoryName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "MenuItemResponseDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", available=" + available + ", stockQuantity=" + stockQuantity + ", subcategoryName="
				+ subcategoryName + ", categoryName=" + categoryName + "]";
	}
    
    
}
