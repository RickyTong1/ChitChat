package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PswEditWindowCtrl  implements Initializable{
	/*�����*/
	@FXML
	private TextField userIDText;//�˺�
	@FXML
	private TextField oldPwsdText;//ԭ����
	@FXML
	private TextField newPswdText;//������
	@FXML
	private TextField vertifyPswd;//ȷ������
	@FXML
	private Button vertifyButton;//ȷ�ϰ�ť
	@FXML
	private Button cancleButton;//ȡ����ť
	
	int ID;
	String oldPswd;
	String newPswd;
	String vertify;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/*�˺��ı��������*/
	public void userIDListener() {
		
	}
	/*ԭ�����ı��������*/
	public void oldPswListener() {
		
	}
	/*�������ı��������*/
	public void newPswListener() {
		
	}
	/*ȷ�������ı��������*/
	public void vertifyPswListener() {
		
	}
	/*ȷ�ϰ�ť������*/
	public void vertifyButtonListener() {
		ID = Integer.parseInt(userIDText.getText());//��ȡ�˺�
		oldPswd = oldPwsdText.getText();
	}
	/*ȡ����ť������*/
	public void cancleButtonListener() {
		/*�˳���ǰ����*/
	    Stage stage = (Stage)cancleButton.getScene().getWindow();
	    stage.close();
	}
	
}
