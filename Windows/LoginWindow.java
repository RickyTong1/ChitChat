package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*��½����LoginWindow*/
public class LoginWindow extends JFrame implements ActionListener{
	JButton loginButton;			//ע�ᰴť
	JButton registerButton;			//��½��ť
	JButton findMyKeyButton;		//�һ����밴ť
	JCheckBox keepMyKeyCheckBox;	//��ס���뵥ѡ��ť
	JButton changePassword;			//�޸����밴ť
	JLabel userLabel;				//�˺ű�ǩ
	JLabel keyLabel;			//�����ǩ	
	JMenu iserIDMenu;				//
	JTextField ID;					//�����ı���
	JPasswordField key;				//�����ı���
	
	Box hbox1,hbox2;				//ˮƽ����
	Box vbox1,vbox2,vbox3;				//��ֱ����
	LoginWindow(){
		init();
		setLayout(new FlowLayout());
		setVisible(true);			//���ÿɼ�
		setTitle("ChitChat");
		setBounds(500,250,350,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//add(new Imagecanvas());
	}
	/*��������ʼ�����*/
	void init() {	
		loginButton = new JButton("ע��");
		registerButton = new JButton("��½");
		changePassword = new JButton("�޸�����");
		findMyKeyButton = new JButton("�һ�����");
		keepMyKeyCheckBox = new JCheckBox("��ס����");
		userLabel = new JLabel("�˻���");
		keyLabel = new JLabel("���룺");
		/*Icon icon = new ImageIcon("C:\\Users\\Administrator\\eclipse-workspace\\ChitChat\\src\\Windows\\01.jpg");
		loginButton.setIcon(icon);
		loginButton.setComponentPopupMenu(null);*/
		ID = new JTextField(10);
		ID.setToolTipText("�������˺�");
		key = new JPasswordField(10);
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
		vbox1 = Box.createVerticalBox();	
		vbox2 = Box.createVerticalBox();
		vbox3 = Box.createVerticalBox();
		
		vbox1.add(userLabel);
		vbox1.add(Box.createVerticalStrut(30));
		vbox1.add(keyLabel);	
		
		vbox2.add(ID);
		vbox2.add(Box.createVerticalStrut(20));
		vbox2.add(key);
		
		hbox1.add(loginButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(registerButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(findMyKeyButton);	
		
		hbox2.add(vbox1);
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(vbox2);
		
		
		vbox3.add(Box.createVerticalStrut(50));
		vbox3.add(hbox2);
		vbox3.add(Box.createVerticalStrut(30));
		vbox3.add(keepMyKeyCheckBox);
		vbox3.add(Box.createVerticalStrut(30));
		vbox3.add(hbox1);
		add(vbox3);
		/*��Ӱ�ť����ļ�����*/
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		changePassword.addActionListener(this);
		findMyKeyButton.addActionListener(this);
		/*����ı���,�����ļ�����*/
		ID.addActionListener(this);
		key.addActionListener(this);
		/*��ӵ�ѡ��ť�ļ�����*/
		keepMyKeyCheckBox.addActionListener(this);
		LookAndFeel.addLookAndFeel(vbox3);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ID) {
			key.requestFocus();
		}
		if(e.getSource() == key) {
			changePassword.doClick();
		}
		/*���ע�ᰴť*/
		if(e.getSource()==loginButton) {
			RegisterWindow registerWindow = new RegisterWindow();
		}
		/*�����¼��ť*/
		if(e.getSource()==registerButton) {
			
		}
		/*����޸����밴ť*/
		if(e.getSource()==changePassword) {
			PasswordEdit passwordEdit = new PasswordEdit();
		}
		/*����һ����밴ť*/
		if(e.getSource()==findMyKeyButton) {
			FindPasswordWindow win = new FindPasswordWindow();			//����
		}
		/*ѡ�е�ѡ��ť*/
		if(e.getSource()==keepMyKeyCheckBox) {
			ChatWindow chatWindow = new ChatWindow();
			//PasswordEdit edit = new PasswordEdit();
			//FriendWindow friend = new FriendWindow();
			//PersonalData personal = new PersonalData();
			//RegisterWindow register = new RegisterWindow();
			//StrangerWindow stranger = new StrangerWindow();
		}
	}
	
}
