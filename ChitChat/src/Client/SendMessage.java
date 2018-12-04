
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;

public class SendMessage{

	
	private String IP;
	private int port;
	private byte[] message = new byte[Constants.SocketConstants.MESSAGE_LENGTH];
	private InetAddress address;
	private DatagramPacket sendPacket;
	private DatagramSocket sendSocket;
	//构造方法一
	public SendMessage(String IP, int port, byte[] message) {
		try {
			address = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			System.out.println("getAddress Failed!");
		}
		this.port = port;
		this.message = message;
		sendMessage();
	}
	//构造方法二
	public SendMessage(String IP, int port, MessageBlob messageBlob) {
		try {
			address = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			System.out.println("getAddress Failed!");
		}
		this.port = port;
		this.message = MessageBlobOperator.pack(messageBlob);
		//System.out.println(messageBlob.text);
		sendMessage();
	}
		
	public void sendMessage() {
		sendPacket = new DatagramPacket(this.message, this.message.length, this.address, this.port);
		try {
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
			sendSocket.close();
			System.out.println("Send message Successfully!");
		} catch (SocketException e) {
			System.out.println("Bind port Failed!");
		} catch (IOException e) {
			System.out.println("Send Packet Failed!");
		}
	}

}
