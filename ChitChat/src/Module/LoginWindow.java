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

	String IP_address;//����ip��ַ
	Vector<KeyKeep> keysVtr = new Vector<KeyKeep>();//�����������Կ��
	File user_info;//�û������������Ϣ
	public String url = "../View/LoginWindow.fxml";
	
	public LoginWindow() {
		user_info = new File(Window.USER_INFO_URL);
		//TODO ip��ַ���ļ��������ļ�д.
		IP_address = "";
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(user_info));
			keysVtr = (Vector<KeyKeep>)in.readObject();
		}
		catch (FileNotFoundException e) {} //�˺���Ϣ�ļ�������ʱ�������ļ�
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "������쳣.\n������:MOD_LOGIN_CSTRKTR","",JOptionPane.PLAIN_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�����װ�ش���.\n������:MOD_LOGIN_CSTRKTR","",JOptionPane.PLAIN_MESSAGE);
		}
		keysVtr = KeyKeepOperate.unpack(keysVtr);
	}


	
}
