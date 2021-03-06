package fileTransportation;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;
 
public class SendFile extends Thread {
    private String dir;//上传的文件保存路径
    //private String fileName;//上传的文件名
    private String host;//socket目标地址
    private int port;//socket目标端口号
    JFrame barWindow = new JFrame();
	JProgressBar progressBar;
	private int percent = 0;
    
	public SendFile(String dir, String host/*ip*/, int port) {
		this.dir = dir;
	//	this.fileName = fileName;
		progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setBackground(Color.CYAN);
	
		barWindow.setLayout(new FlowLayout());
		barWindow.setSize(250, 100);
		barWindow.setTitle("发送进度");
		barWindow.setLocation(200,200);
		barWindow.setVisible(true);
		barWindow.add(progressBar);
		barWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		
		this.host = host;
		this.port = port;
	}
	
	public void run() {
		Socket socket = null;
		DataInputStream input = null;//文件输入流
		DataOutputStream output = null;
		DataInputStream getAck = null;//获得服务器的ACK
		int bufferSize = 8192;
        byte[] buf = new byte[bufferSize];//数据存储
		
		try{
			socket = new Socket(host, port);//设置socket，并进行连接connect
            File file = new File(dir);// 选择进行传输的文件
            
            System.out.println("文件长度:" + (int) file.length());
			
			input = new DataInputStream(new FileInputStream(dir));
			output = new DataOutputStream(socket.getOutputStream());//将socket设置为数据的传输出口
			getAck = new DataInputStream(socket.getInputStream());//设置socket数据的来源
			
			output.writeUTF(file.getName());//将文件名传输过去
			output.flush();
			output.writeLong((long) file.length());//将文件长度传输过去
			output.flush();
			
			int readSize = 0;
			int filelen = (int)file.length();
			int donelen = 0;
			
			while(true)
			{
				if(input != null)
				{
					readSize = input.read(buf);
				}
				if(readSize == -1)
					break;
				
				output.write(buf, 0, readSize);
				donelen += readSize;
				percent = (int)(donelen * 100 / filelen);
				progressBar.setValue(percent);
				if(!getAck.readUTF().equals("OK"))
				{
					System.out.println("服务器"+ host + ":" + port + "失去连接！"); 
					break;
				}
			}
			sleep(1000);//防止发送大文件结束时过早关闭socket导致收不到最后一次返回
            output.flush();// 关闭socket链接
            input.close();
            output.close();
            socket.close();
            getAck.close();
            System.out.println("文件传输完成");
            GetFilePath.filePath = null;
			
		}catch (Exception e) {
			System.out.println("SendFile Error!");
            e.printStackTrace();
        }
	}
	
}