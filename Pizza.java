package sample;

import java.util.Arrays;

public class Pizza implements java.io.Serializable
{
    private String[] toppings;
    private String pizzaSize;
    private String pizzaType;

    public Pizza(String[] toppings, String pizzaSize, String pizzaType)
    {
        this.toppings = toppings;
        this.pizzaSize = pizzaSize;
        this.pizzaType = pizzaType;
    }

    @Override
    public String toString() {
        String str = "\n";
        str = str + "Pizza size: " + pizzaSize + "\n";
        str = str + "Pizza type: " + pizzaType + "\n";
        str = str + "Toppings: " + Arrays.toString(toppings);
        return str;
    }
    public String[] getToppings() {
        return toppings;
    }

    public void setToppings(String[] toppings) {
        this.toppings = toppings;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }
}
