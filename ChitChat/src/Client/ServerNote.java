package Client;

import java.util.Date;

import Constants.Internet;

public class ServerNote extends Message{

	@Deprecated
	public ServerNote(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
		// TODO Auto-generated constructor stub
	}
	public ServerNote(String Note) {
		super(0, 0, new Date().getTime(), Internet.UNREAD, Note, "ϵͳ֪ͨ", "ϵͳ֪ͨ", "", "");
		MainWindow.addNewMessage(this);
	}

}
