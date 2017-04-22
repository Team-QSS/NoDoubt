package qss.nodoubt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	/*
	 * 서버 내의 데이터베이스를 다루는 싱글톤 객체 
	 */
	
	private Connection connection;
	
	private static Database instance = new Database();
	
    private Database(){
    	
    	try {
    		
    		Class.forName("com.mysql.jdbc.Driver");
    		connection = DriverManager.getConnection("", "", "");
            // 서버 내의 데이터베이스와 연결
    		
    	} catch (ClassNotFoundException cne) {
    		cne.printStackTrace();
    	} catch (SQLException sqle) {
    		sqle.printStackTrace();
    	}
    	
    }
    
    public static Database getInstance(){
    	return instance;
    }
    
    public void executeAndUpdate(String sql) throws SQLException {
    	
    	// DELETE, INSERT, UPDATE SQL 문을 통해 테이블 내의 데이터 조작
    	
    	Statement statement = connection.createStatement();
    	statement.executeUpdate(sql);
    	
    }
    
    public void executeAndUpdate(String sql, String [] parameters) throws SQLException {
    	
    	// DELETE, INSERT, UPDATE SQL 문을 통해 테이블 내의 데이터 조작
    	// ? 형태의 플레이스홀더와 그들에 대응하는 매개변수 대입
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	
    	for (int i = 0; i < parameters.length; i++) {
    		statement.setString(i + 1, parameters[i]);
    	}
    	
    	statement.executeUpdate();
    	
    }
    
    public ResultSet executeAndGet(String sql) throws SQLException {
    	
    	// SELECT 문을 통해 테이블 내의 데이터 조회
    	
    	
    	Statement statement = connection.createStatement();
    	return statement.executeQuery(sql);
    	
    }
    
    public ResultSet executeAndGet(String sql, String [] parameters) throws SQLException {
    	
    	// SELECT 문을 통해 테이블 내의 데이터 조회
    	// ? 형태의 플레이스홀더와 그들에 대응하는 매개변수 대입
    	
    	PreparedStatement statement = connection.prepareStatement(sql);
    	
    	for (int i = 0; i < parameters.length; i++) {
   		    statement.setString(i + 1, parameters[i]);
   	    }
    	
    	return statement.executeQuery();
    	
    }
    
    public static void main(String[] args) {
    	
    	/*
    	 * 데이터베이스 테스트 전용 메인 메서드
    	 */
    	
    	try {
			
    		ResultSet result = Database.getInstance()
    				                   .executeAndGet("SELECT * FROM member WHERE id=?", new String[] {"user001"});
			
    		while (result.next()) {
    			
    			System.out.println("id : " + result.getString("id"));
    			System.out.println("password : " + result.getString("password"));
    			System.out.println("name : " + result.getString("name"));
    			
    		}
    		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}    	
    	
    }
}
