package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    private AnchorPane countSection;

    @FXML
    private Text count1;

    @FXML
    private Text count2;

    @FXML
    private Text count3;

    @FXML
    private Button courseBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private AnchorPane graphSection;

    @FXML
    private Button paymentBtn;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private Button studentBtn;

    @FXML
    private TextField txtUserType;

    @FXML
    private TextField txtUsername;

    @FXML
    private Button userBtn;

    @FXML
    private Button registrationBtn;


    @FXML
    void BtnCourseOnAction(ActionEvent event) throws IOException {
        try {
            AnchorPane coursesPane = FXMLLoader.load(getClass().getResource("/view/courses_form.fxml"));
            centerNode.getChildren().clear();
            centerNode.getChildren().add(coursesPane);
        } catch (IOException e) {
            e.printStackTrace();
            // Log or handle the IOException
        }
        if (courseBtn.isDisable()){
            return;
        }
    }


    public void disableButtons(String userType){

        if ("Admission coordinator".equalsIgnoreCase(userType)){
            courseBtn.setDisable(true);
            paymentBtn.setDisable(true);

        }
    }
    @FXML
    void BtnPaymentOnAction(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        centerNode.getChildren().clear();
        centerNode.getChildren().add(paymentPane);

        if (paymentBtn.isDisable()){
            return;
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane studentPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        dashboardForm.getChildren().clear();
        dashboardForm.getChildren().add(studentPane);


    }

    @FXML
    void btnStudentOAction(ActionEvent event) throws IOException {
        AnchorPane studentPane = FXMLLoader.load(this.getClass().getResource("/view/student_form.fxml"));
        centerNode.getChildren().clear();
        centerNode.getChildren().add(studentPane);
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        try {
            AnchorPane userPane = FXMLLoader.load(getClass().getResource("/view/user_form.fxml"));
            centerNode.getChildren().clear();
            centerNode.getChildren().add(userPane);
        } catch (IOException e) {
            e.printStackTrace();
            // Log or handle the IOException
        }
    }

    @FXML
    void registrationBtnOnAction(ActionEvent event) throws IOException {
        AnchorPane registrationPane = FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));
        centerNode.getChildren().clear();
        centerNode.getChildren().add(registrationPane);
    }

    @FXML
    void studentViewBtnOnAction(ActionEvent event) {

    }
}


