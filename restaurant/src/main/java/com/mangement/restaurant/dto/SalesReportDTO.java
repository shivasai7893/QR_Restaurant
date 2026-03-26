package com.mangement.restaurant.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class SalesReportDTO {
    private LocalDate date;
    private Integer totalOrders;
    private Double totalRevenue;
    private Integer totalItemsSold;
    private String bestSellingItem;
    private List<ItemSalesDTO> itemWiseSales;
    
    

    public LocalDate getDate() {
		return date;
	}



	public void setDate(LocalDate date) {
		this.date = date;
	}



	public Integer getTotalOrders() {
		return totalOrders;
	}



	public void setTotalOrders(Integer totalOrders) {
		this.totalOrders = totalOrders;
	}



	public Double getTotalRevenue() {
		return totalRevenue;
	}



	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}



	public Integer getTotalItemsSold() {
		return totalItemsSold;
	}



	public void setTotalItemsSold(Integer totalItemsSold) {
		this.totalItemsSold = totalItemsSold;
	}



	public String getBestSellingItem() {
		return bestSellingItem;
	}



	public void setBestSellingItem(String bestSellingItem) {
		this.bestSellingItem = bestSellingItem;
	}



	public List<ItemSalesDTO> getItemWiseSales() {
		return itemWiseSales;
	}



	public void setItemWiseSales(List<ItemSalesDTO> itemWiseSales) {
		this.itemWiseSales = itemWiseSales;
	}



	@Data
    public static class ItemSalesDTO {
        private String itemName;
        private Integer quantitySold;
        private Double revenue;
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public Integer getQuantitySold() {
			return quantitySold;
		}
		public void setQuantitySold(Integer quantitySold) {
			this.quantitySold = quantitySold;
		}
		public Double getRevenue() {
			return revenue;
		}
		public void setRevenue(Double revenue) {
			this.revenue = revenue;
		}
        
        
    }
}
