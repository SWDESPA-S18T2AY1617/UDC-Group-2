package control.doctor;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class DoctorAddControl {

    @FXML private CheckBox wCheck;
    @FXML private CheckBox rptCheck;
    @FXML private DatePicker endDate;
    @FXML private CheckBox tCheck;
    @FXML private CheckBox fCheck;
    @FXML private Button addBtn;
    @FXML private ComboBox<LocalTime> timeStartCmbBox;
    @FXML private CheckBox hCheck;
    @FXML private CheckBox mCheck;
    @FXML private ComboBox<LocalTime> timeEndCmbBox;
    @FXML private DatePicker startDate;


}
