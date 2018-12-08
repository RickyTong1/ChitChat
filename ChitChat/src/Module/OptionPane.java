package Module;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Popup;

public class OptionPane implements Initializable{
	@FXML
	Label head;
	@FXML
	Label message;
	@FXML
	Button confirm;
	
	String url = "../View/OptionPane.fxml";
	public void onAction() {
		Stage stage = (Stage)confirm.getScene().getWindow();
		stage.close();
	}
	
	public OptionPane(String head,String message) {
		this.head.setText(head);
		this.message.setText(message);
		new Popup(url,"");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
