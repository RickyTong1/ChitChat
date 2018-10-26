package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Client.MainWindow;
import Constants.*;
import DataBaseOperation.OperateSQLServer;
import Restores.KeyKeep;
import Restores.KeyKeepOperate;
import Server.SendThread;

/*登陆窗口LoginWindow*/
public class LoginWindow extends JFrame {
	JButton loginButton;			//登陆按钮
	JButton registerButton;			//注册按钮
	JButton findMyKeyButton;		//找回密码按钮
	JCheckBox keepMyKeyCheckBox;	//记住密码单选按钮
	JButton changePassword;			//修改密码按钮
	JLabel userLabel;				//账号标签
	JLabel keyLabel;			//密码标签	
	JMenu iserIDMenu;				//
	JTextField ID;					//密码文本框
	JPasswordField key;				//密码文本框
	
	public static String IP_address;
	Box hbox1,hbox2;				//水平盒子
	Box vbox1,vbox2,vbox3;				//竖直盒子
	File user_info;
	Vector<KeyKeep> keys = new Vector<KeyKeep>();
	public LoginWindow(){
		try {
			user_info = new File(Window.USER_INFO_URL);
			IP_address = InetAddress.getLocalHost().getHostAddress();
			System.out.println(IP_address);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "请检查网络连接!","",JOptionPane.PLAIN_MESSAGE);	
		}
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(user_info));
			keys = (Vector<KeyKeep>)in.readObject();
			in.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "账号信息文件不存在.","",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "本地账号信息文件读取错误.","",JOptionPane.PLAIN_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "账号信息装载错误.","",JOptionPane.PLAIN_MESSAGE);
		}
		Vector<KeyKeep> temp = new Vector<KeyKeep>();
		for(KeyKeep i:keys) {
			temp.add(KeyKeepOperate.unpack(i));
		}
		keys = temp;
		
		init();
		setLayout(new FlowLayout());
		setVisible(true);			//设置可见
		setTitle("ChitChat");
		setBounds(Window.getMiddleWidth(350),Window.getMiddleHeight(350),350,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//add(new Imagecanvas());
	}
	/*方法：初始化组件*/
	void init() {	
		loginButton = new JButton("登录");
		registerButton = new JButton("注册");
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
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(changePassword);
		
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
		Acts act = new Acts();
		Docs doc = new Docs();
		loginButton.addActionListener(act);
		registerButton.addActionListener(act);
		changePassword.addActionListener(act);
		findMyKeyButton.addActionListener(act);
		/*添加文本框,密码框的监视器*/
		ID.addActionListener(act);
		ID.getDocument().addDocumentListener(doc);
		key.addActionListener(act);
		/*添加单选按钮的监视器*/
		keepMyKeyCheckBox.addActionListener(act);
		LookAndFeel.addLookAndFeel(vbox3);
	}
	class Acts implements ActionListener{

		int turnTo(ActionEvent e) {
			if(e.getSource()==ID)return 1;
			if(e.getSource() == key) return 2;
			if(e.getSource()==loginButton)return 3;
			if(e.getSource()==registerButton)return 4;
			if(e.getSource()==changePassword)return 5;
			if(e.getSource()==findMyKeyButton)return 6;
			if(e.getSource()==keepMyKeyCheckBox)return 7;
			else return -1;
			
		}
		boolean checkRepetitive(String id) {//检查输入的id是否已经存在于keys中.
			for(KeyKeep i : keys) {
				if(i.id.equals(id))return true;
			}
			return false;
		}
		boolean warning() {//账号格式以及密码是否为空检查
			if(!ID.getText().matches("[0-9]+"))
			{
				JOptionPane.showMessageDialog(null, "请注意账号的格式(全由数字组成,不能为空)!","",JOptionPane.PLAIN_MESSAGE);
				return true;
			}
			if(key.getPassword().length == 0)
			{
				JOptionPane.showMessageDialog(null, "请输入密码!","",JOptionPane.PLAIN_MESSAGE);
				return true;
			}
			return false;
				
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int timeCircle = 20000;//循环接收的 上限,超出次数提示连接超时.
			
			
			JPopupMenu wr = new JPopupMenu();
			JLabel idNull = new JLabel("账号格式不对哦!");
			JLabel keyNull = new JLabel("请输入密码!");
			
			switch(turnTo(arg0)) {
			case 1:{//ID
				
				if(!ID.getText().matches("[0-9]+"))
				{
					wr.add(idNull);
					wr.show(ID, 0,0);
					return;
				}
				key.requestFocus();
			}break;
			
			case 2:{//key
				if(key.getPassword().length == 0)
				{
					wr.add(keyNull);
					wr.show(key, 0, 0);
					return;
				}
			}//没有break
			case 3:{//loginButton
				if(warning())return;//账号格式以及密码是否为空检查
				OperateSQLServer oprt = new OperateSQLServer();
				oprt.connectToDatabase();
				ResultSet result = oprt.getPersonalInformation(Integer.parseInt(ID.getText()));

				try {
					result.next();
					String keyGot = result.getString(3);//密码
					if(keyGot.equals(key.getText()))
					{	
						dispose();
						new MainWindow(Integer.parseInt(ID.getText()));
						byte[] info;
						info = ("2#"+ID.getText()+"#"+ChatWindow.getNetworkTime()+"#"+IP_address).getBytes();
						
						SendThread send = new SendThread("192.168.43.29",23334,info);
						new Thread(send).start();
						oprt.updateLoggingStatus(1, Integer.parseInt(ID.getText()));
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "密码错误!","",JOptionPane.PLAIN_MESSAGE);
						return;
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "不存在该用户!","",JOptionPane.PLAIN_MESSAGE);
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "远程服务器IP错误!","",JOptionPane.PLAIN_MESSAGE);
				}
				oprt.closeDatabase();
			}break;
			case 4:{//RegisterWindow
				new RegisterWindow();
			}break;
			case 5:{//changePassword
				 new PasswordEdit();
			}break;
			case 6:{//findkey
				new FindPasswordWindow();
			}break;
			case 7:{//keepMyKeyCheckBox
				if(warning())return ;//账号格式以及密码是否为空检查				
				if(checkRepetitive(ID.getText()))return;//检查输入的id是否已经存在于keys中.
				if(keepMyKeyCheckBox.isSelected())
				{
					KeyKeep myKey = KeyKeepOperate.pack(ID.getText(), key.getText());
					keys.add(myKey);
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
						out.writeObject(keys);
						out.flush();
						out.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "账号信息文件不存在.","",JOptionPane.PLAIN_MESSAGE);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "本地账号信息读取错误","",JOptionPane.PLAIN_MESSAGE);
					}
				}
				if(!keepMyKeyCheckBox.isSelected())
				{
					keys.remove(KeyKeepOperate.pack(ID.getText(), key.getText()));
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
						out.writeObject(keys);
						out.flush();
						out.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "账号信息文件不存在.","",JOptionPane.PLAIN_MESSAGE);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "本地账号信息读取错误","",JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			}break;
			
			default: 
				
			}//switch
		}//override
		
	}//class
	class Docs implements DocumentListener{

		//boolean flag_for_ids_is_visible = true;
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			JPopupMenu ids = new JPopupMenu();
			for(KeyKeep i:keys) {
				if(i.id.startsWith(ID.getText())&& !i.id.equals(ID.getText()))
				{
					JMenuItem it = new JMenuItem(i.id);
					ids.add(it);
					it.addActionListener(e->{
						ID.setText(i.id);
						key.setText(new String(i.keySecured));
						keepMyKeyCheckBox.setSelected(true);
					});
				}
				
			}
			ids.revalidate();
			ids.show(ID,0,ID.getHeight());
			ID.requestFocus();
			
//			validate();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			changedUpdate(arg0);
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			changedUpdate(arg0);
		}
		
	}
}