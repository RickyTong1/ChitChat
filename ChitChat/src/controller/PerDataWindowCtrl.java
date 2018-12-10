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
	String nickName;//昵称
	String email;//邮箱
	String signat;//个性签名
	String sex;//性别
	String year;//出生年
	String month;//出生月
	String day;//出生日
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
		//userIDText.setText(String.valueOf(userID));
		
	}
	/*昵称监视器*/
	public void nickNameLis() {
		
	}
	/*邮箱文本框监视器*/
	public void emailLis() {
		
	}
	/*修改按钮监视器*/
	public void vertifyLis() {
		/*提取个人信息 */
		nickName = nickNameText.getText().trim();
		email = emailText.getText().trim();
		signat = signature.getText().trim();
		sex = (String)sexComboBox.getValue();
		year = (String)yearComboBox.getValue();
		month = (String)monthComboBox.getValue();
		day = (String)dayComboBox.getValue();
		/*设置弹出内容*/
		ContextMenu pop = new ContextMenu();
		MenuItem nickNullWar = new MenuItem("昵称不能为空");
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
	/*取消按钮监视器*/
	public void cancleLis() {
		/*关闭当前窗口*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	

}
