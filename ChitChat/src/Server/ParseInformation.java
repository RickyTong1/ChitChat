package Server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.SynchronousQueue;

import javax.sound.sampled.Port;
import DataBaseOperation.OperateSQLServer;

public class ParseInformation {
	int statement;
	String Nickname;
	String userID;
	String time;
	String information;
	InetAddress address;
	OperateSQLServer operateSQLServer;
	public ParseInformation(int statement, InetAddress address, byte[] information) throws UnknownHostException {
		//final int PORT = 23333;
		this.statement = statement;
		this.address = address;
		operateSQLServer = new OperateSQLServer();
		int maxSplit = 3;
		String sourceStr = new String(information).trim();
		String[] sourceStrArray = sourceStr.split("#",maxSplit);
		userID = sourceStrArray[0];
		time = sourceStrArray[1];
		this.information = sourceStrArray[2];
		System.out.println(this.information);
		switch(statement) {
		case 1: textToServer(this.information); break;
		case 2: LogIn(this.information); break;
		case 3: logOut(); break;
//		case 4: isOnlineToServer(this.information); break;
//		case 5: textToUser(userID); break;
//		case 6: isOnlineToUser(this.information); break;
//		case 7: resetProfileToServer(this.information); break;
//		case 8: resetPswdToServer(this.information); break;
//		case 9: deleteContact(this.information); break;
//		case 10: addContact(this.information); break;
		default: break;
		}
	}
	//1+������ID+ʱ��+Ŀ��ID+�����ı���Ϣ
	public void textToServer(String information) {
		int maxSplit = 2;
		String[] sourceStrArray = information.split("#",maxSplit);
		int targetID = Integer.parseInt(sourceStrArray[0]);
		information = sourceStrArray[1];
		information = "5#"+userID+"#"+time+"#"+information;
		operateSQLServer.connectToDatabase();
		String IP = operateSQLServer.getContactsIP(targetID);
//		System.out.println(IP);
//		System.out.println(information);
//		IP = "127.0.0.1";
		try {
			if(IP != null) {
				SendThread sendThread = new SendThread(IP, 23333, information.getBytes());
				new Thread(sendThread).start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		operateSQLServer.closeDatabase();
	}
	//2+������ID+ʱ��+IP
	public void LogIn(String information) throws UnknownHostException {
		operateSQLServer.connectToDatabase();
		int userID = Integer.parseInt(this.userID);
		operateSQLServer.addUserToOnlineUsers(information, userID);
		operateSQLServer.closeDatabase();
	}
	//3+������ID+ʱ��
	public void logOut() {
		operateSQLServer.connectToDatabase();
		int userID = Integer.parseInt(this.userID);
		operateSQLServer.deleteUserToOnlineUsers(userID);
		operateSQLServer.closeDatabase();
	}
//	//4+������ID+ʱ��+����״̬������
//	public void isOnlineToServer(String information) {}
//	//5+������ID+ʱ��+�����ı���Ϣ
//	public void textToUser(String userID) {
//		Nickname = operateSQLServer.getContactsNickname(32, Integer.parseInt(userID));
//	}
//	//6+������ID+ʱ��+����״̬�����
//	public void isOnlineToUser(String information) {}
//	//7+������ID+ʱ��+���ĸ���������Ϣ
//	public void resetProfileToServer(String information) {
//		int maxSplit = 5;
//		String[] sourceStrArray = information.split("#",maxSplit);
//		String nickName = sourceStrArray[0]; 
//		String sex = sourceStrArray[1];
//		String birthday = sourceStrArray[2];
//		String mailBox = sourceStrArray[3];
//		String personalSignature = sourceStrArray[4];
//		operateSQLServer.connectToDatabase();
//		operateSQLServer.updateUserImformation(Integer.parseInt(this.userID), nickName, sex, birthday, mailBox, personalSignature);
//		operateSQLServer.closeDatabase();
//	}
//	//8+������ID+ʱ��+�޸ĸ�������
//	public void resetPswdToServer(String information) {
//		String password = information;
//		operateSQLServer.connectToDatabase();
//		operateSQLServer.updateUserPassword(Integer.parseInt(this.userID), password);
//		operateSQLServer.closeDatabase();
//	}
//	//9+������ID+ʱ��+ɾ����������contactsID
//	public void deleteContact(String information) {
//		int contactsID = Integer.parseInt(information);
//		int userID = Integer.parseInt(this.userID);
//		operateSQLServer.connectToDatabase();
//		operateSQLServer.deleteContacts(userID, contactsID);
//		operateSQLServer.closeDatabase();
//	}
//	//10+������ID+ʱ��+��Ӻ�������contactsID
//	public void addContact(String information) {
//		int maxSplit = 2;
//		String[] sourceStrArray = information.split("#",maxSplit);
//		int contactsID = Integer.parseInt(sourceStrArray[0]);
//		String remark = sourceStrArray[1];
//		int userID = Integer.parseInt(this.userID);
//		operateSQLServer.connectToDatabase();
//		operateSQLServer.addNewContacts(userID, contactsID, remark);
//		operateSQLServer.closeDatabase();
//	}
	
}
