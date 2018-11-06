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
		byte[] information = new byte[1024];
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
			// 1+������ID+ʱ��+Ŀ��ID+�����ı���Ϣ
			if (recvPacket != null) {
				//System.out.println("111");
				String info =new String(recvPacket.getData(),0,recvPacket.getLength()).trim();// ע��
			//	System.out.println(new String(recvPacket.getData(),0,recvPacket.getLength()));
				/*
				*������Ϻ�Ҫ���,����ע����յĳ���,����ڶ��η��͵���Ϣ��������ϴε�,�ͻ������������:
				*��һ�η���:"12345678"
				*�ڶ��η���:"���"
				*
				*��һ�ν���:"12345678"
				*�ڶ��ν���:"���5678"
				*
				*/
				
				//System.out.println(information);
				int maxSplit = 2;
				String[] sourceStrArray = info.split("#", maxSplit);//mode,(senderID,time,sendeeID,text)
				
				int statement = Integer.parseInt(sourceStrArray[0]);
				info = sourceStrArray[1].trim();//senderID,time,sendeeID,text
		
				if (statement == 1) {// �յ��ı�
					sourceStrArray = info.split("#", maxSplit);// senderID,(time,sendeeID,text)
					String id = sourceStrArray[0];
					info = sourceStrArray[1].trim();
					if (MainWindow.ctsList.get(Integer.parseInt(id)).hasChatWin) {//��������ҳ�Ļ�
						sourceStrArray = info.split("#", 3);//time,(sendeeID,text)
						MainWindow.ctsList.get(Integer.parseInt(id)).chatWindow.addNewMsg(sourceStrArray[2]);
					}
					if (MainWindow.ctsList.get(Integer.parseInt(id)).hasMessage) {//������Ϣ�еĻ�
						sourceStrArray = info.split("#",3);
						MainWindow.ctsList.get(Integer.parseInt(id)).message.refreshMsg(sourceStrArray[2]);
					}
					else {
						sourceStrArray = info.split("#",3);
						MainWindow.addNewMessage(Integer.parseInt(id), MainWindow.ID);
					}

				}
				if (statement == 4) {// ״̬
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
