package CComponents;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JScrollBar;

import Client.Contacts;
import Client.MainWindow;
import Constants.Internet;

public class MessageReceive implements Runnable {//����
	private static MessageReceive instance = null;
	private MessageReceive() {}//���ù��췽��
	
	public static MessageReceive getInstance() {
		if(instance == null) {
			instance = new MessageReceive();
			return instance;
		}
		return instance;
	}

	@Override
	public void run() {
		// ���ݴ���
		byte[] information = new byte[1024*8];
		DatagramPacket recvPacket = new DatagramPacket(information, information.length);
		DatagramSocket recvSocket = null;
		try {
			recvSocket = new DatagramSocket(50000);
			System.out.println("��Ϣ�����߳������ɹ�!");
		} catch (Exception e) {
			System.out.println("��Ϣ�����߳�����ʧ��!");
			return;
		}
		while (true) {
			try {
				recvSocket.receive(recvPacket);
			} catch (IOException e) {
				System.out.println("��Ϣ���մ���!");
				break;
			}
			MessageBlob recvMessage = null;
			byte obj[] = recvPacket.getData();
			recvMessage = MessageBlobOperator.unpack(obj);
			

		}

	}
	
}
