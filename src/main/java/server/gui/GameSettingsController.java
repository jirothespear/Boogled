package server.gui;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.io.IOException;

public class GameSettingsController {

    @FXML
    private Spinner<Integer> roundTimeValue;

    @FXML
    private Spinner<Integer> waitingTimeValue;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    public void initialize() {
        roundTimeValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 5));
        waitingTimeValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE, 3));

        roundTimeValue.valueProperty().addListener(this::onRoundTimeChanged);
        waitingTimeValue.valueProperty().addListener(this::onWaitingTimeChanged);
    }

    @FXML
    public void onRoundTimeChanged(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        System.out.println("Round Time changed from " + oldValue + " to " + newValue);
        // Add your logic here for handling the new value
    }

    @FXML
    public void onWaitingTimeChanged(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        System.out.println("Waiting Time changed from " + oldValue + " to " + newValue);
        // Add your logic here for handling the new value
    }

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("server-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
