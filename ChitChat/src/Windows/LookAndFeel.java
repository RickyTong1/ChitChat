package Windows;

import javax.swing.Box;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/*javax.swing.plaf.nimbus.NimbusLookAndFeel类型外观格局类*/
public class LookAndFeel {
	/*类方法：方便其他类直接调用*/
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
