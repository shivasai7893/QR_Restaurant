package com.mangement.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class InventoryDTO {
	private Integer totalItems;
	private Integer availableItems;
	private Integer outOfStockItems;
	private Integer lowStockItems;
	private List<ItemStockDTO> itemStocks;

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

	public Integer getAvailableItems() {
		return availableItems;
	}

	public void setAvailableItems(Integer availableItems) {
		this.availableItems = availableItems;
	}

	public Integer getOutOfStockItems() {
		return outOfStockItems;
	}

	public void setOutOfStockItems(Integer outOfStockItems) {
		this.outOfStockItems = outOfStockItems;
	}

	public Integer getLowStockItems() {
		return lowStockItems;
	}

	public void setLowStockItems(Integer lowStockItems) {
		this.lowStockItems = lowStockItems;
	}

	public List<ItemStockDTO> getItemStocks() {
		return itemStocks;
	}

	public void setItemStocks(List<ItemStockDTO> itemStocks) {
		this.itemStocks = itemStocks;
	}

	@Data
	public static class ItemStockDTO {
		private Long id;
		private String name;
		private Integer stockQuantity;
		private Double price;
		private Boolean available;
		private String stockStatus; // OK, LOW, OUT
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

		public Integer getStockQuantity() {
			return stockQuantity;
		}

		public void setStockQuantity(Integer stockQuantity) {
			this.stockQuantity = stockQuantity;
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

		public String getStockStatus() {
			return stockStatus;
		}

		public void setStockStatus(String stockStatus) {
			this.stockStatus = stockStatus;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

	}
}
