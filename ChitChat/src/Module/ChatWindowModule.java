package Module;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ChatWindowModule extends Application{
	String nick;
	int contactID;
	int userID;
	public ChatWindowModule(int userID,int contactID,String nick){
		this.nick = nick;
		this.contactID = contactID;
		this.userID = userID;
	}
	public int getuserID() {
		return userID;
	}
	public int getContactID() {	
		return contactID;
	}
	public String getNick() {
		return nick;
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/Windows/ChatWindowFX.fxml"));
		arg0.setTitle("ChitChatWindow");
		
		arg0.setScene(new Scene(root));
		arg0.show();
		arg0.setOnCloseRequest(e ->{
			System.out.println("Window Closing!   Windows.ChatWindowModule");
			//TODO Window Closing
		});
	}
}
