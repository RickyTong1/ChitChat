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
			setIcon(Window.IMAGE_ONLINE_URL);
			break;
		}
		case Internet.OFFLINE: {
			isOnline = Internet.OFFLINE;
			setIcon(Window.IMAGE_OFFLINE_URL);
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
			String match = JOptionPane.showInputDialog(null, null, "�����뱸ע��", JOptionPane.PLAIN_MESSAGE);
			if (match == null)
				return;
			//TODO ����������
//			OperateSQLServer opt = new OperateSQLServer();
//			opt.connectToDatabase();
//			opt.updateFriendRemark(MainWindow.ID, ID, match);
//
//			MainWindow.ctsList.remove(ID);
//			ResultSet info = opt.getPersonalInformation(ID);
//			try {
//				info.next();
//				MainWindow.ctsList.add(new Contacts(info.getInt(1)// ID
//				, info.getInt(6), new Date().getTime()// time
//				, Internet.READ// ifread
//				, "", info.getString(2), info.getString(4), style, info.getString(8)));
//			} catch (SQLException e1) {
//				JOptionPane.showMessageDialog(null, "����:CTS_137", "", JOptionPane.PLAIN_MESSAGE);
//			}
//			opt.closeDatabase();
			MainWindow.ctsList.sort();
			MainWindow.ctsPanel.repaint();
			MainWindow.ctsPanel.revalidate();
			
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
			setIcon(Window.IMAGE_OFFLINE_URL);
		}
			break;
		case Internet.OFFLINE: {
			setIcon(Window.IMAGE_OFFLINE_URL);
		}
		}

		// // TODO ��ϵ��״̬ˢ�·���
		// new Thread() {
		// @Override
		// public void run() {
		// while(true)
		// {
		// OperateSQLServer oprt = new OperateSQLServer();
		// ResultSet rs = oprt.getPersonalInformation(ID);
		// try {
		// rs.next();
		// switch(rs.getInt(6))
		// {
		// case Internet.ONLINE:{
		// MainWindow.ctsList.get(ID).onlineState = Internet.ONLINE;
		// isOnline = Internet.ONLINE;
		// // setIcon(Window.IMAGE_ONLINE_URL);
		// }break;
		// case Internet.OFFLINE:{
		// MainWindow.ctsList.get(ID).onlineState = Internet.OFFLINE;
		// isOnline = Internet.OFFLINE;
		// // setIcon(Window.IMAGE_OFFLINE_URL);
		// }break;
		//
		// }//switch
		// oprt.closeDatabase();
		// MainWindow.ctsList.sort();
		// } catch (SQLException e) {}
		// MainWindow.ctsPanel.remove(thisBx);
		// MainWindow.ctsPanel.add(MainWindow.ctsList.get(ID).create());
		// try {
		// sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// }.start();
		//
	}

}
