package sample;

import javafx.event.ActionEvent; //**Need to import
import javafx.event.EventHandler; //**Need to import

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ChefGUI extends HBox
{
	private Database database; // Database that has order information
	
	private HBox[] boxes; // Horizontal Boxes to display list of orders
	
	private VBox listDisplay;  // Vertical Box that holds the list of orders
	private VBox orderDisplay; // Vertical Box that holds order details
	
	public ChefGUI(Database orderDatabase)
	{
		// Initialize some stuff
		Insets margin = new Insets(10);
		database = orderDatabase;
		
		// Order List Display
		//-------------------------------------------------------
		boxes = Createlist(database); // Initialize the HBox array with orders
		
		// Initialize Vertical Box that holds list of orders
		listDisplay = new VBox();
		listDisplay.setPadding(margin);
		listDisplay.setSpacing(10);
		
		// Add list of orders to Vertical Box that holds it
		for (int i = 0; i < boxes.length; i++)
		{
			if (boxes[i] != null)
			{
				listDisplay.getChildren().add(boxes[i]);
			}
		}
		
		// Initialize scroll pane with orders and stuff
		ScrollPane orderList = new ScrollPane();
		orderList.setContent(listDisplay);
		orderList.setPrefHeight(1080);
		orderList.setFitToHeight(true);
		orderList.setFitToWidth(true);
		
		// Create a button that refreshes the order list
		Button refreshButton = new Button("Refresh");
		refreshButton.setOnAction(new ButtonHandler());
		
		// Create a button that deletes finished orders from the order list
		Button deleteButton = new Button("Back");
		deleteButton.setOnAction(new ButtonHandler());
		
		// Create a box to hold the buttons
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(refreshButton, deleteButton);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);
		
		// Create a label that informs the chef what this is about
		Label incOrdersLabel = new Label("Incoming Orders");
		incOrdersLabel.setFont(new Font("Arial", 18));
		incOrdersLabel.setStyle("-fx-font-weight: bold");
		incOrdersLabel.setAlignment(Pos.CENTER);
		
		// Put the label and the list in a VBox
		VBox queueBox = new VBox();
		queueBox.setSpacing(10);
		queueBox.setAlignment(Pos.TOP_CENTER);
		queueBox.getChildren().addAll(incOrdersLabel, orderList, buttonBox);
		VBox.setVgrow(queueBox, Priority.ALWAYS);
		//-------------------------------------------------------

		// Detailed Order View
		//-------------------------------------------------------
		// VBox to hold the order details
		// Why is it like this?  So that I can wipe out all the stuff inside it
		orderDisplay = new VBox();
		VBox.setVgrow(orderDisplay, Priority.ALWAYS);
		
		// Create a label that informs the chef what THIS section is about
		Label currOrderLabel = new Label("Current Order");
		currOrderLabel.setFont(new Font("Arial", 18));
		currOrderLabel.setStyle("-fx-font-weight: bold");
		
		// Create a VBox that holds the label and the order details
		VBox detailBox = new VBox();
		detailBox.setSpacing(10);
		detailBox.setAlignment(Pos.TOP_CENTER);
		detailBox.getChildren().addAll(currOrderLabel, orderDisplay);
		//-------------------------------------------------------

		// Putting it together
		//-------------------------------------------------------
		this.getChildren().addAll(queueBox, detailBox);
		this.setPadding(margin);
		HBox.setHgrow(queueBox, Priority.ALWAYS);
		HBox.setHgrow(detailBox, Priority.ALWAYS);
	}
	
	/* Method to refresh the list */
	private void RefreshList()
	{
		boxes = Createlist(database);
		
		listDisplay.getChildren().clear();
		
		for (int i = 0; i < boxes.length; i++)
		{
			if (boxes[i] != null)
			{
				listDisplay.getChildren().add(boxes[i]);
			}
		}
	}
	
	/* Use a method to create the HBoxes that comprise the order list */
	private HBox[] Createlist(Database database)
	{
		Insets margin = new Insets(10); // margin stuff
		
		// Get orders from the database and create an array to hold the HBoxes
		ArrayList<Order> orders = database.getAllOrders();
		HBox[] listArray = new HBox[orders.size()];
		
		int skipped = 0; // Used to put the orders in the proper index
		
		// Fill the HBox array with... HBOXES!!!
		for (int i = 0; i < orders.size(); i++)
		{
			Order currentOrder = orders.get(i); // Get the current order
			
			// Make an HBox if the order status is Processed or Cooking
			if (currentOrder.getStatus().compareTo("Processed") == 0 ||
			    currentOrder.getStatus().compareTo("Cooking") == 0)
			{
				// Create a label to hold the ASURITE ID
				Label asuriteLabel = new Label(currentOrder.getAsu());
				asuriteLabel.setFont(new Font("Arial", 12));
				
				//  Create a button that lets the chef see the order details
				Button startButton;
				if (currentOrder.getStatus().compareTo("Cooking") == 0)
				{
					startButton = new Button("Cooking " + currentOrder.getOrderNumber());
				}
				else
				{
					startButton = new Button("Start " + currentOrder.getOrderNumber());
				}
				startButton.setOnAction(new ButtonHandler());
				
				// Set up the HBox that holds the above info.
				HBox listItem = new HBox();
				listItem.setAlignment(Pos.CENTER);
				listItem.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
				listItem.setPadding(margin);
				listItem.setSpacing(10);
				listItem.getChildren().addAll(asuriteLabel, startButton);
				HBox.setHgrow(listItem, Priority.ALWAYS);
				
				listArray[i - skipped] = listItem; // Place the new HBox in the right index in the HBox array
			}
			
			// If the order is finished, don't make an HBox for it
			else
			{
				skipped++;
			}
		}
		
		return listArray; // Return the completed list array
	}
	
	/* Use a method to generate a container to hold the details of an order */
	private VBox CreateDetail(Order order)
	{
		// Create a label to show the ASURITE ID
		Label asurite = new Label("Customer: " + order.getAsu());
		asurite.setFont(new Font("Arial", 12));
		
		// Create a label to show the Order Number
		Label orderNumDisp = new Label("Order Number: " + order.getOrderNumber());
		orderNumDisp.setFont(new Font("Arial", 12));
		
		// Create a label to show the Pizza Type
		Label pizzaType = new Label("Pizza Type: " + order.getPizza().getPizzaType());
		pizzaType.setFont(new Font("Arial", 12));
		
		// Create a label to show that the next section is about toppings
		Label toppingLabel = new Label("Toppings: ");
		toppingLabel.setFont(new Font("Arial", 12));
		
		// Create a VBox to hold toppings information
		VBox toppingsBox = new VBox();
		toppingsBox.setSpacing(10);
		toppingsBox.getChildren().add(toppingLabel);
		
		// Scour the pizza object looking for all its toppings
		for (int i = 0; i < order.getPizza().getToppings().length; i++)
		{
			// Create a label for the current topping
			Label topping = new Label("     " + order.getPizza().getToppings()[i]);
			topping.setFont(new Font("Arial", 12));
			toppingsBox.getChildren().add(topping);
		}
		
		// Create a label to show the pickup time
		Label pickupTime = new Label(order.getPickup().toString());
		pickupTime.setFont(new Font("Arial", 12));
		
		// Create a button that allows the chef to finish the order
		Button finishButton = new Button("Finish " + order.getOrderNumber());
		finishButton.setOnAction(new ButtonHandler());
		
		// Finalize the VBox that'll hold all the above information
		VBox wrapper = new VBox();
		wrapper.setSpacing(10);
		wrapper.getChildren().addAll(asurite, orderNumDisp, pizzaType, toppingsBox, pickupTime, finishButton);
		
		return wrapper; // Return said VBox
	}
	
	/* Handles button presses : */
	private class ButtonHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			String text = ((Button) event.getSource()).getText(); // Get the text in the button
			
			// Check if the clicked button was the Start button
			if (text.substring(0, 1).compareTo("S") == 0)
			{
				// Update Order Status and Display Details
				//-------------------------------------------------
				int ordernum = Integer.parseInt(text.substring(6));
				database.changeOrderStatus(ordernum, "Cooking");
				((Button) event.getSource()).setText("Cooking " + ordernum);
				
				Order currentOrder = database.getOrder(ordernum);
				
				VBox details = CreateDetail(currentOrder);
				
				orderDisplay.getChildren().clear();
				orderDisplay.getChildren().add(details);
				//-------------------------------------------------
			}
			
			// Check if the clicked button was the Cooking button
			else if (text.substring(0, 1).compareTo("C") == 0)
			{
				// Just Display Details
				//-------------------------------------------------
				int ordernum = Integer.parseInt(text.substring(8));
				
				Order currentOrder = database.getOrder(ordernum);
				
				VBox details = CreateDetail(currentOrder);
				
				orderDisplay.getChildren().clear();
				orderDisplay.getChildren().add(details);
				//-------------------------------------------------
			}
			
			// Check if the clicked button was the refresh button
			else if(text.substring(0, 1).compareTo("R") == 0)
			{
				RefreshList(); // Eat Fresh Refresh (I dunno man it's 6 am and I haven't slept...)
			}
			
			// Check if the clicked button was the Finish Button
			else if (text.substring(0, 1).compareTo("F") == 0)
			{
				// Update Order Status and remove it from the list
				//-------------------------------------------------
				int ordernum = Integer.parseInt(text.substring(7)); // Get the order number from the button label
				
				database.changeOrderStatus(ordernum, "Finished");   // Change the status of the order
				
				((Button) event.getSource()).setText("Email Sent"); // Change the button text to let the chef know an email was sent
				((Button) event.getSource()).setDisable(true);      // Don't let the chef click this button again
				
				RefreshList(); // Update the list
				//-------------------------------------------------
			}
			
			else if (text.substring(0, 1).compareTo("B") == 0)
			{
				Main main = new Main();
				
				try
				{
					main.toInitialGUI();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			
		}
	}
}
