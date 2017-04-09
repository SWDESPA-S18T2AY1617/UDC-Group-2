package control.doctor;

import control.AgendaControl;
import control.DayControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.Doctor;
import model.calendar.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;

public class DoctorMainControl {

    @FXML private AnchorPane agendaView;
    @FXML private AnchorPane dayView;
    @FXML private AnchorPane weekView;
    @FXML private AnchorPane doctorToolbar;
    @FXML private AnchorPane loginView;
    @FXML private AnchorPane registerView;
    @FXML private AnchorPane startupView;
    @FXML private AnchorPane addView;
    
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
    @FXML private DoctorToolbarControl doctorToolbarController;
    @FXML private DoctorLoginControl loginViewController;
    @FXML private DoctorRegisterControl registerViewController;
    @FXML private DoctorStartupControl startupViewController;
    @FXML private DoctorAddControl addViewController;
    
    @FXML
    void initialize () {
    	setStartupVisible();
    	doctorToolbarController.disableLogout();
    }
    
    public void enableLogout() {
    	doctorToolbarController.enableLogout();
    }
    
    public void clearFields() {
    	loginViewController.clearFields();
    	registerViewController.clearFields();
    }
    
    public void initializeUI (DoctorCollection collections, AppointmentCollection appCollections) {
    	startupViewController.initializeButtons(this);
    	loginViewController.initializeButtons(this, collections);
    	registerViewController.initializeButtons(this, collections);
    	doctorToolbarController.initializeButtons(this, collections, appCollections);
    	dayViewController.initializeButtons(appCollections, null, null);
    }
    
    public void setCalendar (ModelGregorianCalendar mgc) {
    	doctorToolbarController.setState(mgc);
    }
    
    public void setStartupVisible () {
    	startupView.setVisible(true);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setLoginVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(true);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setRegisterVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(true);
    	addView.setVisible(false);
    }
    
    public void setAgendaVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(true);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setDayVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(true);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setWeekVisible () {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(true);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(false);
    }
    
    public void setAddSlotsVisible() {
    	startupView.setVisible(false);
    	agendaView.setVisible(false);
    	weekView.setVisible(false);
    	dayView.setVisible(false);
    	loginView.setVisible(false);
    	registerView.setVisible(false);
    	addView.setVisible(true);
	}

	public void setDayAppointments(AppointmentCollection ac) {
		ClinicDB.openConnection();
//		dayViewController.setAppointments(ac.getAllOccupiedAppointments(doctor));
		dayViewController.setDocAppointments(ac.getAll());
		System.out.println("WHUT");
		ClinicDB.closeConnection();
	}
	
	public AgendaControl getAgendaControl () {
		return agendaViewController;
	}

	
    
}
