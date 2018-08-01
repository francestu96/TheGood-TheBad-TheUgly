package goodbadugly.clientpc;

import goodbadugly.common.Constants;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class ChatWorker extends SwingWorker<Void, String> {
	private MyConnection connection;
	private boolean team;
	private String nickname;
	private ChatGUI chatFrame;

	public ChatWorker(String nickname) {
		connection = new MyConnection();
		this.nickname = nickname;
	}

	@Override
	protected Void doInBackground() throws Exception {
		connection.connect(Constants.PORT_NO - 1);
		connection.write(nickname);
		synchronized (this) {
			wait();
		}
		publish(new String[] { "", "createGUI" });

		while (true) {
			publish((String) connection.read());
		}
	}

	@Override
	protected void process(List<String> input) {
		try {
			if (input.get(1).equals("createGUI"))
				chatFrame = new ChatGUI(this);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(input.get(0));
			chatFrame.chatBox.append(input.get(0));
		}
	}

	public void send(String message) {
		try {
			connection.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void done() {
	}

	public String getStringTeam() {
		if (team)
			return "White team";
		return "Black team";
	}

	public void setTeam(boolean team) {
		this.team = team;
	}

	public String getNickname() {
		return nickname;
	}
}
