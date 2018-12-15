package z_Tests;

import CComponents.MessageBlob;
import CComponents.MessageBlobType;
import Client.MainWindow;
import Constants.Internet;
import utils.ClientTranslation;
public class Forced_Logout_Test {
	public static void main(String...s) {
		ClientTranslation.mainWindow = new MainWindow(89);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageBlob message = new MessageBlob();
		message.type = MessageBlobType.LOGIN;
		message.onlineState = Internet.FORCED_LOGGED_OUT;
		ClientTranslation.typeTrans(message);
	}
 
}
