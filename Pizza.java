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
}
