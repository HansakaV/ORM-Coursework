package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.util.PasswordUtils; // Import utility class for password verification
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginSide1;

    @FXML
    private AnchorPane loginSide2;

    @FXML
    private AnchorPane loginSide3;

    @FXML
    private AnchorPane loginSide4;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Hyperlink signUpHyperlink;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);

    @FXML
    void loginBtnOnAction(ActionEvent event) throws IOException {

        String username = txtUsername.getText();
        String plainPassword = txtPassword.getText();

        // Validate empty fields
        if (username.isEmpty() || plainPassword.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please fill in all fields!").show();
            return;
        }

        // Retrieve the hashed password and user type from the database
        String hashedPassword = userBO.getHashedPassword(username);
        String userType = userBO.getUserRole(username);

        // Verify the password
        boolean isPasswordMatch = PasswordUtils.isPasswordMatch(plainPassword, hashedPassword);

        if (isPasswordMatch) {
            new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
            Parent root = loader.load();

            DashboardFormController dashboardController = loader.getController();

            // If the user type is "Admission coordinator", disable buttons in the dashboard
            if (userType.equalsIgnoreCase("Admission coordinator")) {
                dashboardController.disableButtons(userType);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            loginForm.getScene().getWindow().hide();

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials!").show();
        }
    }

    @FXML
    void signUpHyperlinkOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/signUp_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        loginForm.getScene().getWindow().hide();
    }
    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password,txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName,txtUsername);
    }

}


