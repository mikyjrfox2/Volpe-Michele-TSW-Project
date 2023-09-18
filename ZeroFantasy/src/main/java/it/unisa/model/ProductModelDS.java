package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import it.unisa.utils.Utility;

public class ProductModelDS implements ProductModel<ProductBean> {

	private DataSource ds = null;
	
	public ProductModelDS(DataSource ds) {
		this.ds = ds;
	}
	@Override
	public ProductBean doRetriveByKey(String code) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ProductBean> doRetrieveAll() throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String selectSQL = "SELECT * FROM product";
		
		
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			Utility.print("doRetrieveAll: "+ preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				ProductBean bean = new ProductBean();
				
				bean.setCode(rs.getInt("code"));
				bean.setName(rs.getString("name"));
				bean.setDescription(rs.getString("description"));
				bean.setPrice(rs.getInt("price"));
				
				products.add(bean);
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
		
		return products;
		
	}

	@Override
	public void doSave(ProductBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO product (code, name, description, price, quantity) VALUES (?, ?, ?, ?, ?)";

		try {
			connection = ds.getConnection();
			
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, item.getCode());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setString(3, item.getDescription());
			preparedStatement.setFloat(4, item.getPrice());

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
	public void doUpdate(ProductBean item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		PreparedStatement preparedStatement2 = null;
		

		ProductBean bean = new ProductBean();
		String selectSQL = "SELECT * FROM product WHERE code=?";

		String updateSQL = "UPDATE product SET name=?, description=?, price=? WHERE code=?";
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, item.getCode());
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				bean.setCode(rs.getInt("code"));
				if(item.getName()!=null) {
					bean.setName(item.getName());
				}else {
					bean.setName(rs.getString("name"));
				}
				if(item.getDescription()!=null) {
					bean.setDescription(item.getDescription());
				}else {
					bean.setDescription(rs.getString("description"));
				}
				if(item.getPrice()!=-1) {
					bean.setPrice(item.getPrice());
				}else {
					bean.setPrice(rs.getInt("price"));
				}
			}
			
			preparedStatement2 = connection.prepareStatement(updateSQL);

	        // Imposta i parametri della query con i dati del prodotto
	        preparedStatement2.setString(1, bean.getName());
	        preparedStatement2.setString(2, bean.getDescription());
	        preparedStatement2.setInt(3, bean.getPrice());
	        preparedStatement2.setInt(4, bean.getCode());

	        // Esegui l'aggiornamento nel database
	        preparedStatement2.executeUpdate();
			
			
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

	@Override
	public void doDelete(int item) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM product WHERE code = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, item);

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
