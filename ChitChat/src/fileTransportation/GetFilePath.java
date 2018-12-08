package fileTransportation;

import java.io.File;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*浏览需要发送文件文件*/
public class GetFilePath extends Application{
	public static String filePath=null;//文件路径
	@Override
	public void start(Stage primaryStage){
		BorderPane root = new BorderPane();
		FileChooser fileChooser = new FileChooser();//创建一个文件选择器实例化
	    fileChooser.setTitle("Open Resource File");//设置文件选择器名称
	    filePath=new String(""+fileChooser.showOpenDialog(primaryStage)+"");//获得发送文件的路径
	    send();//调用发送方法启动线程
	}
	public static void main(String[] args) {
        launch(args);
    }
	public void send() {
		if(filePath == null) {
			JOptionPane.showMessageDialog(null,"发送文件不能为空");
		}
		else {
			SendFile se = new SendFile(filePath,"127.0.0.1",50000);
			se.start();//启动线程
		}
	}
}
