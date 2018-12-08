package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class ChatWindow{
	String nick;
	int contactID;
	int userID;
	public ChatWindow(int userID,int contactID,String nick){
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
	
}
