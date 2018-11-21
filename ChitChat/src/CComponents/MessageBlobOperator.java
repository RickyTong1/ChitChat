package CComponents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageBlobOperator {
	enum GetMessageMember{
		id,onlineState,isRead,nickname
		,remark,style,gender,messagesUnread
		,picture,list,type,answer,lastTimeSpeak
	}

	public static byte[] pack(MessageBlob obj) {
		 ByteArrayOutputStream buffers = new ByteArrayOutputStream();  
	        try {  
	            ObjectOutputStream out = new ObjectOutputStream(buffers);  
	            out.writeObject(buffers);  
	            out.close();  
	        } catch (Exception e) {  
	            System.out.println("error");  
	            return null;  
	        }  
	        return buffers.toByteArray();  
	}
	
	public static MessageBlob unpack(byte[] obj) {
		 Object object = null;  
	        try {  
	            ByteArrayInputStream buffers = new ByteArrayInputStream(obj);  
	            ObjectInputStream in = new ObjectInputStream(buffers);  
	            object = in.readObject();  
	            in.close();  
	        } catch (Exception e) {  
	            System.out.println("error");  
	        }  
	        return (MessageBlob)object;  
	}
	
	public static Object get(MessageBlob obj,byte[] objByte,GetMessageMember member){
		//Throw
		//Anotation
		if(objByte != null && obj == null)
			obj = unpack(objByte);
		switch(member) {
		case id:
			return obj.id;
		case onlineState:
			return obj.onlineState;
		case isRead:
			return obj.isRead;
		case nickname:
			return obj.nickname;
		case remark:
			return obj.remark;
		case style:
			return obj.style;
		case gender:
			return obj.gender;
		case messagesUnread:
			return obj.messagesUnread;
		case picture:
			return obj.pic;
		case list:
			return obj.contactList;
		case type:
			return obj.type;
		case answer:
			return obj.answer;
		case lastTimeSpeak:
			return obj.lastTimeSpeak;
		}
		return null;	
	}
	

}
