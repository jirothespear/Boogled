package client.comproggui;

import Utility.ClientCallback;
import Utility.LoginException;
import Utility.ServerUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import testers.ClientCallbackImpl;

import java.io.IOException;

public class LoginController {

    @FXML
    private ImageView loginBgImage;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView loginImagePane;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label loginTextLabel;

    @FXML
    private Line passwordLine;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Line usernameLine;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label usernameTextLabel;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

//    @FXML
//    public void onLoginButtonClick(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("/lobby-view.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//        stage.centerOnScreen();
//        stage.setResizable(false);
//    }

    private ServerUtility serverUtility;

    private ClientCallbackImpl clientCallbackImpl;

    private ClientCallback clientCallback;

    @FXML
    public void setServerUtility(ServerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


        System.out.println("Username: " + username);
        System.out.println("Password: " + password);


        try {
            serverUtility.login(username, password);

            showSuccessDialog("Login successful");

            serverUtility.loginCallback(clientCallback);


            navigateToLobbyView(event);

        } catch (LoginException e) {
            showErrorDialog("Login failed: " + e.reason);
        } catch (Exception e) {
            showErrorDialog("Login failed: " + e.getMessage());
        }
    }

    private void navigateToLobbyView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lobby-view.fxml"));

            Parent root = fxmlLoader.load();

            LobbyController lobbyController = fxmlLoader.getController();
            lobbyController.setCurrentUsername(usernameTextField.getText());
            lobbyController.setClientCallbackImpl(clientCallbackImpl);
            lobbyController.setClientCallback(clientCallback);
            lobbyController.setServerUtility(serverUtility);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Failed to navigate to lobby view: " + e.getMessage());
        }
    }

    private void showSuccessDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

    public void setClientCallback(ClientCallback cref) {
        clientCallback = cref;
    }
}