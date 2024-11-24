package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.util.PasswordUtils;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;

public class SignUpFormController {

    @FXML
    private ComboBox<String> cmbUser;

    @FXML
    private TextField txtUserId;

    @FXML
    private AnchorPane signForm;

    @FXML
    private Button signUpBtn;

    @FXML
    private Pane signUpDetails;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobile;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword2;

    @FXML
    private TextField txtUsername;

    @FXML
    private Hyperlink loginHyperlink;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);

    public void initialize(){
        setUsers();
        getCurrentUserId();
    }

    @FXML
    void signUpBtnOnAction(ActionEvent event) {

        String userId = txtUserId.getText();
        String userType = cmbUser.getValue();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String password2 = txtPassword2.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();




        if (userId.isEmpty() || userType.isEmpty() || userName.isEmpty() || password.isEmpty() || password2.isEmpty() || email.isEmpty() || mobile.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields!").show();

            } else if (password.equals(password2)) {

                if(isPasswordValid() && isNameValid() && isEmailValid() && isContactValid()) {

                    String hashedPassword = PasswordUtils.hashPassword(password);

                    UserDTO userDTO = new UserDTO(userId,userType,userName,hashedPassword,email,mobile);

                    boolean isSaved = userBO.saveUser(userDTO);

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "User not saved!Passwords are not matched!").show();
            }

    }

    public void setUsers(){
        cmbUser.getItems().addAll("Admin","Admission coordinator");
    }

    @FXML
    void hyperlinkLoginOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        signForm.getScene().getWindow().hide();
    }
    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    @FXML
    void txtMobileOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Contact,txtMobile);
    }

    @FXML
    void txtPassword2OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password,txtPassword2);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password,txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName,txtUsername);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
          if(isPasswordValid()){
              new Alert(Alert.AlertType.INFORMATION,"Invalid format!").show();
          }
    }
    @FXML
    void txtPassword2OnAction(ActionEvent event) {

         if (!(txtPassword.getText().equalsIgnoreCase(txtPassword2.getText()))){
             new Alert(Alert.AlertType.INFORMATION,"Passwords are do not match!").show();
         }
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        if (isEmailValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid email!Try again").show();
        }
    }

    @FXML
    void txtMobileOnAction(ActionEvent event) {
        if (isContactValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid contact type!").show();
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {

        if(isNameValid()){
            new Alert(Alert.AlertType.INFORMATION,"Should have to consist of numbers and letters with 6-16 characters!" +"\n"+
                    "ex:Password1").show();
        }
    }

    public boolean isNameValid(){
        if(!Regex.setTextColor(TextFields.UserName,txtUsername));
        return true;
    }
    public boolean isContactValid(){
        if (!Regex.setTextColor(TextFields.Contact,txtMobile)) return false;
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
    private void getCurrentUserId(){

        String currentId = userBO.getCurrentUserId();
        String nextId = generateNextUserId(currentId);
        txtUserId.setText(nextId);

    }

    private String generateNextUserId(String currentId) {
        if (currentId != null && currentId.matches("U\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "U" + String.format("%03d", ++idNum);
        }
        return "U001";
    }
}



