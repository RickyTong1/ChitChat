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
	final int SEND_TYPE_ON_ENTER_CTRL = 2;//发送快捷键模式,在快速设置中可以更改,默认为ENTER+ALT.
	
	int mode;
	
	@FXML
	ScrollPane sp;//scrollpane装载chatcontent,实现聊天面板滚动
	@FXML
	Pane chatContent;//聊天面板装载chatBox,显示聊天信息.
	@FXML
	ToolBar tools;//工具栏 //TODO 工具栏中的按钮
	@FXML
	TextArea sendField;//待发送文本区
	@FXML
	Button send;//发送按钮文本显示的容器.
	
	TextArea content = new TextArea();//显示区
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
		content.setWrapText(true);//换行
		content.setEditable(false);//不可编辑
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
	

