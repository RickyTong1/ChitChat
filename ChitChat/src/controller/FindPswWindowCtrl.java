package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/*FindPasswordWindow������*/
public class FindPswWindowCtrl implements Initializable{
	/*�����*/
	@FXML
	private TextField IDText;//�˺��ı���
	@FXML
	private TextField emailText;//�����ı���
	@FXML
	private TextField verificationText;//��֤��
	@FXML
	private PasswordField newPswdText;//������
	@FXML
	private PasswordField vertifyText;//ȷ������
	@FXML
	private Button sendEmailButton;//���Ͱ�ť
	@FXML
	private Button vertifyButton;//ȷ�ϰ�ť
	@FXML
	private Button exitButton;///�˳���ť
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
	/*�˺��ı��������*/
	public void IDLis() {
		//TODO
		emailText.requestFocus();
	}
	/*�����ı��������*/
	public void emailLis() {
		//TODO
		verificationText.requestFocus();
	}
	/*��֤���ı��������*/
	public void verificationLis() {
		//TODO
		newPswdText.requestFocus();
	}
	/*ԭ�����ı��������*/
	public void newPswdLis() {
		//TODO
		vertifyText.requestFocus();
	}
	/*ȷ�������ı��������*/
	public void vertifyPswdLis() {
		//TODO
		vertifyButton.requestFocus();
	}
	/*���Ͱ�ť������*/
	public void sendEmailLis() {
		//TODO
	}
	/*ȷ�ϰ�ť������*/
	public void vertifyLis() {
		//TODO
		JOptionPane.showMessageDialog(null, "�����޸ĳɹ�");
		Stage stage = (Stage)vertifyButton.getScene().getWindow();
		stage.close();
	}
	/*�˳���ť������*/
	public void exitLis() {
		/*�رյ�ǰ����*/
		Stage stage = (Stage)exitButton.getScene().getWindow();
		stage.close();
	}

}
