package goodbadugly.clientpc;

import goodbadugly.clientpc.maps.*;
import goodbadugly.common.Constants;
import goodbadugly.common.Packet;

import java.util.ArrayList;

import java.util.List;
import java.awt.*;

import javax.swing.*;

public class LoginWorker extends SwingWorker<Void, Integer> {
	private MyConnection connection;
	private LoginGUI loginFrame;
	private ArrayList<Player> players;
	private Player you;
	private ClientMap map;

	public LoginWorker(LoginGUI frame) {
		connection = new MyConnection();
		loginFrame = frame;
		players = new ArrayList<Player>();
	}

	@Override
	protected Void doInBackground() throws Exception {
		do {
			synchronized (this) {
				wait();
			}
			if (loginFrame.input.getText().equals("")) {
				publish(0);
				// return;
			} else {
				if (connection.connect(Constants.PORT_NO) == Constants.SERVER_UNKNOWN) {
					publish(Constants.SERVER_UNKNOWN);// changes the status
					// label in
					// "Unreachable server"
				} else {
					connection.write(loginFrame.input.getText());
					if (connection.readInt() == Constants.ACCEPTED) {
						publish(Constants.ACCEPTED);// changes the status label
						// in
						// "Waiting for the start of the match"
						break;
					} else
						publish(Constants.NICK_ALREADY_USED);// changes the
					// status label
					// in
					// "Nickname already uesd"
				}
			}
		} while (true);

		Packet in = null;
		int i;

		ChatWorker chatWorker = new ChatWorker(loginFrame.input.getText());
		chatWorker.execute();

		// IN CASE OF MORE MAPS, WE HAVE TO CHOSE IT HERE. WE ALSO NEED TO
		// MODIFY THE SERVER IN ORDER TO RECIVE THE RANDOM MAP CHOSEN
		map = players.size() < 6 ? new ClientMap1() : new ClientMap2();

		// READ POSITIONS OF ALL PLAYERS AND AMMOS IN THE
		// MAP///////////////////////////////////////////////////////////////////
		try {
			for (i = 0; (in = connection.readPacket()).getNode() != -1; i++) {
				players.add(new Player(in.getPlayer(), in.getTeam(), in
						.getAmmo(), map.getNodes()[in.getNode()]));
				if (players.get(i).getName().equals("You"))
					you = players.get(i);
			}

			// reading ammos
			for (i = 0; i < map.getNodes().length; i++)
				map.getNodes()[i].setAmmo(connection.readInt());

		} catch (Exception e) {
			e.printStackTrace();
		}

		synchronized (chatWorker) {
			// waiting to retrive the right team to be written as frame title
			chatWorker.setTeam(you.getTeam());
			chatWorker.notify();
		}
		GameWorker worker = new GameWorker(connection, players, map, you);
		worker.execute();

		return null;
	}

	@Override
	protected void process(List<Integer> input) {
		switch (input.get(0)) {
		case 0:
			loginFrame.state.setText("Nickname cannot be empty!");
			break;

		case Constants.SERVER_UNKNOWN:
			loginFrame.state.setText("Unreachable server! Try after 10 min...");
			break;

		case Constants.NICK_ALREADY_USED:
			loginFrame.state.setText("Nickname already used, try another one!");
			break;

		case Constants.ACCEPTED:
			loginFrame.state.setText("Waiting for the start of the game...");
			loginFrame.input.setVisible(false);
			loginFrame.login.setVisible(false);
			loginFrame.middlePanel.removeAll();
			loginFrame.middlePanel.add(new JLabel(new ImageIcon(
					"bin/images/connecting.gif")));
			loginFrame.getContentPane().add(loginFrame.middlePanel,
					BorderLayout.CENTER);
			break;
		}
	}

	@Override
	protected void done() {
		loginFrame.setVisible(false);
		loginFrame.dispose();
	}
}
