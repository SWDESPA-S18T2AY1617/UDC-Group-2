package control;

import control.doctor.DoctorMainControl;
import control.doctor.DoctorToolbarControl;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class CalendarControl {

	@FXML private GridPane calendarGrid;
	private ToggleButton [][] days;
	private ToggleGroup group;
	
    @FXML
    public void initialize () {
        assert calendarGrid != null : "fx:id=\"calendarGrid\" was not injected: check your FXML file 'CalendarPane.fxml'.";
        days = new ToggleButton [6][7];
        group = new ToggleGroup();
       
        for (int i = 0; i < days.length; i++) {
        	for (int j = 0; j < days[i].length; j++) {
        		GridCell cell = new GridCell ();
        		cell.setX(i);
        		cell.setY(j);
        		cell.setData(0);
        		
        		days[i][j] = new ToggleButton ();
        		days[i][j].setUserData(cell);
        		days[i][j].setText(" ");
        		days[i][j].setPrefSize(42, 20);
        		days[i][j].setToggleGroup(group);
        		days[i][j].setShape(new Rectangle(42, 20));
        		
    			calendarGrid.add(days[i][j], j, i + 1);
    		}
        }
    }
    
    public void setSelectedInput (int key) {
    	for (int i = 0; i < days.length; i++) {
	    	for (int j = 0; j < days[i].length; j++) {
	    		int day = ((GridCell)days[i][j].getUserData()).getData();
	    		if(day == key)
	    			group.selectToggle(days[i][j]);
	    	}
	    }
    }
    
    public boolean setData (int [][] data) {
		try {
		    for (int i = 0; i < days.length; i++) {
		    	for (int j = 0; j < days[i].length; j++) {
		    		((GridCell)days[i][j].getUserData()).setData(data[i][j]);
		    		if (data[i][j] == 0) {
		    			days[i][j].setText(" ");
		    			days[i][j].setDisable(true);
		    		} else {
		    			days[i][j].setText(String.valueOf(data[i][j]));
		    			days[i][j].setDisable(false);
		    		}
		    	}
		    }
		    return true;
	    } catch (IndexOutOfBoundsException ioe) {
	    	return false;
	    }
    }
    
    public void selectedToggleProperty (DoctorToolbarControl toolbarControl, DoctorMainControl maincontrol) {
    	group.selectedToggleProperty().addListener((ov, oldVal, newVal) -> {
    		if(group.getSelectedToggle() != null) {
				toolbarControl.setDay();
			}
    	});
    }
    
    public int getSelectedInput () {
    	int i = 0;
    	try {
    	i = ((GridCell)group.getSelectedToggle().getUserData()).getData();
    	} catch (NullPointerException ne) {
    		System.err.println("[" + getClass().getName() + "]" + " Selected Input is null!");
    	} catch (ClassCastException cc) {
    		System.err.println("[" + getClass().getName() + "]" + " Class casting failed!");
    	}
    	
    	return i;
    }
}

class GridCell {
	private int x;
	private int y;
	private int data;
	
	public int getData() {
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
}
