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
	JTextField sendField; // �����ı���
	JButton send; // ���Ͱ�ť
	Box box, hbox;
	Box chatBox;// ��װ������Ϣ�ĺ��ӣ��ӵ��������chatContent��
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
		setTitle("������Ϣ");
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
		chatContent.setBounds(0, 0, 489, 489);// �������λ�ô�С
		chatContent.setBackground(SystemColor.WHITE);// ���������ɫ
		chatContent.setLayout(new FlowLayout(FlowLayout.LEFT));// ������������
		send = new JButton("����");
		chatBox = Box.createVerticalBox();
		box = Box.createVerticalBox();
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));
		sendField.setBounds(70, 510, 300, 30);// ���÷����ı����λ�ô�С
		send.setBounds(380, 510, 60, 30);// ���÷��Ͱ�ť��λ�úʹ�С
		add(sendField);// ��ӷ����ı���
		add(send);// ��ӷ��Ͱ�ť
		sp = new JScrollPane(chatContent);// �������ɹ���
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
		JLabel nullWarning = new JLabel("������Ϣ����Ϊ��!");
		JLabel outWarning = new JLabel("�����Ķι���!(�벻Ҫ����255��)");
		
		if (sendField.getText().equals(""))// ������ϢΪ��ʱ
		{
			pop.add(nullWarning);
			pop.show(send, -(sendField.getWidth()), -sendField.getHeight());
			// ��ʾ��ʾλ��ΪsendField���м��Ϸ�
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
		chatBox.add(new JLabel("<html>��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + time
				+ "<br></html>"));
		chatBox.add(content);
		// chatContent.add(new JLabel(time));
		chatContent.add(chatBox);
		chatContent.validate();// ˢ��������
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
		
		/* ���ù�����һֱ�����·� */
		JScrollBar vertical = sp.getVerticalScrollBar();
		vertical.setValue(vertical.getMinimum());
		sendField.setText("");
	}

	
	/* ��ȡ����ʱ�� */
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
		chatContent.revalidate();// ˢ��������
		add(chatContent);
	}
	public class Data implements Runnable {
		@Override
		public void run() {
			// ���ݴ���
			byte[] information = new byte[1024];
			DatagramPacket recvPacket = new DatagramPacket(information, information.length);
			DatagramSocket recvSocket = null;
			try {
				recvSocket = new DatagramSocket();
				System.out.println("��Ϣ�����߳������ɹ�!");
			} catch (SocketException e) {
				System.out.println("��Ϣ�����߳�����ʧ��!");
			}
			while (true) {
				try {
					recvSocket.receive(recvPacket);
				} catch (IOException e) {
					System.out.println("��Ϣ���մ���!");
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
//					if (statement == 1) {// �յ��ı�
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
//					if (statement == 4) {// ״̬
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
