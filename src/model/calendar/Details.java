package model.calendar;

import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

public abstract class Details {

	private Color color;
	private Month month;
	private Year year;
	private Integer dayOfMonth;
	private LocalTime timeStart;
	private LocalTime timeEnd;

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public Month getMonth() {
		return month;
	}

	public Year getYear() {
		return year;
	}
	
	public LocalTime getTimeEnd() {
		return timeEnd;
	}
	
	public LocalTime getTimeStart() {
		return timeStart;
	}

	public Color getColor() {
		return color;
	}
	
	public void setTimeEnd (LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public void setDayOfMonth (int day) {
		this.dayOfMonth = day;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}

	public String toString() {
		return "DATE: " + LocalDate.of(year.getValue(), month, dayOfMonth).toString() + "\nCOLOR: " + color.toString() + "\nTIME START: " + timeStart.toString();
	}
}
