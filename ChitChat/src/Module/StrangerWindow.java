package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*陌生人信息窗口（用于显示陌生人信息）*/
public class StrangerWindow {
	public static int userID,contactsID;//用两个静态变量将两个ID传递给FriendWindow
	public StrangerWindow(int userID,int contactsID) {
		this.userID = userID;
		this.contactsID = contactsID;
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/StrangerWindow.fxml"));
			Stage stage = new Stage();
			stage.setTitle("陌生人信息");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
