package goodbadugly.clientpc;

import java.awt.*;
import javax.swing.*;

public class LoginGUI extends JFrame {
	private static final long serialVersionUID = -6704159704095863284L;
	private MyListener listener;
	JButton login;
	JTextField input;
	JLabel state;
	JPanel middlePanel;
	JPanel gamePanel;

	public LoginGUI() {
		super("The good, the bad, the ugly!");

		LoginWorker worker = new LoginWorker(this);
		listener = MyListener.getInstance();
		listener.setWorker(worker);
		worker.execute();

		JLabel stateLabel = new JLabel("State: ");
		JPanel lowerPanel = new JPanel();
		middlePanel = new JPanel();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		input = new JTextField();

		login = new JButton("Login");
		login.setPreferredSize(new Dimension(130, 90));
		login.setActionCommand("login");
		login.addActionListener(listener);
		middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		middlePanel.add(login);

		state = new JLabel("Waiting to enter nickname...");
		lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerPanel.add(stateLabel);
		lowerPanel.add(state);

		getContentPane().add(input, BorderLayout.NORTH);
		getContentPane().add(middlePanel, BorderLayout.CENTER);
		getContentPane().add(lowerPanel, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(400, 250));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
