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

public class Message extends Convasation {// ��Ϣ ��������ߵĻ���Ԫ��

	public Message(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
		// TODO Auto-generated constructor stub
	}

	JLabel timeStick = null;// �ʹ�ĕr�g��
	String latestSpeak;// �ϴ��ʹ��Ԓ�ĕr�g
	JLabel lastSpoke;// �ʹ�Ļ�
	Box all = Box.createHorizontalBox();
	Box elem = null;

	final int L = Constants.MESSAGE_PANEL_WIDTH / 2;// �ǷQ�c�r�g�����g��

	final int LAST_SPOKE_WORD_SIZE = 12;// �ϴη��������С
	final int TIMESTICK_WORD_SIZE = 10;

	@Override
	public Box create() {

		if (elem != null)
			return elem;
		Box elem = Box.createVerticalBox();
		/*
		 * ���Ͻ����MainWindow.addNewMessage() ��thisBx�ظ�װ�ص�����.
		 */

		lastSpoke = new JLabel(spoke);// �O��label
		// �жϲ�����ʱ���
		timeStick = new JLabel();

		setTime(super.lastTimeSpeak);
		// ��������
		lastSpoke.setFont(Fonts.MESSAGE_LASTSPEAK);
		timeStick.setFont(Fonts.MESSAGE_TIMESTICK);// ����

		// �����Ƿ��Ѷ�����������ɫ
		switch (isRead) {
		case Internet.READ:
			lastSpoke.setForeground(Colors.MESSAGE_READ);
			break;
		case Internet.UNREAD:
			lastSpoke.setForeground(Colors.MESSAGE_UNREAD);
		}

		// ���ñ�ǩ��С
		nicknameLabel.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH - L / 2, TIMESTICK_WORD_SIZE));
		timeStick.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH - L / 2, TIMESTICK_WORD_SIZE));
		lastSpoke.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, LAST_SPOKE_WORD_SIZE));

		// ���ö���
		nicknameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);// ���������
		timeStick.setAlignmentX(Component.RIGHT_ALIGNMENT);// ʱ�俿�Ҷ���
		lastSpoke.setAlignmentX(Component.LEFT_ALIGNMENT);

		/*
		 * allװ��һ�е����� elemΪ�ܳɵĴ����.
		 */
		all.setAlignmentX(Component.LEFT_ALIGNMENT);
		all.add(nicknameLabel);
		all.add(Box.createHorizontalStrut(L));
		all.add(timeStick);
		all.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, Constants.MESSAGE_PANEL_HEIGHT / 2));// all�������ô�С

		elem.add(all);
		elem.add(lastSpoke);
		elem.setAlignmentX(Component.LEFT_ALIGNMENT);
		elem.setPreferredSize(new Dimension(Constants.MESSAGE_PANEL_WIDTH, Constants.MESSAGE_PANEL_HEIGHT));
		elem.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		elem.validate();

		/*
		 * Ϊelem���Ӽ������Լ�popupmenu.
		 */
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("�������");
		JMenuItem readThis = new JMenuItem("��Ϊ�Ѷ�");

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
					isRead = Internet.READ;
					lastSpoke.setForeground(Colors.MESSAGE_READ);
					MainWindow.readMessage(ID, elem);
					if (!hasChatWin) {
						hasChatWin = true;
						chatWindow = new ChatWindow(ID, MainWindow.ID, lastSpoke.getText());
					}
					else
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
		time /= 1000;// ��
		time /= 60;// ��
		int sendTime = (int) time;
		if (sendTime < 2)
			latestSpeak = "�ո�";
		else if (sendTime > 28 && sendTime < 32)
			latestSpeak = "Լ��Сʱǰ";
		else if (sendTime <= 58)
			latestSpeak = sendTime + "��֮ǰ";
		else if (sendTime > 58)
			latestSpeak = sendTime / 60 + "Сʱ֮ǰ";
		else if (sendTime / 60 >= 24) {
			if (((sendTime / 60) / 24) / 7 >= 1) {
				int week = ((sendTime / 60) / 24) / 7;
				if (week >= 4)
					latestSpeak = week / 4 + "��֮ǰ";
				latestSpeak = ((sendTime / 60) / 24) / 7 + "��֮ǰ";
			}
			latestSpeak = (sendTime / 60) / 24 + "��֮ǰ";
		}
		timeStick.setText(latestSpeak);
	}

	public void refreshMsg(String msg) {
		// TODO ��ϵ��״̬ˢ�·���
		super.isRead = Internet.UNREAD;
		lastSpoke.setText(msg);
		super.spoke = msg;
		elem.requestFocus();
	}

}