package Module;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*������Ϣ���ڣ����ڵ���������Ϣ���ڣ�*/
public class PerDataWindow {
	public static int userID=0;//PerDataWindowController����ͨ���ñ�����ȡ�û�ID
	public PerDataWindow(int userID) {
		this.userID = userID;
		//TODO
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../FxmlSource/PerDataWindow.fxml"));//װ�ظ�����Ϣҳ��
			Stage stage = new Stage();
			stage.setTitle("������Ϣ");//���ô�������
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
