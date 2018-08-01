package goodbadugly.clientpc;

import goodbadugly.clientpc.maps.*;
import goodbadugly.common.Constants;
import goodbadugly.common.Packet;

import java.util.ArrayList;

import java.io.IOException;
import java.util.List;
import java.awt.*;

import javax.swing.*;

public class GameWorker extends SwingWorker<Void, Integer> {
	private MyConnection connection;
	private ArrayList<Player> players;
	private ClientMap map;
	private JLabel[] citiesLabel;
	private GameGUI gameFrame;

	private Player you;

	private boolean myTurn;

	public GameWorker(MyConnection connection, ArrayList<Player> players,
			ClientMap map, Player you) {
		this.connection = connection;
		this.players = players;
		this.map = map;
		this.you = you;
		myTurn = false;

		citiesLabel = new JLabel[map.getNodes().length];
	}

	@Override
	protected Void doInBackground() throws Exception {
		// computes the reachability of the cities based on your position and
		// set the cities color
		calcReachability();

		// create the game frame and the drawing game panel
		publish(Constants.CHANGE_TO_GAME_FRAME);

		// waiting that EDT finisches to create game GUI
		synchronized (this) {
			wait();
		}

		Object inToCheck;
		ArrayList<Player> plcopy = new ArrayList<Player>();
		long startGameTime = System.currentTimeMillis();
		long startTime;
		Packet in = null;
		int progress;

		while ((progress = (int) (System.currentTimeMillis() - startGameTime)) < Constants.GAME_TIMEOUT) {
			while (!myTurn) {
				inToCheck = connection.read();
				progress = 1000 - ((int) ((System.currentTimeMillis() - startGameTime) * 1000) / Constants.GAME_TIMEOUT);

				if (inToCheck instanceof Integer) {
					if ((Integer) inToCheck == Constants.YOURTURN) {
						myTurn = true;
						in = connection.readPacket();
					}
				} else
					in = (Packet) inToCheck;

				plcopy.clear(); 
				plcopy.addAll(players);

				do {
					for (int j = 0; j < players.size(); j++)
						if (players.get(j).getName().equals(in.getPlayer())) {
							players.get(j).setCurrent(
									map.getNodes()[in.getNode()]);
							players.get(j).setAmmo(in.getAmmo());
							if (in.getPlayer().equals("You"))
								you = players.get(j);
							plcopy.remove(players.get(j));
						}
				} while ((in = connection.readPacket()).getNode() != -1);

				for (int i = 0; i < plcopy.size(); i++)
					players.remove(plcopy.get(i));

				// reading ammos
				for (int i = 0; i < map.getNodes().length; i++)
					map.getNodes()[i].setAmmo(connection.readInt());

				if (myTurn)
					publish(new Integer[] { Constants.PAINT,
							Constants.YOURTURN, progress });
				else
					publish(new Integer[] { Constants.PAINT,
							Constants.NOTYOURTURN, progress });

				duel(inToCheck);
			}
			startTime = System.currentTimeMillis();
			synchronized (this) {
				wait(Constants.TURN_TIMEOUT);
			}
			if ((System.currentTimeMillis() - startTime) < Constants.TURN_TIMEOUT) {
				connection.write(MyListener.getPosToSend());
				calcReachability();
			} else
				publish(Constants.LOSTTURN);

			myTurn = false;
		}
		int white = 0;
		int black = 0;

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getTeam())
				white += players.get(i).getAmmo();
			else
				black += players.get(i).getAmmo();
		}

		if (white == black)
			publish(Constants.PAIR, 0);
		else if (white > black)
			publish(Constants.GAMEOVER, 1);
		else if (white < black)
			publish(Constants.GAMEOVER, 2);
		return null;
	}

	@Override
	protected void process(List<Integer> input) {
		switch (input.get(0)) {
		case Constants.CHANGE_TO_GAME_FRAME:
			gameFrame = new GameGUI(this, map, players);
			synchronized (this) {
				notify();
			}
			break;

		case Constants.LOSTTURN:
			gameFrame.getStateLabel().setText(
					"You lost your turn! Waiting for the next one...");
			break;

		case Constants.PAINT:
			if (input.get(1) == Constants.YOURTURN)
				gameFrame.getStateLabel().setText("It's your turn");
			else
				gameFrame.getStateLabel().setText("Waiting for your turn...");

			gameFrame.getAmmoLabel().setText(String.valueOf(you.getAmmo()));
			gameFrame.getBar().setValue(input.get(2));
			gameFrame.getGamePanel().repaint();
			break;

		case Constants.PAIR:
			gameFrame.setEnabled(false);
			gameFrame.getStateLabel().setText("The two teams drew!");
			break;

		case Constants.GAMEOVER:
			gameFrame.setEnabled(false);
			if (input.get(1) == 1)
				gameFrame.getStateLabel().setText("The white team wins!");
			else
				gameFrame.getStateLabel().setText("The black team wins!");
			break;
		}
	}

	@Override
	protected void done() {
	}

	private void duel(Object in) throws IOException, InterruptedException,
			ClassNotFoundException {
		if (in instanceof Integer) {
			if ((Integer) in != Constants.YOURTURN) {
				DuelWorker duelWorker = new DuelWorker(this, gameFrame,
						connection, (int) in);
				duelWorker.execute();
				synchronized (this) {
					wait();
				}
			}
		}
	}

	private void calcReachability() {
		for (int i = 0; i < map.getNodes().length; i++) {
			if (map.getNodes()[i].isAdiacent(you.getCurrent()))
				map.getNodes()[i].setReachable(true);
			else
				map.getNodes()[i].setReachable(false);
		}
		you.getCurrent().setReachable(true);
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public Rectangle[] getCooImgs() {
		return map.getCooImgs();
	}

	public NodeClient[] getNodes() {
		return map.getNodes();
	}

	public JLabel[] getCitiesLabel() {
		return citiesLabel;
	}

	public Player getYou() {
		return you;
	}
}
