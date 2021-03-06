package CComponents;
public enum MessageBlobType{
	/*
	 * 枚举类型:
	 * 文字聊天,图片聊天,··登录,··登出,··更改密码,··删除好友,··添加好友请求,··添加好友答复
	 * 询问是否在线,··注册,··查找好友,··个人资料请求,··好友资料请求,··个人资料答复,··好友资料答复
	 * ··个人资料更新，··好友资料更新, ··聊天信息请求，··聊天信息答复
	 * 服务端文件接受请求，服务端文件发送请求,个人文件记录删除
	 * */
	CHAT_TEXT,CHAT_PICTURE,LOGIN,LOGOUT
	,EDIT_MY_KEY,DELETE_CONTACT
	,ADD_CONTACT_QUEST,ADD_CONTACT_ANSWER
	,ONLINE_STATE_QUEST,ONLINE_STATE_ANSWER
	,REGISTER,FIND_FRIEND
	,SELF_PROFILE_QUEST,FRIEND_PROFILE_QUEST
	,SELF_PROFILE_ANSWER,FRIEND_PROFILE_ANSWER
	,SELF_PROFILE_UPDATE,FRIEND_PROFILE_UPDATE
	,CHAT_CONTENT_QUEST,CHAT_CONTENT_ANSWER
	,FRIEND_LIST_QUEST,FRIEND_LIST_ANSWER
	/*暂时未实现*/
	,RECEIVE_FILE,SEND_FILE,DELETE_SELF_FILE
	/*新增需求:个人验证表请求，个人文件表请求，验证表和文件请求*/
	,SELF_VERIFY,SELF_FILE,VERIFY_FILE
}
