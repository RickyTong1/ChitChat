package CComponents;

import java.awt.Image;
import java.io.Serializable;


public class MessageBlob implements Serializable{
	/*
	* 该类继承Serializable接口,可通过对象流输出为字节byte[]型,进而被打包发送至服务端.
	* 
	* 除文件传输外,客户端与服务端的通信单位都为该类的实例.
	* 除文件传输外,客户端与服务端的通信单位都为该类的实例.
	* 
	* 通过调用MessageBlobOperator类中的静态方法public static byte[] pack(MessageBlob obj)将其转换为byte[].
	* 通过调用MessageBlobOperator类中的静态方法public static MessageBlob unpack(byte[] obj)将其还原为MessageBlob实例.
	* 建议通过调用MessageBlobOperator类中相应的静态方法提取MessageBlob中的信息.
	*
	*/
	public int senderID;//发送者id
	public int targetID;//目标者id
	public int roomID;//群房间id
	
	public int onlineState;
	public int isRead;//是否已读
	
	public String key;//密码
	public String nickname;
	public String remark;//备注
	public String style;//个性签名
	public String gender;//性别
	public String email;
	public String birth;
	public String phoneNum;//手机号
	//增加
	public String targetRemark;
	public String roomName;
	
	public String text;//文本消息
	public String[] messagesRead;//已读消息
	public String[] messagesUnread;//未读消息
	
	
	public String senderIP;
	public String targetIP;
	
	public Image pic;

	public class Contacts implements Serializable{
		public String nickname;
		public int id;
		public String style;
		public String remark;
		public Image pic;
	}
	
	public Contacts[] contactslist;
	
	public MessageBlobType type;
	public MessageAnswerType answer;
	public long lastTimeSpeak;
	
	//创建长度为counts对象数组
	public void createContactslist(int counts) {
		contactslist = new Contacts[counts];
		int i;
		for(i=0;i<counts;i++) {
			contactslist[i] = new Contacts();
		}
	}
	
	
	
}

