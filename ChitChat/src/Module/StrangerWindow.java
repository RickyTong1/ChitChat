package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*陌生人信息窗口（用于显示陌生人信息）*/
public class StrangerWindow {
	public static int userID,contactsID;//用两个静态变量将两个ID传递给FriendWindow
	String url = "../FxmlSource/StrangerWindow.fxml";
	
	public StrangerWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		new Popup(url,"陌生人资料");
	}
}
