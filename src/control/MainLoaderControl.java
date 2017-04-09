package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import control.client.ClientMainControl;
import control.doctor.DoctorMainControl;
import control.secretary.SecretaryMainControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.calendar.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.DoctorCollection;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainLoaderControl {
	@FXML private AnchorPane frontPane;
	@FXML private ImageView bg;
	@FXML private Rectangle rectangle;
	@FXML private Button doctorButton;
	@FXML private ImageView doctorIcon;
	@FXML private Button patientButton;
	@FXML private ImageView patientIcon;
	@FXML private Button secretaryButton;
	@FXML private ImageView secretaryIcon;
	
	private static int doctorCount;
	private static int clientCount;
	private static int secretaryCount;
	
	private List <DoctorMainControl> doctorControllers;
	private List <SecretaryMainControl> secretaryControllers;
	private List <ClientMainControl> clientControllers;
	
	@FXML
	private void initialize () {
		doctorCount = 0;
		clientCount = 0;
		secretaryCount = 0;

		doctorControllers = new ArrayList <DoctorMainControl> ();
		secretaryControllers = new ArrayList <SecretaryMainControl> ();
		clientControllers = new ArrayList <ClientMainControl> ();
		
		doctorButton.setOnAction(event -> {
			setDoctorStage();
		});
		
		patientButton.setOnAction(event -> {
			setClientStage();
		});
		
		secretaryButton.setOnAction(event -> {
			setSecretaryStage();
		});
	}
	
	private void setDoctorStage () {
		try {
			doctorCount ++;
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/doctor/DoctorMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
			
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			DoctorCollection collections = new DoctorCollection();
			AppointmentCollection appCollections = new AppointmentCollection();
			
			doctorControllers.add(loader.getController());
			doctorControllers.get(doctorCount - 1).setCalendar(new ModelGregorianCalendar());
			doctorControllers.get(doctorCount - 1).initializeUI(collections, appCollections);
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Doctorr.png")));
			stage.setTitle("Doctor Login no. " + doctorCount);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setClientStage () {
		try {
			clientCount ++;
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/client/ClientMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
		
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			clientControllers.add(loader.getController());
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Patient.png")));
			stage.setTitle("Client Login no. " + clientCount);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setSecretaryStage () {
		try {
			secretaryCount ++;
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/secretary/SecretaryMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
		
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			secretaryControllers.add(loader.getController());
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Secretary1.png")));
			stage.setTitle("Secretary Login no. " + secretaryCount);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
