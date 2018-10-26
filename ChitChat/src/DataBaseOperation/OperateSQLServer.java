package DataBaseOperation;
import java.net.UnknownHostException;
import java.security.interfaces.RSAKey;
import java.sql.*;

import Server.SendThread;

public class OperateSQLServer {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	//�������ݿ�
	public void connectToDatabase() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //����JDBC����
//	    String dbURL = "jdbc:sqlserver://119.23.36.69:1433; DatabaseName=test";  //���ӷ����������ݿ�test
		String dbURL = "jdbc:sqlserver://192.168.43.37:1433; DatabaseName=ChitChat";  //���ӷ����������ݿ�test
		//String dbURL = "jdbc:sqlserver://172.18.175.146:1433; DatabaseName=ChitChat";  //���ӷ����������ݿ�test
	    String userName = "sa";  //Ĭ���û���
//	    String userPwd = "cc44..";  //����	
	    String userPwd = "123";  //����	
	    try {
		     Class.forName(driverName);
		     connection = DriverManager.getConnection(dbURL, userName, userPwd);
		     System.out.println("Connection Successful!");  //������ӳɹ� ����̨���Connection Successful!
	    } catch (Exception e) {
	    	System.out.println("ͨ���˿� 1433 ���ӵ����� 192.168.43.37 �� TCP/IP ����ʧ�ܡ�����:��connect timed out��");
	    }
	}
	//�ر����ݿ�
	public void closeDatabase() {
		try {
			connection.close();
			System.out.println("Close Connection Successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void DatabaseOperation(int statement, int userID, int contactsID, String IP){}	
	//����userID�û���contactsID�û�֮��������¼��
	public String createConversationTable(int userID, int contactsID){
		String ConversationTable = "C"+String.valueOf(userID)+"C"+String.valueOf(contactsID);
		try {
			statement = connection.createStatement();
			String sql = "create table "+ConversationTable+"("
		             + "timeStick varchar(50) NOT NULL primary key," 
		    		 +"sender  varchar(50) unique," 
		    		 +"detail  varchar(50)," 
		    		 +"isRead   varchar(50))";
			statement.execute(sql);
			System.out.println("createConversationTable Successful!");
		} catch (SQLException e) {
			System.out.println("�������¼���Ѿ�����!");
		}
		return ConversationTable;
	}
	//ɾ��userID�û���contactsID�û�֮��������¼��
	public void deleteConversationTable(int userID, int contactsID){
		String ConversationTable = "C"+String.valueOf(userID)+"C"+String.valueOf(contactsID);
		try {
			statement = connection.createStatement();
			String sql = "drop table "+ConversationTable;
			statement.execute(sql);
			System.out.println("deleteConversationTable Successful!");
		} catch (SQLException e) {
			System.out.println("�������¼���Ѿ�����!");
		}
	}
	//����userID�û��ĺ����б�
	public String createContactsTable(int userID) {
		String ContactsTable = "C"+String.valueOf(userID);
		try {
			statement = connection.createStatement();
			String sql = "create table "+ContactsTable+"("
		             + "contactsID int NOT NULL primary key," 
		    		 +"ConversationTable  varchar(50) unique," 
		             +"nickname varchar(50),"
		    		 +"remark  varchar(50))";    		 
			statement.execute(sql);
			System.out.println("createContactsTable Successful!");
		} catch (SQLException e) {
			System.out.println("�ú����б��Ѿ�����!");			
		}
		return ContactsTable;
	}
	//��Ӹ�userID�û�����µĺ���contactsID
	public void addNewContacts(int userID, int contactsID, String remark) {
		createConversationTable(userID, contactsID);
		String ContactsTableUserID = "C"+String.valueOf(userID);
		String ContactsTableContactsID = "C"+String.valueOf(contactsID);
		String ConversationTable = "C"+String.valueOf(userID)+"C"+String.valueOf(contactsID);
		try {
			String nickname = getContactsNickname(contactsID);
			if(getContactsNickname(userID, contactsID) == null) {
				statement = connection.createStatement();
				String sql = "insert into "+ContactsTableUserID+"(contactsID, ConversationTable, nickname, remark) values (\'"+contactsID+"\',\'"+ConversationTable+"\',\'"+nickname+"\',\'"+remark+"\')";
				statement.executeUpdate(sql);
				if(getContactsNickname(contactsID, userID) == null) {
					nickname = getContactsNickname(userID);
					sql = "insert into "+ContactsTableContactsID+"(contactsID, ConversationTable, nickname, remark) values (\'"+userID+"\',\'"+ConversationTable+"\',\'"+nickname+"\',\'"+nickname+"\')";
					statement.executeUpdate(sql);
				}		
			}		
			System.out.println("addNewContacts Successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//ɾ��userID�û��ĺ���contactsID
	public void deleteContacts(int userID, int contactsID) {
		deleteConversationTable(userID, contactsID);
		String ContactsTableUserID = "C"+String.valueOf(userID);
		String ContactsTableContactsID = "C"+String.valueOf(contactsID);
		try {
			statement = connection.createStatement();
			String sql = "delete from "+ContactsTableUserID+" where contactsID = \'"+contactsID+"\'";
			statement.executeUpdate(sql);
			sql = "delete from "+ContactsTableContactsID+" where contactsID = \'"+userID+"\'";
			statement.executeUpdate(sql);
			System.out.println("deleteNewContacts Successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//ע���û�
	public void regesterUserInformation(String nickName, String password, String sex, String birthday, String mailBox, String phoneNumber, String personalSignature) {
		try {
			statement = connection.createStatement();
			String sql = "insert into userInformation values(\'"+nickName+"\',\'"+password+"\',null,\'"+mailBox+"\',0,\'"+phoneNumber+"\',\'"+sex+"\',\'"+birthday+"\',\'"+personalSignature+"\')";
//			String sql = "delete from userInformation";
			statement.executeUpdate(sql);
			sql = "select * from userInformation where mailbox=\'"+mailBox+"\'";
			resultSet = statement.executeQuery(sql);
			resultSet.next();
			int userID = resultSet.getInt(1);
			String ContactsTable = createContactsTable(userID);
			sql = "update userInformation set friendList=\'"+ContactsTable+"\' where userID="+userID;
			statement.executeUpdate(sql);
			System.out.println("regesterUserInformation Successful!");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//���¸�������
	public void updateUserImformation(int userID, String nickName, String sex, String birthday, String mailBox, String personalSignature) {
		try {
			statement = connection.createStatement();
			String sql = "update userInformation set nickName=\'"+nickName+"\',sex=\'"+sex+"\',birthday=\'"+birthday+"\',mailBox=\'"+mailBox+"\',personalSignature=\'"+personalSignature+"\' where userID="+userID;
			statement.executeUpdate(sql);
			System.out.println("updateUserImformation Successful!");
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	//���¸�������
	public void updateUserPassword(int userID, String password) {
		try {
			statement = connection.createStatement();
			String sql = "update userInformation set password=\'"+password+"\' where userID="+userID;
			statement.executeUpdate(sql);
			System.out.println("updateUserImformation Successful!");
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	//���º��ѱ�ע
	public void updateFriendRemark(int userID, Integer contactsID, String remark) {
		try {
			statement = connection.createStatement();
			String sql = "update C"+userID+" set remark=\'"+remark+"\' where contactsID="+contactsID;
			statement.executeUpdate(sql);
			System.out.println("updateFriendRemark Successful!");
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	//���µ�½״̬��֪ͨ�б����������ߵĺ���
	public void updateLoggingStatus(int Status, int userID) {
		if(Status == 1) {
			try {
				statement = connection.createStatement();
				String sql = "update userInformation set loggingStatus = 1 where userID="+userID;
				statement.executeUpdate(sql);
				System.out.println("updateLoggingStatus Successful!");
				sql = "select * from C"+userID;
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					int id = rs.getInt(1);
					sql = "select * from userInformation where userID="+id;
					statement = connection.createStatement();
					ResultSet info = statement.executeQuery(sql);
					if(info.next()) {
						if(info.getInt(6) == 1) {		
							SendThread st = new SendThread(getContactsIP(info.getInt(1)), 23333, ("4#1#"+info.getInt(1)).getBytes());
							new Thread(st).start();
						}
					}	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}	
		}
		else {
			try {
				statement = connection.createStatement();
				String sql = "update userInformation set loggingStatus = 0 where userID="+userID;
				statement.executeUpdate(sql);
				System.out.println("updateLoggingStatus Successful!");
				sql = "select * from C"+userID;
				ResultSet rs = statement.executeQuery(sql);
				while(rs.next()) {
					int id = rs.getInt(1);
					sql = "select * from userInformation where userID="+id;
					statement = connection.createStatement();
					ResultSet info = statement.executeQuery(sql);
					if(info.next()) {
						if(info.getInt(6) == 1) {		
							SendThread st = new SendThread(getContactsIP(info.getInt(1)), 23333, ("4#0#"+info.getInt(1)).getBytes());
							new Thread(st).start();
						}
					}	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}	
		}
	}
	//����������Ϣ
	public void saveConversationData(){}
	//�������û���IP��userIDд���������ݿ�onlineUsers
	public void addUserToOnlineUsers(String IP, int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from onlineUsers where IP=\'"+IP+"\' and userID="+userID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				System.out.println("�������û��Ѵ������������ݿ���!");
			}
			else {
				sql = "insert into onlineUsers values(\'"+IP+"\',"+userID+")";
				statement.executeUpdate(sql);
				System.out.println("insert Successfully!");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//�������û���IP��userID���������ݿ�onlineUsersɾ��
	public void deleteUserToOnlineUsers(int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from onlineUsers where userID="+userID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {				
				sql = "delete from onlineUsers where userID="+userID;
				statement.executeUpdate(sql);
				System.out.println("delete Successfully!");
			}
			else {
				System.out.println("�������û����������������ݿ���!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//��ȡ���ݿ��û��ܱ�Ľ����
	public ResultSet getUserInformation() {
		try {
			statement = connection.createStatement();
			String sql = "select * from userInformation";
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			System.out.println("���ݿ��û��ܱ��ȡʧ��!");
		}
		return null;
	}
	//��ȡ�������ݿ�onlineUsers�Ľ����
	public ResultSet getOnlineUsers() {
		try {
			statement = connection.createStatement();
			String sql = "select * from onlineUsers";
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			System.out.println("���ݿ����߱��ȡʧ��!");
		}
		return null;
	}
	//�������б���ͨ��userID��ȡ��Ӧ��IP
	public String getContactsIP(int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from onlineUsers where userID="+userID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				String IP = resultSet.getString(1);
				return IP;
			}
			else {
				System.out.println("���û�������");
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//ͨ��userID��userInformation���л�ȡ��Ӧ��nickname
	public String getContactsNickname(int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from userInformation where userID="+userID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				String nickName = resultSet.getString(1);
				return nickName;
			}
			else {
				System.out.println("���û�������");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//ͨ������userID�ĺ����б�����ѯcontactsID��remark
	public String getContactsNickname(int userID, int contactsID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from C"+userID+" where contactsID="+contactsID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				String remark = resultSet.getString(4);
				return remark;
			}
			else {
				System.out.println("���Ѳ�����!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//ͨ������userID��ȡ�����б�����
	public ResultSet getFriendList(int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from C"+userID;
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//ͨ������userID��ȡ��Ӧ�ĸ�������
	public ResultSet getPersonalInformation(int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from userInformation where userID="+userID;
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//ͨ������nickname��ȡ��Ӧ��userID
		public int getUserID(String nickname) {
			try {
				statement = connection.createStatement();
				String sql = "select * from userInformation where nickname=\'"+nickname+"\'";
				resultSet = statement.executeQuery(sql);
				resultSet.next();
				return resultSet.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
	//�ж��ǳƵ�Ψһ��
	public Boolean isUniqueNickname(String nickname) {
		try {
			statement = connection.createStatement();
			String sql = "select * from userInformation where nickname=\'"+nickname+"\'";
			resultSet = statement.executeQuery(sql);
			if(resultSet.next())
				return false;
			else
				return true;
		} catch (SQLException e) {
			System.out.println("���ݿ��û��ܱ��ȡʧ��!");
		}
		return false;
	}
//	public static void main(String args[]) {
//		OperateSQLServer oss = new OperateSQLServer();
//		oss.connectToDatabase();
//		//oss.addNewContacts(37, 41, "��");
//		//oss.deleteContacts(37, 41);
//		oss.getUserID("���");
//		oss.closeDatabase();
//	}
}

