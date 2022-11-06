public class Pizza implements java.io.Serializable
{
    public String[] toppings;
    public String pizzaSize;
    public String pizzaType;

    public Pizza(String[] toppings, String pizzaSize, String pizzaType)
    {
        this.toppings = toppings;
        this.pizzaSize = pizzaSize;
        this.pizzaType = pizzaType;
    }
}
