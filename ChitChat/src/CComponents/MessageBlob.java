package CComponents;

import java.awt.Image;
import java.io.Serializable;


public class MessageBlob implements Serializable{
	/*
	* ����̳�Serializable�ӿ�,��ͨ�����������Ϊ�ֽ�byte[]��,��������������������.
	* 
	* ���ļ�������,�ͻ��������˵�ͨ�ŵ�λ��Ϊ�����ʵ��.
	* ���ļ�������,�ͻ��������˵�ͨ�ŵ�λ��Ϊ�����ʵ��.
	* 
	* ͨ������MessageBlobOperator���еľ�̬����public static byte[] pack(MessageBlob obj)����ת��Ϊbyte[].
	* ͨ������MessageBlobOperator���еľ�̬����public static MessageBlob unpack(byte[] obj)���仹ԭΪMessageBlobʵ��.
	* ����ͨ������MessageBlobOperator������Ӧ�ľ�̬������ȡMessageBlob�е���Ϣ.
	*
	*/
	int senderID;
	int targetID;
	
	int onlineState;
	int isRead;//�Ƿ��Ѷ�
	
	String key;//����
	String nickname;
	String remark;//��ע
	String style;//����ǩ��
	String gender;//�Ա�
	String email;
	String birth;
	String phoneNum;//�ֻ���
	
	String[] messagesUnread;//δ����Ϣ
	
	
	String senderIP;
	String targetIP;
	
	
	Image pic;

	class Contacts{
		String nickname;
		int id;
		String style;
		String remark;
		Image pic;
	}
	
	Contacts[] contactslist;
	
	MessageBlobType type;
	MessageAnswerType answer;
	long lastTimeSpeak;
	
	
}

