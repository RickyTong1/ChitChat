package Client;

import javax.swing.Box;
import Constants.Constants;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Constants.SocketConstants;
import Property.Property;
import utils.Window;

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
		JButton nope;
		button.add(ojbk);

		if (mode != Constants.MSG_MODE_REFUSE) {
			nope = new JButton("拒绝");
			nope.addActionListener(e -> {
				MessageBlob message = new MessageBlob();

				if (mode == Constants.MSG_MODE_ADDFD)
					message.type = MessageBlobType.ADD_CONTACT_ANSWER;
				else if (mode == Constants.MSG_MODE_FILE)
					message.type = MessageBlobType.SEND_FILE;

				message.answer = MessageAnswerType.NEGATIVE;
				message.senderID = MainWindow.ID;
				message.targetID = senderID;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));
				this.dispose();
			});
			button.add(Box.createHorizontalStrut(6));
			button.add(nope);
		}

		ojbk.addActionListener(e -> {
			if (mode == Constants.MSG_MODE_ADDFD) {
				MessageBlob message = new MessageBlob();

				if (mode == Constants.MSG_MODE_ADDFD)
					message.type = MessageBlobType.ADD_CONTACT_ANSWER;
				else if (mode == Constants.MSG_MODE_FILE)
					message.type = MessageBlobType.SEND_FILE;

				message.answer = MessageAnswerType.POSITIVE;
				message.senderID = MainWindow.ID;
				message.targetID = senderID;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

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
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

				this.dispose();
				return;
			}
			if (mode == Constants.MSG_MODE_FILE) {
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.SEND_FILE;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.FILE_GRN_PORT, MessageBlobOperator.pack(message));
			}
		});

		head.add(Box.createVerticalStrut(10));
		head.add(note);
		head.add(Box.createVerticalStrut(10));
		head.add(button);
		add(head);
		pack();
		setBounds(Window.getMiddleWidth(350), Window.getMiddleHeight(150), 350, 150);
		setVisible(true);

	}

}
