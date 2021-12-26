package uz.pdp.model;

import uz.pdp.model.enums.Size;

public class Cloth implements Comparable<Cloth>{
    //Properties
    private int id;
    private String name;
    private String color;
    private Size size;
    private double price;
    private double discount;
    private int quantity;
    private int ownerId;

    public Cloth() {
    }

    public Cloth(int id, String name, String color, Size size, double price, double discount, int quantity, int ownerId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
        this.ownerId = ownerId;
    }

    public Cloth(int id, String name, String color, Size size, double price, double discount, int quantity) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Cloth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int compareTo(Cloth o) {
        return 0;
    }
}
