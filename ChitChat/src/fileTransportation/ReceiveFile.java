package fileTransportation;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;

public class ReceiveFile extends Thread {
	private String dir;// 文件保存路径，在main函数中设置
	private int port;// Socket服务器的端口
	public static int percent=0;//文件传输进度
	JFrame barWindow = new JFrame();
	JProgressBar progressBar;
	public ReceiveFile(String dir, int port) {
		this.dir = dir;
		this.port = port;
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setBackground(Color.CYAN);
	
		barWindow.setLayout(new FlowLayout());
		barWindow.setSize(250, 100);
		barWindow.setTitle("接收进度");
		barWindow.setLocation(200,200);
		barWindow.setVisible(true);
		barWindow.add(progressBar);
		barWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		
	}
	@SuppressWarnings("resource") // 抑制编译器警告
	@Override
	public void run() {
//		progressBar.setBar();
		DataInputStream input = null;// 这里将socket监听到的数据流作为数据的输入流
		DataOutputStream fileOut = null;// 文件输出流
		DataOutputStream ack = null;// 作为对client数据传输成功的响应
		int bufferSize = 8192;// 缓冲区
		byte[] buf = new byte[bufferSize];// 数据存储
		long donelen = 0;// 传输完成的数据长度
		long filelen = 0;// 文件长度
		try {
			ServerSocket listen = new ServerSocket(port);// 设置端口监听器
			Socket socket = listen.accept();// 开始监听，在监听到客户端之前，一直堵塞
			// 将socket数据作为数据输入流
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// 以客户端的IP地址作为存储路径
			String fileDir = dir + "\\"
					+ socket.getInetAddress().toString().substring(1, socket.getInetAddress().toString().length());
			;
			File file = new File(fileDir);
			if (!file.exists())// 判断文件夹是否存在，不存在则创建
			{
				file.mkdir();
			}
			String fileName = input.readUTF();// 读取文件名
			String filePath = fileDir + "\\" + fileName;// 设置文件路径
			file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			
			fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			filelen = input.readLong();// 读取文件长度

			System.out.println("文件的长度为:" + filelen + "\n");
			System.out.println("开始接收文件!" + "\n");
			
			ack = new DataOutputStream(socket.getOutputStream());
			
			
			while (true) {
				int read = 0;
				if (input != null) {
					read = input.read(buf);
					if(read != -1)
						ack.writeUTF("OK");// 结束到数据以后给client一个回复
				}

				if (read == -1) {
					break;
				}
				donelen += read;
				// 下面进度条本为图形界面的prograssBar做的，这里如果是大文件，可能会重复打印出一些相同的百分比
				//System.out.println("文件接收了" + (donelen * 100 / filelen) + "%\n");
				percent = (int)(donelen * 100 / filelen);
				//TODO			
				progressBar.setValue(percent);
				fileOut.write(buf, 0, read);
			}
			if (donelen == filelen)
				System.out.println("接收完成，文件存为" + file + "\n");
			else {//传输失败时删除文件
				System.out.printf("IP:%s发来的%s传输过程中失去连接\n", socket.getInetAddress(), fileName);
				file.delete();
			}
			ack.close();
			input.close();
			fileOut.close();
			System.out.println("1111");

		} 
		catch (Exception e) {
			System.out.println("ReceiveFile Error!");
			e.printStackTrace();
			return;
		}
	}
}