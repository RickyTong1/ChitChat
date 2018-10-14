package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/*资料窗口公用的类*/
public class PublicDateWindow extends JFrame{
	JLabel nickName_label;				//昵称标签
	JLabel sex_label;					//性别标签
	JLabel birthday_label;				//生日标签
	JLabel age_label;					//年龄标签
	JLabel signature_label;				//个性签名标签
	JTextField nickName;				//昵称文本框
	JTextField sex;						//性别文本框
	JTextField birthday;				//生日文本框
	JTextField age;						//年龄文本框
	JTextField signature;				//个性签名文本框
	Box bigBox;							//水平盒子
	Box labelBox,textFieldBox;			//竖直盒子
	PublicDateWindow(){						//构造方法
		init();							//调用初始化方法
		setLayout(new FlowLayout());
		setLabelLocation();
		setTextFieldLocation();
		setBoxLocation();
		setVisible(true);
		setTitle("资料卡");
		setResizable(false);
		setSize(300,300);
		setLocation(300,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/*初始化组件*/
	void init() {
		nickName_label = new JLabel("昵称：");
		sex_label = new JLabel("性别：");
		birthday_label = new JLabel("生日：");
		age_label = new JLabel("年龄：");
		signature_label = new JLabel("个性签名：");
		nickName = new JTextField(10);
		sex = new JTextField(10);
		birthday = new JTextField(10);
		age = new JTextField(10);
		signature = new JTextField(10);
		bigBox = Box.createHorizontalBox();
		labelBox = Box.createVerticalBox();
		textFieldBox = Box.createVerticalBox();
	}
	/*定义标签位置（相对位置）*/
	void setLabelLocation() {
		labelBox.add(nickName_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(sex_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(birthday_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(age_label);
		labelBox.add(Box.createVerticalStrut(21));
		labelBox.add(signature_label);
	}
	/*设定文本框位置（相对位置）*/
	void setTextFieldLocation() {
		textFieldBox.add(nickName);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(sex);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(birthday);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(age);
		textFieldBox.add(Box.createVerticalStrut(9));
		textFieldBox.add(signature);
	}
	/*将盒子添加到窗口*/
	void setBoxLocation() {
		bigBox.add(labelBox);
		bigBox.add(Box.createHorizontalStrut(20));
		bigBox.add(textFieldBox);
		add(bigBox);
	}
}
