package fileTransportation;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import CComponents.MessageBlob;
import CComponents.MessageBlobOperator;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Client.SendMessage;
import Constants.SocketConstants;
import Property.Property;

/*�������ڱ����豸�洢�ռ䣬�������յ����ļ����浽��λ��*/
/*Ŀǰ�и����⣬���ǵ����ļ������ʱ���ֱ�ӵ�رմ��ڻᱨ����Ϊ�޷����·��*/
//12.15 @Auther RickyTong1 
//		������.
public class ChooseFolder {
	public static String savePath;// ����·��

	public ChooseFolder(String fileName) {
		
		File file = new File("data\\");

		if (file != null) {
			savePath = file.getAbsolutePath();
			System.out.println("�ļ�:" + file.getAbsolutePath());
		//	System.out.println(jfc.getSelectedFile().getName());
		}

	}

	public static void save() {
		if (savePath == null) {
			JOptionPane.showMessageDialog(null, "�����ļ�����Ϊ��");
		} else {
			ReceiveFile se = new ReceiveFile(savePath, SocketConstants.FILE_GRN_PORT);
			se.start();// �����߳�
		}
	}
}
