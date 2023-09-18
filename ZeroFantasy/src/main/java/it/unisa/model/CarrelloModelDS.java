package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class CarrelloModelDS implements CarrelloModel<Carrello> {

	private DataSource ds = null;
	
	public CarrelloModelDS(DataSource ds) {
		this.ds = ds;
	}
	@Override
	public Carrello doRetriveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Carrello> doRetrieveAll(boolean acquistato) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM carrello WHERE acquistato = ?";
		

		Collection<Carrello> carLib = new LinkedList<Carrello>();
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			preparedStatement.setBoolean(1, acquistato);
			
			Utility.print("doRetrieveAll: "+ preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Carrello a1 = new Carrello();
				
				a1.setName(rs.getString("name"));
				a1.setUsername(rs.getString("username"));
				a1.setPrice(rs.getInt("price"));
				a1.setData(rs.getString("data_acquisto"));
				
				carLib.add(a1);
			}
			
			
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			}finally {
				if(connection != null)
					connection.close();
			}
			
		}
		
		return carLib;
	}

	
	@Override
	public void doSave(Carrello item) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO carrello (name, username, acquistato, data_acquisto, price) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getUsername());
			preparedStatement.setBoolean(3, item.isAcquistato());
			preparedStatement.setString(4, item.getData());
			preparedStatement.setInt(5, item.getPrice());

			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}
	@Override
	public void doUpdate(Carrello item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Utility.print("acquisto di: "+ item.toString());

		String updateSQL = "UPDATE carrello SET acquistato=? WHERE name = ? AND username = ? AND acquistato=? LIMIT 1";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setBoolean(1, item.isAcquistato());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setString(3, item.getUsername());
			preparedStatement.setBoolean(4, !item.isAcquistato());
			

			preparedStatement.executeUpdate();
			
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			}finally {
				if(connection != null)
					connection.close();
			}
			
		}
		
	}
	
	public void doDelete(Carrello item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM carrello WHERE name = ? AND username = ? AND acquistato = 0 LIMIT 1";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getUsername());

			result = preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	

	
	}

}
