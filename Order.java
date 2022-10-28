import java.io.*;
import java.lang.*;
import java.time.*;

public class Order implements  java.io.Serializable {
    public int orderNumber;
    public String asu;
    public double price;
    public Pizza pizza;
    public LocalDateTime pickup;
    public String status;

    public Order(int orderNumber, double price, String asu, Pizza pizza, int pickup) {
        this.orderNumber = orderNumber;
        this.price = price;
        this.asu = asu;
        this.pizza = pizza;
        this.pickup = LocalDateTime.now().plusMinutes(pickup);
        this.status = "Processing";
    }
}


