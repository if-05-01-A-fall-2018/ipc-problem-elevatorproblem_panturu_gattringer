package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TargetInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller extends Observable implements Initializable{

    public GridPane gripdane;
    private int direction = -20;
    private boolean isLiftGoingUp = true;
    private Timeline timeline = null;
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
    private List<Passenger> waitingToEnter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.passengerTextfield.setEditable(true);
        this.currentLevelTextfield.setEditable(true);
    }

    public void startAnimationButtonPressed(ActionEvent actionEvent) {
        if(timeline == null || timeline.getStatus() == Timeline.Status.STOPPED){
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(1500),
                    ae -> onTimerTick()));
            timeline.setCycleCount(Animation.INDEFINITE);
            elevatorRectangle.setY(elevatorRectangle.getY());
            timeline.playFromStart();
            this.startButton.setText("Stop");
        }
        else if(timeline.getStatus() == Timeline.Status.RUNNING){
            this.startButton.setText("Start");
            elevatorRectangle.setY(elevatorRectangle.getY());
            timeline.stop();
        }
    }


    public void enterLiftButtonPressed(ActionEvent actionEvent) {
        Passenger p = new Passenger();
        if(waitingToEnter == null)waitingToEnter = new ArrayList<>();
        waitingToEnter.add(p);
    }

    private void onTimerTick() {
        moveElevator();
        if(waitingToEnter!= null && !waitingToEnter.isEmpty()){
            waitingToEnter.stream()
                    .forEach(x -> this.addObserver(x));
            waitingToEnter.clear();
        }
        this.passengerTextfield.setText(""+ this.countObservers());
        this.setChanged();
        this.notifyObservers();
    }

    private void moveElevator() {
        //when the lift is up, it should now move down
        if(elevatorRectangle.getY() == -260){
            isLiftGoingUp = false;
            elevatorRectangle.setY( elevatorRectangle.getY() - direction);
            System.out.println(elevatorRectangle.getY());
        }
        //keep on going down until Y = -20
        else if (elevatorRectangle.getY() > -280 && elevatorRectangle.getY() <= -20 && isLiftGoingUp == false){
            elevatorRectangle.setY( elevatorRectangle.getY() - direction);
            System.out.println(elevatorRectangle.getY());
        }
        //when done going down, the lift goes back up
        else if(elevatorRectangle.getY() == 0 && isLiftGoingUp == false){
            isLiftGoingUp = true;
            elevatorRectangle.setY( elevatorRectangle.getY() + direction);
            System.out.println(elevatorRectangle.getY());
        }
        // start going up
        else if (elevatorRectangle.getY() == 0 && isLiftGoingUp == true){
            elevatorRectangle.setY( elevatorRectangle.getY() + direction);
            System.out.println(elevatorRectangle.getY());
        }
        //go up
        else {
            elevatorRectangle.setY(elevatorRectangle.getY() + direction);
            System.out.println(elevatorRectangle.getY());
        }
    }
}