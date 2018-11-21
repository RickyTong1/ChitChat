package CComponents;
enum MessageBlobType{
	/*
	 * 枚举类型:
	 * 文字聊天,图片聊天,登录,登出,更改个人资料,更改密码,删除好友,添加好友请求,添加好友答复
	 * 询问是否在线,正面答复(同意添加好友、在线),负面答复(不同意添加好友、不在线)
	 * */
	CHAT_TEXT,CHAT_PICTURE,LOGIN,LOGOUT
	,EDIT_MY_PROFILE,EDIT_MY_KEY,DELETE_CONTACT
	,ADD_CONTACT_QUEST,ADD_CONTACT_ANSWER
	,ONLINE_STATE_QUEST,ONLINE_STATE_ANSWER
	,POSITIVE_ANSWER,NEGATIVE_ANSWER
}
