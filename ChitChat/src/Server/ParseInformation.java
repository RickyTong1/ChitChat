package Server;

public class ParseInformation {
	int statement;
	String userID;
	String time;
	String information;
	public ParseInformation(byte[] information) {
		int maxSplit = 4;
		String sourceStr = new String(information);
		String[] sourceStrArray = sourceStr.split("#",maxSplit);
		statement = Integer.parseInt(sourceStrArray[0]);
		userID = sourceStrArray[1];
		time = sourceStrArray[2];
		this.information = sourceStrArray[3];
		switch(statement) {
		case 1: break;
		case 2: break;
		case 3: break;
		default: break;
		}
	}
	//消息中专服务端然后发送到客户端
	public void messageToClient(String information) {}
	//向服务端发送登录消息
	public void loginToServer(String information) {}
	//向服务端发送
}
