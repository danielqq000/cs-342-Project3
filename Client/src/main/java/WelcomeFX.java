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

import java.io.Serializable;
import java.util.function.Consumer;

public class WelcomeFX {
    public static void display(Stage primaryStage) {
        Welcome welcome = new Welcome();
        // Create a title
        Text title = new Text("Welcome to Three Card Poker");
        Font PokerKingsT = Font.loadFont(Welcome.class.getResourceAsStream("PokerKings-Regular.ttf"), 50);
        title.setFont(PokerKingsT);

        Text subtitle = new Text("Please enter IP address and port number");
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
        Text ipLabel = new Text("IP Address:");
        TextField ipTextField = new TextField();
        ipTextField.setPromptText("Enter IP address");
        ipLabel.setFont(Font.font("Cambria"));
        ipTextField.setFont(Font.font("Cambria"));
        HBox ipBox = new HBox(ipLabel, ipTextField);
        ipBox.setAlignment(Pos.CENTER);
        ipBox.setSpacing(20);
        ipTextField.setFocusTraversable(false);

        // Create a text field for port entry
        Text portLabel = new Text("Port:");
        TextField portTextField = new TextField();
        portTextField.setPromptText("Enter port number");
        portLabel.setFont(Font.font("Cambria"));
        portTextField.setFont(Font.font("Cambria"));
        HBox portBox = new HBox(portLabel, portTextField);
        portBox.setTranslateX(17);
        portBox.setAlignment(Pos.CENTER);
        portBox.setSpacing(20);
        portTextField.setFocusTraversable(false);

        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Cambria"));

        // Create a connect button
        Button connectButton = new Button("Join Game");
        //connectButton.requestFocus();
        connectButton.setTranslateX(2);
        connectButton.setOnAction(event -> {
            try {
                welcome.setIP(ipTextField.getText()); //doesn't work with '.', need event handler
                welcome.setPort(Integer.parseInt(portTextField.getText()));
                Client client = new Client(callback, welcome.getIP(), welcome.getPort());
                statusLabel.setText("Connected!");
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
            } //catch ()
        });
		/*connectButton.setOnAction(event -> {
			connectToServer();
			joinGame();
		});*/

        // Create a VBox to hold all the elements
        VBox vbox = new VBox(subtitle, ipBox, portBox, connectButton, statusLabel);
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
        primaryStage.setWidth(825);
        primaryStage.setHeight(625);
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

		/*primaryStage.setOnShown(event -> {
			//scene.getRoot().setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
			primaryStage.sizeToScene();
		});*/
		/*primaryStage.setOnShown(event -> {
			double width = primaryStage.getWidth();
			double height = primaryStage.getHeight();
			scene.setRoot(root);
			scene.setWidth(width);
			scene.setHeight(height);
		});*/
        //scene.widthProperty().bind(primaryStage.widthProperty());
        //scene.heightProperty().bind(primaryStage.heightProperty());
		/*root.setOnMouseClicked(event -> {
			// Remove focus from the text field
			ipTextField.setFocusTraversable(false);
			portTextField.setFocusTraversable(false);
		});*/
        // Add event filter to root node
		/*root.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TextField) {
				// Let the TextField handle the event
				return;
			} else {
				// Set focus to a different node in the scene
				//connectButton.requestFocus();
				// Remove cursor from all text fields
				//ipTextField.positionCaret(0);
				//portTextField.positionCaret(0);
				if (ipTextField.isFocused())
					ipTextField.getParent().requestFocus();
				if (portTextField.isFocused())
					portTextField.getParent().requestFocus();

			}
		});*/
    }
    private static Consumer<Serializable> callback = data -> {
        // Handle received data as needed
        System.out.println("Received data: " + data);
    };

}

//feel free to remove the starter code from this method
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		 // Auto-generated method stub
		 primaryStage.setTitle("Welcome to JavaFX");

		 Rectangle rect = new Rectangle (100, 40, 100, 100);
	     rect.setArcHeight(50);
	     rect.setArcWidth(50);
	     rect.setFill(Color.VIOLET);

	     RotateTransition rt = new RotateTransition(Duration.millis(5000), rect);
	     rt.setByAngle(270);
	     rt.setCycleCount(4);
	     rt.setAutoReverse(true);
	     SequentialTransition seqTransition = new SequentialTransition (
	         new PauseTransition(Duration.millis(500)),
	         rt
	     );
	     seqTransition.play();

	     FadeTransition ft = new FadeTransition(Duration.millis(5000), rect);
	     ft.setFromValue(1.0);
	     ft.setToValue(0.3);
	     ft.setCycleCount(4);
	     ft.setAutoReverse(true);

	     ft.play();
	     BorderPane root = new BorderPane();
	     root.setCenter(rect);

	     Scene scene = new Scene(root, 700,700);
			primaryStage.setScene(scene);
			primaryStage.show();
	}*/

//}

