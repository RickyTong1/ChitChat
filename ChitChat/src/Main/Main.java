package Main;

import Client.SendMessage;
import Module.ChatWindowModule;
import javafx.application.Application;


public class Main{
	
	
	public static void main(String args[]) {
		
		
		new SendMessage("172.20.10.2",55555,"NIHAO".getBytes());
	}
	

	
}
