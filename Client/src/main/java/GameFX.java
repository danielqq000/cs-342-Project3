import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            BorderPane root = loader.load();
            root.setMinWidth(800);
            root.setMinHeight(600);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Scene scene = new Scene(root, 800, 600);
        //scene.getStylesheets().add("style.css");
        //primaryStage.setScene(scene);
        /*primaryStage.setWidth(800);
        primaryStage.setHeight(600);*/

        primaryStage.setTitle("Three Card Poker");
        primaryStage.show();
    }
}
