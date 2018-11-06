package CComponents;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JScrollBar;

import Client.Contacts;
import Client.MainWindow;
import Constants.Internet;

public class MessageReceive implements Runnable {//单例
	private static MessageReceive instance = null;
	private MessageReceive() {}//禁用构造方法
	
	public static MessageReceive getInstance() {
		if(instance == null) {
			instance = new MessageReceive();
			return instance;
		}
		return instance;
	}

	@Override
	public void run() {
		// 数据处理
		byte[] information = new byte[1024];
		DatagramPacket recvPacket = new DatagramPacket(information, information.length);
		DatagramSocket recvSocket = null;
		try {
			recvSocket = new DatagramSocket(50000);
			System.out.println("信息接受线程启动成功!");
		} catch (Exception e) {
			System.out.println("信息接受线程启动失败!");
			return;
		}
		while (true) {
			try {
				recvSocket.receive(recvPacket);
			} catch (IOException e) {
				System.out.println("信息接收错误!");
				break;
			}
			// 1+发送者ID+时间+目标ID+聊天文本消息
			if (recvPacket != null) {
				//System.out.println("111");
				String info =new String(recvPacket.getData(),0,recvPacket.getLength()).trim();// 注意
			//	System.out.println(new String(recvPacket.getData(),0,recvPacket.getLength()));
				/*
				*发送完毕后要清空,或者注意接收的长度,否则第二次发送的消息如果短于上次的,就会出现下面的情况:
				*第一次发送:"12345678"
				*第二次发送:"你好"
				*
				*第一次接收:"12345678"
				*第二次接收:"你好5678"
				*
				*/
				
				//System.out.println(information);
				int maxSplit = 2;
				String[] sourceStrArray = info.split("#", maxSplit);//mode,(senderID,time,sendeeID,text)
				
				int statement = Integer.parseInt(sourceStrArray[0]);
				info = sourceStrArray[1].trim();//senderID,time,sendeeID,text
		
				if (statement == 1) {// 收到文本
					sourceStrArray = info.split("#", maxSplit);// senderID,(time,sendeeID,text)
					String id = sourceStrArray[0];
					info = sourceStrArray[1].trim();
					if (MainWindow.ctsList.get(Integer.parseInt(id)).hasChatWin) {//打开了聊天页的话
						sourceStrArray = info.split("#", 3);//time,(sendeeID,text)
						MainWindow.ctsList.get(Integer.parseInt(id)).chatWindow.addNewMsg(sourceStrArray[2]);
					}
					if (MainWindow.ctsList.get(Integer.parseInt(id)).hasMessage) {//存在信息盒的话
						sourceStrArray = info.split("#",3);
						MainWindow.ctsList.get(Integer.parseInt(id)).message.refreshMsg(sourceStrArray[2]);
					}
					else {
						sourceStrArray = info.split("#",3);
						MainWindow.addNewMessage(Integer.parseInt(id), MainWindow.ID);
					}

				}
				if (statement == 4) {// 状态
					sourceStrArray = info.split("#", maxSplit);// ID
					if (sourceStrArray[0].equals("1"))// Internet.ONLINE
					{
						String id = sourceStrArray[1];
						Convasation i = MainWindow.ctsList.get(Integer.parseInt(id));
						i.onlineState = Internet.ONLINE;
						MainWindow.repaintContact(Integer.parseInt(id));
					}
					if (sourceStrArray[0].equals("0"))// offline
					{
						String id = sourceStrArray[1];
						Convasation i = MainWindow.ctsList.get(Integer.parseInt(id));
						i.onlineState = Internet.OFFLINE;
						MainWindow.repaintContact(Integer.parseInt(id));
					}
				}
				
			}
		}

	}
}
