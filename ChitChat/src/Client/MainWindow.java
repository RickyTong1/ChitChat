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

	public static int ID;// 账号
	private String key = null;
	Image image;// 用户头像
	public static JLabel nicknameLabel;// 用户昵称
	public static JLabel styleWord;// 用户的个性签名
	public static boolean hasPersonalWindow = false;
	
	JSplitPane split;// 切分窗格,左边为联系人,右边是消息
	JMenuBar operats;// 左上角菜单栏
	JMenu menu;// 菜单
	JMenuItem searchContacts;// 搜索好友
	JMenuItem createContactsGroup;// 创建分组
	public static ConvList<Convasation> msgList;// 消息列表
	public static ConvList<Convasation> ctsList;// 联系人列表
	JScrollPane right;// 主页面右侧框
	static JSplitPane left;// 主页面左侧的框
	JScrollPane messages;// 消息列表容器
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
		
		messageInit();// 聊天消息初始化
		// 在messageInit()方法中已經設置過bottomComponrnt.

		//请求添加好友的待处理事务
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.SELF_VERIFY;
		message.senderID = ID;
		message.senderIP = Property.NATIVE_IP;
		new SendMessage(Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(message));
		
//		//id和ip没变,请求待处理的文件接收事务.
//		message.type = MessageBlobType.SELF_FILE;
//		new SendMessage(Property.SERVER_IP
//				,SocketConstants.GENERAL_PORT
//				,MessageBlobOperator.pack(message));
		
		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// 设置个人信息框离顶部的距离
		left.setDividerSize(2);// 设置分隔线宽度
		left.setEnabled(false);// 分隔线不可移动

		MessageBlob quest_for_friend_list = new MessageBlob();// 联系人列表初始化
		quest_for_friend_list.type = MessageBlobType.FRIEND_LIST_QUEST;
		quest_for_friend_list.senderIP = Property.NATIVE_IP;
		quest_for_friend_list.senderID = ID;
		new SendMessage(Property.SERVER_IP
				, SocketConstants.GENERAL_PORT
				, MessageBlobOperator.pack(quest_for_friend_list));
		
		right = new JScrollPane();
		JLabel ctsHead = new JLabel("所有联系人");
		searchContacts = new JMenuItem("搜索联系人");
		/*
		 * 实现了搜索联系人的功能.
		 */
		searchContacts.addActionListener(e -> {
			String match = JOptionPane.showInputDialog(null, null, "请输入账号", JOptionPane.PLAIN_MESSAGE);
			if (match == null)
				return;
			if (match.length() >= 10) {
				JOptionPane.showMessageDialog(null, "用户不存在!", "", JOptionPane.PLAIN_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "请输入账号!", "", JOptionPane.PLAIN_MESSAGE);
			}
		});// TODO 扩充

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
		right.setColumnHeaderView(ctsHead);// 设置标题
		right.setViewportView(ctsPanel);// 设置Viewport

		right.setCorner(JScrollPane.UPPER_RIGHT_CORNER, plus);
		right.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		right.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* 是否可以连续重绘 */, left, right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(false);// 与left(JSplitPane)相同.

		setContentPane(split);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {// 下线操作
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
			System.out.println("文件不存在.");
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

	public void messageInit() {// TODO: 消息初始化

	
		boolean hasMessage = false;

		if (!hasMessage) {
			JLabel wr = new JLabel("暂无消息哦");
			msgPanel.add(wr);

		} else {
			msgList.sort();

			GridBagLayout mS = new GridBagLayout();
			GridBagConstraints mSCstrs = new GridBagConstraints();
			mSCstrs.fill = GridBagConstraints.HORIZONTAL;// GirdBag布局
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
		JLabel msgHead = new JLabel("消息");
		msgHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		msgHead.setFont(Fonts.MESSAGE_HEADER);

		messages.setAlignmentY(Component.TOP_ALIGNMENT);
		messages.setColumnHeaderView(msgHead);// 设置标题
		messages.setViewportView(msgPanel);// 设置Viewport

		// JScrollBar msgScrollBar = messages.getVerticalScrollBar();
		// msgScrollBar.setValue(msgScrollBar.getMaximum());//设置该scrollpane的滚动条

		messages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		left = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		left.setBottomComponent(messages);

	}

	public static void ContactsInit(MessageBlob e) {// TODO:联系人列表初始化
		boolean hasFriend = false;
		if (e.contactslist.length > 0)
			hasFriend = true;
		for (int i = 0; i < e.contactslist.length; i++) {
			ctsList.add(new Contacts(e.contactslist[i].id, e.contactslist[i].status, 0, 0, "",
					e.contactslist[i].nickname, e.contactslist[i].remark, e.contactslist[i].style, ""));
		}

		if (!hasFriend) {
			JLabel wr = new JLabel("暂无联系人哦");
			ctsPanel.add(wr);
		} else {
			ctsList.sort();

			reloadContacts();
//			GridBagLayout LS = new GridBagLayout();
//			GridBagConstraints LSCstrs = new GridBagConstraints();
//			LSCstrs.gridheight = GridBagConstraints.PAGE_START;
//			LSCstrs.gridwidth = GridBagConstraints.REMAINDER;// 设置布局
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
		MainWindow.msgPanel.revalidate();// 重新显示
	}

	public static void readMessage(int ID, Box elem) {
		MainWindow.msgList.get(ID).isRead = Internet.READ;
		MainWindow.msgList.add(MainWindow.msgList.remove(ID));// 已讀后將該消息抽出並插入到末尾.
		// MainWindow.msgList.sort();
		MainWindow.msgPanel.remove(elem);
		MainWindow.msgPanel.add(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();
		// TODO 數據庫
	}

	public static void repaintContact(int ID) {

		ctsList.add(ctsList.remove(ID));
		ctsList.sort();
		reloadContacts();

	}

	public static void deleteContacts(int ID, int userID, Box elem) {// TODO:删除联系人
		
		ctsList.remove(ID);
		ctsList.sort();
		MainWindow.ctsPanel.remove(elem);
		MainWindow.ctsPanel.repaint();// 加上此句會消除一個顯示bug.原因不明.
		MainWindow.ctsPanel.revalidate();// 重新显示
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.DELETE_CONTACT;
		message.senderID = userID;
		message.targetID = ID;
		new SendMessage(Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(message));
	}

	public static boolean addContacts(MessageBlob e) {// TODO: 添加联系人
		
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
		msgList.add(0, newMessage);// 置顶
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
		mSCstrs.fill = GridBagConstraints.HORIZONTAL;// GirdBag布局
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

		MainWindow.msgPanel.repaint();// 加上此句會消除一個顯示bug.原因不明.
		MainWindow.msgPanel.revalidate();// 重新显示
	}

	public static void reloadContacts() {

		ctsPanel.removeAll();

		GridBagLayout LS = new GridBagLayout();
		GridBagConstraints LSCstrs = new GridBagConstraints();
		LSCstrs.gridheight = GridBagConstraints.PAGE_START;
		LSCstrs.gridwidth = GridBagConstraints.REMAINDER;// 设置布局

		for (Convasation i : ctsList) {
			Box m = (Box) i.create();
			LS.setConstraints(m, LSCstrs);
			ctsPanel.add(m);
		}
		ctsPanel.setLayout(LS);

		MainWindow.ctsPanel.repaint();// 加上此句會消除一個顯示bug.原因不明.
		MainWindow.ctsPanel.revalidate();// 重新显示

	}
}
