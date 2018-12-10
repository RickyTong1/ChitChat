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

public class ChatWindow extends JFrame {
	static String time = "sjkla";
	String nick;
	JTextArea sendField; // 发送文本区
	JButton send; // 发送按钮
	JButton qset;//快速设置
	JButton file;//文件
	Box box, hbox;
	Box chatBox;// 封装聊天消息的盒子，加到聊天面板chatContent中
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
		message.targetID = ContactID;
		message.senderIP = Property.Property.NATIVE_IP;
		new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT, MessageBlobOperator.pack(message));
		// 请求聊天记录.

		init();
		setLayout(null);
		setTitle("聊天信息");
		setBounds(utils.Window.getMiddleWidth(500), utils.Window.getMiddleHeight(600), 500, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void init() {
		sendField = new JTextArea(10, 3);
		// TODO
		chatContent = new JPanel();
		chatContent.setBounds(0, 0, 489, 489);// 设置面板位置大小
		chatContent.setBackground(SystemColor.WHITE);// 设置面板颜色
		chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));// 设置面板左对齐
		send = new JButton("发送");
		qset = new JButton("快速设置");
		file = new JButton("文件发送");
		chatBox = Box.createVerticalBox();
		box = Box.createVerticalBox();
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));
		sendField.setBounds(70, 510, 300, 30);// 设置发送文本框的位置大小
		send.setBounds(380, 510, 60, 30);// 设置发送按钮的位置和大小
		add(file);
		add(sendField);// 添加发送文本框
		Box send_and_qset = Box.createVerticalBox();
		send_and_qset.add(send);// 添加发送按钮
		send_and_qset.add(Box.createVerticalStrut(20));
		send_and_qset.add(qset);
		add(send_and_qset);
		sp = new JScrollPane(chatContent);// 设置面板可滚动
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(sp);
		sp.setBounds(0, 0, 489, 489);
		hbox.setBounds(0, 500, 500, 100);
		add(hbox);
		box.add(Box.createVerticalStrut(30));
		box.add(hbox);
		add(box);
		Acts a = new Acts();
		sendField.addKeyListener(a);
		send.addActionListener(a);
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

	class Acts implements ActionListener,KeyListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JPopupMenu pop = new JPopupMenu();
			JLabel nullWarning = new JLabel("发送消息不能为空!");
			JLabel outWarning = new JLabel("发送文段过长!(请不要超过255字)");

			if (sendField.getText().equals(""))// 发送消息为空时
			{
				pop.add(nullWarning);
				pop.show(send, -(sendField.getWidth()), -sendField.getHeight());
				// 警示显示位置为sendField的中间上方
				new Thread() {
					@Override
					public void run() {
						try {
							sleep(1000);
						} catch (InterruptedException e) {
						}
						pop.setVisible(false);
					}
				}.start();// 让这个提示一秒后自己消失

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
			chatBox.add(new JLabel("<html>我&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ time + "<br></html>"));
			chatBox.add(content);
			// chatContent.add(new JLabel(time));
			chatContent.add(chatBox);
			chatContent.validate();// 刷新面板组件
			// send
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.CHAT_TEXT;
			message.senderID = MainWindow.ID;
			message.senderIP = Property.Property.NATIVE_IP;
			message.targetID = contactID;
			message.text = sendField.getText();

			new SendMessage(Property.Property.SERVER_IP, SocketConstants.GENERAL_PORT,
					MessageBlobOperator.pack(message));

			/* 设置滚动条一直在最下方 */
			JScrollBar vertical = sp.getVerticalScrollBar();
			vertical.setValue(vertical.getMinimum());
			sendField.setText("");
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

	/* 获取网络时间 */
	public static String getNetworkTime() {

		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		return dateFormat.format(date);

	}

	public void addNewMsg(String msg) {
		time = getNetworkTime();
		content = new JTextArea(msg);
		content.setLineWrap(true);
		content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);
		chatBox.add(new JLabel("<html>" + nick
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + time + "<br></html>"));
		chatBox.add(content);
		// chatContent.add(new JLabel(time));
		chatContent.add(chatBox);
		chatContent.repaint();
		chatContent.revalidate();// 刷新面板组件
		this.requestFocus();
		sendField.requestFocus();
	}

}
