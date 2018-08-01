package goodbadugly.clientpc;

import goodbadugly.common.Constants;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingWorker;

public class MyListener implements ActionListener, MouseListener {

	private static GameWorker gameWorker;
	private static SwingWorker worker;
	private static MyListener instance;
	private static int posToSend;
	private static int duelMove;

	static {
		instance = new MyListener();
	}

	public static MyListener getInstance() {
		return instance;
	}

	public static void setGameWorker(GameWorker w) {
		gameWorker = w;
	}

	public static void setWorker(SwingWorker w) {
		worker = w;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		Point click;
		int i;
		System.out.println(gameWorker.isMyTurn());
		if (gameWorker.isMyTurn()) {
			click = new Point(me.getX(), me.getY());
			for (i = 0; i < gameWorker.getCooImgs().length; i++)
				if (gameWorker.getCooImgs()[i].contains(click.x, click.y))
					if (gameWorker.getNodes()[i] != gameWorker.getYou()
							.getCurrent()
							&& gameWorker.getNodes()[i].isReachable()) {
						posToSend = i;
						gameWorker.getYou()
								.setCurrent(gameWorker.getNodes()[i]);
						synchronized (gameWorker) {
							gameWorker.notify();
						}
						break;
					}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("rock"))
			duelMove = Constants.ROCK;
		else if (command.equals("scissor"))
			duelMove = Constants.SCISSOR;
		else if (command.equals("paper"))
			duelMove = Constants.PAPER;

		synchronized (worker) {
			worker.notify();
		}
	}

	public static int getPosToSend() {
		return posToSend;
	}

	public static int getDuelMove() {
		return duelMove;
	}

	public static void setDuelMove(int move) {
		duelMove=move;
	}
}
