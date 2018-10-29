package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import CComponents.Convasation;
import Constants.*;
import DataBaseOperation.OperateSQLServer;
import Windows.ChatWindow;

public class Message extends Convasation {// 消息 主窗口左边的基本元素

	public Message(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
		// TODO Auto-generated constructor stub
	}

	JLabel timeStick = null;// 送达的時間戳
	String latestSpeak;// 上次送达的話的時間
	JLabel lastSpoke;// 送达的话
	Box all = Box.createHorizontalBox();
	Box elem = Box.createVerticalBox();

	final int L = Constants.MESSAGE_PANEL_WIDTH / 2;// 昵稱與時間戳的間距

	final int LAST_SPOKE_WORD_SIZE = 12;// 上次发言字体大小
	final int TIMESTICK_WORD_SIZE = 10;

	@Override
	public Box create() {

		lastSpoke = new JLabel(spoke);// 設定label
		// 判断并给出时间戳
		timeStick = new JLabel();

		long time = new Date().getTime() - lastTimeSpeak;
		time /= 1000;// 秒
		time /= 60;// 分

		int stick = (int) time;// 存储时间标.

		new Thread() {
			@Override
			public void run() {

				try {
					sleep(20000);
				} catch (InterruptedException e) {}//提升性能			
				switch ((int) stick / 60)// 小时
				{
				case 0: {
					if (stick * 60 < 1)
						latestSpeak = "刚刚";
					else if (stick * 60 > 28 && stick * 60 < 32)
						latestSpeak = "约半小时前";
					else
						latestSpeak = String.valueOf(stick * 60) + "分之前";
				}
					break;
				default: {
					if (stick >= 24) {
						latestSpeak = String.valueOf(stick / 24) + "天之前";
						break;
					} else
						latestSpeak = String.valueOf(stick) + "小时 之前";
				}
					break;
				}
			}
		}.start();

		timeStick.setText(latestSpeak);

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
					isRead = Internet.UNREAD;
					lastSpoke.setForeground(Colors.MESSAGE_READ);
					MainWindow.readMessage(ID, elem);
					if (!hasChatWin) {
						hasChatWin = true;
						chatWindow = new ChatWindow(ID, MainWindow.ID, lastSpoke.getText());
					}
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

	public void refreshMsg(String msg) {
		// TODO 联系人状态刷新方法
		super.isRead = Internet.UNREAD;
		lastSpoke.setText(msg);

	}

}
