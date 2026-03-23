package com.mangement.restaurant.dto;

import lombok.Data;
import java.util.List;

@Data
public class BillDTO {
    private Long orderId;
    private int tableNumber;
    private String status;
    private List<BillItemDTO> items;
    private double totalPrice;

    @Data
    public static class BillItemDTO {
        private String itemName;
        private int quantity;
        private double unitPrice;
        private double subtotal;
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public double getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(double unitPrice) {
			this.unitPrice = unitPrice;
		}
		public double getSubtotal() {
			return subtotal;
		}
		public void setSubtotal(double subtotal) {
			this.subtotal = subtotal;
		}
        
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BillItemDTO> getItems() {
		return items;
	}

	public void setItems(List<BillItemDTO> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "BillDTO [orderId=" + orderId + ", tableNumber=" + tableNumber + ", status=" + status + ", items="
				+ items + ", totalPrice=" + totalPrice + "]";
	}
    
}
