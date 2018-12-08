package fileTransportation;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;

public class ReceiveFile extends Thread {
	private String dir;// �ļ�����·������main����������
	private int port;// Socket�������Ķ˿�
	public static int percent=0;//�ļ��������
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
		barWindow.setTitle("���ս���");
		barWindow.setLocation(200,200);
		barWindow.setVisible(true);
		barWindow.add(progressBar);
		barWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		
	}
	@SuppressWarnings("resource") // ���Ʊ���������
	@Override
	public void run() {
//		progressBar.setBar();
		DataInputStream input = null;// ���ｫsocket����������������Ϊ���ݵ�������
		DataOutputStream fileOut = null;// �ļ������
		DataOutputStream ack = null;// ��Ϊ��client���ݴ���ɹ�����Ӧ
		int bufferSize = 8192;// ������
		byte[] buf = new byte[bufferSize];// ���ݴ洢
		long donelen = 0;// ������ɵ����ݳ���
		long filelen = 0;// �ļ�����
		try {
			ServerSocket listen = new ServerSocket(port);// ���ö˿ڼ�����
			Socket socket = listen.accept();// ��ʼ�������ڼ������ͻ���֮ǰ��һֱ����
			// ��socket������Ϊ����������
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// �Կͻ��˵�IP��ַ��Ϊ�洢·��
			String fileDir = dir + "\\"
					+ socket.getInetAddress().toString().substring(1, socket.getInetAddress().toString().length());
			;
			File file = new File(fileDir);
			if (!file.exists())// �ж��ļ����Ƿ���ڣ��������򴴽�
			{
				file.mkdir();
			}
			String fileName = input.readUTF();// ��ȡ�ļ���
			String filePath = fileDir + "\\" + fileName;// �����ļ�·��
			file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			
			fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			filelen = input.readLong();// ��ȡ�ļ�����

			System.out.println("�ļ��ĳ���Ϊ:" + filelen + "\n");
			System.out.println("��ʼ�����ļ�!" + "\n");
			
			ack = new DataOutputStream(socket.getOutputStream());
			
			
			while (true) {
				int read = 0;
				if (input != null) {
					read = input.read(buf);
					if(read != -1)
						ack.writeUTF("OK");// �����������Ժ��clientһ���ظ�
				}

				if (read == -1) {
					break;
				}
				donelen += read;
				// �����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
				//System.out.println("�ļ�������" + (donelen * 100 / filelen) + "%\n");
				percent = (int)(donelen * 100 / filelen);
				//TODO			
				progressBar.setValue(percent);
				fileOut.write(buf, 0, read);
			}
			if (donelen == filelen)
				System.out.println("������ɣ��ļ���Ϊ" + file + "\n");
			else {//����ʧ��ʱɾ���ļ�
				System.out.printf("IP:%s������%s���������ʧȥ����\n", socket.getInetAddress(), fileName);
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