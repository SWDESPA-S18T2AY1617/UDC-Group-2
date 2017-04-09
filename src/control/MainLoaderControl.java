package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import control.client.ClientMainControl;
import control.doctor.DoctorMainControl;
import control.secretary.SecretaryMainControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.ModelGregorianCalendar;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
import model.storage.DoctorCollection;
import model.storage.EventCollection;
import model.storage.SecretaryCollection;

public class MainLoaderControl {
	@FXML private Button doctorButton;
	@FXML private Button patientButton;
	@FXML private Button secretaryButton;
	
	private List <DoctorMainControl> doctorControllers;
	private List <SecretaryMainControl> secretaryControllers;
	private List <ClientMainControl> clientControllers;
	
	private AppointmentCollection appointments;
	private ClientCollection clients;
	private DoctorCollection doctors;
	private SecretaryCollection secretaries;
	private EventCollection events;
	
	public void setEvents (EventCollection events) {
		this.events = events;
	}

	public void setAppointments(AppointmentCollection appointments) {
		this.appointments = appointments;
	}
	
	public void setClients(ClientCollection clients) {
		this.clients = clients;
	}

	public void setDoctors(DoctorCollection doctors) {
		this.doctors = doctors;
	}

	public void setSecretaries(SecretaryCollection secretaries) {
		this.secretaries = secretaries;
	}
	
	@FXML
	private void initialize () {
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
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/doctor/DoctorMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
		
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			doctorControllers.add(0, loader.getController());
			doctorControllers.get(0).setCalendar(new ModelGregorianCalendar());
			doctorControllers.get(0).setAppointments(appointments);
			doctorControllers.get(0).setDoctors(doctors);
			doctorControllers.get(0).setEvents(events);
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Doctorr.png")));
			stage.setTitle("Doctor Login no. " + doctorControllers.size());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setClientStage () {
		try {
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/client/ClientMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
		
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			clientControllers.add(0, loader.getController());
			clientControllers.get(0).setCalendar(new ModelGregorianCalendar());
			clientControllers.get(0).setClients(clients);
			clientControllers.get(0).setDoctors(doctors);
			clientControllers.get(0).setAppointments(appointments);
			clientControllers.get(0).setEvents(events);
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Patient.png")));
			stage.setTitle("Client Login no. " + clientControllers.size());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setSecretaryStage () {
		try {
			Stage stage = new Stage ();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/secretary/SecretaryMainPane.fxml"));
			Scene scene = new Scene(loader.load(), 1090, 590);
		
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			secretaryControllers.add(0, loader.getController());
			secretaryControllers.get(0).setCalendar(new ModelGregorianCalendar());
			secretaryControllers.get(0).setAppointments(appointments);
			secretaryControllers.get(0).setDoctors(doctors);
			secretaryControllers.get(0).setEvents(events);
			secretaryControllers.get(0).setSecretaries(secretaries);
			secretaryControllers.get(0).setClients(clients);
			
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/Secretary1.png")));
			stage.setTitle("Secretary Login no. " + secretaryControllers.size());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
