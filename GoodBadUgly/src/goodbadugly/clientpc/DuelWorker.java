package goodbadugly.clientpc;

import goodbadugly.common.Constants;
import java.util.List;
import java.awt.*;

import javax.swing.*;

public class DuelWorker extends SwingWorker<Void, Integer> {
	private GameWorker gameWorker;
	private GameGUI gameFrame;
	private MyConnection connection;
	private DuelGUI duelFrame;
	private int inToCheck;

	public DuelWorker(GameWorker worker, GameGUI frame,	MyConnection connection, int in) {
		gameWorker = worker;
		gameFrame = frame;
		inToCheck = in;
		this.connection = connection;
	}

	@Override
	protected Void doInBackground() throws Exception {
		if (inToCheck == Constants.DUELYOU) {
			publish(Constants.DUELYOU);
			MyListener.setDuelMove(Constants.NO_ANSWER);

			// waiting the listener notify() in order to send the right
			// move(scissor, rock or paper)
			synchronized (this) {
				wait(Constants.TURN_TIMEOUT-1000);
			}

			duelFrame.buttonsPanel.removeAll();
			duelFrame.buttonsPanel.add(new JLabel(new ImageIcon(new ImageIcon("bin/images/duels/duelWaiting.gif").getImage().getScaledInstance(100, 100,java.awt.Image.SCALE_DEFAULT))));
			duelFrame.pack();

			connection.write(MyListener.getDuelMove());
			publish((int) connection.read());

			gameWorker.getYou().setAmmo((int)connection.read());
			connection.read();

			synchronized (gameWorker) {
				gameWorker.notify();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			publish(Constants.DUELOTH);
			connection.read();
			synchronized (gameWorker) {
				gameWorker.notify();
			}
			gameFrame.setEnabled(true);
		}

		return null;
	}

	@Override
	protected void process(List<Integer> input) {
		switch (input.get(0)) {
		case Constants.DUELOTH:
			gameFrame.setEnabled(false);
			gameFrame.getGamePanel().repaint();
			gameFrame.getStateLabel().setText("Waiting for the finish of the duel...");
			break;

		case Constants.DUELYOU:
			gameFrame.setEnabled(false);
			gameFrame.getGamePanel().repaint();
			gameFrame.getStateLabel().setText("Ongoing duel...");
			duelFrame = new DuelGUI(this);
			break;

		case Constants.LOSE:
			duelFrame.buttonsPanel.removeAll();
			duelFrame.state.setText("Your opponent...");
			duelFrame.setPreferredSize(new Dimension(500, 500));
			duelFrame.buttonsPanel.add(new JLabel(new ImageIcon(new ImageIcon("bin/images/duels/loser.jpg").getImage().getScaledInstance(500, 500,java.awt.Image.SCALE_SMOOTH))));
			duelFrame.pack();
			gameFrame.setEnabled(true);
			break;

		case Constants.WIN:
			duelFrame.buttonsPanel.removeAll();
			duelFrame.state.setText("YOU!!!");
			duelFrame.setPreferredSize(new Dimension(500, 500));
			duelFrame.buttonsPanel.add(new JLabel(new ImageIcon(new ImageIcon("bin/images/duels/winner.jpg").getImage().getScaledInstance(500, 500,java.awt.Image.SCALE_SMOOTH))));
			duelFrame.pack();
			gameFrame.setEnabled(true);
			break;

		case Constants.TIE:
			duelFrame.buttonsPanel.removeAll();
			duelFrame.state.setText("Nobody, you both decide to looking for the wanted girl...");
			duelFrame.setPreferredSize(new Dimension(500, 750));
			duelFrame.buttonsPanel.add(new JLabel(new ImageIcon(new ImageIcon("bin/images/duels/tie.jpg").getImage().getScaledInstance(500, 750,java.awt.Image.SCALE_SMOOTH))));
			duelFrame.pack();
			gameFrame.setEnabled(true);
			break;
		}
	}

	@Override
	protected void done() {
		if (inToCheck == Constants.DUELYOU) {
			duelFrame.setVisible(false);
			duelFrame.dispose();
		}
	}
}
