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

public class MessageReceive extends Thread {// 单例
	private static MessageReceive instance = null;

	private MessageReceive() {
		this.start();
	}// 禁用构造方法

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
		// 数据处理
		byte[] information = new byte[SocketConstants.MESSAGE_LENGTH];
		DatagramPacket recvPacket = new DatagramPacket(information, information.length);
		DatagramSocket recvSocket = null;

		try {
			recvSocket = new DatagramSocket(SocketConstants.GENERAL_PORT);
			System.out.println("信息接受线程启动成功!");
			while (true) {
				try {
					
					recvSocket.receive(recvPacket);
					System.out.println("接收到信息!");
				} catch (IOException e) {
					System.out.println("信息接收错误!");
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
			System.out.println("信息接受线程启动失败.");
		}catch(Exception ee ) {
			System.out.println("其他地方错误 MR_EXCEPTION_EE.");
		}

	}

}
