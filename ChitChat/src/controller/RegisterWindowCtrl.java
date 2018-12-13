package controller;
import Constants.*;
import Property.Property;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.SendMessage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.LocalData;

public class RegisterWindowCtrl implements Initializable {
	/*组件绑定*/
	@FXML
	private ComboBox sexComboBox;//性别下拉框
	@FXML
	private ComboBox year;//年份下拉框
	@FXML
	private ComboBox month;//月份下拉框
	@FXML
	private ComboBox day;//日份下拉框
	@FXML
	private TextField nickNameTextField;//昵称文本框
	@FXML
	private TextField emailTextField;//邮箱密码框
	@FXML
	private PasswordField pswdPswdField;//密码框
	@FXML
	private PasswordField vertifyPswdField;//确认密码框
	@FXML
	private Button registerButton;//注册按钮
	@FXML
	private Button exitButton;//退出按钮

	String nickName;//昵称
	String email;//电子邮箱
	String password;//密码
	String vertify;//确认的密码
	String sex;//性别
	String birthdayYear;//出生年份
	String birthdayMonth;//出生月份
	String birthdayDay;//出生日份
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// nothing here.Don't need!!!
		/*构建性别下拉框*/
		ObservableList<String> sexOptions = FXCollections.observableArrayList("男","女");
		sexComboBox.setItems(sexOptions);
		sexComboBox.setValue("男");
		/*默认出生年月日*/
		year.setValue("1999");	
		month.setValue("1");	
		day.setValue("1");
	}
	/*昵称文本框监视器*/
	public void nickListener(ActionEvent nick) {
		emailTextField.requestFocus();
	}
	/*（暂时不需要）*/
	public void sexListener(ActionEvent sex) {		
		
	}
	/*邮箱密码框监视器*/
	public void emailListener(ActionEvent email) {
		pswdPswdField.requestFocus();
	}
	/*密码框监视器*/
	public void passwordListener(ActionEvent password) {
		vertifyPswdField.requestFocus();
	}
	/*确认密码框监视器*/
	public void vertifyListener(ActionEvent vertify) {
		registerButton.requestFocus();
	}
	/*注册按钮监视器*/
	public void registerListener(ActionEvent register) {
		nickName = nickNameTextField.getText();//提取昵称文本框内容
		sex = (String) sexComboBox.getValue();//提取性别下拉框内容
		email = emailTextField.getText();//提取邮箱文本框内容
		password = pswdPswdField.getText();//提取密码内容
		vertify = vertifyPswdField.getText();//提取确认密码内容
		birthdayYear = (String) year.getValue();//提取年份下拉框内容
		birthdayMonth = (String)month.getValue();//提取月份下拉框内容
		birthdayDay = (String)day.getValue();//提取日份下拉框内容
		/*设置弹出菜单提示*/
		ContextMenu pop = new ContextMenu();
		MenuItem IDNullWar = new MenuItem("账号不能为空");//账号空警告
		MenuItem pswdNullWar = new MenuItem("密码不能为空");//密码空警告
		if(nickName.trim().compareTo("")==0) {
			pop.getItems().add(IDNullWar);//账号为空
			pop.show(nickNameTextField, Side.BOTTOM, 0,0);
		}
		else if(password.trim().compareTo("")==0) {
			pop.getItems().add(pswdNullWar);//密码为空
			pop.show(pswdPswdField, Side.BOTTOM, 0,0);
		}
	
		else if(password.trim().compareTo(vertify.trim())!=0) {
			JOptionPane.showMessageDialog(null, "两次密码不一致，请重新确认");//两次密码比较失败
		}
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.REGISTER;
		message.senderIP = Property.NATIVE_IP;
		message.nickname = nickName;
		message.key = password;
		message.email = email;
		message.phoneNum = null;
		message.birth = birthdayYear+"-"+birthdayMonth
				+"-"+birthdayDay;
		message.gender = sex;
		message.style = null;
		System.out.println(message.senderIP+"in SendMessage");
		new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

	}
	/*退出按钮监视器*/
	public void exitListener(ActionEvent exit) {
		/*关闭当前窗口*/
		Stage stage = (Stage)exitButton.getScene().getWindow();
		stage.close();
	}
	/*年份下拉框鼠标进入事件*/
	public void yearItemListener() {
		int birthdayYear=LocalData.NOW_YEAR;	
		String year1[] = new String[100];//年份
		/*构建年份下拉框*/
		for(int i = 0;i<100;i++) {
			year1[i] = new String(""+birthdayYear+"");
			birthdayYear--;
		}
		ObservableList<String> yearOptions = FXCollections.observableArrayList(year1);
		year.setItems(yearOptions);
	}
	/*月份下拉框鼠标进入事件*/
	public void monthItemListener() {
		String month1[] = null;
		int birthdayMonth=12;
		if(Integer.parseInt((String)year.getValue())!= LocalData.NOW_YEAR) {
			month1 = new String[12];//月份
			for(int i=0;i<12;i++) {
				month1[i] = new String(""+birthdayMonth+"");
				birthdayMonth--;
			}
		}
		if(Integer.parseInt((String)year.getValue()) == LocalData.NOW_YEAR){
			month1 = new String[LocalData.NOW_MONTH];//月份
			birthdayMonth = LocalData.NOW_MONTH;
			/*构建月份下拉框*/
			for(int i=0;i<LocalData.NOW_MONTH;i++) {
				month1[i] = new String(""+birthdayMonth+"");
				birthdayMonth--;
			}
		}		
		ObservableList<String> monthOptions = FXCollections.observableArrayList(month1);
		month.setItems(monthOptions);
	}
	/*日份下拉框鼠标进入事件*/
	public void dayItemListener() {
		int birthdayDay = 31;//天数
		int leapJudge=Integer.parseInt((String)year.getValue());//判断是否是闰年
		int chioce=Integer.parseInt((String)month.getValue());//获取月份下拉框内容，用于switch case语句
		switch(chioce){
		case 2: birthdayDay = 28;break;
		case 4:
		case 6:
		case 9:
		case 11:birthdayDay = 30;
				break;
			//default :break;		
		}
		if((leapJudge%4==0 && leapJudge%100!=0 || leapJudge%400==0) && chioce==2) {
			birthdayDay = 29;
		}
		/*当前年份*/
		if(leapJudge == LocalData.NOW_YEAR && chioce == LocalData.NOW_MONTH) {
			birthdayDay = LocalData.NOW_DAY;//
		}
		String day1[] = new String[birthdayDay];//日期		
		/*构建日期下拉框*/
		for(int i=0;birthdayDay>0;i++) {
			day1[i] = new String(""+birthdayDay+"");
			birthdayDay--;
		}
		ObservableList<String> dayOptions = FXCollections.observableArrayList(day1);
		day.setItems(dayOptions);//设置下拉框内容
	}	
}
