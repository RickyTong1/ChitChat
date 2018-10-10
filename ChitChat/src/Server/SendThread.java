package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendThread implements Runnable {
	final static int PORT = 23333;
	final static int info_length = 1024; 
	InetAddress address;
	String IP;
	int port;
	byte[] information = new byte[info_length];
	DatagramPacket sendPacket;
	DatagramSocket sendSocket;
	public SendThread(String IP, int port, byte[] information) throws UnknownHostException {
		this.IP = IP;
		address = InetAddress.getByName(IP);
		this.port = port;
		this.information = information;
	}
	public SendThread(DatagramPacket packet) {
		information = packet.getData();
	}
	@Override
	public void run() {
		try {
			sendPacket = new DatagramPacket(information, information.length, address, port);
			sendPacket.setData(information);
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
			sendSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
