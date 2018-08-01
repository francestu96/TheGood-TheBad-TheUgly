package goodbadugly.clientpc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class ChatGUI extends JFrame {
	private static final long serialVersionUID = -5219271451364137304L;
	private ChatWorker worker;
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox;

	public ChatGUI(ChatWorker w) {
		super(w.getStringTeam() + " Chat");

		worker = w;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(171, 148, 0));
		southPanel.setLayout(new GridBagLayout());

		messageBox = new JTextField(30);
		messageBox.requestFocusInWindow();

		sendMessage = new JButton("Send Message");
		sendMessage.addActionListener(new SendMessageButtonListener());

		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		chatBox.setLineWrap(true);

		mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.LINE_START;
		left.fill = GridBagConstraints.HORIZONTAL;
		left.weightx = 512.0D;
		left.weighty = 1.0D;

		GridBagConstraints right = new GridBagConstraints();
		right.insets = new Insets(0, 10, 0, 0);
		right.anchor = GridBagConstraints.LINE_END;
		right.fill = GridBagConstraints.NONE;
		right.weightx = 1.0D;
		right.weighty = 1.0D;

		southPanel.add(messageBox, left);
		southPanel.add(sendMessage, right);

		mainPanel.add(BorderLayout.SOUTH, southPanel);

		add(mainPanel);
		setSize(470, 300);
		setVisible(true);
	}

	class SendMessageButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			worker.send(worker.getNickname());
			worker.send(messageBox.getText());
			System.out.println("Ho inviato " + messageBox.getText());
			chatBox.append("<you>:  " + messageBox.getText() + "\n");
			messageBox.setText("");
			messageBox.requestFocusInWindow();
		}
	}
}
