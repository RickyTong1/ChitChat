package Client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import java.awt.Dimension;
import Property.Property;
import Constants.*;

import Windows.ChatWindow;
import Windows.FriendWindow;
import CComponents.*;

public class Contacts extends Convasation {// ��ϵ�� �������Ҳ�� Ԫ��

	Box thisBx = null;

	public Contacts(int id, int online, long time, int isread, String spoke, String nick, String remark, String style,
			String gender) {
		super(id, online, time, isread, spoke, nick, remark, style, gender);
	}

	
	int isOnline;
	Icon onlineImage;
	public JLabel onlineState;

	@Override
	public Box create() {

		if(thisBx != null)
			return thisBx;
		thisBx = Box.createHorizontalBox();
		/*
		 * ���Ͻ����MainWindow.addNewContacts()
		 * ��thisBx�ظ�װ�ص�����.
		 * */
		
		
		Box labelBx = Box.createVerticalBox();
		/*
		 * ��������״̬ͼ��
		 * 
		 */
		switch (super.onlineState) {
		case Internet.ONLINE: {
			isOnline = Internet.ONLINE;
			setIcon(Property.IMAGE_ONLINE_URL);
			break;
		}
		case Internet.OFFLINE: {
			isOnline = Internet.OFFLINE;
			setIcon(Property.IMAGE_OFFLINE_URL);
			break;
		}
		}

		// ��������
		styleWord.setFont(Fonts.CONTACTS_STYLEWORD);// ����

		// labelbxװ����
		labelBx.add(super.nicknameLabel);
		labelBx.add(Box.createVerticalStrut(5));
		labelBx.add(super.styleWord);
		labelBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH, Constants.CONTACTS_PANEL_HEIGHT));

		// �ܳ�box
		thisBx.add(labelBx);
		thisBx.add(Box.createHorizontalStrut(5));
		thisBx.add(onlineState);
		thisBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH, Constants.CONTACTS_PANEL_HEIGHT));
		thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));

		// ���ü�������popupmenu
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("ɾ������ϵ��");
		JMenuItem setRemark = new JMenuItem("���ñ�ע");
		JMenuItem showProfile = new JMenuItem("������Ϣ");
		operator.add(deleteThis);
		operator.add(setRemark);
		operator.add(showProfile);

		deleteThis.addActionListener(e -> {
			int choose = JOptionPane.showConfirmDialog(null, "ȷ��ɾ������ϵ��?\n���������ܳ���.", "��ʾ", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			// System.out.println(choose);
			if (choose == 0/* ѡ�� */) {
				MainWindow.deleteContacts(ID, MainWindow.ID, thisBx);// ����ɾ��ָ��
				if (hasChatWin) {
					chatWindow.dispose();
					chatWindow = null;
					hasChatWin = false;
				}
			}
		});
		showProfile.addActionListener(e -> {
			new FriendWindow(MainWindow.ID, this.ID);
		});
		setRemark.addActionListener(e -> {
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.FRIEND_PROFILE_QUEST;
			message.senderIP = Property.NATIVE_IP;
			message.senderID = MainWindow.ID;
			message.targetID = super.ID;
			
			new SendMessage(
					Property.SERVER_IP
					,SocketConstants.GENERAL_PORT
					,MessageBlobOperator.pack(message));
			
		});

		thisBx.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.isMetaDown()) {
					operator.show(e.getComponent(), e.getX(), e.getY());
				}
				if (e.getClickCount() == 2) {
					if (!hasChatWin) {
						hasChatWin = true;
						chatWindow = new ChatWindow(ID, MainWindow.ID, "");
						
					}
					chatWindow.requestFocus();//����
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_CHOSED));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));

			}

		});
		return thisBx;

	}

	public void setIcon(String url) {
		try {
			this.onlineImage = new ImageIcon(ImageIO.read(new File(url)));
			this.onlineState = new JLabel(onlineImage);
			this.onlineState.setPreferredSize(
					new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH, Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("�ļ�������.");
		}
	}

	public void setOnlineState(int onlineState) {
		switch (onlineState) {
		case Internet.ONLINE: {
			setIcon(Property.IMAGE_OFFLINE_URL);
		}
			break;
		case Internet.OFFLINE: {
			setIcon(Property.IMAGE_OFFLINE_URL);
		}
		}
	}

}
