package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MinesFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox; // Container to hold the game interface
        MyController controller; // Controller for the game logic
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("check.fxml"));
            vbox = loader.load(); 
            controller = loader.getController(); // Get the associated controller
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        GridPane gridPane = new GridPane(); // Grid to hold the cells of the game
        gridPane.setPadding(new Insets(10)); // Set padding
        gridPane.setHgap(5); // Set gap between cells
        gridPane.setVgap(5); 
        gridPane.setStyle("-fx-background-color: black;"); // Set background color of the grid
        controller.setGrid(gridPane); 
        controller.reset(null); // Initialize the game

        vbox.getChildren().add(gridPane);

        Scene scene = new Scene(vbox); // Create a scene with the VBox as the root
        primaryStage.setScene(scene); 
        primaryStage.setTitle("Mines");
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
