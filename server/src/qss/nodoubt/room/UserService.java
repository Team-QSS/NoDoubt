package qss.nodoubt.room;

import org.json.simple.JSONArray;

import qss.nodoubt.database.Database;

public class UserService {
    private static UserService instance;
    private Database database = Database.getInstance();
    
    private UserService(){}
    
    public int create(String id, String password, String name) {
    	String sql = "INSERT users (id, password, name) VALUES (?, ?, ?)";
    	return database.executeAndUpdate(sql, id, password, name);
    }
    
    public JSONArray read(String id) {
    	// 중복 사용자 확인을 위해 id만을 이용해서 사용자 조회 
    	String sql = "SELECT * FROM users WHERE id=?";
    	return database.executeAndGet(sql, id);
    }
    
    public JSONArray read(String id, String password) {
    	// 로그인을 위해 id, password를 이용해서 사용자 조회
    	String sql = "SELECT * FROM users WHERE id=? && password=?";
    	return database.executeAndGet(sql, id, password);
    }
}
