package fileTransportation;

import java.io.File;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*�������ڱ����豸�洢�ռ䣬�������յ����ļ����浽��λ��*/
/*Ŀǰ�и����⣬���ǵ����ļ������ʱ���ֱ�ӵ�رմ��ڻᱨ����Ϊ�޷����·��*/
public class ChooseFolder extends Application{
	public static String savePath;//����·�� 
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage){
		savePath = "C:\\Users\\Administrator\\Desktop";//Ĭ��·�� 
		BorderPane root = new BorderPane();
	    savePath=new String(""+chooseFolder()+"");//�ļ�����·��
	    ReceiveFile re = new ReceiveFile(savePath,50000);//�����ļ��߳�
	    re.start();//�����߳�
	}
	/*��ȡ�ļ�����·��*/
	public File chooseFolder() {
	    Stage fileStage = null;
	    DirectoryChooser folderChooser = new DirectoryChooser();//����һ��·��ѡ����
	    folderChooser.setTitle("Choose Folder");//�����ļ�·��ѡ��������
	    File selectedFile = folderChooser.showDialog(fileStage);//�����������
	    return selectedFile;//���ػ�õ��ļ�·��
	}
}
