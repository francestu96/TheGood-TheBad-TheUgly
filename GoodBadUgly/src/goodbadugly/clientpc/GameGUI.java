package goodbadugly.clientpc;

import goodbadugly.clientpc.maps.*;
import goodbadugly.common.Constants;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class GameGUI extends JFrame {
	private static final long serialVersionUID = 4113892456337368247L;
	private GamePanel gamePanel;
	private JPanel infoPanel;
	private JPanel statePanel;
	private JPanel ammoPanel;
	private JLabel state;
	private JProgressBar bar;
	private JLabel ammo;

	public GameGUI(GameWorker worker, ClientMap map, ArrayList<Player> players) {
		super("The good, the bad,the ugly!");

		setLocation(0, 0);
		setSize(new Dimension(Constants.WIDTH, Constants.HEIGHT
				+ Constants.STATE_PANEL_HEIGHT + Constants.BAR_HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		UIManager.put("ProgressBar.foreground", new Color(171, 148, 0));
		UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
		UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

		bar = new JProgressBar(0, 1000);
		bar.setValue(0);
		bar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bar.setStringPainted(true);
		bar.setString("Remaining time");
		bar.setPreferredSize(new Dimension(Constants.WIDTH,
				Constants.BAR_HEIGHT));
		add(bar, BorderLayout.NORTH);

		try {
			gamePanel = new GamePanel(worker, map, players);
		} catch (IOException e) {
			e.printStackTrace();
		}
		add(gamePanel, BorderLayout.CENTER);

		state = new JLabel("Waiting for my turn...");
		statePanel = new JPanel();
		statePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
				Color.BLACK));
		statePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		statePanel.add(new JLabel("State: "));
		statePanel.add(state);

		ammo = new JLabel();
		ammoPanel = new JPanel();
		ammoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
				Color.BLACK));
		ammoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ammoPanel.add(new JLabel("Ammunitions: "));
		ammoPanel.add(ammo);

		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout());
		infoPanel.setPreferredSize(new Dimension(Constants.WIDTH,
				Constants.STATE_PANEL_HEIGHT));
		infoPanel.add(statePanel);
		infoPanel.add(ammoPanel);

		add(infoPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public JLabel getStateLabel() {
		return state;
	}

	public JLabel getAmmoLabel() {
		return ammo;
	}

	public JProgressBar getBar() {
		return bar;
	}

	public class GamePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1773943708940920639L;
		private ClientMap map;
		private Image backGround;
		private ArrayList<Player> players;

		public GamePanel(GameWorker worker, ClientMap map,
				ArrayList<Player> players) throws IOException {
			backGround = map.background;
			setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
			addMouseListener(MyListener.getInstance());
			MyListener.setGameWorker(worker);
			this.map = map;
			this.players = players;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			int k, j;

			g.drawImage(backGround, 0, 0, this);
			g.setColor(new Color(21, 0, 34));
			g.setFont(map.font);

			for (int i = 0; i < map.getNodes().length; i++) {
				g.drawImage(map.getNodes()[i].getImage(),
						map.getCooImgs()[i].x, map.getCooImgs()[i].y,
						map.getCooImgs()[i].width, map.getCooImgs()[i].height,
						null);
				j = 0;
				for (k = 0; k < players.size(); k++)
					if (players.get(k).getCurrent().getName()
							.equals(map.getNodes()[i].getName())) {
						if (!players.get(k).getName()
								.equals(Constants.RENEGADE))
							g.drawString(players.get(k).getName(),
									map.getCooPlayerLabe()[i][j].x,
									map.getCooPlayerLabe()[i][j].y);
						g.drawImage(players.get(k).getImage(), map
								.getCooPlayerLabe()[i][j].x, (int) (map
								.getCooPlayerLabe()[i][j].y - Math
								.round(map.spriteHeight * 1.20)),
								map.spriteWidth, map.spriteHeight, null);
						j++;
					}
			}
		}
	}
}
