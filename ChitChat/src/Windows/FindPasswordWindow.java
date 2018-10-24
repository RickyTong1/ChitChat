package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Constants.Window;
/*找回密码界面*/
public class FindPasswordWindow extends JFrame implements ActionListener{
	JTextField userID;					//用户账号
	JTextField emailTextField;		//邮箱文本框
	JTextField codeText;			//验证码文本框
	JButton send;					//发送按钮
	JPasswordField password;					//密码密码框
	JPasswordField passwordConfirm;				//确认密码密码框
	JButton okButton;					//确认按钮
	JButton exitButton;					//退出按钮
	/*标签对象*/
	JLabel userID_label;				//账号标签
	JLabel codeLabel;				//验证码标签
	JLabel emailLabel;					//邮箱标签
	JLabel password_label;				//密码标签
	JLabel passwordConfirm_label;			//确认密码标签
	Box hbox1,hbox2;					//水平布局的盒子
	Box emailBox;					//邮箱盒子
	Box vbox_label,vbox_textField,vbox_buttom;		//竖直布局的盒子
	FindPasswordWindow(){					//构造方法
		init();							//调用初始化方法
		addComponent();					//调用添加组件方法
		addListener();					//调用添加监视器方法
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("找回密码");		
		setLocation(Window.getMiddleWidth(300),Window.getMiddleHeight(350));
		setSize(300,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		/*组件初始化*/
		userID = new JTextField(10);
		emailTextField = new JTextField(10);
		codeText = new JTextField(10);
		codeText.setToolTipText("请输入收到的验证码");
		password = new JPasswordField(10);
		passwordConfirm = new JPasswordField(10);
		okButton = new JButton("确认");
		exitButton = new JButton("退出");
		send = new JButton("发送");
		userID_label = new JLabel("账号：");
		password_label = new JLabel("原密码：");//TODO ????
		passwordConfirm_label = new JLabel("确认密码：");
		emailLabel = new JLabel("邮箱：");
		codeLabel = new JLabel("验证码：");
		/*竖直盒子初始化*/
		vbox_label = Box.createVerticalBox();
		vbox_textField = Box.createVerticalBox();
		vbox_buttom = Box.createVerticalBox();
		/*水平盒子初始化*/
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
		emailBox = Box.createHorizontalBox();
	}
	/*添加组件方法*/
	void addComponent() {
		/*将标签加入到盒子中*/
		vbox_label.add(userID_label);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(emailLabel);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(codeLabel);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(password_label);
		vbox_label.add(Box.createVerticalStrut(27));
		vbox_label.add(passwordConfirm_label);
		
		/*将文本框加入到盒子中*/
		vbox_textField.add(userID);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(emailBox);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(codeText);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(password);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(passwordConfirm);
		
		/*将按钮组件添加到盒子中*/
		hbox1.add(okButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(exitButton);
		/*盒子嵌套*/
		emailBox.add(emailTextField);
		emailBox.add(Box.createHorizontalStrut(10));
		emailBox.add(send);
		hbox2.add(vbox_label);
		hbox2.add(Box.createHorizontalStrut(5));
		hbox2.add(vbox_textField);
		
		vbox_buttom.add(Box.createVerticalStrut(20));
		vbox_buttom.add(hbox2);
		vbox_buttom.add(Box.createVerticalStrut(30));
		vbox_buttom.add(hbox1);
		add(vbox_buttom);
	}
	/*给组件添加监视器方法*/
	void addListener() {
		userID.addActionListener(this);
		emailTextField.addActionListener(this);
		codeText.addActionListener(this);
		password.addActionListener(this);
		passwordConfirm.addActionListener(this);
		okButton.addActionListener(this);
		exitButton.addActionListener(this);
		send.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==userID) {					//账号文本框
			emailTextField.requestFocus();
		}
		if(e.getSource()==emailTextField) {			//邮箱文本框
			codeText.requestFocus();
		}
		if(e.getSource()==codeText) {				//验证码文本框
			password.requestFocus();
		}				
		if(e.getSource()==password) {				//密码密码框
			passwordConfirm.requestFocus();
		}
		if(e.getSource()==passwordConfirm) {		//确认密码密码框
			if(!passwordConfirm.getText().equals(password.getText()))
				JOptionPane.showMessageDialog(null,"两次密码输入不一致，请重新输入密码");
		}
		if(e.getSource()==okButton) {				//确定按钮
			
		}
		if(e.getSource()==exitButton) {				//退出按钮
			dispose();
		}
		if(e.getSource()==send) {					//发送按钮
			//TODO
		}
	}
}