package Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*该类用于切换界面，输入相应的Fxml文件位置以及窗口名字*/
public class Popup{
	public Popup(String url,String title) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(url));
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
