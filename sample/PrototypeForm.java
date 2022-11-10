package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrototypeForm extends JFrame {
    private String asurite;
    private static Database database;
    private JButton createOrderButton;
    private JButton verifyButton;
    private JPanel mainPanel;
    private JCheckBox mushroomCheckBox;
    private JCheckBox onionCheckBox;
    private JCheckBox olivesCheckBox;
    private JCheckBox extraCheeseCheckBox;
    private JRadioButton pepperoniRadioButton;
    private JRadioButton vegetableRadioButton;
    private JRadioButton cheeseRadioButton;
    private JTextField asuriteTextField;
    private JButton submitOrderButton;
    private JTextField checkTextField;
    private JButton checkStatusButton;
    private JLabel PlacementLabel;
    private JLabel statusLabel;
    private JTextField placeOrderTextField;

    public PrototypeForm() {
        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asurite = asuriteTextField.getText();
                String pizzaType = "";
                String[] toppings = new String[4];

                if(mushroomCheckBox.isSelected()) {
                    toppings[0] = "Mushroom";
                }
                if(onionCheckBox.isSelected()) {
                    toppings[1] = "Mushroom";
                }
                if(olivesCheckBox.isSelected()) {
                    toppings[2] = "Olive";
                }
                if(extraCheeseCheckBox.isSelected()) {
                    toppings[3] = "Olive";
                }
                //=====================================
                if(pepperoniRadioButton.isSelected()) {
                    pizzaType = "Pepperoni";
                }
                else if(vegetableRadioButton.isSelected()) {
                    pizzaType = "Vegetable";
                }
                else if(cheeseRadioButton.isSelected()) {
                    pizzaType = "Cheese";
                }

                Pizza pizza = new Pizza(toppings, "Large", pizzaType);
                Order order = new Order(database.generateOrderNumber(), 99.99, asurite, pizza, 30);
                database.addOrder(order);
                PlacementLabel.setText("Your order number is: " + Integer.toString(order.getOrderNumber()));
            }
        });
        checkStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order = database.getOrder(Integer.parseInt(checkTextField.getText()));
                if(order == null) {
                    statusLabel.setText("Order not found");
                }
                else {
                    statusLabel.setText("Your order status: " + order.getStatus());
                }
            }
        });
    }

    public static void protoForm() {
            database = new Database();
            PrototypeForm h = new PrototypeForm();
            h.setContentPane(h.mainPanel);
            h.setTitle("ASU PIZZA");
            h.setSize(430, 280);
            h.setVisible(true);
            h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
}
