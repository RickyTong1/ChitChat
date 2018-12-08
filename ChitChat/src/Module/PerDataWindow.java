package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*个人信息窗口（用于弹出个人信息窗口）*/
public class PerDataWindow {
	public static int userID=0;//PerDataWindowController可以通过该变量获取用户ID
	String url = "../FxmlSource/PerDataWindow.fxml";
	
	public PerDataWindow(int userID) {
		this.userID = userID;
		new Popup(url,"个人信息");
	}
}
