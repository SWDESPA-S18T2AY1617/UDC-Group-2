package debug;
	
import control.DayInnerControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.storage.EventCollection;
import model.storage.EventDB;


public class TestControl extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/DayInnerPane.fxml"));
			
			Pane root = new Pane ();
			Scene scene = new Scene(root, 530, 500);
			
			root.getChildren().add(loader.load());
			scene.getStylesheets().add(getClass().getResource("/config/style-config.css").toExternalForm());
			
			primaryStage.setTitle("My Productivity Tool");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			EventDB db = new EventDB();
			EventCollection ec = new EventCollection(db);
			ec.openDB();
			
			DayInnerControl control = loader.getController();
			
			control.setDataEvents(ec.getAll());
			
			ec.closeDB();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
