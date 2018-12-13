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
	public static boolean hsFile = false;

	public GetFilePath(int contactID) {

		if(filePath == null)
			hsFile = true;
		else 
			return;
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();

		if (file != null) {
			filePath = file.getAbsolutePath();
			fileName = jfc.getSelectedFile().getName();
			MessageBlob message = new MessageBlob();
			message.type = MessageBlobType.RECEIVE_FILE;//让服务器接收消息
			message.senderIP = Property.NATIVE_IP;
			message.senderID = MainWindow.ID;
			message.targetID = contactID;
			//message.fileName = filePath.
			message.fileName = GetFilePath.fileName;
			new SendMessage(Property.SERVER_IP, SocketConstants.SERVER_PORT, MessageBlobOperator.pack(message));

		}
		System.out.println("文件:" + file.getAbsolutePath());
		System.out.println(jfc.getSelectedFile().getName());
	}

	public static void send() {
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, "发送文件不能为空");
		} else {
			SendFile se = new SendFile(filePath, Property.SERVER_IP, SocketConstants.FILE_GRN_PORT);
			se.start();// 启动线程
		}
	}
}
