package Main;

import CComponents.MessageReceive;
import Windows.ChatWindow;
import Windows.LoginWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/*登陆界面（程序最开始运行的类）*/
public class Main  {
	public static LoginWindow loginwindow= null;
    
    public static void main(String[] args) {

    	MessageReceive.getInstance();//启动后台接收信息线程.
    	loginwindow = new LoginWindow();
    	
    	//launch();
    	//new ChatWindow(1,1);
    }

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		// TODO Auto-generated method stub
//		Parent root = FXMLLoader.load(getClass().getResource("../View/LoginWindow.fxml"));
//		primaryStage.setTitle("a");
//		primaryStage.setScene(new Scene(root));
//		primaryStage.show();
//	}
}
