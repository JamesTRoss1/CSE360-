package application;

import java.io.File;  
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class ReturningCustomer extends Application {
	
	private AnchorPane root;
	private Scene scene;
	private Stage primaryStage;
	private Database db;
	private Customergui newCustomerGui;
	private RetCustomer retCustomer;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			CustomerGUI(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void CustomerGUI(Database db1) {
	    try {
	    	
	    	db = db1; // Pass in database
	    	
	    	 // Create and format Title
	    	 Label title = new Label();
		     title.setLayoutX(201.0);
		     title.setLayoutY(14.0);
		     title.setPrefHeight(35.0);
		     title.setPrefWidth(252.0);
		     title.setText("Returning Customer");
		     title.setTextAlignment(TextAlignment.CENTER);
		     title.setFont(Font.font(24));
	    	
	    	// relative path for images
	    	String basePath = new File("").getAbsolutePath();
	        Image image = new Image(new FileInputStream(basePath + "/forksundevil.png"));
	        //Setting the image view for fork logo
	        ImageView fork = new ImageView(image);
	        
	        // Format fork image
	        fork.setFitHeight(167.0);
	        fork.setFitWidth(97.0);
	        fork.setLayoutX(99.0);
	        fork.setLayoutY(67.0);
	        fork.setPickOnBounds(true);
	        fork.setPreserveRatio(true);
	        
	        Image image2 = new Image(new FileInputStream(basePath + "/pepperoni_pizza.jpeg"));
	        //Setting the image view for pizza logo
	        ImageView pizza = new ImageView(image2);
	        
	        // Format pizza image
	        pizza.setFitHeight(130);
	        pizza.setFitWidth(180);
	        pizza.setLayoutX(376.0);
	        pizza.setLayoutY(86.0);
	        pizza.setPickOnBounds(true);
	        pizza.setPreserveRatio(true);
	        
	        // Create and format New Order button
	        Button btnNewOrder = new Button();
	        btnNewOrder.setLayoutX(247.0);
	        btnNewOrder.setLayoutY(101.0);
	        btnNewOrder.setMnemonicParsing(false);
	        //btnNewOrder.setOnAction()
	        btnNewOrder.setPrefHeight(50.0);
	        btnNewOrder.setPrefWidth(106.0);
	        btnNewOrder.setText("Place New Order");
	        
	        // Create and format Pizza Status button
	        Button btnPizzaStatus = new Button();
	        btnPizzaStatus.setLayoutX(235.0);
	        btnPizzaStatus.setLayoutY(307.0);
	        btnPizzaStatus.setMnemonicParsing(false);
	        //btnPizzaStatus.setOnAction()
	        btnPizzaStatus.setPrefHeight(35.0);
	        btnPizzaStatus.setPrefWidth(120.0);
	        btnPizzaStatus.setText("Check Pizza Status");
	        
	        // Create and format a Text Field 
	        TextField tfOrderNumber = new TextField();
	        tfOrderNumber.setLayoutX(213.0);
	        tfOrderNumber.setLayoutY(257.0);
	        tfOrderNumber.setPrefHeight(35.0);
	        tfOrderNumber.setPrefWidth(163.0);
	        tfOrderNumber.setPromptText("Order Number");
	        
	        root = new AnchorPane();
	        scene = new Scene(root, 650, 400); 
	        
	        // Put children components into AnchorPane
	        root.getChildren().addAll(title, btnNewOrder, btnPizzaStatus, tfOrderNumber, fork, pizza);
	        
	        // If New Order button is pressed, go to the New Customer Order Page
	        btnNewOrder.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	Database database = new Database();
	            	newCustomerGui = new Customergui(database);
	            	scene = new Scene(newCustomerGui, 1080, 720);
	            	primaryStage.setScene(scene);
	                primaryStage.show();
	            }
	        });
	        
	        btnPizzaStatus.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	
	            	String[] array = {"Mushrooms", "Pepperoni"};
	            	Pizza pizza = new Pizza(array, "Large", "Cheese");
	            	Order sampleOrder = new Order(1, 0, "pc01", pizza, 30);
	            	
	            	int pcordernum = sampleOrder.getOrderNumber(); // order number is 1 in this case
	            	String status = sampleOrder.getStatus(); // default: "Processing"
	            	
	            	String orderString = tfOrderNumber.getText(); // store order number input
	            	
	        		int orderNum = Integer.parseInt(orderString);
	        		
	        		// Checks if the orderNumber matches any from the db and prints out 
	        		// order status if match is found. Otherwise, print out some error statement.
	        		if(pcordernum == orderNum) {	
		            	retCustomer = new RetCustomer();
		            	retCustomer.start(primaryStage);
	        		}
	        		//else if() {
	        			// Implement some error
	        		//}
	        		else {
	        			tfOrderNumber.clear();
						tfOrderNumber.setStyle("-fx-prompt-text-fill: red;");
						tfOrderNumber.setPromptText("Invalid Order Number");
	        		}
	                
	        		
	        		
	            }
	        });
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    primaryStage.setScene(scene);
        primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
