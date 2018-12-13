package controller;
import Constants.*;
import Property.Property;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.SendMessage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.LocalData;

public class RegisterWindowCtrl implements Initializable {
	/*�����*/
	@FXML
	private ComboBox sexComboBox;//�Ա�������
	@FXML
	private ComboBox year;//���������
	@FXML
	private ComboBox month;//�·�������
	@FXML
	private ComboBox day;//�շ�������
	@FXML
	private TextField nickNameTextField;//�ǳ��ı���
	@FXML
	private TextField emailTextField;//���������
	@FXML
	private PasswordField pswdPswdField;//�����
	@FXML
	private PasswordField vertifyPswdField;//ȷ�������
	@FXML
	private Button registerButton;//ע�ᰴť
	@FXML
	private Button exitButton;//�˳���ť

	String nickName;//�ǳ�
	String email;//��������
	String password;//����
	String vertify;//ȷ�ϵ�����
	String sex;//�Ա�
	String birthdayYear;//�������
	String birthdayMonth;//�����·�
	String birthdayDay;//�����շ�
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// nothing here.Don't need!!!
		/*�����Ա�������*/
		ObservableList<String> sexOptions = FXCollections.observableArrayList("��","Ů");
		sexComboBox.setItems(sexOptions);
		sexComboBox.setValue("��");
		/*Ĭ�ϳ���������*/
		year.setValue("1999");	
		month.setValue("1");	
		day.setValue("1");
	}
	/*�ǳ��ı��������*/
	public void nickListener(ActionEvent nick) {
		emailTextField.requestFocus();
	}
	/*����ʱ����Ҫ��*/
	public void sexListener(ActionEvent sex) {		
		
	}
	/*��������������*/
	public void emailListener(ActionEvent email) {
		pswdPswdField.requestFocus();
	}
	/*����������*/
	public void passwordListener(ActionEvent password) {
		vertifyPswdField.requestFocus();
	}
	/*ȷ������������*/
	public void vertifyListener(ActionEvent vertify) {
		registerButton.requestFocus();
	}
	/*ע�ᰴť������*/
	public void registerListener(ActionEvent register) {
		nickName = nickNameTextField.getText();//��ȡ�ǳ��ı�������
		sex = (String) sexComboBox.getValue();//��ȡ�Ա�����������
		email = emailTextField.getText();//��ȡ�����ı�������
		password = pswdPswdField.getText();//��ȡ��������
		vertify = vertifyPswdField.getText();//��ȡȷ����������
		birthdayYear = (String) year.getValue();//��ȡ�������������
		birthdayMonth = (String)month.getValue();//��ȡ�·�����������
		birthdayDay = (String)day.getValue();//��ȡ�շ�����������
		/*���õ����˵���ʾ*/
		ContextMenu pop = new ContextMenu();
		MenuItem IDNullWar = new MenuItem("�˺Ų���Ϊ��");//�˺ſվ���
		MenuItem pswdNullWar = new MenuItem("���벻��Ϊ��");//����վ���
		if(nickName.trim().compareTo("")==0) {
			pop.getItems().add(IDNullWar);//�˺�Ϊ��
			pop.show(nickNameTextField, Side.BOTTOM, 0,0);
		}
		else if(password.trim().compareTo("")==0) {
			pop.getItems().add(pswdNullWar);//����Ϊ��
			pop.show(pswdPswdField, Side.BOTTOM, 0,0);
		}
	
		else if(password.trim().compareTo(vertify.trim())!=0) {
			JOptionPane.showMessageDialog(null, "�������벻һ�£�������ȷ��");//��������Ƚ�ʧ��
		}
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.REGISTER;
		message.senderIP = Property.NATIVE_IP;
		message.nickname = nickName;
		message.key = password;
		message.email = email;
		message.phoneNum = null;
		message.birth = birthdayYear+"-"+birthdayMonth
				+"-"+birthdayDay;
		message.gender = sex;
		message.style = null;
		System.out.println(message.senderIP+"in SendMessage");
		new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

	}
	/*�˳���ť������*/
	public void exitListener(ActionEvent exit) {
		/*�رյ�ǰ����*/
		Stage stage = (Stage)exitButton.getScene().getWindow();
		stage.close();
	}
	/*����������������¼�*/
	public void yearItemListener() {
		int birthdayYear=LocalData.NOW_YEAR;	
		String year1[] = new String[100];//���
		/*�������������*/
		for(int i = 0;i<100;i++) {
			year1[i] = new String(""+birthdayYear+"");
			birthdayYear--;
		}
		ObservableList<String> yearOptions = FXCollections.observableArrayList(year1);
		year.setItems(yearOptions);
	}
	/*�·��������������¼�*/
	public void monthItemListener() {
		String month1[] = null;
		int birthdayMonth=12;
		if(Integer.parseInt((String)year.getValue())!= LocalData.NOW_YEAR) {
			month1 = new String[12];//�·�
			for(int i=0;i<12;i++) {
				month1[i] = new String(""+birthdayMonth+"");
				birthdayMonth--;
			}
		}
		if(Integer.parseInt((String)year.getValue()) == LocalData.NOW_YEAR){
			month1 = new String[LocalData.NOW_MONTH];//�·�
			birthdayMonth = LocalData.NOW_MONTH;
			/*�����·�������*/
			for(int i=0;i<LocalData.NOW_MONTH;i++) {
				month1[i] = new String(""+birthdayMonth+"");
				birthdayMonth--;
			}
		}		
		ObservableList<String> monthOptions = FXCollections.observableArrayList(month1);
		month.setItems(monthOptions);
	}
	/*�շ��������������¼�*/
	public void dayItemListener() {
		int birthdayDay = 31;//����
		int leapJudge=Integer.parseInt((String)year.getValue());//�ж��Ƿ�������
		int chioce=Integer.parseInt((String)month.getValue());//��ȡ�·����������ݣ�����switch case���
		switch(chioce){
		case 2: birthdayDay = 28;break;
		case 4:
		case 6:
		case 9:
		case 11:birthdayDay = 30;
				break;
			//default :break;		
		}
		if((leapJudge%4==0 && leapJudge%100!=0 || leapJudge%400==0) && chioce==2) {
			birthdayDay = 29;
		}
		/*��ǰ���*/
		if(leapJudge == LocalData.NOW_YEAR && chioce == LocalData.NOW_MONTH) {
			birthdayDay = LocalData.NOW_DAY;//
		}
		String day1[] = new String[birthdayDay];//����		
		/*��������������*/
		for(int i=0;birthdayDay>0;i++) {
			day1[i] = new String(""+birthdayDay+"");
			birthdayDay--;
		}
		ObservableList<String> dayOptions = FXCollections.observableArrayList(day1);
		day.setItems(dayOptions);//��������������
	}	
}
