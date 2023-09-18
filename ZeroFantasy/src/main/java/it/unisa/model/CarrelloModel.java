package it.unisa.model;

import java.sql.SQLException;
import java.util.Collection;

public interface CarrelloModel<T> {


	public T doRetriveByKey(String code) throws SQLException;
	
	public Collection<T> doRetrieveAll(boolean acquistato) throws SQLException;
	
	public void doSave(T item) throws SQLException;
	
	public void doUpdate(T item) throws SQLException;
	
	public void doDelete(T item) throws SQLException;
}
