package Windows;

import javax.swing.Box;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/*javax.swing.plaf.nimbus.NimbusLookAndFeel������۸����*/
public class LookAndFeel {
	/*�෽��������������ֱ�ӵ���*/
	public static void addLookAndFeel(Box a) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(a);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
