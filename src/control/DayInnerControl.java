package control;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import model.calendar.Appointment;
import model.calendar.Details;
import model.calendar.Event;
import model.calendar.EventDetails;
import model.storage.AppointmentCollection;
import model.storage.EventCollection;

public class DayInnerControl extends AnchorPane {

	@FXML
	private AnchorPane schedule;
	private List<CellControl> cells;

	@FXML
	public void initialize() {
		cells = new ArrayList<CellControl>();
	}

	public void initializeButtons(AppointmentCollection appointments) {
		// if (cells != null) {
		// for (CellControl cell: cells) {
		// cell.initializeButtons(appointments);
		// }
		// }
	}

	public void setDataEvents(Iterator<Appointment> itr) {
		if (itr != null) {
			schedule.getChildren().clear();

			List<Appointment> apps = new ArrayList<Appointment>();
			while (itr.hasNext()) {
				apps.add(itr.next());
			}

			for (int i = 0; i < apps.size(); i++) {
				cells.add(new CellControl());
				cells.get(i).setUserData(apps.get(i));
				schedule.getChildren().add(cells.get(i));

				cells.get(i).setLayoutX(0.0);

				Details details = cells.get(i).getUserData().getDetails();

				float start = 0;

				start = details.getTimeStart().getHour() * 60 + details.getTimeStart().getMinute();

				cells.get(i).setLayoutY(start);
			}
		}
	}
}

class CellControl extends AnchorPane {
	private Label text;
	private Appointment app;
	private Popup popup;
	private PopupControl popupControl;

	public final static double DEF_WIDTH = 400;
	public final static double DEF_HEIGHT = 60;

	public CellControl() {
		super();
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("/view/PopupPane.fxml"));
//		popup = new Popup();
//
//		try {
//			popup.getContent().add(loader.load());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		popupControl = loader.getController();

		text = new Label();
		text.setAlignment(Pos.CENTER);
		text.setWrapText(true);
		text.setFont(Font.font(null, FontWeight.BOLD, 14));

		getChildren().add(text);
		setMouseListener();
	}

	public void setUserData(Appointment app) {

		if (app != null) {
			this.app = app;
			setStyle("-fx-background-color: #" + app.getDetails().getColor().toString().substring(2));
			Color color = app.getDetails().getColor();
			color.invert();
			text.setTextFill(color.invert());

			LocalTime timeStart = app.getDetails().getTimeStart();
			LocalTime timeEnd = app.getDetails().getTimeEnd();

			String message = "";
			// if (event.getDetails() instanceof EventDetails) {
			// timeEnd = ((EventDetails)event.getDetails()).getTimeEnd();
			// message = "EVENT: ";
			// } else {
			// timeEnd = timeStart.plusMinutes(30);
			// message = "TASK: ";
			// }

			message = "APPOINTMENT: ";

			double height = (timeEnd.toSecondOfDay() - timeStart.toSecondOfDay()) / DEF_HEIGHT;
			double width = DEF_WIDTH;
			
			System.out.println("MESSAGE: " + message);
			text.setText(message + " " + app.getClient().getName().toString());

			setSize(height, width);
			// popupControl.setUserData(event);
			// popup.setAutoHide(true);
		}
	}

	// public void initializeButtons (AppointmentCollection apppointments) {
	// popupControl.setDeleteButton(appointments);
	// popupControl.setDoneBox(appointments);
	// }

	public void setSize(double height, double width) {
		setPrefSize(width, height);
		setMaxSize(width, height);
		setMinSize(width, height);

		text.setMaxSize(width, height);
		text.setMinSize(width, height);
		text.setPrefSize(width, height);
	}

	public Appointment getUserData() {
		return app;
	}

	public void setMouseListener() {
		setOnMouseClicked(handler -> {
			if (popup.isShowing())
				popup.hide();
			else
				popup.show(this, handler.getScreenX() - popup.getWidth() / 2, handler.getScreenY());
		});
	}
}