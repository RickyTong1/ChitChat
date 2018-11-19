package Main;

import Client.MainWindow;
import WindowsController.ChatWindowControl;
import Windows.ChatWindow;
import Windows.ChatWindowModule;
import Windows.FriendWindow;
import Windows.LoginWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main{
	
	
	public static void main(String args[]) {
		
		Application.launch(ChatWindowModule.class);
		//System.out.println("");
	}
	

	
}
