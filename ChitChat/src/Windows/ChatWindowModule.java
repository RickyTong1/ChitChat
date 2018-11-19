package Windows;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ChatWindowModule extends Application{
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/Windows/ChatWindowFX.fxml"));
		arg0.setTitle("ChitChatWindow");
		
		arg0.setScene(new Scene(root));
		arg0.show();
		arg0.setOnCloseRequest(e ->{
			System.out.println("Window Closing!");
		});
	}
}
