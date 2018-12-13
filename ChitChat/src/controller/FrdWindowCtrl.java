package controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.SocketConstants;
import Module.FriendWindow;
import Property.Property;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FrdWindowCtrl implements Initializable {
	private int contactsID = FriendWindow.contactsID;// Ŀ�����ID
	/* ����� */
	@FXML
	private static TextField userIDText;// �����˺ţ������޸ģ�
	@FXML
	private static TextField nickName;// �����ǳƣ������޸ģ�
	@FXML
	private static TextField remark;// ���ѱ�ע�����޸ģ�
	@FXML
	private static TextField sex;// �����Ա𣨲����޸ģ�
	@FXML
	private static TextField birthday;// ���ѳ������ڣ����ɱ༭��
	@FXML
	private static TextField age;// �������䣨���ɱ༭��
	@FXML
	private static TextArea signature;// ���Ѹ���ǩ�������ɱ༭��
	@FXML
	private Button saveButton;// ���水ť
	@FXML
	private Button cancleButton;// ȡ����ť

	String remarkNew;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	/* ��ע������ */
	public void remarkLis() {
		remarkNew = remark.getText();
	}

	/* ���水ť������ */
	public void saveLis() {
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.FRIEND_PROFILE_UPDATE;
		message.senderIP = Property.SERVER_IP;
		message.senderID = MainWindow.ID;
		message.targetID = contactsID;
		new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));
		JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
	}

	/* ȡ����ť������ */
	public void cancleLis() {
		/* �رյ�ǰ���� */
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		stage.close();
	}

	public static void setContents(MessageBlob e) {
		userIDText.setText("" + e.targetID);// �˺�
		nickName.setText(e.nickname);// �ǳ�
		remark.setText(e.remark);// ��ע
		sex.setText(e.gender);// �Ա�
		birthday.setText(e.birth);// ����
		long ages = (new Date(e.birth).getTime()) - (new Date().getTime());

		age.setText("" + new Date(ages).getYear());// ����
		signature.setText(e.style);// ����ǩ��
	}

}
