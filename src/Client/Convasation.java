package Client;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Convasation extends JPanel implements Runnable{
	Icon icon;//ͷ��
	JLabel nicknameLabel;//�ǳ�
	List<Convasation> list;//��ϵ��/��Ϣ�б�
	int ID;//�˺�(��ΪΨһ��ʶ��)
	ActionListener actListener;
	
	public abstract void refreshMsg();
	@Override
	public void run() {
		//TODO: ��дʹ�ܹ�ʵʱˢ��״̬.
	}
	
}
