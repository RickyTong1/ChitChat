package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
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
import Constants.*;

public class MainWindow extends JFrame {

	Image image;// 用户头像
	JLabel nicknameLabel;// 用户昵称
	JLabel styleWord;// 用户的个性签名
	JSplitPane split;// 切分窗格,左边为联系人,右边是消息
	JMenuBar operats;// 左上角菜单栏
	JMenu menu;// 菜单
	JMenuItem searchContacts;// 搜索好友
	JMenuItem createContactsGroup;// 创建分组
	static ConvList<Convasation> msgList;//消息列表
	static ConvList<Convasation> ctsList;//联系人列表
	JScrollPane right;// 主页面右侧框
	JSplitPane left;// 主页面左侧的框
	JScrollPane messages;// 消息列表容器

	MainWindow() {
		setLayout(new FlowLayout());
		init();
		setBounds(400, 200, Constants.MAIN_WINDOW_WIDTH, Constants.MAIN_WINDOW_HEIGHT);
		setVisible(true);
	}

	/*
	 * init()函数调用persInfoInit(),messageInit()和ContactsInit()方法初始化主页面的基本信息
	 */
	public void init() {
			try {
				
				msgList = new ConvList(Constants.MESSAGE);
				ctsList = new ConvList(Constants.CONTACTS);
						} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"",JOptionPane.PLAIN_MESSAGE);
			}
			
		persInfoInit();// 信息初始化

		messageInit();// 聊天消息初始化
		//在messageInit()方法中已經設置過bottomComponrnt.
		
		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// 设置个人信息框离顶部的距离
		left.setDividerSize(2);// 设置分隔线宽度
		left.setEnabled(false);// 分隔线不可移动
		
		ContactsInit();// 联系人列表初始化

		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* 是否可以连续重绘 */, left
				,right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(true);// 与left(JSplitPane)相同.

		setContentPane(split);
		this.setResizable(true);// 不可更改大小//TODO 

	}

	public void persInfoInit() {// TODO:用户信息初始化

		String nickname = "赫鲁晓夫爱玉米";// TODO 改
		String style = "你好啊李银河.";
		
		Icon onlineImage;
		JLabel onlineState = null;
		try {
			onlineImage = new ImageIcon(ImageIO.read(
					new File("C:\\Users\\Ricky\\eclipse-workspace\\ChitChat\\image\\Online.png")));
			onlineState = new JLabel(onlineImage);
			onlineState.setPreferredSize(new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH
					,Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("文件不存在.");
		}
		
		nicknameLabel = new JLabel(nickname);
		styleWord = new JLabel(style);
		styleWord.setFont(Fonts.USER_STYLEWORD);
		

		Box labels = Box.createVerticalBox();
		labels.add(nicknameLabel);
		labels.add(Box.createVerticalStrut(5));
		labels.add(styleWord);

		Box prof = Box.createHorizontalBox();
		left = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, prof, null);
		prof.add(labels);
		prof.add(onlineState);
		
		
	}

	public void messageInit() {// TODO: 消息初始化
		
		//TODO 拉取消息列表
		//以下为测试用例.
		int s = 8;
		Message[] mes = new Message[s];
		for(int i = 0; i < s; i++)
		{
			mes[i] = new Message(1234566, Internet.ONLINE
					,new Date().getTime(),Internet.READ,"无论这个句子有多长长长长长长长长长长长长长长都不会影响","赫魯曉夫愛玉米"
					,"苟利國家生死以");
			msgList.add(mes[i]);
		}
		//這是測試用例.
		msgList.sort();
		JPanel msgPanel = new JPanel();//.createVerticalBox();
		
		GridBagLayout mS = new GridBagLayout();
		GridBagConstraints mSCstrs = new GridBagConstraints();
	//	mSCstrs.fill = GridBagConstraints.HORIZONTAL;//GirdBag布局
		mSCstrs.gridheight = GridBagConstraints.PAGE_START;
		mSCstrs.gridwidth = GridBagConstraints.REMAINDER;
		
		for(Convasation i: msgList)
		{
			Box m = (Box) i.create();
			mS.setConstraints(m,mSCstrs);
			msgPanel.add(m);
		}
		//msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		
		msgPanel.setLayout(mS);
		//msgL.setPreferredSize(getMinimumSize());
		JScrollPane msgScroll = new JScrollPane();
		JLabel msgHead = new JLabel("消息");
		msgHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		msgHead.setFont(Fonts.MESSAGE_HEADER);
		
		msgScroll.setColumnHeaderView(msgHead);//设置标题
		msgScroll.setViewportView(msgPanel);//设置Viewport
		
		JScrollBar msgScrollBar = msgScroll.getVerticalScrollBar();
		msgScrollBar.setValue(msgScrollBar.getMaximum());//设置该scrollpane的滚动条
		
		msgScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		msgScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		left.setBottomComponent(msgScroll);
		
	}

	public void ContactsInit() {// TODO:联系人列表初始化
		
		//TODO 拉取联系人列表,以下为测试用例
		int s = 8;
		Contacts cts[] = new Contacts[s];
		for(int i = 0; i<s;i++)
		{
			cts[i] = new Contacts(1234566, Internet.ONLINE
					,new Date().getTime(),Internet.READ,"无论这个句子有多长长长长长长长长长长长长长长都不会影响","赫魯曉夫愛玉米"
					,"苟利國家生死以");
			ctsList.add(cts[i]);
		}
		msgList.sort();
		JPanel ctsPanel = new JPanel();//.createVerticalBox();
		
		GridBagLayout LS = new GridBagLayout();
		GridBagConstraints LSCstrs = new GridBagConstraints();
		LSCstrs.gridheight = GridBagConstraints.PAGE_START;
		LSCstrs.gridwidth = GridBagConstraints.REMAINDER;//设置布局
		
		for(Convasation i:ctsList)
		{
			Box m = (Box) i.create();
			LS.setConstraints(m,LSCstrs);
			ctsPanel.add(m);
		}
		//msgL.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		
		ctsPanel.setLayout(LS);
		//msgL.setPreferredSize(getMinimumSize());
		
		right = new JScrollPane();
		JLabel ctsHead = new JLabel("所有联系人");
		searchContacts = new JMenuItem("搜索联系人");
		searchContacts.addActionListener(e->{
			//TODO 弹出搜索页面
		});
		
		JButton plus = new JButton("+");
		plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JPopupMenu pop = new JPopupMenu();
				pop.add(searchContacts);
				pop.show(plus,0,0);
			}
		});
		ctsHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		ctsHead.setFont(Fonts.MESSAGE_HEADER);
		
		right.setColumnHeaderView(ctsHead);//设置标题
		right.setViewportView(ctsPanel);//设置Viewport
		
		right.setCorner(JScrollPane.UPPER_RIGHT_CORNER,plus );
		right.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		right.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
	}

	public int deleteContacts() {// TODO:删除联系人
		return 0;
	}

	public int addContacts() {// TODO: 添加联系人
		return 0;
	}

	public int changeContactsRemark() {// TODO:更改联系人备注
		return 0;
	}

	public int preferenceSet(int statement) {// TODO:个性设置,参数:在线状态
		return 0;
	}
}
