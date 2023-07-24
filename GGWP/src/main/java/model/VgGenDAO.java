package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class VgGenDAO implements Model<VgGen> {
	private DataSource ds;

	public VgGenDAO(DataSource d) {
		ds = d;
	}

	public VgGen doRetrieveByKey(String code) throws SQLException {
		return null;
	}

	public ArrayList<VgGen> doRetrieveAll(String search) throws SQLException {
		ArrayList<VgGen> al = new ArrayList<VgGen>();
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement s = null;

		String sql = "SELECT * FROM vggenere";
		if(search!=null && !search.equals("")) 
			sql+=" WHERE genere='"+search+"';";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			rs = s.executeQuery();

			while (rs.next()) {
				VgGen vg = new VgGen();
				vg.setGenere(rs.getString(1));
				vg.setVg(rs.getString(2));
				al.add(vg);
			}
		} finally {
			if (c != null)
				c.close();
			try {
				if (s != null)
					s.close();
			} finally {
				if (rs != null)
					rs.close();
			}
		}

		return al;
	}

	public void doSave(VgGen item) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		
		String sql="INSERT INTO vggenere VALUES(?,?)";
		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, item.getGenere());
			s.setString(2, item.getVg());
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

	public void doUpdate(VgGen item) throws SQLException {
		
	}

	public void doDelete(VgGen item) throws SQLException {
		Connection c=null;
		PreparedStatement s=null;
		
		String sql="DELETE FROM vggenere WHERE genere=? AND gioco=?";
		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, item.getGenere());
			s.setString(2, item.getVg());
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

}
