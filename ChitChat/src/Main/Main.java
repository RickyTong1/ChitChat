package Main;

import Windows.ChatWindow;
import Windows.LoginWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/*��½���棨�����ʼ���е��ࣩ*/
public class Main  {
    
    public static void main(String[] args) {

    	new LoginWindow();
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
