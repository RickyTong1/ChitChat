package CComponents;

import java.awt.Image;
import java.io.Serializable;

import Client.Contacts;

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
	int id;
	int onlineState;
	int isRead;//�Ƿ��Ѷ�
	
	String nickname;
	String remark;//��ע
	String style;//����ǩ��
	String gender;//�Ա�
	String[] messagesUnread;//δ����Ϣ
	
	Image pic;

	Contacts[] contactList;//�����б�
	
	MessageBlobType type;
	MessageBlobType answer;
	long lastTimeSpeak;
}
