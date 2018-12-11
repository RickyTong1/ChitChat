package Client;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Constants.SocketConstants;
import Property.Property;

public class ServerNoteView extends JFrame {

	int mode;// 1 加好友;2 文件

	public ServerNoteView(int senderID, String Note, int mode) {
		this.mode = mode;
		Box head = Box.createVerticalBox();
		Box note = Box.createHorizontalBox();
		Box button = Box.createHorizontalBox();
		head.add(new JLabel("系统提示"));
		note.add(new JLabel(Note));
		JButton ojbk = new JButton("好");
		JButton nope = new JButton("拒绝");
		ojbk.addActionListener(e -> {
			if (mode == 1) {
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.ADD_CONTACT_ANSWER;
				message.answer = MessageAnswerType.POSITIVE;
				message.senderID = MainWindow.ID;
				message.targetID = senderID;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.GENERAL_PORT, MessageBlobOperator.pack(message));

				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MessageBlob quest_for_friend_list = new MessageBlob();// 联系人列表初始化
				quest_for_friend_list.type = MessageBlobType.FRIEND_LIST_QUEST;
				quest_for_friend_list.senderIP = Property.NATIVE_IP;
				quest_for_friend_list.senderID = MainWindow.ID;
				new SendMessage(Property.SERVER_IP, SocketConstants.GENERAL_PORT,
						MessageBlobOperator.pack(quest_for_friend_list));
				this.dispose();
				return;
			}
			if(mode == 2) {
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.SEND_FILE;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP
						,SocketConstants.FILE_GRN_PORT
						,MessageBlobOperator.pack(message));
			}
		});
		nope.addActionListener(e -> {
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.ADD_CONTACT_ANSWER;
			message.answer = MessageAnswerType.NEGATIVE;
			message.senderID = MainWindow.ID;
			message.targetID = senderID;
			message.senderIP = Property.NATIVE_IP;
			new SendMessage(Property.SERVER_IP, SocketConstants.GENERAL_PORT, MessageBlobOperator.pack(message));
			this.dispose();
		});
		button.add(ojbk);
		button.add(Box.createHorizontalStrut(6));
		button.add(nope);
		head.add(Box.createVerticalStrut(10));
		head.add(note);
		head.add(Box.createVerticalStrut(10));
		head.add(button);
		add(head);
		pack();
		setVisible(true);

	}

}
