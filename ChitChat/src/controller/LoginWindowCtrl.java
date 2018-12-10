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

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import CComponents.MessageReceive;
import Client.SendMessage;
import Constants.SocketConstants;
import Restores.KeyKeep;
//import Restores.KeyKeep;
import Restores.KeyKeepOperate;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.Popup;

public class LoginWindowCtrl implements Initializable {
	/*�����*/
	@FXML
	private TextField ID;//�˺�
	@FXML
	private PasswordField password;//�����
	@FXML
	private Button loginButton;//��½��ť
	@FXML
	private Button registerButton;//ע�ᰴť
	@FXML
	private Button findButton;//�һ����밴ť
	@FXML
	private Button changeButton;//�޸����밴ť
	@FXML
	private CheckBox keepMyKey;//��ס����
	
	public static int userID;//�û��˺�
	String userPswd;//�û�����
	Vector<KeyKeep> keysVtr = new Vector<KeyKeep>();//�����������Կ��
	File user_info;//�û������������Ϣ
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user_info = new File(Property.Property.USER_INFO_URL);
		//TODO ip��ַ���ļ��������ļ�д.
		MessageReceive s = MessageReceive.getInstance();
		
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
	/*�˺��ı��������*/
	public void IDListener() {
		userID = Integer.parseInt(ID.getText());
		password.requestFocus();	
	}
	/*����������*/
	public void passwordListener() {
		loginButton.requestFocus();
	}
	/*��½��ť������*/
	public void loginListener() {
		/*���õ����˵���ʾ*/
		ContextMenu pop = new ContextMenu();
		MenuItem IDNullWar = new MenuItem("�˺Ų���Ϊ��");
		MenuItem pswdNullWar = new MenuItem("���벻��Ϊ��");
		MenuItem IDError = new MenuItem("�˺Ų�����");
		MenuItem pswdError = new MenuItem("�������");
		/*�˺�Ϊ��*/
		if(ID.getText().trim().compareTo("")==0) {
			pop.getItems().add(IDNullWar);//�˺�Ϊ��
			pop.show(ID, Side.BOTTOM,0,0);
		}
		else if(password.getText().trim().compareTo("")==0) {
			pop.getItems().add(pswdNullWar);//�˺�Ϊ��
			pop.show(password, Side.BOTTOM,0,0);
		}
		else if(ID.getText().compareTo("")!=0&&password.getText().compareTo("")!=0) {
			userID = Integer.parseInt(ID.getText().trim());//��ȡ�˺�
			userPswd = password.getText().trim();//��ȡ����
			// ��������ƥ�䣬ƥ�䲻�ɹ�������������˺Ų����ڵ������û�������
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.LOGIN;
			message.senderIP = Property.Property.NATIVE_IP;
			message.senderID = Integer.parseInt(ID.getText());
			message.key = password.getText();
			message.onlineState = 0;
			
			new SendMessage(Property.Property.SERVER_IP
					,SocketConstants.GENERAL_PORT
					,MessageBlobOperator.pack(message));
			/*��½�ɹ���رյ�ǰ����*/
			Stage stage = (Stage)loginButton.getScene().getWindow();
			stage.close();
		}
	}
	/*ע�ᰴť������*/
	public void registerListener() {
		Popup PerDataWindow = new Popup("../View/RegisterWindow.fxml","ע�ᴰ��");
	}
	/*�һ����������*/
	public void findListener() {
		Popup findPasswordWindow = new Popup("../View/FindPasswordWindow.fxml","�һ�����");
	}
	/*�޸����������*/
	public void changeListener() {
		Popup change  = new Popup("../View/PasswordEditWindow.fxml","�޸�����");
	}
	//��������
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
				JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�������.","",JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "�����˺���Ϣ��ȡ����","",JOptionPane.PLAIN_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�������.","",JOptionPane.PLAIN_MESSAGE);
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "�����˺���Ϣ��ȡ����","",JOptionPane.PLAIN_MESSAGE);
			}	
		}
	}
	//ʵʱ�˺�-����ƥ��
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
