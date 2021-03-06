package application;
	
import control.MainLoaderControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.storage.AppointmentCollection;
import model.storage.ClientCollection;
import model.storage.DoctorCollection;
import model.storage.EventCollection;
import model.storage.SecretaryCollection;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/MainPane.fxml"));
			
			Scene scene = new Scene(loader.load(), 690, 440);
			
			MainLoaderControl mainControl = loader.getController();
			ClientCollection clients = new ClientCollection();
			DoctorCollection doctors = new DoctorCollection();
			SecretaryCollection secretaries = new SecretaryCollection();
			AppointmentCollection appointments = new AppointmentCollection();
			EventCollection events = new EventCollection();
					
			mainControl.setClients(clients);
			mainControl.setAppointments(appointments);
			mainControl.setDoctors(doctors);
			mainControl.setSecretaries(secretaries);
			mainControl.setEvents(events);

			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/event.png")));
			primaryStage.setTitle("Clinic Appointment");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
