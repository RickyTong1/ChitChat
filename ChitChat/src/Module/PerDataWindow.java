package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Popup;
/*������Ϣ���ڣ����ڵ���������Ϣ���ڣ�*/
public class PerDataWindow {
	public static int userID=0;//PerDataWindowController����ͨ���ñ�����ȡ�û�ID
	String url = "../FxmlSource/PerDataWindow.fxml";
	
	public PerDataWindow(int userID) {
		this.userID = userID;
		new Popup(url,"������Ϣ");
	}
}
