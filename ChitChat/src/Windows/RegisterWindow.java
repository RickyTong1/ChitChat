package Windows;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.SendMessage;
import Constants.SocketConstants;

import java.awt.*;
import java.awt.event.*;

public class RegisterWindow extends JFrame{
	//static User []user=new User[100];
	//��ʱ����
	String tempUserNo;
	String tempUserName;
	String tempUserSex;
	String tempPassword;
	String tempYear;
	String tempMonth;
	String tempDay;
	String tempEmail;
	//���Ԫ��
	Box boxLeft;
	Box boxRight;
	Box boxRight_birth;
	Box boxBigUp;
	Box boxDown;
	Box boxMain;
	JLabel[] label=new JLabel[7];
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
	JPasswordField pvd;
	JPasswordField repvd;
	JButton button1;
	JButton button2;
	//������
	TextListener textListener;
	ComboBoxListener comboBoxListener_sex;
	ComboBoxListener comboBoxListener_year;
	ComboBoxListener comboBoxListener_month;
	ComboBoxListener comboBoxListener_day;
	MyKeyListener keyListener;
	RegisterWindow(){
		this.setLayout(new FlowLayout());
		init();
		setBounds(500, 200, 500, 400);
		setTitle("�û�ע��");
		setVisible(true);
		setResizable(false);//���ô��ڲ��ɵ�
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void init() {
		//��ʼ����ʱ����
		tempUserSex="��";
		tempYear="1994";
		tempMonth="1";
		tempDay="1";
		//��������
		boxMain=Box.createVerticalBox();
		boxBigUp=Box.createHorizontalBox();
		//������߱�ǩ�����������
		boxLeft=Box.createVerticalBox();
		label[0]=new JLabel("�˺ţ�");
		label[1]=new JLabel("�ǳƣ�");
		label[2]=new JLabel("�Ա�");
		label[3]=new JLabel("�������ڣ�");
		label[4]=new JLabel("Email��");
		label[5]=new JLabel("���룺");
		label[6]=new JLabel("ȷ�����룺");
		for(int i=1;i<7;i++) {
			boxLeft.add(label[i]);
			if(7-i-1!=0) {						
				boxLeft.add(Box.createVerticalStrut(17));
			}
		}
		//�����ұ߿�
		boxRight=Box.createVerticalBox();
		boxRight_birth=Box.createHorizontalBox();
		comBox_sex=new JComboBox<String>();
		comBox_year=new JComboBox<String>();
		comBox_month=new JComboBox<String>();
		comBox_day=new JComboBox<String>();
		textNo=new JTextField(25);	
		textName=new JTextField(25);
		comBox_sex.addItem("��");
		comBox_sex.addItem("Ů");
		
		for(int i=1950;i<=2018;i++) {			
			comBox_year.addItem(String.valueOf(i));
		}
		
		for(int i=1;i<=12;i++) {			
			comBox_month.addItem(String.valueOf(i));
		}
		for(int i=1;i<=31;i++) {			
				comBox_day.addItem(String.valueOf(i));
		}
			
		year=new JLabel("��");
		month=new JLabel("��");
		day=new JLabel("��");		
		textEmail=new JTextField(25);
		pvd=new JPasswordField(25);		
		repvd=new JPasswordField(25);		
		//�����Һ���
//		boxRight.add(textNo);
//		boxRight.add(Box.createVerticalStrut(6));
		boxRight.add(textName);
		boxRight.add(Box.createVerticalStrut(6));
		boxRight.add(comBox_sex);
		boxRight.add(Box.createVerticalStrut(6));
		boxRight_birth.add(comBox_year);
		boxRight_birth.add(year);
		boxRight_birth.add(comBox_month);
		boxRight_birth.add(month);
		boxRight_birth.add(comBox_day);
		boxRight_birth.add(day);
		boxRight.add(boxRight_birth);
		boxRight.add(Box.createVerticalStrut(6));
		boxRight.add(textEmail);
		boxRight.add(Box.createVerticalStrut(6));
		boxRight.add(pvd);	
		boxRight.add(Box.createVerticalStrut(6));
		boxRight.add(repvd);
		//��䰴ť����
		boxDown=Box.createHorizontalBox();
		button1=new JButton("ע��");
		button2=new JButton("�˳�");
		boxDown.add(button1);
		boxDown.add(Box.createHorizontalStrut(30));
		boxDown.add(button2);
		//����������
		boxBigUp.add(boxLeft);
		boxBigUp.add(Box.createHorizontalStrut(10));
		boxBigUp.add(boxRight);	
		boxMain.add(Box.createVerticalStrut(25));
		boxMain.add(boxBigUp);
		boxMain.add(Box.createVerticalStrut(20));
		boxMain.add(boxDown);
		
		add(boxMain);
		//ע�������
		textListener =new TextListener();
		textNo.addActionListener(textListener);
		textName.addActionListener(textListener);
		textEmail.addActionListener(textListener);
		pvd.addActionListener(textListener);
		repvd.addActionListener(textListener);
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
		pvd.setText("");
		repvd.setText("");
		comBox_sex.setSelectedItem("��");
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
			tempPassword=pvd.getText();
			if(e.getSource()==button2)
				dispose();
			else if(e.getSource()==textNo) {
					textName.requestFocus();
			}
			else if(tempUserName.equals("")) {
				JOptionPane.showMessageDialog(null, "�ǳƲ���Ϊ��");
			}
			else if(e.getSource()==textName) {
				
				textEmail.requestFocus();
			}
			else if(e.getSource()==textEmail) {
					pvd.requestFocus();	
			}
			else if(tempPassword.equals("")) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			}
			else if(e.getSource()==pvd) {
				repvd.requestFocus();
			}
			else if(repvd.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "ע��ʧ����ȷ�������Ƿ���ȷ");
			}
			else if(!tempPassword.equals(repvd.getText())) {
				JOptionPane.showMessageDialog(null, "�����������벻һ��");
			}
			else if(e.getSource()==repvd) {
				button1.requestFocus();
			}
			else if(e.getSource()==button1) {
				String birth=tempYear+"-"+tempMonth+"-"+tempDay;
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.REGISTER;
				message.senderIP = Property.Property.NATIVE_IP;
				message.nickname = tempUserName;
				message.key = tempPassword;
				message.email = tempEmail;
				message.phoneNum = null;
				message.birth = birth;
				message.gender = tempUserSex;
				message.style = null;
				System.out.println(message.senderIP);
				new SendMessage(
						Property.Property.SERVER_IP
						,SocketConstants.GENERAL_PORT
						,MessageBlobOperator.pack(message));
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
				//�������¸ı�ĳ�µ�����
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
					System.out.println("�����˵������쳣");
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
}

