import java.io.*;
import java.lang.*;
import java.time.*;

public class Order implements  java.io.Serializable {
    private int orderNumber;
    private String asu;
    private double price;
    private Pizza pizza;
    private LocalDateTime pickup;
    private String status;

    public Order(int orderNumber, double price, String asu, Pizza pizza, int pickup) {
        this.orderNumber = orderNumber;
        this.price = price;
        this.asu = asu;
        this.pizza = pizza;
        this.pickup = LocalDateTime.now().plusMinutes(pickup);
        this.status = "Processing";
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAsu() {
        return asu;
    }

    public void setAsu(String asu) {
        this.asu = asu;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public LocalDateTime getPickup() {
        return pickup;
    }

    public void setPickup(LocalDateTime pickup) {
        this.pickup = pickup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


