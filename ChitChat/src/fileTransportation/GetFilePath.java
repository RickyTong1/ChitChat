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

/*浏览需要发送文件文件*/
public class GetFilePath {
	public static String filePath = null;// 文件路径
	public static String fileName = "";

	public GetFilePath() {

		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();

		if (file != null) {
			filePath = file.getAbsolutePath();
			fileName = jfc.getSelectedFile().getName();
			System.out.println("文件:" + file.getAbsolutePath());
			System.out.println(jfc.getSelectedFile().getName());
		}
	}

	public static void send() {
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, "发送文件不能为空");
		} else {
			SendFile se = new SendFile(filePath, Property.Property.SERVER_IP, 50000);
			se.start();// 启动线程
		}
	}
}
