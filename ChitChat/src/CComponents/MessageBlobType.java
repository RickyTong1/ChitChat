package CComponents;
enum MessageBlobType{
	/*
	 * ö������:
	 * ��������,ͼƬ����,��¼,�ǳ�,���ĸ�������,��������,ɾ������,��Ӻ�������,��Ӻ��Ѵ�
	 * ѯ���Ƿ�����,ע��,���Һ���,��������,����,
	 * */
	CHAT_TEXT,CHAT_PICTURE,LOGIN,LOGOUT
	,EDIT_MY_PROFILE,EDIT_MY_KEY,DELETE_CONTACT
	,ADD_CONTACT_QUEST,ADD_CONTACT_ANSWER
	,ONLINE_STATE_QUEST,ONLINE_STATE_ANSWER
	,REGISTER,FIND_FRIEND,PROFILE_QUEST,PROFILE,
	
}
enum MessageAnswerType{
	//�����(ͬ����Ӻ��ѡ�����),�����(��ͬ����Ӻ��ѡ�������)
	POSITIVE,NEGATIVE
}
