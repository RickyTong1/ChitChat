package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.*;
import Module.PerDataWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PerDataWindowCtrl implements Initializable{
	String nickName;//�ǳ�
	String email;//����
	String signat;//����ǩ��
	String sex;//�Ա�
	String year;//������
	String month;//������
	String day;//������
	@FXML
	private TextField userIDText;//�˺�
	@FXML
	private TextField nickNameText;//�ǳ�
	@FXML
	private TextField emailText;//����
	@FXML
	private TextArea signature;//����ǩ��
	@FXML
	private ComboBox sexComboBox;//�Ա�������
	@FXML
	private ComboBox yearComboBox;//���������
	@FXML
	private ComboBox monthComboBox;//�·�������
	@FXML
	private ComboBox dayComboBox;//�շ�������
	@FXML
	private Button vertifyButton;//ȷ�ϰ�ť
	@FXML
	private Button cancleButton;//ȡ����ť
	
	private int userID = PerDataWindow.userID;//��ȡ�û��˺�
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
		/*�����ݿ��ж�ȡ��Ϣ����ʾ*/
		//userIDText.setText(String.valueOf(userID));
		
	}
	/*�ǳƼ�����*/
	public void nickNameLis() {
		
	}
	/*�����ı��������*/
	public void emailLis() {
		
	}
	/*�޸İ�ť������*/
	public void vertifyLis() {
		/*��ȡ������Ϣ */
		nickName = nickNameText.getText().trim();
		email = emailText.getText().trim();
		signat = signature.getText().trim();
		sex = (String)sexComboBox.getValue();
		year = (String)yearComboBox.getValue();
		month = (String)monthComboBox.getValue();
		day = (String)dayComboBox.getValue();
		/*���õ�������*/
		ContextMenu pop = new ContextMenu();
		MenuItem nickNullWar = new MenuItem("�ǳƲ���Ϊ��");
		if(nickName.trim().compareTo("")==0) {
			pop.getItems().add(nickNullWar);
			pop.show(nickNameText,Side.BOTTOM, 0,0);
		}
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.SELF_PROFILE_UPDATE;
		message.senderIP = Property.Property.NATIVE_IP;
		message.senderID = MainWindow.ID;
		message.nickname = nickName;
		message.key = null;
		message.email = email;
		message.phoneNum = null;
		message.birth = year+"-"+month+"-"+day;
		message.gender = sex;
		message.style = signature.getText();
		new SendMessage(Property.Property.SERVER_IP
				,SocketConstants.GENERAL_PORT
				,MessageBlobOperator.pack(message));
		
		MainWindow.nicknameLabel = new JLabel(nickName);
		MainWindow.styleWord = new JLabel(signature.getText());
	}
	/*ȡ����ť������*/
	public void cancleLis() {
		/*�رյ�ǰ����*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	

}
