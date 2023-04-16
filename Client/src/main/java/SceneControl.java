import javafx.application.Application;
import javafx.stage.Stage;

public class SceneControl extends Application {
    @Override
    public void start(Stage primaryStage) {
        WelcomeFX.display(primaryStage);
    }

    public void game(Stage primaryStage) {
        GameFX.display(primaryStage);
    }

    public void result(Stage primaryStage) {
        ResultsFX.display(primaryStage);
    }

    public static void main(String[] args) {
        System.out.println("***Program Starts***");
        launch(args);
    }
}
