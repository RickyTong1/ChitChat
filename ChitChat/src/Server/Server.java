package Server;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class Server{
	
//	int statement;
//	public boolean parseInformation(byte[] information) {return true;}
	JFrame serverFrame; //服务器窗口
	JLabel IPLabel;	//服务器本地IP标签
	JLabel PortLabel; //服务器本地Port标签
	JLabel onlineNumberLabel; //在线人数标签
	JLabel totalNumberLabel; //系统总用户标签
	JLabel nowTimeLabel; //系统当前时间标签
	JTextField IPField; //服务器本地IP区
	JTextField PortField; //服务器本地Port区
	JTextField onlineNumberField; //在线人数区
	JTextField totalNumberField; //系统总用户区
	JTextField nowTimeField; //系统当前时间区
	JTextArea chatArea; //已发送消息域
	JTextArea typingArea; //待发送消息域
	JScrollPane chatSPanel;	//已发送消息滚动面板
	JScrollPane typingSPanel; //未发送消息滚动面板
	JButton send; //发送按钮

	public Server() {

		serverFrame = new JFrame("服务端");
		IPLabel = new JLabel("本地IP:");
		PortLabel = new JLabel("本地Port:");
		onlineNumberLabel = new JLabel("在线人数:");
		totalNumberLabel = new JLabel("总用户人数:");
		nowTimeLabel = new JLabel("当前时间:");
		IPField = new JTextField(13);
		PortField = new JTextField(7);
		onlineNumberField = new JTextField(5);
		totalNumberField = new JTextField(5);
		nowTimeField = new JTextField(10);
		//填充第一行IP和Port显示端口,用Box布局
		Box oneRol = Box.createHorizontalBox();
		oneRol.add(IPLabel);
		oneRol.add(Box.createHorizontalStrut(5));
		oneRol.add(IPField);
		oneRol.add(Box.createHorizontalStrut(120));
		oneRol.add(PortLabel);
		oneRol.add(Box.createHorizontalStrut(5));
		oneRol.add(PortField);
		oneRol.add(Box.createHorizontalStrut(30));
		//填充第二行在线人数、总用户数、当前时间显示窗口,用Box布局
		Box twoRol = Box.createHorizontalBox();
		twoRol.add(onlineNumberLabel);	
		twoRol.add(Box.createHorizontalStrut(5));
		twoRol.add(onlineNumberField);
		twoRol.add(Box.createHorizontalStrut(20));
		twoRol.add(totalNumberLabel);
		twoRol.add(Box.createHorizontalStrut(5));
		twoRol.add(totalNumberField);
		twoRol.add(Box.createHorizontalStrut(20));
		twoRol.add(nowTimeLabel);
		twoRol.add(Box.createHorizontalStrut(5));
		twoRol.add(nowTimeField);
		//用垂直布局Box填充第一行和第二行窗口显示的组件
		Box topBox = Box.createVerticalBox();
		topBox.add(Box.createVerticalStrut(20));
		topBox.add(oneRol);
		topBox.add(Box.createVerticalStrut(10));
		topBox.add(twoRol);
		//用BorderLayout布局的面板center将监控窗口放到NORTH位置
		JPanel centerPanel = new JPanel(); 
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(topBox, BorderLayout.NORTH);
		//用垂直布局的Box将聊天域和编辑文本域组装起来，并放在centerPanel面板center位置
		Box centerBox = Box.createVerticalBox();
		chatArea = new JTextArea(7,8);
		typingArea = new JTextArea();
		chatArea.setEditable(false);
		typingArea.setLineWrap(true);
		typingArea.setWrapStyleWord(true);
		chatSPanel = new JScrollPane(chatArea);
		typingSPanel = new JScrollPane(typingArea);
		centerBox.add(Box.createVerticalStrut(20));
		centerBox.add(chatSPanel);
		centerBox.add(Box.createVerticalStrut(30));
		centerBox.add(typingSPanel);
		centerBox.add(Box.createVerticalStrut(10));
		//将发送按钮放在centerPanel面板south位置并居中显示
		centerPanel.add(centerBox, BorderLayout.CENTER);
		send = new JButton("发送");
		//send.setContentAreaFilled(false);
		centerBox.add(send, BorderLayout.CENTER);
		send.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerBox.add(Box.createVerticalStrut(70));
		//将叠加好的组件放到主面板的center位置
		serverFrame.add(centerPanel);
		//控制center左右两边的空隙，优化界面
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		panelLeft.setPreferredSize(new Dimension(40, 600));
		panelRight.setPreferredSize(new Dimension(40, 600));
		serverFrame.add(panelLeft, BorderLayout.WEST);
		serverFrame.add(panelRight, BorderLayout.EAST);
		//将窗口显示出来并设置好不可调整大小
		serverFrame.setVisible(true);
		serverFrame.setBounds(330, 70, 700, 600);
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverFrame.setResizable(false);
		//给组件装监听器
		send.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == send && typingArea.getText()!=null) {	

					String statement = "1";
					String userID = "007";
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
					String time = dateFormat.format(new Date());
					String information = statement+"#"+userID+"#"+time+"#"+typingArea.getText();
					try {
						SendThread st = new SendThread("127.0.0.1", 23334, information.getBytes());
						Thread sendThread = new Thread(st);
						sendThread.start();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}	
					typingArea.setText("");
				}		
			}
		});
	}
	
	public void init() throws IOException{
		try {
			String address = InetAddress.getLocalHost().getHostAddress().toString();
			IPField.setText(" "+address);
			PortField.setText(" "+"23333");
			Timer timer = new Timer();
			timer.schedule(new MyTask(), 0, 1000); //timer实时更新在线人数、总用户数、当前时间
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//数据处理
		byte[] imformation = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(imformation, imformation.length);
		try {
			
			while(true) {
				DatagramSocket recvSocket = new DatagramSocket(23334);
				recvSocket.receive(recvPacket);
				recvSocket.close();
				if(recvPacket != null) {
									
				}			
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	class MyTask extends TimerTask {		 
			@Override
			public void run() {
				onlineNumberField.setText(" "+String.valueOf(0));
				totalNumberField.setText(" "+String.valueOf(0));
				String webUrl = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
				GetNetworkTime getNetworkTime = new GetNetworkTime();
				//nowTimeField.setText(" "+getNetworkTime.getWebsiteDatetime(webUrl));
			} 
		}
	public static void main(String args[]) throws IOException {
		Server server = new Server();
		server.init();
	}

}
