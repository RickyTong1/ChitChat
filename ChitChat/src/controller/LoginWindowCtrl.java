package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import Constants.Window;
import Module.LoginWindow;
import Module.Popup;
import Restores.KeyKeep;
import Restores.KeyKeepOperate;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginWindowCtrl implements Initializable {
	/*绑定组件*/
	@FXML
	private TextField ID;//账号
	@FXML
	private PasswordField password;//密码框
	@FXML
	private Button loginButton;//登陆按钮
	@FXML
	private Button registerButton;//注册按钮
	@FXML
	private Button findButton;//找回密码按钮
	@FXML
	private Button changeButton;//修改密码按钮
	@FXML
	private CheckBox keepMyKey;//记住密码
	
	int userID;//用户账号
	String userPswd;//用户密码
	String IP_address;//本机ip地址
	Vector<KeyKeep> keysVtr = new Vector<KeyKeep>();//本机保存的密钥串
	File user_info;//用户保存的密码信息
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user_info = new File(Window.USER_INFO_URL);
		//TODO ip地址和文件用配置文件写.
		IP_address = "";
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(user_info));
			keysVtr = (Vector<KeyKeep>)in.readObject();
		}
		catch (FileNotFoundException e) {} //账号信息文件不存在时创造新文件
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "输出流异常.\n错误码:MOD_LOGIN_CSTRKTR","",JOptionPane.PLAIN_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "账号信息文件内容装载错误.\n错误码:MOD_LOGIN_CSTRKTR","",JOptionPane.PLAIN_MESSAGE);
		}
		keysVtr = KeyKeepOperate.unpack(keysVtr);
	}
	/*账号文本框监视器*/
	public void IDListener() {
		password.requestFocus();
	}
	/*密码框监视器*/
	public void passwordListener() {
		loginButton.requestFocus();
	}
	/*登陆按钮监视器*/
	public void loginListener() {
		//TODO
		userID = Integer.parseInt(ID.getText());//提取账号
		userPswd = password.getText();//提取密码
		/*登陆成功后关闭当前窗口*/
		Stage stage = (Stage)loginButton.getScene().getWindow();
		stage.close();
	}
	/*注册按钮监视器*/
	public void registerListener() {
		Popup PerDataWindow = new Popup("../View/RegisterWindow.fxml","注册窗口");
	}
	/*找回密码监视器*/
	public void findListener() {
		Popup findPasswordWindow = new Popup("../View/FindPasswordWindow.fxml","找回密码");
	}
	/*修改密码监视器*/
	public void changeListener() {
		Popup change  = new Popup("../View/PasswordEditWindow.fxml","修改密码");
	}
	//保存密码
	public void KeyKeepListener() {
		if(keepMyKey.isSelected()) {
			KeyKeep myKey = KeyKeepOperate.pack(ID.getText(), password.getText());
			keysVtr.add(myKey);
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
				out.writeObject(keysVtr);
				out.flush();
				out.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "账号信息文件不存在.","",JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "本地账号信息读取错误","",JOptionPane.PLAIN_MESSAGE);
			}
		}
		if(!keepMyKey.isSelected()){
			keysVtr.remove(KeyKeepOperate.pack(ID.getText(), password.getText()));
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
				out.writeObject(keysVtr);
				out.flush();
				out.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "账号信息文件不存在.","",JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "本地账号信息读取错误","",JOptionPane.PLAIN_MESSAGE);
			}	
		}
	}
	//实时账号-密码匹配
	public void docListener() {
		JPopupMenu ids = new JPopupMenu();
		for(KeyKeep i:keysVtr) {
			if(i.id.startsWith(ID.getText())&& !i.id.equals(ID.getText()))
			{
				JMenuItem it = new JMenuItem(i.id);
				ids.add(it);
				it.addActionListener(e->{
					ID.setText(i.id);
					password.setText(new String(i.keySecured));
					keepMyKey.setSelected(true);
				});
			}
			
		}
		ids.revalidate();
		
		ids.show(null,(int)ID.getLayoutX(),(int) ID.getHeight());
		
		ID.requestFocus();
		
//		validate();
	}
	
	
}
