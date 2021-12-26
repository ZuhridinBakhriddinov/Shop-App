package uz.pdp.model;

public class PayType {
    int id;
    String name;
    double commissionFee;

    public PayType() {
    }

    public PayType(int id, String name, double commissionFee) {
        this.id = id;
        this.name = name;
        this.commissionFee = commissionFee;
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

    public double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(double commissionFee) {
        this.commissionFee = commissionFee;
    }

    @Override
    public String toString() {
        return "PayType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commissionFee=" + commissionFee +
                '}';
    }
}
