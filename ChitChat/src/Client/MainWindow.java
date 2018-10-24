package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import CComponents.ConvList;
import CComponents.Convasation;
import Constants.*;
import DataBaseOperation.OperateSQLServer;
import Server.ParseInformation;
import Server.SendThread;
import Windows.ChatWindow;
import Windows.LoginWindow;
import Windows.PersonalData;
import Windows.StrangerWindow;

public class MainWindow extends JFrame {

	static int ID;// �˺�
	Image image;// �û�ͷ��
	public static JLabel nicknameLabel;// �û��ǳ�
	public static JLabel styleWord;// �û��ĸ���ǩ��
	JSplitPane split;// �зִ���,���Ϊ��ϵ��,�ұ�����Ϣ
	JMenuBar operats;// ���Ͻǲ˵���
	JMenu menu;// �˵�
	JMenuItem searchContacts;// ��������
	JMenuItem createContactsGroup;// ��������
	public static ConvList<Convasation> msgList;// ��Ϣ�б�
	public static ConvList<Convasation> ctsList;// ��ϵ���б�
	JScrollPane right;// ��ҳ���Ҳ��
	JSplitPane left;// ��ҳ�����Ŀ�
	JScrollPane messages;// ��Ϣ�б�����

	public static JPanel msgPanel = new JPanel();
	public static JPanel ctsPanel = new JPanel();
	// public static Vector<Integer> ctsIDs = new Vector<Integer>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainWindow(int id) {
		ID = id;

		try {
			msgList = new ConvList(Constants.CONVLIST_MESSAGE);
			ctsList = new ConvList(Constants.CONVLIST_CONTACTS);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.PLAIN_MESSAGE);
		}
		setLayout(new FlowLayout());
		init();
		setBounds(Window.getMiddleWidth(Constants.MAIN_WINDOW_WIDTH),
				Window.getMiddleHeight(Constants.MAIN_WINDOW_HEIGHT), Constants.MAIN_WINDOW_WIDTH,
				Constants.MAIN_WINDOW_HEIGHT);
		setVisible(true);
	
//		Refresh rfs = new Refresh();
//		new Thread(rfs).start();
	}

	public void init() {

		persInfoInit();// ��Ϣ��ʼ��
		messageInit();// ������Ϣ��ʼ��
		// ��messageInit()�������ѽ��O���^bottomComponrnt.

		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// ���ø�����Ϣ���붥���ľ���
		left.setDividerSize(2);// ���÷ָ��߿��
		left.setEnabled(false);// �ָ��߲����ƶ�

		ContactsInit();// ��ϵ���б��ʼ��

		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* �Ƿ���������ػ� */, left, right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(false);// ��left(JSplitPane)��ͬ.

		setContentPane(split);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {// ���߲���
			@Override
			public void windowClosing(WindowEvent arg0) {
				byte[] info;
				info = ("3#" + ID + "#" + ChatWindow.getNetworkTime() + "#" + ID).getBytes();

				SendThread send;
				try {
					OperateSQLServer oprt = new OperateSQLServer();
					oprt.connectToDatabase();
					send = new SendThread("192.168.43.29", 23334, info);
					new Thread(send).start();
					oprt.updateLoggingStatus(0, ID);
					oprt.closeDatabase();
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public void persInfoInit() {// TODO:�û���Ϣ��ʼ��
		String nickname = null;
		String style = null;
		OperateSQLServer oprt = new OperateSQLServer();
		oprt.connectToDatabase();
		ResultSet rs = oprt.getPersonalInformation(ID);

		try {
			rs.next();
			nickname = rs.getString(2);// nickname
			style = rs.getString(10);// ����ǩ��
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "���������쳣.\n������:127", "", JOptionPane.PLAIN_MESSAGE);
		}

		oprt.closeDatabase();

		Icon onlineImage;
		JLabel onlineState = null;
		try {
			onlineImage = new ImageIcon(ImageIO.read(new File(Window.IMAGE_ONLINE_URL)));
			onlineState = new JLabel(onlineImage);
			onlineState.setPreferredSize(
					new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH, Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("�ļ�������.");
		}

		if (nickname == null || style == null) {
			JOptionPane.showMessageDialog(null, "������Ϣ��ȡ�쳣.��������.\n������:146", "", JOptionPane.PLAIN_MESSAGE);
		}
		nicknameLabel = new JLabel(nickname);
		nicknameLabel.setFont(Fonts.USER_NICKNAME);
		styleWord = new JLabel(style);
		styleWord.setFont(Fonts.USER_STYLEWORD);

		Box labels = Box.createVerticalBox();
		labels.add(nicknameLabel);
		labels.add(Box.createVerticalStrut(5));
		labels.add(styleWord);

		Box prof = Box.createHorizontalBox();
		left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, prof, null);
		prof.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new PersonalData(MainWindow.ID);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				prof.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_CHOSED));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				prof.setBorder(BorderFactory.createLineBorder(null));
			}
		});
		prof.add(labels);
		prof.add(onlineState);
	}

	public void messageInit() {// TODO: ��Ϣ��ʼ��

		// TODO ��ȡ��Ϣ�б� ��ȡ����
		// ����Ϊ��������.

		// �@�ǜyԇ����.

		msgList.sort();

		GridBagLayout mS = new GridBagLayout();
		GridBagConstraints mSCstrs = new GridBagConstraints();
		mSCstrs.fill = GridBagConstraints.HORIZONTAL;// GirdBag����
		mSCstrs.anchor = GridBagConstraints.NORTH;
		mSCstrs.gridwidth = GridBagConstraints.REMAINDER;

		for (Convasation i : msgList) {
			Box m = (Box) i.create();
			mS.setConstraints(m, mSCstrs);
			msgPanel.add(m);
			ctsList.get(i.ID).hasMessage = true;

		}
		// msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));

		msgPanel.setLayout(mS);
		// msgL.setPreferredSize(getMinimumSize());
		messages = new JScrollPane();
		JLabel msgHead = new JLabel("��Ϣ");
		msgHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		msgHead.setFont(Fonts.MESSAGE_HEADER);

		messages.setAlignmentY(Component.TOP_ALIGNMENT);
		messages.setColumnHeaderView(msgHead);// ���ñ���
		messages.setViewportView(msgPanel);// ����Viewport

		// JScrollBar msgScrollBar = messages.getVerticalScrollBar();
		// msgScrollBar.setValue(msgScrollBar.getMaximum());//���ø�scrollpane�Ĺ�����

		messages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		left.setBottomComponent(messages);

	}

	public void ContactsInit() {// TODO:��ϵ���б��ʼ��
		boolean hasFriend = true;
		// TODO ��ȡ��ϵ���б�,����Ϊ��������
		OperateSQLServer oprt = new OperateSQLServer();
		oprt.connectToDatabase();
		ResultSet rs = oprt.getFriendList(ID);
		try {
			if (!rs.next()) {
				oprt.closeDatabase();
				oprt = null;
				hasFriend = false;
			} else {
				do {
					int id = rs.getInt(1);
					ResultSet info = oprt.getPersonalInformation(id);
					info.next();
					int onlineState = info.getInt(6);
					String style = info.getString(10);
					ctsList.add(new Contacts(info.getInt(1)// ID
							, onlineState, new Date().getTime()// time
							, Internet.READ// ifread
							, "", info.getString(2)// �ǳ�
							, rs.getString(4)// rs,������Ϣ��,��ע
							, style, info.getString(8)));
				} while (rs.next());
			}
			rs = null;
			oprt.closeDatabase();
			oprt = null;
		} catch (Exception e) {
		}

		if (!hasFriend) {
			JLabel wr = new JLabel("������ϵ��Ŷ");
			ctsPanel.add(wr);
		} else {
			ctsList.sort();

			GridBagLayout LS = new GridBagLayout();
			GridBagConstraints LSCstrs = new GridBagConstraints();
			LSCstrs.gridheight = GridBagConstraints.PAGE_START;
			LSCstrs.gridwidth = GridBagConstraints.REMAINDER;// ���ò���

			for (Convasation i : ctsList) {
				Box m = (Box) i.create();
				LS.setConstraints(m, LSCstrs);
				ctsPanel.add(m);
			}
			// msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));

			ctsPanel.setLayout(LS);
		}
		// msgL.setPreferredSize(getMinimumSize());

		right = new JScrollPane();
		JLabel ctsHead = new JLabel("������ϵ��");
		searchContacts = new JMenuItem("������ϵ��");

		searchContacts.addActionListener(e -> {
			String match = JOptionPane.showInputDialog(null, null, "�������˺�", JOptionPane.PLAIN_MESSAGE);
			if (match == null)
				return;
			if (match.length() >= 10) {
				JOptionPane.showMessageDialog(null, "�û�������!", "", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			if (match.matches("[0-9]+")) {
				int id = Integer.parseInt(match);
				OperateSQLServer opt = new OperateSQLServer();
				opt.connectToDatabase();
				ResultSet rss = opt.getPersonalInformation(id);
				try {
					if (!rss.next()) {
						JOptionPane.showMessageDialog(null, "�û�������!", "", JOptionPane.PLAIN_MESSAGE);
						return;
					} else
						new StrangerWindow(MainWindow.ID, id);
				} catch (HeadlessException e1) {
					JOptionPane.showMessageDialog(null, "�����쳣!\n������:283", "", JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "�����쳣!\nz", "", JOptionPane.PLAIN_MESSAGE);
				}
				opt.closeDatabase();
			} else {
				JOptionPane.showMessageDialog(null, "�������˺�!", "", JOptionPane.PLAIN_MESSAGE);
			}
		});// TODO ����

		/*
		 * 
		 * �ͷ��ڴ�
		 * 
		 */
		JButton plus = new JButton("+");
		plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JPopupMenu pop = new JPopupMenu();
				pop.add(searchContacts);
				pop.show(plus, 0, 0);
			}
		});
		ctsHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		ctsHead.setFont(Fonts.MESSAGE_HEADER);
		right.setColumnHeaderView(ctsHead);// ���ñ���
		right.setViewportView(ctsPanel);// ����Viewport

		right.setCorner(JScrollPane.UPPER_RIGHT_CORNER, plus);
		right.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		right.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public static void deleteMessage(int ID, Box elem) {
		msgList.remove(ID);
		msgList.sort();
		msgPanel.remove(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();// ������ʾ
		// MainWindow.messages.repaint();
		// TODO ������
	}

	public static void readMessage(int ID, Box elem) {
		MainWindow.msgList.get(ID).isRead = Internet.READ;
		MainWindow.msgList.add(MainWindow.msgList.remove(ID));// ���x��ԓ��Ϣ����K���뵽ĩβ.
		// MainWindow.msgList.sort();
		MainWindow.msgPanel.remove(elem);
		MainWindow.msgPanel.add(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();
		// TODO ������
	}

	public static void deleteContacts(int ID, int userID, Box elem) {// TODO:ɾ����ϵ��

		ctsList.remove(ID);
		ctsList.sort();
		MainWindow.ctsPanel.remove(elem);
		MainWindow.ctsPanel.repaint();// ���ϴ˾������һ���@ʾbug.ԭ����.
		MainWindow.ctsPanel.revalidate();// ������ʾ
		OperateSQLServer oprt = new OperateSQLServer();
		oprt.connectToDatabase();
		oprt.deleteContacts(userID, ID);

		oprt.closeDatabase();
	}

	public static boolean addContacts(int userID, int ID) {// TODO: �����ϵ��
		OperateSQLServer oprt = new OperateSQLServer();
		oprt.connectToDatabase();
		ResultSet rs = oprt.getPersonalInformation(ID);
		int id;
		int onlineState;
		String gender;
		try {
			if (userID == ID)
				throw new SQLException();// ���ܼ��Լ�Ϊ����
			rs.next();
			id = rs.getInt(1);
			onlineState = rs.getInt(6);
			gender = rs.getString(8);
			ctsList.add(new Contacts(id, onlineState, new Date().getTime(), Internet.READ, ""// �ϴ���������һ��
					, rs.getString(2), ""// ��ע
					, rs.getString(10), gender));
			MainWindow.ctsPanel.repaint();// ���ϴ˾������һ���@ʾbug.ԭ����.
			MainWindow.ctsPanel.revalidate();// ������ʾ
			oprt.addNewContacts(userID, ID, rs.getString(2));
		} catch (SQLException e) {
			return false;
		}
		oprt.closeDatabase();
		return true;
	}

	
	public class Refresh implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
				MainWindow.msgPanel.removeAll();
				//messageInit();
				MainWindow.msgPanel.repaint();
				MainWindow.msgPanel.revalidate();
				
				MainWindow.ctsList.clear();
				MainWindow.ctsPanel.removeAll();
				//ContactsInit();
				MainWindow.ctsPanel.repaint();
				MainWindow.ctsPanel.revalidate();

			}
		}
	}

}
