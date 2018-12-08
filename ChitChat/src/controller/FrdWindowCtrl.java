package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import Module.FriendWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FrdWindowCtrl implements Initializable{
	private int userID = FriendWindow.userID;//�û�ID
	private int contactsID = FriendWindow.contactsID;//Ŀ�����ID
	/*�����*/
	@FXML
	private TextField userIDText;//�����˺ţ������޸ģ�
	@FXML
	private TextField nickName;//�����ǳƣ������޸ģ�
	@FXML
	private TextField remark;//���ѱ�ע�����޸ģ�
	@FXML
	private TextField sex;//�����Ա𣨲����޸ģ�
	@FXML
	private TextField birthday;//���ѳ������ڣ����ɱ༭��
	@FXML
	private TextField age;//�������䣨���ɱ༭��
	@FXML
	private TextArea signature;//���Ѹ���ǩ�������ɱ༭��
	@FXML
	private Button saveButton;//���水ť
	@FXML
	private Button cancleButton;//ȡ����ť
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		/*�����ݿ��ж�ȡ������Ϣ����ʾ*/
		userIDText.setText(String.valueOf(contactsID));//�˺�
		nickName.setText("nickName");//�ǳ�
		remark.setText("remark");//��ע
		sex.setText("sex");//�Ա�
		birthday.setText("birthday");//����
		age.setText("age");//����
		signature.setText("����һ�����Եĸ���ǩ����");//����ǩ��
	}
	/*��ע������*/
	public void remarkLis() {
		
	}
	/*���水ť������ */
	public void saveLis() {
		JOptionPane.showMessageDialog(null,"�޸ĳɹ�");
	}
	/*ȡ����ť������*/
	public void cancleLis() {
		/*�رյ�ǰ����*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	
}
