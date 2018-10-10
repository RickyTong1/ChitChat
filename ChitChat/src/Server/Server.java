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
	JFrame serverFrame; //����������
	JLabel IPLabel;	//����������IP��ǩ
	JLabel PortLabel; //����������Port��ǩ
	JLabel onlineNumberLabel; //����������ǩ
	JLabel totalNumberLabel; //ϵͳ���û���ǩ
	JLabel nowTimeLabel; //ϵͳ��ǰʱ���ǩ
	JTextField IPField; //����������IP��
	JTextField PortField; //����������Port��
	JTextField onlineNumberField; //����������
	JTextField totalNumberField; //ϵͳ���û���
	JTextField nowTimeField; //ϵͳ��ǰʱ����
	JTextArea chatArea; //�ѷ�����Ϣ��
	JTextArea typingArea; //��������Ϣ��
	JScrollPane chatSPanel;	//�ѷ�����Ϣ�������
	JScrollPane typingSPanel; //δ������Ϣ�������
	JButton send; //���Ͱ�ť

	public Server() {

		serverFrame = new JFrame("�����");
		IPLabel = new JLabel("����IP:");
		PortLabel = new JLabel("����Port:");
		onlineNumberLabel = new JLabel("��������:");
		totalNumberLabel = new JLabel("���û�����:");
		nowTimeLabel = new JLabel("��ǰʱ��:");
		IPField = new JTextField(13);
		PortField = new JTextField(7);
		onlineNumberField = new JTextField(5);
		totalNumberField = new JTextField(5);
		nowTimeField = new JTextField(10);
		//����һ��IP��Port��ʾ�˿�,��Box����
		Box oneRol = Box.createHorizontalBox();
		oneRol.add(IPLabel);
		oneRol.add(Box.createHorizontalStrut(5));
		oneRol.add(IPField);
		oneRol.add(Box.createHorizontalStrut(120));
		oneRol.add(PortLabel);
		oneRol.add(Box.createHorizontalStrut(5));
		oneRol.add(PortField);
		oneRol.add(Box.createHorizontalStrut(30));
		//���ڶ����������������û�������ǰʱ����ʾ����,��Box����
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
		//�ô�ֱ����Box����һ�к͵ڶ��д�����ʾ�����
		Box topBox = Box.createVerticalBox();
		topBox.add(Box.createVerticalStrut(20));
		topBox.add(oneRol);
		topBox.add(Box.createVerticalStrut(10));
		topBox.add(twoRol);
		//��BorderLayout���ֵ����center����ش��ڷŵ�NORTHλ��
		JPanel centerPanel = new JPanel(); 
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(topBox, BorderLayout.NORTH);
		//�ô�ֱ���ֵ�Box��������ͱ༭�ı�����װ������������centerPanel���centerλ��
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
		//�����Ͱ�ť����centerPanel���southλ�ò�������ʾ
		centerPanel.add(centerBox, BorderLayout.CENTER);
		send = new JButton("����");
		//send.setContentAreaFilled(false);
		centerBox.add(send, BorderLayout.CENTER);
		send.setAlignmentX(Component.CENTER_ALIGNMENT);
		centerBox.add(Box.createVerticalStrut(70));
		//�����Ӻõ�����ŵ�������centerλ��
		serverFrame.add(centerPanel);
		//����center�������ߵĿ�϶���Ż�����
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		panelLeft.setPreferredSize(new Dimension(40, 600));
		panelRight.setPreferredSize(new Dimension(40, 600));
		serverFrame.add(panelLeft, BorderLayout.WEST);
		serverFrame.add(panelRight, BorderLayout.EAST);
		//��������ʾ���������úò��ɵ�����С
		serverFrame.setVisible(true);
		serverFrame.setBounds(330, 70, 700, 600);
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverFrame.setResizable(false);
		//�����װ������
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
			timer.schedule(new MyTask(), 0, 1000); //timerʵʱ�����������������û�������ǰʱ��
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//���ݴ���
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
				String webUrl = "http://www.ntsc.ac.cn";//�й���ѧԺ������ʱ����
				GetNetworkTime getNetworkTime = new GetNetworkTime();
				//nowTimeField.setText(" "+getNetworkTime.getWebsiteDatetime(webUrl));
			} 
		}
	public static void main(String args[]) throws IOException {
		Server server = new Server();
		server.init();
	}

}
