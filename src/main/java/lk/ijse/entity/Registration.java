package lk.ijse.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Registration {

    @Id
    private String registrationID;
    private String date;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
    private String studentName;
    private String programName;
    private double programFee;
    private double upfrontPayment;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL)
    List<Payment> payments;

    public Registration() {
    }

    public Registration(String registrationID, String date, Student student, Program program, String studentName, String programName, double programFee, double upfrontPayment) {
        this.registrationID = registrationID;
        this.date = date;
        this.student = student;
        this.program = program;
        this.studentName = studentName;
        this.programName = programName;
        this.programFee = programFee;
        this.upfrontPayment = upfrontPayment;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public double getProgramFee() {
        return programFee;
    }

    public void setProgramFee(double programFee) {
        this.programFee = programFee;
    }

    public double getUpfrontPayment() {
        return upfrontPayment;
    }

    public void setUpfrontPayment(double upfrontPayment) {
        this.upfrontPayment = upfrontPayment;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationID='" + registrationID + '\'' +
                ", date='" + date + '\'' +
                ", student=" + student +
                ", program=" + program +
                ", studentName='" + studentName + '\'' +
                ", programName='" + programName + '\'' +
                ", programFee=" + programFee +
                ", upfrontPayment=" + upfrontPayment +
                '}';
    }
}
