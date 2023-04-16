import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultsFX {
    public static void display(Stage primaryStage) {
        // Create a title
        Text title = new Text("Thanks for Playing!");
        Font PokerKingsT = Font.loadFont(Welcome.class.getResourceAsStream("PokerKings-Regular.ttf"), 50);
        title.setFont(PokerKingsT);

        Results result = new Results();
        Text subtitle = new Text(result.getPlayerResult());
        Font PokerKingsS = Font.loadFont(Welcome.class.getResourceAsStream("PokerKings-Regular.ttf"), 20);
        subtitle.setFont(PokerKingsS);

        // Create three cards fanned out
        //InputStream cardStream = new FileInputStream("src/main/java/card.png");
        //Image cardImage = new ImageView(new Image(getClass().getResourceAsStream("card.png")));
        //Image cardImage = new Image(cardStream);
        Image ASImage = new Image("Q_S.png", 200, 300, true, true);
        Image KSImage = new Image("K_S.png", 200, 300, true, true);
        Image QSImage = new Image("A_S.png", 200, 300, true, true);
        ImageView card1 = new ImageView(ASImage);
        ImageView card2 = new ImageView(KSImage);
        ImageView card3 = new ImageView(QSImage);
        HBox cards = new HBox(card1, card2, card3);
        cards.setSpacing(-150);
        cards.setAlignment(Pos.CENTER);
        card1.setRotate(-10);
        card1.setTranslateY(25);
        card2.setRotate(0);
        card2.setTranslateY(0);
        card3.setRotate(10);
        card3.setTranslateY(25);

        // Create a text field for IP address entry
        Label resultLabel = new Label("Result:");
        Text earnings = new Text(Integer.toString(result.getTotalWins()));
        resultLabel.setFont(Font.font("Cambria"));
        earnings.setFont(Font.font("Cambria"));
        HBox earningsBox = new HBox(resultLabel, earnings);
        earningsBox.setAlignment(Pos.CENTER);
        earningsBox.setSpacing(20);

        // Create a text field for port entry
        Label replay = new Label(":Play Again?");
        replay.setFont(Font.font("Cambria"));
        Label exit = new Label("Exit?");
        exit.setFont(Font.font("Cambria"));
        HBox choiceBox = new HBox(replay, exit);
        //portBox.setTranslateX(17);
        choiceBox.setAlignment(Pos.CENTER);
        //portBox.setSpacing(20);

        //Label statusLabel = new Label();
        //statusLabel.setFont(Font.font("Cambria"));

        // Create a connect button
        Button replayButton = new Button("Join Game");
        Button exitButton = new Button("Exit Game");
        HBox buttonBox = new HBox(replayButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        //connectButton.requestFocus();
        /*connectButton.setTranslateX(2);
        connectButton.setOnAction(event -> {
            try {
                ip = Integer.parseInt(ipTextField.getText()); //doesn't work with '.', need event handler
                port = Integer.parseInt(portTextField.getText());
                statusLabel.setText("Success!");
                //connectToServer();
                //joinGame();
                // start button action
                SceneControl sc = new SceneControl();
                sc.game(primaryStage);

            } catch (NumberFormatException e){
                // Handle the case where the input is not a valid integer
                // Display an error message
                statusLabel.setText("Invalid input. Please enter integers only.");
                // Clear the text fields
                ipTextField.setText("");
                portTextField.setText("");
            }
        });*/
		/*connectButton.setOnAction(event -> {
			connectToServer();
			joinGame();
		});*/

        // Create a VBox to hold all the elements
        VBox vbox = new VBox(subtitle, earningsBox, choiceBox, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));

        //HBox hbox = new HBox(cards, vbox);
        //hbox.setPadding(new Insets(30));
        //hbox.setSpacing(10);

        // Create a StackPane to hold the VBox and center it in the scene
        // StackPane root = new StackPane(fscene);
        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(30, 0, 0, 0));
        root.setCenter(cards);
        root.setRight(vbox);

        // Create a scene with a CSS style sheet
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("style.css");

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.setWidth(850);
        primaryStage.setHeight(650);
        primaryStage.setTitle("Three Card Poker");

        root.setOnMouseClicked(event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node instanceof TextField) {
                // Mouse click was inside a text field, do nothing
            } else {
                // Mouse click was outside a text field, remove focus from all text fields
                root.requestFocus();
            }
        });

        primaryStage.show();
    }
}
