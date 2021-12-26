package uz.pdp.model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int clientId;
    private String clientName;
    private String clothName;
    private int clothQuantity;
    private double clothPrice;
    private String payTypeName;
    private LocalDateTime localTime;

    public Transaction() {
    }

    public Transaction(int id, int clientId, String clientName, String clothName, int clothQuantity, double clothPrice, String payTypeName) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clothName = clothName;
        this.clothQuantity = clothQuantity;
        this.clothPrice = clothPrice;
        this.payTypeName = payTypeName;
    }

    public Transaction(int id, int clientId, String clientName, String clothName, int clothQuantity,
                       double clothPrice, String payTypeName, LocalDateTime localTime) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clothName = clothName;
        this.clothQuantity = clothQuantity;
        this.clothPrice = clothPrice;
        this.payTypeName = payTypeName;
        this.localTime = localTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public int getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(int clothQuantity) {
        this.clothQuantity = clothQuantity;
    }

    public double getClothPrice() {
        return clothPrice;
    }

    public void setClothPrice(double clothPrice) {
        this.clothPrice = clothPrice;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clothName='" + clothName + '\'' +
                ", clothQuantity=" + clothQuantity +
                ", clothPrice=" + clothPrice +
                ", payTypeName='" + payTypeName + '\'' +
                ", localTime=" + localTime +
                '}';
    }
}
