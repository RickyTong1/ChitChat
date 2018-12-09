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
	public int senderID;//������id
	public int targetID;//Ŀ����id
	public int roomID;//Ⱥ����id
	
	public int onlineState;
	public int isRead;//�Ƿ��Ѷ�
	public int unreadCounts;//δ����Ϣ����Ŀ
	public int totalCounts;//�ܼ�¼��(�����¼�����Ǻ��Ѽ�¼��)
	
	public String newKey;
	public String key;//����
	public String nickname;
	public String remark;//��ע
	public String style;//����ǩ��
	public String gender;//�Ա�
	public String email;
	public String birth;
	public String phoneNum;//�ֻ���
	//����
	public String targetRemark;//����ע
	public String roomName;
	
	
	public String senderIP;
	public String targetIP;
	public String text;//�ı���Ϣ
	
	public Image pic;
	
	public class Messages implements Serializable{
		public int senderID;
		public String text;
		public String time;
		public int status;
	}
	
	public Messages[] messageslist;//�����¼
	
	
	

	public class Contacts implements Serializable{
		public String nickname;
		public int id;
		public String style;
		public String remark;//��ʱû��
		public Image pic;
		public int status;
	}
	
	public Contacts[] contactslist;
	
	public MessageBlobType type;
	public MessageAnswerType answer;
	public long lastTimeSpeak;
	
	//��������Ϊcounts��������
	public void createContactslist(int counts) {
		contactslist = new Contacts[counts];
		int i;
		for(i=0;i<counts;i++) {
			contactslist[i] = new Contacts();
		}
	}
	//��������Ϊcounts����Ϣ����
	public void createMessagesliist(int counts) {
		totalCounts = counts;//��¼����
		messageslist = new Messages[counts];
		int i;
		for(i=0;i<counts;i++) {
			messageslist[i] = new Messages();
		}
	}
}

