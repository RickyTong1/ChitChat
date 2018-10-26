package Windows;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Client.MainWindow;
import Constants.*;
import DataBaseOperation.OperateSQLServer;
import Restores.KeyKeep;
import Restores.KeyKeepOperate;
import Server.SendThread;

/*��½����LoginWindow*/
public class LoginWindow extends JFrame {
	JButton loginButton;			//��½��ť
	JButton registerButton;			//ע�ᰴť
	JButton findMyKeyButton;		//�һ����밴ť
	JCheckBox keepMyKeyCheckBox;	//��ס���뵥ѡ��ť
	JButton changePassword;			//�޸����밴ť
	JLabel userLabel;				//�˺ű�ǩ
	JLabel keyLabel;			//�����ǩ	
	JMenu iserIDMenu;				//
	JTextField ID;					//�����ı���
	JPasswordField key;				//�����ı���
	
	public static String IP_address;
	Box hbox1,hbox2;				//ˮƽ����
	Box vbox1,vbox2,vbox3;				//��ֱ����
	File user_info;
	Vector<KeyKeep> keys = new Vector<KeyKeep>();
	public LoginWindow(){
		try {
			user_info = new File(Window.USER_INFO_URL);
			IP_address = InetAddress.getLocalHost().getHostAddress();
			System.out.println(IP_address);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "������������!","",JOptionPane.PLAIN_MESSAGE);	
		}
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(user_info));
			keys = (Vector<KeyKeep>)in.readObject();
			in.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�������.","",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "�����˺���Ϣ�ļ���ȡ����.","",JOptionPane.PLAIN_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "�˺���Ϣװ�ش���.","",JOptionPane.PLAIN_MESSAGE);
		}
		Vector<KeyKeep> temp = new Vector<KeyKeep>();
		for(KeyKeep i:keys) {
			temp.add(KeyKeepOperate.unpack(i));
		}
		keys = temp;
		
		init();
		setLayout(new FlowLayout());
		setVisible(true);			//���ÿɼ�
		setTitle("ChitChat");
		setBounds(Window.getMiddleWidth(350),Window.getMiddleHeight(350),350,350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//add(new Imagecanvas());
	}
	/*��������ʼ�����*/
	void init() {	
		loginButton = new JButton("��¼");
		registerButton = new JButton("ע��");
		changePassword = new JButton("�޸�����");
		findMyKeyButton = new JButton("�һ�����");
		keepMyKeyCheckBox = new JCheckBox("��ס����");
		userLabel = new JLabel("�˻���");
		keyLabel = new JLabel("���룺");
		/*Icon icon = new ImageIcon("C:\\Users\\Administrator\\eclipse-workspace\\ChitChat\\src\\Windows\\01.jpg");
		loginButton.setIcon(icon);
		loginButton.setComponentPopupMenu(null);*/
		ID = new JTextField(10);
		ID.setToolTipText("�������˺�");
		key = new JPasswordField(10);
		hbox1 = Box.createHorizontalBox();
		hbox2 = Box.createHorizontalBox();
		vbox1 = Box.createVerticalBox();	
		vbox2 = Box.createVerticalBox();
		vbox3 = Box.createVerticalBox();
		
		vbox1.add(userLabel);
		vbox1.add(Box.createVerticalStrut(30));
		vbox1.add(keyLabel);	
		
		vbox2.add(ID);
		vbox2.add(Box.createVerticalStrut(20));
		vbox2.add(key);
		
		hbox1.add(loginButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(registerButton);
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(findMyKeyButton);	
		hbox1.add(Box.createHorizontalStrut(10));
		hbox1.add(changePassword);
		
		hbox2.add(vbox1);
		hbox2.add(Box.createHorizontalStrut(10));
		hbox2.add(vbox2);
		
		
		vbox3.add(Box.createVerticalStrut(50));
		vbox3.add(hbox2);
		vbox3.add(Box.createVerticalStrut(30));
		vbox3.add(keepMyKeyCheckBox);
		vbox3.add(Box.createVerticalStrut(30));
		vbox3.add(hbox1);
		add(vbox3);
		/*��Ӱ�ť����ļ�����*/
		Acts act = new Acts();
		Docs doc = new Docs();
		loginButton.addActionListener(act);
		registerButton.addActionListener(act);
		changePassword.addActionListener(act);
		findMyKeyButton.addActionListener(act);
		/*����ı���,�����ļ�����*/
		ID.addActionListener(act);
		ID.getDocument().addDocumentListener(doc);
		key.addActionListener(act);
		/*��ӵ�ѡ��ť�ļ�����*/
		keepMyKeyCheckBox.addActionListener(act);
		LookAndFeel.addLookAndFeel(vbox3);
	}
	class Acts implements ActionListener{

		int turnTo(ActionEvent e) {
			if(e.getSource()==ID)return 1;
			if(e.getSource() == key) return 2;
			if(e.getSource()==loginButton)return 3;
			if(e.getSource()==registerButton)return 4;
			if(e.getSource()==changePassword)return 5;
			if(e.getSource()==findMyKeyButton)return 6;
			if(e.getSource()==keepMyKeyCheckBox)return 7;
			else return -1;
			
		}
		boolean checkRepetitive(String id) {//��������id�Ƿ��Ѿ�������keys��.
			for(KeyKeep i : keys) {
				if(i.id.equals(id))return true;
			}
			return false;
		}
		boolean warning() {//�˺Ÿ�ʽ�Լ������Ƿ�Ϊ�ռ��
			if(!ID.getText().matches("[0-9]+"))
			{
				JOptionPane.showMessageDialog(null, "��ע���˺ŵĸ�ʽ(ȫ���������,����Ϊ��)!","",JOptionPane.PLAIN_MESSAGE);
				return true;
			}
			if(key.getPassword().length == 0)
			{
				JOptionPane.showMessageDialog(null, "����������!","",JOptionPane.PLAIN_MESSAGE);
				return true;
			}
			return false;
				
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int timeCircle = 20000;//ѭ�����յ� ����,����������ʾ���ӳ�ʱ.
			
			
			JPopupMenu wr = new JPopupMenu();
			JLabel idNull = new JLabel("�˺Ÿ�ʽ����Ŷ!");
			JLabel keyNull = new JLabel("����������!");
			
			switch(turnTo(arg0)) {
			case 1:{//ID
				
				if(!ID.getText().matches("[0-9]+"))
				{
					wr.add(idNull);
					wr.show(ID, 0,0);
					return;
				}
				key.requestFocus();
			}break;
			
			case 2:{//key
				if(key.getPassword().length == 0)
				{
					wr.add(keyNull);
					wr.show(key, 0, 0);
					return;
				}
			}//û��break
			case 3:{//loginButton
				if(warning())return;//�˺Ÿ�ʽ�Լ������Ƿ�Ϊ�ռ��
				OperateSQLServer oprt = new OperateSQLServer();
				oprt.connectToDatabase();
				ResultSet result = oprt.getPersonalInformation(Integer.parseInt(ID.getText()));

				try {
					result.next();
					String keyGot = result.getString(3);//����
					if(keyGot.equals(key.getText()))
					{	
						dispose();
						new MainWindow(Integer.parseInt(ID.getText()));
						byte[] info;
						info = ("2#"+ID.getText()+"#"+ChatWindow.getNetworkTime()+"#"+IP_address).getBytes();
						
						SendThread send = new SendThread("192.168.43.29",23334,info);
						new Thread(send).start();
						oprt.updateLoggingStatus(1, Integer.parseInt(ID.getText()));
						
					}
					else
					{
						JOptionPane.showMessageDialog(null, "�������!","",JOptionPane.PLAIN_MESSAGE);
						return;
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "�����ڸ��û�!","",JOptionPane.PLAIN_MESSAGE);
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Զ�̷�����IP����!","",JOptionPane.PLAIN_MESSAGE);
				}
				oprt.closeDatabase();
			}break;
			case 4:{//RegisterWindow
				new RegisterWindow();
			}break;
			case 5:{//changePassword
				 new PasswordEdit();
			}break;
			case 6:{//findkey
				new FindPasswordWindow();
			}break;
			case 7:{//keepMyKeyCheckBox
				if(warning())return ;//�˺Ÿ�ʽ�Լ������Ƿ�Ϊ�ռ��				
				if(checkRepetitive(ID.getText()))return;//��������id�Ƿ��Ѿ�������keys��.
				if(keepMyKeyCheckBox.isSelected())
				{
					KeyKeep myKey = KeyKeepOperate.pack(ID.getText(), key.getText());
					keys.add(myKey);
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
						out.writeObject(keys);
						out.flush();
						out.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�������.","",JOptionPane.PLAIN_MESSAGE);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "�����˺���Ϣ��ȡ����","",JOptionPane.PLAIN_MESSAGE);
					}
				}
				if(!keepMyKeyCheckBox.isSelected())
				{
					keys.remove(KeyKeepOperate.pack(ID.getText(), key.getText()));
					try {
						ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(user_info));
						out.writeObject(keys);
						out.flush();
						out.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "�˺���Ϣ�ļ�������.","",JOptionPane.PLAIN_MESSAGE);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "�����˺���Ϣ��ȡ����","",JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			}break;
			
			default: 
				
			}//switch
		}//override
		
	}//class
	class Docs implements DocumentListener{

		//boolean flag_for_ids_is_visible = true;
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			JPopupMenu ids = new JPopupMenu();
			for(KeyKeep i:keys) {
				if(i.id.startsWith(ID.getText())&& !i.id.equals(ID.getText()))
				{
					JMenuItem it = new JMenuItem(i.id);
					ids.add(it);
					it.addActionListener(e->{
						ID.setText(i.id);
						key.setText(new String(i.keySecured));
						keepMyKeyCheckBox.setSelected(true);
					});
				}
				
			}
			ids.revalidate();
			ids.show(ID,0,ID.getHeight());
			ID.requestFocus();
			
//			validate();
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			changedUpdate(arg0);
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			changedUpdate(arg0);
		}
		
	}
}