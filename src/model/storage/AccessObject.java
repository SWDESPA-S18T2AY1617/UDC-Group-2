package model.storage;

import java.util.Iterator;

public abstract class AccessObject <T> {
	public abstract Iterator <T> getAll ();
	public abstract T get (int id);
	public abstract boolean update (T object);
	public abstract boolean add (T object);
	public abstract boolean delete (T object);
}
