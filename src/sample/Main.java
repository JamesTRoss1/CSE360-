package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public GridPane root;
    public Scene scene;
    public VBox vbox;
    private boolean valid = false;
    public Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        toInitialGUI();
    }

    public void toInitialGUI() throws Exception {
        String basePath = new File("").getAbsolutePath();
        Image image = new Image(new FileInputStream(basePath + "/back_arrow.png"));
        //Setting the image view
        ImageView imageView = new ImageView(image);

        Image image2 = new Image(new FileInputStream(basePath + "/pizza.png"));
        //Setting the image view
        ImageView imageView2 = new ImageView(image2);

        Label title = new Label("Welcome To Sun Devil Pizza");
        Button customerButton =new Button ("I am a Customer");
        Button chefButton = new Button("I am a Chef");
        Button orderProcButton = new Button("I am a Order Processor");
        root = new GridPane();
        vbox = new VBox();
        scene = new Scene(root);
        ColumnConstraints cons1 = new ColumnConstraints();
        cons1.setHgrow(Priority.NEVER);
        root.getColumnConstraints().add(cons1);
        ColumnConstraints cons2 = new ColumnConstraints();
        cons2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(cons1, cons2);
        RowConstraints rcons1 = new RowConstraints();
        rcons1.setVgrow(Priority.NEVER);
        RowConstraints rcons2 = new RowConstraints();
        rcons2.setVgrow(Priority.ALWAYS);
        root.getRowConstraints().addAll(rcons1, rcons2);
        vbox.getChildren().addAll(title, new Text(" "), customerButton, new Text(" "), new Text(" "), orderProcButton, new Text(" "), chefButton);
        root.add(vbox, 3, 3, 5, 5);
        root.add(imageView, 0, 0, 3, 3);
        root.add(imageView2, 9, 0, 3, 3);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked((MouseEvent e) -> {
            try {
                toInitialGUI();
            } catch (Exception exception) {

            }
        });
        title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        customerButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        customerButton.setAlignment(Pos.CENTER);
        customerButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        orderProcButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        orderProcButton.setAlignment(Pos.CENTER);
        orderProcButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        chefButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        chefButton.setAlignment(Pos.CENTER);
        chefButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        customerButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                toCustomerGUI();
            }
        });
        chefButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                toValidationGUI("chef");
            }
        });
        orderProcButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                toValidationGUI("op");
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toValidationGUI(String role) {
        final Label[] validation = {new Label("Employee Authentication")};
        Button validateButton = new Button("Sign In");
        validateButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        validateButton.setAlignment(Pos.CENTER);
        validateButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        TextField userField = new TextField();
        userField.setPromptText("User Name");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        vbox.getChildren().remove(0, vbox.getChildren().size());
        vbox.getChildren().add(validation[0]);
        vbox.getChildren().add(new Text(" "));
        vbox.getChildren().add(userField);
        vbox.getChildren().add(passwordField);
        vbox.getChildren().add(new Text(" "));
        vbox.getChildren().add(validateButton);
        vbox.getChildren().add(new Text(" "));
        root.getChildren().remove(vbox);
        Text authText = new Text();
        vbox.getChildren().add(authText);
        root.add(vbox, 3, 3, 5, 5);
        GridPane.setValignment(vbox, VPos.CENTER);

        validateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                valid = false;
                String user = userField.getText();
                String pass = passwordField.getText();
                userField.clear();
                passwordField.clear();
                //Logic to check if auth is valid
                FileReader userFile = null;
                String basePath = new File("").getAbsolutePath();
                try {
                    userFile = new FileReader(basePath + "/users.txt");
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                FileReader passFile = null;
                try {
                    passFile = new FileReader(basePath + "/passwords.txt");
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                BufferedReader ubr = new BufferedReader(userFile);
                BufferedReader pbr = new BufferedReader(passFile);
                try {
                    while(ubr.ready() && pbr.ready()) {
                        String username = ubr.readLine();
                        String password = pbr.readLine();
                        if(username.compareTo(user) == 0 && password.compareTo(pass) == 0) {
                            valid = true;
                        }
                    }
                } catch (IOException ioException) {

                }
                if(valid == true) {
                    if(role == "chef") {
                        toChefGUI();
                    }
                    if(role == "op") {
                        toOrderProcessorGUI();
                    }
                }
                else {
                    authText.setText("Auth Denied");
                }
            }});
    }

    public void toCustomerGUI() {

    }

    public void toChefGUI() {
        System.out.println("To Chef");
    }

    public void toOrderProcessorGUI() {
        System.out.println("To Order Processor");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
