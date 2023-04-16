import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class GameFX {
    public static void display(Stage primaryStage) {
        //BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(GameFX.class.getResource("poker_scene.fxml"));
        FXML_Control controller = loader.getController();
        try {
            //gameScene(loader);
            BorderPane root = loader.load();
            root.setMinWidth(800);
            root.setMinHeight(600);
            Scene scene = new Scene(root);
            root.setOnMouseClicked(event -> {
                Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof TextField) {
                    // Mouse click was inside a text field, do nothing
                } else {
                    // Mouse click was outside a text field, remove focus from all text fields
                    root.requestFocus();
                }
            });
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Scene scene = new Scene(root, 800, 600);
        //scene.getStylesheets().add("style.css");
        //primaryStage.setScene(scene);
        /*primaryStage.setWidth(800);
        primaryStage.setHeight(600);*/
        /*root.setOnMouseClicked(event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node instanceof TextField) {
                // Mouse click was inside a text field, do nothing
            } else {
                // Mouse click was outside a text field, remove focus from all text fields
                root.requestFocus();
            }
        });*/

        primaryStage.setTitle("Three Card Poker");
        primaryStage.show();
    }

    /*public void gameScene(FXMLLoader loader) {
        BorderPane root = loader.load();
        root.setMinWidth(800);
        root.setMinHeight(600);
        Scene scene = new Scene(root);
        root.setOnMouseClicked(event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node instanceof TextField) {
                // Mouse click was inside a text field, do nothing
            } else {
                // Mouse click was outside a text field, remove focus from all text fields
                root.requestFocus();
            }
        });
        primaryStage.setScene(scene);
    }*/
}
