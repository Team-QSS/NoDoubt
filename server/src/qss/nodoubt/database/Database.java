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
	
    private Database(){
    	// Driver 객체 동적 로딩 및 connection 인스턴스 초기화
    	
    	try {
    		// 객체 생성 시 서버의 데이터베이스와 연결 
    		Class.forName("com.mysql.jdbc.Driver");
    		connection = DriverManager.getConnection("","","");
            // 서버 내의 데이터베이스와 연결
    	} catch (ClassNotFoundException | SQLException e) {
    		e.printStackTrace();
    	} 
    }
    
    public static Database getInstance(){
    	return instance;
    }
    
    public void executeAndUpdate(String sql) throws SQLException {
    	// DELETE, INSERT, UPDATE SQL 문을 통해 테이블 내의 데이터 조작
    	
    	try (Statement statement = connection.createStatement()) {
    		// try-with-resources 구문을 이용해 statement를 생성 후 try ~ catch 루프 종료 시 statement의 메모리 할당 해제
    		statement.executeUpdate(sql);
        	    // SQL 쿼리문을 데이터베이스에 적용한다	
    	} catch (SQLException sqle) {
		    throw new SQLException (sqle);
    	}
    	
    }
    
    public void executeAndUpdate(String sql, String [] parameters) throws SQLException {
    	/*
    	 * DELETE, INSERT, UPDATE SQL 문을 통해 테이블 내의 데이터 조작
    	 * ? 형태의 플레이스홀더의 인덱스에 대응하는 매개변수를 할당해 동적 SQL 쿼리문 생성 및 적용
    	 */
    	
    	try (PreparedStatement statement = connection.prepareStatement(sql)) {
    		// try-with-resources 구문을 이용해 statement를 생성 후 try ~ catch 루프 종료 시 statement의 메모리 할당 해제
        	
    		for (int i = 0; i < parameters.length; i++) {
        		// 매개변수가 담긴 배열의 인덱스는 0부터, placeholder의 인덱스는 1부터 시작한다
        		statement.setString(i + 1, parameters[i]);
        		    // 플레이스 홀더에 대응하는 매개변수의 값을 적용한다.
        	}
    		
        	statement.executeUpdate();
        	    // statement에 생성되어 있던 동적 SQL 쿼리문을 데이터베이스에 적용한다.
    	} catch (SQLException sqle) {
    		throw new SQLException (sqle);
    	}
    	
    }
    
    public JSONArray executeAndGet(String sql) throws SQLException {
    	// SELECT 문을 통해 테이블 내의 데이터 조회 및 HashMap<String, Object> 형태로 결과 데이터 반환
    	
    	try (Statement statement = connection.createStatement()) {
    		// try-with-resources 구문을 이용해 statement를 생성 후 try ~ catch 루프 종료 시 statement의 메모리 할당 해제
    		
    		ResultSet resultSet = statement.executeQuery(sql);
    		    // SQL 쿼리문을 데이터베이스에 적용하고 조회된 튜플들은 ResultSet 자료형으로 반환된다	
    		return filterData(resultSet);
    		    // 조회한 튜플들의 데이터를 HashMap<String, Object> 형태로 가공 및 반환 
    		
    	} catch (SQLException sqle) {
    		throw new SQLException(sqle);
    	}
    	
    }
    
    public JSONArray executeAndGet(String sql, String [] parameters) throws SQLException {
    	/*
    	 * SELECT 문을 통해 테이블 내의 데이터 조회 및 HashMap<String, Object> 형태로 결과 데이터 반환
    	 * ? 형태의 플레이스홀더의 인덱스에 대응하는 매개변수를 할당해 동적 SQL 쿼리문 생성 및 적용
    	 */
    	
        try (PreparedStatement  statement = connection.prepareStatement(sql)) {
    		// try-with-resources 구문을 이용해 statement를 생성 후 try ~ catch 루프 종료 시 statement의 메모리 할당 해제
    		
            for (int i = 0; i < parameters.length; i++) {
            	// 매개변수가 담긴 배열의 인덱스는 0부터, placeholder의 인덱스는 1부터 시작한다
            	statement.setString(i + 1, parameters[i]);
            	    // 플레이스 홀더에 대응하는 매개변수의 값을 적용한다.
       	    }
    		
    		ResultSet resultSet = statement.executeQuery();
    		    // statement에 생성되어 있던 동적 SQL 쿼리문을 데이터베이스에 적용하고 조회된 튜플들의 데이터는 ResultSet 자료형으로 반환된다
    		return filterData(resultSet);
    		    // 조회한 튜플들의 데이터를 ArrayList 형태로 가공 및 반환 
    		
    	} catch (SQLException sqle) {
    		throw new SQLException(sqle);
    	}
        
    }
    
    private JSONArray filterData(ResultSet resultSet) throws SQLException {
    	/*
    	 * 튜플들의 데이터가 담긴 ResultSet 객체를 ArrayList 형태로 가공 및 반환
    	 */
    	
    	JSONArray results = new JSONArray();
    	// 조회한 튜플들의 데이터 목록을 저장하는 JSONArray 컬렉션
    	
    	while (resultSet.next()) {
    		// 튜플들의 목록을 순회한다
    		JSONObject result = new JSONObject();
		        // 튜플의 데이터를 담는 JSONObject 컬랙션
    		
			ResultSetMetaData metaData = resultSet.getMetaData();
			    // 해당 튜플 내 메타데이터를 조회한다
			
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
	            // 칼럼의 인덱스는 1부터 시작한다
    		    String label = metaData.getColumnLabel(i);
    		        // 튜플 내 i번째 칼럼의 이름 얻어오기
    		    Object value = resultSet.getObject(i);
    		    
    		        // 튜플 내 i번째 칼럼의 값 얻어오기
    		    result.put(label, value);
    		        // 튜플 내 i번째 칼럼의 이름, 값을 results 해시맵에 저장
			}
			
			results.add(result);
			    // results JSONArray에 튜플의 데이터를 추가한다 
		}
    	
    	return results;
    	    // ArrayList 형태로 가공한 결과값을 반환한다
    }
    
    public static void main (String[] args) {
    	/*
    	 * 데이터베이스 테스트 
    	 * 전용 메인 메서드
    	 */
    	
    	try {
    		
    		JSONArray results = Database.getInstance()
                                        .executeAndGet("SELECT * FROM member");
    		for(Object obj:results) {
    			// 결과로 반환된 튜플의 데이터들을 조회
    			JSONObject result = (JSONObject)obj;
    			
    			for (Object key : result.keySet()) {
    				// 튜플의 데이터를 조회
    				Object value = result.get(key);
    				System.out.println(key + " : " + value);
    			}
    		}
    		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}    	
    	
    }
    
}
