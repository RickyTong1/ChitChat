package fileTransportation;

import java.io.File;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*该类用于保存设备存储空间，并将接收到的文件保存到该位置*/
/*目前有个问题，就是弹出文件浏览器时如果直接点关闭窗口会报错，因为无法获得路径*/
public class ChooseFolder extends Application{
	public static String savePath;//保存路径 
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage){
		savePath = "C:\\Users\\Administrator\\Desktop";//默认路径 
		BorderPane root = new BorderPane();
	    savePath=new String(""+chooseFolder()+"");//文件保存路径
	    ReceiveFile re = new ReceiveFile(savePath,50000);//接收文件线程
	    re.start();//启动线程
	}
	/*获取文件保存路径*/
	public File chooseFolder() {
	    Stage fileStage = null;
	    DirectoryChooser folderChooser = new DirectoryChooser();//创建一个路径选择器
	    folderChooser.setTitle("Choose Folder");//设置文件路径选择器名称
	    File selectedFile = folderChooser.showDialog(fileStage);//开启浏览窗口
	    return selectedFile;//返回获得的文件路径
	}
}
