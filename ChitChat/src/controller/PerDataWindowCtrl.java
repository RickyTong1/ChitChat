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
	private TextField userIDText;//账号
	@FXML
	private TextField nickNameText;//昵称
	@FXML
	private TextField emailText;//邮箱
	@FXML
	private TextArea signature;//个性签名
	@FXML
	private ComboBox sexComboBox;//性别下拉框
	@FXML
	private ComboBox yearComboBox;//年份下拉框
	@FXML
	private ComboBox monthComboBox;//月份下拉框
	@FXML
	private ComboBox dayComboBox;//日份下拉框
	@FXML
	private Button vertifyButton;//确认按钮
	@FXML
	private Button cancleButton;//取消按钮
	private int userID = PerDataWindow.userID;//获取用户账号
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
		/*从数据库中读取消息并显示*/
	
	}
	/*昵称监视器*/
	public void nickNameLis() {
		nickNameText.getText();
	}
	/*邮箱文本框监视器*/
	public void emailLis() {
		
	}
	/*确认按钮监视器*/
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
	/*取消按钮监视器*/
	public void cancleLis() {
		/*关闭当前窗口*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	

}
