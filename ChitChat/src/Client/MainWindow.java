package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import Property.Property;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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
import CComponents.MessageAnswerType;
import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import CComponents.MessageReceive;
import Constants.*;
import Windows.PersonalData;
import utils.Window;

public class MainWindow extends JFrame {

	public static int ID;// �˺�
	private String key = null;
	Image image;// �û�ͷ��
	public static JLabel nicknameLabel;// �û��ǳ�
	public static JLabel styleWord;// �û��ĸ���ǩ��
	public static boolean hasPersonalWindow = false;
	
	JSplitPane split;// �зִ���,���Ϊ��ϵ��,�ұ�����Ϣ
	JMenuBar operats;// ���Ͻǲ˵���
	JMenu menu;// �˵�
	JMenuItem searchContacts;// ��������
	JMenuItem createContactsGroup;// ��������
	public static ConvList<Convasation> msgList;// ��Ϣ�б�
	public static ConvList<Convasation> ctsList;// ��ϵ���б�
	JScrollPane right;// ��ҳ���Ҳ��
	static JSplitPane left;// ��ҳ�����Ŀ�
	JScrollPane messages;// ��Ϣ�б�����
	static Box prof = Box.createHorizontalBox();

	public static JPanel msgPanel = new JPanel();
	public static JPanel ctsPanel = new JPanel();
	// public static Vector<Integer> ctsIDs = new Vector<Integer>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainWindow(int id) {
		ID = id;
		MessageReceive getMsg = MessageReceive.getInstance();
		
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

		
		// Refresh rfs = new Refresh();
		// new Thread(rfs).start();
	}

	public void init() {
		MessageBlob quest_for_profile = new MessageBlob();
		quest_for_profile.type = MessageBlobType.SELF_PROFILE_QUEST;
		quest_for_profile.senderID = ID;
		quest_for_profile.senderIP = Property.NATIVE_IP;
		
		new SendMessage(Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(quest_for_profile));
		
		messageInit();// ������Ϣ��ʼ��
		// ��messageInit()�������ѽ��O���^bottomComponrnt.

		//������Ӻ��ѵĴ���������
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.SELF_VERIFY;
		message.senderID = ID;
		message.senderIP = Property.NATIVE_IP;
		new SendMessage(Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(message));
		
//		//id��ipû��,�����������ļ���������.
//		message.type = MessageBlobType.SELF_FILE;
//		new SendMessage(Property.SERVER_IP
//				,SocketConstants.GENERAL_PORT
//				,MessageBlobOperator.pack(message));
		
		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// ���ø�����Ϣ���붥���ľ���
		left.setDividerSize(2);// ���÷ָ��߿��
		left.setEnabled(false);// �ָ��߲����ƶ�

		MessageBlob quest_for_friend_list = new MessageBlob();// ��ϵ���б��ʼ��
		quest_for_friend_list.type = MessageBlobType.FRIEND_LIST_QUEST;
		quest_for_friend_list.senderIP = Property.NATIVE_IP;
		quest_for_friend_list.senderID = ID;
		new SendMessage(Property.SERVER_IP
				, SocketConstants.GENERAL_PORT
				, MessageBlobOperator.pack(quest_for_friend_list));
		
		right = new JScrollPane();
		JLabel ctsHead = new JLabel("������ϵ��");
		searchContacts = new JMenuItem("������ϵ��");
		/*
		 * ʵ����������ϵ�˵Ĺ���.
		 */
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
				message.type = MessageBlobType.FIND_FRIEND;
				message.targetID = id;
				message.senderID = ID;
				message.senderIP = Property.NATIVE_IP;
				new SendMessage(Property.SERVER_IP, SocketConstants.GENERAL_PORT, MessageBlobOperator.pack(message));

			} else {
				JOptionPane.showMessageDialog(null, "�������˺�!", "", JOptionPane.PLAIN_MESSAGE);
			}
		});// TODO ����

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

		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* �Ƿ���������ػ� */, left, right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(false);// ��left(JSplitPane)��ͬ.

		setContentPane(split);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {// ���߲���
			@Override
			public void windowClosing(WindowEvent arg0) {
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.LOGOUT;
				message.senderIP = Property.NATIVE_IP;
				message.senderID = ID;
				message.key = null;
				new SendMessage(Property.SERVER_IP
						,SocketConstants.GENERAL_PORT
						,MessageBlobOperator.pack(message));
				System.exit(0);
			}
		});

	}

	public static void persInfoInit(MessageBlob msg) {
		String nickname = msg.nickname;
		String style = msg.style;
		
		Icon onlineImage;
		JLabel onlineState = null;
		try {
			onlineImage = new ImageIcon(ImageIO.read(new File(Property.IMAGE_ONLINE_URL)));
			onlineState = new JLabel(onlineImage);
			onlineState.setPreferredSize(
					new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH, Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("�ļ�������.");
		}

		nicknameLabel = new JLabel(nickname);
		nicknameLabel.setFont(Fonts.USER_NICKNAME);
		styleWord = new JLabel(style);
		styleWord.setFont(Fonts.USER_STYLEWORD);

		Box labels = Box.createVerticalBox();
		labels.add(nicknameLabel);
		labels.add(Box.createVerticalStrut(5));
		labels.add(styleWord);

		prof.add(labels);
		prof.add(onlineState);
		left.setTopComponent(prof);
		
		prof.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					MessageBlob message = new MessageBlob();
					message.type = MessageBlobType.SELF_PROFILE_QUEST;
					message.senderIP = Property.NATIVE_IP;
					message.senderID = ID;
					new SendMessage(Property.SERVER_IP
							,SocketConstants.GENERAL_PORT
							,MessageBlobOperator.pack(message));
					MainWindow.hasPersonalWindow = true;
					
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
		
	}

	public void messageInit() {// TODO: ��Ϣ��ʼ��

	
		boolean hasMessage = false;

		if (!hasMessage) {
			JLabel wr = new JLabel("������ϢŶ");
			msgPanel.add(wr);

		} else {
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
				// ctsList.get(i.ID).hasMessage = true;
			}
			// msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));

			msgPanel.setLayout(mS);
		}
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
		left = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		left.setBottomComponent(messages);

	}

	public static void ContactsInit(MessageBlob e) {// TODO:��ϵ���б��ʼ��
		boolean hasFriend = false;
		if (e.contactslist.length > 0)
			hasFriend = true;
		for (int i = 0; i < e.contactslist.length; i++) {
			ctsList.add(new Contacts(e.contactslist[i].id, e.contactslist[i].status, 0, 0, "",
					e.contactslist[i].nickname, e.contactslist[i].remark, e.contactslist[i].style, ""));
		}

		if (!hasFriend) {
			JLabel wr = new JLabel("������ϵ��Ŷ");
			ctsPanel.add(wr);
		} else {
			ctsList.sort();

			reloadContacts();
//			GridBagLayout LS = new GridBagLayout();
//			GridBagConstraints LSCstrs = new GridBagConstraints();
//			LSCstrs.gridheight = GridBagConstraints.PAGE_START;
//			LSCstrs.gridwidth = GridBagConstraints.REMAINDER;// ���ò���
//
//			for (Convasation i : ctsList) {
//				Box m = (Box) i.create();
//				LS.setConstraints(m, LSCstrs);
//				ctsPanel.add(m);
//			}
//			ctsPanel.setLayout(LS);
		}
		// msgL.setPreferredSize(getMinimumSize());

	}

	public static void deleteMessage(int ID, Box elem) {
		msgList.remove(ID);
		msgList.sort();
		msgPanel.remove(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();// ������ʾ
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

	public static void repaintContact(int ID) {

		ctsList.add(ctsList.remove(ID));
		ctsList.sort();
		reloadContacts();

	}

	public static void deleteContacts(int ID, int userID, Box elem) {// TODO:ɾ����ϵ��
		
		ctsList.remove(ID);
		ctsList.sort();
		MainWindow.ctsPanel.remove(elem);
		MainWindow.ctsPanel.repaint();// ���ϴ˾������һ���@ʾbug.ԭ����.
		MainWindow.ctsPanel.revalidate();// ������ʾ
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.DELETE_CONTACT;
		message.senderID = userID;
		message.targetID = ID;
		new SendMessage(Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(message));
	}

	public static boolean addContacts(MessageBlob e) {// TODO: �����ϵ��
		
		Contacts newContact = 
				new Contacts(e.senderID
				,Internet.ONLINE
				,0
				,Internet.READ
				,""
				,e.nickname
				,e.targetRemark
				,""
				,"");
		ctsList.add(newContact);
		ctsList.sort();
		reloadContacts();
		return true;
	}

	public static void addNewMessage(MessageBlob e) {

		String nickname = ctsList.get(e.senderID).nickname;
		String remark = ctsList.get(e.senderID).remark;
		
		Message newMessage = new Message(
				e.senderID
				,Internet.ONLINE
				,new Date().getTime()
				,Internet.UNREAD
				,e.text
				,nickname
				,remark
				,""
				,"");
		msgList.add(0, newMessage);// �ö�
		msgList.sort();
		reloadMessages();
	}
	public static void addNewMessage(Message e) {
		msgList.add(0,e);
		msgList.sort();
		reloadMessages();
	}

	public static void reloadMessages() {
		msgPanel.removeAll();
		GridBagLayout mS = new GridBagLayout();
		GridBagConstraints mSCstrs = new GridBagConstraints();
		mSCstrs.fill = GridBagConstraints.HORIZONTAL;// GirdBag����
		mSCstrs.anchor = GridBagConstraints.NORTH;
		mSCstrs.gridwidth = GridBagConstraints.REMAINDER;

		for (Convasation i : msgList) {
			Box m = (Box) i.create();
			mS.setConstraints(m, mSCstrs);
			msgPanel.add(m);
			// ctsList.get(i.ID).hasMessage = true;
		}
		// msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));

		msgPanel.setLayout(mS);

		MainWindow.msgPanel.repaint();// ���ϴ˾������һ���@ʾbug.ԭ����.
		MainWindow.msgPanel.revalidate();// ������ʾ
	}

	public static void reloadContacts() {

		ctsPanel.removeAll();

		GridBagLayout LS = new GridBagLayout();
		GridBagConstraints LSCstrs = new GridBagConstraints();
		LSCstrs.gridheight = GridBagConstraints.PAGE_START;
		LSCstrs.gridwidth = GridBagConstraints.REMAINDER;// ���ò���

		for (Convasation i : ctsList) {
			Box m = (Box) i.create();
			LS.setConstraints(m, LSCstrs);
			ctsPanel.add(m);
		}
		ctsPanel.setLayout(LS);

		MainWindow.ctsPanel.repaint();// ���ϴ˾������һ���@ʾbug.ԭ����.
		MainWindow.ctsPanel.revalidate();// ������ʾ

	}
}
