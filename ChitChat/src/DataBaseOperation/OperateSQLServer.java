package DataBaseOperation;
import java.sql.*;

public class OperateSQLServer {
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	//连接数据库
	public void connectToDatabase() {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动
//	    String dbURL = "jdbc:sqlserver://119.23.36.69:1433; DatabaseName=test";  //连接服务器和数据库test
		String dbURL = "jdbc:sqlserver://192.168.43.37:1433; DatabaseName=ChitChat";  //连接服务器和数据库test
	    String userName = "sa";  //默认用户名
//	    String userPwd = "cc44..";  //密码	
	    String userPwd = "123";  //密码	
	    try {
		     Class.forName(driverName);
		     connection = DriverManager.getConnection(dbURL, userName, userPwd);
		     System.out.println("Connection Successful!");  //如果连接成功 控制台输出Connection Successful!
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	//关闭数据库
	public void closeDatabase() {
		try {
			connection.close();
			System.out.println("Close Connection Successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void DatabaseOperation(int statement, int userID, int contactsID, String IP){}	
	//创建userID用户和contactsID用户之间的聊天记录表
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
			System.out.println("该聊天记录表已经存在!");
		}
		return ConversationTable;
	}
	//删除userID用户和contactsID用户之间的聊天记录表
	public void deleteConversationTable(int userID, int contactsID){
		String ConversationTable = "C"+String.valueOf(userID)+"C"+String.valueOf(contactsID);
		try {
			statement = connection.createStatement();
			String sql = "drop table "+ConversationTable;
			statement.execute(sql);
			System.out.println("deleteConversationTable Successful!");
		} catch (SQLException e) {
			System.out.println("该聊天记录表已经存在!");
		}
	}
	//创建userID用户的好友列表
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
			System.out.println("该好友列表已经存在!");			
		}
		return ContactsTable;
	}
	//添加给userID用户添加新的好友contactsID
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
	//删除userID用户的好友contactsID
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
	//注册用户
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
	//更新个人资料
	public void updateUserImformation(String nickName, String sex, String birthday, String mailBox, String personalSignature) {	}
	//更新登陆状态并通知列表里所有在线的好友,如果是上线则将登陆IP以及userID写到在线数据库中
	public void updateLoggingStatus(int Status, String userID, String IP) {}
	//更新聊天信息
	public void saveConversationData(){}
	//把在线用户的IP和userID写入在线数据库onlineUsers
	public void addUserToOnlineUsers(String IP, int userID) {
		try {
			statement = connection.createStatement();
			String sql = "select * from onlineUsers where IP=\'"+IP+"\' and userID="+userID;
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				System.out.println("该在线用户已存在于在线数据库中!");
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
	//把离线用户的IP和userID写入在线数据库onlineUsers
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
				System.out.println("该在线用户不存在于在线数据库中!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//根据在线ID获取数据库中对应的IP
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
				System.out.println("改用户不在线");
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//通过userID从userInformation表中获取对应的nickname
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
				System.out.println("该用户不存在");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//通过查找userID的好友列表来查询contactsID的remark
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
				System.out.println("好友不存在!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//通过查找userID获取好友列表结果集
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
	//通过查找userID获取对应的个人资料
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
//		oss.regesterUserInformation("nick", "123", "男", "9-9", "1233@qq.com", "17768689898", "你好");
//		oss.getContactsIP(29);
//		oss.getContactsNickname(userID, contactsID)
//		oss.createContactsTable(1);
//		oss.createContactsTable(1);
//		oss.addNewContacts(33, 32, "小米");
//		oss.deleteContacts(123, 456);
//		oss.getPersonalInformation(36);
//		oss.addUserToOnlineUsers("123.2.2.2", 34);
//		oss.deleteUserToOnlineUsers(30);

		oss.closeDatabase();
	}
	
}

