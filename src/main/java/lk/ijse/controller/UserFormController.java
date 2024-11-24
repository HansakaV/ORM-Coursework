package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import lk.ijse.view.tm.UserTm;

import java.util.List;

public class UserFormController {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Pane userForm;

    @FXML
    private Text userHeading;

    @FXML
    private AnchorPane userPage;

    @FXML
    private TableView<UserTm> userTbl;

    @FXML
    private Button clearBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);


    public void initialize(){
        setCellValueFactory();
        loadAllUsers();
        addTableSelectionListener();
    }

    private void clearFields(){
        txtUserName.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtPassword.setText("");

    }

    private void addTableSelectionListener() {
        userTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getUserDetails(newValue);
            }
        });
    }
    private void getUserDetails(UserTm userTm) {
        txtUserName.setText(userTm.getUserName());
        txtPassword.setText(userTm.getPassword());
        txtEmail.setText(userTm.getEmail());
        txtContact.setText(userTm.getContact());

    }
    private void setCellValueFactory() {
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllUsers(){
        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try {
            List<UserDTO> userList = userBO.getAllUsers();
            for (UserDTO userDTO : userList){
                UserTm userTm = new UserTm(
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        userDTO.getEmail(),
                        userDTO.getMobile()
                );
                obList.add(userTm);
            }
            userTbl.setItems(obList);
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {

        String userName = txtUserName.getText();

        try {
            boolean isDeleted = userBO.deleteUser(userName);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"User deleted!").show();
                loadAllUsers();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String name = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        UserDTO userDTO = userBO.searchByEmail(email);

        if ((isContactValid()) && (isEmailValid()) && (isNameValid()) && (isPasswordValid())) {

            boolean isUpdated = userBO.updateUser(new UserDTO(userDTO.getUserId(), userDTO.getUserType(), name, password, email, contact));

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
                loadAllUsers();
            }
        }
        else {
            new Alert(Alert.AlertType.ERROR,"User not updated!").show();
        }
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Contact,txtContact);
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        if (isEmailValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid email!Try again").show();
        }
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        if(isNameValid()){
            new Alert(Alert.AlertType.INFORMATION,"Should have to consist of numbers and letters with 6-16 characters!" +"\n"+
                    "ex:Password1").show();
        }
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        if(isPasswordValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid format!").show();
        }
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName,txtUserName);
    }

    public boolean isContactValid(){
        if (!Regex.setTextColor(TextFields.Contact,txtContact)) return false;
        return true;
    }
    public boolean isNameValid(){
        if(!Regex.setTextColor(TextFields.UserName,txtUserName));
        return true;
    }
    public boolean isEmailValid(){
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmail));
        return true;
    }
    public boolean isPasswordValid(){
        if (!Regex.setTextColor(TextFields.Password,txtPassword)) return false;
        return true;
    }
}
