package fileTransportation;
import java.net.*;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
 
public class SendFile extends Thread {
    private String dir;//�ϴ����ļ�����·��
    //private String fileName;//�ϴ����ļ���
    private String host;//socketĿ���ַ
    private int port;//socketĿ��˿ں�
    
	public SendFile(String dir, String host/*ip*/, int port) {
		this.dir = dir;
	//	this.fileName = fileName;
		this.host = host;
		this.port = port;
	}
	
	public void run() {
		Socket socket = null;
		DataInputStream input = null;//�ļ�������
		DataOutputStream output = null;
		DataInputStream getAck = null;//��÷�������ACK
		int bufferSize = 8192;
        byte[] buf = new byte[bufferSize];//���ݴ洢
		
		try{
			socket = new Socket(host, port);//����socket������������connect
            File file = new File(dir);// ѡ����д�����ļ�
            
            System.out.println("�ļ�����:" + (int) file.length());
			
			input = new DataInputStream(new FileInputStream(dir));
			output = new DataOutputStream(socket.getOutputStream());//��socket����Ϊ���ݵĴ������
			getAck = new DataInputStream(socket.getInputStream());//����socket���ݵ���Դ
			
			output.writeUTF(file.getName());//���ļ��������ȥ
			output.flush();
			output.writeLong((long) file.length());//���ļ����ȴ����ȥ
			output.flush();
			
			int readSize = 0;
			
			while(true)
			{
				if(input != null)
				{
					readSize = input.read(buf);
				}
				if(readSize == -1)
					break;
				
				output.write(buf, 0, readSize);
				
				if(!getAck.readUTF().equals("OK"))
				{
					System.out.println("������"+ host + ":" + port + "ʧȥ���ӣ�"); 
					break;
				}
			}
			sleep(1000);//��ֹ���ʹ��ļ�����ʱ����ر�socket�����ղ������һ�η���
            output.flush();// �ر�socket����
            input.close();
            output.close();
            socket.close();
            getAck.close();
            System.out.println("�ļ��������");
			
		}catch (Exception e) {
			System.out.println("SendFile Error!");
            e.printStackTrace();
        }
	}
	
}