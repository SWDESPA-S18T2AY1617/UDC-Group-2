package control;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import model.EventObserver;
import model.ModelGregorianCalendar;
import model.storage.EventCollection;

public class MainControl extends EventObserver {
	@FXML AnchorPane dayView;
	@FXML AnchorPane agendaView;
	@FXML AnchorPane addView;
	@FXML AnchorPane toolbarView;
	
	@FXML ToolbarControl toolbarViewController;
	@FXML DayControl dayViewController;
	@FXML AgendaControl agendaViewController;
	@FXML AddControl addViewController;
	
	@FXML
	public void initialize () {
		setDayVisible();
	}
	
	private void initializeUI (EventCollection events) {
		agendaViewController.initializeButtons(events);
		addViewController.initializeButtons(events);
		toolbarViewController.initializeButtons(this);
		dayViewController.initializeButtons(events);
	}
	
	public void setCalendarData (ModelGregorianCalendar mgc) {
		toolbarViewController.setState(mgc);
	}
	
	public void setDayVisible () {
		dayView.setVisible(true);
		addView.setVisible(false);
		agendaView.setVisible(false);
		addViewController.clearFields();
	}
	
	public void setAddVisible () {
		dayView.setVisible(false);
		addView.setVisible(true);
		agendaView.setVisible(false);
	}
	
	public void setAgendaVisible () {
		dayView.setVisible(false);
		addView.setVisible(false);
		agendaView.setVisible(true);
		addViewController.clearFields();
	}

	public void setAdd(boolean value) {
		addViewController.isStatus(value);
	}

	@Override
	public void update() {
		collections.openDB();
		
		if (toolbarViewController.showEvents() && toolbarViewController.showTasks()) {
			agendaViewController.setEvents(collections.getAllByDate(toolbarViewController.getDay()));
			dayViewController.setEvents(collections.getAllByDate(toolbarViewController.getDay()));	
		} else if (toolbarViewController.showEvents()) {
			agendaViewController.setEvents(collections.getEventsForThisDay(toolbarViewController.getDay()));
			dayViewController.setEvents(collections.getEventsForThisDay(toolbarViewController.getDay()));
		} else if (toolbarViewController.showTasks()) {
			agendaViewController.setEvents(collections.getTasksForThisDay(toolbarViewController.getDay()));
			dayViewController.setEvents(collections.getTasksForThisDay(toolbarViewController.getDay()));
		}
		
		dayViewController.initializeButtons(collections);
		agendaViewController.initializeButtons(collections);
		
		collections.closeDB();
	}

	public void initializeController(EventCollection collections) {
		initializeUI(collections);
		setEvents(collections);
	}
}
