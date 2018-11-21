package CComponents;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;

import Client.Contacts;

public class MessageBlobOperator {

	public static byte[] pack(MessageBlob obj) {
		 ByteArrayOutputStream buffers = new ByteArrayOutputStream();  
	        try {  
	            ObjectOutputStream out = new ObjectOutputStream(buffers);  
	            out.writeObject(obj);  
	            out.close();  
	        } catch (Exception e) {  
	            System.out.println("error_MessageOperator_pack()");  
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
	            System.out.println("error_MessageOperator_unpack()");  
	        }  
	        return (MessageBlob)object;  
	}
}
