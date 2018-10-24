package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import Constants.Window;
import DataBaseOperation.OperateSQLServer;

/*修改密码界面*/
public class PasswordEdit extends JFrame implements ActionListener{
	JTextField userID;					//用户账号
	JTextField originalPswd;			//原密码文本框
	JPasswordField newPswd;					//新密码密码框
	JPasswordField pswdConfirm;				//确认密码密码框
	JButton okButton;					//确认按钮
	JButton exitButton;					//退出按钮
	/*标签对象*/
	JLabel userID_label;				//账号标签
	JLabel originalPswd_label;			//原先密码标签
	JLabel newPswd_label;				//新密码标签
	JLabel pswdConfirm_label;			//确认新密码标签
	Box hbox1,hbox2;					//水平布局的盒子
	Box vbox_label,vbox_textField,vbox_buttom;		//竖直布局的盒子
	String tempID;						//临时保存账号
	String tempOriginalPswd;			//临时保存原始密码
	String tempNewPswd;					//临时保存新密码
	String tempPswdConfirm;				//临时保存确认密码
	PasswordEdit(){
		init();
		addComponent();
		addAction();
		setLayout(new FlowLayout());
		setVisible(true);
		setTitle("修改密码");		
		setLocation(Window.getMiddleWidth(300),Window.getMiddleHeight(350));
		setSize(300,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		/*组件初始化*/
		userID = new JTextField(10);
		originalPswd = new JTextField(10);
		newPswd = new JPasswordField(10);
		pswdConfirm = new JPasswordField(10);
		okButton = new JButton("确认");
		exitButton = new JButton("退出");
		userID_label = new JLabel("账号：");
		originalPswd_label = new JLabel("原密码：");
		newPswd_label = new JLabel("新密码：");
		pswdConfirm_label = new JLabel("确认密码：");
		/*竖直盒子初始化*/
		vbox_label = Box.createVerticalBox();
		vbox_textField = Box.createVerticalBox();
		vbox_buttom = Box.createVerticalBox();
		/*水平盒子初始化*/
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
	}
	void addComponent() {
		/*将标签加入到盒子中*/
		vbox_label.add(userID_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(originalPswd_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(newPswd_label);
		vbox_label.add(Box.createVerticalStrut(26));
		vbox_label.add(pswdConfirm_label);
		/*将文本框加入到盒子中*/
		vbox_textField.add(userID);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(originalPswd);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(newPswd);
		vbox_textField.add(Box.createVerticalStrut(15));
		vbox_textField.add(pswdConfirm);
		/*将按钮组件添加到盒子中*/
		hbox1.add(okButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(exitButton);
		/*盒子嵌套*/
		hbox2.add(vbox_label);
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(vbox_textField);
		
		vbox_buttom.add(Box.createVerticalStrut(20));
		vbox_buttom.add(hbox2);
		vbox_buttom.add(Box.createVerticalStrut(30));
		vbox_buttom.add(hbox1);
		add(vbox_buttom);
		}
	/*给各组件添加监视器方法*/
	void addAction() {
		userID.addActionListener(this);
		originalPswd.addActionListener(this);
		newPswd.addActionListener(this);
		pswdConfirm.addActionListener(this);
		okButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		tempID = userID.getText();
		tempOriginalPswd = originalPswd.getText();
		tempNewPswd = newPswd.getText();
		tempPswdConfirm = pswdConfirm.getText();
		if(tempID.equals("")) {
			userID.requestFocus();
			JOptionPane.showMessageDialog(null,"账号不能为空!");	
		}
		else if(e.getSource()==userID) {
			originalPswd.requestFocus();
		}
		else if(tempOriginalPswd.equals("")) {
			originalPswd.requestFocus();
			JOptionPane.showMessageDialog(null,"原始密码不能为空!");	
		}
		else if(e.getSource()==originalPswd) {
			newPswd.requestFocus();
		}
		else if(tempNewPswd.equals("")) {
			newPswd.requestFocus();
			JOptionPane.showMessageDialog(null,"新密码不能为空!");	
		}
		else if(e.getSource()==newPswd) {
			if(!tempNewPswd.equals(tempOriginalPswd))
				pswdConfirm.requestFocus();
			else {
				JOptionPane.showMessageDialog(null,"新密码不能和原密码一样!");
			}
		}
		else if(tempPswdConfirm.equals("")) {
			pswdConfirm.requestFocus();
			JOptionPane.showMessageDialog(null,"确认密码不能为空!");	
		}
		else if(e.getSource()==pswdConfirm) {
			if(!tempPswdConfirm.equals(tempNewPswd)) {
				JOptionPane.showMessageDialog(null,"两次密码输入不一致，请重新输入密码");
			}
			else {
				okButton.doClick();
			}
		}
		if(e.getSource()==okButton) {					
			OperateSQLServer oss = new OperateSQLServer();
			oss.connectToDatabase();
			ResultSet rs = oss.getPersonalInformation(Integer.parseInt(tempID));
			try {
				if(rs.next()) {
					if(rs.getString(3).equals(tempOriginalPswd)) {
						oss.updateUserPassword(Integer.parseInt(tempID), tempNewPswd);
					}
					else{
						originalPswd.requestFocus();
						JOptionPane.showMessageDialog(null,"原密码错误,请重新输入!");
					}
				}
				else {
					userID.requestFocus();
					JOptionPane.showMessageDialog(null,"用户不存在,请重新输入!");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			oss.closeDatabase();
		}
		if(e.getSource()==exitButton) {
			dispose();
		}
	}
}
