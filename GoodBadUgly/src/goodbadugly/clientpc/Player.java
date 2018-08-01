package goodbadugly.clientpc;

import goodbadugly.common.Constants;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private String name;
	private boolean team; // true white, false black
	private int ammo;
	private NodeClient current;
	private Image img;
	private Image imgyou;

	public Player(String name, boolean team, int ammo, NodeClient initial) {
		this.name = name;
		this.team = team;
		current = initial;
		this.ammo = ammo;
		try {
			if (name.equals(Constants.RENEGADE))
				img = ImageIO.read(new File("bin/images/players/renegade.png"));
			else if (team) {
				img = ImageIO.read(new File("bin/images/players/white.png"));
				imgyou = ImageIO.read(new File("bin/images/players/white_you.png"));
			} else {
				img = ImageIO.read(new File("bin/images/players/black.png"));
				imgyou = ImageIO.read(new File("bin/images/players/black_you.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public boolean getTeam() {
		return team;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int a) {
		ammo = a;
	}

	public void setCurrent(NodeClient node) {
		current = node;
	}

	public NodeClient getCurrent() {
		return current;
	}

	public Image getImage() {
		return name.equals("You") ? imgyou : img;
	}
}
