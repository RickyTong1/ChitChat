package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*个人信息窗口（用于弹出个人信息窗口）*/
public class PerDataWindow {
	public static int userID=0;//PerDataWindowController可以通过该变量获取用户ID
	public PerDataWindow(int userID) {
		this.userID = userID;
		//TODO
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/PerDataWindow.fxml"));//装载个人信息页面
			Stage stage = new Stage();
			stage.setTitle("个人信息");//设置窗口名称
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
