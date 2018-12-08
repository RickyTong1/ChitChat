package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import Exceptions.ButtonTypeSwitchException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatWindowCtrl{

	private enum sendField_quickKey{//发送快捷键模式,在快速设置中可以更改,默认为ENTER+ALT.
		SEND_TYPE_ON_ENTER,SEND_TYPE_ON_ENTER_ALT,SEND_TYPE_ON_ENTER_CTRL
	}
	
	 
	private enum button_type{
		SEND,QUICK_SET
	}
	private enum message_type{
		SERVER,OTHERS,MYSELF
	}
	
	@FXML
	ScrollPane sp;//scrollpane装载chatcontent,实现聊天面板滚动
	@FXML
	Pane chatContent;//聊天面板装载chatBox,显示聊天信息.
	@FXML
	ToolBar tools;//工具栏 //TODO 工具栏中的按钮
	@FXML
	TextArea sendField;//待发送文本区
	@FXML
	ListView<VBox> sentMessageList;
	@FXML
	Button send;//发送按钮文本显示的容器.
	
	@FXML 
	Button quickSet;
	TextArea content = new TextArea();//显示区
	VBox chatBox = new VBox();
	
	
	//Popup pop = new Popup();
	public void buttonsListener(ActionEvent e) {
			try {
				switch(switchTo(e)){
				case SEND:{
					//TODO SEND
				}break;
				case QUICK_SET:{
					
				}
				
				}
			} catch (Exception e1) {e1.printStackTrace();}
		
	}
	
	void addNewMsg(String msg,message_type type) {
		String time = getTime();
		content.setWrapText(true);//换行
		content.setEditable(false);//不可编辑
		
		Task<VBox> othersMessages = new Task<VBox>() {
            @Override
            public VBox call() throws Exception {
            	content.setBackground(new Background(new BackgroundFill(Color.CYAN,null,null)));
            	chatBox.getChildren().add(new Label("<html>" 
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
            			+ time + "<br></html>"));
            	chatBox.getChildren().add(content);
            	return new VBox();    			
            }
        };

		
	}
	public static String getTime() {

		long date = new Date().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
		return dateFormat.format(date);

	}
	private button_type switchTo(ActionEvent e) throws Exception{
		Button source = (Button) e.getSource();
		if(source == send)return button_type.SEND;
		if(source == quickSet)return button_type.QUICK_SET;
		else throw new ButtonTypeSwitchException("ChatWindowControl.turnTo()");
	}
}
	

