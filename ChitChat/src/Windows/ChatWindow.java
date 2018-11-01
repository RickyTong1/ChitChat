package Windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import Client.MainWindow;
import Constants.*;
import Constants.Window;
import DataBaseOperation.OperateSQLServer;
import Server.SendThread;

public class ChatWindow extends JFrame implements ActionListener {
	static String time = "sjkla";
	String nick;
	JTextField sendField; // 发送文本区
	JButton send; // 发送按钮
	Box box, hbox;
	Box chatBox;// 封装聊天消息的盒子，加到聊天面板chatContent中
	JPanel chatContent;
	JScrollPane sp;
	JTextArea content;
	int contactID;
	int userID;
	String spoke;

	public ChatWindow(int ContactID, int userID, String lastSpoke) {
		this.contactID = ContactID;
		this.userID = userID;
		spoke = lastSpoke;
		// OperateSQLServer oprt = new OperateSQLServer();
		// oprt.connectToDatabase();
		// ResultSet rs = oprt.getPersonalInformation(contactID);
		// try {
		// rs.next();
		// nick = rs.getString(2);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// TODO Server rebuild.
		init();
		setLayout(null);
		setTitle("聊天信息");
		setBounds(Window.getMiddleWidth(500), Window.getMiddleHeight(600), 500, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void init() {
		sendField = new JTextField(10);
		chatContent = new JPanel();
		chatContent.setBounds(0, 0, 489, 489);// 设置面板位置大小
		chatContent.setBackground(SystemColor.WHITE);// 设置面板颜色
		chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));// 设置面板左对齐
		send = new JButton("发送");
		chatBox = Box.createVerticalBox();
		box = Box.createVerticalBox();
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));
		sendField.setBounds(70, 510, 300, 30);// 设置发送文本框的位置大小
		send.setBounds(380, 510, 60, 30);// 设置发送按钮的位置和大小
		add(sendField);// 添加发送文本框
		add(send);// 添加发送按钮
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
		sendField.addActionListener(this);
		send.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				MainWindow.ctsList.get(contactID).hasChatWin = false;
				MainWindow.ctsList.get(contactID).chatWindow = null;
				dispose();
			}
		});
	}

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
		chatBox.add(new JLabel("<html>我&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + time
				+ "<br></html>"));
		chatBox.add(content);
		// chatContent.add(new JLabel(time));
		chatContent.add(chatBox);
		chatContent.validate();// 刷新面板组件
		// send
		byte[] info = ("1#" + String.valueOf(contactID) + "#" + ChatWindow.getNetworkTime() + "#"
				+ String.valueOf(contactID) + "#" + (sendField.getText())).getBytes();
		// 1+发送者ID+时间+目标ID+聊天文本消息
		try {
			// TODO 这里不能用ip
			/// SendThread sd = new SendThread("192.168.43.29",23333,info);
			SendThread sd = new SendThread("127.0.0.1", 50000, info);
			new Thread(sd).start();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		info = null;
		
		

		/* 设置滚动条一直在最下方 */
		JScrollBar vertical = sp.getVerticalScrollBar();
		vertical.setValue(vertical.getMinimum());
		sendField.setText("");
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
	}

}
