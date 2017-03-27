package debug;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopupExample extends Application {
  public static void main(String[] args) { launch(args); }

  @Override public void start(final Stage primaryStage) {        
    primaryStage.setTitle("Popup Example");  
    final Popup popup = new Popup();
    
    FXMLLoader loader = new FXMLLoader();

	loader.setLocation(getClass().getResource("/view/EventCellPopup.fxml"));
    AnchorPane pane = null;
	try {
		pane = loader.load();
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	if(pane != null)
		popup.getContent().addAll(pane);

    Button show = new Button("Show");
    
    show.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        popup.show(primaryStage);
      }
    });

    Button hide = new Button("Hide");
    hide.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        popup.hide();
      }
    });

    HBox layout = new HBox(10);
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
    layout.getChildren().addAll(show, hide);
    
    primaryStage.setScene(new Scene(layout, 400, 400));
    primaryStage.show();
  }
}