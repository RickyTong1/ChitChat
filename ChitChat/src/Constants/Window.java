package Constants;

import java.awt.Toolkit;

public class Window {

	static final int THIS_DEVISE_HEIGHT
		= Toolkit.getDefaultToolkit().getScreenSize().height;//��ʾ����
	
	static final int THIS_DEVISE_WIDTH
		= Toolkit.getDefaultToolkit().getScreenSize().width;//��
	
	static final int DEVISE_MIDDLE_WIDTH
		= THIS_DEVISE_WIDTH / 2;
	
	static final int DEVISE_MIDDLE_HEIGHT
		= THIS_DEVISE_HEIGHT / 2;
	
	
	/*���Ͻ�����,��ȥ����ĳ����һ���,ʹ�������м�λ����ʾ���м�.*/
	public static int getMiddleWidth(int FrameWidth)
	{
		return DEVISE_MIDDLE_WIDTH - FrameWidth / 2;
	}
	public static int getMiddleHeight(int FrameHeight) {
		return DEVISE_MIDDLE_HEIGHT - FrameHeight / 2;
	}
}
