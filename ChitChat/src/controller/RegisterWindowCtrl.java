package controller;
import Constants.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	int birthdayYear;//出生年份
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
		nickName = nickNameTextField.getText();//将昵称文本框内容提出
		email = emailTextField.getText();//提取邮箱文本框内容
		password = pswdPswdField.getText();//提取密码内容
		vertify = vertifyPswdField.getText();//提取确认密码内容
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
