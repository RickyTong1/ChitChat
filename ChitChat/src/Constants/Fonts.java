package Constants;

import java.awt.Font;

public interface Fonts {//各模块的字体
	public static final Font MESSAGE_HEADER = new Font("黑体",Font.BOLD, 20);
	public static final Font MESSAGE_TIMESTICK = new Font("宋体",Font.ITALIC, 10/*10 pt*/);
	public static final Font MESSAGE_NICKNAME = new Font("黑体",Font.BOLD, 15);
	public static final Font MESSAGE_LASTSPEAK = new Font("宋体",Font.PLAIN,12);
	
	
	public static final Font CONTACTS_STYLEWORD = new Font("宋体",Font.PLAIN, 12 );
	public static final Font CONTACTS_NICKNAME = new Font("黑体",Font.BOLD, 15);
	
	public static final Font USER_STYLEWORD = new Font("宋体",Font.PLAIN,12);
	public static final Font USER_NICKNAME = new Font("黑体",Font.BOLD, 20);
	
}