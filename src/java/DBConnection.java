
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
public class DBConnection {
	private  String connectionString = "jdbc:mysql://localhost:3306/dbpj?Unicode=true&characterEncoding=utf8";
	private  String DBUsername = "root";
	private  String DBUserPassword = "270329zuki";
	private  Connection connection;
	
	static {
		try {
			// class name for mysql driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public DBConnection(){
		
	}
	public DBConnection(String connection,String name, String password){
		this.connectionString=connection;
		this.DBUsername=name;
		this.DBUserPassword=password;
	}
	public  Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(connectionString,
						DBUsername, DBUserPassword);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//JOptionPane.showMessageDialog(null, "连接数据库失败,错误为：\n"+e.getMessage(),"连接失败",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return null;
			}
		}
		return connection;
	}
}
