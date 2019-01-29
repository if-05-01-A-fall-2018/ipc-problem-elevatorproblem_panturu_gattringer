package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label passengerLabel;

    @FXML
    private Label currentLevelLabel;

    @FXML
    private TextField passengerTextfield;

    @FXML
    private TextField currentLevelTextfield;

    @FXML
    private Button startButton;

    @FXML
    private Button enterLift;

    @FXML
    private Line verticalLine;

    @FXML
    private Rectangle elevatorRectangle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.passengerTextfield.setEditable(false);
        this.currentLevelTextfield.setEditable(false);
    }
}
