package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
/*登陆窗口LoginWindow*/
public class LoginWindow extends JFrame implements ActionListener{
	JButton loginButton;			//注册按钮
	JButton registerButton;			//登陆按钮
	JButton findMyKeyButton;		//找回密码按钮
	JCheckBox keepMyKeyCheckBox;	//记住密码单选按钮
	JButton changePassword;			//修改密码按钮
	JLabel userLabel;				//账号标签
	JLabel keyLabel;			//密码标签	
	JMenu iserIDMenu;				//
	JTextField ID;					//密码文本框
	JPasswordField key;				//密码文本框
	
	Box hbox1,hbox2;				//水平盒子
	Box vbox1,vbox2,vbox3;				//竖直盒子
	LoginWindow(){
		init();
		setLayout(new FlowLayout());
		setVisible(true);			//设置可见
		setTitle("ChitChat");
		setBounds(500,250,350,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//add(new Imagecanvas());
	}
	/*方法：初始化组件*/
	void init() {	
		loginButton = new JButton("注册");
		registerButton = new JButton("登陆");
		changePassword = new JButton("修改密码");
		findMyKeyButton = new JButton("找回密码");
		keepMyKeyCheckBox = new JCheckBox("记住密码");
		userLabel = new JLabel("账户：");
		keyLabel = new JLabel("密码：");
		/*Icon icon = new ImageIcon("C:\\Users\\Administrator\\eclipse-workspace\\ChitChat\\src\\Windows\\01.jpg");
		loginButton.setIcon(icon);
		loginButton.setComponentPopupMenu(null);*/
		ID = new JTextField(10);
		ID.setToolTipText("请输入账号");
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
		/*添加按钮组件的监视器*/
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		changePassword.addActionListener(this);
		findMyKeyButton.addActionListener(this);
		/*添加文本框,密码框的监视器*/
		ID.addActionListener(this);
		key.addActionListener(this);
		/*添加单选按钮的监视器*/
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
		/*点击注册按钮*/
		if(e.getSource()==loginButton) {
			RegisterWindow registerWindow = new RegisterWindow();
		}
		/*点击登录按钮*/
		if(e.getSource()==registerButton) {
			
		}
		/*点击修改密码按钮*/
		if(e.getSource()==changePassword) {
			PasswordEdit passwordEdit = new PasswordEdit();
		}
		/*点击找回密码按钮*/
		if(e.getSource()==findMyKeyButton) {
			FindPasswordWindow win = new FindPasswordWindow();			//测试
		}
		/*选中单选按钮*/
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
