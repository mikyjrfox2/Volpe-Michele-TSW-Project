package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class CartDAO implements Model<Cart>{
	private DataSource ds;
	
	public CartDAO(DataSource d) {
		ds=d;
	}
	
	public Cart doRetrieveByKey(String code) throws SQLException {
		return null;
	}

	public ArrayList<Cart> doRetrieveAll(String order) throws SQLException {
		ArrayList<Cart> cart=null;
		Connection c=null;
		ResultSet rs=null;
		PreparedStatement s=null;
		
		String sql="SELECT * FROM carrello";
		if(!order.equals(""))
			sql+=" ORDER BY "+order+" DESC";
		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			rs=s.executeQuery();
			cart=new ArrayList<Cart>();
			while(rs.next()) {
				Cart ca=new Cart();
				ca.setActualCost(rs.getDouble("prezzo_effettivo"));
				ca.setCost(rs.getDouble("prezzo"));
				ca.setEmail(rs.getString("email"));
				ca.setPurchased(rs.getBoolean("acquistato"));
				ca.setPurchaseDate(rs.getDate("data_acquisto"));
				ca.setVg(rs.getString("titolo"));
				ca.setImg(rs.getString("immagine"));
				cart.add(ca);
			}
		}finally {
			if(c!=null)
				c.close();
			try {
				if(s!=null)
					s.close();
			}finally{
				if(rs!=null)
					rs.close();
				}
		}
		return cart;
	}

	public void doSave(Cart item) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		String sql="INSERT INTO carrello(titolo, email, acquistato, immagine, prezzo) VALUES(?,?,0,?,?)";
		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			s.setString(1, item.getVg());
			s.setString(2, item.getEmail());
			s.setString(3, item.getImg());
			s.setDouble(4, item.getCost());
			s.executeUpdate();
			
		}finally {
			if(c!=null)
				c.close();
			try {
				if(s!=null)
					s.close();
			}finally {}
		}
		
	}

	public void doUpdate(Cart item) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		String sql="UPDATE carrello SET acquistato=?, data_acquisto=?, prezzo_effettivo=? WHERE email=? AND titolo=?";
		java.util.Date today=new java.util.Date();
		
		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			s.setBoolean(1, true);
			s.setDate(2, new java.sql.Date(today.getTime()));
			s.setDouble(3, item.getActualCost());
			s.setString(4, item.getEmail());
			s.setString(5, item.getVg());
			s.executeUpdate();
		}finally {
			if(c!=null)
				c.close();
			try {
				if(s!=null)
					s.close();
			}finally{}
		}
	}

	public void doDelete(Cart item) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		String sql="DELETE FROM carrello WHERE titolo=? AND email=?";
		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			s.setString(1, item.getVg());
			s.setString(2, item.getEmail());
			s.executeUpdate();
		}finally {
			if(c!=null)
				c.close();
			try {
				if(s!=null)
					s.close();
			}finally {}
		}
	}

}
