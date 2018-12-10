package CComponents;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JScrollBar;

import Client.Contacts;
import Client.MainWindow;
import Constants.Internet;
import Constants.SocketConstants;
import utils.ClientTranslation;

public class MessageReceive extends Thread {// ����
	private static MessageReceive instance = null;

	private MessageReceive() {
		this.start();
	}// ���ù��췽��

	public static MessageReceive getInstance() {
		if (instance == null) {
			instance = new MessageReceive();
			return instance;
		}
		return instance;
	}

	public static void destroyMRcv() {
		instance.destroy();
	}

	@Override
	public void run() {
		// ���ݴ���
		byte[] information = new byte[SocketConstants.MESSAGE_LENGTH];
		DatagramPacket recvPacket = new DatagramPacket(information, information.length);
		DatagramSocket recvSocket = null;

		try {
			recvSocket = new DatagramSocket(SocketConstants.GENERAL_PORT);
			System.out.println("��Ϣ�����߳������ɹ�!");
			while (true) {
				try {
					
					recvSocket.receive(recvPacket);
					System.out.println("���յ���Ϣ!");
				} catch (IOException e) {
					System.out.println("��Ϣ���մ���!");
					break;
				}
				MessageBlob recvMessage = null;
				byte obj[] = recvPacket.getData();

				recvMessage = MessageBlobOperator.unpack(obj);
				
	//				System.out.println("MR_EXCEPTION_E.");
				try{
					ClientTranslation.typeTrans(recvMessage);
				}catch(Exception e) {
					e.printStackTrace();

				}
			}
		} catch (SocketException e1) {
			System.out.println("��Ϣ�����߳�����ʧ��.");
		}catch(Exception ee ) {
			System.out.println("�����ط����� MR_EXCEPTION_EE.");
		}

	}

}
