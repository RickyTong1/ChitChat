package Windows;

import java.awt.*;

public class ChitChat {
	/*����*/
	public static void main(String[] args) {
		LoginWindow loginWindow = new LoginWindow();			//��½����
		/*�����������Ͻ�ͼ��*/
		Toolkit tool = loginWindow.getToolkit();
		Image image = tool.getImage("C:\\Users\\Administrator\\eclipse-workspace\\ChitChat\\src\\Windows\\01.jpg");				//����ͼƬ·��
		loginWindow.setIconImage(image);
		Test test = new Test();			  				//�����Ҽ��˵����
	}
}