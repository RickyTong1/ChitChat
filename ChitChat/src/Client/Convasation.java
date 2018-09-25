package Client;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Convasation extends JPanel implements Runnable{
	Icon icon;//头像
	JLabel nicknameLabel;//昵称
	List<Convasation> list;//联系人/消息列表
	int ID;//账号(作为唯一标识符)
	ActionListener actListener;
	
	public abstract void refreshMsg();
	@Override
	public void run() {
		//TODO: 改写使能够实时刷新状态.
	}
	
}
