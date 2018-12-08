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
	/*绑定组件*/
	@FXML
	private TextField userIDText;//账号
	@FXML
	private TextField oldPwsdText;//原密码
	@FXML
	private TextField newPswdText;//新密码
	@FXML
	private TextField vertifyPswd;//确认密码
	@FXML
	private Button vertifyButton;//确认按钮
	@FXML
	private Button cancleButton;//取消按钮
	
	int ID;
	String oldPswd;
	String newPswd;
	String vertify;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/*账号文本框监视器*/
	public void userIDListener() {
		
	}
	/*原密码文本框监视器*/
	public void oldPswListener() {
		
	}
	/*新密码文本框监视器*/
	public void newPswListener() {
		
	}
	/*确认密码文本框监视器*/
	public void vertifyPswListener() {
		
	}
	/*确认按钮监视器*/
	public void vertifyButtonListener() {
		ID = Integer.parseInt(userIDText.getText());//提取账号
		oldPswd = oldPwsdText.getText();
	}
	/*取消按钮监视器*/
	public void cancleButtonListener() {
		/*退出当前窗口*/
	    Stage stage = (Stage)cancleButton.getScene().getWindow();
	    stage.close();
	}
	
}
