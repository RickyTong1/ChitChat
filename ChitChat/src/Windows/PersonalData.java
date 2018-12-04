package Windows;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import Client.MainWindow;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalData extends JFrame{
	//static User []user=new User[100];
	//临时变量
	String tempUserNo;
	String tempUserName;
	String tempUserSex;
	String tempYear;
	String tempMonth;
	String tempDay;
	String tempEmail;
	//板块元素
	Box boxLeft;
	Box boxRight;
	Box boxRight_birth;
	Box boxBigUp;
	Box boxDown;
	Box boxMain;
	Box signatureBox;
	JLabel[] label=new JLabel[6];
	JComboBox<String> comBox_sex;
	JComboBox<String> comBox_year;
	JComboBox<String> comBox_month;
	JComboBox<String> comBox_day;
	JLabel year;
	JLabel month;
	JLabel day;
	JTextField textNo;
	JTextField textName;
	JTextField textEmail;
	JTextArea signature;
	JButton button1;
	JButton button2;
	//监视器
	TextListener textListener;
	ComboBoxListener comboBoxListener_sex;
	ComboBoxListener comboBoxListener_year;
	ComboBoxListener comboBoxListener_month;
	ComboBoxListener comboBoxListener_day;
	MyKeyListener keyListener;
	int id;
	public PersonalData(int id){
		this.id = id;
		this.setLayout(new FlowLayout());
		init();
		setBounds(500, 200, 400, 450);
		setTitle("我的资料");
		setVisible(true);
		setResizable(false);//设置窗口不可调
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void init() {
		//初始化临时变量
		tempUserSex="男";
		tempYear="1994";
		tempMonth="1";
		tempDay="1";
		signatureBox = Box.createHorizontalBox();
		//创建界面
		boxMain=Box.createVerticalBox();
		boxBigUp=Box.createHorizontalBox();
		//创建左边标签并放入左盒子
		boxLeft=Box.createVerticalBox();
		label[0]=new JLabel("账号：");
		label[1]=new JLabel("昵称：");
		label[2]=new JLabel("性别：");
		label[3]=new JLabel("出生日期：");
		label[4]=new JLabel("Email：");
		label[5]=new JLabel("个性签名：");
//		label[5]=new JLabel("密码：");
//		label[6]=new JLabel("确认密码：");
		for(int i=0;i<5;i++) {
			boxLeft.add(label[i]);
			if(5-i-1!=0) {						
				boxLeft.add(Box.createVerticalStrut(21));
			}
		}
		boxLeft.add(Box.createVerticalStrut(13));
		//boxLeft.add(Box.createVerticalStrut(15));
		//创建右边框
		boxRight=Box.createVerticalBox();
		boxRight_birth=Box.createHorizontalBox();
		comBox_sex=new JComboBox<String>();
		comBox_year=new JComboBox<String>();
		comBox_month=new JComboBox<String>();
		comBox_day=new JComboBox<String>();
		signature  = new JTextArea(1,3);
		signature.setLineWrap(true);
		textNo=new JTextField(15);		
		textNo.setEditable(false);
		textName=new JTextField(15);
		comBox_sex.addItem("男");
		comBox_sex.addItem("女");
		for(int i=1950;i<=2018;i++) {			
			comBox_year.addItem(String.valueOf(i));
		}
		
		for(int i=1;i<=12;i++) {			
			comBox_month.addItem(String.valueOf(i));
		}
		for(int i=1;i<=31;i++) {			
				comBox_day.addItem(String.valueOf(i));
		}
			
		year=new JLabel("年");
		month=new JLabel("月");
		day=new JLabel("日");		
		textEmail=new JTextField(15);
		//从数据库读取数据
//		oss = new OperateSQLServer();
//		oss.connectToDatabase();
//		ResultSet rs = oss.getPersonalInformation(id);
//		try {
//			if(rs.next()) {
//				textNo.setText(rs.getString(1));
//				textName.setText(rs.getString(2));
//				if(rs.getString(8).equals("男"))
//					comBox_sex.setSelectedIndex(0);
//				else
//					comBox_sex.setSelectedIndex(1);
//				String birth = rs.getString(9);
//				int maxSplit = 3;
//				String[] source = birth.split("-", maxSplit);
//				int birthYear = Integer.parseInt(source[0]);
//				int birthMonth = Integer.parseInt(source[1]);
//				int birthDay = Integer.parseInt(source[2]);
//				comBox_year.setSelectedIndex(birthYear-1950);
//				comBox_month.setSelectedIndex(birthMonth-1);
//				comBox_day.setSelectedIndex(birthDay-1);
//				textEmail.setText(rs.getString(5));
//				signature.setText(rs.getString(10));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		oss.closeDatabase();TODO Server rebuild.
			
		//放入右盒子
		boxRight.add(textNo);
		boxRight.add(Box.createVerticalStrut(12));
		boxRight.add(textName);
		boxRight.add(Box.createVerticalStrut(9));
		boxRight.add(comBox_sex);
		boxRight.add(Box.createVerticalStrut(11));
		boxRight_birth.add(comBox_year);
		boxRight_birth.add(year);
		boxRight_birth.add(comBox_month);
		boxRight_birth.add(month);
		boxRight_birth.add(comBox_day);
		boxRight_birth.add(day);
		boxRight.add(boxRight_birth);
		boxRight.add(Box.createVerticalStrut(12));
		boxRight.add(textEmail);
		boxRight.add(Box.createVerticalStrut(12));
		signatureBox.add(label[5]);
		signatureBox.add(Box.createHorizontalStrut(10));
		signatureBox.add(signature);
		//boxRight.add(signatureBox);
		
//		boxRight.add(pvd);	
//		boxRight.add(Box.createVerticalStrut(6));
//		boxRight.add(repvd);
		//填充按钮盒子
		boxDown=Box.createHorizontalBox();
		button1=new JButton("保存");
		button2=new JButton("退出");
		boxDown.add(button1);
		boxDown.add(Box.createHorizontalStrut(30));
		boxDown.add(button2);
		//放入主盒子
		boxBigUp.add(boxLeft);
		boxBigUp.add(Box.createHorizontalStrut(10));
		boxBigUp.add(boxRight);	
		boxMain.add(Box.createVerticalStrut(25));
		boxMain.add(boxBigUp);
		boxMain.add(signatureBox);
		boxMain.add(Box.createVerticalStrut(20));
		boxMain.add(boxDown);
		
		add(boxMain);
		//注册监视器
		textListener =new TextListener();
		textNo.addActionListener(textListener);
		textName.addActionListener(textListener);
		textEmail.addActionListener(textListener);
		//pvd.addActionListener(textListener);
		//repvd.addActionListener(textListener);
		button1.addActionListener(textListener);
		button2.addActionListener(textListener);
		comboBoxListener_sex=new ComboBoxListener();
		comBox_sex.addItemListener(comboBoxListener_sex);
		comboBoxListener_year=new ComboBoxListener();
		comBox_year.addItemListener(comboBoxListener_sex);
		comboBoxListener_month=new ComboBoxListener();
		comBox_month.addItemListener(comboBoxListener_sex);
		comboBoxListener_day=new ComboBoxListener();
		comBox_day.addItemListener(comboBoxListener_sex);
		keyListener=new MyKeyListener();
		button1.addKeyListener(keyListener);
	}
	void removeList() {
		textNo.setText("");
		textName.setText("");
		textEmail.setText("");
//		pvd.setText("");
//		repvd.setText("");
		comBox_sex.setSelectedItem("男");
		comBox_year.setSelectedItem("1994");
		comBox_month.setSelectedItem("1");
		comBox_day.setSelectedItem("1");
		textNo.requestFocus();
	}
	class TextListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			tempUserNo=textNo.getText();
			tempUserName=textName.getText();
			tempEmail=textEmail.getText();
			//tempPassword=pvd.getText();
			String birth = tempYear+"-"+tempMonth+"-"+tempDay;
			if(e.getSource() == button2)
				dispose();
			else if(e.getSource() == button1) {
//				OperateSQLServer oss;
//				oss.connectToDatabase();
//				oss.updateUserImformation(id, tempUserName, tempUserSex, birth, tempEmail, signature.getText());
//				oss.closeDatabase();TODO Server rebuild.
				MainWindow.nicknameLabel = new JLabel(tempUserName);
				MainWindow.styleWord = new JLabel(signature.getText());
				dispose();
			}
		}	
	}
	class ComboBoxListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			tempUserSex=(String)comBox_sex.getSelectedItem();
			tempYear=(String)comBox_year.getSelectedItem();
			tempMonth=(String)comBox_month.getSelectedItem();
			tempDay=(String)comBox_day.getSelectedItem();
			if(e.getStateChange()==ItemEvent.SELECTED) {
				//根据年月改变某月的天数
				try {
					int add=0;
					int year=Integer.parseInt(tempYear);
					int month=Integer.parseInt(tempMonth);
					if((year%4==0 && year%100!=0) || year%400==0)
						add=1;
					switch(month) {
						case 1:case 3:case 5:case 7:case 8:case 10:case 12:
						{
							boxRight_birth.remove(comBox_day);
							boxRight_birth.remove(day);
							comBox_day=new JComboBox();
							boxRight_birth.add(comBox_day);
							boxRight_birth.add(day);
							for(int i=1;i<=31;i++) {			
								comBox_day.addItem(String.valueOf(i));
							}
						}break;
						case 2:{
							boxRight_birth.remove(comBox_day);
							boxRight_birth.remove(day);
							comBox_day=new JComboBox();
							boxRight_birth.add(comBox_day);
							boxRight_birth.add(day);
							for(int i=1;i<=28+add;i++) {			
								comBox_day.addItem(String.valueOf(i));
							}
						}break;
						default:{
							boxRight_birth.remove(comBox_day);
							boxRight_birth.remove(day);
							comBox_day=new JComboBox();
							boxRight_birth.add(comBox_day);
							boxRight_birth.add(day);
							for(int i=1;i<=30;i++) {			
								comBox_day.addItem(String.valueOf(i));
							}
						}break;
					}
				}
				catch(Exception e1){
					System.out.println("下拉菜单出现异常");
				}
			}
		}
	}
	class MyKeyListener implements KeyListener{

		public void keyTyped(KeyEvent e) {
			if(e.getSource()==button1) {
				button1.doClick();
			}
		}
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		
	}
//	public static void main(String []args) {
//		PersonalTest win = new PersonalTest(37);
//	}
}

