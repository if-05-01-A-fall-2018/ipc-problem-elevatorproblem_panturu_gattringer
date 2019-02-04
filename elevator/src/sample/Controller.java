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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Controller extends Observable implements Initializable{
    private int direction = -20;
    private boolean isLiftGoingUp = true;
    private Timeline timeline = null;
    private String lastCare;
    private int countLevel = 0;
    private boolean hasMaintenanceStarted = false;

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
    public TextField peopleWaitingToEnterTextField;

    @FXML
    public TextField careTakerTicksTillMaintenanceTextField;

    @FXML
    public TextField peopleEnterPerTickTextField;

    @FXML
    public TextField ticksUntilMaintenanceEnterTextField;

    @FXML
    public Label inMaintenanceLabel;

    @FXML
    private Line verticalLine;

    @FXML
    private Rectangle elevatorRectangle;
    private List<Passenger> waitingToEnter;
    private int roundsTillLeave = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.currentLevelTextfield.setDisable(true);
        this.peopleWaitingToEnterTextField.setDisable(true);
        this.passengerTextfield.setDisable(true);
        this.careTakerTicksTillMaintenanceTextField.setDisable(true);
        this.ticksUntilMaintenanceEnterTextField.setText("5");
        this.peopleEnterPerTickTextField.setText("2");

    }

    public void startAnimationButtonPressed(ActionEvent actionEvent) {
        if(timeline == null || timeline.getStatus() == Timeline.Status.STOPPED){
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(1000),
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
        if (waitingToEnter == null) waitingToEnter = new ArrayList<>();
        try {
            for (int i = 0; i < Integer.parseInt(peopleEnterPerTickTextField.getText()); i++) {
                Passenger p = new Passenger();
                waitingToEnter.add(p);
            }
        }
        catch(Exception e){

        }
        this.peopleWaitingToEnterTextField.setText(waitingToEnter.size()+"");
    }

    private void onTimerTick() {
        if(!this.ticksUntilMaintenanceEnterTextField.getText().equals(this.lastCare)) {
            lastCare = this.ticksUntilMaintenanceEnterTextField.getText();
            this.careTakerTicksTillMaintenanceTextField.setText(lastCare);
        }
        enterLiftButtonPressed();
        System.out.println(hasMaintenanceStarted);
        if(!hasMaintenanceStarted){
            this.careTakerTicksTillMaintenanceTextField.setText(Integer.parseInt(careTakerTicksTillMaintenanceTextField.getText()) -1 +"");
            startMaintenance();
            moveElevator();
        }
        if(waitingToEnter!= null && !waitingToEnter.isEmpty() && !hasMaintenanceStarted){
            waitingToEnter.stream()
                    .forEach(x -> this.addObserver(x));
            waitingToEnter.clear();
        }

        else if(hasMaintenanceStarted && this.countObservers() == 0){
            roundsTillLeave--;
            elevatorRectangle.setFill(Paint.valueOf("#A85858"));
            this.inMaintenanceLabel.setText("IN MAINTENANCE!!!");
            stopMaintenance();
        }
        else if(hasMaintenanceStarted && this.countObservers() != 0){
            this.inMaintenanceLabel.setText("People must go out!!!");
            moveElevator();
        }
        this.setChanged();
        this.notifyObservers();
        this.passengerTextfield.setText(""+ this.countObservers());
    }

    private void moveElevator(){
        if(elevatorRectangle.getY() == -260){                                                                           //when the lift is up, it should now move down
            isLiftGoingUp = false;
            elevatorRectangle.setY(elevatorRectangle.getY() - direction);
            countLevel--;
        }
        else if (elevatorRectangle.getY() > -280 && elevatorRectangle.getY() <= -20 && isLiftGoingUp == false){         //keep on going down until Y = -20
            elevatorRectangle.setY(elevatorRectangle.getY() - direction);
            countLevel--;
        }
        else if(elevatorRectangle.getY() == 0 && isLiftGoingUp == false){                                               //when done going down, the lift goes back up
            isLiftGoingUp = true;
            elevatorRectangle.setY(elevatorRectangle.getY() + direction);
            countLevel++;
        }
        else{                                                                                                           // start going up
            elevatorRectangle.setY(elevatorRectangle.getY() + direction);
            countLevel++;
        }
        printLevelAndY();
    }

    private void printLevelAndY(){
        currentLevelTextfield.setText(Integer.toString(countLevel));
        System.out.println("Level: " + countLevel);
        System.out.println(elevatorRectangle.getY());
    }

    private void startMaintenance() {
        if(careTakerTicksTillMaintenanceTextField.getText().equals("0")) {
            hasMaintenanceStarted = true;
            Random rand = new Random();
            this.roundsTillLeave = rand.nextInt(10) + 1;
            System.out.println("Maintenance time:" + this.roundsTillLeave);
            //this.inMaintenanceLabel.setText("IN MAINTENANCE!!!");
        }
    }

    private void stopMaintenance(){
        if(roundsTillLeave == 0) {
            hasMaintenanceStarted = false;
            this.inMaintenanceLabel.setText("");
            this.careTakerTicksTillMaintenanceTextField.setText( this.ticksUntilMaintenanceEnterTextField.getText());
            elevatorRectangle.setFill(Paint.valueOf("#81E6BB"));
        }
    }
}