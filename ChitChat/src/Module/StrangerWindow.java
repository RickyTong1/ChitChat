package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*İ������Ϣ���ڣ�������ʾİ������Ϣ��*/
public class StrangerWindow {
	public static int userID,contactsID;//��������̬����������ID���ݸ�FriendWindow
	String url = "../FxmlSource/StrangerWindow.fxml";
	
	public StrangerWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		new Popup(url,"İ��������");
	}
}
