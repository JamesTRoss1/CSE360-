package application;

import javafx.fxml.FXML; 

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class ReturningCustomerSceneController {
	@FXML
	private TextField tfOrderNumber;
	private Database db;
	private ImageView forkimage;
	private ImageView pizzaimage;

	// Event Listener on Button.onAction
	// (If Place New Order button is pressed, customer will be taken to New Customer GUI.)
	@FXML
	public void btnNewOrder(ActionEvent event) {
		
		//Stage mainWindow = (Stage) tfOrderNumber.getScene().getWindow();
		//String title = tfOrderNumber.getText();
		//mainWindow.setTitle(title);
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void btnPizzaStatus(ActionEvent event) {
		
		String orderString = tfOrderNumber.getText(); // store order number input
		int orderNum = Integer.parseInt(orderString);
		
		// Checks if the orderNumber matches any from the db and prints out 
		// order status if match is found. Otherwise, print out some error statement.
		if(db.foundOrderNumber(orderNum) == true) {
			Order order = db.getOrder(orderNum); // Get specific order from db
			System.out.print(order.status);
		}
		else {
			System.out.print("Invalid Order Number.");
		}
	}
//	public void showImages() {
//	    try {
//	        Image image = new Image("pepperoni_pizza.jpeg");
//	        forkimage.setImage(image);
//	        forkimage.setCache(true);
//	        
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    
//	    try {
//	    	Image image2 = new Image("forksundevil.png");
//	        pizzaimage.setImage(image2);
//	        pizzaimage.setCache(true);
//	    }
//	    catch(Exception e) {
//	    	e.printStackTrace();
//	    }
//	}
	
}
