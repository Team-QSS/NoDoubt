package qss.nodoubt.util;

public interface Protocol {
	String DUMMY_PACKET="Dummy";
	
	String REGISTER_REQUEST="RegisterRequest";
	String REGISTER_RESULT="RegisterResult";
	
	String LOGIN_REQUEST="LoginRequest";
	String LOGIN_RESULT="LoginResult";
	
	String ROOM_LIST_REQUEST="RoomListRequest";
	String ROOM_LIST="RoomList";
	
	String CREATE_ROOM_REQUEST="CreateRoomRequest";
	String CREATE_ROOM_RESULT="CreateRoomResult";
	
	String ADD_ROOM="AddRoom";
	String REMOVE_ROOM="RemoveRoom";
	
	String JOIN_ROOM_REQUEST="JoinRoomRequest";
	String JOIN_ROOM_RESULT="JoinRoomResult";
	
	//방목록을 보는사람
	String UPDATE_ROOM="UpdateRoom";
	
	String REFUSE_ROOM_REQUEST="RefuseRoomRequest";
	
	String QUIT_ROOM_REQUEST="QuitRoomRequest";
	String QUIT_ROOM_REPORT="QuitRoomReport";
	
	String READY_ROOM_REQUEST="ReadyRoomRequest";
	String READY_ROOM_REPORT="ReadyRoomReport";
	
	String KICK_ROOM_REQUEST="KickRoomRequest";
	String KICK_ROOM_REPORT="KickRoomReport";
	
	String BAN_ROOM_REQUEST="BanRoomRequest";
	String BAN_ROOM_REPORT="BanRoomReport";
}
