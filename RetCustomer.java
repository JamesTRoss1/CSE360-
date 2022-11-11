package sample;

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

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void go(int index, int tfOrderNumber) {
		Database db = new Database();
		if(index == 0) {
			ProcessingWaitingWindow(db, tfOrderNumber);
		}
		if(index == 1) {
			InitialWaitingWindow(db, tfOrderNumber);
		}
		if(index == 2) {
			IntermediateWaitingWindow(db, tfOrderNumber);
		}
		if(index == 3) {
			FinishedWaitingWindow(db, tfOrderNumber);
		}
	}

	public void ProcessingWaitingWindow(Database db1, int tfOrderNumber) {
		try {

			// Initialize again
			db = db1;
			int argtfOrderNumber = tfOrderNumber;
			Order order = db.getOrder(argtfOrderNumber);


			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + order.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + order.getOrderNumber());
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
			progressbar.setProgress(0.25);

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
			scene = new Scene(root, 1280, 720);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza, btn);


			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ReturningCustomer rtc = new ReturningCustomer();
					rtc.start(primaryStage);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void InitialWaitingWindow(Database db1, int tfOrderNumber) {
		try {

			// Initialize again
			db = db1;
			int argtfOrderNumber = tfOrderNumber;
			Order order = db.getOrder(argtfOrderNumber);


			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + order.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + order.getOrderNumber());
			for_Label.setFont(Font.font("Arial", 18.0));

			Label orderStatus = new Label();
			orderStatus.setLayoutX(54.0);
			orderStatus.setLayoutY(135.0);
			orderStatus.setPrefHeight(35.0);
			orderStatus.setPrefWidth(297.0);
			orderStatus.setText("Order Status: waiting to be cooked");
			orderStatus.setFont(Font.font("Arial", 18.0));
			orderStatus.setTextFill(Paint.valueOf("#f70000"));

			ProgressBar progressbar = new ProgressBar();
			progressbar.setLayoutX(54.0);
			progressbar.setLayoutY(217.0);
			progressbar.setPrefHeight(35.0);
			progressbar.setPrefWidth(231.0);
			progressbar.setProgress(0.50);

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
			scene = new Scene(root, 1280, 720);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza, btn);


			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ReturningCustomer rtc = new ReturningCustomer();
					rtc.start(primaryStage);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void IntermediateWaitingWindow(Database db1, int tfOrderNumber) {
		try {

			// Initialize again
			db = db1;
			int argtfOrderNumber = tfOrderNumber;
			Order order = db.getOrder(argtfOrderNumber);

			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + order.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + order.getOrderNumber());
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
			progressbar.setProgress(0.75);

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
			scene = new Scene(root, 1280, 720);

			root.getChildren().addAll(orderNumber, for_Label, orderStatus, progressbar, fork, pizza, btn);

			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ReturningCustomer rtc = new ReturningCustomer();
					rtc.start(primaryStage);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void FinishedWaitingWindow(Database db1, int tfOrderNumber) {
		try {

			db = db1;
			int argtfOrderNumber = tfOrderNumber;
			Order order = db.getOrder(argtfOrderNumber);


			Label orderNumber = new Label();
			orderNumber.setLayoutX(54.0);
			orderNumber.setLayoutY(39.0);
			orderNumber.setPrefHeight(35.0);
			orderNumber.setPrefWidth(254.0);
			orderNumber.setText("For: " + order.getAsu());
			orderNumber.setFont(Font.font("Arial", 18.0));

			Label for_Label = new Label();
			for_Label.setLayoutX(54.0);
			for_Label.setLayoutY(74.0);
			for_Label.setPrefHeight(35.0);
			for_Label.setPrefWidth(254.0);
			for_Label.setText("Order Number: " + order.getOrderNumber());
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