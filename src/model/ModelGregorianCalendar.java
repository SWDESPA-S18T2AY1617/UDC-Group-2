package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ModelGregorianCalendar extends GregorianCalendar {
	private static final long serialVersionUID = 1L;
	
	private List <CalendarObserver> calendarObservers;
	
	public ModelGregorianCalendar () {
		calendarObservers = new ArrayList <CalendarObserver> ();
	}
	public void register (CalendarObserver obs) {
		calendarObservers.add(obs);
	}
	
	public void unregister (CalendarObserver obs) {
		calendarObservers.remove(obs);
	}
	
	public void setDate (LocalDate date) {
		set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
		set(Calendar.MONTH, date.getMonthValue() - 1);
		set(Calendar.YEAR, date.getYear());
		notifyAllObservers();
	}
	
	public LocalDate selectedDate () {
		return LocalDate.of(get(Calendar.YEAR), Month.of(get(Calendar.MONTH) + 1), get(Calendar.DAY_OF_MONTH));
	}
	
	public void jumpToCurrentDate () {
		setDate(LocalDate.now());
		notifyAllObservers();
	}
	
	public int dayOfMonthBound () {
		return YearMonth.of(get(Calendar.YEAR), Month.of(get(Calendar.MONTH) + 1)).lengthOfMonth();
	}
	
	private void notifyAllObservers () {
		for(int i = 0; i < calendarObservers.size(); i++)
			calendarObservers.get(i).update();
	}
	
	@Override
	public void add (int field, int value) {
		super.add(field, value);
		notifyAllObservers();
	}
	
	@Override
	public void set (int field, int value) {
		super.set(field, value);
		notifyAllObservers();
	}
}
