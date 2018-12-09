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

public class Contacts extends Convasation {// 联系人 主窗口右侧的 元素

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
		 * 以上解决了MainWindow.addNewContacts()
		 * 中thisBx重复装载的问题.
		 * */
		
		
		Box labelBx = Box.createVerticalBox();
		/*
		 * 设置在线状态图标
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

		// 设置字体
		styleWord.setFont(Fonts.CONTACTS_STYLEWORD);// 字体

		// labelbx装文字
		labelBx.add(super.nicknameLabel);
		labelBx.add(Box.createVerticalStrut(5));
		labelBx.add(super.styleWord);
		labelBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH, Constants.CONTACTS_PANEL_HEIGHT));

		// 总成box
		thisBx.add(labelBx);
		thisBx.add(Box.createHorizontalStrut(5));
		thisBx.add(onlineState);
		thisBx.setPreferredSize(new Dimension(Constants.CONTACTS_PANEL_WIDTH, Constants.CONTACTS_PANEL_HEIGHT));
		thisBx.setBorder(BorderFactory.createLineBorder(Colors.CONTACT_BORDER_COLOR));

		// 设置监听器和popupmenu
		JPopupMenu operator = new JPopupMenu();
		JMenuItem deleteThis = new JMenuItem("删除该联系人");
		JMenuItem setRemark = new JMenuItem("设置备注");
		JMenuItem showProfile = new JMenuItem("个人信息");
		operator.add(deleteThis);
		operator.add(setRemark);
		operator.add(showProfile);

		deleteThis.addActionListener(e -> {
			int choose = JOptionPane.showConfirmDialog(null, "确认删除该联系人?\n操作将不能撤销.", "提示", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			// System.out.println(choose);
			if (choose == 0/* 选是 */) {
				MainWindow.deleteContacts(ID, MainWindow.ID, thisBx);// 发送删除指令
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
					chatWindow.requestFocus();//焦点
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
			System.out.println("文件不存在.");
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
