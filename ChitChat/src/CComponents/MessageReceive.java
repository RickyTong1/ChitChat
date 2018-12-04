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
		byte[] information = new byte[1024*8];
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
			MessageBlob recvMessage = null;
			byte obj[] = recvPacket.getData();
			recvMessage = MessageBlobOperator.unpack(obj);
			

		}

	}
	
}
