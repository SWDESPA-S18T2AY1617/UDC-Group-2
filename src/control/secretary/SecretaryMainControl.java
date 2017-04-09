package control.secretary;

import control.AgendaControl;
import control.DayControl;
import control.ToolbarControl;
import control.WeekControl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import model.AppointmentObserver;
import model.ModelGregorianCalendar;
import model.Secretary;
import model.storage.ClinicDB;
import model.storage.DoctorCollection;
import model.storage.EventCollection;
import model.storage.SecretaryCollection;

public class SecretaryMainControl extends AppointmentObserver {
	
	@FXML private AnchorPane agenda;
    @FXML private AnchorPane day;
    @FXML private AnchorPane week;
    @FXML private AnchorPane toolbar;
    @FXML private AnchorPane login;
    @FXML private AnchorPane register;
    @FXML private AnchorPane startup;
    
    @FXML private AgendaControl agendaController;
    @FXML private DayControl dayController;
    @FXML private WeekControl weekController;
    @FXML private SecretaryRegisterControl registerController;
    @FXML private SecretaryLoginControl loginController;
    @FXML private SecretaryStartupControl startupController;
    @FXML private ToolbarControl toolbarController; 

	private EventCollection events;
	private DoctorCollection doctors;
	private Secretary mainSecretary;
	
	private ModelGregorianCalendar mgc;
	
	public void setDoctors(DoctorCollection doctors) {
		this.doctors = doctors;
		ClinicDB.openConnection();
		toolbarController.setDoctors(this.doctors.getAll());
		ClinicDB.closeConnection();
	}
	
	public void setEvents(EventCollection events) {
		this.events = events;
	}
	
    public void setCalendar (ModelGregorianCalendar mgc) {
    	this.mgc = mgc;
    	toolbarController.setCalendar(mgc);
    }
    
    @FXML
	public void initialize () {
		setToolbarPane();
		setStartupVisible();
		setStart();
		
		setStartupVisible();
	}

	private void setStart() {
		startupController.setEvents(this);
	}

	public void setMainSecretary (Secretary s) {
		this.mainSecretary = s;
		toolbar.setDisable(false);
		
		if (s == null) {
			toolbar.setDisable(true);
			setStartupVisible();
		}
	}
	
	public void setToolbarPane () {
		toolbarController.setParent(this);
		toolbarController.setLogoutAction(logout -> {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle("Logout Confirmation!");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");
    		alert.showAndWait();
    		
    		if(alert.getResult() == ButtonType.OK) {
    			setStartupVisible();
    			mainSecretary = null;
    			toolbar.setDisable(true);
    		} 
		});
		
		toolbarController.setDayAction(day -> {
			setDayVisible();
		});
		
		toolbarController.setWeekAction(week -> {
			setWeekVisible();
		});
		
		toolbarController.setReservationAction(reserve -> {
			update();
		});
		
		
		toolbarController.setDoctors(null);
		toolbar.setDisable(true);
	}
    
    public void setStartupVisible () {
    	startup.setVisible(true);
    	agenda.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    }
    
    public void setLoginVisible () {
    	startup.setVisible(false);
    	agenda.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(true);
    	register.setVisible(false);
    }
    
    public void setRegisterVisible () {
    	startup.setVisible(false);
    	agenda.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(true);
    }
    
    public void setAgendaVisible () {
    	startup.setVisible(false);
    	agenda.setVisible(true);
    	week.setVisible(false);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    }
    
    public void setDayVisible () {
    	startup.setVisible(false);
    	agenda.setVisible(false);
    	week.setVisible(false);
    	day.setVisible(true);
    	login.setVisible(false);
    	register.setVisible(false);
    }
    
    public void setWeekVisible () {
    	startup.setVisible(false);
    	agenda.setVisible(false);
    	week.setVisible(true);
    	day.setVisible(false);
    	login.setVisible(false);
    	register.setVisible(false);
    }

	@Override
	public void update() {
		
	}

	public void setSecretaries(SecretaryCollection secretaries) {
		loginController.setEvents(this, secretaries);
		registerController.setEvents(this, secretaries);
	}
}
