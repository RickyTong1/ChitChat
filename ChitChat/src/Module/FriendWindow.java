package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*�������ϴ��ڣ����ڵ����������Ͽ���*/
public class FriendWindow {
	public static int userID,contactsID;//��������̬����������ID���ݸ�FriendWindow
	public FriendWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/FrdWindow.fxml"));//װ��FrdWindow.fxml
			Stage stage = new Stage();
			stage.setTitle("������Ϣ");//���ñ���
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
