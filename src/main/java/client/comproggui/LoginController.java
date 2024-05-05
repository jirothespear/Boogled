package client.comproggui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

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

    @FXML
    public void onLoginButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/lobby-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }
}
