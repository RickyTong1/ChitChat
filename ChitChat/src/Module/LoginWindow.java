package Module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
/*
 * @Auther RickyTong
 * 
 * 
 * */
import java.util.Vector;

import javax.swing.JOptionPane;

import Constants.Window;
import Restores.KeyKeep;
import Restores.KeyKeepOperate;

public class LoginWindow{

	String IP_address;//本机ip地址
	Vector<KeyKeep> keysVtr = new Vector<KeyKeep>();//本机保存的密钥串
	File user_info;//用户保存的密码信息
	public String url = "../View/LoginWindow.fxml";
	
	public LoginWindow() {
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


	
}
