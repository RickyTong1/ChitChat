package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*İ������Ϣ���ڣ�������ʾİ������Ϣ��*/
public class StrangerWindow {
	public static int userID,contactsID;//��������̬����������ID���ݸ�FriendWindow
	public StrangerWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/StrangerWindow.fxml"));
			Stage stage = new Stage();
			stage.setTitle("İ������Ϣ");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
