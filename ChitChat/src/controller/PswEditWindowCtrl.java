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
	
	int ID;//账号
	String oldPswd;//旧密码
	String newPswd;//新密码
	String vertify;//确认密码
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	/*账号文本框监视器*/
	public void userIDListener() {
		newPswdText.requestFocus();
	}
	/*原密码文本框监视器*/
	public void oldPswListener() {
		oldPwsdText.requestFocus();
	}
	/*新密码文本框监视器*/
	public void newPswListener() {
		vertifyPswd.requestFocus();
	}
	/*确认密码文本框监视器*/
	public void vertifyPswListener() {
		
	}
	/*确认按钮监视器*/
	public void vertifyButtonListener() {
		
		oldPswd = oldPwsdText.getText();//提取原密码内容
		newPswd = newPswdText.getText().trim();//提取新密码内容
		vertify = vertifyPswd.getText().trim();//提取确认密码内容
		ContextMenu pop = new ContextMenu();
		MenuItem IDNullWar = new MenuItem("账号不能为空");
		MenuItem pswdNullWar = new MenuItem("密码不能为空");
		if(userIDText.getText().trim().compareTo("")==0) {
			pop.getItems().add(IDNullWar);//账号为空
			pop.show(userIDText, Side.BOTTOM, 0,0);
		}
		else if(oldPswd.compareTo("")==0) {
			ID = Integer.parseInt(userIDText.getText());//提取账号
			pop.getItems().add(pswdNullWar);//原密码为空
			pop.show(oldPwsdText,Side.BOTTOM,0,0);
		}
		else if(newPswd.compareTo("")==0) {
			pop.getItems().add(pswdNullWar);//新密码为空
			pop.show(newPswdText, Side.BOTTOM, 0, 0);
		}
		else if(newPswd.compareTo(vertify)!=0) {
			JOptionPane.showMessageDialog(null, "两次密码输入不一致，请重新输入");
		}
		//TODO 向后端发送修改密码请求，注意账号和 密码匹配
	}
	/*取消按钮监视器*/
	public void cancleButtonListener() {
		/*退出当前窗口*/
	    Stage stage = (Stage)cancleButton.getScene().getWindow();
	    stage.close();
	}
	
}
