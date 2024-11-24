package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.view.tm.StudentTm2;

import java.util.List;

public class ViewRegistrationsFormController {



    @FXML
    private TableColumn<?, ?> colCoordinatorId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Text studentHeading;

    @FXML
    private Button viewBtn;

    @FXML
    private AnchorPane viewRegistrationForm;

    @FXML
    private TableView<StudentTm2> viewTbl;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.STUDENT);

    public void initialize(){
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCoordinatorId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }

    @FXML
    void viewBtnOnAction(ActionEvent event) {
         viewAllStudents();
    }

    private void viewAllStudents(){
        ObservableList<StudentTm2> obList = FXCollections.observableArrayList();

        try {
            List<StudentDTO> studentList = studentBO.getRegisteredStudents();

            for(StudentDTO studentDTO : studentList){

                StudentTm2 studentTm2 = new StudentTm2(
                        studentDTO.getStudent_id(),
                        studentDTO.getName(),
                        studentDTO.getUser().getUserId()
                );

                obList.add(studentTm2);
            }
            System.out.println("done1");
            viewTbl.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading students: " + e.getMessage()).show();
        }

    }
}
