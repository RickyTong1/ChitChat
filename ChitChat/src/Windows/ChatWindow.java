package Windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.*;
import fileTransportation.GetFilePath;
import fileTransportation.SendFile;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChatWindow extends JFrame {
	public static String filePath;
	static String time = "sjkla";
	String nick;
	JTextArea sendField; // �����ı���
	JButton send; // ���Ͱ�ť
	JButton qset;// ��������
	JButton file;// �ļ�
	Box box, hbox;
	Box chatBox;// ��װ������Ϣ�ĺ��ӣ��ӵ��������chatContent��
	JPanel chatContent;
	JScrollPane sp;
	JTextArea content;
	int contactID;
	int userID;
	String spoke;

	public ChatWindow(int ContactID, int userID) {
		this.contactID = ContactID;
		this.userID = userID;

		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.CHAT_CONTENT_QUEST;
		message.senderID = MainWindow.ID;
		message.totalCounts = 5;//��ʼĬ�J�L��
		message.targetID = ContactID;
		message.senderIP = Property.Property.NATIVE_IP;
		new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT, MessageBlobOperator.pack(message));
		// ���������¼.

		init();
		setLayout(null);
		setTitle("������Ϣ");
		setBounds(utils.Window.getMiddleWidth(500), utils.Window.getMiddleHeight(600), 500, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void init() {
		sendField = new JTextArea(10, 3);
		// TODO
		chatContent = new JPanel();
		chatContent.setBounds(0, 0, 489, 450);// �������λ�ô�С
		chatContent.setBackground(SystemColor.WHITE);// ���������ɫ
		chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));// ������������
		send = new JButton("����");
		qset = new JButton("��������");
		file = new JButton("�ļ�����");

		chatBox = Box.createVerticalBox();

		box = Box.createVerticalBox();
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));

		file.setBounds(5, 451, 100, 25);
		sendField.setBounds(5, 477, 400, 90);// ���÷����ı����λ�ô�С
		sendField.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

		send.setBounds(406, 542, 87, 25);// ���÷��Ͱ�ť��λ�úʹ�С
		qset.setBounds(406, 477, 87, 25);

		add(file);
		add(sendField);
		add(send);
		add(qset);
		sp = new JScrollPane(chatContent);// �������ɹ���
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
		sp.setBounds(0, 0, 489, 450);
		hbox.setBounds(0, 500, 500, 100);
		add(hbox);
		box.add(Box.createVerticalStrut(30));
		box.add(hbox);
		add(box);
		Acts a = new Acts();
		sendField.addKeyListener(a);
		send.addActionListener(a);
		qset.addActionListener(a);
		file.addActionListener(a);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (MainWindow.ctsList != null) {
					MainWindow.ctsList.get(contactID).hasChatWin = false;
					MainWindow.ctsList.get(contactID).chatWindow = null;
				}
				dispose();
			}
		});
	}

	class Acts implements ActionListener, KeyListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == send) {
				JPopupMenu pop = new JPopupMenu();
				JLabel nullWarning = new JLabel("������Ϣ����Ϊ��!");
				JLabel outWarning = new JLabel("�����Ķι���!(�벻Ҫ����255��)");

				if (sendField.getText().equals(""))// ������ϢΪ��ʱ
				{
					pop.add(nullWarning);
					pop.show(send, -(sendField.getWidth()), -sendField.getHeight());
					// ��ʾ��ʾλ��ΪsendField���м��Ϸ�
					new Thread() {
						@Override
						public void run() {
							try {
								sleep(1000);
							} catch (InterruptedException e) {
							}
							pop.setVisible(false);
						}
					}.start();// �������ʾһ����Լ���ʧ

					sendField.requestFocus();
					return;
				}
				if (sendField.getText().length() >= 255) {
					pop.add(outWarning);
					pop.show(send, -(sendField.getWidth()), -sendField.getHeight());
					sendField.setText("");
					return;
				}
				time = getNetworkTime();
				content = new JTextArea(sendField.getText());
				content.setLineWrap(true);
				content.setBackground(Color.CYAN);
				content.setEditable(false);
				chatBox.add(new JLabel("<html>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+ time + "<br></html>"));
				chatBox.add(content);
				// chatContent.add(new JLabel(time));
				chatContent.add(chatBox);
				chatContent.validate();// ˢ��������
				// send
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.CHAT_TEXT;
				message.senderID = MainWindow.ID;
				message.senderIP = Property.Property.NATIVE_IP;
				message.targetID = contactID;
				message.text = sendField.getText();

				new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT,
						MessageBlobOperator.pack(message));

				/* ���ù�����һֱ�����·� */
				JScrollBar vertical = sp.getVerticalScrollBar();
				vertical.setValue(vertical.getMinimum());
				sendField.setText("");
			}
			if(e.getSource() == qset) {//��������
				
			}
			if(e.getSource() == file) {//�ļ�����
				new GetFilePath();
				MessageBlob message = new MessageBlob();
				message.type = MessageBlobType.RECEIVE_FILE;//�÷�����������Ϣ
				message.senderIP = Property.Property.NATIVE_IP;
				message.senderID = MainWindow.ID;
				message.targetID = contactID;
				//message.fileName = filePath.
				message.fileName = GetFilePath.fileName;
				new SendMessage(Property.Property.SERVER_IP
						,SocketConstants.FILE_GRN_PORT
						,MessageBlobOperator.pack(message));
				
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

	/* ��ȡ����ʱ�� */
	public static String getNetworkTime() {

		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		return dateFormat.format(date);

	}

	public void addNewMsg(int id,String nick,String msg) {
		time = getNetworkTime();
		content = new JTextArea(msg);
		content.setLineWrap(true);
		if(id == MainWindow.ID) {
			content.setBackground(Color.CYAN);
			nick = "��";
		}		
		else
			content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);
		chatBox.add(new JLabel("<html>" + nick
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + time + "<br></html>"));
		chatBox.add(content);
		// chatContent.add(new JLabel(time));
		chatContent.add(chatBox);
		chatContent.repaint();
		chatContent.revalidate();// ˢ��������
		this.requestFocus();
		sendField.requestFocus();
	}

}
