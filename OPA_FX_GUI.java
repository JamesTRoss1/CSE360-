package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class OPA_FX_GUI extends HBox{
    private Database database; // Will be retrieving/setting info from this

    private HBox[] boxes; // Borrowing the technique Ariel invented to display orders using hboxes
    private HBox[] chefBoxes;
    private VBox incomingQueue; // The orders that need to be approved
    private VBox chefQueue; // The orders that the Chef has received (from the processing agent)
    Label detailsLabelOne;
    Label detailsLabelTwo;
    Label chefDetailsLabelOne;
    Label chefDetailsLabelTwo;

    public OPA_FX_GUI(Database givenDatabase) {
        database = givenDatabase; // Will need to be given in the program's Main.java
        Insets margin = new Insets(10); // For aesthetic purposes
        //IDEA: have two ArrayLists, one for incomingQueue, one for chefQueue?

        // populate (for the first time) our incoming orders
        boxes = makeBoxes(database);

        // Initialize incomingQueue
        incomingQueue = new VBox();
        incomingQueue.setPadding(margin);
        incomingQueue.setSpacing(10); // Following Ariel's spacing for uniformity purposes

        // Populate incomingQueue
        for (int i = 0; i < boxes.length; i++)
        {
            if (boxes[i] != null)
            {
                incomingQueue.getChildren().add(boxes[i]);
            }
        }

        //Use a scroll pane to hold the incomingQueue
        ScrollPane incomingScroll = new ScrollPane();
        incomingScroll.setContent(incomingQueue);
        incomingScroll.setPrefHeight(1850);
        incomingScroll.setFitToHeight(true);
        incomingScroll.setFitToWidth(true);

        // Will update incoming orders/chef's orders through manually clicking a button
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(new buttonEvent());

        // Label that will be used in conjunction with incomingQueue
        Label incomingLabel = new Label("Incoming Orders");
        incomingLabel.setFont(new Font("Arial", 18));
        incomingLabel.setStyle("-fx-font-weight: bold");
        incomingLabel.setAlignment(Pos.CENTER);

        detailsLabelOne = new Label("");
        detailsLabelOne.setFont(new Font("Arial", 15));
        detailsLabelTwo = new Label("");
        detailsLabelTwo.setFont(new Font("Arial", 15));
        incomingLabel.setStyle("-fx-font-weight: bold");

        // Put everything related to incomingQueue together
        VBox incomingBox = new VBox();
        incomingBox.setSpacing(10);
        incomingBox.setAlignment(Pos.TOP_CENTER);
        incomingBox.getChildren().addAll(incomingLabel, incomingScroll, refreshButton, detailsLabelOne, detailsLabelTwo);
        VBox.setVgrow(incomingBox, Priority.ALWAYS);
        //--------------------
        chefQueue = new VBox();

        chefQueue.setPadding(margin);
        chefQueue.setSpacing(10);
        ScrollPane chefScroll = new ScrollPane();
        chefScroll.setContent(chefQueue);
        chefScroll.setPrefHeight(1080);
        chefScroll.setFitToHeight(true);
        chefScroll.setFitToWidth(true);
        VBox.setVgrow(chefQueue, Priority.ALWAYS);
        Label chefLabel = new Label("Chef's Queue");
        chefLabel.setFont(new Font("Arial", 18));
        chefLabel.setStyle("-fx-font-weight: bold");

        chefDetailsLabelOne = new Label("");
        chefDetailsLabelOne.setFont(new Font("Arial", 15));
        chefDetailsLabelTwo = new Label("");
        chefDetailsLabelTwo.setFont(new Font("Arial", 15));

        VBox chefBox = new VBox();
        chefBox.setSpacing(10);
        chefBox.setAlignment(Pos.TOP_CENTER);
        chefBox.getChildren().addAll(chefLabel, chefQueue);
        chefBox.setAlignment(Pos.BOTTOM_CENTER);
        chefBox.getChildren().addAll(chefDetailsLabelOne, chefDetailsLabelTwo);

        chefBoxes = makeChefQueue(database);
        for (int i = 0; i < boxes.length; i++)
        {
            if (chefBoxes[i] != null)
            {
                chefQueue.getChildren().add(chefBoxes[i]);
            }
        }

        this.getChildren().addAll(incomingBox, chefBox);
        this.setPadding(margin);
        HBox.setHgrow(incomingBox, Priority.ALWAYS);
        HBox.setHgrow(chefBox, Priority.ALWAYS);
    }

    //Initially creates and populates the HBoxes that display each order. If any new orders are made, we will need
    //another function to update said HBoxes, otherwise the list of orders will be outdated.
    private HBox[] makeBoxes(Database database) {
        Insets margin = new Insets(10); // More aesthetic stuff

        ArrayList<Order> orders = database.getAllOrders(); // Store all orders in an ArrayList for ease of iterating through them
        HBox[] incomingOrdersArray = new HBox[orders.size()]; // Array to be returned, containing all of the incoming orders

        int notIncluded = 0;
        // Populate incomingOrdersArray with more HBoxes (since we want a button next to each order that can be used to send it to the Chef, instead of needing one at the button of the list
        for(int i = 0; i < orders.size(); i++) {
            Order currentOrder = orders.get(i);

            //each order will have value "unprocessed" before it is sent to the chef. Will change this to "processing"
            if(currentOrder.getStatus().compareTo("Processing") == 0) {
                // need a label for asurite, and possibly one for toppings/type
                // also need a button that sends it to the chef's queue
                Label asuriteLabel = new Label(currentOrder.getAsu());
                asuriteLabel.setFont(new Font("Arial", 12));

                Label toppingsLabel = new Label("Toppings: ");


                // The following button will be used to send the order to the chefQueue
                Button sendButton = new Button("Send to Chef: " + currentOrder.getOrderNumber());
                sendButton.setOnAction(new buttonEvent()); // Allows our button to do stuff

                Button detailsButton = new Button("View More Info for " + currentOrder.getOrderNumber());
                detailsButton.setOnAction(new buttonEvent()); // Will be used to display more order information upon request

                // Finally, put it all together
                HBox currentOrderBox = new HBox();
                currentOrderBox.setAlignment(Pos.CENTER);
                currentOrderBox.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
                currentOrderBox.setPadding(margin);
                currentOrderBox.setSpacing(10);
                currentOrderBox.getChildren().addAll(asuriteLabel, detailsButton, sendButton);
                HBox.setHgrow(currentOrderBox, Priority.ALWAYS);

                incomingOrdersArray[i-notIncluded] = currentOrderBox;
            }
            else {
                notIncluded++;
            }
        }

        return incomingOrdersArray;
    }

    private HBox[] makeChefQueue(Database database) {
       /* Label informationLabel = new Label("Asurite: " + order.getAsu() + " | Order number: " + order.getOrderNumber() + " | Status: " + order.getStatus());
        informationLabel.setFont(new Font("Arial", 12));
        VBox chefOrderDetailsBox = new VBox();
        chefOrderDetailsBox.setSpacing(10);
        chefOrderDetailsBox.getChildren().addAll(informationLabel);
        return chefOrderDetailsBox;*/
        //=================================================

        Insets margin = new Insets(10); // More aesthetic stuff

        ArrayList<Order> orders = database.getAllOrders(); // Store all orders in an ArrayList for ease of iterating through them
        HBox[] chefOrdersArray = new HBox[orders.size()]; // Array to be returned, containing all of the incoming orders

        int notIncluded = 0;
        chefQueue.getChildren().clear();
        // Populate incomingOrdersArray with more HBoxes (since we want a button next to each order that can be used to send it to the Chef, instead of needing one at the button of the list
        for(int i = 0; i < orders.size(); i++) {
            Order currentOrder = orders.get(i);

            //each order will have value "unprocessed" before it is sent to the chef. Will change this to "processing"
            if(currentOrder.getStatus().compareTo("Processed") == 0) {
                // need a label for asurite, and possibly one for toppings/type
                // also need a button that sends it to the chef's queue
                Label asuriteLabel = new Label(currentOrder.getAsu());
                asuriteLabel.setFont(new Font("Arial", 12));

                Button detailsButton = new Button("Display Info for " + currentOrder.getOrderNumber());
                detailsButton.setOnAction(new buttonEvent()); // Will be used to display more order information upon request

                // Finally, put it all together
                HBox chefQueueBox = new HBox();
                chefQueueBox.setAlignment(Pos.CENTER);
                chefQueueBox.setBackground(new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
                chefQueueBox.setPadding(margin);
                chefQueueBox.setSpacing(10);
                chefQueueBox.getChildren().addAll(asuriteLabel, detailsButton);
                HBox.setHgrow(chefQueueBox, Priority.ALWAYS);

                chefOrdersArray[i-notIncluded] = chefQueueBox;
            }
            else {
                notIncluded++;
            }
        }

        return chefOrdersArray;


    }

    // whipe our queues and re-check for updated items
    private void refresh() {
        // First, refresh incoming queue
        boxes = makeBoxes(database);
        incomingQueue.getChildren().clear();
        for(int i = 0; i < boxes.length; i ++) {
            if(boxes[i] != null) {
                incomingQueue.getChildren().add(boxes[i]);
            }
        }

        // Secondly, refresh chef's queue
        chefBoxes = makeChefQueue(database);
        chefQueue.getChildren().clear();
        for(int i = 0; i < chefBoxes.length; i ++) {
            if(chefBoxes[i] != null) {
                chefQueue.getChildren().add(chefBoxes[i]);
            }
        }
    }
    private class buttonEvent implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            String buttonText = ((Button) e.getSource()).getText();
            // Sketchy, but get order number from the button pressed
            // if refreshButton is pressed...
            if(buttonText.compareTo("Refresh") == 0) {
                detailsLabelOne.setText("");
                detailsLabelTwo.setText("");
                refresh();
            }
            else if(buttonText.substring(0,1).compareTo("S") == 0) {
                // set status to "Processing". Then, call refresh().

                int orderNum = Integer.parseInt(buttonText.substring(14));
                database.changeOrderStatus(orderNum, "Processed");

                // Send said order to chef's queue
                /*Order currentOrder = database.getOrder(orderNum);
                VBox chefOrderDetailsBox = makeChefQueue(currentOrder);
                chefQueue.getChildren().add(chefOrderDetailsBox);
                detailsLabel.setText("");*/

                Order currentOrder = database.getOrder(orderNum);
                // Populate the Chef queue (which is the entire essence of what this button does)
                chefBoxes = makeChefQueue(database);
                for (int i = 0; i < boxes.length; i++)
                {
                    if (chefBoxes[i] != null)
                    {
                        chefQueue.getChildren().add(chefBoxes[i]);
                    }
                }

                refresh();
            }
            else if(buttonText.substring(0,1).compareTo("V") == 0) {
                // Display more information has been requested
                int orderNum = Integer.parseInt(buttonText.substring(19));
                Order currentOrder = database.getOrder(orderNum);
                String toppings = "";

                for(int i = 0; i < currentOrder.getPizza().getToppings().length; i++) {
                    toppings += " " + currentOrder.getPizza().getToppings()[i];
                }

                detailsLabelOne.setText("ASURITE ID: " + currentOrder.getAsu() + ", Order Number: " + currentOrder.getOrderNumber());
                detailsLabelTwo.setText(currentOrder.getPizza().getPizzaSize() + ", " + currentOrder.getPizza().getPizzaType() + ", Toppings: "
                        + toppings);


            }
            else if(buttonText.substring(0,1).compareTo("D") == 0) {
                int orderNum = Integer.parseInt(buttonText.substring(17));
                Order currentOrder = database.getOrder(orderNum);
                String toppings = "";

                for(int i = 0; i < currentOrder.getPizza().getToppings().length; i++) {
                    toppings += " " + currentOrder.getPizza().getToppings()[i];
                }

                //chefDetailsLabel.setText("ASURITE ID: " + currentOrder.getAsu() + ", Order Number: " + currentOrder.getOrderNumber() + ", " + currentOrder.getPizza().getPizzaSize() + ", " + currentOrder.getPizza().getPizzaType() + ", Toppings: "
                //      + toppings);
                chefDetailsLabelOne.setText("ASURITE ID: " + currentOrder.getAsu() + ", Order Number: " + currentOrder.getOrderNumber());
                chefDetailsLabelTwo.setText(currentOrder.getPizza().getPizzaSize() + ", " + currentOrder.getPizza().getPizzaType() + ", Toppings: "
                        + toppings);
            }
        }
    }
}