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
/*FindPasswordWindow控制器*/
public class FindPswWindowCtrl implements Initializable{
	/*绑定组件*/
	@FXML
	private TextField IDText;//账号文本框
	@FXML
	private TextField emailText;//邮箱文本框
	@FXML
	private TextField verificationText;//验证码
	@FXML
	private PasswordField newPswdText;//新密码
	@FXML
	private PasswordField vertifyText;//确认密码
	@FXML
	private Button sendEmailButton;//发送按钮
	@FXML
	private Button vertifyButton;//确认按钮
	@FXML
	private Button exitButton;///退出按钮
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
	/*账号文本框监视器*/
	public void IDLis() {
		//TODO
		emailText.requestFocus();
	}
	/*邮箱文本框监视器*/
	public void emailLis() {
		//TODO
		verificationText.requestFocus();
	}
	/*验证码文本框监视器*/
	public void verificationLis() {
		//TODO
		newPswdText.requestFocus();
	}
	/*原密码文本框监视器*/
	public void newPswdLis() {
		//TODO
		vertifyText.requestFocus();
	}
	/*确认密码文本框监视器*/
	public void vertifyPswdLis() {
		//TODO
		vertifyButton.requestFocus();
	}
	/*发送按钮监视器*/
	public void sendEmailLis() {
		//TODO
	}
	/*确认按钮监视器*/
	public void vertifyLis() {
		//TODO
		JOptionPane.showMessageDialog(null, "密码修改成功");
		Stage stage = (Stage)vertifyButton.getScene().getWindow();
		stage.close();
	}
	/*退出按钮监视器*/
	public void exitLis() {
		/*关闭当前窗口*/
		Stage stage = (Stage)exitButton.getScene().getWindow();
		stage.close();
	}

}
