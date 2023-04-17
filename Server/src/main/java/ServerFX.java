import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class ServerFX extends Application {

    private Server conn;
    private Integer port;

    private TextArea messages = new TextArea();
    private Label numClientLabel;    // show number of clients connecting to this server
    private Label serverStatus; // show waiting information
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setScene(new Scene(createContent()));
        this.stage.show();
    }

    @Override
    public void stop() throws Exception{
        conn.closeConn();
    }

	// create server
    private Server createServer() {
        return new Server(this.port,
                data-> { Platform.runLater(()->{
                    String s = data.toString();
                    if(s.contains("PLAYERNUM#")){
                        this.numClientLabel.setText(s.split("#")[1]);
                    }
                    if(s.contains("REFRESHLIST#")){
                        s = s.replace("REFRESHLIST#","");
                        this.messages.setText(s);
                    }
                });}
        );
    }

    private Parent createContent() {

        Label portLabel = new Label("Input Port:");
        TextField input = new TextField();
        Button startButton = new Button("Start Server");
        Button closeButton = new Button("Close Server");
        closeButton.setDisable(true);
        this.serverStatus = new Label("Server off");
		messages.setEditable(false);
		
		// HBox for port info
        HBox HBox1 = new HBox(20, portLabel, input);
		// HBox for server toggle buttons
        HBox HBox2 = new HBox(20, startButton, closeButton);

		// numbers of current clients
        Label labelNumClient = new Label("Client connected:");
        this.numClientLabel = new Label("0");

		// HBox for Client info and server status
        HBox HBox3 = new HBox(20, labelNumClient, numClientLabel, serverStatus);

		// start port depends on input port
        startButton.setOnAction(event -> {
            Integer portNum = 0;
            try {
                portNum = Integer.valueOf(input.getText());
            }
            catch (Exception e){
                serverStatus.setText("invalid port");
                return;
            }
			// need at least 4 digits
            if(portNum<1000){
                serverStatus.setText("invalid port");
                return;
            }

            this.port = portNum;
            startButton.setDisable(true);
            closeButton.setDisable(false);
            serverStatus.setText("Server on");

            this.conn = createServer();
            try{this.conn.startConn();}
            catch(Exception e){
				throw new RuntimeException("Server failed to turn on!");
            }
        });

		// close button, close all connection
        closeButton.setOnAction(event -> {
            startButton.setDisable(false);
            closeButton.setDisable(true);
            serverStatus.setText("Server off");
            messages.clear();
            this.numClientLabel.setText("0");

            try{this.conn.closeConn();}
            catch(Exception e){
				throw new RuntimeException("Server failed to turn off!");
            }
        });

        messages.setPrefHeight(300);

        Label clientListLabel = new Label("Connected Client List:");
        VBox root = new VBox(15, HBox1, HBox2, HBox3, clientListLabel, messages);
        root.setPrefSize(300, 500);
        root.setPadding(new Insets(20));

        return root;
    }
}
