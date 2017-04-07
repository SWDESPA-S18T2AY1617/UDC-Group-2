package control.client;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;

public class ClientMainControl {

	@FXML private AnchorPane clientMainPane;
	@FXML private AnchorPane appointment;
	@FXML private AnchorPane week;
	@FXML private AnchorPane day;
	@FXML private AnchorPane clientLogin;
	@FXML private AnchorPane clientRegister;
	@FXML private AnchorPane clientStart;
	@FXML private ImageView bg;
	
	@FXML private ClientLoginControl clientLoginController;
	@FXML private ClientRegisterControl clientRegisterController;
	@FXML private ClientStartupControl clientStartController;
	
	@FXML
	public void initialize () {
		
	}
}
