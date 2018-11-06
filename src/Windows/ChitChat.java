package Windows;

import java.awt.*;

public class ChitChat {
	/*主类*/
	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();			//登陆窗口
		/*更换窗口左上角图标*/
		Toolkit tool = loginWindow.getToolkit();
		Image image = tool.getImage("C:\\Users\\Administrator\\eclipse-workspace\\ChitChat\\src\\Windows\\01.jpg");				//设置图片路径
		loginWindow.setIconImage(image);
		Test test = new Test();			  				//测试右键菜单语句
	}
}