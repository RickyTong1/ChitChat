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

	Image image;// �û�ͷ��
	JLabel nicknameLabel;// �û��ǳ�
	JLabel styleWord;// �û��ĸ���ǩ��
	JSplitPane split;// �зִ���,���Ϊ��ϵ��,�ұ�����Ϣ
	JMenuBar operats;// ���Ͻǲ˵���
	JMenu menu;// �˵�
	JMenuItem searchContacts;// ��������
	JMenuItem createContactsGroup;// ��������
	static ConvList<Convasation> msgList;//��Ϣ�б�
	static ConvList<Convasation> ctsList;//��ϵ���б�
	JScrollPane right;// ��ҳ���Ҳ��
	JSplitPane left;// ��ҳ�����Ŀ�
	JScrollPane messages;// ��Ϣ�б�����
	
	static JPanel msgPanel = new JPanel();
	static JPanel ctsPanel = new JPanel();

	MainWindow() {
		setLayout(new FlowLayout());
		init();
		setBounds(400, 200, Constants.MAIN_WINDOW_WIDTH, Constants.MAIN_WINDOW_HEIGHT);
		setVisible(true);
	}

	/*
	 * init()��������persInfoInit(),messageInit()��ContactsInit()������ʼ����ҳ��Ļ�����Ϣ
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void init() {
			try {
				
				msgList = new ConvList(Constants.CONVLIST_MESSAGE);
				ctsList = new ConvList(Constants.CONVLIST_CONTACTS);
						} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"",JOptionPane.PLAIN_MESSAGE);
			}
			
		persInfoInit();// ��Ϣ��ʼ��

		messageInit();// ������Ϣ��ʼ��
		//��messageInit()�������ѽ��O���^bottomComponrnt.
		
		left.setDividerLocation(Constants.MAIN_WINDOW_USER_BLOCK_HEIGHT);// ���ø�����Ϣ���붥���ľ���
		left.setDividerSize(2);// ���÷ָ��߿��
		left.setEnabled(false);// �ָ��߲����ƶ�
		
		ContactsInit();// ��ϵ���б��ʼ��

		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true/* �Ƿ���������ػ� */, left
				,right);
		split.setDividerLocation(Constants.MESSAGE_PANEL_WIDTH);
		split.setDividerSize(2);
		split.setEnabled(true);// ��left(JSplitPane)��ͬ.

		setContentPane(split);
		this.setResizable(true);// ���ɸ��Ĵ�С//TODO 

	}

	public void persInfoInit() {// TODO:�û���Ϣ��ʼ��

		String nickname = "��³��������";// TODO ��
		String style = "��ð�������.";
		
		Icon onlineImage;
		JLabel onlineState = null;
		try {
			onlineImage = new ImageIcon(ImageIO.read(
					new File("C:\\Users\\Ricky\\eclipse-workspace\\ChitChat\\image\\Online.png")));
			onlineState = new JLabel(onlineImage);
			onlineState.setPreferredSize(new Dimension(Constants.CONTACTS_ONLINESTATE_WIDTH
					,Constants.CONTACTS_ONLINESTATE_WIDTH));
		} catch (IOException e) {
			System.out.println("�ļ�������.");
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

	public void messageInit() {// TODO: ��Ϣ��ʼ��
		
		//TODO ��ȡ��Ϣ�б�
		//����Ϊ��������.
		int s = 9;
		Message[] mes = new Message[s];
		msgList.add(new Message(1234566, Internet.ONLINE
				,new Date().getTime(),Internet.UNREAD,"������������ж೤��������������������������������Ӱ��","�����Է������"
				,"��������������"));
		for(int i = 0; i < s; i++)
		{
			mes[i] = new Message(1234566, Internet.ONLINE
					,new Date().getTime(),Internet.READ,"������������ж೤��������������������������������Ӱ��","�����Է������"
					,"��������������");
			msgList.add(new Message(1234566, Internet.ONLINE
					,new Date().getTime(),Internet.UNREAD,"������������ж೤��������������������������������Ӱ��","�����Է������"
					,"��������������"));
			msgList.add(mes[i]);
		}
		//�@�ǜyԇ����.
		
		msgList.sort();
		
		GridBagLayout mS = new GridBagLayout();
		GridBagConstraints mSCstrs = new GridBagConstraints();
		mSCstrs.fill = GridBagConstraints.HORIZONTAL;//GirdBag����
		mSCstrs.anchor = GridBagConstraints.NORTH;
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
		messages = new JScrollPane();
		JLabel msgHead = new JLabel("��Ϣ");
		msgHead.setBorder(BorderFactory.createLineBorder(Colors.MESSAGE_BORDER_COLOR));
		msgHead.setFont(Fonts.MESSAGE_HEADER);
		
		messages.setAlignmentY(Component.TOP_ALIGNMENT);
		messages.setColumnHeaderView(msgHead);//���ñ���
		messages.setViewportView(msgPanel);//����Viewport
		
//		JScrollBar msgScrollBar = messages.getVerticalScrollBar();
//		msgScrollBar.setValue(msgScrollBar.getMaximum());//���ø�scrollpane�Ĺ�����
		
		messages.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		left.setBottomComponent(messages);
		
	}

	public void ContactsInit() {// TODO:��ϵ���б��ʼ��
		
		//TODO ��ȡ��ϵ���б�,����Ϊ��������
		int s = 8;
		Contacts cts[] = new Contacts[s];
		for(int i = 0; i<s;i++)
		{
			cts[i] = new Contacts(1234566, Internet.ONLINE
					,new Date().getTime(),Internet.READ,"������������ж೤��������������������������������Ӱ��","�����Է������"
					,"��������������");
			ctsList.add(cts[i]);
		}
		msgList.sort();
		
		GridBagLayout LS = new GridBagLayout();
		GridBagConstraints LSCstrs = new GridBagConstraints();
		LSCstrs.gridheight = GridBagConstraints.PAGE_START;
		LSCstrs.gridwidth = GridBagConstraints.REMAINDER;//���ò���
		
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
		JLabel ctsHead = new JLabel("������ϵ��");
		searchContacts = new JMenuItem("������ϵ��");
		searchContacts.addActionListener(e->{
			//TODO ��������ҳ��
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
		
		right.setColumnHeaderView(ctsHead);//���ñ���
		right.setViewportView(ctsPanel);//����Viewport
		
		right.setCorner(JScrollPane.UPPER_RIGHT_CORNER,plus );
		right.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		right.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
	}

	public static void deleteMessage(int ID,Box elem) {
		msgList.remove(ID);
		msgList.sort();
		msgPanel.remove(elem);
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();//������ʾ
		//MainWindow.messages.repaint();
	}
	public static void readMessage(int ID) {
		
		MainWindow.msgList.sort();
		MainWindow.msgPanel.repaint();
		MainWindow.msgPanel.revalidate();
	}
	public static boolean deleteContacts(int ID,Box elem) {// TODO:ɾ����ϵ��
		
		ctsList.remove(ID);
		ctsList.sort();
		MainWindow.ctsPanel.remove(elem);
		MainWindow.ctsPanel.revalidate();
		MainWindow.ctsPanel.repaint();
		MainWindow.ctsPanel.revalidate();//������ʾ
		return true;
		
		
	}

	public static boolean addContacts(int ID) {// TODO: �����ϵ��
		return true;
	}

	public boolean changeContactsRemark(int ID) {// TODO:������ϵ�˱�ע
		return true;
	}

	public static boolean preferenceSet(int statement) {// TODO:��������,����:����״̬
		return true;
	}
}
