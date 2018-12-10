package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
	
	int ID;//�˺�
	String oldPswd;//������
	String newPswd;//������
	String vertify;//ȷ������
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/*�˺��ı��������*/
	public void userIDListener() {
		newPswdText.requestFocus();
	}
	/*ԭ�����ı��������*/
	public void oldPswListener() {
		oldPwsdText.requestFocus();
	}
	/*�������ı��������*/
	public void newPswListener() {
		vertifyPswd.requestFocus();
	}
	/*ȷ�������ı��������*/
	public void vertifyPswListener() {
		
	}
	/*ȷ�ϰ�ť������*/
	public void vertifyButtonListener() {
		
		oldPswd = oldPwsdText.getText();//��ȡԭ��������
		newPswd = newPswdText.getText().trim();//��ȡ����������
		vertify = vertifyPswd.getText().trim();//��ȡȷ����������
		ContextMenu pop = new ContextMenu();
		MenuItem IDNullWar = new MenuItem("�˺Ų���Ϊ��");
		MenuItem pswdNullWar = new MenuItem("���벻��Ϊ��");
		if(userIDText.getText().trim().compareTo("")==0) {
			pop.getItems().add(IDNullWar);//�˺�Ϊ��
			pop.show(userIDText, Side.BOTTOM, 0,0);
		}
		else if(oldPswd.compareTo("")==0) {
			ID = Integer.parseInt(userIDText.getText());//��ȡ�˺�
			pop.getItems().add(pswdNullWar);//ԭ����Ϊ��
			pop.show(oldPwsdText,Side.BOTTOM,0,0);
		}
		else if(newPswd.compareTo("")==0) {
			pop.getItems().add(pswdNullWar);//������Ϊ��
			pop.show(newPswdText, Side.BOTTOM, 0, 0);
		}
		else if(newPswd.compareTo(vertify)!=0) {
			JOptionPane.showMessageDialog(null, "�����������벻һ�£�����������");
		}
		//TODO ���˷����޸���������ע���˺ź� ����ƥ��
	}
	/*ȡ����ť������*/
	public void cancleButtonListener() {
		/*�˳���ǰ����*/
	    Stage stage = (Stage)cancleButton.getScene().getWindow();
	    stage.close();
	}
	
}
