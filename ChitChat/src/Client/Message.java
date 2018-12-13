package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import CComponents.Convasation;
import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Constants.*;
import Property.Property;
import Windows.ChatWindow;

public class Message extends Convasation {// 消息 主窗口左边的基本元素

	public Message(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
		// TODO Auto-generated constructor stub
	}

	int mode = 0;// 1 加好友 ;2文件;3拒絕提示

	long spokeTime = super.lastTimeSpeak;
	JLabel timeStick = null;// 送达的時間戳
	String latestSpeak;// 上次送达的話的時間
	JLabel lastSpoke;// 送达的话
	Box all = Box.createHorizontalBox();
	Box elem = null;

	final int L = Constants.MESSAGE_PANEL_WIDTH / 2;// 昵稱與時間戳的間距

	final int LAST_SPOKE_WORD_SIZE = 12;// 上次发言字体大小
	final int TIMESTICK_WORD_SIZE = 10;

	@Override
	public Box create() {

		if (elem != null)
			return elem;
		Box elem = Box.createVerticalBox();
		/*
		 * 以上解决了MainWindow.addNewMessage() 中thisBx重复装载的问题.
		 */

		lastSpoke = new JLabel(spoke);// 設定label
		// 判断并给出时间戳
		timeStick = new JLabel();

		// TODO 时间戳测试
		setTime(spokeTime);
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(60000);// 一分钟一次
					} catch (InterruptedException e) {
					}
					// spokeTime -= 60000*60*24;//时间加速大法
					setTime(spokeTime);
				}
			}
		}.start();

		// 设置字体
		lastSpoke.setFont(Fonts.MESSAGE_LASTSPEAK);
		timeStick.setFont(Fonts.MESSAGE_TIMESTICK);// 字体

		// 根据是否已读设置字体颜色
		switch (isRead) {
		case Internet.READ:
			lastSpoke.setForeground(Colors.MESSAGE_READ);
			break;
		case Internet.UNREAD:
			lastSpoke.setForeground(Colors.MESSAGE_UNREAD);
		}

		// 设置标签大小
		nicknameLabel.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH - L / 2, TIMESTICK_WORD_SIZE));
		timeStick.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH - L / 2, TIMESTICK_WORD_SIZE));
		lastSpoke.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, LAST_SPOKE_WORD_SIZE));

		// 设置对齐
		nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);// 设置左对齐
		timeStick.setAlignmentX(Component.RIGHT_ALIGNMENT);// 时间靠右对齐
		lastSpoke.setAlignmentX(Component.LEFT_ALIGNMENT);

		/*
		 * all装第一行的文字 elem为总成的大盒子.
		 */
		all.setAlignmentX(Component.LEFT_ALIGNMENT);
		all.add(nicknameLabel);
		all.add(Box.createHorizontalStrut(L));
		all.add(timeStick);
		all.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, Constants.MESSAGE_PANEL_HEIGHT / 2));// all盒子设置大小

		elem.add(all);
		elem.add(lastSpoke);
		elem.setAlignmentX(Component.LEFT_ALIGNMENT);
		elem.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, Constants.MESSAGE_PANEL_HEIGHT));
		elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		elem.validate();

		/*
		 * 为elem添加监听器以及popupmenu.
		 */
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("清除这条");
		JMenuItem readThis = new JMenuItem("设为已读");

		operator.add(deleteThis);
		operator.add(readThis);
		elem.add(operator);

		deleteThis.addActionListener(e -> {
			MainWindow.deleteMessage(ID, elem);
			hasMessage = false;

		});
		readThis.addActionListener(e -> {
			if (mode == Constants.MSG_MODE_ADDFD&&isRead == Internet.UNREAD) {
				MessageBlob message = new MessageBlob();

				if (mode == Constants.MSG_MODE_ADDFD)
					message.type = MessageBlobType.ADD_CONTACT_ANSWER;
				else if (mode == Constants.MSG_MODE_FILE)
					message.type = MessageBlobType.SEND_FILE;

				message.answer = MessageAnswerType.NEGATIVE;
				message.senderID = MainWindow.ID;
				message.targetID = ID;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

			}

			isRead = Internet.READ;
			lastSpoke.setForeground(Colors.MESSAGE_READ);
			MainWindow.readMessage(ID, elem);
		});

		elem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isMetaDown()) {
					operator.show(e.getComponent(), e.getX(), e.getY());
				}
				if (e.getClickCount() == 2) {
					isRead = Internet.READ;
					lastSpoke.setForeground(Colors.MESSAGE_READ);
					MainWindow.readMessage(ID, elem);
					if (mode == 1 || mode == 2 || mode == 3) {
						new ServerNoteView(ID, spoke, mode);
						return;
					}
					if (!hasChatWin) {
						hasChatWin = true;
						chatWindow = new ChatWindow(ID, MainWindow.ID);
					} else
						chatWindow.requestFocus();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_CHOSED));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
			}

		});
		return elem;
	}

	public void setTime(long rawTime) {
		long time = new Date().getTime() - rawTime;
		time /= 1000;// 秒
		time /= 60;// 分
		int sendTime = (int) time;
		if (sendTime < 2)
			latestSpeak = "刚刚";
		else if (sendTime > 28 && sendTime < 32)
			latestSpeak = "约半小时前";
		else if (sendTime <= 58)
			latestSpeak = sendTime + "分之前";
		else if (sendTime > 58 && sendTime < 1440/* 一天 */) {
			if (sendTime / 60 == 0)
				latestSpeak = "1小时之前";
			else
				latestSpeak = sendTime / 60 + "小时之前";
		}

		else if (sendTime / 60 >= 24) {
			int day = (sendTime / 60) / 24;
			if (day >= 7) {
				int week = day / 7;
				if (week >= 4)
					latestSpeak = week / 4 + "月之前";
				else
					latestSpeak = week + "周之前";
			} else
				latestSpeak = day + "天之前";
		}
		timeStick.setText(latestSpeak);
	}

	public void refreshMsg(String msg) {
		// TODO 联系人状态刷新方法
		super.isRead = Internet.UNREAD;
		lastSpoke.setText(msg);
		super.spoke = msg;
		elem.requestFocus();
	}

}
