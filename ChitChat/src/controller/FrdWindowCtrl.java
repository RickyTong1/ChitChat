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
	private int userID = FriendWindow.userID;//用户ID
	private int contactsID = FriendWindow.contactsID;//目标好友ID
	/*组件绑定*/
	@FXML
	private TextField userIDText;//好友账号（不可修改）
	@FXML
	private TextField nickName;//好友昵称（不可修改）
	@FXML
	private TextField remark;//好友备注（可修改）
	@FXML
	private TextField sex;//好友性别（不可修改）
	@FXML
	private TextField birthday;//好友出生日期（不可编辑）
	@FXML
	private TextField age;//好友年龄（不可编辑）
	@FXML
	private TextArea signature;//好友个性签名（不可编辑）
	@FXML
	private Button saveButton;//保存按钮
	@FXML
	private Button cancleButton;//取消按钮
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		/*从数据库中读取好友信息并显示*/
		userIDText.setText(String.valueOf(contactsID));//账号
		nickName.setText("nickName");//昵称
		remark.setText("remark");//备注
		sex.setText("sex");//性别
		birthday.setText("birthday");//生日
		age.setText("age");//年龄
		signature.setText("这是一个测试的个性签名。");//个性签名
	}
	/*备注监视器*/
	public void remarkLis() {
		
	}
	/*保存按钮监视器 */
	public void saveLis() {
		JOptionPane.showMessageDialog(null,"修改成功");
	}
	/*取消按钮监视器*/
	public void cancleLis() {
		/*关闭当前窗口*/
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
	
}
