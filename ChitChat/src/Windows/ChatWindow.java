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
	public ChatWindow(int ContactID,int userID,String lastSpoke) {
		this.contactID = ContactID;
		this.userID = userID;
		spoke = lastSpoke;
		OperateSQLServer oprt = new OperateSQLServer();
		oprt.connectToDatabase();
		ResultSet rs = oprt.getPersonalInformation(contactID);
		try {
			rs.next();
			nick = rs.getString(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init();
		setLayout(null);
		setTitle("聊天信息");
		setBounds(Window.getMiddleWidth(500), Window.getMiddleHeight(600), 500, 600);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Data d = new Data();
		new Thread(d).start();
		
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
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				MainWindow.ctsList.get(contactID).hasChatWin = false;
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
			return;
		}
		if (sendField.getText().length() >= 255)
		{
			pop.add(outWarning);
			pop.show(send, -(sendField.getWidth()), -sendField.getHeight());
			sendField.setText("");
			return ;
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
		//send
		byte[] info = (sendField.getText()).getBytes();
		OperateSQLServer oss = new OperateSQLServer();
		oss.connectToDatabase();
		try {
			SendThread sd = new SendThread("192.168.43.29",23333,info);
			new Thread(sd).start();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//reseive
		//new Thr
		
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
	public void addNewMsg(String msg){
		time = getNetworkTime();
		content = new JTextArea(msg);
		content.setLineWrap(true);
		content.setBackground(Color.LIGHT_GRAY);
		content.setEditable(false);
		chatBox.add(new JLabel("<html>"+nick+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + time
				+ "<br></html>"));
		chatBox.add(content);
		// chatContent.add(new JLabel(time));
		chatContent.add(chatBox);
		chatContent.repaint();
		chatContent.revalidate();// 刷新面板组件
		add(chatContent);
	}
	public class Data implements Runnable {
		@Override
		public void run() {
			// 数据处理
			byte[] information = new byte[1024];
			DatagramPacket recvPacket = new DatagramPacket(information, information.length);
			DatagramSocket recvSocket = null;
			try {
				recvSocket = new DatagramSocket();
				System.out.println("信息接受线程启动成功!");
			} catch (SocketException e) {
				System.out.println("信息接受线程启动失败!");
			}
			while (true) {
				try {
					recvSocket.receive(recvPacket);
				} catch (IOException e) {
					System.out.println("信息接收错误!");
				}
				if (recvPacket != null) {
					information = recvPacket.getData();
					String info = new String(information);
					int maxSplit = 4;
					String[] sourceStrArray = info.split("#", maxSplit);
					info = sourceStrArray[3];
					if (MainWindow.ctsList.get(Integer.parseInt(sourceStrArray[1])).hasChatWin) {
						sourceStrArray = info.split("#", maxSplit);
						addNewMsg(sourceStrArray[1]);
					}
				
//					int statement = Integer.parseInt(sourceStrArray[0]);
//					info = sourceStrArray[1].trim();
//					if (statement == 1) {// 收到文本
//						maxSplit = 4;
//						sourceStrArray = info.split("#", maxSplit);// ID
//						String id = sourceStrArray[0];
//						if (MainWindow.ctsList.get(Integer.parseInt(id)).hasChatWin) {
//							sourceStrArray = info.split("#", maxSplit);
//							MainWindow.ctsList.get(Integer.parseInt(id)).chatwindow.addNewMsg(sourceStrArray[1]);
//						}
//						if (MainWindow.ctsList.get(Integer.parseInt(id)).hasMessage) {
//							sourceStrArray = info.split("#", maxSplit);
//							MainWindow.ctsList.get(Integer.parseInt(id)).message.refreshMsg(sourceStrArray[1]);
//						}
						

					}
//					if (statement == 4) {// 状态
//						sourceStrArray = info.split("#", maxSplit);// ID
//						if (sourceStrArray[0].equals("1"))// Internet.ONLINE
//						{
//							String id = sourceStrArray[1];
//							MainWindow.ctsList.get(Integer.parseInt(id)).onlineState = Internet.ONLINE;
//						}
//						if (sourceStrArray[0].equals("0"))// offline
//						{
//							String id = sourceStrArray[1];
//							MainWindow.ctsList.get(Integer.parseInt(id)).onlineState = Internet.OFFLINE;
//						}
//					}

//				}
			}
		}
	}
}
