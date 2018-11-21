package CComponents;

import java.awt.Image;
import java.io.Serializable;

import Client.Contacts;

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
	int id;
	int onlineState;
	int isRead;//是否已读
	
	String nickname;
	String remark;//备注
	String style;//个性签名
	String gender;//性别
	String[] messagesUnread;//未读消息
	
	Image pic;

	Contacts[] contactList;//好友列表
	
	MessageBlobType type;
	MessageBlobType answer;
	long lastTimeSpeak;
}
