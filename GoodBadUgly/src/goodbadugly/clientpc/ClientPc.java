package goodbadugly.clientpc;

import javax.swing.SwingUtilities;

public class ClientPc {
	public static void main(String[] args) {
		Runnable init = new Runnable() {
			public void run() {
				new LoginGUI();
			}
		};
		SwingUtilities.invokeLater(init);
	}
}
