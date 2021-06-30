package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDao {

	private Connection con;

	public loginDao(Connection connection) {
		this.con = connection;
	}

	public boolean isRegistered(String user, String psw) throws SQLException {
		ResultSet retQueryCode = null;
		String query = "SELECT * FROM utente where username=" + "\"" + user + "\"" + " AND password=" + "\"" + psw
				+ "\"";
		PreparedStatement pstatement = null;
		try {
			pstatement = con.prepareStatement(query);
			retQueryCode = pstatement.executeQuery();

			if (retQueryCode.next())
				if (retQueryCode.getString("username") != null) { // parte dal ele -1;
					return true;
				}

		} finally {
			try {
				if (pstatement != null)
					pstatement.close();
			} catch (Exception e2) {
				throw new SQLException(e2);
			}
		}
		return false;

	}

}
