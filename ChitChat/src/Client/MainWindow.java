package Client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import CComponents.MessageReceive;
import Constants.*;
import Windows.PersonalData;
import utils.Window;


public class MainWindow extends JFrame {

	public static int ID;// 账号
	Image image;// 用户头像
	public static JLabel nicknameLabel;// 用户昵称
	public static JLabel styleWord;// 用户的个性签名
	JSplitPane split;// 切分窗格,左边为联系人,右边是消息
	JMenuBar operats;// 左上角菜单栏
	JMenu menu;// 菜单
	JMenuItem searchContacts;// 搜索好友
	JMenuItem createContactsGroup;// 创建分组
	public static ConvList<Convasation> msgList;// 消息列表
	public static ConvList<Convasation> ctsList;// 联系人列表
	JScrollPane right;// 主页面右侧框
	JSplitPane left;// 主页面左侧的框
	JScrollPane messages;// 消息列表容器

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

		MessageReceive getMsg = MessageReceive.getInstance();
		new Thread(getMsg).start();;
		// Refresh rfs = new Refresh();
		// new Thread(rfs).start();
	}

	public void init() {

		persInfoInit();// 信息初始化
		messageInit();// 聊天消息初始化
		// 在messageInit()方法中已經設置過bottomComponrnt.

		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// 设置个人信息框离顶部的距离
		left.setDividerSize(2);// 设置分隔线宽度
		left.setEnabled(false);// 分隔线不可移动

		ContactsInit();// 联系人列表初始化

		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* 是否可以连续重绘 */, left, right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(false);// 与left(JSplitPane)相同.

		setContentPane(split);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {// 下线操作
			@Override
			public void windowClosing(WindowEvent arg0) {
				// byte[] info;
				// info = ("3#" + ID + "#" + ChatWindow.getNetworkTime() + "#" + ID).getBytes();

				// SendThread send;
				// try {
				// TODO 服务器改造
				// OperateSQLServer oprt = new OperateSQLServer();
				// oprt.connectToDatabase();
				// send = new SendThread("192.168.43.29", 23334, info);
				// new Thread(send).start();
				// oprt.updateLoggingStatus(0, ID);
				// oprt.closeDatabase();
				System.exit(0);
				// } catch (UnknownHostException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		});

	}

	public void persInfoInit() {
		String nickname = null;
		String style = null;
		// OperateSQLServer oprt = new OperateSQLServer();
		// oprt.connectToDatabase();
		// ResultSet rs = oprt.getPersonalInformation(ID);
		//
		// try {
		// rs.next();
		// nickname = rs.getString(2);// nickname
		// style = rs.getString(10);// 个性签名
		// } catch (SQLException e1) {
		// JOptionPane.showMessageDialog(null, "网络连接异常.\n错误码:127", "",
		// JOptionPane.PLAIN_MESSAGE);
		// }
		//
		// oprt.closeDatabase();TODO Server rebuild.
		nickname = "赫鲁晓夫爱玉米";
		style = "这是个没有个性的个性签名.不过测试工程师认为应该测试下这个最长时会是什么情况.略略略略略略略";

		Icon onlineImage;
		JLabel onlineState = null;
		try {
			onlineImage = new ImageIcon(ImageIO.read(new File(Window.IMAGE_ONLINE_URL)));
			onlineState = new JLabel(onlineImage);
			onlineState.setPreferredSize(
					new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH, Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("文件不存在.");
		}

		if (nickname == null || style == null) {
			JOptionPane.showMessageDialog(null, "个人信息拉取异常.请检查网络.\n错误码: MAIWIN_PNINFO_177", "",
					JOptionPane.PLAIN_MESSAGE);
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

	public void messageInit() {// TODO: 消息初始化

		boolean hasMessage = false;
	
		if (!hasMessage) {
			JLabel wr = new JLabel("暂无联系人哦");
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
		left.setBottomComponent(messages);

	}

	public void ContactsInit() {// TODO:联系人列表初始化
		boolean hasFriend = false;
		// OperateSQLServer oprt = new OperateSQLServer();
		// oprt.connectToDatabase();
		// ResultSet rs = oprt.getFriendList(ID);
		// try {
		// if (!rs.next()) {
		// oprt.closeDatabase();
		// oprt = null;
		// } else {
		// hasFriend = true;
		// do {
		// int id = rs.getInt(1);
		// ResultSet info = oprt.getPersonalInformation(id);
		// info.next();
		// int onlineState = info.getInt(6);
		// String style = info.getString(10);
		// ctsList.add(new Contacts(info.getInt(1)// ID
		// , onlineState, new Date().getTime()// time
		// , Internet.READ// ifread
		// , "", info.getString(2)// 昵称
		// , rs.getString(4)// rs,好友信息库,备注
		// , style, info.getString(8)));
		// } while (rs.next());
		// }
		// rs = null;
		// oprt.closeDatabase();
		// oprt = null;
		// } catch (Exception e) {
		// }//TODO Server Rebuild.
		
		if (!hasFriend) {
			JLabel wr = new JLabel("暂无联系人哦");
			ctsPanel.add(wr);
		} else {
			ctsList.sort();

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
		}
		// msgL.setPreferredSize(getMinimumSize());

		right = new JScrollPane();
		JLabel ctsHead = new JLabel("所有联系人");
		searchContacts = new JMenuItem("搜索联系人");

		searchContacts.addActionListener(e -> {
			//TODO 测试中 上线时请修改
			addContacts(1,1);
//			String match = JOptionPane.showInputDialog(null, null, "请输入账号", JOptionPane.PLAIN_MESSAGE);
//			if (match == null)
//				return;
//			if (match.length() >= 10) {
//				JOptionPane.showMessageDialog(null, "用户不存在!", "", JOptionPane.PLAIN_MESSAGE);
//				return;
//			}
//			if (match.matches("[0-9]+")) {
//				int id = Integer.parseInt(match);
//				// OperateSQLServer opt = new OperateSQLServer();
//				// opt.connectToDatabase();
//				// ResultSet rss = opt.getPersonalInformation(id);
//				// try {
//				// if (!rss.next()) {
//				// JOptionPane.showMessageDialog(null, "用户不存在!", "", JOptionPane.PLAIN_MESSAGE);
//				// return;
//				// } else
//				// new StrangerWindow(MainWindow.ID, id);
//				// } catch (HeadlessException e1) {
//				// JOptionPane.showMessageDialog(null, "连接异常!\n错误码:283", "",
//				// JOptionPane.PLAIN_MESSAGE);
//				// } catch (SQLException e1) {
//				// JOptionPane.showMessageDialog(null, "连接异常!\nz", "",
//				// JOptionPane.PLAIN_MESSAGE);
//				// }
//				// opt.closeDatabase();TODO Server rebuild
//			} else {
//				JOptionPane.showMessageDialog(null, "请输入账号!", "", JOptionPane.PLAIN_MESSAGE);
//			}
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
	}

	public static void deleteMessage(int ID, Box elem) {
		msgList.remove(ID);
		msgList.sort();
		msgPanel.remove(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();// 重新显示
		// MainWindow.messages.repaint();
		// TODO 數據庫
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
		// OperateSQLServer oprt = new OperateSQLServer();
		// oprt.connectToDatabase();
		// oprt.deleteContacts(userID, ID);
		//
		// oprt.closeDatabase();TODO Server rebuild.
	}

	public static boolean addContacts(int userID, int ID) {// TODO: 添加联系人
		// OperateSQLServer oprt = new OperateSQLServer();
		// oprt.connectToDatabase();
		// ResultSet rs = oprt.getPersonalInformation(ID);
		// int id;
		// int onlineState;
		// String gender;
		// try {
		// if (userID == ID)
		// throw new SQLException();// 不能加自己为好友
		// rs.next();
		// id = rs.getInt(1);
		// onlineState = rs.getInt(6);
		// gender = rs.getString(8);
		// Contacts newContact = new Contacts(id, onlineState, new Date().getTime(),
		// Internet.READ, ""// 上次聊天的最后一句
		// , rs.getString(2), ""// 备注
		// , rs.getString(10), gender);
		// oprt.addNewContacts(userID, ID, rs.getString(2));
		// oprt.closeDatabase();

		//TODO 数据库操作.
		//ctsList.add(newContact);
		ctsList.sort();
		
		 reloadContacts();

		// } catch (SQLException e) {
		// return false;
		// }TODO Server rebuild.

		return true;
	}

	public static void addNewMessage(int userID,int ID) {
		
		//TODO 数据库操作
		Message newMessage = new Message(47, 1
				, new Date().getTime(), Internet.UNREAD, "大骗子欠我的东西快还我"// 上次聊天的最后一句
				, "新联系人", "新联系人备注"// 备注
				, "个性签名", "性别");
		msgList.add(0,newMessage);//置顶
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
