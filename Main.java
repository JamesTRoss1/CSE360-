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
    //class attributes
    public static Stage primaryStage;
    public GridPane root;
    public static Scene scene;
    public VBox vbox;
    private boolean valid = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        toInitialGUI();
    }

    public void toInitialGUI() throws Exception {
        String basePath = new File("").getAbsolutePath();
        //get the absolute path in order to work on any machine
        Image image = new Image(new FileInputStream(basePath + "/back_arrow.png"));
        //Setting the image view
        ImageView imageView = new ImageView(image);

        Image image2 = new Image(new FileInputStream(basePath + "/pizza.png"));
        //Setting the image view
        ImageView imageView2 = new ImageView(image2);
        Image image3 = new Image(new FileInputStream(basePath + "/logo.png"));
        //Setting the image view
        ImageView imageView3 = new ImageView(image3);
        Image img = new Image(new FileInputStream(basePath + "/asu_white.jpg"));
        //gui elements
        Label title = new Label("Welcome To Sun Devil Pizza");
        Button customerButton =new Button ("I am a Customer");
        Button chefButton = new Button("I am a Chef");
        Button orderProcButton = new Button("I am a Order Processor");
        Button debugButton = new Button("View Log");
        root = new GridPane();
        vbox = new VBox();
        scene = new Scene(root);
        //columns and rows for formatting
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
        //add elements to the pane and the vbox
        root.getRowConstraints().addAll(rcons1, rcons2);
        vbox.getChildren().addAll(title, new Text(" "), customerButton, new Text(" "),
                new Text(" "), orderProcButton, new Text(" "), chefButton, new Text(" "),
                new Text(" "), debugButton
        );
        root.add(vbox, 3, 0, 5, 5);
        root.add(imageView, 0, 0, 1, 1);
        root.add(imageView3, 0, 0, 3, 3);
        root.add(imageView2, 9, 0, 3, 3);
        //resize images and have callback
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        imageView2.setFitWidth(220);
        imageView2.setFitHeight(220);
        imageView3.setFitWidth(220);
        imageView3.setFitHeight(220);
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked((MouseEvent e) -> {
            try {
                toInitialGUI();
            } catch (Exception exception) {

            }
        });
        //formatting
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
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(50, 50, false, false, true, false));
        Background bGround = new Background(bImg);
        root.setBackground(bGround);
        //event handlers for buttons
        customerButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    toCustomerGUI();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
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
        debugButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Database db = new Database();
                db.printOrders();
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
        root.add(vbox, 3, 1, 5, 5);
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

    public void toCustomerGUI() throws Exception {
        ReturningCustomer ret = new ReturningCustomer();
        ret.start(primaryStage);
    }

    public void toChefGUI() {
        Database db = new Database();
        ChefGUI chef = new ChefGUI(db);
        scene = new Scene(chef, 1280, 720);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toOrderProcessorGUI() {
        Database db = new Database();
        OPA_FX_GUI op = new OPA_FX_GUI(db);
        scene = new Scene(op, 1280, 720);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
