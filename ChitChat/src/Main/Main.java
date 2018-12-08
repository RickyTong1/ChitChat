package Main;

import Module.LoginWindow;
import Module.Popup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/*登陆界面（程序最开始运行的类）*/
public class Main extends Application {
    
    public static void main(String[] args) {

    	launch();
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		LoginWindow login = new LoginWindow();
		Parent root = FXMLLoader.load(getClass().getResource(login.url));
		primaryStage.setTitle("a");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
