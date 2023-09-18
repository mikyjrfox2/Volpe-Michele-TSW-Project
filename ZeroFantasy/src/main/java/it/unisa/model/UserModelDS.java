package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class UserModelDS implements UserModel<User> {

	private DataSource ds = null;
	
	public UserModelDS(DataSource ds) {
		this.ds = ds;
	}
	@Override
	public User doRetriveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> doRetrieveAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM user";
		
		
		
		Collection<User> adminList = new LinkedList<User>();
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			Utility.print("doRetrieveAll: "+ preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				User a1 = new User();
				
				a1.setPortafoglio(rs.getInt("portafoglio"));
				a1.setUsername(rs.getString("username"));
				a1.setPassword(rs.getString("password"));
				a1.setTipo(rs.getString("tipo"));
				
				adminList.add(a1);
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
		
		return adminList;
		
	}

	
	@Override
	public void doSave(User item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO user (username, password, tipo, portafoglio) VALUES (?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getUsername());
			preparedStatement.setString(2, item.getPassword());
			preparedStatement.setString(3, item.getTipo());
			preparedStatement.setInt(4, 0);

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
	public void doUpdate(User item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		PreparedStatement preparedStatement2 = null;


		User bean = new User();
		String selectSQL = "SELECT * FROM user WHERE username=?";
		
		String updateSQL = "UPDATE user SET portafoglio=? WHERE username=?";
		
		Utility.print(item.toString());
		try {
			
			connection = ds.getConnection();
			preparedStatement2 = connection.prepareStatement(selectSQL);
			preparedStatement2.setString(1, item.getUsername());
			
			
			ResultSet rs = preparedStatement2.executeQuery();
			
			while(rs.next()) {
				bean.setUsername(rs.getString("username"));
				bean.setPortafoglio(rs.getInt("portafoglio"));
			}
			
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, item.getPortafoglio() + bean.getPortafoglio());
			preparedStatement.setString(2, item.getUsername());
			
			preparedStatement.executeUpdate();
			
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();

				if(preparedStatement2 != null)
					preparedStatement2.close();
			}finally {
				if(connection != null)
					connection.close();
			}
			
		}
		
	}
	
	public void doDelete(int item) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
