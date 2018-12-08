package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*好友资料窗口（用于弹出好友资料卡）*/
public class FriendWindow {
	public static int userID,contactsID;//用两个静态变量将两个ID传递给FriendWindow
	public FriendWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/FrdWindow.fxml"));//装载FrdWindow.fxml
			Stage stage = new Stage();
			stage.setTitle("好友信息");//设置标题
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
