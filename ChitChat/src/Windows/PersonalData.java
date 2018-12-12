package Windows;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.SocketConstants;
import Property.Property;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalData extends JFrame{
	//static User []user=new User[100];
	//��ʱ����
	String tempUserNo;
	String tempUserName;
	String tempUserSex;
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
	//������
	TextListener textListener;
	ComboBoxListener comboBoxListener_sex;
	ComboBoxListener comboBoxListener_year;
	ComboBoxListener comboBoxListener_month;
	ComboBoxListener comboBoxListener_day;
	MyKeyListener keyListener;
	int id;
	public PersonalData(MessageBlob e){
		this.id = e.senderID;
		this.setLayout(new FlowLayout());
		init(e);
		setBounds(500, 200, 400, 450);
		setTitle("�ҵ�����");
		setVisible(true);
		setResizable(false);//���ô��ڲ��ɵ�
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void init(MessageBlob e) {
		//��ʼ����ʱ����
		tempUserSex="��";
		tempYear="1994";
		tempMonth="1";
		tempDay="1";
		signatureBox = Box.createHorizontalBox();
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
		label[5]=new JLabel("����ǩ����");
//		label[5]=new JLabel("���룺");
//		label[6]=new JLabel("ȷ�����룺");
		for(int i=0;i<5;i++) {
			boxLeft.add(label[i]);
			if(5-i-1!=0) {						
				boxLeft.add(Box.createVerticalStrut(21));
			}
		}
		boxLeft.add(Box.createVerticalStrut(13));
		//boxLeft.add(Box.createVerticalStrut(15));
		//�����ұ߿�
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
		textEmail=new JTextField(15);
		//�����ݿ��ȡ����
		textNo.setText(""+e.senderID);
		textName.setText(e.nickname);
		if(e.gender.equals("��"))
			comBox_sex.setSelectedIndex(0);
		else
			comBox_sex.setSelectedIndex(1);
		String birth = e.birth;
		int maxSplit = 3;
		String[] source = birth.split("-", maxSplit);
		int birthYear = Integer.parseInt(source[0]);
		int birthMonth = Integer.parseInt(source[1]);
		int birthDay = Integer.parseInt(source[2]);
		comBox_year.setSelectedIndex(birthYear-1950);
		comBox_month.setSelectedIndex(birthMonth-1);
		comBox_day.setSelectedIndex(birthDay-1);
		textEmail.setText(e.email);
		signature.setText(e.style);
		
			
		//�����Һ���
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

		
		//��䰴ť����
		boxDown=Box.createHorizontalBox();
		button1=new JButton("����");
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
		boxMain.add(signatureBox);
		boxMain.add(Box.createVerticalStrut(20));
		boxMain.add(boxDown);
		
		add(boxMain);
		//ע�������
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
		this.addWindowListener(new WindowAdapter() {// ���߲���
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				MainWindow.hasPersonalWindow = false;
			}
		});
	}
	void removeList() {
		textNo.setText("");
		textName.setText("");
		textEmail.setText("");
//		pvd.setText("");
//		repvd.setText("");
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
			//tempPassword=pvd.getText();
			String birth = tempYear+"-"+tempMonth+"-"+tempDay;
			if(e.getSource() == button2) {
				MainWindow.hasPersonalWindow = false;
				dispose();
				
			}
				
			else if(e.getSource() == button1) {
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.SELF_PROFILE_UPDATE;
				message.senderIP = Property.NATIVE_IP;
				message.senderID = MainWindow.ID;
				message.nickname = tempUserName;
				message.key = null;
				message.email = tempEmail;
				message.phoneNum = null;
				message.birth = birth;
				message.gender = tempUserSex;
				message.style = signature.getText();
				new SendMessage(Property.SERVER_IP
						,SocketConstants.GENERAL_PORT
						,MessageBlobOperator.pack(message));
				MainWindow.hasPersonalWindow = false;
				
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e1) {
//					// TODO ʵʱˢ��
//					e1.printStackTrace();
//				}
//				MessageBlob quest_for_profile = new MessageBlob();
//				quest_for_profile.type = MessageBlobType.SELF_PROFILE_QUEST;
//				quest_for_profile.senderID = MainWindow.ID;
//				quest_for_profile.senderIP = Property.NATIVE_IP;
//				
//				new SendMessage(Property.SERVER_IP
//						,SocketConstants.GENERAL_PORT
//						,MessageBlobOperator.pack(quest_for_profile));
				
//				
//				MainWindow.nicknameLabel = new JLabel(tempUserName);
//				MainWindow.styleWord = new JLabel(signature.getText());
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
//	public static void main(String []args) {
//		PersonalTest win = new PersonalTest(37);
//	}
}

