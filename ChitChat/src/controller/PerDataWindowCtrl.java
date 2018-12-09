package controller;

import java.net.URL;
import java.util.ResourceBundle;

import CComponents.MessageBlob;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Constants.*;
import Module.PerDataWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PerDataWindowCtrl implements Initializable{
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
	
	}
	/*�ǳƼ�����*/
	public void nickNameLis() {
		nickNameText.getText();
	}
	/*�����ı��������*/
	public void emailLis() {
		
	}
	/*ȷ�ϰ�ť������*/
	public void vertifyLis() {
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.SELF_PROFILE_UPDATE;
		message.senderIP = Property.Property.NATIVE_IP;
		message.senderID = MainWindow.ID;
		message.nickname = nickNameText.getText();
		message.key = null;
		message.email = emailText.getText();
		message.phoneNum = null;
		
		
		
	}
	/*ȡ����ť������*/
	public void cancleLis() {
		/*�رյ�ǰ����*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	

}
