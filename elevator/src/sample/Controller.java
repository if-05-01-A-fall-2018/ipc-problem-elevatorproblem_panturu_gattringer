package sample;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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
    private Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.passengerTextfield.setEditable(true);
        this.currentLevelTextfield.setEditable(true);
    }

    public void moveElevator(){

    }

    public void enterLiftButtonPressed(ActionEvent actionEvent) {
        Passenger p = new Passenger();
        Lift.getInstance().addObserver(p);
    }

    public void startAnimationButtonPressed(ActionEvent actionEvent) {

    }
}