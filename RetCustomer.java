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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class RetCustomer extends Application {

	private AnchorPane root;
	private Scene scene;
	private Stage primaryStage;
	private Database db;
	private ChefGUI chefGui;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			InitialStatusWindow(db);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void InitialStatusWindow(Database db1) {
		try {
			
			db = db1; // Pass in database
			
			// Create and format Title
			Label title = new Label();
			title.setLayoutX(210.0);
			title.setLayoutY(140.0);
			title.setPrefHeight(64.0);
			title.setPrefWidth(172.0);
			title.setText("Enter Order Number");
			title.setTextAlignment(TextAlignment.CENTER);
			title.setFont(Font.font("Arial", 18.0));

			// Create and format a Text Field
			TextField tfOrderNumber = new TextField();
			tfOrderNumber.setLayoutX(210.0);
			tfOrderNumber.setLayoutY(187.0);
			tfOrderNumber.setPrefHeight(44.0);
			tfOrderNumber.setPrefWidth(172.0);
			tfOrderNumber.setPromptText("Order Number");

			// Create and format Title (counter for Text Field)
			Label counter = new Label();
			counter.setLayoutX(354.0);
			counter.setLayoutY(231.0);
			counter.setPrefHeight(18.0);
			counter.setPrefWidth(27.0);
			counter.setText("0/12");
			

			Button btn = new Button();
			btn.setLayoutX(396.0);
			btn.setLayoutY(196.0);
			btn.setMnemonicParsing(false);
			btn.setPrefHeight(30.0);
			btn.setPrefWidth(54.0);
			btn.setText("ENTER");

			root = new AnchorPane();
			scene = new Scene(root, 650, 500);

			// Put children components into AnchorPane
			root.getChildren().addAll(title, tfOrderNumber, counter, btn);

			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// 1. Parse text from user and check to see if text input is valid
					// 2. Check database to see if order number exists
					// 3. If true, set button to go to order status page

					// Make a order first
					String[] array = { "Mushrooms", "Pepperoni" };
					Pizza pizza = new Pizza(array, "Large", "Cheese");
					Order sampleOrder = new Order(1, 0, "pc01", pizza, 30); // first argument is the order number
					int order_num = sampleOrder.getOrderNumber();

					// Parse text from text field
					String orderString = tfOrderNumber.getText(); // store order number input
					String trimorderString = orderString.trim(); // trim whitespace
					int orderNum = Integer.parseInt(trimorderString); // Parse String into integer

					// Create database/Add order to database
					db = new Database();
					//db.createDatabase();
					db.addOrder(sampleOrder);
					
					
					
					// Check if particular order number is in database
					if (db.foundOrderNumber(order_num) == true) {
						
						if(orderNum == order_num) {
							Order order = db.getOrder(sampleOrder.getOrderNumber());
							//order.setStatus("PROCESSING");
							System.out.println(order.getStatus()); 
							
							if(order.getStatus().toUpperCase() == "PROCESSING") { // part 1 doc says ACCEPTED
								InitialWaitingWindow();
							}
							else if(order.getStatus().toUpperCase() == "COOKING") {
								IntermediateWaitingWindow();
							}
							else if(order.getStatus().toUpperCase() == "READY") {
								FinishedWaitingWindow();
							}
							else {
								//
							}
						}
						else {
							tfOrderNumber.clear();
							tfOrderNumber.setStyle("-fx-prompt-text-fill: red;");
							tfOrderNumber.setPromptText("Invalid Order Number");
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void InitialWaitingWindow() {
		try {
			
			// Make a order first
			String[] array = { "Mushrooms", "Pepperoni" };
			Pizza pizza_ = new Pizza(array, "Large", "Cheese");
			Order sampleOrder = new Order(1, 0, "pc01", pizza_, 30); // first argument is the order number
			
			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + sampleOrder.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + sampleOrder.getOrderNumber());
			for_Label.setFont(Font.font("Arial", 18.0));

			Label orderStatus = new Label();
			orderStatus.setLayoutX(54.0);
			orderStatus.setLayoutY(135.0);
			orderStatus.setPrefHeight(35.0);
			orderStatus.setPrefWidth(297.0);
			orderStatus.setText("Order Status: waiting to be accepted");
			orderStatus.setFont(Font.font("Arial", 18.0));
			orderStatus.setTextFill(Paint.valueOf("#f70000"));

			ProgressBar progressbar = new ProgressBar();
			progressbar.setLayoutX(54.0);
			progressbar.setLayoutY(217.0);
			progressbar.setPrefHeight(35.0);
			progressbar.setPrefWidth(231.0);
			progressbar.setProgress(0.33);

			// relative path for images
			String basePath = new File("").getAbsolutePath();
			Image image = new Image(new FileInputStream(basePath + "/forksundevil.png"));
			// Setting the image view for fork logo
			ImageView fork = new ImageView(image);

			// Format fork image
			fork.setFitHeight(110.0);
			fork.setFitWidth(94.0);
			fork.setLayoutX(496.0);
			fork.setLayoutY(14.0);
			fork.setPickOnBounds(true);
			fork.setPreserveRatio(true);

			Image image2 = new Image(new FileInputStream(basePath + "/pizza_poster.jpg"));
			// Setting the image view for pizza logo
			ImageView pizza = new ImageView(image2);

			// Format pizza image
			pizza.setFitHeight(250.0);
			pizza.setFitWidth(300.0);
			pizza.setLayoutX(352.0);
			pizza.setLayoutY(200.0);
			pizza.setPickOnBounds(true);
			pizza.setPreserveRatio(true);
			
			Button btn = new Button();
			btn.setLayoutX(142.0);
			btn.setLayoutY(297.0);
			btn.setMnemonicParsing(false);
			btn.setText("Back");

			root = new AnchorPane();
			scene = new Scene(root, 650, 500);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza, btn);

			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					InitialStatusWindow(db);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void IntermediateWaitingWindow() {
		try {
			
			// Make a order first
			String[] array = { "Mushrooms", "Pepperoni" };
			Pizza pizza_ = new Pizza(array, "Large", "Cheese");
			Order sampleOrder = new Order(1, 0, "pc01", pizza_, 30); // first argument is the order number
			
			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + sampleOrder.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + sampleOrder.getOrderNumber());
			for_Label.setFont(Font.font("Arial", 18.0));

			Label orderStatus = new Label();
			orderStatus.setLayoutX(54.0);
			orderStatus.setLayoutY(135.0);
			orderStatus.setPrefHeight(35.0);
			orderStatus.setPrefWidth(297.0);
			orderStatus.setText("Order Cooking!");
			orderStatus.setFont(Font.font("Arial", 18.0));
			orderStatus.setTextFill(Paint.valueOf("#f70000"));

			ProgressBar progressbar = new ProgressBar();
			progressbar.setLayoutX(54.0);
			progressbar.setLayoutY(217.0);
			progressbar.setPrefHeight(35.0);
			progressbar.setPrefWidth(231.0);
			progressbar.setProgress(0.66);

			// relative path for images
			String basePath = new File("").getAbsolutePath();
			Image image = new Image(new FileInputStream(basePath + "/forksundevil.png"));
			// Setting the image view for fork logo
			ImageView fork = new ImageView(image);

			// Format fork image
			fork.setFitHeight(110.0);
			fork.setFitWidth(94.0);
			fork.setLayoutX(496.0);
			fork.setLayoutY(14.0);
			fork.setPickOnBounds(true);
			fork.setPreserveRatio(true);

			Image image2 = new Image(new FileInputStream(basePath + "/chefcookingpizza.jpg"));
			// Setting the image view for pizza logo
			ImageView pizza = new ImageView(image2);

			// Format pizza image
			pizza.setFitHeight(250.0);
			pizza.setFitWidth(300.0);
			pizza.setLayoutX(352.0);
			pizza.setLayoutY(200.0);
			pizza.setPickOnBounds(true);
			pizza.setPreserveRatio(true);
			
			Button btn = new Button();
			btn.setLayoutX(142.0);
			btn.setLayoutY(297.0);
			btn.setMnemonicParsing(false);
			btn.setText("Back");

			root = new AnchorPane();
			scene = new Scene(root, 650, 500);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza, btn);

			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					InitialStatusWindow(db);
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void FinishedWaitingWindow() {
		try {
			
			// Make a order first
			String[] array = { "Mushrooms", "Pepperoni" };
			Pizza pizza_ = new Pizza(array, "Large", "Cheese");
			Order sampleOrder = new Order(1, 0, "pc01", pizza_, 30); // first argument is the order number
			
			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + sampleOrder.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + sampleOrder.getOrderNumber());
			for_Label.setFont(Font.font("Arial", 18.0));

			Label orderStatus = new Label();
			orderStatus.setLayoutX(54.0);
			orderStatus.setLayoutY(135.0);
			orderStatus.setPrefHeight(35.0);
			orderStatus.setPrefWidth(297.0);
			orderStatus.setText("Order is ready!");
			orderStatus.setFont(Font.font("Arial", 18.0));
			orderStatus.setTextFill(Paint.valueOf("#f70000"));

			ProgressBar progressbar = new ProgressBar();
			progressbar.setLayoutX(54.0);
			progressbar.setLayoutY(217.0);
			progressbar.setPrefHeight(35.0);
			progressbar.setPrefWidth(231.0);
			progressbar.setProgress(1); 

			// relative path for images
			String basePath = new File("").getAbsolutePath();
			Image image = new Image(new FileInputStream(basePath + "/forksundevil.png"));
			// Setting the image view for fork logo
			ImageView fork = new ImageView(image);

			// Format fork image
			fork.setFitHeight(110.0);
			fork.setFitWidth(94.0);
			fork.setLayoutX(496.0);
			fork.setLayoutY(14.0);
			fork.setPickOnBounds(true);
			fork.setPreserveRatio(true);

			Image image2 = new Image(new FileInputStream(basePath + "/pepperoni_pizza.jpeg"));
			// Setting the image view for pizza logo
			ImageView pizza = new ImageView(image2);

			// Format pizza image
			pizza.setFitHeight(250.0);
			pizza.setFitWidth(250.0);
			pizza.setLayoutX(352.0);
			pizza.setLayoutY(200.0);
			pizza.setPickOnBounds(true);
			pizza.setPreserveRatio(true);
			

			root = new AnchorPane();
			scene = new Scene(root, 650, 500);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza);

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
