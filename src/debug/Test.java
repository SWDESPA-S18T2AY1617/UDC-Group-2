package debug;

import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.Iterator;

import javafx.scene.paint.Color;
import model.Event;
import model.Status;
import model.TaskDetails;
import model.storage.EventCollection;
import model.storage.EventDB;

public class Test {
	public static void main (String [] args) {
		EventDB db = new EventDB (); 
		EventCollection eventCollection = new EventCollection(db);
		
		eventCollection.openDB();
	
		TaskDetails task = new TaskDetails();
		
		task.setColor(Color.ANTIQUEWHITE);
		task.setDayOfMonth(7);
		task.setMonth(Month.MARCH);
		task.setStatus(Status.PENDING);
		task.setTimeStart(LocalTime.of(6, 30));
		task.setYear(Year.of(2017));
	
		Event event = new Event();
		
		Iterator <Event> i = eventCollection.getEventsOnly();
		
		while (i.hasNext()) {
			event = i.next();
			System.out.println(event);
		}
		
		i = eventCollection.getTasksOnly();
		if(i!=null)
		while (i.hasNext()) {
			event = i.next();
			System.out.println(event);
		}
		
		i = eventCollection.getTasksOnly();
		if(i!=null)
		while (i.hasNext()) {
			event = i.next();
			System.out.println(event);
		}
		
		i = eventCollection.getTasksOnly();
		if(i!=null)
		while (i.hasNext()) {
			event = i.next();
			System.out.println(event);
		}
		
		i = eventCollection.getTasksOnly();
		if(i!=null)
		while (i.hasNext()) {
			event = i.next();
			System.out.println(event);
			eventCollection.remove(event);
		}
		
		
		eventCollection.closeDB();
		
		
	}
}
