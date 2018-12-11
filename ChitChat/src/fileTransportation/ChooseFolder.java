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

/*�������ڱ����豸�洢�ռ䣬�������յ����ļ����浽��λ��*/
/*Ŀǰ�и����⣬���ǵ����ļ������ʱ���ֱ�ӵ�رմ��ڻᱨ����Ϊ�޷����·��*/
public class ChooseFolder {
	public static String savePath;// ����·��

	public ChooseFolder(String fileName) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showDialog(new JLabel(), "ѡ��");
		File file = jfc.getSelectedFile();

		if (file != null) {
			savePath = file.getAbsolutePath();
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.SEND_FILE;//�÷�����������Ϣ
			message.senderIP = Property.Property.NATIVE_IP;
			message.senderID = MainWindow.ID;
			message.fileName = fileName;
		//	message.fileName = GetFilePath.fileName;
			new SendMessage(Property.Property.SERVER_IP
					,SocketConstants.GENERAL_PORT
					,MessageBlobOperator.pack(message));
			System.out.println("�ļ�:" + file.getAbsolutePath());
			System.out.println(jfc.getSelectedFile().getName());
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
