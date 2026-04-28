package com.cruxtrack.backend.entity;

public class InventoryRequest {
    private String itemName;
    private int quantity;

    public InventoryRequest() {
    }

    public InventoryRequest(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

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
}
