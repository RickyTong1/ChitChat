package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StrangerWindowCtrl implements Initializable{
	@FXML
	private TextField nickText;
	@FXML
	private TextField sexText;
	@FXML
	private TextField birthdayText;
	@FXML
	private TextField ageText;
	@FXML
	private TextArea signatureText;
	@FXML
	private Button cancleButton;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nickText.setText("�ǳ�");
		sexText.setText("��");
		birthdayText.setText("1999-05-22");
		ageText.setText("20");
		signatureText.setText("��ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð���ð�");
	}//TODO ����
	public void cancleLis() {
		Stage stage = (Stage)cancleButton.getScene().getWindow();
		stage.close();
	}
}
