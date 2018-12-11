package fileTransportation;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*�����Ҫ�����ļ��ļ�*/
public class GetFilePath {
	public static String filePath = null;// �ļ�·��
	public static String fileName = "";

	public GetFilePath() {

		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "ѡ��");
		File file = jfc.getSelectedFile();

		if (file != null) {
			filePath = file.getAbsolutePath();
			fileName = jfc.getSelectedFile().getName();
			System.out.println("�ļ�:" + file.getAbsolutePath());
			System.out.println(jfc.getSelectedFile().getName());
		}
	}

	public static void send() {
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, "�����ļ�����Ϊ��");
		} else {
			SendFile se = new SendFile(filePath, Property.Property.SERVER_IP, 50000);
			se.start();// �����߳�
		}
	}
}
