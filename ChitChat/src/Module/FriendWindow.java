package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*好友资料窗口（用于弹出好友资料卡）*/
public class FriendWindow {
	public static int userID,contactsID;//用两个静态变量将两个ID传递给FriendWindow
	String url = "../View/FrdWindow.fxml";
	public FriendWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		new Popup(url,"好友信息");
	}
}
