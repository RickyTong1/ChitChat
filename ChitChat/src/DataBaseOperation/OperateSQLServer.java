package DataBaseOperation;
import java.sql.*;

public class OperateSQLServer {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	//�������ݿ�
	public void connectToDatabase() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //����JDBC����
//	    String dbURL = "jdbc:sqlserver://119.23.36.69:1433; DatabaseName=test";  //���ӷ����������ݿ�test
		String dbURL = "jdbc:sqlserver://192.168.43.37:1433; DatabaseName=ChitChat";  //���ӷ����������ݿ�test
	    String userName = "sa";  //Ĭ���û���
//	    String userPwd = "cc44..";  //����	
	    String userPwd = "123";  //����	
	    try {
		     Class.forName(driverName);
		     connection = DriverManager.getConnection(dbURL, userName, userPwd);
		     System.out.println("Connection Successful!");  //������ӳɹ� ����̨���Connection Successful!
	    } catch (Exception e) {
	    	e.printStackTrace();
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
		String ContactsTable = "C"+String.valueOf(userID);
		String ConversationTable = "C"+String.valueOf(userID)+"C"+String.valueOf(contactsID);
		try {
			String nickname = getContactsNickname(contactsID);
			statement = connection.createStatement();
			String sql = "insert into "+ContactsTable+"(contactsID, ConversationTable, nickname, remark) values (\'"+contactsID+"\',\'"+ConversationTable+"\',\'"+nickname+"\')"+"\',\'"+remark+"\')";
			statement.executeUpdate(sql);
			System.out.println("addNewContacts Successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//ɾ��userID�û��ĺ���contactsID
	public void deleteContacts(int userID, int contactsID) {
		deleteConversationTable(userID, contactsID);
		String ContactsTable = "C"+String.valueOf(userID);
		try {
			statement = connection.createStatement();
			String sql = "delete from "+ContactsTable+" where contactsID = \'"+contactsID+"\'";
			statement.executeUpdate(sql);
			System.out.println("addNewContacts Successful!");
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
	public void updateUserImformation(String nickName, String sex, String birthday, String mailBox, String personalSignature) {	}
	//���µ�½״̬��֪ͨ�б����������ߵĺ���,����������򽫵�½IP�Լ�userIDд���������ݿ���
	public void updateLoggingStatus(int Status, String userID, String IP) {}
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
				System.out.println("insert Successfully!");
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�������û���IP��userIDд���������ݿ�onlineUsers
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
	//��������ID��ȡ���ݿ��ж�Ӧ��IP
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
			String sql = "select * from C"+userID+"where contactsID="+contactsID;
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
	public static void main(String args[]) {
		OperateSQLServer oss = new OperateSQLServer();
		oss.connectToDatabase();
//		oss.regesterUserInformation("nick", "123", "��", "9-9", "1233@qq.com", "17768689898", "���");
//		oss.getContactsIP(29);
//		oss.getContactsNickname(userID, contactsID)
//		oss.createContactsTable(1);
//		oss.createContactsTable(1);
//		oss.addNewContacts(33, 32, "С��");
//		oss.deleteContacts(123, 456);
//		oss.getPersonalInformation(36);
//		oss.addUserToOnlineUsers("123.2.2.2", 34);
//		oss.deleteUserToOnlineUsers(30);

		oss.closeDatabase();
	}
	
}

