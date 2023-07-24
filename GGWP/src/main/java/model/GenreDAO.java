package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class GenreDAO implements Model<Genre>{
	private DataSource ds;
	
	public GenreDAO(DataSource d) {
		ds=d;
	}

	public Genre doRetrieveByKey(String code) throws SQLException {
		return null;
	}

	public ArrayList<Genre> doRetrieveAll(String order) throws SQLException {
		ArrayList<Genre> al=new ArrayList<Genre>();
		Connection c=null;
		ResultSet rs=null;
		PreparedStatement s=null;
		
		String sql="SELECT tipo FROM genere";
		if(order!=null && !order.equals(""))
			sql+=" ORDER BY "+order;

		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			rs=s.executeQuery();
			
			while(rs.next()) {
				Genre g=new Genre();
				g.setType(rs.getString(1));
				al.add(g);
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
		return al;
	}

	public void doSave(Genre item) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;

		String sql = "INSERT INTO genere VALUES(?);";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, item.getType());
			s.executeUpdate();

		} finally {
			if (c != null)
				c.close();
			try {
				if (s != null)
					s.close();
			} finally {}
		}
	}

	public void doUpdate(Genre item) throws SQLException {
		
	}

	public void doDelete(Genre item) throws SQLException {
		
	}
	
	
}
