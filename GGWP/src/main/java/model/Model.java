package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Model<T> {
	
	public T doRetrieveByKey(String code) throws SQLException;
	
	public ArrayList<T> doRetrieveAll(String order) throws SQLException;
	
	public void doSave(T item) throws SQLException;
	
	public void doUpdate(T item) throws SQLException;
	
	public void doDelete(T item) throws SQLException;
	
}
