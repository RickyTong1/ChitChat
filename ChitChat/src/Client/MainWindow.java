package Client;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import CComponents.*;

public class MainWindow extends JFrame
{
	Icon icon;//用户头像
	JLabel nicknameLabel;//用户昵称
	JLabel styleWord;//用户的个性签名
	JSplitPane spilitBar;//切分窗格,左边为联系人,右边是消息
	JMenuBar operats;//左上角菜单栏
	JMenu menu;//菜单
	JMenuItem searchContacts;//搜索好友
	JMenuItem createContactsGroup;//创建分组
	
	//TODO:msgList,ctsList,contactsList变量类型设定
	ACanvas myOnlineState;//用户在线状态
	Box userInfo;
	
	MainWindow(){
		setLayout(new FlowLayout());
		init();
		setBounds(400, 400, 800, 600);
		setVisible(true);
	}
	public void init() {
		
		persInfoInit();
		messageInit();
		ContactsInit();
		
		
	}
	public void persInfoInit(){//TODO:用户信息初始化
	
		icon = new ImageIcon("URL");//TODO:url:头像集目录
		
	}
	public void messageInit() {//TODO: 消息初始化
		
	}
	public void ContactsInit() {//TODO:联系人列表初始化
		
	}
	public int deleteContacts() {//TODO:删除联系人
		return 0;
	}
	public int addContacts() {//TODO: 添加联系人
		return 0;
	}
	public int changeContactsRemark() {//TODO:更改联系人备注
		return 0;
	}
	public int preferenceSet(int statement) {//TODO:个性设置,参数:在线状态
		return 0;
	}
}
