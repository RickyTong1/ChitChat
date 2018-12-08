package Module;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*���������л����棬������Ӧ��Fxml�ļ�λ���Լ���������*/
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
