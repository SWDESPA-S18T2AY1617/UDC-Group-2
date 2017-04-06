package model.calendar;

public enum Status {
	DONE, PENDING;
	@Override
	public String toString()
	{
		switch(this)
		{
			case DONE: return "Done";
			case PENDING: return "Pending";
			default: return "None";
		}
	}
	
	public boolean equals (Status o) {
		if(o != this)
			return false;
		return true;
	}

	public static Status of (String string) {
		if(string == null)
			return null;
		switch (string.toLowerCase()) {
			case "done": return DONE;
			case "pending": return PENDING;
			default: return null;
		}
	}
}
