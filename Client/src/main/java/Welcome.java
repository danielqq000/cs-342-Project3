import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;


public class Welcome {
	private static long ip = 0;
	private static int port = 0;

	public void setIP(int ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getIP() {
		return this.ip;
	}

	;

	public int getPort() {
		return this.port;
	}

	;
	//@Override
}
