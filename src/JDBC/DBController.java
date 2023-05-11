package JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import Config.Question;
import JDBC.mysqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class DBController {
	
	
	public static ArrayList<Question> getAllQuestions() {
		
		ArrayList<Question> questions = new ArrayList<Question>();
		String sqlQuery = "SELECT * FROM Question";
		try {
			try {
				if (mysqlConnection.getConnection() != null) {
					Statement st = mysqlConnection.conn.createStatement();
					ResultSet rs = st.executeQuery(sqlQuery);

					while (rs.next()) {
						Question question = new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
	                    questions.add(question);
						//System.out.println(question);
					}

					rs.close();
				} else
					System.out.println("myConn is NULL!");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
}

