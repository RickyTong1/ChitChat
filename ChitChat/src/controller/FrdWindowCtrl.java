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
	private int contactsID = FriendWindow.contactsID;// 目标好友ID
	/* 组件绑定 */
	@FXML
	private static TextField userIDText;// 好友账号（不可修改）
	@FXML
	private static TextField nickName;// 好友昵称（不可修改）
	@FXML
	private static TextField remark;// 好友备注（可修改）
	@FXML
	private static TextField sex;// 好友性别（不可修改）
	@FXML
	private static TextField birthday;// 好友出生日期（不可编辑）
	@FXML
	private static TextField age;// 好友年龄（不可编辑）
	@FXML
	private static TextArea signature;// 好友个性签名（不可编辑）
	@FXML
	private Button saveButton;// 保存按钮
	@FXML
	private Button cancleButton;// 取消按钮

	String remarkNew;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	/* 备注监视器 */
	public void remarkLis() {
		remarkNew = remark.getText();
	}

	/* 保存按钮监视器 */
	public void saveLis() {
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.FRIEND_PROFILE_UPDATE;
		message.senderIP = Property.SERVER_IP;
		message.senderID = MainWindow.ID;
		message.targetID = contactsID;
		new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));
		JOptionPane.showMessageDialog(null, "修改成功");
	}

	/* 取消按钮监视器 */
	public void cancleLis() {
		/* 关闭当前窗口 */
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		stage.close();
	}

	public static void setContents(MessageBlob e) {
		userIDText.setText("" + e.targetID);// 账号
		nickName.setText(e.nickname);// 昵称
		remark.setText(e.remark);// 备注
		sex.setText(e.gender);// 性别
		birthday.setText(e.birth);// 生日
		long ages = (new Date(e.birth).getTime()) - (new Date().getTime());

		age.setText("" + new Date(ages).getYear());// 年龄
		signature.setText(e.style);// 个性签名
	}

}
