package Client_Java.controllers;

import CORBA_IDL.Utility.ClientCallback;
import CORBA_IDL.Utility.GameStartException;
import CORBA_IDL.Utility.LoginException;
import CORBA_IDL.Utility.PlayerUtility;
import javafx.application.Platform;
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
import Client_Java.model.ClientCallbackImpl;

import java.io.IOException;
import java.util.Properties;

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

    private String gameID;

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

    private PlayerUtility serverUtility;

    private ClientCallbackImpl clientCallbackImpl;

    private ClientCallback clientCallback;

    @FXML
    public void setServerUtility(PlayerUtility serverUtility) {
        this.serverUtility = serverUtility;
    }

    @FXML
    public void onLoginButtonClick(ActionEvent event) {
        Properties props = new Properties();


        String username = usernameTextField.getText();
        String password = passwordTextField.getText();


        System.out.println("Username: " + username);
        System.out.println("Password: " + password);


        try {
            serverUtility.login(username, password);

            serverUtility.userCallback(clientCallback, username);
            if (serverUtility.getActiveGames() > 0 ){
                if (serverUtility.getGameID(username) != null) {
                    System.out.println("User is in game");
                    serverUtility.reconnect(username, serverUtility.getGameID(username), clientCallback);
                    navigateInGame(username);
                } else {
                    navigateToLobbyView(event);
                }
            } else {
                navigateToLobbyView(event);
            }



        } catch (LoginException e) {

        } catch (Exception e) {
        }
    }  // onLoginButtonClick
    @FXML
    public void navigateInGame(String currentUser) {
        Platform.runLater(() -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/game-room-view.fxml"));
                Parent root = loader.load();
                GameRoomController gameRoomController = loader.getController();

                gameID = serverUtility.getGameID(currentUser);
                gameRoomController.setGameID(gameID);
                gameRoomController.setCurrentGameUser(currentUser);
                gameRoomController.setServerUtility(serverUtility);
                gameRoomController.setClientCallback(clientCallback);
                clientCallbackImpl.setGameRoomController(gameRoomController);
                gameRoomController.setClientCallbackImpl(clientCallbackImpl);

                Scene gameScene = new Scene(root);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(gameScene);
                stage.show();
                stage.centerOnScreen();
                stage.setResizable(false);
                gameScene.getStylesheets().add(getClass().getResource("/Font.css").toExternalForm());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (GameStartException e) {
                throw new RuntimeException(e);
            }
        });
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
        }
    }





    public void setClientCallbackImpl(ClientCallbackImpl clientCallbackImpl) {
        this.clientCallbackImpl = clientCallbackImpl;
    }

    public void setClientCallback(ClientCallback cref) {
        clientCallback = cref;
    }
}