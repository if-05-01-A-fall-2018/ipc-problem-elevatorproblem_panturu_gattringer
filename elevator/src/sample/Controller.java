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
import java.util.*;

public class Controller extends Observable implements Initializable{

    public GridPane gripdane;
    public TextField peopleWaitingToEnterTextField;
    public TextField careTakerTicksTillMaintanceTextField;
    public TextField peopleEnterPerTickTextField;
    public TextField ticksUntilMaintanceEnterTextFiled;
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
    private boolean mainteanceStart = false;

    @FXML
    private Rectangle elevatorRectangle;
    private List<Passenger> waitingToEnter;


    private int roundsTillLeave = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.passengerTextfield.setEditable(true);
        this.currentLevelTextfield.setEditable(true);
        this.ticksUntilMaintanceEnterTextFiled.setText("50");
        this.careTakerTicksTillMaintanceTextField.setText("50");
        this.passengerTextfield.setDisable(true);
        this.currentLevelTextfield.setDisable(true);
        this.peopleWaitingToEnterTextField.setDisable(true);

    }

    public void startAnimationButtonPressed(ActionEvent actionEvent) {
        mainteanceStart = false;
        if(timeline == null || timeline.getStatus() == Timeline.Status.STOPPED){
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(2000),
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

    public void enterLiftButtonPressed() {
        for(int i = 0; i< Integer.parseInt(peopleEnterPerTickTextField.getText()); i++){
        Passenger p = new Passenger();
        if(waitingToEnter == null)waitingToEnter = new ArrayList<>();
        waitingToEnter.add(p);
        }
        this.peopleWaitingToEnterTextField.setText(waitingToEnter.size()+"");
    }

    private void onTimerTick() {
        moveElevator();
        enterLiftButtonPressed();
        elevatorRectangle.setY( elevatorRectangle.getY() + direction);
        if(!mainteanceStart){
            this.careTakerTicksTillMaintanceTextField.setText(Integer.parseInt(careTakerTicksTillMaintanceTextField.getText()) -1 +"");
            if(careTakerTicksTillMaintanceTextField.getText() == "0") {
                mainteanceStart = true;
                Random rand = new Random();
                this.roundsTillLeave = rand.nextInt(10) + 1;
            }
        }
        if(waitingToEnter!= null && !waitingToEnter.isEmpty() && !mainteanceStart){
            waitingToEnter.stream()
                    .forEach(x -> this.addObserver(x));
            waitingToEnter.clear();
            this.setChanged();
            this.notifyObservers();
        }
        else if(mainteanceStart && this.countObservers() == 0){
                roundsTillLeave--;
                if(roundsTillLeave == 0)mainteanceStart = false;
        }
        this.passengerTextfield.setText(""+ this.countObservers());
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