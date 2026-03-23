package com.mangement.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Integer tableNumber;
    private Boolean isVip = false;
    private List<OrderItemRequestDTO> items;
	public Integer getTableNumber() {
		return tableNumber;
	}
	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}
	public List<OrderItemRequestDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemRequestDTO> items) {
		this.items = items;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}   
}
