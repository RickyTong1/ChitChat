package fileTransportation;

import java.io.File;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*�����Ҫ�����ļ��ļ�*/
public class GetFilePath extends Application{
	public static String filePath=null;//�ļ�·��
	@Override
	public void start(Stage primaryStage){
		BorderPane root = new BorderPane();
		FileChooser fileChooser = new FileChooser();//����һ���ļ�ѡ����ʵ����
	    fileChooser.setTitle("Open Resource File");//�����ļ�ѡ��������
	    filePath=new String(""+fileChooser.showOpenDialog(primaryStage)+"");//��÷����ļ���·��
	    send();//���÷��ͷ��������߳�
	}
	public static void main(String[] args) {
        launch(args);
    }
	public void send() {
		if(filePath == null) {
			JOptionPane.showMessageDialog(null,"�����ļ�����Ϊ��");
		}
		else {
			SendFile se = new SendFile(filePath,"127.0.0.1",50000);
			se.start();//�����߳�
		}
	}
}
