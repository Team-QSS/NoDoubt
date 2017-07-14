package qss.nodoubt.room;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import qss.nodoubt.database.Database;

public class UserService {
    private static UserService instance;
    
    private Database database = Database.getInstance();
    private Gson gson = new Gson();
    
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
    
    public User login(String id, String password) {
    	// 사용자가 입력한 아이디, 패스워드를 이용한 로그인을 수행 (id: 사용자의 아이디, password: 사용자의 비밀번호)
    	
    	if (users.size() == 1) {
    	    
    	} else {
    	}
    }
    
    public User login(User user) {
    	// 아이디, 비밀번호가 담긴 User 객체를 이용해서 로그인 수행
    }
}
