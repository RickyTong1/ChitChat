package Windows;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;
/*个人资料界面*/
public class PersonalData extends PublicDateWindow implements ActionListener{
	JButton saveButton;				//保存资料按钮
	JButton exitButton;				//退出按钮
	Box hbox;						//按钮水平盒子
	Box buttomBox;					//底层盒子
	PersonalData(){					//构造方法
		newInit();					//调用初始化方法
		addListener();		//调用添加监视器方法，给组件添加监视器
	}
	/*初始化组件方法*/
	void newInit() {									
		saveButton = new JButton("确认");
		exitButton = new JButton("退出");
		hbox = Box.createHorizontalBox();
		buttomBox = Box.createVerticalBox();
		hbox.add(saveButton);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(exitButton);
		buttomBox.add(bigBox);
		buttomBox.add(Box.createVerticalStrut(20));
		buttomBox.add(hbox);
		add(buttomBox);
	}
	/*添加监视器方法*/
	void addListener() {
		saveButton.addActionListener(this);
		exitButton.addActionListener(this);
		nickName.addActionListener(this);				//昵称文本框
		sex.addActionListener(this);						//性别文本框
		birthday.addActionListener(this);;				//生日文本框
		age.addActionListener(this);						//年龄文本框
		signature.addActionListener(this); 				//个性签名文本框
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton) {
			System.exit(0);
		}
		if(e.getSource()==saveButton) {
			
		}
		if(e.getSource()==nickName) {
			sex.requestFocus();
		}
		if(e.getSource()==sex) {
			signature.requestFocus();
		}
		if(e.getSource()==birthday) {
			
		}
		if(e.getSource()==signature) {
			
		}
		
	}
}
