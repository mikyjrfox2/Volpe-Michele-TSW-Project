package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class UserDAO implements Model<User> {

	private DataSource ds;

	public UserDAO(DataSource d) {
		ds = d;
	}

	public User doRetrieveByKey(String code) throws SQLException {
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		User u = null;

		String sql = "SELECT * FROM utenti WHERE email=?";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, code);
			rs = s.executeQuery();
			rs.next();

			u = new User();
			u.setBio(rs.getString("bio"));
			u.setEmail(rs.getString("email"));
			u.setName(rs.getString("nome"));
			u.setNickname(rs.getString("nickname"));
			u.setPassword(rs.getString("password"));
			u.setPoints(rs.getInt("punti"));
			u.setProfilePic(rs.getString("immagine"));
			u.setRole(rs.getString("tipo"));
			u.setWallet(rs.getDouble("portafoglio"));

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

		return u;
	}

	public ArrayList<User> doRetrieveAll(String order) throws SQLException {
		Connection c = null;
		ResultSet rs = null;
		PreparedStatement s = null;
		ArrayList<User> al = new ArrayList<User>();

		String sql = "SELECT * FROM utenti";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			rs = s.executeQuery();
			rs = s.executeQuery(sql);

			while (rs.next()) {
				User u = new User();
				u.setBio(rs.getString("bio"));
				u.setEmail(rs.getString("email"));
				u.setName(rs.getString("nome"));
				u.setNickname(rs.getString("nickname"));
				u.setPassword(rs.getString("password"));
				u.setPoints(rs.getInt("punti"));
				u.setProfilePic(rs.getString("immagine"));
				u.setRole(rs.getString("tipo"));
				u.setWallet(rs.getDouble("portafoglio"));
				al.add(u);
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

	public void doSave(User item) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;

		String sql = "INSERT INTO utenti VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, item.getEmail());
			s.setString(2, item.getPassword());
			s.setString(3, item.getRole());
			s.setString(4, item.getNickname());
			s.setString(5, item.getName());
			s.setString(6, item.getBio());
			s.setString(7, item.getProfilePic());
			s.setInt(8, item.getPoints());
			s.setDouble(9, item.getWallet());
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

	public void doUpdate(User item) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "UPDATE utenti SET punti=?, portafoglio=?, password=?, nickname=?, nome=?, bio=?, immagine=? WHERE email=?";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setInt(1, item.getPoints());
			s.setDouble(2, item.getWallet());
			s.setString(3, item.getPassword());
			s.setString(4, item.getNickname());
			s.setString(5, item.getName());
			s.setString(6, item.getBio());
			s.setString(7, item.getProfilePic());
			s.setString(8, item.getEmail());
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

	public void doDelete(User item) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		String sql = "DELETE FROM utenti WHERE email=?";

		try {
			c = ds.getConnection();
			s = c.prepareStatement(sql);
			s.setString(1, item.getEmail());
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
