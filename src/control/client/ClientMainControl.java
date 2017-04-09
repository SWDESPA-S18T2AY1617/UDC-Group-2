package control.client;

import control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.calendar.ModelGregorianCalendar;
import model.storage.ClientCollection;
import javafx.scene.image.ImageView;

public class ClientMainControl {

	@FXML private AnchorPane clientMainPane;	//where huhu
	@FXML private AnchorPane appointment;
	@FXML private AnchorPane week;
	@FXML private AnchorPane day;
	@FXML private AnchorPane clientLogin;
	@FXML private AnchorPane clientRegister;
	@FXML private AnchorPane clientStart;
	@FXML private ImageView bg;
	
	@FXML private CalendarControl calendarViewController;
    @FXML private AgendaControl agendaViewController;
    @FXML private DayControl dayViewController;
    @FXML private WeekControl weekViewController;
	
	@FXML private ClientLoginControl clientLoginController;
	@FXML private ClientRegisterControl clientRegisterController;
	@FXML private ClientStartupControl clientStartController;
	
	@FXML
	public void initialize () {
		setStartupVisible();
	}
	
	 public void enableLogout() {
	    	//secToolbarController.enableLogout();
	    }
	    
	    public void clearFields() {
	    	clientLoginController.clearFields();
	    	clientRegisterController.clearFields();
	    }
	    
	    public void initializeUI (ClientCollection clientCollections) {
	    	clientStartController.initializeButtons(this);
	    	clientLoginController.initializeButtons(this, clientCollections);
	    	clientRegisterController.initializeButtons(this, clientCollections);
	    	//secToolbarController.initializeButtons(this, secCollections);
	    }

	    public void setCalendar (ModelGregorianCalendar mgc) {
	    	//secToolbarController.setState(mgc);
	    }
	    
	    public void setStartupVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(true);
	    	appointment.setVisible(false);
	    	week.setVisible(false);
	    	day.setVisible(false);
	    	clientLogin.setVisible(false);
	    	clientRegister.setVisible(false);
	    }
	    
	    public void setLoginVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(false);
	    	appointment.setVisible(false);
	    	week.setVisible(false);
	    	day.setVisible(false);
	    	clientLogin.setVisible(true);
	    	clientRegister.setVisible(false);
	    }
	    
	    public void setRegisterVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(false);
	    	appointment.setVisible(false);
	    	week.setVisible(false);
	    	day.setVisible(false);
	    	clientLogin.setVisible(false);
	    	clientRegister.setVisible(true);
	    }
	    
	    public void setAgendaVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(false);
	    	appointment.setVisible(true);			//is this the agenda view of client??
	    	week.setVisible(false);
	    	day.setVisible(false);
	    	clientLogin.setVisible(false);
	    	clientRegister.setVisible(false);
	    }
	    
	    public void setDayVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(false);
	    	appointment.setVisible(false);
	    	week.setVisible(false);
	    	day.setVisible(true);
	    	clientLogin.setVisible(false);
	    	clientRegister.setVisible(false);
	    }
	    
	    public void setWeekVisible () {
	    	clientMainPane.setVisible(true); 		//not sure
	    	clientStart.setVisible(false);
	    	appointment.setVisible(false);
	    	week.setVisible(true);
	    	day.setVisible(false);
	    	clientLogin.setVisible(false);
	    	clientRegister.setVisible(false);
	    }
	    
	    public void initializeComboBox(ClientCollection clientCollections) {
			//secToolbarController.initializeComboBox(secCollections);
		}
}
