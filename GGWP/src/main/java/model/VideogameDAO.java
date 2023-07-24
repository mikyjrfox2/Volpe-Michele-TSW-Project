package model;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

public class VideogameDAO implements Model<Videogame>{
	private DataSource ds;
	
	public VideogameDAO(DataSource d) {
		ds=d;
	}
	
	public Videogame doRetrieveByKey(String code) throws SQLException {
		Videogame vg=null;
		Connection c=null;
		ResultSet rs=null;
		PreparedStatement s=null;
		
		String sql="SELECT * FROM videogioco WHERE titolo=\""+code+"\"";

		try {
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			rs=s.executeQuery();
			rs.next();

			vg=new Videogame();
			vg.setDescription(rs.getString("descrizione"));
			vg.setDeveloper(rs.getString("sviluppatore"));
			vg.setPrice(rs.getDouble("prezzo"));
			vg.setPublisher(rs.getString("editore"));
			vg.setReleaseDate(rs.getDate("data_pubb"));
			vg.setTitle(rs.getString("titolo"));
			vg.setVideo(rs.getString("video"));
			vg.setImage(rs.getString("immagine"));
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
		return vg;
	}

	
	public ArrayList<Videogame> doRetrieveAll(String order) throws SQLException {
		ArrayList<Videogame> al=new ArrayList<Videogame>();
		Connection c=null;
		ResultSet rs=null;
		PreparedStatement s=null;
		
		String sql="SELECT * FROM videogioco";
			
		try {
			
			c=ds.getConnection();
			s=c.prepareStatement(sql);
			rs=s.executeQuery();
			
			
			if(order!=null && !order.equals(""))
				sql+=" ORDER BY "+order;
						
			rs=s.executeQuery(sql);
			
			while(rs.next()) {
				Videogame vg=new Videogame();
				vg.setDescription(rs.getString("descrizione"));
				vg.setDeveloper(rs.getString("sviluppatore"));
				vg.setPrice(rs.getDouble("prezzo"));
				vg.setPublisher(rs.getString("editore"));
				vg.setReleaseDate(rs.getDate("data_pubb"));
				vg.setTitle(rs.getString("titolo"));
				vg.setVideo(rs.getString("video"));
				vg.setImage(rs.getString("immagine"));
				vg.setAddDate(rs.getDate("data_agg"));
				vg.setnAcq(rs.getInt("nAcq"));
				al.add(vg);
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

	
	public void doSave(Videogame v) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		
		String sql="INSERT INTO videogioco VALUES(?,?,?,?,?,?,?,?,?,0)";
		
		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, v.getTitle());
			s.setDate(2, v.getReleaseDate());
			s.setString(3, v.getDeveloper());
			s.setString(4, v.getPublisher());
			s.setString(5, v.getDescription());
			s.setDouble(6, v.getPrice());
			s.setString(7, v.getImage());
			s.setString(8, v.getVideo());
			java.util.Date oggi=new java.util.Date();
			s.setDate(9, new Date(oggi.getTime()));
			s.executeUpdate();
		}finally {
			if (c != null)
				c.close();
			try {
				if (s != null)
					s.close();
			} finally {}
		}
	}

	
	public void doUpdate(Videogame v) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "UPDATE videogioco SET descrizione=\""+v.getDescription()+"\", prezzo="+v.getPrice()+", immagine=\""+v.getImage()+"\", video=\""+v.getVideo()+"\", nAcq="+v.getnAcq()+" WHERE titolo=\""+v.getTitle()+"\"";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.executeUpdate();
		}finally {
			if (c != null)
				c.close();
			try {
				if (s != null)
					s.close();
			} finally {}
		}
	}

	public void doDelete(Videogame v) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		String sqlVg = "DELETE FROM videogioco WHERE titolo=?";
		String sqlGen="DELETE FROM vggenere WHERE gioco=?";
		
		try {
			c = ds.getConnection();
			s = c.prepareStatement(sqlGen);
			s.setString(1, v.getTitle());
			s.executeUpdate();
			s.close();
			s=c.prepareStatement(sqlVg);
			s.setString(1, v.getTitle());
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

}
