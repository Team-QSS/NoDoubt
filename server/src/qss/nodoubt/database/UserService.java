package qss.nodoubt.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import qss.nodoubt.room.User;

public class UserService {
    private static UserService instance = new UserService();
    
    private Database database = Database.getInstance();
    private Gson gson = new Gson();
    
    private UserService(){}
    public static UserService getInstance() {
    	return instance;
    }
    
    public int create(String id, String password, String name) {
    	String sql = "INSERT users (id, password, name) VALUES (?, ?, ?)";
    	return database.executeAndUpdate(sql, id, password, name);
    }
    
    public JSONArray read(String id) {
    	// 중복 사용자 확인을 위해 id만을 이용해서 사용자 조회 
    	String sql = "SELECT id as ID, password, name FROM users WHERE id=?";
    	return database.executeAndGet(sql, id);
    }
    
    public JSONArray read(String id, String password) {
    	// 로그인을 위해 id, password를 이용해서 사용자 조회
    	String sql = "SELECT id as ID, password, name FROM users WHERE id=? && password=?";
    	return database.executeAndGet(sql, id, password);
    }
    
    public User login(String id, String password) {
    	// 사용자가 입력한 아이디, 패스워드를 이용한 로그인을 수행 (id: 사용자의 아이디, password: 사용자의 비밀번호)
    	JSONArray users = read(id, password);                     // 아이디, 패스워드를 이용해 사용자 목록 조회
    	
    	if (users.size() == 1) {
    	    JSONObject data = (JSONObject) users.get(0);     // JSON 배열의 길이가 1일 때, 사용자 인증이 성공함 
    	    
    	    User user = gson.fromJson(data.toString(), User.class);    
    	    return user;
    	} else {
    		return null;
    	}
    }
    
    public User login(User user) {
    	// 아이디, 비밀번호가 담긴 User 객체를 이용해서 로그인 수행
    	return login(user.getID(), user.getPassword());
    }
}
