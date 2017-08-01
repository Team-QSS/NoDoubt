package qss.nodoubt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Database {
	/*
	 * 서버 내의 데이터베이스를 다루는 싱글톤 객체 
	 */
	
	private Connection connection;
	private static Database instance = new Database();
	
    private Database() {
    	// Driver 객체 동적 로딩 및 connection 인스턴스 초기화
    	try {
    		// 객체 생성 시 서버의 데이터베이스와 연결 
    		Class.forName("com.mysql.jdbc.Driver");
    		    // Driver 클래스를 동적 로딩 및 생성
    		connection = DriverManager.getConnection("jdbc:mysql://35.160.125.239/no_doubt?useSSL=true", "dongly", "ehdrmfdl");
                // 서버 내의 데이터베이스와의 커넥션 생성
    	} catch (ClassNotFoundException | SQLException e) {
    		e.printStackTrace();
    	} 
    }
    
    public static Database getInstance() {
    	return instance;
    }
    
    public JSONArray executeAndGet(String sql, Object ... objects) {
    	// SELECT와 같이 테이블의 값을 조회하는 쿼리 실행
    	try {
    		// connection PreparedStatement 실행
    		PreparedStatement statement = connection.prepareStatement(sql);
        	if (objects.length > 0) {
        		// 플레이스 홀더 (물음표 : ?) 가 담긴 SQL 문을 실행 시 파라미터 조회
    			int index = 1;
    			for (Object object : objects) {
        			statement.setObject(index++, object);
        		}
        		return filterData(statement.executeQuery());
        	} else {
        		// 플레이스 홀더가 없을 경우 바로 SQL 실행
        		return filterData(statement.executeQuery());
        	} 
    	} catch (SQLException sqlE) {
    		return null;
    	}
    }
    
    public int executeAndUpdate(String sql, Object ... objects) {
    	// DELETE, INSERT, UPDATE와 같이 테이블의 값을 변경하는 쿼리 실행
    	try {
    		PreparedStatement statement = connection.prepareStatement(sql);
        	if (objects.length > 0) {
    			int index = 1;
    			for (Object object : objects) {
        			statement.setObject(index++, object);
        		}
        		return statement.executeUpdate();
        	} else {
        		return statement.executeUpdate();
        	} 
    	} catch (SQLException sqlE) {
    		return -1;
    	}
    }
    
    private static JSONArray filterData(ResultSet resultSet) throws SQLException{
    	// 튜플들의 데이터가 담긴 ResultSet 객체를 ArrayList 형태로 가공 및 반환
    	JSONArray results = new JSONArray();
 
    	while (resultSet.next()) {
    		// 튜플들의 목록을 조회한다
    		JSONObject result = new JSONObject();
			ResultSetMetaData metaData = resultSet.getMetaData(); 
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				// 해당 튜플 내 메타데이터를 조회한다
    		    String label = metaData.getColumnLabel(i);
    		    Object value = resultSet.getObject(i);
    		    result.put(label, value);
			}
			results.add(result);    // results JSONArray에 튜플의 데이터를 추가한다 
		}
    	
    	return (results.size() > 0) ? results : null;    // JSONArray 형태로 가공한 결과값을 반환한다
    }
}

