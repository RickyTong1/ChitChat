package CComponents;
public enum MessageBlobType{
	/*
	 * ö������:
	 * ��������,ͼƬ����,������¼,�����ǳ�,��������,����ɾ������,������Ӻ�������,������Ӻ��Ѵ�
	 * ѯ���Ƿ�����,����ע��,�������Һ���,����������������,����������������,�����������ϴ�,�����������ϴ�
	 * �����������ϸ��£������������ϸ���, ������Ϣ����������Ϣ��
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
}
