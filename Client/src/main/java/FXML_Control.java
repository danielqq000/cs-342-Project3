import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXML_Control { // implements Initializable {

    @FXML
    private Button drawButton;
    private Button playButton;
    private Button foldButton;
    private TextField anteField;
    private TextField pairsPlusField;
    //onAction="#handleSubmitButtonAction" need actions in fxml

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize code here
    }*/

    @FXML
    private void handleButtonClick(ActionEvent event) {
        // handle button click here
    }

}
