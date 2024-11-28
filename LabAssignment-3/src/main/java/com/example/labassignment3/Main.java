package com.example.labassignment3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private List<User> userCredentials = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        loadUserData();

        Label titleLabel = new Label("Lab Assignment-3");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(new FileInputStream("pics.jpg"));
            imageView.setImage(image);
            imageView.setFitWidth(450);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            System.err.println("Image not found: " + e.getMessage());
        }

        Label usernameLabel = new Label("User Name:");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        Label notificationLabel = new Label();
        notificationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(30));
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        HBox buttonBox = new HBox(15, loginButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 2, 2, 1);
        gridPane.add(notificationLabel, 0, 3, 2, 1);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: rgba(85,182,164,0.9); " +
                "-fx-border-radius: 15px; -fx-background-radius: 15px; " +
                "-fx-padding: 20px; -fx-border-color: #4CAF50; -fx-border-width: 2px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0, 2);");

        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-background-radius: 10px; -fx-padding: 10px;");
        exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-background-radius: 10px; -fx-padding: 10px;");

        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white;"));
        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (validateCredentials(username, password)) {
                showUserTab(username);
            } else {
                notificationLabel.setText("Incorrect username or password!");
            }
        });

        exitButton.setOnAction(e -> primaryStage.close());

        VBox mainLayout = new VBox(20, titleLabel, imageView, gridPane);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #2C3E50, #4CA1AF);");

        Scene scene = new Scene(mainLayout, 600, 500);

        primaryStage.setTitle("Login Application");

        primaryStage.getIcons().add(new Image("file:logo.png"));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean validateCredentials(String username, String password) {
        for (User user : userCredentials) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void showUserTab(String username) {
        Stage userStage = new Stage();
        Label welcomeLabel = new Label("Welcome, " + username + "!");
        Scene scene = new Scene(welcomeLabel, 300, 100);
        userStage.setTitle("User Panel");
        userStage.setScene(scene);
        userStage.show();
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userCredentials.add(new User(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
