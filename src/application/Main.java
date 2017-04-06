package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/MainPane.fxml"));
			
			Scene scene = new Scene(loader.load(), 690, 440);

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
