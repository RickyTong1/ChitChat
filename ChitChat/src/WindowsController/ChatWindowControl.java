package WindowsController;

import java.awt.Button;
import java.awt.Color;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatWindowControl implements Initializable{

	final int SEND_TYPE_ON_ENTER = 0;
	final int SEND_TYPE_ON_ENTER_ALT = 1;
	final int SEND_TYPE_ON_ENTER_CTRL = 2;//���Ϳ�ݼ�ģʽ,�ڿ��������п��Ը���,Ĭ��ΪENTER+ALT.
	
	int mode;
	
	@FXML
	ScrollPane sp;//scrollpaneװ��chatcontent,ʵ������������
	@FXML
	Pane chatContent;//�������װ��chatBox,��ʾ������Ϣ.
	@FXML
	ToolBar tools;//������ //TODO �������еİ�ť
	@FXML
	TextArea sendField;//�������ı���
	@FXML
	Button send;//���Ͱ�ť�ı���ʾ������.
	
	TextArea content = new TextArea();//��ʾ��
	VBox chatBox = new VBox();
	
	
	Popup pop = new Popup();
	public void sendListener(ActionEvent e) {
		
		System.out.println(e.getSource());
		System.out.println(send);
		System.out.println("Action Detected!");
		addNewMsg("");
	}
	public void sendFieldListener(MouseEvent e) {
		System.out.println("EventHandeled!");
		//launch("");
	}
	
	public void windowClosing(WindowEvent e) {
		System.out.println("Closing Event Detected!");
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	void addNewMsg(String msg) {
		String time = getTime();
		content.setWrapText(true);//����
		content.setEditable(false);//���ɱ༭
		chatBox.setClip(new Label("nihao!"));
		chatBox.setClip(content);
		chatContent.setClip(chatBox);
		Task<HBox> othersMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
               return new HBox();
            }
        };

		
	}
	public static String getTime() {

		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		return dateFormat.format(date);

	}
}
	

