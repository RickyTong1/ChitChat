package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*�������ϴ��ڣ����ڵ����������Ͽ���*/
public class FriendWindow {
	public static int userID;//��������̬����������ID���ݸ�FriendWindow
	public static int contactsID;
	public String url = "../View/FrdWindow.fxml";
	public FriendWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		new Popup(url,"������Ϣ");
	}
}
