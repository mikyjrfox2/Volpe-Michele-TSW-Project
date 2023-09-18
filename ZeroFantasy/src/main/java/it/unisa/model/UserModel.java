package it.unisa.model;

import java.sql.SQLException;
import java.util.Collection;

public interface UserModel<T> {

	public T doRetriveByKey(String code) throws SQLException;
	
	public Collection<T> doRetrieveAll() throws SQLException;
	
	public void doSave(T item) throws SQLException;
	
	public void doUpdate(T item) throws SQLException;
	
	public void doDelete(int item) throws SQLException;

}
