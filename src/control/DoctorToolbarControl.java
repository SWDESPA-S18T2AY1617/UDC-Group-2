package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import model.user.Doctor;

public class DoctorToolbarControl {
	
    @FXML private AnchorPane calendar;
    @FXML private Button prevBtn;
    @FXML private Button logoutBtn;
    @FXML private RadioButton dayRBtn;
    @FXML private Button calendarBtn;
    @FXML private Label yearLabel;
    @FXML private Label monthLabel;
    @FXML private Button slotsBtn;
    @FXML private Button agendaBtn;
    @FXML private ComboBox <Doctor> doctorComboBox;
    @FXML private Button todayBtn;
    @FXML private CheckBox checkDoc2;
    @FXML private CheckBox checkDoc3;
    @FXML private RadioButton weekRBtn;
    @FXML private CheckBox checkDoc1;
    @FXML private AnchorPane doctorToolBarPane;
    @FXML private Button nextBtn;

    @FXML 
    void initialize () {
    	
    }
}
