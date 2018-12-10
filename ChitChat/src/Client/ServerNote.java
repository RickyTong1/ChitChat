package Client;

import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import CComponents.MessageBlob;
import CComponents.MessageBlobType;
import Constants.Internet;
import Windows.ChatWindow;

public class ServerNote extends Message{

	@Deprecated
	public ServerNote(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
		// TODO Auto-generated constructor stub
	}
	public ServerNote(int senderID,String Note) {
		super(senderID, 0, new Date().getTime(), Internet.UNREAD, Note, "ϵͳ֪ͨ", "ϵͳ֪ͨ", "", "ϵͳ֪ͨ");
		MainWindow.addNewMessage(this);
	}
}
