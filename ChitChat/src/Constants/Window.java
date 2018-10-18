package Constants;

import java.awt.Toolkit;

public class Window {

	static final int THIS_DEVISE_HEIGHT
		= Toolkit.getDefaultToolkit().getScreenSize().height;//显示器高
	
	static final int THIS_DEVISE_WIDTH
		= Toolkit.getDefaultToolkit().getScreenSize().width;//宽
	
	static final int DEVISE_MIDDLE_WIDTH
		= THIS_DEVISE_WIDTH / 2;
	
	static final int DEVISE_MIDDLE_HEIGHT
		= THIS_DEVISE_HEIGHT / 2;
	
	
	/*左上角坐标,减去窗体的长宽的一半后,使窗体正中间位于显示器中间.*/
	public static int getMiddleWidth(int FrameWidth)
	{
		return DEVISE_MIDDLE_WIDTH - FrameWidth / 2;
	}
	public static int getMiddleHeight(int FrameHeight) {
		return DEVISE_MIDDLE_HEIGHT - FrameHeight / 2;
	}
}
