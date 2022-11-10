package application;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

import java.awt.event.ActionListener;
import java.text.NumberFormat;

//import java.awt.event.ActionListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Customergui extends GridPane {
	private Label label1;
	private Label label2;// label for: "pizzaType, Toppings (underlined, and color orange"
	private Label label3; // label for ASURITEID
	
	private Label label5 ;
	private Label label6; // pickup time
	private TextField text; // Textfield for ASURITEID
	private Button button; //Big Order button 
	private ToggleGroup t1 ; // for the pizza type
	private ToggleGroup t2 ; // for the Toppings 
	private RadioButton r1  ;
	private RadioButton r2;
	private RadioButton r3;
	private CheckBox r4;
	private CheckBox r5; 
	private CheckBox r6;
	private CheckBox r7;
	private Label placementLabel;
	private ComboBox<String>  pickupTime; // pickuptime 
	private String  pickuptime, asurite; // what the customer picked
	private Pizza pizza;
	private Database orderDatabase;
	private double price; 

	
	public Customerpage(Database data){
	// Database
		orderDatabase = data;
	// Pizza type 
		r1= new RadioButton("Cheese");
		r2 = new RadioButton("Pepperoni");
		r3 = new RadioButton("Vegetable");

		t1 = new ToggleGroup();
		r1.setToggleGroup(t1);
		r1.setUserData("Cheese");
		r2.setToggleGroup(t1);
		r2.setUserData("Pepporoni");
		r3.setToggleGroup(t1);
		r3.setUserData("Vegetable");
		

	//Toppings
		r4 = new CheckBox("Mushrooms");
		r5 = new CheckBox("Onions");
		r6 = new CheckBox("Olives");
		r7 = new  CheckBox("Sausages");
		
		
	// pickUpTime
		pickupTime =  new ComboBox<String>();
		pickupTime.getItems().addAll("Default: 30 minutes", "45 minutes", "60 minutes");
		pickupTime.setValue("Default: 30 minutes");
		pickupTime.setMinWidth(30);
		
	//	PickuptimeHandler handler1 = new PickuptimeHandler();
//		pickupTime.setOnAction(handler1);
	//	pickupTime.setMinHeight(50);
		
	// Labels
		label1 = new Label("Pizzatype");
		label1.setUnderline(true);
		label1.setTextFill(Color.GOLD);
		
		label2  = new Label("Topping");
		label2.setTextFill(Color.GOLD);
		label2.setUnderline(true);
		
	
		
		label3 = new Label("ASURITE ID: ");
		label3.setTextFill(Color.BLACK);
		
		
		//label4 = new Label("* INVALID ASURITE TRY AGAIN");
	//	label4.setTextFill(Color.RED);
	//	label4.setFont(Font.font("Arial" ));
		
		label5 = new Label("Ready To Order? "); 
		label5.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC, 25));
		
		
		
		label6 = new Label("Select pick up time ");
		label6.setTextFill(Color.GOLD);
		label2.setUnderline(true);
	// TextField 
		
		text = new TextField("Customer4");
		
		placementLabel = new Label(" ");
		placementLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC, 15));
		
		
		
		
	//Button 
		button = new Button("Submit Order");
		button.setStyle("-fx-background-color: MediumSeaGreen");

		button.setMinSize(100, 70);
		button.setOnAction(new ButtonHandler());
		
	//vbox for pizza type 
		VBox pane = new VBox();
		pane.setAlignment(Pos.TOP_LEFT);
		pane.setSpacing(20);
		 pane.setMaxSize(160,60);
		
		 pane.getChildren().addAll(label1,r1,r2,r3);
		 
		 
	// vbox for toppings 
		VBox pane1 = new VBox();
		pane1.setSpacing(10);
		 pane1.setMaxSize(160,60);
		pane1.getChildren().addAll(label2,r4,r5,r6,r7);
	//	pane1.setAlignment(Pos.BOTTOM_CENTER);
		
		
	// for pickUptime
		VBox pane2 = new VBox();
		pane1.setSpacing(10);
		pane.setMaxSize(160,60);
		pane2.getChildren().addAll(label6,pickupTime);
		
	// Hbox for the ASURITE ID 
		HBox pane3 = new HBox();
		pane3.setSpacing(10);
		 pane.setMaxSize(160,60);
		pane3.getChildren().addAll(label3, text);
		
	// Hbox for the button and the Order placement label
		HBox pane4 = new HBox();
		pane4.setSpacing(10);
		// pane.setMaxSize(160,60);
		 pane4.getChildren().addAll( button, placementLabel);
		 
		 
		 
		 
		// add panes to the gridpane 
		
		this.setAlignment(Pos.CENTER);
		this.setPrefHeight(200);
		this.setPrefWidth(60);
		this.setHgap(10); //set horizontal gap between columns
		this.setVgap(10);
		this.add(label5, 1,0 );
	
		this.add(pane,0 , 2);
		this.add(pane1, 1, 2);
		this.add(pane2, 4, 2);
		this.add(pane3, 0, 4);
		this.add(pane4, 4, 4);
		//this.add(placementLabel, 3, 4);
	
		
	}
	
	
	// shows event handler that adds the order to the database  when user clicks button
	
		
	private class ButtonHandler implements EventHandler<ActionEvent>
		{
		    //Override the abstact method handle()
		    public void handle(ActionEvent e)
		    
		    
		    {
		    
		    	asurite = text.getText();
		    
		    	String pizzaType = "";
		    	String[] toppings = new String[4];
		    	
		    	if(r4.isSelected()) {
		    		toppings[0] = "Mushrooms";
		    	}
		    	if(r5.isSelected()) {
		    		toppings[1] = "Onions";
		    	}
		    	if(r6.isSelected()) {
		    		toppings[2] = "Olives";
		    		
		    	}
		    	if(r7.isSelected()) {
		    		toppings[3] = "Sausages";
		    	}
		    	toppings = newToppings(toppings);
		    	
		    //======================================//
		    	if(r1.isSelected()) {
		    		pizzaType = "Cheese";
		    		
		    	}
		    	if(r2.isSelected()) {
		    		pizzaType = "Pepperoni";
		    		
		    	}
		    	if(r3.isSelected()) {
		    		pizzaType= "Vegetable";
		    	}	    	
		    	//"Default: 30 minutes", "45 minutes", "60 minutes");
		    	pickuptime = pickupTime.getSelectionModel().getSelectedItem();
		    	int time;
		    	switch(pickuptime.toUpperCase()) {
		    	case "DEfAULT: 30 MINUTES":
		    		time = 30;
		    		break;
		    	case "45 MINUTES":
		    		time = 45;
		    		break;
		    	case "60 MINUTES":
		    		time = 60; 
		    		break;
		    	default: 
		    		time = 30;
		    		break;
		    	}
		  // check if the ASUrite is valid 
		    	// check if their is a number at the end
		    	char[] chars = asurite.toCharArray();
		    	int i = asurite.length()-1;
		    	int x = 0;
		    	boolean hasNumberatend = false;    
		    	boolean hasNumber = false;
		    	while (x != 2) {
		    		if(Character.isDigit(chars[i])) {
		    			hasNumberatend = true;
		    		}
		    		x++;
		    		i--;
		    	}
		       // check that the ASURITE does not contain any numbers 
		
		   
		    		for(int p = 0; p < asurite.length() - 3; p++) {
		    			if(Character.isDigit(chars[p])) {
		    			hasNumber = true;
		    			}
		    			
		    		}
		    	
		       // check if the ASURITE has special characters
		    		boolean anyspecialcharacters = false;
		    		 for (int p = 0; p < asurite.length(); p++) {
		    			 
		    	            // Checking the character for not being a
		    	            // letter,digit or space
		    	            if (!Character.isDigit(asurite.charAt(p))
		    	                && !Character.isLetter(asurite.charAt(p))
		    	                && !Character.isWhitespace(asurite.charAt(p))) {
		    	                // Incrementing the countr for spl
		    	                // characters by unity
		    	                anyspecialcharacters = true;
		    	            }
		    	        }
		    	 
		    	
		    	// checks if pizza type is NULL tell customer to choose pizzaType
		    	if (pizzaType.equals("") ) {
		    		
		    		placementLabel.setText("Please select the Pizza type");
		    		placementLabel.setTextFill(Color.RED);
		    		
		    		}
		    	else {
		    	// if the cutomer has choosen the pizza type, we must make sure the ASUrite id is in proper form
		    		
		    		if(hasNumber == false && hasNumberatend == false || hasNumber == true && hasNumberatend == true
		    				|| hasNumber == true && hasNumberatend == false || anyspecialcharacters ==true ) {
		    			placementLabel.setText("* INVALID ASURITE");
		    			placementLabel.setTextFill(Color.RED);
		    	}
		    	else {
		    	pizza = new Pizza(toppings, "Large", pizzaType);
		    	price = findPrice(toppings,pizzaType);
		    	Order order = new Order(orderDatabase.generateOrderNumber(), price, asurite, pizza, time);
		    	placementLabel.setText("Your order number is: " + Integer.toString(order.orderNumber) + "\nFor: " 
		    			+ asurite + "\nThat will be $" + price) ;
		    	placementLabel.setTextFill(Color.MAROON);
		    	}}
		    
		    	
		   
	}
	
	
	 
	 
	
		}
	// This will return a new string Array that will hold the toppings without
	// any null elements.
	private String[] newToppings(String[] toppings) {
		int x = 0;
		
		for(int i = 0; i < toppings.length; i++ ) {
			if(toppings[i] != null) {
				x++;
			}}
		String[] newtoppings = new String[x];
		x = 0;
		for(int j = 0; j < toppings.length; j++ ) {
			if(toppings[j] != null) {
				newtoppings[x] = toppings[j];
				x++;
			}
		
			
		}
		
		return newtoppings;
	}
	/* Method will be used to create the price of the pizza
	 prices will vary from the toppings and the pizza type 
	 Type:                                    Toppings:
	 cheese = $6                              Mushrooms = $2
	 Pepporoni = $8                           Onions = $2
	 Vegetable = $7                           Olives = $2
	                                          Sausage = $4  
	 
	 
	 */
	private double findPrice(String[] toppings, String pizzaType) {
		// find the price of the toppings 
		double setprice = 0;
		double price = 0;
		double azsalesTax = .056;
		for(int i = 0; i < toppings.length; i++) {
			if(toppings[i] != null) {
				switch(toppings[i].toUpperCase()) {
				case "MUSHROOMS":
					setprice = setprice + 2;
					break;
				case "ONIONS":
					setprice = setprice + 2;
					break;
				case "OLIVES":
					setprice = setprice + 2;
					break;
				case "SAUSAGES":
					setprice = setprice + 4;
					break;
								}
			}
			
		}
		// find price of the pizzatype
		switch(pizzaType.toUpperCase()) {
		case "CHEESE":
			setprice = setprice + 6;
			break;
		case "PEPPERONI":
			setprice = setprice + 8;
			break;
		case "VEGETABLE":
			setprice = setprice + 7;
			break;
		default:
			setprice = setprice + 0;
			break;
		}
		NumberFormat nf= NumberFormat.getInstance();
	    nf.setMaximumFractionDigits(2);
	    setprice = setprice + setprice * azsalesTax;
	    price = Double.parseDouble(nf.format(setprice));
		return price;
	}
	
}



