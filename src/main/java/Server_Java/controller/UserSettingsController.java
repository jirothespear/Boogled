package Server_Java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Server_Java.model.User;
import Server_Java.sqlconnector.DataPB;

import java.io.IOException;
import java.util.ArrayList;

public class UserSettingsController {
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private ListView<?> listview;

    ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private TextField searchUsernameTextField;


    @FXML
    private TableColumn<User, Integer> columnID;

    @FXML
    private TableColumn<User, String> columnPassword;

    @FXML
    private TableColumn<User, String> columnUsername;

    @FXML
    private TableView<User> tableView;

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/server-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onCreateUserButtonClick(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/create-user.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        root.requestFocus();
    }



    public void initialize (){
        ArrayList<User> users = DataPB.getUsers();
        ObservableList<User> list = FXCollections.observableArrayList(users);
        columnID.setCellValueFactory(new PropertyValueFactory<User,Integer>("userId"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));

        tableView.setItems(list);
    }



        @FXML
        void onDeleteClick(ActionEvent event) {

            User person = tableView.getSelectionModel().getSelectedItem();

            DataPB.deleteUser(person.getUserId());


            ArrayList<User> users = DataPB.getUsers();
            ObservableList<User> list = FXCollections.observableArrayList(users);
            columnID.setCellValueFactory(new PropertyValueFactory<User,Integer>("userId"));
            columnUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
            columnPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));

            tableView.setItems(list);

        }

        @FXML
        void onSearchClick(ActionEvent event) {

            if (searchUsernameTextField.getText().equals("")){
                ArrayList<User> users = DataPB.getUsers();
                ObservableList<User> list = FXCollections.observableArrayList(users);
                columnID.setCellValueFactory(new PropertyValueFactory<User,Integer>("userId"));
                columnUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
                columnPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));

                tableView.setItems(list);
            } else {
                ArrayList<User> users = DataPB.getSearchedUsers(searchUsernameTextField.getText());
                ObservableList<User> list = FXCollections.observableArrayList(users);
                columnID.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
                columnUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
                columnPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

                tableView.setItems(list);

            }



        }

        @FXML
        void onUpdateClick(ActionEvent event) throws IOException {
            User person = tableView.getSelectionModel().getSelectedItem();
        UpdateUserStorage.userId = person.getUserId();
        UpdateUserStorage.username = person.getUsername();
        UpdateUserStorage.password = person.getPassword();

            root = FXMLLoader.load(getClass().getResource("/update-user.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            root.requestFocus();
        }

    }


